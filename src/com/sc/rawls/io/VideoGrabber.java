package com.sc.rawls.io;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import java.awt.image.DataBufferInt;

public class VideoGrabber {

	private FFmpegFrameGrabber g;
	
	public VideoGrabber(String path)
	{
		g = new FFmpegFrameGrabber(path);
		try {
			g.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasNextFrame()
	{
		if(g.getFrameNumber() < g.getLengthInFrames() - 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int[] getNextFrameData()
	{
		Frame f;
		try {
			f = g.grab();
		} catch (Exception e) {

			e.printStackTrace();
			f = new Frame();
		}
		
		BufferedImage bi = new Java2DFrameConverter().getBufferedImage(f);
		
		final int[] data = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();
		
		return data;
	}
}
