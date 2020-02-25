package liu;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@SuppressWarnings("Duplicates")
public class LIU extends JApplet {
    public ArrayList<Task> tasks = new ArrayList<Task>();

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void getDataFromFile(String fileName) {
        ArrayList<String> fileContent = new ArrayList<String>();
        Scanner in;

        try {
            in = new Scanner(new FileReader(fileName));
            while (in.hasNextLine()) {
                fileContent.add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się otworzyć pliku!");
            System.exit(0);
        }

        if (fileContent.isEmpty()) {
            System.out.println("Plik jest pusty!");
            System.exit(0);
        } else {
            for (String line : fileContent) {
                String[] singleTaskArr = line.split(";");
                int taskNumber = Integer.parseInt(singleTaskArr[0]);
                int taskDuration = Integer.parseInt(singleTaskArr[1]);
                int taskArrivalTime = Integer.parseInt(singleTaskArr[2]);
                int taskFinishTime = Integer.parseInt(singleTaskArr[3]);

                Task task = new Task(taskNumber, taskDuration, taskArrivalTime, taskFinishTime, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false);
                this.tasks.add(task);
            }

            for (String line : fileContent) {
                String[] singleTaskArr = line.split(";");
                int taskNumber = Integer.parseInt(singleTaskArr[0]);

                if (singleTaskArr[4].equals("-")) {
//                    System.out.println("Nie ma poprzedników");
                } else {
                    String[] singleTaskConnectionsArr = singleTaskArr[4].split(",");
                    for (String s : singleTaskConnectionsArr) {
                        addPreviousTasks(this.tasks.get(taskNumber - 1), tasks.get(Integer.parseInt(s) - 1));
                    }
                }
            }
        }
    }

    public void printAllTasks() {
        System.out.println();
        for (Task t : this.tasks) {
            System.out.println(t.toString());
        }
    }

    public void addPreviousTasks(Task currentTask, Task previousTask) {
        // cyklicznosc - nie dodaje tego samego
        if (currentTask.getTaskNumber() != previousTask.getTaskNumber()) {
            currentTask.getPreviousTasks().add(previousTask);
            previousTask.getNextTasks().add(currentTask);
        }
    }

    public ArrayList<Integer> getConnectedTasksFinishTimes(ArrayList<Task> tasksToAssign, ArrayList<Integer> finishTimes) {
        for (Task nextTask : tasksToAssign) {
            finishTimes.add(nextTask.getFinishTime());
            finishTimes = getConnectedTasksFinishTimes(nextTask.getNextTasks(), finishTimes);
        }
        return finishTimes;
    }

    public void assignModifiedFinishTime() {
        for (Task task : this.tasks) {
            ArrayList<Integer> finishTimes = new ArrayList<Integer>();
            finishTimes.add(task.getFinishTime());
            finishTimes = getConnectedTasksFinishTimes(task.getNextTasks(), finishTimes);

            Collections.sort(finishTimes);
            task.setModFinishTime(finishTimes.get(0));
        }
    }

    public boolean isAllTasksCompleted(ArrayList<Task> tasksList) {
        boolean allCompleted = true;
        for (Task t : tasksList) {
            if (!t.getCompleted()) {
                allCompleted = false;
            }
        }
        return allCompleted;
    }

    public void makeShedule() {
        System.out.println("SCHEDULE:");

        int minModifiedFinishIndex = 1;
        int time = 0;

        ArrayList<Task> activeTasks = new ArrayList<Task>();
        ArrayList<String> schedule = new ArrayList<String>();

        while (!isAllTasksCompleted(tasks) || time < 20) {
            for (Task task : tasks) {
                if (task.getArrivalTime() == time) {
                    task.setActive(true);
                    activeTasks.add(task);
                }
            }

            if (!activeTasks.isEmpty()) {
                for (Task task1 : activeTasks) {
                    for (Task task2 : activeTasks) {
                        if (task1.getModFinishTime() <= task2.getModFinishTime()) {
                            if (isAllTasksCompleted(task1.getPreviousTasks()))
                                minModifiedFinishIndex = task1.getTaskNumber();
                        } else {
                            if (isAllTasksCompleted(task2.getPreviousTasks()))
                                minModifiedFinishIndex = task2.getTaskNumber();
                        }
                    }
                }
                Task currentTask = new Task();
                currentTask = tasks.get(minModifiedFinishIndex - 1);
                if (currentTask.getActive() && !currentTask.getCompleted()) {
                    int duration = currentTask.getDuration() - 1;
                    currentTask.setDuration(duration);
                    schedule.add("" + currentTask.getTaskNumber());
                } else {
                    schedule.add("0");
                }
            } else {
                schedule.add("0");
            }

            for (Task task : tasks) {
                if (task.getDuration() == 0 && !task.getCompleted()) {
                    task.setCompleted(true);
                    task.setLateness((time + 1) - task.getFinishTime());
                    activeTasks.remove(task);
                }
            }

            time++;
        }

        System.out.print("time: [");
        for (int i = 0; i < schedule.size(); i++) {
            System.out.print(String.format("%2d", i) + ", ");
        }
        System.out.println("]");

        System.out.print("task: [");
        for (String s : schedule) {
            System.out.print(String.format("%2s", s) + ", ");
        }
        System.out.println("]");
    }
}
