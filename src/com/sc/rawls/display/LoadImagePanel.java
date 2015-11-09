package com.sc.rawls.display;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.sc.rawls.data.Gradient;
import com.sc.rawls.data.Histogram;
import com.sc.rawls.main.Histogrammer;

@SuppressWarnings("serial")
public class LoadImagePanel extends JPanel implements ActionListener{

	String path, s_path;
	JButton but, but1, buts,help;
	JRadioButton r,g,b,a,b64_1,b64_2,b32_1,b32_2,b16_1,b16_2;
	ButtonGroup rg;
	JComboBox<Integer> deltaValue, stepValue;
	JComboBox<String> feature;
	JCheckBox gradient;
	//JLabel status;
	
	public LoadImagePanel()
	{
		this.setLayout(new GridLayout(0, 1));
		this.setPreferredSize(new Dimension(250, 425));
		
		deltaValue = new JComboBox<Integer>();
		stepValue = new JComboBox<Integer>();
		
		for(int i = 0; i < 256; i++)
		{
			deltaValue.addItem(i);
			stepValue.addItem(i);
		}
		
		deltaValue.setSelectedIndex(32);
		stepValue.setSelectedIndex(32);
		feature = new JComboBox<String>();
		
		feature.addItem("None");
		feature.addItem("Insert Black");
		feature.addItem("Insert White");
		feature.setSelectedIndex(0);
		
		JLabel featureLabel = new JLabel("Gradient Features");
		
		path = "";
		s_path = "";
		rg = new ButtonGroup();
		but = new JButton("Load an Image");
		but.setLocation(5, 5);
		but.addActionListener(this);
		but1 = new JButton("Start");
		but1.setLocation(5, 170);
		but1.addActionListener(this);
		help = new JButton("Help");
		help.addActionListener(this);
		gradient = new JCheckBox("Compute Gradient Between Points");
		//r = new JRadioButton("Red-Merge Sort (Res-256)");
		//r.setSelected(true);
		//g = new JRadioButton("Green-Merge Sort (Res-256)");
		//b = new JRadioButton("Blue-Merge Sort (Res-256)");
		a = new JRadioButton("Full Precision Sort");
		a.setSelected(true);
		//b64_1 = new JRadioButton("Top 64 Colors (Res-64)");
		b64_2 = new JRadioButton("64 Value Precision");
		//b32_1 = new JRadioButton("Top 64 Colors (Res-32");
		b32_2 = new JRadioButton("32 Value Precision");
		//b16_1 = new JRadioButton("Top 64 Colors (Res-16)");
		//b16_2 = new JRadioButton("Top 256 Colors (Res-16)");
	//	r.setLocation(new Point(5, 80));
	//	g.setLocation(new Point(5, 95));
	//	b.setLocation(new Point(5, 110));
		a.setLocation(new Point(5, 125));
		//b64_1.setLocation(5, 140);
		b64_2.setLocation(5, 155);
		//b32_1.setLocation(5, 170);
		b32_2.setLocation(5, 185);
		//b16_1.setLocation(5, 200);
		//b16_2.setLocation(5, 215);
		JLabel label = new JLabel("Sorting Method");
		label.setLocation(5, 75);
		JLabel deltaLabel  = new JLabel("Repitition Threshold");
		JLabel stepLabel = new JLabel("Gradient Steps Between Colors");
		//JLabel status = new JLabel("No Image Selected");
		//status.setLocation(5, 140);
		buts = new JButton("Choose Save Location");
		buts.addActionListener(this);
	//	rg.add(r);
	//	rg.add(g);
	//	rg.add(b);
		rg.add(a);
		//rg.add(b64_1);
		rg.add(b64_2);
		//rg.add(b32_1);
		rg.add(b32_2);
		//rg.add(b16_1);
	//	rg.add(b16_2);
		this.add(but);
		this.add(buts);
		this.add(label);
		//this.add(r);
		//this.add(g);
		//this.add(b);
		this.add(a);
	//	this.add(b64_1);
		this.add(b64_2);
	//	this.add(b32_1);
		this.add(b32_2);
	//	this.add(b16_1);
	//	this.add(b16_2);
		//this.add(status);
		this.add(deltaLabel);
		this.add(deltaValue);
		this.add(gradient);
		this.add(featureLabel);
		this.add(feature);
		this.add(stepLabel);
		this.add(stepValue);
		this.add(help);
		this.add(but1);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(but))
		{
			JFileChooser open = new JFileChooser();
			int r_val = open.showOpenDialog(null);
			
			if(r_val == JFileChooser.APPROVE_OPTION)
			{
				File f = open.getSelectedFile();
				
				path = f.getAbsolutePath();
				System.out.println(path);
			}
		}
		
