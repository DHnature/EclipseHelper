package analyzer.query;

/**Parent interface of all<br>
 * NOTICE: UPDATE TO LAMBDA in version 1.8
 * @since 1.7
 * <p>
 * The comparator pretty much defines how the select method in {@link Selector}'s select method choose
 * the right elements
 * <p>
 * It comes in 4 versions. 
 * 
 * */
interface Comparator{
	/**Compare method. How it is implemented is up to the type*/
	public boolean compareTo(double one, double two);

}


class LessThanComparator implements Comparator {
	private boolean trueIfEquals;
	
	public LessThanComparator(boolean trueIfEquals) {
		this.trueIfEquals=trueIfEquals;
		
	}
	
	@Override
	public boolean compareTo(double one, double two) {
		return one<two || (one==two && trueIfEquals);

	}

}

class EqualToComparator implements Comparator{

	@Override
	public boolean compareTo(double one, double two) {
		return one==two;

	}


}

class NotEqualToComparator implements Comparator{

	@Override
	public boolean compareTo(double one, double two) {
		return one!=two;

	}


}

class GreaterThanComparator implements Comparator{
	private boolean trueIfEquals;
	
	public GreaterThanComparator(boolean trueIfEquals) {
		this.trueIfEquals=trueIfEquals;
		
	}
	
	@Override
	public boolean compareTo(double one, double two) {
		return one>two || (one==two && this.trueIfEquals);
	}

}