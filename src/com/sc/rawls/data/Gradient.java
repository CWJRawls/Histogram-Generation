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
		hist = h;
		
		gradient_feature = feat;
		
		base = new int[b.length];
		
		for(int i = 0; i < b.length; i++)
		{
			base[i] = b[i];
		}
		
		removeExtraIndeces();
	}
	
	public int computeGradient()
	{
		
		int[] steps = new int[base.length];
		
		return 1;
	}
	
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
	
	public int canGetData()
	{
		if(!init_check)
			return 0;
		else
			return 1;
	}

}
