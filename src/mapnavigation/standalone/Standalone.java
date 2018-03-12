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
	
	public int run() {
		Algorithm calculation = new Algorithm();
		File map = new File(inputname);
		if(map.exists()) {
			calculation.readimage(inputname);
			calculation.createmaps();
			if(calculation.possibleposition(startX, startY)==0) {
				calculation.setuplocation(startX, startY);
				calculation.calcdistances();
				if(calculation.possibleposition(targetX, targetY)==0) {
					calculation.markway(targetX, targetY);
					calculation.outputtofile(outputname);
					return 0;
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
		return -1;
	}
}
