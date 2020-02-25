package liu;

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
        super("Graph");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            String elementsNames[] = new String[tasks.size()];
            Object elements[] = new Object[tasks.size()];

            for (int i = 0; i < tasks.size(); i++) {
                    elementsNames[i] = "Z" + tasks.get(i).getTaskNumber() + ", " + tasks.get(i).getDuration();
                elements[i] = graph.insertVertex(parent, null, elementsNames[i], 0, 0, 80, 30);

                if (!tasks.get(i).getPreviousTasks().isEmpty()) {
                    for (Task previousTask : tasks.get(i).getPreviousTasks()) {
                        graph.insertEdge(parent, null, "", elements[previousTask.getTaskNumber() - 1], elements[i]);
                    }
                }
            }

//            Object vDogsRoot = graph.insertVertex(parent, null, "DOG", 0, 0, 80, 30);
//            Object v2 = graph.insertVertex(parent, null, "Shar Pei", 0, 0, 80, 30);
//            Object v3 = graph.insertVertex(parent, null, "Pug", 0, 0, 80, 30);
//            Object v4 = graph.insertVertex(parent, null, "Cocker Spaniel", 0, 0, 80, 30);
//            Object v5 = graph.insertVertex(parent, null, "Pit Bull", 0, 0, 80, 30);
//            Object v6 = graph.insertVertex(parent, null, "Chihuahua", 0, 0, 80, 30);
//
//            graph.insertEdge(parent, null, "", vDogsRoot, v2);
//            graph.insertEdge(parent, null, "", vDogsRoot, v3);
//            graph.insertEdge(parent, null, "", vDogsRoot, v4);
//            graph.insertEdge(parent, null, "", vDogsRoot, v5);
//            graph.insertEdge(parent, null, "", vDogsRoot, v6);

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
