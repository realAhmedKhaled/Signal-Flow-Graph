package classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import interfaces.IGraph;
import interfaces.INode;

public class Graph implements IGraph {
	private int n;
	private INode[] nodes;
	private LinkedList<Loop> loops;

	public Graph(int numberOfnodes) {
		this.n = numberOfnodes;
		nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(numberOfnodes);
		}
	}

	@Override
	public LinkedList<Loop> getLoops() {
		ArrayList<Integer> loopsinbinary = new ArrayList<>();
		int x = 0;// current node
		int child = nodes[x].getNextunvisitedChild();
		loops = new LinkedList<>();
		
		Stack<Integer> stack = new Stack<>();

		stack.push(x);
		nodes[x].SetVisited();
		while (!stack.isEmpty()) {
			if (child == -1) {
				int temp = stack.pop();
				nodes[temp].resetVisited();
				if (stack.isEmpty())
					break;
				x = stack.peek();
				child = nodes[x].getNextunvisitedChild();
				continue;

			}
			if (nodes[child].isVisited()) {
				Stack<Integer> temp = new Stack<>();

				int binary = 0, stacki = stack.size() - 1;
				temp.push(child);
				binary = binary + (1 << child);
				boolean flag = false;
				while (stack.get(stacki) != child) {
					binary = binary + (1 << stack.get(stacki));
					temp.push(stack.get(stacki));
					stacki--;

					// System.out.print(loop[i]+" ");
				}
				temp.push(stack.get(stacki));
				// System.out.println();
				int i;
				for (i = 0; i < loopsinbinary.size(); i++) {
					if ((loopsinbinary.get(i) & binary) == (loopsinbinary.get(i) | binary))
						break;
				}
				Integer[] loop = new Integer[temp.size()];
				// System.out.println(temp.size());
				for (int j = 0; j < temp.size(); j++) {
					loop[j] = temp.get(temp.size() - j - 1);
					// System.out.println(loop[j]+" j");
				}
				if (i == loopsinbinary.size()) {
					loops.add(new Loop(loop, binary, nodes));
					loopsinbinary.add(binary);
				}
				child = nodes[x].getNextunvisitedChild();
				continue;

			}
			nodes[child].SetVisited();
			stack.push(child);
			x = child;
			child = nodes[x].getNextunvisitedChild();

		}

		return loops;
	}

	@Override
	public boolean[][] getForwardPasses(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void BuildGraph(int[] start, int[] end, int[] gain) {
		// TODO Auto-generated method stub
		for (int i = 0; i < start.length; i++) {
			nodes[start[i]].AddChild(end[i], gain[i]);
		}

	}

	@Override
	public LinkedList<Loop> getNontouchedLoops() {
		// TODO Auto-generated method stub
		for(int i=0;i<loops.size();i++)
		{
			Loop temp= loops.get(i);
			boolean flag=false;
			for(int j=i+1;j<loops.size();j++)
			{
				if(flag==true)
					loops.get(j).addLoop(temp.getLoops(),temp.getBitnodes());
				else
					flag=loops.get(j).addLoop(temp.getLoops(),temp.getBitnodes());
				
			}
			if(flag==true)
			{
				loops.remove(temp);
				i--;
				
			}
		}
			
		return loops;
	}
	
	

}
