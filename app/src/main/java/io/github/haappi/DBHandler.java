package io.github.haappi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "woah";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_STUDENT = "students";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_STUDENT_NAME = "student_name";

    public static final String TABLE_CLASS = "classes";
    public static final String COLUMN_CLASS_ID = "class_id";
    public static final String COLUMN_CLASS_NAME = "class_name";

    public static final String TABLE_STUDENT_CLASSES = "student_classes";
    public static final String COLUMN_STUDENT_CLASS_ID = "student_class_id";

    private static final String DATABASE_CREATE_STUDENT =
            "create table if not exists "
                    + TABLE_STUDENT
                    + "("
                    + COLUMN_STUDENT_ID
                    + " integer primary key autoincrement, "
                    + COLUMN_STUDENT_NAME
                    + " text not null);";

    private static final String DATABASE_CREATE_CLASS =
            "create table if not exists "
                    + TABLE_CLASS
                    + "("
                    + COLUMN_CLASS_ID
                    + " integer primary key autoincrement, "
                    + COLUMN_CLASS_NAME
                    + " text not null);";

    private static final String DATABASE_CREATE_STUDENT_CLASSES =
            "create table if not exists "
                    + TABLE_STUDENT_CLASSES
                    + "("
                    + COLUMN_STUDENT_CLASS_ID
                    + " integer primary key autoincrement, "
                    + COLUMN_STUDENT_ID
                    + " integer not null, "
                    + COLUMN_CLASS_ID
                    + " integer not null, "
                    + "foreign key("
                    + COLUMN_STUDENT_ID
                    + ") references "
                    + TABLE_STUDENT
                    + "("
                    + COLUMN_STUDENT_ID
                    + "), "
                    + "foreign key("
                    + COLUMN_CLASS_ID
                    + ") references "
                    + TABLE_CLASS
                    + "("
                    + COLUMN_CLASS_ID
                    + "));";

    private static DBHandler instance;

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    public static DBHandler getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBHandler not initialized. Call this method with Context.");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CLASS);
        database.execSQL(DATABASE_CREATE_STUDENT);
        database.execSQL(DATABASE_CREATE_STUDENT_CLASSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new RuntimeException("Not implemented");
    }

    public ArrayList<SubmitClass> getAll() {
        return null;
    }

    @SuppressLint("Range")
    public Student getStudentWithEnrolledClasses(long studentId) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Query the student table to get student details
        Cursor studentCursor =
                database.query(
                        TABLE_STUDENT,
                        null,
                        COLUMN_STUDENT_ID + " = ?",
                        new String[] {String.valueOf(studentId)},
                        null,
                        null,
                        null);

        Student student = null;

        if (studentCursor != null && studentCursor.moveToFirst()) {
            student = new Student();
            student.setStudentId(
                    studentCursor.getLong(studentCursor.getColumnIndex(COLUMN_STUDENT_ID)));
            student.setStudentName(
                    studentCursor.getString(studentCursor.getColumnIndex(COLUMN_STUDENT_NAME)));

            String[] columns = {COLUMN_CLASS_ID};
            String selection = COLUMN_STUDENT_ID + " = ?";
            String[] selectionArgs = {String.valueOf(studentId)};

            Cursor classCursor =
                    database.query(
                            TABLE_STUDENT_CLASSES,
                            columns,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            null);

            if (classCursor != null) {
                List<Long> enrolledClassIds = new ArrayList<>();
                while (classCursor.moveToNext()) {
                    enrolledClassIds.add(
                            classCursor.getLong(classCursor.getColumnIndex(COLUMN_CLASS_ID)));
                }
                classCursor.close();

                student.setEnrolledClassIds(enrolledClassIds);
            }
        }

        if (studentCursor != null) {
            studentCursor.close();
        }

        return student;
    }

    @SuppressLint("Range")
    public ClassObj getClassWithEnrolledStudents(long classId) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Query the classes table to get class details
        Cursor classCursor =
                database.query(
                        TABLE_CLASS,
                        null,
                        COLUMN_CLASS_ID + " = ?",
                        new String[] {String.valueOf(classId)},
                        null,
                        null,
                        null);

        ClassObj classObject = null;

        if (classCursor != null && classCursor.moveToFirst()) {
            classObject = new ClassObj();
            classObject.setClassId(
                    classCursor.getLong(classCursor.getColumnIndex(COLUMN_CLASS_ID)));
            classObject.setClassName(
                    classCursor.getString(classCursor.getColumnIndex(COLUMN_CLASS_NAME)));

            String[] columns = {COLUMN_STUDENT_ID};
            String selection = COLUMN_CLASS_ID + " = ?";
            String[] selectionArgs = {String.valueOf(classId)};

            Cursor studentCursor =
                    database.query(
                            TABLE_STUDENT_CLASSES,
                            columns,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            null);

            if (studentCursor != null) {
                List<Long> enrolledStudentIds = new ArrayList<>();
                while (studentCursor.moveToNext()) {
                    enrolledStudentIds.add(
                            studentCursor.getLong(studentCursor.getColumnIndex(COLUMN_STUDENT_ID)));
                }
                studentCursor.close();

                classObject.setEnrolledStudentIds(enrolledStudentIds);
            }
        }

        if (classCursor != null) {
            classCursor.close();
        }

        return classObject;
    }


    @SuppressLint("Range")
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {COLUMN_STUDENT_ID, COLUMN_STUDENT_NAME};

        Cursor cursor = database.query(TABLE_STUDENT, columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long studentId = cursor.getLong(cursor.getColumnIndex(COLUMN_STUDENT_ID));
                String studentName = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME));

                Student student = new Student();
                student.setStudentId(studentId);
                student.setStudentName(studentName);

                studentList.add(student);
            }
            cursor.close();
        }

        return studentList;
    }
}
