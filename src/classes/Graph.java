package classes;

import interfaces.IGraph;
import interfaces.INode;

public class Graph implements IGraph {
	private int n;
	INode[] nodes;
	
	public Graph(int numberOfnodes)
	{
		this.n=numberOfnodes;
		nodes=new INode[n];
	}

	@Override
	public int[][] getLoops() {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public boolean[][] getForwardPasses(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void BuildGraph(int[] start, int[] end, int[] gain) {
		// TODO Auto-generated method stub

	}

}
