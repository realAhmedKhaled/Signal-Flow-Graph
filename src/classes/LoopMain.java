package classes;

import java.util.ArrayList;

import interfaces.IGraph;

public class LoopMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
		IGraph x=new Graph(8);
		int []start= {0,1,2,3,3,4,5,5,5,6,6,7,7};
		int []end=   {1,2,3,4,6,5,6,7,4,7,2,5,1};
		int []gain=  {1,1,1,1,1,1,1,1,1,1,1,1,1};
		x.BuildGraph(start, end, gain);
		ArrayList<Integer[]> result=x.getLoops();
		for(int i=0;i<result.size();i++)
		{
			for (int j=0;j<result.get(i).length;j++)
			{
				if((result.get(i)[j]+1)==0)
					break;
				System.out.print((result.get(i)[j]+1)+" ");
			}
			System.out.println();
		}
	}

}
