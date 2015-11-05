package com.sc.rawls.data;

public class Histogram {

	private int[][][] bins = new int[256][256][256];
	private int[][][] colors = new int[256][256][256];
	private int[] sig_bins; //avoid allocating memory if we don't need it
	private int[] sig_colors;
	
	private int sortFlag = 0;
	
	//used to avoid functions that are expecting a certain array size
	private int colorResFlag;
	
	public static final int COLOR_RES_256 = 0;
	public static final int COLOR_RES_64 = 1;
	public static final int COLOR_RES_32 = 2;
	public static final int COLOR_RES_16 = 3;
	
	
	public Histogram(int crf)
	{
		
		colorResFlag = crf;
		//init the matrix with 0s
		for(int i = 0; i < 256; i++)
		{
			for(int j = 0; j < 256; j++)
			{
				for(int k = 0; k < 256; k++)
				{
					bins[i][j][k] = 0;
					int color = 0;
					color += i;
					color += j << 8;
					color += k << 16;
					colors[i][j][k] = color;
				}
			}
		}
		
		System.out.println("Finished Constructor");
	}
	
	/*------------------------------*/
	/*      UTILITY FUNCTIONS       */
	/*------------------------------*/
	
	//only take one int since all matrices should be cubic
	private void reInitMatrices(int dim)
	{
		colors = new int[dim][dim][dim];
		bins = new int[dim][dim][dim];
		
		for(int i = 0; i < dim; i++)
		{
			for(int j = 0; j < dim; j++)
			{
				for(int k = 0; k < dim; k++)
				{
					colors[i][j][k] = 0;
					bins[i][j][k] = 0;
				}
			}
		}
	}
	
	//placed here since it is not expecting an array of a predetermined size
	private void sortSigArrays()
	{
		for(int i = 1; i < sig_bins.length - 1; i++)
		{
			int temp = sig_bins[i];
			int temp_c = sig_colors[i];
			
			for(int j = i - 1; j >= 0 && temp > sig_bins[j]; j--)
			{
				sig_bins[j + 1] = sig_bins[j];
				sig_colors[j + 1] = sig_colors[j];
				sig_bins[j] = temp;
				sig_colors[j] = temp_c;
			}
		}
	}
	
	
	/* Functions Below this are grouped by the number of values per color channel. */
	
	/*-------------------------------*/
	/* STANDARD 256 COLOR RESOLUTION */
	/*-------------------------------*/
	public int addToBin(int data)
	{
		//Avoid misplaced calls
		if(colorResFlag != 0)
			return 0;
		
		int r_val = data & 0xFF;
		int g_val = (data >> 8) & 0xFF;
		int b_val = (data >> 16) & 0xFF;
		
		bins[r_val][g_val][b_val]++;
		
		return 1;
	}
	
	public int blueMergeSortBins()
	{
		if(colorResFlag != 0)
			return 0;
		
		sortFlag = 1;
		//first sort all blue rows in z dim
		for(int i = 0; i < 65536; i++)
		{
			int x = i % 256;
			int y = i / 256;
			
			for(int z = 1; z < 256; z++)
			{
				int temp = bins[x][y][z];
				int temp_c = colors[x][y][z];
				
				for(int j = z - 1; j >= 0 && temp > bins[x][y][j]; j--)
				{
					bins[x][y][j + 1] = bins[x][y][j];
					colors[x][y][j + 1] = colors[x][y][j];
					
					bins[x][y][j] = temp;
					colors[x][y][j] = temp_c;
				}
			}
		}
		
		//sort remaining dimensions
		for(int i = 1; i < 65536; i++)
		{
			int temp = bins[i % 256][i / 256][0];
			int temp_c = colors[i % 256][i / 256][0];
			for(int j = i - 1; j >= 0 && temp > bins[i % 256][i / 256][0]; j--)
			{
				bins[(j + 1) % 256][(j + 1) / 256][0] = bins[j % 256][j / 256][0];
				colors[(j + 1) % 256][(j + 1) / 256][0] = colors[j % 256][j / 256][0];
				
				bins[j % 256][j / 256][0] = temp;
				colors[j % 256][j / 256][0] = temp_c;
			}
		}
		
		return 1;
	}
	
