package classes;

import java.util.ArrayList;
import java.util.Stack;

import interfaces.IGraph;
import interfaces.INode;

public class Graph  implements IGraph   {
	private int n;
	private INode[] nodes;
	private ArrayList<Path> loops;
	private int delta=1;
	private double overallGain;
	private ArrayList<Path> forwardpathes;
	private ArrayList<Path> nonTouchedLoops;

	public Graph(int numberOfnodes, int[] pathStart, int[] pathEnd, int[] pathGain, int startNode, int endNode) {
		this.n = numberOfnodes;
		nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(numberOfnodes);
		}
		BuildGraph(pathStart, pathEnd, pathGain);
		generateForwardPasses(startNode, endNode);
		generateLoops();
		generateNontouchedLoops();
	}

	public String getCalculations()
	{
		StringBuilder result= new StringBuilder("delta = ");
		result.append(delta+" \n");
		for(int i=0;i<forwardpathes.size();i++)
		{
			int temp=getDelta(forwardpathes.get(i));
			overallGain+=temp*forwardpathes.get(i).getGainInt();
			result.append("delta"+(i+1)+"= "+temp+"\n");
		}
		overallGain/=delta;
		result.append("the Overall transfer Function ="+overallGain);
		
		return result.toString();
	}
	private int getDelta(Path path) {
		int delta=1;
		for(Path loop:loops)
		{
			if(loop.isAdditive(path.getLoops(), path.getBitnodes()))
			{
				delta-=loop.getGainInt();
			}
			
		}
		for(Path loop:nonTouchedLoops)
		{
			if(loop.isAdditive(path.getLoops(), path.getBitnodes()))
			{
				if(loop.getBitnodes().size()%2==0)
				{
					delta+=loop.getGainInt();
				}
				else
					delta-=loop.getGainInt();
			}
		}
		return delta;
	}

	public String getLoops() {
		StringBuilder result = new StringBuilder("Loops:\n");
		int i = 0;

		for (Path loop : loops) {
			i++;
			result.append("P" + i + ": (");
			for (int j = 0; j < loop.getLoop().length; j++) {
				if (j == loop.getLoop().length - 1)
					result.append(loop.getLoop()[j] + ") Gain= "+loop.getGainInt()+"\n");
				else
					result.append(loop.getLoop()[j] + ", ");

			}
			result.append("\n");
		}

		return result.toString();
	}

	public String getNonTouchedLoops()
	{
		StringBuilder result = new StringBuilder("Non-Touched Loops:\n");
		ArrayList<Path> now = new ArrayList<>();
		now = getnontouched(2);
		int c=2;
		while (now.size() != 0) 
		{
			result.append(""+c+" non-Touched Loops: \n\t");
			for (int i = 0;i<now.size(); i++)
			{
				result.append("(");
				for (int j = 0; j < now.get(i).getLoopIndex().size(); j++) 
				{
					if (j == now.get(i).getLoopIndex().size() - 1)
						result.append("P"+(now.get(i).getLoopIndex().get(j)+1) + ") Gain="+now.get(i).getGainInt());
					else
						result.append("P"+(now.get(i).getLoopIndex().get(j)+1) + ", ");

				}
				result.append("\n\t");
			}
			c++;
			now = getnontouched(c);
			result.append("\n");
		}

		return result.toString();
	}

	private ArrayList<Path> getnontouched(int i) {
		// TODO Auto-generated method stub
		ArrayList<Path> result = new ArrayList<>();
		for (Path loop : nonTouchedLoops) {
			if (loop.getBitnodes().size() == i)
				result.add(loop);
		}
		return result;
	}

	public String getForwardPathes() {

		StringBuilder result = new StringBuilder("Forward Pathes:\n");
		int i = 0;
		for (Path path : forwardpathes) {
			i++;
			result.append("L" + i + ": (");
			for (int j = 0; j < path.getLoop().length; j++) {
				if (j == path.getLoop().length - 1)
					result.append(path.getLoop()[j] + ") Gain="+path.getGainInt());
				else
					result.append(path.getLoop()[j] + ", ");

			}
			result.append("\n");
		}

		return result.toString();
	}

	private void generateLoops() {
		ArrayList<Integer> loopsinbinary = new ArrayList<>();
		int x = 0;// current node
		int child = nodes[x].getNextunvisitedChild();
		loops = new ArrayList<>();
		nonTouchedLoops = new ArrayList<>();

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
					loops.add(new Path(loop, binary, nodes, loops.size()));
					nonTouchedLoops.add(new Path(loop, binary, nodes, nonTouchedLoops.size()));
					loopsinbinary.add(binary);
					delta-=loops.get(loops.size()-1).getGainInt();
				}
				child = nodes[x].getNextunvisitedChild();
				continue;

			}
			nodes[child].SetVisited();
			stack.push(child);
			x = child;
			child = nodes[x].getNextunvisitedChild();

		}

	}

	private void generateForwardPasses(int start, int end) {
		ArrayList<Integer> loopsinbinary = new ArrayList<>();
		int x = start;// current node
		int child = nodes[x].getNextunvisitedChild();
		forwardpathes = new ArrayList<>();
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
				child = nodes[x].getNextunvisitedChild();
				continue;

			}

			if (child == end) {
				Stack<Integer> temp = new Stack<>();

				int binary = 0, stacki = stack.size() - 1;
				temp.push(child);
				binary = binary + (1 << child);
				while (stack.get(stacki) != start) {
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
					forwardpathes.add(new Path(loop, binary, nodes, forwardpathes.size()));
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

	}

	private void BuildGraph(int[] start, int[] end, int[] gain) {
		// TODO Auto-generated method stub
		for (int i = 0; i < start.length; i++) {
			nodes[start[i]].AddChild(end[i], gain[i]);
		}

	}

	private void generateNontouchedLoops() {
		// TODO Auto-generated method stub
		for (int i = 0; i < nonTouchedLoops.size(); i++) {
			Path temp = nonTouchedLoops.get(i);
			for (int j = i + 1; j < nonTouchedLoops.size(); j++) {
				if (nonTouchedLoops.get(j).isAdditive(temp.getLoops(), temp.getBitnodes())) {
					Path newLoop = new Path(nonTouchedLoops.get(j), temp);
					nonTouchedLoops.add(i + 1, newLoop);
					if(newLoop.getBitnodes().size()%2==0)
						delta+=newLoop.getGainInt();
					else
						delta-=newLoop.getGainInt();
					j++;
				}

			}
			if (temp.getBitnodes().size() == 1) {
				nonTouchedLoops.remove(temp);
				i--;

			}
		}

	}

}
