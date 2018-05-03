package classes;

import java.util.ArrayList;

import interfaces.IGraph;

public class LoopMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	int []start= {0,1,2,3,4,5,5,6,7,7,8};
		int []end=   {1,2,3,4,5,2,6,7,4,8,6};
		int []gain=  {1,1,1,1,1,1,1,1,1,1,1};
		*/
		System.out.println("");
		
		int []start= {0,1,1,2,3,3,4,4,5,5};
		int []end=   {1,2,5,3,4,2,1,3,5,4};
		int []gain=  {1,5,10,10,2,-1,-1,-2,-1,2};
		Graph x=new Graph(6,start,end,gain,0,4);
		
		System.out.println(x.getForwardPathes());
		System.out.println(x.getLoops());
		System.out.println(x.getNonTouchedLoops());
		System.out.println(x.getCalculations());
		
		
		
		
		
		
/*
		x.BuildGraph(start, end, gain);
		ArrayList<Path> result=x.getLoops();
		for(Path loop:result)
		{
			for(int i=0;i<loop.getLoop().length;i++)
				System.out.print(loop.getLoop()[i]+" ");
			System.out.println("gain="+loop.getGain());
			
		}
		result=x.getNontouchedLoops();
		System.out.println("non");
		for(int i=0;i<result.size();i++)
		{
			System.out.println("+++++++++++++++++++++++");
			ArrayList<Integer[]> loop=result.get(i).getLoops();
			for (int j=0;j<loop.size();j++)
			{
				Integer[] arr=loop.get(j);
				for(int k=0;k<arr.length;k++)
					System.out.print(arr[k]+" ");
				System.out.println();
			}
			//System.out.println("      "+Integer.toBinaryString(result.get(i).getBitnodes())+"   "+result.get(i).getGain());
		}
*/	}

}
