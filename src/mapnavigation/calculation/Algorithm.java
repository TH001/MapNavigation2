package mapnavigation.calculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import mapnavigation.DEFINES;

public class Algorithm {
	
	Converter conv = new Converter();
	

	int[][] colormap;
	double[][] distancemap;
	int[][][] lasthopmap;
	
	double maxdistance; 				//maximale distance f�r den Fall, dass
										//alle Punkte abgelaufen werden m�ssen
										//eh der Zielpunkt ereicht wird
	double lastmindistance;
	double tempdistance = 0;
	int[] tempcoodinate = new int [2];
	int done = 1;
	int buffer;
	boolean diagonal=false;
	int nowalls = 0;
	
	private int writingattempt = 0;
	private String [] fileOutput = new String[12];
	private int inputfound = 0;
	private int startset = 0;
	private int targetset = 0;
	private int calcingactiv = 0;
	private int finallydone = 0;
	private String inputname;
	private String outputname;
	
	private int startX = -1;
	private int startY = -1;
	private int tagetX = -1;
	private int tagetY = -1;
	
//	double outpercentage;
//	double percentage;
//	
//	private int memorystartX = -1;
//	private int memorystartY = -1;

	public Algorithm() {
		// TODO Auto-generated constructor stub
	}
	public void doeveything() {
		System.out.println("Working");
	}
	
	public void createmaps() {
		colormap= new int[conv.pixelX][conv.pixelY];		//karten gr��e intitaliesienen
		distancemap= new double[conv.pixelX][conv.pixelY];	//karten gr��e initialiesieren
		lasthopmap= new int[conv.pixelX][conv.pixelY][2];	//karten gr��e initialiesieren
															//dreistufig um koodinaten des last hop zu speichern
		this.maxdistance = (conv.pixelX * conv.pixelY);			//anzahl der pixel als maximale
																//distance setzten, Annahme alle
																//pixel sind gleich weit von ein-
																//ander entfernt
		
		for (int i = 0; i < distancemap.length; i++) {			//f�r alle Felder der distancemap
			for (int j = 0; j < distancemap[i].length; j++) {	//die maximale distance setzten
				distancemap[i][j]=maxdistance;
			}
		}
		for (int i = 0; i < colormap.length; i++) {				//colormap komplett weiss f�rben
			for (int j = 0; j < colormap[i].length; j++) {
				colormap[i][j]=DEFINES.WHITE;
			}
		}
		for (int i = 0; i < colormap.length; i++) {				//mackiere alle Wande in der Colormap
			for (int j = 0; j < colormap[i].length; j++) {			//als Rot (nich begehbar)
//				System.out.println("x:"+i+" pixelX:"+conv.pixelX+ " y:"+j+" pixelY:"+conv.pixelY);
				if(conv.getcolor(i, j)==DEFINES.BLACK) {
					colormap[i][j]=DEFINES.RED;
				}
			}
		}
		for (int i = 0; i < lasthopmap.length; i++) {			//f�r alle Felder der lasthopmap
			for (int j = 0; j < lasthopmap[i].length; j++) {	//-1 in der x und y coodinate setzen
				lasthopmap[i][j][0]=-1;
				lasthopmap[i][j][1]=-1;
			}
		}
		
	}
	
