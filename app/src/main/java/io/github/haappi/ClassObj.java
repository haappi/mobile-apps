package io.github.haappi;

import java.util.List;

public class ClassObj {
    private long classId;
    private String className;
    private List<Long> enrolledStudentIds;

    public ClassObj() {
    }

    public ClassObj(String className) {
        this.className = className;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Long> getEnrolledStudentIds() {
        return enrolledStudentIds;
    }

    public void setEnrolledStudentIds(List<Long> enrolledStudentIds) {
        this.enrolledStudentIds = enrolledStudentIds;
    }
}
