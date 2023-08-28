import asqlite
import json
import os
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
    source_title: str
    source_url: str


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
    print("Database setup complete")


@app.get("/get-question")
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
                conditions.append("difficulty = ?")
                values.append(str(difficulty))

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
                        "source_title": question[11],
                        "source_url": question[12]
                    }
                    to_return.append(QuestionResponseModel(**dict_o_values))
            if to_return:
                return to_return
            raise HTTPException(status_code=404, detail="No questions found")


@app.get("/health")
async def health():
    return {"status": "OK", "uptime": f"{int(time.time())  - start_time} seconds",
            "OS": f"{platform.system()} - {platform.release()}"}


@app.post("/stop")
async def stop():
    os.kill(os.getpid(), signal.SIGINT)
