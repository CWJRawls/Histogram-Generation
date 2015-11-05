package com.sc.rawls.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.sc.rawls.main.Histogrammer;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements ActionListener{

	JButton but, but1;
	
	//need constructor
	public MainPanel()
	{
		but = new JButton();
		but.setText("Load Image");
		but.setSize(195, 60);
		but.addActionListener(this);
		this.add(but);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(but))
		{
			Histogrammer.loadImage();
		}
	}
	
	
}
