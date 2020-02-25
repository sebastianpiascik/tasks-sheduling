package johnson;

public class Main {

    public static void main(String[] args)
    {
        Johnson johnson = new Johnson();
        johnson.getDataFromFile("dane_johnson.txt");
        johnson.printAllTasks();
        johnson.calculateModDurations();
        johnson.assingCollections();
        johnson.printAllTasks();
        johnson.createSchema();
        johnson.printMachines();
    }
}
