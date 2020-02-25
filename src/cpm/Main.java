package cpm;

import java.util.ArrayList;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        CPM cpm = new CPM();
        cpm.getDataFromFile("dane_cpm.txt");
        cpm.getTasksTimes();
        cpm.printAllTasks();

        ArrayList<Task> criticalPath = new ArrayList<Task>();
        cpm.findCriticalPath(cpm.getTasks(), criticalPath);
        cpm.printCriticalPath(criticalPath);

        cpm.printSchedule();

        Graph frame = new Graph(cpm.getTasks(), criticalPath);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);

//        cpm.init();
//
//        JFrame frame = new JFrame();
//        frame.getContentPane().add(cpm);
//        frame.setTitle("Sieć AN");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
}
