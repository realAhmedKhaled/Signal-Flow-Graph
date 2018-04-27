package classes;

import interfaces.INode;

public class Node implements INode {

	boolean visited;
	boolean[] childNodes;
	int[] gains;
	int childIterator;
	int n;

	public Node(int numberOfnodes) {
		n = numberOfnodes;
		childIterator = 0;
		gains=new int[n];
		childNodes= new boolean[n];
	}

	@Override
	public void AddChild(int childNode, int childGain) {
		childNodes[childNode] = true;
		gains[childNode] = childGain;

	}

	@Override
	public boolean isChild(int node) {
		// TODO Auto-generated method stub
		return childNodes[node];
	}

	@Override
	public void SetVisited() {
		// TODO Auto-generated method stub
		visited = true;

	}

	@Override
	public void resetVisited() {
		// TODO Auto-generated method stub
		visited = false;
		childIterator = 0;

	}

	@Override
	public boolean isVisited() {
		// TODO Auto-generated method stub
		return visited;
	}

	@Override
	public int getNextunvisitedChild() {
		// TODO Auto-generated method stub
		while (childIterator< n) {
			if (childNodes[childIterator])
			{
				int temp=childIterator;
				childIterator++;
				return temp;
			}
			childIterator++;
		}

		return -1;
	}

	@Override
	public int getGain(int nextNode) {
		// TODO Auto-generated method stub
		return gains[nextNode];
	}

}
