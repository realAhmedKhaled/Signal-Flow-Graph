package interfaces;


public interface IGraph {
	/**
	 * @return the delatas and the overall transfer function
	 */
	public String getCalculations();
	
	/**
	 * @return the loops with their gain
	 */
	public String getLoops();
	
	/**
	 * @return the non-touched loops indexes and their gain
	 */
	public String getNonTouchedLoops();
	
	/**
	 * @return the forward pathes and their gain for each path
	 */
	public String getForwardPathes();


	
	
	
}