		if(e.getSource().equals(but1))
		{
			if(!path.equals("") && !s_path.equals(""))
			{
				int c_res;
				int selection = 0;
				boolean do_gradient = gradient.isSelected();
				int steps = stepValue.getSelectedIndex();
				int t_feat = feature.getSelectedIndex();
				int feat = 0;
				int rep_size = deltaValue.getSelectedIndex();
				
				if(t_feat == 0)
					feat = Gradient.GRAD_FEATURE_NONE;
				else if(t_feat == 1)
					feat = Gradient.GRAD_FEATURE_BLACK;
				else
					feat = Gradient.GRAD_FEATURE_WHITE;
				
				feat += (steps << 8);
				
				
				/*
				if(r.isSelected())
				{
					selection = 1;
					c_res = Histogram.COLOR_RES_256;
				}
				else if(g.isSelected())
				{
					selection = 2;
					c_res = Histogram.COLOR_RES_256;
				}
				else if(b.isSelected())
				{
					selection = 3;
					c_res = Histogram.COLOR_RES_256;
				}
				else if(a.isSelected())
				{
					selection = 4;
					c_res = Histogram.COLOR_RES_256;
				}
				else if(b64_1.isSelected())
				{
					selection = 5;
					c_res = Histogram.COLOR_RES_64;
				}
				else if(b64_2.isSelected())
				{
					selection = 6;
					c_res = Histogram.COLOR_RES_64;
				}
				else if(b32_1.isSelected())
				{
					selection = 7;
					c_res = Histogram.COLOR_RES_32;
				}
				else if(b32_2.isSelected())
				{
					selection = 8;
					c_res = Histogram.COLOR_RES_32;
				}
				else if(b16_1.isSelected())
				{
					selection = 9;
					c_res = Histogram.COLOR_RES_16;
				}
				else
				{
					selection = 10;
					c_res = Histogram.COLOR_RES_16;
				}
				*/
				
				if(a.isSelected())
				{
					selection = 4;
					c_res = Histogram.COLOR_RES_256;
				}
				else if(b64_2.isSelected())
				{
					selection = 6;
					c_res = Histogram.COLOR_RES_64;
				}
				else
				{
					selection = 8;
					c_res = Histogram.COLOR_RES_32;
				}
				
				System.out.println("Starting Image Read");
				Histogrammer.readAndSortImage(path, s_path,selection,c_res,rep_size,do_gradient,feat);
			}
		}
		
		if(e.getSource().equals(buts))
		{
			JFileChooser save = new JFileChooser();
			
			int r_val = save.showSaveDialog(null);
			
			if(r_val == JFileChooser.APPROVE_OPTION)
			{
				File f = save.getSelectedFile();
				
				s_path = f.getAbsolutePath();
				
				System.out.println(s_path);
			}
		}
		
		if(e.getSource().equals(help))
		{
			System.out.println("SORTING METHOD HELP");
			System.out.println("\tEach sorting method is labeled with the rgb channel precision of the sorting method.\n\tFULL PRECISION: Colors are sorted into a cube of 256 values in the red channel, 256 values in the green channel, and 256 values in the red channel\n\t64-VALUES: The sorting cube consists of 64 bins in each color channel, the result of averaging the larger matrix by color occurence.\n\t32-VALUES: The sorting cube consists of 32 bins in each color channel, done by averaging the larger cube by color occurences.");
			System.out.println("\nREPITITION FILTER HELP");
			System.out.println("\tThe repitition filter governs how close another color can be to another high occuring color before it is considered non-unique.\n\tThe process examines all three color channels to determine uniqueness based on the value selected here.\n\t0 - ALL Colors are unique | 255 - NO Colors are unique");
			System.out.println("\nGRADIENT OPTIONS HELP");
			System.out.println("\tCompute Gradient: When checked will attempt to expand the histogram image and compute a smooth gradient between neighboring colors in the final output image.");
			System.out.println("\tInsert Black: When selected and computing a gradient will cause the gradient to fade to black, then to the next color.");
			System.out.println("\tInsert White: When selected and computing a gradient will casue the gradient to fade to white, then to the next color.");
			System.out.println("\tNone: When selected and computing a gradient will fade directly to the next adjacent color.");
			System.out.println("\tNumber of Steps: The number of steps the gradient will compute between two adjacent colors.\n\tThis setting will include any inserted black or white values.");
		}
		
	}
}
