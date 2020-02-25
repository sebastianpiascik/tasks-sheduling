package johnson;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;

import javax.crypto.Mac;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Johnson extends JApplet {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    public ArrayList<Machine> machines = new ArrayList<Machine>();
    public ArrayList<Task> tasksN1 = new ArrayList<Task>();
    public ArrayList<Task> tasksN2 = new ArrayList<Task>();

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void getDataFromFile(String fileName) {

        int machinesNumber = 3;
        for (int i = 0; i < machinesNumber; i++) {
            machines.add(new Machine((i + 1)));
        }

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
                String[] taskDurations = singleTaskArr[1].split(",");
                ArrayList<Integer> durations = new ArrayList<Integer>();

                if (taskDurations.length < machinesNumber)
                {
                    System.out.println("Podano za mało czasów działania");
                    System.exit(0);
                }
                else if (taskDurations.length > machinesNumber)
                {
                    System.out.println("Podano za dużo czasów działania");
                    System.exit(0);
                }

                for (String s : taskDurations) {
                    if (s.isEmpty())
                    {
                        System.out.println("Uzupelnij dane");
                        System.exit(0);
                    }
                    durations.add(Integer.parseInt(s));
                }

                if (durations.get(0) < durations.get(1) && durations.get(2) < durations.get(1))
                {
                    System.out.println("Maszyna druga nie może zdominować pozostałych");
                    System.exit(0);
                }

                Task task = new Task(taskNumber, durations, 0, 0);
                this.tasks.add(task);
            }
        }
    }

    public void printAllTasks() {
        System.out.println();
        for (Task t : this.tasks) {
            System.out.println(t.toString());
        }
    }

    public void printTasks(ArrayList<Task> tasksToPrint) {
        for (Task t : tasksToPrint) {
            System.out.println(t.toString());
        }
    }

    public void calculateModDurations() {
        for (Task task : this.tasks) {
            int modDuration1 = task.getDurations().get(0) + task.getDurations().get(1);
            int modDuration2 = task.getDurations().get(1) + task.getDurations().get(2);
            task.setModDuration1(modDuration1);
            task.setModDuration2(modDuration2);
        }
    }


    public void assingCollections() {
        for (Task task : this.tasks) {
            if (task.getModDuration1() < task.getModDuration2()) {
                this.tasksN1.add(task);
            } else if (task.getModDuration1() >= task.getModDuration2()) {
                this.tasksN2.add(task);
            }
        }
    }

    public void createSchema() {

        Collections.sort(tasksN1, Task.compareMod1);
        System.out.println("\nPo sortowaniu N1: ");
        printTasks(tasksN1);

        Collections.sort(tasksN2, Task.compareMod2);
        System.out.println("\nPo sortowaniu N2: ");
        printTasks(tasksN2);


        for (Task t : this.tasksN1) {
            for (int j = 0; j < t.getDurations().size(); j++) {
                for (int k = 0; k < t.getDurations().get(j); k++) {
                    machines.get(j).getSchedule().add(t);
                }
                for(int i=0; i<machines.size();i++){
                    if(i > j){
                        for (int k = 0; k < t.getDurations().get(j); k++) {
                            if(machines.get(i).getSchedule().size() < machines.get(j).getSchedule().size()){
                                machines.get(i).getSchedule().add(new Task(0, new ArrayList<Integer>(), 0, 0));
                            }
                        }
                    }
                }
            }
        }


        for (Task t : this.tasksN2) {
            for (int j = 0; j < t.getDurations().size(); j++) {
                for (int k = 0; k < t.getDurations().get(j); k++) {
                    machines.get(j).getSchedule().add(t);
                }
                for(int i=0; i<machines.size();i++){
                    if(i > j){
                        for (int k = 0; k < t.getDurations().get(j); k++) {
                            if(machines.get(i).getSchedule().size() < machines.get(j).getSchedule().size()){
                                machines.get(i).getSchedule().add(new Task(0, new ArrayList<Integer>(), 0, 0));
                            }
                        }
                    }
                }
            }
        }
    }

    public void printMachines() {
        System.out.print("0 : ");
        for(int i=1;i<=this.machines.get(this.machines.size()-1).getSchedule().size();i++){
            System.out.print(String.format("%3d", i) + ";");
        }
        System.out.println();
        System.out.println();
        for (Machine m : this.machines) {
            System.out.println(m.getMachineNumber() + " : " + Task.printTaskArrayList(m.getSchedule()));
        }
    }
}
