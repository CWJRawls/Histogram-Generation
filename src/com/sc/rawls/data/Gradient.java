package com.sc.rawls.data;

public class Gradient {
	
	public static final int GRAD_FEATURE_NONE = 0;
	public static final int GRAD_FEATURE_BLACK = 1;
	public static final int GRAD_FEATURE_WHITE = 2;
	
	private int[] base;
	private int[] gradient;
	private int gradient_feature;
	private boolean init_check = false;
	
	public Gradient(int[] b, int feat)
	{
		gradient_feature = feat;
		
		base = new int[b.length];
		
		for(int i = 0; i < b.length; i++)
		{
			base[i] = b[i];
		}
	}
	
	public int computeGradient()
	{
		
		int[] steps = new int[base.length];
		
		return 1;
	}
	
	private void removeExtraIndeces()
	{
		
	}
	
	public int canGetData()
	{
		if(!init_check)
			return 0;
		else
			return 1;
	}

}
