package hu;

import javax.swing.JFrame;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;

public class Graph extends JFrame
{
    private static final long serialVersionUID = -2707712944901661771L;

    public Graph(ArrayList<Task> tasks)
    {
        super("graph!");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            String elementsNames[] = new String[tasks.size()];
            Object elements[] = new Object[tasks.size()];

            for (int i = 0; i < tasks.size(); i++) {
                elementsNames[i] = "Z" + tasks.get(i).getTaskNumber() + ", " + tasks.get(i).getLevel();
                elements[i] = graph.insertVertex(parent, null, elementsNames[i], 0, 0, 80, 30);

                if (!tasks.get(i).getPreviousTasks().isEmpty()) {
                    for (Task previousTask : tasks.get(i).getPreviousTasks()) {
                        graph.insertEdge(parent, null, "", elements[previousTask.getTaskNumber() - 1], elements[i]);
                    }
                }
            }

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.setUseBoundingBox(false);

            layout.execute(parent);
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}
