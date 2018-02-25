/**
 * Name			: UIfunktion.java
 * Author		: Tom
 * Created on	: 24.02.2018
 * Description	: empty
 */
package mapnavigation.gui;

import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import mapnavigation.calculation.Algorithm;

public class UIfunktion {

	public UIfunktion() {
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algorithm calculation = new Algorithm();
					MainWindow frame = new MainWindow(calculation);
					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.disableall();
					frame.Inputname.enable();
					frame.setStatus("geben sie eine Inputkarte an", 0, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
