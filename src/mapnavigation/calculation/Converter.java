/*
 * Name			: Converter.java
 * Author		: Tom
 * Created on	: 31.12.2017
 * Description	: empty
 */
package mapnavigation.calculation;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

import mapnavigation.DEFINES;

public class Converter {
	
//	static converter conv= new converter();
	
	public BufferedImage inputimg;
	public BufferedImage outputimg;
	public File inputfile = new File("notnull");
	public File outputfile;
	public Color c;
	
	public int pixelX;
	public int pixelY;
	
	public Converter() {
	}
//	public static void main(String[] args) {
//		conv.readpicture("firstTest.png");
//		System.out.println(conv.getcolor(01, 01));
//		
//	}
	public int readpicture(String inputname) {
		
		if(inputname == inputfile.getName()) return 0;
		//write picture-file to BufferedImage
		inputimg=null;
		inputfile=new File(inputname);
		try {								//wenn ein fehler kommt führe die exeption aus
			inputimg=ImageIO.read(inputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("read picture to buffered image");
		//Bildmaße einlesen & plus Rand
		pixelX=inputimg.getWidth()+2;	//eins weil null ja schon zusätzlich da ist
		pixelY=inputimg.getHeight()+2;
		System.out.println("höhe:" + (pixelY-2) + " breite:" + (pixelX-2));
		return 1;
	}
	public int getcolor(int xcoordinate, int ycoordinate) {
		//adding black edge
		if((xcoordinate==0||ycoordinate==0) || (xcoordinate==pixelX-1||ycoordinate==pixelY-1)) {
			return DEFINES.BLACK;
		}
		else {
			c = new Color(inputimg.getRGB((xcoordinate-1),(ycoordinate-1)));
			if	   (c.getRed()	>200 &&
					c.getGreen()>200 &&
					c.getBlue()	>200) {
					return DEFINES.WHITE;
			}
			else if(c.getRed()	<50 &&		//for testing only one channel
					c.getGreen()<50 &&
					c.getBlue()	<50) {
					return DEFINES.BLACK;
			}
			else {
				return -1;
			}
		}
	}
	
	public void writepicture(String outputname, int[][] finalmap) {
		outputimg = new BufferedImage(pixelX,pixelY, BufferedImage.TYPE_INT_RGB);
		outputfile= new File(outputname);
		
		//Colors shifted
		for (int j = 0; j < pixelY; j++) {
			for (int i = 0; i < pixelX; i++) {
				if(finalmap[i][j]==DEFINES.WHITE) {
					c = new Color(255, 255, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.BLACK) {
					c = new Color(125, 0, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.YELLO) {
					c = new Color(255, 255, 255);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.GREEN) {
					c = new Color(0, 0, 255);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.BLUE) {
					c = new Color(0, 255, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.PURPLE) {
					c = new Color(255, 0, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.RED) {
					c = new Color(0, 0, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.ORANGE) {
					c = new Color(255, 102, 50);
					outputimg.setRGB(i, j, c.getRGB());
				}
				
			}
		}
		
		try {
			ImageIO.write(outputimg, "PNG", outputfile);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error: " + e);
		}
	}
	public void writeorignalpicture(String outputname, int[][] finalmap) {
		outputimg = new BufferedImage(pixelX,pixelY, BufferedImage.TYPE_INT_RGB);
		outputfile= new File(outputname);
		
		for (int j = 0; j < pixelY; j++) {
			for (int i = 0; i < pixelX; i++) {
				if(finalmap[i][j]==DEFINES.WHITE) {
					c = new Color(255, 255, 255);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.BLACK) {
					c = new Color(0, 0, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.YELLO) {
					c = new Color(255, 255, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.GREEN) {
					c = new Color(0, 255, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.BLUE) {
					c = new Color(0, 0, 255);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.PURPLE) {
					c = new Color(125, 10, 125);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.RED) {
					c = new Color(255, 0, 0);
					outputimg.setRGB(i, j, c.getRGB());
				}
				else if(finalmap[i][j]==DEFINES.ORANGE) {
					c = new Color(255, 102, 50);
					outputimg.setRGB(i, j, c.getRGB());
				}
				
			}
		}
		
		try {
			ImageIO.write(outputimg, "PNG", outputfile);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error: " + e);
		}
	}
	
	public String getInputname() {
		return inputfile.getName();
	}
}
