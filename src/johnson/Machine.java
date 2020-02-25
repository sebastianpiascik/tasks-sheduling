package johnson;

import java.util.ArrayList;

public class Machine
{
    private int machineNumber;
    private ArrayList<Task> schedule;

    public Machine()
    {
        super();
    }

    public Machine(int machineNumber)
    {
        super();
        this.machineNumber = machineNumber;
        this.schedule = new ArrayList<Task>();
    }

    public int getMachineNumber()
    {
        return machineNumber;
    }
    public void setMachineNumber(int machineNumber)
    {
        this.machineNumber = machineNumber;
    }

    public ArrayList<Task> getSchedule()
    {
        return schedule;
    }
    public void setSchedule(ArrayList<Task> schedule)
    {
        this.schedule = schedule;
    }
}
