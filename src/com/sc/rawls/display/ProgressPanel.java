package com.sc.rawls.display;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel{

	JLabel prog;
	
	public ProgressPanel()
	{
		prog = new JLabel("Loading Image");
		prog.setLocation(5, 5);
		this.add(prog);
	}
	
	public void updateLabel(String label)
	{
		prog.setText(label);
	}
}
