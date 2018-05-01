package classes;

import java.util.ArrayList;

import interfaces.IGraph;

public class LoopMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IGraph x=new Graph(9);
	/*	int []start= {0,1,2,3,4,5,5,6,7,7,8};
		int []end=   {1,2,3,4,5,2,6,7,4,8,6};
		int []gain=  {1,1,1,1,1,1,1,1,1,1,1};
		*/
		
		int []start= {0,1,1,2,3,4,4,5,6,6,6,7,7,8,8};
		int []end=   {1,1,2,3,4,5,7,6,7,8,5,8,3,6,2};
		int []gain=  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
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
	}

}
