package interfaces;

import java.util.ArrayList;

public interface IGraph {

	/**
	 * gets the loops occurred in the graph
	 * 
	 * @return 2D array. each raw represents a sequence of indexes that form a
	 *         loop.
	 */
	ArrayList<Integer[]> getLoops();

	/**
	 * get all the forward passes between two points in the graph
	 * 
	 * @return 2D array. each raw represents a sequence of indexes that form a
	 *         forward pass.
	 */

	boolean[][] getForwardPasses(int start, int end);

	/**
	 * builds the whole graph at the beginning of the process using the passes
	 * and their gain as an input
	 * 
	 * @param start
	 *            array of all the start nodes of each pass
	 * @param end
	 *            the corresponding end nodes of each pass
	 * @param gain
	 *            the corresponding gain of each pass
	 */

	void BuildGraph(int[] start, int[] end, int[] gain);

}
