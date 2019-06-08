package com.example.badanie.Models;

public class Query {

    public void setTime(String time) {
        this.time = time;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    private String time;
    private int taskNumber;

    @Override
    public String toString() {
        return "Query{" +
                "time='" + time + '\'' +
                ", taskNumber=" + taskNumber +
                '}';
    }
}
