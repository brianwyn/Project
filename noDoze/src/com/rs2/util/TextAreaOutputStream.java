package com.rs2.util;

import java.io.*; 
import java.util.*; 
import javax.swing.*;

public class TextAreaOutputStream extends OutputStream {

	@SuppressWarnings("rawtypes")
	private LinkedList lineLengths;
	private JTextArea textArea;                               
	private int maxLines;                                                     
	private int curLength;                              
	private byte[] oneByte;                                
	
	public TextAreaOutputStream(JTextArea ta) {     
		this(ta,1000);     
	}  
	
	@SuppressWarnings("rawtypes")
	public TextAreaOutputStream(JTextArea ta, int ml) {     
		if(ml < 1) { 
			try {
				System.out.println("Maximum lines of "+ml+" in TextAreaOutputStream constructor is not permitted"); 
			} catch (Exception e) {
				System.out.println(e);
				
			}
		}     
		textArea = ta;     
		maxLines = ml;     
		lineLengths = new LinkedList();     
		curLength = 0;     
		oneByte = new byte[1];     
	}  
	
	@SuppressWarnings("rawtypes")
	public synchronized void clear() {     
		lineLengths = new LinkedList();     
		curLength = 0;     
		textArea.setText("");     
	}  
	
	public synchronized int getMaximumLines() { 
		return maxLines; 
	} 
	
	public synchronized void setMaximumLines(int val) { 
		maxLines = val; 
	}  
	
	public void close() {     
		if (textArea != null) {         
			textArea = null;         
			lineLengths = null;        
			oneByte = null;         
		}     
	}  
	
	public void flush() {     
	} 
	
	public void write(int val) {     
		oneByte[0] = (byte)val;     
		write(oneByte, 0, 1);     
	}  
	public void write(byte[] ba) {    
		write(ba, 0, ba.length);     
	}  
	
	@SuppressWarnings("unchecked")
	public synchronized void write(byte[] ba, int str, int len) {    
		try {         
			curLength += len;
			if (bytesEndWith(ba, str, len, LINE_SEP)) {
				lineLengths.addLast(new Integer(curLength));
				curLength = 0; 
				if (lineLengths.size() > maxLines) {
					textArea.replaceRange(null, 0, ((Integer)lineLengths.removeFirst()).intValue()); 
				}             
			}         
			for(int xa = 0; xa < 10; xa++) {
				try { 
					textArea.append(new String(ba, str, len));
					break;
				} catch(Throwable thr) {                                                 
					if ( xa == 9) { 
						thr.printStackTrace(); 
					} else { 
						Thread.sleep(200);    
					}
				}
			}                     
		} catch(Throwable thr) {         
			CharArrayWriter caw = new CharArrayWriter();         
			thr.printStackTrace(new PrintWriter(caw, true));         
			textArea.append(System.getProperty("line.separator", "\n"));         
			textArea.append(caw.toString());         
		}     
	}

	private boolean bytesEndWith(byte[] ba, int str, int len, byte[] ew) {     
		if (len<LINE_SEP.length) { 
			return false; 
		}     
		for(int xa = 0, xb = (str + len - LINE_SEP.length); 
			xa < LINE_SEP.length; xa++, xb++) {
			if (LINE_SEP[xa] != ba[xb]) {
				return false;
			} 
		}    
		return true;    
	}  

	static private byte[] LINE_SEP = System.getProperty("line.separator", "\n").getBytes(); 	
} 