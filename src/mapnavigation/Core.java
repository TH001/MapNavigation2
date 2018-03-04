/**
 * Name			: Core.java
 * Author		: Tom
 * Created on	: 24.02.2018
 * Description	: empty
 */
package mapnavigation;

import java.io.File;

import mapnavigation.gui.UIfunktion;
import mapnavigation.standalone.Standalone;

public class Core {
////Objects//////////////////////////////////////////
	
	UIfunktion gui = new UIfunktion();
	Standalone salone =new Standalone();
	
	public Core() {
		
	}
	public static void main(String[] args) {
		try
		{
			Core MAIN = new Core();
			MAIN.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run() throws Exception {
		System.out.println("Point0");
//		gui.run();
		salone.run();
	}
}