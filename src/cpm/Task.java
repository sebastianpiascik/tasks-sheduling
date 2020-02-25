package cpm;

import java.util.ArrayList;

public class Task {

    private int taskNumber;
    private int duration;
    private int startTime;
    private int finishTime;
    private ArrayList<Task> previousTasks;

    public Task()
    {
        super();
    }

    public Task(int taskNumber, int duration, int startTime,
                int finishTime, ArrayList<Task> previousTasks)
    {
        super();
        this.taskNumber = taskNumber;
        this.duration = duration;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.previousTasks = previousTasks;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public ArrayList<Task> getPreviousTasks() {
        return previousTasks;
    }

    public void setPreviousTasks(ArrayList<Task> previousTasks) {
        this.previousTasks = previousTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskNumber=" + taskNumber +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", previousTasks=" + printArrayList(previousTasks) +
                '}';
    }

    public static String printArrayList(ArrayList<Task> al){
        String s = "";
        for(Task t : al){
            s += t.getTaskNumber() + ";";
        }
        return s;
    }
}