	public void cleancolormap() {
		for (int i = 0; i < colormap.length; i++) {				//colormap komplett weiss f�rben
			for (int j = 0; j < colormap[i].length; j++) {
				if(colormap[i][j]==DEFINES.GREEN||colormap[i][j]==DEFINES.PURPLE) {
					colormap[i][j]=DEFINES.YELLO;
				}
			}
		}
	}
	public void setuplocation() {
		if(this.colormap[startX][startY]!=DEFINES.RED) {	//Startpunkt muss begehbar sein
			tempcoodinate[0]=startX;
			tempcoodinate[1]=startY;
			this.colormap[tempcoodinate[0]][startY]=DEFINES.BLUE;
			this.distancemap[tempcoodinate[0]][tempcoodinate[1]]=0;
			this.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]=startX;
			this.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]=startY;
			lastmindistance = 0;
		}
		else {
			System.out.println("Startlocation couldn't be set!");
		}
	}
	public void calcdistances() {
		System.out.println(nowalls);
		while(done!=0) {// bis kein weiterer punkt auf diese art und weise berechnet werden kann
			done=0;
			for (int i = 0; i < distancemap.length; i++) {
				for (int j = 0; j < distancemap[i].length; j++) {
					if(colormap[i][j]!=DEFINES.YELLO) {
						if(distancemap[i][j]<=lastmindistance) {		//<= nur verwendet falls ich quere schnitte noch programieren sollte = muss auch funktionien
							tempcoodinate[0]=i;
							tempcoodinate[1]=j;
							tempdistance=distancemap[i][j];
							calcroundpoint();
//							percentage=percentage+1;
						}
					}
				}
			}
			if(diagonal==true) {
				lastmindistance=lastmindistance+Math.sqrt(2);
				diagonal=false;
			}
			else {
				lastmindistance++;
			}
		}
//		System.out.println(percentage + ";" + outpercentage);
	}
	public void calcroundpoint() {
		//kreisformiges abarbeiten der punkte um den temporen punkt(unter tempcoodinate) nach
		//der reihnfolge NOSW
			//direkt angrenzende
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.YELLO) {				//nicht f�r w�nde berechnen
				if(this.distancemap [tempcoodinate[0]+1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt k�rzer
					this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]]   = DEFINES.ORANGE;//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]][tempcoodinate[1]+1]>tempdistance+1) {		//kontrolle weg mit temp punkt k�rzer
					this.distancemap[tempcoodinate[0]][tempcoodinate[1]+1]   =tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]][tempcoodinate[1]+1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]-1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt k�rzer
					this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]]   = DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]][tempcoodinate[1]-1]>tempdistance+1) {		//kontrolle weg mit temp punkt k�rzer
					this.distancemap[tempcoodinate[0]][tempcoodinate[1]-1]   =tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]][tempcoodinate[1]-1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			//�ber ecke angrenzende
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {					//nich durch w�nde berechnen
				if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.YELLO) {	//nicht f�r w�nde berechnen
					if( this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt k�rzer
						this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {					//nich durch w�nde berechnen
				if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.YELLO) {		//nicht f�r w�nde berechnen
					 if(this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt k�rzer
						this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {							//nich durch w�nde berechnen
				if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.YELLO) {				//nicht f�r w�nde berechnen
					if( this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt k�rzer
						this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {							//nich durch w�nde berechnen
				if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.YELLO) {				//nicht f�r w�nde berechnen
					if( this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt k�rzer
						this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
		//aktuellen punkt als final berechnet makieren
		this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.YELLO;
	}
	public void markway() {
			tempcoodinate[0]=startX;
			tempcoodinate[1]=startY;
			this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.BLUE;
		if(this.colormap[tagetX][tagetY]!=DEFINES.RED && this.distancemap[tagetX][tagetY]!=this.maxdistance) {	//Zielpunkt muss begehbar sein
			tempcoodinate[0]=tagetX;
			tempcoodinate[1]=tagetY;
			this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.PURPLE;
		}
		else {
			System.out.println("Tagetlocation couldn't be set!");
		}
		while(startX!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]||startY!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]) {
	//		System.out.println(buffer + ";" + tempcoodinate[0] + "," + tempcoodinate[1]); //testing
			colormap[lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]][lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]]=DEFINES.GREEN;
			buffer=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0];
			tempcoodinate[1]=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1];
			tempcoodinate[0]=buffer;
		}
	}
	public void readfromFile() {
		String line;
	    try {
	    	File transferbuffer = new File("transferbuffer.tmp");
			// Creates a FileReader Object
			FileReader filereader = new FileReader(transferbuffer); 
	        BufferedReader bufferreader = new BufferedReader(filereader);
	        line = bufferreader.readLine();

	        for (int i = 0; line != null; i++) {
//	        	if(i == 0)if(Integer.parseInt(line) == writingattempt)return;	//no change no date read again
	        	fileOutput[i] = line;
	        	line = bufferreader.readLine();
			}
	        bufferreader.close();
//	        while (line != null) {     
//	          //do whatever here 
//	            line = bufferreader.readLine();
//	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    
	    //TESTOUTPUT
	    System.out.println("beginn");
	    for (int i = 0; i < fileOutput.length; i++) {
			System.out.println(fileOutput[i]);
		}
	    System.out.println("end");
	    
	    //OUTPUTTODATA
	    
		writingattempt = Integer.parseInt(fileOutput[0]);
		inputfound = Integer.parseInt(fileOutput[1]);
		startset = Integer.parseInt(fileOutput[2]);
		targetset = Integer.parseInt(fileOutput[3]);
		calcingactiv = Integer.parseInt(fileOutput[4]);
		inputname = fileOutput[5];
		outputname= fileOutput[6];
		startX = Integer.parseInt(fileOutput[7]);
		startY = Integer.parseInt(fileOutput[8]);
		tagetX = Integer.parseInt(fileOutput[9]);
		tagetY = Integer.parseInt(fileOutput[10]);
		finallydone = Integer.parseInt(fileOutput[11]);
		
		File transferbuffer = new File("transferbuffer.tmp");
		transferbuffer.delete();
	}
}