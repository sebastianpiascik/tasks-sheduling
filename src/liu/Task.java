package liu;

import java.util.ArrayList;

public class Task {

    private int taskNumber;
    private int duration;
    private int arrivalTime;
    private int finishTime;
    private int modFinishTime;
    private int lateness;
    private ArrayList<Task> previousTasks;
    private ArrayList<Task> nextTasks;
    private Boolean isActive = false;
    private Boolean isCompleted = false;

    public Task()
    {
        super();
    }

    public Task(int taskNumber, int duration, int arrivalTime,
                int finishTime, int modFinishTime, int lateness, ArrayList<Task> previousTasks, ArrayList<Task> nextTasks,
                Boolean isActive, Boolean isCompleted)
    {
        super();
        this.taskNumber = taskNumber;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.finishTime = finishTime;
        this.modFinishTime = modFinishTime;
        this.lateness = lateness;
        this.previousTasks = previousTasks;
        this.nextTasks = nextTasks;
        this.isActive = isActive;
        this.isCompleted = isCompleted;
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

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getModFinishTime() {
        return modFinishTime;
    }

    public void setModFinishTime(int modFinishTime) {
        this.modFinishTime = modFinishTime;
    }

    public int getLateness() {
        return lateness;
    }

    public void setLateness(int lateness) {
        this.lateness = lateness;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskNumber=" + taskNumber +
                ", duration=" + duration +
                ", arrivalTime=" + arrivalTime +
                ", finishTime=" + finishTime +
                ", modFinishTime=" + modFinishTime +
                ", lateness=" + lateness +
                ", previousTasks=" + printArrayList(previousTasks) +
                ", nextTasks=" + printArrayList(nextTasks) +
                ", isActive=" + isActive +
                ", isCompleted=" + isCompleted +
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
