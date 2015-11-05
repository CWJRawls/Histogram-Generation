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
		frame.setPreferredSize(new Dimension(200, 250));
		frame.pack();
		frame.setVisible(true);
		//System.out.println("Number of components: " + frame.getComponentCount());
	}
	
	public static void errorReEntry()
	{
		frame.remove(ep);
		frame.setVisible(false);
		frame.add(lip);
		frame.setPreferredSize(new Dimension(200, 250));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void readAndSortImage(String path, String s_path, int s_method)
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
		Histogram h = new Histogram();
		
		System.out.println("Entering Try block");
		try{
			img = ImageIO.read(new File(path));
			BufferedImage n_img = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
			n_img.setData(img.getRaster());
			pp.updateLabel("Getting Image Data");
			System.out.println("Getting Image Data");
			int[] data = ((DataBufferInt) n_img.getRaster().getDataBuffer()).getData();
			pp.updateLabel("Adding image data to bins");
			System.out.println("Adding image data to bins");
			for(int i = 0; i < data.length; i++)
			{
				System.out.println("Adding data for pixel: " + i + "|" + data[i]);
				h.addToBin(data[i]);
			}
			
			System.out.println("Beginning data sorting");
			int done = 0;
			if(s_method == 1)
				done = h.redMergeSortBins();
			else if(s_method == 2)
				done = h.greenMergeSortBins();
			else if(s_method == 3)
				done = h.blueMergeSortBins();
			else
				done = h.quickAccurateSort();
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
		frame.setPreferredSize(new Dimension(200, 250));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void updateProgressLabel(String label)
	{
		pp.updateLabel(label);
	}

}
