package johnson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Task {

    private int taskNumber;
    private ArrayList<Integer> durations;
    private int modDuration1;
    private int modDuration2;

    public Task()
    {
        super();
    }

    public Task(int taskNumber, ArrayList<Integer> durations, int modDuration1, int modDuration2) {
        super();
        this.taskNumber = taskNumber;
        this.durations = durations;
        this.modDuration1 = modDuration1;
        this.modDuration2 = modDuration2;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public void setDurations(ArrayList<Integer> durations) {
        this.durations = durations;
    }

    public int getModDuration1() {
        return modDuration1;
    }

    public void setModDuration1(int modDuration1) {
        this.modDuration1 = modDuration1;
    }

    public int getModDuration2() {
        return modDuration2;
    }

    public void setModDuration2(int modDuration2) {
        this.modDuration2 = modDuration2;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskNumber=" + taskNumber +
                ", durations=" + printArrayList(durations) +
                ", modDuration1=" + modDuration1 +
                ", modDuration2=" + modDuration2 +
                '}';
    }

    public static String printTaskArrayList(ArrayList<Task> al){
        String s = "";
        for(Task t : al){
            s += String.format("%3d", t.getTaskNumber()) + ";";
        }
        return s;
    }

    public static String printArrayList(ArrayList<Integer> al){
        String s = "";
        for(int duration : al){
            s += duration + ";";
        }
        return s;
    }

    public static Comparator<Task> compareMod1 = new Comparator<Task>()
    {
        public int compare(Task task1, Task task2)
        {
            int duration1 = task1.getModDuration1();
            int duration2 = task2.getModDuration1();
            return duration1 - duration2;
        }
    };

    public static Comparator<Task> compareMod2 = new Comparator<Task>()
    {
        public int compare(Task task1, Task task2)
        {
            int duration1 = task1.getModDuration1();
            int duration2 = task2.getModDuration1();
            return duration2 - duration1;
        }
    };
}