	public int greenMergeSortBins()
	{
		
		if(colorResFlag != 0)
			return 0;
		
		sortFlag = 2;
		
		//First sort the green dimension
		for(int i = 0; i < 65536; i++)
		{
			for(int j = 1; j < 256; j++)
			{
				int temp = bins[i % 256][j][i / 256];
				int temp_c = colors[i % 256][j - 1][i / 256]; 
				
				for(int z = j - 1; z >= 0 && temp > bins[i % 256][z][i / 256]; z--)
				{
					bins[i % 256][z + 1][i / 256] = bins[i % 256][z][i / 256];
					colors[i % 256][z + 1][i / 256] = colors[i % 256][z][i / 256];
					
					bins[i % 256][z][i / 256] = temp;
					colors[i % 256][z][i / 256] = temp_c;
				}
			}
		}
		
		//Then sort the rest of the matrix
		
		for(int i = 1; i < 65536; i++)
		{
			int temp = bins[i % 256][0][i / 256];
			int temp_c = colors[i % 256][0][i / 256];
			
			for(int j = i - 1; j >= 0 && temp > bins[j % 256][0][j / 256]; j--)
			{
				bins[(j + 1) % 256][0][(j + 1) / 256] = bins[j % 256][0][j / 256];
				colors[(j + 1) % 256][0][(j + 1) / 256] = colors[j % 256][0][j / 256];
				
				bins[j % 256][0][j / 256] = temp;
				colors[j % 256][0][j / 256] = temp_c;
				
			}
		}
		
		return 1;
	}
	
	public int redMergeSortBins()
	{
		if(colorResFlag != 0)
			return 0;
		
		sortFlag = 2;
		
		//First sort the red dimension
		System.out.println("Merging Red Values");
		for(int i = 0; i < 65536; i++)
		{
			int a = i % 256;
			int b = i / 256;
			System.out.println("at: " + "green " + a + "| blue " + b);
			
			for(int j = 1; j < 256; j++)
			{
				int temp = bins[j - 1][i % 256][i / 256];
				int temp_c = colors[j - 1][i % 256][i / 256]; 
				
				for(int z = j - 1; z >= 0 && temp > bins[z][i % 256][i / 256]; z--)
				{
					bins[z + 1][i % 256][i / 256] = bins[z][i % 256][i / 256];
					colors[z + 1][i % 256][i / 256] = colors[z][i % 256][i / 256];
					
					bins[z][i % 256][i / 256] = temp;
					colors[z][i % 256][i / 256] = temp_c;
				}
			}
		}
		
		//Then sort the rest of the matrix
		
		for(int i = 1; i < 65536; i++)
		{
			int temp = bins[0][i % 256][i / 256];
			int temp_c = colors[0][i % 256][i / 256];
			
			int a = i % 256;
			int b = i / 256;
			System.out.println("Sorting for: 0|" + a + "|" + b + " " + temp + "|" + temp_c);
			
			for(int j = i - 1; j >= 0 && temp > bins[0][j % 256][j / 256]; j--)
			{
				bins[0][(j + 1) % 256][(j + 1) / 256] = bins[0][j % 256][j / 256];
				colors[0][(j + 1) % 256][(j + 1) / 256] = colors[0][j % 256][j / 256];
				
				bins[0][j % 256][j / 256] = temp;
				colors[0][j % 256][j / 256] = temp_c;
				
			}
		}
		
		return 1;
	}
	
	/* Disabled for how slow it is
	public int accurateSortBins()
	{
		if(colorResFlag != 0)
			return 0;
		
		sortFlag = 4;
		
		for(int j = 0; j < 256; j++) //z loop
		{
			for(int i = 0; i < 65536; i++) //xy loop
			{
				int temp = bins[i % 256][i / 256][j];
				int temp_c = colors[i % 256][i / 256][j];
				int a = i % 256;
				int b = i / 256;
				int k = i - 1;
				int l = j;
				
				if(k < 0)
				{
					k = 65535;
					l -= 1;
				}
				
				System.out.print("Sorting for: " + a + "|" + b + "|" + j + " k=" + k + " L=" + l + " bin_count=" + temp);
				
				while(l >= 0 && temp > bins[k % 256][k / 256][l] && temp > 100)
				{
					//swap values
					if(k == 65535 && l < 255)
					{
						bins[0][0][l + 1] = bins[k % 256][k / 256][l];
						colors[0][0][l + 1] = colors[k % 256][k / 256][l];
						
						bins[k % 256][k / 256][l] = temp;
						colors[k % 256][k / 256][l] = temp_c;
					}
					else
					{
						bins[(k + 1) % 256][(k + 1) / 256][l] = bins[k % 256][k / 256][l];
						colors[(k + 1) % 256][(k + 1) / 256][l] = colors[k % 256][k / 256][l];
						
						bins[k % 256][k / 256][l] = temp;
						colors[k % 256][k / 256][l] = temp_c;
					}
					//decrement and prep for next iteration
					k--;
					if(k < 0)
					{
						l--;
						k = 65535;
					}
				}
				System.out.println(" K=" + k + " L=" + l );
				
			}
		}
		
		return 1;
	}
	*/
	
