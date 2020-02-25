package liu;

import javax.swing.*;

public class Main {

    // mod finish
    public static void main(String[] args)
    {
        LIU liu = new LIU();
        liu.getDataFromFile("dane_liu2.txt");
        liu.printAllTasks();

        liu.assignModifiedFinishTime();
        liu.printAllTasks();

        liu.printAllTasks();
        Graph frame = new Graph(liu.getTasks());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);

        liu.makeShedule();
        liu.printAllTasks();
    }
}
