package hu;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        Hu hu = new Hu();

        hu.getDataFromFile("dane_hu2.txt");
        System.out.println("Liczba maszyn: "+hu.numberOfMachines);

        hu.setLevels();
        System.out.println("All tasks");
        hu.printAllTasks();

        Graph frame = new Graph(hu.tasks);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);


        if (hu.inOrOutTree.equals("out-tree")) {
            hu.fixSetLevels();
            System.out.println("All tasks after fix out-tree");
            hu.printAllTasks();
        }

        hu.makeShedule();



    }
}
