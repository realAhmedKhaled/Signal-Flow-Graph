package sfg;

import javafx.scene.control.Label;

/**
 * Created by first on 5/3/2018.
 */
public class EdgeLabel extends Label {
    MyEdge edge;
    public EdgeLabel(MyEdge edge,String text){
        super(text);
        this.edge = edge;
    }
}
