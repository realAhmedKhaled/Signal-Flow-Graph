package classes;

import java.util.ArrayList;
import java.util.Stack;

import interfaces.IGraph;
import interfaces.INode;

public class Graph implements IGraph {
	private int n;
	private INode[] nodes;
	
	public Graph(int numberOfnodes)
	{
		this.n=numberOfnodes;
		nodes=new Node[n];
		for(int i=0;i<n;i++)
		{
			nodes[i]=new Node(numberOfnodes);
		}
	}

	@Override
	public ArrayList<Integer[]> getLoops() {
		
		int x=0;//current node
		int child=nodes[x].getNextunvisitedChild();
		ArrayList<Integer[]> result= new ArrayList<>();
		Stack<Integer> stack=new Stack<>();

		
		stack.push(x);
		nodes[x].SetVisited();
		while(!stack.isEmpty())
		{
			if(child==-1)
			{
				int temp=stack.pop();
				nodes[temp].resetVisited();
				if(stack.isEmpty())
					break;
				x=stack.peek();
				child=nodes[x].getNextunvisitedChild();
				continue;
				
			}
			if(nodes[child].isVisited())
			{
				Integer []loop=new Integer[n+1];
				
				loop[0]=child;
				boolean flag=false;
				for(int i=1,stacki=stack.size()-1;i<n+1;i++,stacki--)
				{
					if(flag)
						loop[i]=-1;
					else
					{
					loop[i]=stack.get(stacki);
					if(loop[i]==child)
						flag=true;
					}
					//System.out.print(loop[i]+" ");
				}
				//System.out.println();
				result.add(loop);
				child=nodes[x].getNextunvisitedChild();
				continue;
				
			}
			nodes[child].SetVisited();
			stack.push(child);
			x=child;
			child=nodes[x].getNextunvisitedChild();
					
		}
		
		
		return result;
	}

	@Override
	public boolean[][] getForwardPasses(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void BuildGraph(int[] start, int[] end, int[] gain) {
		// TODO Auto-generated method stub
		for(int i=0;i<start.length;i++)
		{
			nodes[start[i]].AddChild(end[i], gain[i]);
		}

	}

}
