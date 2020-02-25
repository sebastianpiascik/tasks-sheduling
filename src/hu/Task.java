package hu;

import java.util.ArrayList;
import java.util.Comparator;

public class Task{

    private int taskNumber;
    private int duration;
    private int startTime;
    private int finishTime;
    private int level;
    private boolean isCompleted = false;
    private ArrayList<Task> previousTasks;
    private ArrayList<Task> nextTasks;

    public Task()
    {
        super();
    }

    public Task(int taskNumber, int duration, int level,  ArrayList<Task> previousTasks, ArrayList<Task> nextTasks)
    {
        super();
        this.taskNumber = taskNumber;
        this.duration = duration;
        this.level = level;
        this.previousTasks = previousTasks;
        this.nextTasks = nextTasks;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Task> getPreviousTasks() {
        return previousTasks;
    }

    public void setPreviousTasks(ArrayList<Task> previousTasks) {
        this.previousTasks = previousTasks;
    }

    public ArrayList<Task> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(ArrayList<Task> nextTasks) {
        this.nextTasks = nextTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskNumber=" + taskNumber +
                ", duration=" + duration +
                ", level=" + level +
                ", isCompleted=" + isCompleted +
                ", previousTasks=" + printArrayList(previousTasks) +
                ", nextTasks=" + printArrayList(nextTasks) +
                '}';
    }

    public static String printArrayList(ArrayList<Task> al){
        String s = "";
        for(Task t : al){
            s += t.getTaskNumber() + ";";
        }
        return s;
    }

    public static Comparator<Task> compareLevels = new Comparator<Task>()
    {
        public int compare(Task task1, Task task2)
        {
            int lvl1 = task1.getLevel();
            int lvl2 = task2.getLevel();
            return lvl2 - lvl1;
        }
    };
}

