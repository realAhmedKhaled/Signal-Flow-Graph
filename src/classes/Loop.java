package classes;

import java.util.ArrayList;
import java.util.LinkedList;

import interfaces.INode;

public class Loop {
	private LinkedList<Integer[]> loops;
	private LinkedList<Integer> bitnodes;
	private int gain;	
	
	public Loop (Integer[] nodes, int bitnodes,INode [] allNodes)
	{
		this.loops=new LinkedList<>();
		this.bitnodes=new LinkedList<>();
		this.loops.add(nodes);
		this.bitnodes.add(bitnodes);
		gain=0;
		for(int i=0;i<nodes.length-1;i++)
		{
			gain+=allNodes[i].getGain(nodes[i+1]);
			//System.out.println(nodes[i]+" "+nodes[i+1]+" "+gain);
		}
	}
	public int getGain()
	{
		return gain;
	}
	public LinkedList<Integer> getBitnodes()
	{
		return bitnodes;
	}
	public Integer[]  getLoop()
	{
		return loops.get(0);
	}
	public LinkedList<Integer[]> getLoops()
	{
		return loops;
	}
	
	
	public boolean addLoop(LinkedList<Integer[]> loops, LinkedList<Integer> bitnode)
	{
		ArrayList<Integer> repeatednodes=new ArrayList<>();
		for(int i=0;i<bitnode.size();i++)
		{
			for(int j=0;j<bitnodes.size();j++)
			{
				if((bitnode.get(i)&bitnodes.get(j))==(bitnode.get(i)|bitnodes.get(j)))
				{
					repeatednodes.add(j);
					continue;
				}
				if((bitnode.get(i)&bitnodes.get(j))!=0)
					return false;
			}
		}
		for(int i=0;i<bitnode.size();i++)
		{
			if(repeatednodes.contains(i))
					continue;
			bitnodes.add(bitnode.get(i));
		//	System.out.println("before:"+this.loops.size());
			this.loops.add(loops.get(i));
			//System.out.println(this.loops.size());
			//System.out.println(loops.size());
			
		}		
		return true;
		
	}

}
