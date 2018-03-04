package mapnavigation.standalone;

import java.io.File;

import mapnavigation.calculation.Algorithm;

public class Standalone {

	private String inputname = "test4.png";
	private String outputname = "standaloneoutput0.png";
	private int startX = 2;
	private int startY = 2;
	private int targetX = 14;
	private int targetY = 11;
	
	public Standalone() {
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		Algorithm calculation = new Algorithm();
		File picture = new File(inputname);
		if(picture.exists()) {
			calculation.readimage(inputname);
			calculation.createmaps();
			if(calculation.posibleposition(startX, startY)==0) {
				calculation.setuplocation(startX, startY);
				calculation.calcdistances();
				if(calculation.posibleposition(targetX, targetY)==0) {
					calculation.cleancolormap();
					calculation.markway(targetX, targetY);
					calculation.outputtofile(outputname);
				}
				else {
					System.out.println("Ziel konnte nicht gesetzt werden");
				}
			}
			else {
				System.out.println("Start konnte nicht gesetzt werden");
			}
		}
		else {
			System.out.println("Karte existiert nicht oder liegt am falschen Ort");
		}
	}
}
