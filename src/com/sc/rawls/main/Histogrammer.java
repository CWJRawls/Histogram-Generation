package com.sc.rawls.main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.image.DataBufferInt;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sc.rawls.data.Histogram;
import com.sc.rawls.display.ErrorPanel;
import com.sc.rawls.display.LoadImagePanel;
import com.sc.rawls.display.MainPanel;
import com.sc.rawls.display.ProgressPanel;
import com.sc.rawls.io.ImageWriter;

public class Histogrammer {

	static JFrame frame = new JFrame();
	static MainPanel mp = new MainPanel();
	static LoadImagePanel lip = new LoadImagePanel();
	static ErrorPanel ep = new ErrorPanel();
	static ProgressPanel pp = new ProgressPanel();
	static BufferedImage out;
	
	public static void main(String[] args) {
		
		frame.setName("Histogrammer");
		frame.setTitle("Histogrammer");
		frame.setPreferredSize(new Dimension(200, 60));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//create and add pane
		frame.add(mp);
			//pack window
		frame.pack();
		frame.setVisible(true);
			//start
	}
	
	public static void loadImage()
	{
		frame.remove(mp);
		frame.setVisible(false);
		frame.add(lip);
		frame.setPreferredSize(new Dimension(250, 375));
		frame.pack();
		frame.setVisible(true);
		//System.out.println("Number of components: " + frame.getComponentCount());
	}
	
	public static void errorReEntry()
	{
		frame.remove(ep);
		frame.setVisible(false);
		frame.add(lip);
		frame.setPreferredSize(new Dimension(250, 375));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void readAndSortImage(String path, String s_path, int s_method, int c_res)
	{
		System.out.println(frame.getComponentCount());
		frame.remove(lip);
		frame.setVisible(false);
		frame.add(pp);
		frame.setPreferredSize(new Dimension(200, 50));
		frame.pack();
		frame.setVisible(true);
		
		BufferedImage img = null;
		System.out.println("Reading Image");
		Histogram h = new Histogram(c_res);
		
		System.out.println("Entering Try block");
		try{
			img = ImageIO.read(new File(path));
			BufferedImage n_img = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
			n_img.setData(img.getRaster());
			pp.updateLabel("Getting Image Data");
			System.out.println("Getting Image Data");
			int[] data = ((DataBufferInt) n_img.getRaster().getDataBuffer()).getData();
			pp.updateLabel("Adding image data to bins");
			System.out.print("Adding image data to bins");
			for(int i = 0; i < data.length; i++)
			{
				if(c_res == Histogram.COLOR_RES_256)
					h.addToBin(data[i]);
				else if(c_res == Histogram.COLOR_RES_64)
					h.addToBin64(data[i]);
				else if(c_res == Histogram.COLOR_RES_32)
					h.addToBin32(data[i]);
				
				if(i % 20 == 0)
				{
					System.out.print(" " + i);
				}
				if(i % 4000 == 0)
				{
					System.out.println("");
				}
			}
			
			if(c_res == Histogram.COLOR_RES_64)
				h.calcAverageBinColor64();
			else if(c_res == Histogram.COLOR_RES_32)
				h.calcAverageBinColor32();
			
			System.out.println("Beginning data sorting");
			int done = 0;
			if(s_method == 1)
				done = h.redMergeSortBins();
			else if(s_method == 2)
				done = h.greenMergeSortBins();
			else if(s_method == 3)
				done = h.blueMergeSortBins();
			else if(s_method == 4)
				done = h.quickAccurateSort();
			else if(s_method == 5)
				done = h.fullSortColors64();
			else if(s_method == 6)
				done = h.bin256Sort64();
			else if(s_method == 7)
				done = h.fullSortColors32();
			else
				done = h.bin256Sort32();
			
			if(done == 1)
			{
				pp.updateLabel("Finished Sort");
				System.out.println("Sort Finished");
				int[][] s_data = h.getSortedArray();
				
				//initialize the output image to the size of the output buffer
				out = new BufferedImage(s_data.length, s_data[0].length, BufferedImage.TYPE_INT_RGB);
				
				System.out.println("Building Sorted Image from Array");
				for(int i = 0; i < s_data.length; i++)
				{
					for(int j = 0; j < s_data[0].length; j++)
					{
						out.setRGB(i, j, s_data[i][j]);
					}
				}
				
				ImageWriter.writeImage(out, s_path);
			}
		} catch(Exception e) {
			e.printStackTrace();
			frame.setVisible(false);
			frame.remove(pp);
			frame.setPreferredSize(new Dimension(200, 80));
			frame.add(ep);
			frame.pack();
			frame.setVisible(true);
		};
		
		frame.setVisible(false);
		frame.remove(pp);
		frame.add(lip);
		frame.setPreferredSize(new Dimension(250, 375));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void updateProgressLabel(String label)
	{
		pp.updateLabel(label);
	}

}
