package interfaces;

public interface INode {

	/**
	 * adds new child to the node by the node index and gain between them
	 * 
	 * @param childNode
	 *            the index of the child node
	 * @param childGain
	 *            the gain of the child node
	 */
	void AddChild(int childNode, int childGain);

	/**
	 * checks if a specific node is a child of this node or not
	 * 
	 * @param node
	 *            the child node index
	 * @return true if node is child of this node and false otherwise
	 */

	boolean isChild(int node);

	/**
	 * set the node to be visited
	 */

	void SetVisited();

	/**
	 * set the node to be unvisited and set the children counter to the first
	 * node
	 */

	void resetVisited();

	/**
	 * check if this node is visited or not
	 * 
	 * @return true if visited and false otherwise
	 */

	boolean isVisited();

	/**
	 * iterates through the child nodes and returns the next unvisited child in
	 * this node
	 * 
	 * @return the index of the next unvisited child in this node and -1 if
	 *         there is no more unvisited childs
	 */

	int getNextunvisitedChild();
	/**
	 * get the gain of between this node and the nodex node
	 * @param nextNode
	 * @return the gain of the path between them
	 */
	
	int getGain(int nextNode);
		

}
