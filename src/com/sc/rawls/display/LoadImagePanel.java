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

import com.sc.rawls.main.Histogrammer;

@SuppressWarnings("serial")
public class LoadImagePanel extends JPanel implements ActionListener{

	String path, s_path;
	JButton but, but1, buts;
	JRadioButton r,g,b,a;
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
		but1.setLocation(5, 155);
		but1.addActionListener(this);
		r = new JRadioButton("Red-Merge Sort");
		r.setSelected(true);
		g = new JRadioButton("Green-Merge Sort");
		b = new JRadioButton("Blue-Merge Sort");
		a = new JRadioButton("Full Precision Sort (Slow)");
		r.setLocation(new Point(5, 80));
		g.setLocation(new Point(5, 95));
		b.setLocation(new Point(5, 110));
		a.setLocation(new Point(5, 125));
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
		this.add(but);
		this.add(buts);
		this.add(label);
		this.add(r);
		this.add(g);
		this.add(b);
		this.add(a);
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
				int selection = 0;
				if(r.isSelected())
					selection = 1;
				else if(g.isSelected())
					selection = 2;
				else if(b.isSelected())
					selection = 3;
				else
					selection = 4;
				System.out.println("Starting Image Read");
				Histogrammer.readAndSortImage(path, s_path,selection);
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
