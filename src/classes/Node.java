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
		// TODO set the size of connectedNodes and gait to n;
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
		for (int i = childIterator + 1; i < n; i++) {
			if (childNodes[i])
				return i;
		}

		return -1;
	}

}
