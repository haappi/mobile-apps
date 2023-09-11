package io.github.haappi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private long studentId;
    private String studentName;
    private List<Long> enrolledClasses;

    public Student() {

    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<Long> getEnrolledClassIds() {
        return enrolledClasses;
    }

    public void setEnrolledClassIds(List<Long> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public String toString() {
        return "name=" + studentName + " id=" + studentId + " classes=" + enrolledClasses.stream().collect(ArrayList::new, (list, entry) -> list.add(entry.toString()), ArrayList::addAll);
    }
}
