package cpm;

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

public class CPM extends JApplet {
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

                Task task = new Task(taskNumber, taskDuration, 0, 0, new ArrayList<Task>());
                this.tasks.add(task);
            }

            for (String line : fileContent) {
                String[] singleTaskArr = line.split(";");
                int taskNumber = Integer.parseInt(singleTaskArr[0]);

                if (singleTaskArr[2].equals("-")) {
//                    System.out.println("Nie ma poprzedników");
                } else {
                    String[] singleTaskConnectionsArr = singleTaskArr[2].split(",");
                    for (String s : singleTaskConnectionsArr) {
                        addPreviousTasks(this.tasks.get(taskNumber - 1), tasks.get(Integer.parseInt(s) - 1));
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

    public void addPreviousTasks(Task currentTask, Task previousTask) {
        if (currentTask.getTaskNumber() != previousTask.getTaskNumber()) {
            if (currentTask.getTaskNumber() > previousTask.getTaskNumber()) {
                currentTask.getPreviousTasks().add(previousTask);
            }
        }
    }

    public void getTasksTimes() {
        for (Task task : this.tasks) {
            if (task.getPreviousTasks().isEmpty()) {
                task.setStartTime(0);
                task.setFinishTime(task.getDuration());
            } else {

                // Cykliczność
                for (Task previousTask : task.getPreviousTasks()) {
                    if (previousTask.getTaskNumber() == task.getTaskNumber()) {
                        previousTask.setStartTime(task.getStartTime());
                        previousTask.setFinishTime(task.getFinishTime());
                    }
                }

                int[] previousTasksFinishTimes = new int[task.getPreviousTasks().size()];

                for (int i = 0; i < task.getPreviousTasks().size(); i++) {
                    previousTasksFinishTimes[i] = task.getPreviousTasks().get(i).getFinishTime();
                }
                Arrays.sort(previousTasksFinishTimes);

                int maxFinishTime = previousTasksFinishTimes[previousTasksFinishTimes.length - 1];
                task.setStartTime(maxFinishTime);
                task.setFinishTime(maxFinishTime + task.getDuration());
            }
        }
    }

    public void findCriticalPath(ArrayList<Task> availableTasks, ArrayList<Task> criticalPath) {
        Task maxTask = new Task();
        for (Task task : availableTasks) {
            if (task.getFinishTime() > maxTask.getFinishTime()) {
                maxTask = task;
            }
        }
        criticalPath.add(maxTask);

        if (!maxTask.getPreviousTasks().isEmpty()) {
            findCriticalPath(maxTask.getPreviousTasks(), criticalPath);
        }
    }

    public ArrayList<Task> returnCriticalPath() {
        ArrayList<Task> cp = new ArrayList<Task>();
        findCriticalPath(getTasks(), cp);
        return cp;
    }

    public void printCriticalPath(ArrayList<Task> criticalPath) {
        System.out.println("\nCritical Path:");
        for (Task t : criticalPath) {
            System.out.print(" -> " + t.getTaskNumber());
        }
        System.out.println();
    }

    @Override
    public void init() {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
                new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
//        DirectedGraph<String, DefaultEdge> directedGraph
//                = new DefaultDirectedGraph<>(DefaultEdge.class);

//        UndirectedGraph<String, DefaultEdge> graph =
//                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

//        Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);


        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        String elements[] = new String[tasks.size()];

        for (int i = 0; i < tasks.size(); i++) {
            elements[i] = "Z" + tasks.get(i).getTaskNumber() + ", " + tasks.get(i).getDuration();
            g.addVertex(elements[i]);

            if (!tasks.get(i).getPreviousTasks().isEmpty()) {
                for (Task previousTask : tasks.get(i).getPreviousTasks()) {
                    g.addEdge(elements[previousTask.getTaskNumber() - 1], elements[i]);
                }
            }
        }

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void printSchedule() {
        int scheduleTime = 0;

        ArrayList<Task> cp = new ArrayList<Task>();
        this.findCriticalPath(this.getTasks(), cp);
        for(Task task : cp){
            scheduleTime = scheduleTime + task.getDuration();
        }
        System.out.println("\nNajdluzszy czas: "+scheduleTime);


        ArrayList<Task> availableTasks = new ArrayList<Task>();
        for(Task t : this.tasks){
            availableTasks.add(t);
        }
        int[][] schedule = new int[this.tasks.size()][scheduleTime];
        for (int i=0;i<this.tasks.size();i++) {
            for(int j=0;j<scheduleTime;j++){
                schedule[i][j]=0;
            }
        }
        int machine = 0;

        Collections.reverse(availableTasks);

        System.out.println("\nHarmonogram");
        for (int i=availableTasks.size()-1;i>=0;i--) {
            if(cp.contains(availableTasks.get(i))){
//                System.out.print("Z" + availableTasks.get(i).getTaskNumber() + ":" + availableTasks.get(i).getDuration() + " - ");
                for(int j=availableTasks.get(i).getStartTime();j<availableTasks.get(i).getFinishTime();j++){
                    schedule[machine][j]=availableTasks.get(i).getTaskNumber();
                }
                availableTasks.remove(i);
            }
        }
        machine++;

        boolean isFree = true;

        while(!availableTasks.isEmpty()){
            isFree = true;
            for(int i=availableTasks.size()-1;i>=0;i--){
                for(int j=availableTasks.get(i).getStartTime();j<availableTasks.get(i).getFinishTime();j++){
                    if(schedule[machine][j] != 0){
                        isFree=false;
                    }
                }
                if(isFree){
                    for(int j=availableTasks.get(i).getStartTime();j<availableTasks.get(i).getFinishTime();j++){
                        schedule[machine][j]=availableTasks.get(i).getTaskNumber();
                    }
                    availableTasks.remove(i);
                }
            }
            machine++;
        }



        for (int i=0;i<this.tasks.size();i++) {
            System.out.print("[ ");
                for(int j=0;j<scheduleTime;j++){
                    System.out.print(schedule[i][j]+", ");
                }
            System.out.println(" ]");
        }
    }
}
