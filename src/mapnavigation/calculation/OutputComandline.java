package mapnavigation.calculation;

public class OutputComandline {

	public OutputComandline() {
		// TODO Auto-generated constructor stub
	}
	public void output(String usage,int[][][] map){
		switch (usage){
			case "colormap":
				for (int i = 0; i < map[0].length; i++) {
					for (int j = 0; j < map.length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < map.length; j++) {
						System.out.print("| " + map[j][i][0] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < map.length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap double":
				for (int i = 0; i < map[0].length; i++) {
					for (int j = 0; j < map.length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < map.length; j++) {
						if(map[j][i][0]<10) {
							System.out.print("|   " + (int)map[j][i][0] +"");
						}
						else if(map[j][i][0]<100){
							System.out.print("|  " + (int)map[j][i][0] +"");
						}
						else {
							System.out.print("| " + (int)map[j][i][0] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < map.length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap":
				for (int i = 1; i < map[0].length-1; i++) {
					for (int j = 1; j < map.length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < map.length-1; j++) {
						if(map[j][i][0]<0 && map[j][i][1]<0) {
							System.out.print("|" + map[j][i][0] + ";" + map[j][i][1] +"");
						}
						else if(map[j][i][0]<10 && map[j][i][1]<10 ) {
							System.out.print("| " + map[j][i][0] + ";" + map[j][i][1] +" ");
						}
						else if((map[j][i][0]>=10||map[j][i][0]<0) && map[j][i][1]<10) {
							System.out.print("|" + map[j][i][0] + ";" + map[j][i][1] +" ");
						}
						else if(map[j][i][0]<10 && (map[j][i][1]>=10||map[j][i][1]<0)) {
							System.out.print("| " + map[j][i][0] + ";" + map[j][i][1] +"");
						}
						else {
							System.out.print("|" + map[j][i][0] + ";" + map[j][i][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < map.length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
			
			//special versions
			case "colormap invert":
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < map[i].length; j++) {
						System.out.print("| " + map[i][j] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < map[map.length-1].length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap invert":
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < map[i].length; j++) {
						if(map[i][j][0] < 10) {
							System.out.print("|   " + map[i][j] +"");
						}
						else if(map[i][j][0] < 100){
							System.out.print("|  " + map[i][j] +"");
						}
						else {
							System.out.print("| " + map[i][j] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < map[map.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "distancemap double invert":
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < map[i].length; j++) {
						if(map[i][j][0] < 10) {
							System.out.print("|   " + (int)map[i][j][0] +"");
						}
						else if(map[i][j][0] < 100){
							System.out.print("|  " + (int)map[i][j][0] +"");
						}
						else {
							System.out.print("| " + (int)map[i][j][0] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < map[map.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap invert":
				for (int i = 1; i < map.length-1; i++) {
					for (int j = 1; j < map[i].length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < map[i].length-1; j++) {
						if(map[i][j][0]<10 && map[i][j][1]<10) {
							System.out.print("| " + map[i][j][0] + ";" + map[i][j][1] +" ");
						}
						else if(map[i][j][0]>=10 && map[i][j][1]<10) {
							System.out.print("|" + map[i][j][0] + ";" + map[i][j][1] +" ");
						}
						else if(map[i][j][0]<10 && map[i][j][1]>=10) {
							System.out.print("| " + map[i][j][0] + ";" + map[i][j][1] +"");
						}
						else {
							System.out.print("|" + map[i][j][0] + ";" + map[i][j][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < map[map.length-1].length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
		}
	}
}
