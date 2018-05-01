package classes;

import java.util.ArrayList;

import interfaces.INode;

public class Path {
	private ArrayList<Integer[]> loops;
	private ArrayList<Integer> bitnodes;
	private int gain;	
	
	public Path (Integer[] nodes, int bitnodes,INode [] allNodes)
	{
		this.loops=new ArrayList<>();
		this.bitnodes=new ArrayList<>();
		this.loops.add(nodes);
		this.bitnodes.add(bitnodes);
		gain=0;
		for(int i=0;i<nodes.length-1;i++)
		{
			gain+=allNodes[nodes[i]].getGain(nodes[i+1]);
			//System.out.println(nodes[i]+" "+nodes[i+1]+" "+gain);
		}
	}
	public Path (Path l1,Path l2)
	{
		this.loops=new ArrayList<>();
		this.bitnodes=new ArrayList<>();
		for(int i=0;i<l1.getBitnodes().size();i++)
		{
			bitnodes.add(l1.getBitnodes().get(i));
			loops.add(l1.getLoops().get(i));
		}
		for(int i=0;i<l2.getBitnodes().size();i++)
		{
			boolean repeated=false;
			for(int j=0;j<l1.getBitnodes().size();j++)
			if((l2.getBitnodes().get(i)&l1.getBitnodes().get(j))==(l2.getBitnodes().get(i)|l1.getBitnodes().get(j)))
			{
				repeated=true;
				break;
			}
			if(!repeated)
			{
				bitnodes.add(l2.getBitnodes().get(i));
				loops.add(l2.getLoops().get(i));
			}
		}
		
	}
	public int getGain()
	{
		return gain;
	}
	public ArrayList<Integer> getBitnodes()
	{
		return bitnodes;
	}
	public Integer[]  getLoop()
	{
		return loops.get(0);
	}
	public ArrayList<Integer[]> getLoops()
	{
		return loops;
	}
	
	
	public boolean isAdditive(ArrayList<Integer[]> loops, ArrayList<Integer> bitnode)
	{
		//ArrayList<Integer> repeatednodes=new ArrayList<>();
		for(int i=0;i<bitnode.size();i++)
		{
			for(int j=0;j<bitnodes.size();j++)
			{
				/*if((bitnode.get(i)&bitnodes.get(j))==(bitnode.get(i)|bitnodes.get(j)))
				{
					repeatednodes.add(j);
					continue;
				}*/
				if((bitnode.get(i)&bitnodes.get(j))!=0)
					return false;
			}
		}
		return true;

		/*for(int i=0;i<bitnode.size();i++)
		{
			if(repeatednodes.contains(i))
					continue;
			bitnodes.add(bitnode.get(i));
		//	System.out.println("before:"+this.loops.size());
			this.loops.add(loops.get(i));
			//System.out.println(this.loops.size());
			//System.out.println(loops.size());
			
		}		*/
		
	}

}
