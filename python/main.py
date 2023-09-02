import asqlite
import json
import platform
import signal
import time
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os

app = FastAPI()
start_time = int(time.time())

db_path = os.environ.get("DB_PATH", "trivia.sqlite")


class QuestionResponseModel(BaseModel):
    id: int
    image_type: str
    image_data: str
    question: str
    answers: list[str]
    correct: int
    explanation: str
    hint: str
    difficulty: int
    category: str
    tags: list[str]
    source: str
    source_url: str

class InputResponseModel(BaseModel):
    image_type: str
    image_data: str
    question: str
    answers: list[str]
    correct: int
    explanation: str
    hint: str
    difficulty: int
    category: str
    tags: list[str]
    source: str
    source_url: str

def seconds_to_human_readable(seconds):
  """Converts seconds to human readable format.

  Args:
    seconds: The number of seconds to convert.

  Returns:
    A human readable string representation of the number of seconds.
  """

  # Convert seconds to days, hours, minutes, and seconds.
  days = seconds // 86400
  hours = (seconds % 86400) // 3600
  minutes = (seconds % 3600) // 60
  seconds = seconds % 60

  # Format the human readable string.
  return '{days} days, {hours} hours, {minutes} minutes, {seconds} seconds'.format(
      days=days, hours=hours, minutes=minutes, seconds=seconds)


@app.on_event("startup")
async def startup_event():
    print("Starting up...")
    print(f"OS: {platform.system()} - {platform.release()}")

    print("Setting up database...")
    async with asqlite.connect(db_path) as conn:
        async with conn.cursor() as cursor:
            await cursor.execute('''
                CREATE TABLE IF NOT EXISTS questions (
                    id INTEGER PRIMARY KEY,
                    question TEXT,
                    category TEXT,
                    image_type TEXT,
                    image_data TEXT,
                    answers TEXT,
                    correct INTEGER,
                    explanation TEXT,
                    hint TEXT,
                    difficulty INTEGER,
                    tags TEXT,
                    source TEXT,
                    source_url TEXT
                );
            ''')
            await conn.commit()
            await cursor.execute('SELECT * FROM questions')
            await cursor.execute('SELECT COUNT(*) FROM questions')
            print(f"Found {(await cursor.fetchone())[0]} questions")
    print("Database setup complete")


@app.get("/question")
async def root(category: str | None = None, difficulty: int | None = None, limit: int | None = 1) -> \
list[QuestionResponseModel]:
    async with asqlite.connect(db_path) as conn:
        async with conn.cursor() as cursor:
            query = "SELECT * FROM questions WHERE 1=1"  # where just so i can join the and later

            conditions = []
            values = []  # only used so someone can't inject SQL

            if category:
                conditions.append("category = ?")
                values.append(str(category))  # prevent SQL injection
            if difficulty:
                conditions.append(f"difficulty = {difficulty}")

            if conditions:
                query += " AND " + " AND ".join(conditions)
                query += f" ORDER BY RANDOM() LIMIT {limit}"
                await cursor.execute(query, "".join(values))
            else:
                query += f" ORDER BY RANDOM() LIMIT {limit}"
                await cursor.execute(query)

            questions = await cursor.fetchall()

            to_return = []

            for question in questions:
                if question:
                    dict_o_values = {
                        "id": question[0],
                        "question": question[1],
                        "category": question[2],
                        "image_type": question[3],
                        "image_data": question[4],
                        "answers": json.loads(question[5]),
                        "correct": question[6],
                        "explanation": question[7],
                        "hint": question[8],
                        "difficulty": question[9],
                        "tags": json.loads(question[10]),
                        "source": question[11],
                        "source_url": question[12]
                    }
                    to_return.append(QuestionResponseModel(**dict_o_values))
            if to_return:
                return to_return
            raise HTTPException(status_code=404, detail="No questions found")


@app.post("/question")
async def add_question(question: InputResponseModel):
    async with asqlite.connect(db_path) as conn:
        async with conn.cursor() as cursor:
            query = '''
                INSERT INTO questions (
                    question, category, image_type, image_data, answers, correct, explanation,
                    hint, difficulty, tags, source, source_url
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            '''
            await cursor.execute(query,
                                 question.question, question.category, question.image_type,
                                 question.image_data, json.dumps(question.answers),
                                 question.correct, question.explanation, question.hint,
                                 question.difficulty, json.dumps(question.tags),
                                 question.source, question.source_url)
            await conn.commit()

    return {"message": "Question added successfully"}


@app.get("/health")
async def health():
    return {"status": "OK", "uptime": f"{seconds_to_human_readable(int(time.time())  - start_time)}",
            "OS": f"{platform.system()} - {platform.release()}"}


@app.post("/stop")
async def stop():
    os.kill(os.getpid(), signal.SIGINT)
