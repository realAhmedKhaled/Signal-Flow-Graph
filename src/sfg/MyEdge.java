package sfg;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

/**
 * Created by first on 5/2/2018.
 */
public class MyEdge extends QuadCurve {
    int fromIndex;
    int toIndex;
    double value;
    MyNode fromNode ;
    MyNode toNode;
    //int index = -1;
    EdgeLabel gainLabel;
    /*public MyEdge2(int fromIndex,int toIndex,int index){
        super();
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.index = index;
    }*/

    public MyEdge(int fromIndex,int toIndex,double v){
        super();
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        value =v;
    }

    public void setLabel (double layoutX , double layoutY){

    }

}
