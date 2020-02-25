package hu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Hu {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    public int numberOfMachines;
    public static String inOrOutTree = new String();

    @SuppressWarnings("Duplicates")
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
                if (singleTaskArr[0].equals("ilosc_maszyn")) {
                    numberOfMachines = Integer.parseInt(singleTaskArr[1]);
                } else {
                    int taskNumber = Integer.parseInt(singleTaskArr[0]);
                    int taskDuration = Integer.parseInt(singleTaskArr[1]);

                    Task task = new Task(taskNumber, taskDuration, 0, new ArrayList<Task>(), new ArrayList<Task>());
                    this.tasks.add(task);
                }
            }

            for (String line : fileContent) {
                String[] singleTaskArr = line.split(";");
                if (!singleTaskArr[0].equals("ilosc_maszyn")) {
                    int taskNumber = Integer.parseInt(singleTaskArr[0]);

                    if (singleTaskArr[2].equals("-")) {
                        System.out.println(singleTaskArr[0] + "Nie ma poprzedników");
                    } else {
                        String[] singleTaskConnectionsArr = singleTaskArr[2].split(",");
                        for (String s : singleTaskConnectionsArr) {
                            addPreviousTasks(this.tasks.get(taskNumber - 1), tasks.get(Integer.parseInt(s) - 1));
                        }
                    }
                }
            }
        }
    }

    public void printAllTasks() {
        for (Task t : this.tasks) {
            System.out.println(t.toString());
        }
    }

    public void printAllTasks(ArrayList<Task> tasksToShow) {
        for (Task t : tasksToShow) {
            System.out.println(t.toString());
        }
    }

    public void addPreviousTasks(Task currentTask, Task previousTask) {
        if (currentTask.getTaskNumber() != previousTask.getTaskNumber()) {
            currentTask.getPreviousTasks().add(previousTask);
            previousTask.getNextTasks().add(currentTask);
        }
    }

    public void setLevels() {
        for (Task task : tasks) {
            task.setLevel(0);
        }

        if (!inOrOutTree.equals("out-tree")) {
            Collections.reverse(tasks);
        }
        for (Task task : tasks) {
            if (task.getNextTasks().isEmpty()) {
                task.setLevel(1);
            } else {
                task.setLevel(task.getNextTasks().get(0).getLevel() + 1);
            }
        }
        if (!inOrOutTree.equals("out-tree")) {
            Collections.reverse(tasks);
        }


        int maxLevel = 0;
        for (Task task : tasks) {
            if (task.getLevel() > maxLevel) maxLevel = task.getLevel();
        }

        int minCounter = 0;
        int maxCounter = 0;
        for (Task task : tasks) {
            if (task.getLevel() == 1) minCounter++;
            if (task.getLevel() == maxLevel) maxCounter++;
        }

        if (!inOrOutTree.equals("out-tree")) {
            if (minCounter > maxCounter) inOrOutTree = "out-tree";
            if (minCounter < maxCounter) inOrOutTree = "in-tree";
        }
        System.out.println("NOW IS: [ " + inOrOutTree + " ]");
    }

    public void fixSetLevels() {
        if (inOrOutTree.equals("out-tree")) {
            for (Task task : tasks) {
                ArrayList<Task> tmpPrevs = task.getPreviousTasks();
                task.setPreviousTasks(task.getNextTasks());
                //task.setPreviousTasks(new ArrayList<Task>());
                task.setNextTasks(tmpPrevs);
            }
            setLevels();
            System.out.print("\n");
        }
    }

    public boolean checkIfAllCompleted(ArrayList<Task> tasksList) {
        boolean allCompleted = true;
        for (Task t : tasksList) {
            if (!t.isCompleted()) {
                allCompleted = false;
            }
        }
        System.out.println(allCompleted);
        return allCompleted;
    }

    public void makeShedule() {

        int time = 0;

        ArrayList<Task> completedTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            Task t = task;
            completedTasks.add(t);
        }
        Collections.sort(completedTasks, Task.compareLevels);

        int[][] schedule = new int[numberOfMachines][tasks.size()];
        for (int i = 0; i < numberOfMachines; i++) {
            for (int j = 0; j < tasks.size(); j++) {
                schedule[i][j] = 0;
            }
        }


        while (!checkIfAllCompleted(completedTasks)) {

            System.out.println();

            ArrayList<Task> tasksToSchedule = new ArrayList<Task>();

            for (Task task : completedTasks) {
                if (task.getPreviousTasks().isEmpty() && !task.isCompleted()) {
                    tasksToSchedule.add(task);
                }
                if (tasksToSchedule.size() == numberOfMachines) break;
            }

            if (tasksToSchedule.size() < numberOfMachines) {
                for (Task task : completedTasks) {
                    if (!task.isCompleted()) {
                        boolean scheduleCurrentTask = true;
                        for (Task prevTask : task.getPreviousTasks()) {
                            if (!prevTask.isCompleted()) {
                                scheduleCurrentTask = false;
                            }
                        }
                        if (scheduleCurrentTask) {
                            tasksToSchedule.add(task);
                        }
                    }
                }
            }

            Collections.sort(tasksToSchedule, Task.compareLevels);

            System.out.println("Tasks to schedule");
            printAllTasks(tasksToSchedule);

            int index = 0;
            for (Task task : completedTasks) {
                if (tasksToSchedule.contains(task)) {
                    task.setCompleted(true);
                    schedule[index][time] = task.getTaskNumber();
                    index++;
                }
            }

            //System.out.println("\ncompleted tasks");
            //printAllTasks(completedTasks);

            System.out.println("Time: " + time);

            System.out.println("Schedule: ");
            for (int i = 0; i < numberOfMachines; i++) {
                for (int j = 0; j < tasks.size(); j++) {
                    System.out.print(schedule[i][j] + ",");
                }
                System.out.println();
            }
            time++;
        }

        System.out.println("abc: " + inOrOutTree);
        if (inOrOutTree.equals("out-tree")) {
            int maxColumn = 0;
            for (int j = 0; j < tasks.size(); j++) {
                if (schedule[0][j] != 0) {
                    maxColumn = j;
                }
            }

            //System.out.println("maxcolumn: "+ maxColumn);

            for (int i = 0; i < numberOfMachines; i++) {
                int columnIndex = 0;
                for (int x = maxColumn; x > maxColumn / 2; x--) {
                    //System.out.println("column index: " + columnIndex + ", x: " + x);
                    int tmp = schedule[i][columnIndex];
                    schedule[i][columnIndex] = schedule[i][x];
                    schedule[i][x] = tmp;
                    columnIndex++;
                }
            }

            System.out.println("Schedule: ");
            for (int i = 0; i < numberOfMachines; i++) {
                for (int j = 0; j < tasks.size(); j++) {
                    System.out.print(schedule[i][j] + ",");
                }
                System.out.println();
            }
        }


    }
}
