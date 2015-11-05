package com.sc.rawls.display;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel{

	JLabel prog;
	
	public ProgressPanel()
	{
		prog = new JLabel("Loading Image");
		this.add(prog);
	}
	
	public void updateLabel(String label)
	{
		prog.setText(label);
	}
}
