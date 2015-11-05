package com.sc.rawls.display;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.sc.rawls.data.Histogram;
import com.sc.rawls.main.Histogrammer;

@SuppressWarnings("serial")
public class LoadImagePanel extends JPanel implements ActionListener{

	String path, s_path;
	JButton but, but1, buts;
	JRadioButton r,g,b,a,b64_1,b64_2;
	ButtonGroup rg;
	//JLabel status;
	
	public LoadImagePanel()
	{
		path = "";
		s_path = "";
		rg = new ButtonGroup();
		but = new JButton("Load an Image");
		but.setLocation(5, 5);
		but.addActionListener(this);
		but1 = new JButton("Start");
		but1.setLocation(5, 170);
		but1.addActionListener(this);
		r = new JRadioButton("Red-Merge Sort (Res-256)");
		r.setSelected(true);
		g = new JRadioButton("Green-Merge Sort (Res-256)");
		b = new JRadioButton("Blue-Merge Sort (Res-256)");
		a = new JRadioButton("Full Precision Sort (Res-256) (Slow)");
		b64_1 = new JRadioButton("Top 64 Colors (Res-64)");
		b64_2 = new JRadioButton("Top 256 Color (Res-64)");
		r.setLocation(new Point(5, 80));
		g.setLocation(new Point(5, 95));
		b.setLocation(new Point(5, 110));
		a.setLocation(new Point(5, 125));
		b64_1.setLocation(5, 140);
		b64_2.setLocation(5, 155);
		JLabel label = new JLabel("Sorting Method");
		label.setLocation(5, 75);
		//JLabel status = new JLabel("No Image Selected");
		//status.setLocation(5, 140);
		buts = new JButton("Choose Save Location");
		buts.addActionListener(this);
		rg.add(r);
		rg.add(g);
		rg.add(b);
		rg.add(a);
		rg.add(b64_1);
		rg.add(b64_2);
		this.add(but);
		this.add(buts);
		this.add(label);
		this.add(r);
		this.add(g);
		this.add(b);
		this.add(a);
		this.add(b64_1);
		this.add(b64_2);
		//this.add(status);
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
				else
				{
					selection = 6;
					c_res = Histogram.COLOR_RES_64;
				}
				System.out.println("Starting Image Read");
				Histogrammer.readAndSortImage(path, s_path,selection,c_res);
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
		
	}
}
