package sfg;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by first on 5/2/2018.
 */
public class MyNode extends Circle {
    int id = -1;
    ArrayList<MyEdge> outEdgesArr= new ArrayList<>();
    ArrayList<MyEdge> inEdgesArr = new ArrayList<>();

   Label indexLabel = new Label();

    public MyNode(int centerX, int centerY, int radius, int id) {
        super(centerX,centerY,radius);
        this.id =id;
    }


    public void move(double eventX,double eventY){
        super.setCenterX(eventX);
        super.setCenterY(eventY);
        //move edges
    }

    /*public void AddInEdge(MyEdge2 inEdge){
        inEdgeIndex.add(inEdge);
    }
    public void AddOutEdge(MyEdge2 outEdge){
        outEdgeIndex.add(outEdge);
    }
    public void removeInEdge(MyEdge2 inEdge){
        inEdgeIndex.remove(inEdge);
    }
    public void removeOutEdge(MyEdge2 outEdge){
        outEdgeIndex.remove(outEdge);
    }*/

}
