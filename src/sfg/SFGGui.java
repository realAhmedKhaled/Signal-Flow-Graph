package sfg;/**
 * Created by first on 5/1/2018.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import javax.swing.*;

import classes.Graph;

import java.util.ArrayList;

public class SFGGui extends Application {
    ArrayList<MyNode> nodesArr = new ArrayList<>();
    ArrayList<MyEdge> edgesArr = new ArrayList<>();
    Pane root = new Pane();
    AnchorPane canvas = new AnchorPane();
    boolean isAddNode = false;
    boolean isAddEdge = false;
    Button addNodeBtn ;
    Button addEdgeBtn ;
    Button GainBtn ;
    Button resetBtn;

    Label fromLabel;
    Label toLabel;
    Label edgeGainLabel;
    Label startLabel;
    Label endLabel;


    TextField fromTextField ;
    TextField toTextField ;
    TextField edgeGainTextField;
    TextField startTextField;
    TextField endTextField;

    GridPane grid ;

    int numberOfAddedNodes = 0;

    EventHandler<MouseEvent> moveNodeListener =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            MyNode node= (MyNode)event.getSource();
            node.setCenterX(event.getX());
            node.setCenterY(event.getY());
            node.indexLabel.setLayoutX(event.getX()-5);
            node.indexLabel.setLayoutY(event.getY()-15);


            for(int i = 0; i < node.inEdgesArr.size(); i++){
                double toX = event.getX();
                double toY = event.getY();
                double fromX = node.inEdgesArr.get(i).fromNode.getCenterX();
                double x = Math.abs((fromX - toX) / 2);
                double fromY = node.inEdgesArr.get(i).fromNode.getCenterY() - node.getRadius();
                double y = Math.abs((fromY - toY) / 2);
                if (fromX < toX) {
                    x += fromX;
                } else {
                    x += toX;
                }

                if (fromY < toY) {
                    y += fromY;
                } else {
                    y += toY;
                }
                moveEdge(x,y,node.inEdgesArr.get(i));
            }
            for(int i = 0; i < node.outEdgesArr.size(); i++){
                double fromX = event.getX();
                double fromY = event.getY();
                double toX = node.outEdgesArr.get(i).toNode.getCenterX();
                double x = Math.abs((fromX - toX) / 2);
                double toY = node.outEdgesArr.get(i).toNode.getCenterY() - node.getRadius();
                double y = Math.abs((fromY - toY) / 2);
                if (fromX < toX) {
                    x += fromX;
                } else {
                    x += toX;
                }

                if (fromY < toY) {
                    y += fromY;
                } else {
                    y += toY;
                }
                moveEdge(x,y,node.outEdgesArr.get(i));
            }
        }
    };
    EventHandler<MouseEvent> removeNodeListener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            MyNode node = (MyNode) event.getSource();
            if(event.getButton() == MouseButton.SECONDARY){
                removeNode(node);
            }
        }
    };
    EventHandler<MouseEvent> removeEdgeListener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            MyEdge edge = (MyEdge) event.getSource();
            if(event.getButton() == MouseButton.SECONDARY){
                removeEdge(edge);
            }
        }
    };
    EventHandler<MouseEvent> moveEdgeListener=new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            MyEdge  edge= (MyEdge)event.getSource();
            edge.setControlX(event.getX());
            edge.setControlY(event.getY());
            if(edge.fromIndex != edge.toIndex) {
                if (edge.fromNode.getCenterY() - edge.fromNode.getRadius() <= event.getY()) {
                    edge.setStartY(edge.fromNode.getCenterY() + edge.fromNode.getRadius());
                } else {
                    edge.setStartY(edge.fromNode.getCenterY() - edge.fromNode.getRadius());
                }
                if (edge.toNode.getCenterY() - edge.toNode.getRadius()<= event.getY()) {
                    edge.setEndY(edge.toNode.getCenterY() + edge.toNode.getRadius());
                } else {
                    edge.setEndY(edge.toNode.getCenterY() - edge.toNode.getRadius());
                }
            }

            //top equation
            double t = 0.5;
            t =(1-t)*((1-t)*edge.getStartY()+t* edge.getControlY())+t*((1-t)*edge.getControlY()+t*edge.getEndY());

            edge.gainLabel.setLayoutY(t - 15);
            t = 0.5;
            t =(1-t)*((1-t)*edge.getStartX()+t* edge.getControlX())+t*((1-t)*edge.getControlX()+t*edge.getEndX());
            edge.gainLabel.setLayoutX(t);


        }
    };





    private void moveEdge(double x , double y,MyEdge edge){

        if(edge.fromIndex != edge.toIndex) {
            edge.setControlX(x);
            edge.setControlY(y -50);
            if (edge.fromNode.getCenterY() - edge.fromNode.getRadius() <= x) {
                edge.setStartY(edge.fromNode.getCenterY() + edge.fromNode.getRadius());
            } else {
                edge.setStartY(edge.fromNode.getCenterY() - edge.fromNode.getRadius());
            }
            if (edge.toNode.getCenterY() - edge.toNode.getRadius()<= y) {
                edge.setEndY(edge.toNode.getCenterY() + edge.toNode.getRadius());
            } else {
                edge.setEndY(edge.toNode.getCenterY() - edge.toNode.getRadius());
            }
            edge.setStartX(edge.fromNode.getCenterX());
            edge.setEndX(edge.toNode.getCenterX());
        }else{
            edge.setStartX(edge.fromNode.getCenterX() - edge.fromNode.getRadius());
            edge.setEndX(edge.fromNode.getCenterX()  + edge.fromNode.getRadius());

            edge.setStartY(edge.fromNode.getCenterY());
            edge.setEndY(edge.fromNode.getCenterY());

            y = edge.fromNode.getCenterY()  - 4 * edge.fromNode.getRadius();
            x = edge.fromNode.getCenterX();

            edge.setControlX(x);
            edge.setControlY(y - 50);
        }
        //top equation
        double t = 0.5;
        t =(1-t)*((1-t)*edge.getStartY()+t* edge.getControlY())+t*((1-t)*edge.getControlY()+t*edge.getEndY());

        edge.gainLabel.setLayoutY(t - 15);
        t =0.5;
        t =(1-t)*((1-t)*edge.getStartX()+t* edge.getControlX())+t*((1-t)*edge.getControlX()+t*edge.getEndX());
        edge.gainLabel.setLayoutX(t);

    }

    public static void main(String[] args) {
        launch(args);
    }
    private void removeNode(MyNode node){
        nodesArr.remove(node);

        // loop in edges and remove
        for(int i = 0; i < node.inEdgesArr.size(); i++){
            removeEdge(node.inEdgesArr.get(i));
        }
        for(int i = 0; i < node.outEdgesArr.size(); i++){
            removeEdge(node.outEdgesArr.get(i));
        }
        canvas.getChildren().removeAll(node,node.indexLabel);
    }

    private void removeEdge(MyEdge edge){
        edgesArr.remove(edge);
        canvas.getChildren().removeAll(edge,edge.gainLabel);

        edge.fromNode.outEdgesArr.remove(edge);
        edge.toNode.inEdgesArr.remove(edge);
        //remove from in and out nodes
    }
    private void addNode(double x, double y){
        //if(x> 0 && x<785 &&y>0 && y<585) {
            MyNode n = new MyNode((int) Math.round(x), (int) Math.round(y), 15, numberOfAddedNodes);
            n.fillProperty().setValue(new Color(0, 0, 0, 0));
            n.setStroke(new Color(0, 0, 0, 1));
            n.setStrokeWidth(2);
            n.setOnMouseDragged(moveNodeListener);
            n.setOnMouseClicked(removeNodeListener);
            n.indexLabel = new Label(numberOfAddedNodes++ +"");
            n.indexLabel.setLayoutX(x-5);
            n.indexLabel.setLayoutY(y-15);
            n.indexLabel.setMinSize(30,30);
            n.indexLabel.setFont(new Font(15) );
            n.indexLabel.setTextAlignment(TextAlignment.CENTER);
            nodesArr.add(n);
            //n after label to  be above it
            canvas.getChildren().addAll(n.indexLabel,n);
        //}



       // MyNode node = new node
        //add to backend an node
    }
    private void addEdge(){
        int fromIndex,toIndex;
        double value;
        double fromX =0,fromY=0,toX=0,toY=0,x=0,y = 0;
        try {
            String input = fromTextField.getText();
            fromIndex = Integer.parseInt(input);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"wrong from index","error", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            String input = toTextField.getText();
            toIndex = Integer.parseInt(input);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"wrong to index","error", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        try {
            String input = edgeGainTextField.getText();
            value = Double.parseDouble(input);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"wrong gain","error", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        for(int i = 0; i <edgesArr.size(); i++){
            if(edgesArr.get(i).fromIndex == fromIndex && edgesArr.get(i).toIndex == toIndex){
                edgesArr.get(i).value += value;
                edgesArr.get(i).gainLabel.setText(edgesArr.get(i).value+"");
                fromTextField.clear();
                toTextField.clear();
                edgeGainTextField.clear();
                return;
            }
        }


        MyEdge edge = new MyEdge(fromIndex,toIndex,value);
        edge.setOnMouseDragged(moveEdgeListener);
        edge.setOnMouseClicked(removeEdgeListener);
        edge.fromIndex = fromIndex;
        edge.toIndex = toIndex;
        edge.value = value;



        boolean isFromFound= false;
        boolean isToFound = false;
        int i;
        for(i= 0; i < nodesArr.size() && (!isFromFound || !isToFound); i++){
            //System.out.println(nodesArr.get(i).id);
            if(nodesArr.get(i).id == fromIndex) {
                fromX = nodesArr.get(i).getCenterX();
                edge.setStartX(fromX);
                fromY = nodesArr.get(i).getCenterY() - nodesArr.get(i).getRadius();
                edge.setStartY(fromY);
                edge.fromNode = nodesArr.get(i);
                isFromFound = true;
                nodesArr.get(i).outEdgesArr.add(edge);
            }
            if (nodesArr.get(i).id == toIndex){
                toX = nodesArr.get(i).getCenterX();
                toY = nodesArr.get(i).getCenterY()-nodesArr.get(i).getRadius();
                edge.setEndX(toX);
                edge.setEndY(toY);
                edge.toNode = nodesArr.get(i);
                isToFound = true;
                nodesArr.get(i).inEdgesArr.add(edge);
            }
        }

        if(!isFromFound){
            JOptionPane.showMessageDialog(null,"from node isn't found","result", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if(!isToFound){
            //System.out.println(numberOfAddedNodes);
            JOptionPane.showMessageDialog(null,"to node isn't found","result", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if(fromIndex == toIndex){
            i--;
            edge.setStartX(fromX - nodesArr.get(i).getRadius());
            edge.setEndX(fromX + nodesArr.get(i).getRadius());

            edge.setStartY(nodesArr.get(i).getCenterY());
            edge.setEndY(nodesArr.get(i).getCenterY());

           y =nodesArr.get(i).getCenterY() - 4 * nodesArr.get(i).getRadius();
           x = nodesArr.get(i).getCenterX();

        }else {

            x = Math.abs((fromX - toX) / 2);
            y = Math.abs((fromY - toY) / 2);
            if (fromX < toX) {
                x += fromX;
            } else {
                x += toX;
            }

            if (fromY < toY) {
                y += fromY;
            } else {
                y += toY;
            }
        }


        edge.setControlX(x);
        edge.setControlY(y -50);

        edge.fillProperty().setValue(new Color(0,0,0,0));
        edge.setStrokeWidth(3);
        if(fromIndex < toIndex) {
            edge.setStroke(new Color(1, 0, 0, 1));
        }else{
            edge.setStroke(new Color(0, 1, 0, 1));
        }

        edge.gainLabel= new EdgeLabel(edge,value+"");

        /*edge.gainLabel.setLayoutX(x);
        edge.gainLabel.setLayoutY(y);
*/
        //top equation
        double t = 0.5;
        t =(1-t)*((1-t)*edge.getStartY()+t* edge.getControlY())+t*((1-t)*edge.getControlY()+t*edge.getEndY());

        edge.gainLabel.setLayoutY(t - 15);
        t = 0.5;
        t =(1-t)*((1-t)*edge.getStartX()+t* edge.getControlX())+t*((1-t)*edge.getControlX()+t*edge.getEndX());
        edge.gainLabel.setLayoutX(t);


        edgesArr.add(edge);
        canvas.getChildren().addAll(edge,edge.gainLabel);

        fromTextField.clear();
        toTextField.clear();
        edgeGainTextField.clear();
        //add to backend an edge
    }

    private void resetGraph(){
		int size  = nodesArr.size();
		for(int i = 0; i < size; i++){
			canvas.getChildren().remove(nodesArr.get(nodesArr.size()-1));
			canvas.getChildren().remove(nodesArr.get(nodesArr.size()-1).indexLabel);			
			nodesArr.remove(nodesArr.size()-1);
		}
		size  = edgesArr.size();
		for(int i = 0; i < size; i++){
			canvas.getChildren().remove(edgesArr.get(edgesArr.size()-1));
			canvas.getChildren().remove(edgesArr.get(edgesArr.size()-1).gainLabel);
			edgesArr.remove(edgesArr.size()-1);
		}
		numberOfAddedNodes = 0;
	}
    private  void printGain(){
    	int start,end;
    	try {
            String input = startTextField.getText();
            start = Integer.parseInt(input);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"start not found","error", JOptionPane.PLAIN_MESSAGE);
            return;
        }
    	try {
            String input = endTextField.getText();
            end = Integer.parseInt(input);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"end not found","error", JOptionPane.PLAIN_MESSAGE);
            return;
        }
    	
    	boolean isStartNFound= false;
        boolean isEndNFound = false;
       
        int startInArr = -1, endInArr = -1;
    	int [] id = new int [nodesArr.size()];
    	for(int i = 0 ;i < id.length; i++){
    		id[i] = nodesArr.get(i).id;
    		if(nodesArr.get(i).id == start) {
                isStartNFound = true;
                startInArr = i;
            }
            if (nodesArr.get(i).id == end){
                isEndNFound = true;
                endInArr = i;
            }
    	}
    	if(!isStartNFound){
            JOptionPane.showMessageDialog(null,"satrt node isn't found","result", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        if(!isEndNFound){
            //System.out.println(numberOfAddedNodes);
            JOptionPane.showMessageDialog(null,"end node isn't found","result", JOptionPane.PLAIN_MESSAGE);
            return;
        }
    	int[] from = new int[edgesArr.size()];
    	int[] to = new int[edgesArr.size()];
    	int[] gain = new int [edgesArr.size()];
    	
    	for(int i = 0; i < edgesArr.size();i++){
    		gain[i] = (int)Math.round(edgesArr.get(i).value);
    		boolean isStartFound = false, isEndFound = false;
    		for(int j = 0; j < id.length &&(!isEndFound || ! isStartFound); j++){
    			if(id[j] == edgesArr.get(i).fromIndex){
    				from[i] = j; 
    				isStartFound = true;
    			}
    			if(id[j] == edgesArr.get(i).toIndex){
    				to[i] = j;
    				isEndFound = true;
    			}
    		}
    	}
    	
    	Graph graph = new Graph(nodesArr.size(), from,to,gain, startInArr, endInArr,id);
    	
    	String out = new String();
  
    	out += graph.getForwardPathes() +"\n";
    	out += graph.getLoops()+"\n";
    	out += graph.getNonTouchedLoops()+"\n";
    	out += graph.getCalculations();
    	JOptionPane.showMessageDialog(null,out,"result", JOptionPane.PLAIN_MESSAGE);
    }
    private  void initializeButtons(){
        addNodeBtn = new Button();
        addNodeBtn.setText("Add Node");
        addNodeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isAddNode = true;
                if(nodesArr.size() != 0) {
                    addNode(nodesArr.get(nodesArr.size() - 1).getCenterX() + 100, nodesArr.get(nodesArr.size() - 1).getCenterY());
                }else{
                    addNode(50, 300);
                }

            }
        });


        addEdgeBtn = new Button();
        addEdgeBtn.setText("Add Edge");
        addEdgeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isAddEdge = true;
                addEdge();
            }
        });


        GainBtn = new Button();
        GainBtn.setText("Get Gain");
        GainBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printGain();
            }
        });
        
        resetBtn= new Button();
        resetBtn.setText("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				resetGraph();
			}
			
		});


        fromLabel = new Label("from");
        toLabel = new Label("to");
        edgeGainLabel = new Label("Edge Gain");
        startLabel = new Label("start");
        endLabel = new Label("end");


        fromTextField = new TextField();
        fromTextField.setEditable(true);
        fromTextField.setPrefWidth(50);


        toTextField = new TextField();
        fromTextField.setEditable(true);
        fromTextField.setPrefWidth(50);

        edgeGainTextField = new TextField();
        fromTextField.setEditable(true);
        fromTextField.setPrefWidth(50);

        startTextField = new TextField();
        fromTextField.setEditable(true);
        fromTextField.setPrefWidth(50);

        endTextField = new TextField();
        fromTextField.setEditable(true);
        fromTextField.setPrefWidth(50);





        grid = new GridPane();
        grid.setLayoutX(800);
        grid.setLayoutY(0);
        grid.getChildren().addAll(addNodeBtn,addEdgeBtn,GainBtn,endLabel,fromLabel,toLabel,startLabel,edgeGainLabel
                                    ,endTextField,startTextField,fromTextField,toTextField,edgeGainTextField,resetBtn);


        grid.setConstraints(addNodeBtn,0,0);
        grid.setConstraints(fromLabel,0,1);
        grid.setConstraints(fromTextField,1,1);
        grid.setConstraints(toLabel,0,2);
        grid.setConstraints(toTextField,1,2);
        grid.setConstraints(edgeGainLabel,0,3);
        grid.setConstraints(edgeGainTextField,1,3);
        grid.setConstraints(addEdgeBtn,0,4);
        grid.setConstraints(startLabel,0,5);
        grid.setConstraints(startTextField,1,5);
        grid.setConstraints(endLabel,0,6);
        grid.setConstraints(endTextField,1,6);
        grid.setConstraints(GainBtn,0,7);
        grid.setConstraints(resetBtn,0,8);

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(10);
        grid.setHgap(10);
        root.getChildren().add(grid);



        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        canvas.setPrefHeight(600);
        canvas.setPrefWidth(800);

        /*canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isAddNode){
                    addNode(event.getX(),event.getY());
                    isAddNode = false;
                }else if(isAddEdge){
                    addEdge(event.getX(),event.getY());
                    isAddEdge = false;
                }
            }
        });*/

        canvas.setBackground(new Background(new BackgroundFill(new Color(
                                        1,1,0.9,1), CornerRadii.EMPTY, Insets.EMPTY)));

        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutY(0);
        scrollPane.setLayoutX(0);
        scrollPane.setPrefHeight(600);
        scrollPane.setPrefWidth(800);

        scrollPane.setContent(canvas);
        root.getChildren().add(scrollPane);












    }


    @Override
    public void start(Stage primaryStage) {
        initializeButtons();




        primaryStage.setTitle("SFG");


        primaryStage.setScene(new Scene(root, 1100, 600,Color.LIGHTBLUE));
        primaryStage.show();

    }


}
