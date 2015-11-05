package com.sc.rawls.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sc.rawls.main.Histogrammer;

@SuppressWarnings("serial")
public class ErrorPanel extends JPanel implements ActionListener{

	private JButton but;
	public ErrorPanel()
	{
		JLabel Label = new JLabel("OOPS! Looks like we had an error!");
		but = new JButton("Try Again");
		but.addActionListener(this);
		Label.setLocation(5, 5);
		but.setLocation(5, 20);
		this.add(Label);
		this.add(but);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(but))
		{
			Histogrammer.errorReEntry();
		}
		
	}

}
