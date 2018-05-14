package classes;

import java.util.ArrayList;

import interfaces.INode;

public class Path {
	private ArrayList<Integer[]> paths;
	private ArrayList<Integer> bitnodes;
	private ArrayList<Integer> loopsIndex;
	private double gainInt;
	private int bitLoops;
	private ArrayList<Double> gain;	
	
	public Path (Integer[] nodes, int bitnodes,INode [] allNodes,int loopIndex)
	{
		this.paths=new ArrayList<>();
		this.bitnodes=new ArrayList<>();
		this.paths.add(nodes);
		this.bitnodes.add(bitnodes);
		this.loopsIndex=new ArrayList<>();
		this.gain=new ArrayList<>();
		this.gainInt=0;
		bitLoops|=((1<<loopIndex));
		double gaintemp=1;
		this.loopsIndex.add(loopIndex);
		for(int i=0;i<nodes.length-1;i++)
		{
			gaintemp*=allNodes[nodes[i]].getGain(nodes[i+1]);
			
		}
		gain.add(gaintemp);
		gainInt=gaintemp;
	}
	public Path (Path l1,Path l2)
	{
		this.paths=new ArrayList<>();
		this.bitnodes=new ArrayList<>();
		this.loopsIndex=new ArrayList<>();
		this.gain=new ArrayList<>();
		gainInt=1;
		bitLoops=l1.getBitLoops()|l2.getBitLoops();
		for(int i=0;i<l1.getBitnodes().size();i++)
		{
			bitnodes.add(l1.getBitnodes().get(i));
			paths.add(l1.getLoops().get(i));
			gain.add(l1.getGain().get(i));
			gainInt*=l1.getGain().get(i);
			loopsIndex.add(l1.getLoopIndex().get(i));
			
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
				paths.add(l2.getLoops().get(i));
				gain.add(l2.getGain().get(i));
				gainInt*=l2.getGain().get(i);
				loopsIndex.add(l2.getLoopIndex().get(i));

			}
		}
		
	}
	
	public ArrayList<Double> getGain()
	{
		return gain;
	}
	public ArrayList<Integer> getLoopIndex()
	{
		return loopsIndex;
	}

	public ArrayList<Integer> getBitnodes()
	{
		return bitnodes;
	}
	public int getBitLoops()
	{
		return bitLoops;
	}
	public Integer[]  getLoop()
	{
		return paths.get(0);
	}
	public ArrayList<Integer[]> getLoops()
	{
		return paths;
	}
	public Double getGainInt()
	{
		return gainInt;
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
