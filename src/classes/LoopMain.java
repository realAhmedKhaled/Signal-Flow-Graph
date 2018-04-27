package classes;

import java.util.LinkedList;

import interfaces.IGraph;

public class LoopMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
		IGraph x=new Graph(9);
		int []start= {0,1,1,2,2,3,3,4,5,5,6,6,7,7,8};
		int []end=   {1,2,3,4,3,5,1,6,7,5,8,7,4,1,2};
		int []gain=  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		x.BuildGraph(start, end, gain);
		LinkedList<Loop> result=x.getLoops();
		for(Loop loop:result)
		{
			for(int i=0;i<loop.getLoop().length;i++)
				System.out.print(loop.getLoop()[i]+" ");
			System.out.println();
			
		}
		result=x.getNontouchedLoops();
		for(int i=0;i<result.size();i++)
		{
			System.out.println("+++++++++++++++++++++++");
			LinkedList<Integer[]> loop=result.get(i).getLoops();
			for (int j=0;j<loop.size();j++)
			{
				Integer[] arr=loop.get(j);
				for(int k=0;k<arr.length;k++)
					System.out.print(arr[k]+" ");
				System.out.println();
			}
			//System.out.println("      "+Integer.toBinaryString(result.get(i).getBitnodes())+"   "+result.get(i).getGain());
		}
	}

}