	public int quickAccurateSort()
	{
		if(colorResFlag != 0)
			return 0;
		
		sortFlag = 5;
		sig_bins = new int[512];
		sig_colors = new int[512];
		for(int i = 0; i < 64; i++)
		{
			sig_bins[i] = 0;
			sig_colors[i] = 0;
		}
		
		int head = 0; //variable for us to find where we are for copying data
		
		for(int z = 0; z < 256; z++)
		{
			for(int y = 0; y < 256; y++)
			{
				for(int x = 0; x < 256; x++)
				{
					if(bins[x][y][z] >= 100)
					{
						System.out.println("Significant data at " + x + "|" + y + "|" + z);
						if(head < sig_bins.length - 1)
						{
							//add to the open index
							sig_bins[head] = bins[x][y][z];
							sig_colors[head] = colors[x][y][z];
							//increment head variable
							head++;
							//if we are at the last index, then sort the array
							if(head == sig_bins.length - 1)
							{
								sortSigArrays();
							}
						}
						else if(head == sig_bins.length - 1 && bins[x][y][z] > sig_bins[sig_bins.length - 1])
						{
							sig_bins[sig_bins.length - 1] = bins[x][y][z];
							sig_colors[sig_bins.length - 1] = colors[x][y][z];
							
							sortSigArrays();
						}
						else
						{
							System.out.println("Data Not Large Enough for list");
						}
					}
				}
			}
		}
		
		//on the off chance there were not enough values that fit the range, then sort before exiting the function
		if(head < 63)
		{
			sortSigArrays();
		}
		
		return 1;
	}
	
	
	/*----------------------*/
	/* 64 COLOR RESOLUTION  */
	/*----------------------*/
	
	public void addToBin64(int color)
	{
		colors = new int[64][64][64];
		bins  = new int[64][64][64];
		
		reInitMatrices(64);
		
		int r = 0xFF & (color);
		int g = 0xFF & (color >> 8);
		int b = 0xFF & (color >> 16);
		int offset = (r % 4) + ((g % 4) * 4) + ((b % 4) * 16);
		
		r /= 4;
		g /= 4;
		b /= 4;
		
		bins[r][g][b]++;
		colors[r][g][b] += offset;
		
	}
	
	//Named and used specifically for when dealing with 64 colors in r/g/b
	public void calcAverageBinColor64()
	{
		
	}
	
	
	/*--------------------------------*/
	/*    DATA OUTPUT FUNCTIONS       */
	/*--------------------------------*/
	
	
	public int[][] getSortedArray()
	{
		//need to change array grab based on sort method
		
		int[][] output = new int[256][256];
		if(sortFlag == 1)
		{
			for(int i = 0; i < 65536; i++)
			{
				output[i % 256][i / 256] = colors[i % 256][i / 256][0];
			}
		}
		else if(sortFlag == 2)
		{
			for(int i = 0; i < 65336; i++)
			{
				output[i % 256][i / 256] = colors[i % 256][0][i / 256];				
			}
		}
		else if(sortFlag == 3)
		{
			for(int i = 0; i < 65336; i++)
			{
				output[i % 256][i / 256] = colors[0][i % 256][i / 256];				
			}			
		}
		else if(sortFlag == 4)
		{
			for(int i = 0; i < 65536; i++)
			{
				output[i % 256][i / 256] = colors[i % 256][i / 256][0];
			}
		}
		else if(sortFlag == 5)
		{
			output = new int[sig_bins.length][1];
			for(int i = 0; i < sig_bins.length; i++)
			{
				output[i][0] = sig_colors[i];
			}
		}
		else
		{
			for(int i = 0; i < 65536; i++)
			{
				output[i % 256][i / 256] = colors[i % 256][i / 256][0];
			}			
		}
		
		return output;
	}
}
