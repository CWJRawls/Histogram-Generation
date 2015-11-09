package com.sc.rawls.data;

public class Gradient {
	
	public static final int GRAD_FEATURE_NONE = 0;
	public static final int GRAD_FEATURE_BLACK = 1;
	public static final int GRAD_FEATURE_WHITE = 2;
	
	private int[] base;
	private int[] gradient;
	private int gradient_feature;
	private Histogram hist;
	private boolean init_check = false;
	
	/* CONSTRUCTOR
	 * Takes the integer array from the sort
	 * an integer describing any features that need to be added to the gradient
	 * A histogram object to reference for bin information. (Should be the same object that provided the array)
	 */
	public Gradient(int[] b, int feat, Histogram h)
	{
		//set the histogram
		hist = h;
		
		//set the feature value
		gradient_feature = feat;
		
		//initialize the base array
		base = new int[b.length];
		
		for(int i = 0; i < b.length; i++)
		{
			base[i] = b[i];
		}
		
		//filter out any extra values in the base array and reset it to the correct size
		removeExtraIndeces();
	}
	
	/*COMPUTE GRADIENT Function
	 * takes no arguments and returns an integer when done.
	 * Will use the gradient feature to determine how the gradient between adjacent indeces is calculated.
	 * If the computation is successful, the gradient array will be filled at the function's conclusion.
	 */
	public int computeGradient()
	{
		//check to make sure there is data to compute a gradient on
		if(base.length == 0)
			return 0;
		
		int steps = (gradient_feature >> 8) & 0xFF;
		
		return 1;
	}
	
	/*REMOVE EXTRA INDECES Function
	 * takes no arguments and is a void return when done.
	 * Starts at the end of the base array and works towards base[0] to find all values that do not have a bin > 0
	 * When the loop is complete, and if there were any values of bin == 0, then the base array is reinitialized and refilled with only valid values.
	 */
	private void removeExtraIndeces()
	{
		int end = base.length;
		
		//start at the end of the matrix and find all open black pixels
		for(int i = base.length - 1; hist.getBinAt(i) == 0 && i >= 0; i++)
		{
			end = i;
		}
		
		//only reinitialize matrices in the case that empty pixels need to be removed
		if(end != base.length)
		{
			//create a temporary array to hold values
			int[] temp = new int[end + 1];
			
			for(int i = 0; i < temp.length; i++)
			{
				temp[i] = base[i];
			}
			
			//reallocate base to the new number of indexes and refill the array
			base = new int[temp.length];
			
			for(int i = 0; i < base.length; i++)
			{
				base[i] = temp[i];
			}
		}
	}
	
	/* CAN GET DATA Function
	 * takes no arguments and returns a boolean value
	 * returns the value of init_check flag
	 * Should be called before asking for gradient data
	 */
	public boolean canGetData()
	{
		return init_check;
	}

}
