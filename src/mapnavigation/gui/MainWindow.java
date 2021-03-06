package mapnavigation.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.sun.javafx.tk.Toolkit;

import jdk.internal.dynalink.linker.ConversionComparator;
import mapnavigation.DEFINES;
import mapnavigation.calculation.Algorithm;


public class MainWindow extends JFrame {
	
	public static final long serialVersionUID = 1;
	public JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JTextField Inputname;
	public JTextField Outputname;
	public JFormattedTextField startX;
	public JFormattedTextField startY;
	public JFormattedTextField targetY;
	public JFormattedTextField targetX;
	public Button Run;
	public Button DistancesRun;
	public Button checkbutton;
	public Button SetStart;
	public Button SetTarget;
	public Button outputw;
	
	private JLabel StatusMesssage;
	private JProgressBar progressBar;
	private JProgressBar progressBar_active;
	
	private ImageIcon Logo = new ImageIcon("/designe/imgsource/Logo.png");
	
	private int xx,xy;
	
	private int activeprogressbarvalue=75;
	private int progressbarvalue=50;
	
	String newoutputname = "out.png";
	int startState=0;
	int targetState=0;
	
	private int checkedInput;	
	private int writingattempt = 0;
	private int startset = 0;
	private int targetset = 0;
	private int calcingactive = 0;
	private int finallydone = 0;
	/**
	 * Create the 
	 */
	public MainWindow(Algorithm calculation) {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335*1, 545*1);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//		setIconImage(Logo.getImage());
//		setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage("/designe/imgsource/Logo.png"));
		
		ExternalViewer externalframe = new ExternalViewer(this);		
		externalframe.setVisible(true);
		
		System.out.print(externalframe.getlastclickX()+"|"+externalframe.getlastclickY());
		
		JPanel out_panel_0 = new JPanel();
		out_panel_0.setBounds(335, 0, 314, 450);
		contentPane.add(out_panel_0);
		
		JPanel out_panel_1 = new JPanel();
		out_panel_1.setBounds(0, 545, 581, 99);
		contentPane.add(out_panel_1);
		
		
		Run = new Button("Run Waycalc");
		Run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Run.setLabel("calculating Way...");
				Run.disable();
				checkbutton.disable();
				SetStart.disable();
				SetTarget.disable();
				setStatus("l�sche alten Weg bitte Warten", 50, 85);
				repaint();
				calculation.cleancolormap();
				setStatus("schreibe Weg bitte Warten", 50, 90);
				repaint();
				calculation.markway(Integer.parseInt(targetX.getText()), Integer.parseInt(targetY.getText()));
				setStatus("speichere Datei bitte Warten", 50, 95);
				repaint();
				calculation.outputtofile(Outputname.getText());
				externalframe.reloadeinputmap(Outputname.getText(), MainWindow.this);
				Run.enable();
				checkbutton.enable();
				SetStart.enable();
				SetTarget.enable();
				Run.setLabel("Run Waycalc");
				setStatus("Weg berechnet! siehe "+Outputname.getText()+" Neue �nderungen?", 100, 100);
				Outputname.setText(generateNewOutputname());
				repaint();
			}
		});
		Run.setForeground(Color.WHITE);
		Run.setBackground(new Color(241, 57, 83));
		Run.setBounds(25, 490, 283, 36);
		contentPane.add(Run);
		
		DistancesRun = new Button("Run Distancescalc");
		DistancesRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DistancesRun.setLabel("Calculating Distances...");
				DistancesRun.disable();
				checkbutton.disable();
				SetStart.disable();
				SetTarget.disable();
				targetX.disable();
				targetY.disable();
				setStatus("Calculating Distances please wait", 50, 30);
//				revalidate();
				repaint();
				//TODO funktioniert nicht weil der haupt Tread auf sleep ist solange action Performed active ist
				calculation.calcdistances();
				
				DistancesRun.enable();
				checkbutton.enable();
				SetStart.enable();
				SetTarget.enable();
				DistancesRun.setLabel("Run Distancescalc");
				if(SetTarget.getLabel()=="Set Target") {
					targetX.enable();
					targetY.enable();
					SetTarget.enable();
					setStatus("geben sie einen Zielpunkt an", 0, 60);
				}
				else {
					targetX.disable();
					targetY.disable();
					SetTarget.enable();
					Run.enable();
					setStatus("starten Sie die Wegberechnung", 0, 70);
				}
				repaint();
			}
		});
		DistancesRun.setForeground(Color.WHITE);
		DistancesRun.setBackground(new Color(241, 57, 83));
		DistancesRun.setBounds(25, 445, 283, 36);
		contentPane.add(DistancesRun);
		
//		Run = new Button("External Viewer");
//		Run.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ExternalViewer externalframe = new ExternalViewer(this, Inputname.getText());
//			}
//		});
//		Run.setForeground(Color.WHITE);
//		Run.setBackground(new Color(50, 220, 240));
//		Run.setBounds(25, 535, 283, 36);
//		contentPane.add(Run);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(245, 245, 245, 190));
		panel.setBounds(12, 38, 306, 80);
//		panel.isDisplayable();
		contentPane.add(panel);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(progressbarvalue);
		progressBar.setForeground(Color.BLUE);
//		progressBar.setBackground(Color.GRAY);
		progressBar.setBounds(5, 60, 295, 14);
		panel.add(progressBar);
		
		progressBar_active = new JProgressBar();
		progressBar_active.setStringPainted(true);
		progressBar_active.setValue(activeprogressbarvalue);
		progressBar_active.setForeground(Color.GREEN);
//		progressBar_aktiv.setBackground(Color.GRAY);
		progressBar_active.setBounds(5, 45, 295, 10);
		panel.add(progressBar_active);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBackground(Color.GRAY);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStatus.setBounds(15, 10, 50, 20);
		panel.add(lblStatus);
		
		StatusMesssage = new JLabel("we got you");
		StatusMesssage.setHorizontalAlignment(SwingConstants.LEFT);
		StatusMesssage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		StatusMesssage.setBackground(Color.GRAY);
		StatusMesssage.setBounds(70, 12, 220, 20);
		panel.add(StatusMesssage);
		
		JLabel Input = new JLabel("Input Karte");
		Input.setBounds(25, 129, 114, 18);
		contentPane.add(Input);
		
//		JLabel inputfound = new JLabel("#");
//		inputfound.setFont(new Font("Tahoma", Font.BOLD, 35));
//		inputfound.setBackground(Color.BLUE);
//		inputfound.setBounds(641, 83, 37, 36);
//		contentPane.add(inputfound);
		
		Inputname = new JTextField();
		Inputname.setFont(new Font("Tahoma", Font.ITALIC, 16));
		Inputname.setText("test4.png");
		Inputname.setToolTipText("exect name of the input Map\r\nHas to be stored at the root-MapNavigation-folder");
		Inputname.setBounds(25, 154, 188, 36);
		contentPane.add(Inputname);
		Inputname.setColumns(10);
		
		checkbutton = new Button("load?");
		checkbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkbutton.getLabel()=="load?") {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
						while(calculation.readimage(picture.getName())!=100) {
							setStatus("lese Bild",calculation.readimage(picture.getName()),5);
						}
						calculation.createmaps();
						calculation.outputtofile("temp.png");
						externalframe.reloadeinputmap("temp.png",MainWindow.this);
						File temp = new File("temp.png");
						temp.delete();						
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
						Inputname.disable();
						startX.enable();
						startY.enable();
						SetStart.enable();
						setStatus("geben sie einen Startpunkt an", 0, 10);
						repaint();
					}
					else {
						checkbutton.setLabel("not found");
						checkbutton.setBackground(Color.ORANGE);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 1;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
//					System.exit(1);
				}
				else if(checkbutton.getLabel()=="not found") {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
						while(calculation.readimage(picture.getName())!=100) {
							setStatus("lese Bild",calculation.readimage(picture.getName()),5);
						}
						calculation.createmaps();
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
						Inputname.disable();
						startX.enable();
						startY.enable();
						SetStart.enable();
						setStatus("geben sie einen Startpunkt an", 0, 10);
						repaint();
//						externalframe.reloadeinputmap(Inputname.getText());
					}
					else {
						checkbutton.setLabel("not found");
						checkbutton.setBackground(Color.ORANGE);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 1;
						System.out.println("changeto:" + checkedInput);
					}
				}
				else {
					if(!Inputname.isEnabled()) {
						checkbutton.setLabel("load?");		
						checkbutton.setBackground(Color.DARK_GRAY);
						checkbutton.setForeground(Color.WHITE);
						checkedInput = 0;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
						disableall();
						SetStart.setLabel("Set Start");
						SetTarget.setLabel("Set Target");
						checkbutton.enable();
						Inputname.enable();
						setStatus("geben sie eine Inputkarte an", 0, 0);
						repaint();
					}
					else {
						File picture = new File(Inputname.getText());
						if(picture.exists()) {
							while(calculation.readimage(picture.getName())!=100) {
								setStatus("lese Bild",calculation.readimage(picture.getName()),5);
							}
							calculation.createmaps();
		//						checkbutton.setLabel("load?");		
		//						checkbutton.setBackground(Color.DARK_GRAY);
		//						checkbutton.setForeground(Color.WHITE);
		//						checkedInput = 0;
							System.out.println("changeto:" + checkedInput);
							writetoFile();
//							externalframe.reloadeinputmap(Inputname.getText());
						}
						else {
							checkbutton.setLabel("not found");
							checkbutton.setBackground(Color.ORANGE);
							checkbutton.setForeground(Color.DARK_GRAY);
							checkedInput = 1;
							System.out.println("changeto:" + checkedInput);
							writetoFile();
						}
					}
				}
			}
		});
		checkbutton.setForeground(Color.WHITE);
		checkbutton.setBackground(Color.DARK_GRAY);
		checkbutton.setBounds(222, 153, 86, 36);
		contentPane.add(checkbutton);
		
		JLabel Output = new JLabel("Output Karte");
		Output.setBounds(25, 203, 114, 18);
		contentPane.add(Output);
		
		Outputname = new JTextField();
		Outputname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				File outputfile = new File(Outputname.getText());
				if(outputfile.exists()) {
					Outputname.setBackground(Color.YELLOW);
					Outputname.setToolTipText("this name is already used try any other .png");
				}
				else {
					Outputname.setBackground(Color.WHITE);
					Outputname.setToolTipText("Any not jused name .png");
				}
			}
		});
		Outputname.setFont(new Font("Tahoma", Font.ITALIC, 16));	
		Outputname.setText(generateNewOutputname());
		Outputname.setToolTipText("Any not jused name .png");
		Outputname.setColumns(10);
		Outputname.setBounds(25, 228, 283, 36);
		contentPane.add(Outputname);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(245,245,245,190));
		panel_1.setBounds(12, 283, 306, 162);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Start = new JLabel("Startposition");
		Start.setBounds(14, 4, 133, 18);
		panel_1.add(Start);
		
		startX = new JFormattedTextField();
		startX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				startX.setText(startX.getText().replaceAll("\\D+",""));
				if(Integer.parseInt(startX.getText())==0){
					startX.setText(""+(externalframe.getlastclickX()));
					startY.setText(""+(externalframe.getlastclickY()));
				}
				startState=calculation.possibleposition(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
				if(startState==-2) {
					startX.setBackground(Color.RED);
					startY.setBackground(Color.RED);
//					SetStart.disable();
					repaint();
				}
				else if(startState==-1) {
					startX.setBackground(Color.ORANGE);
					startY.setBackground(Color.ORANGE);
//					SetStart.disable();
					repaint();
				}
				else {
					startX.setBackground(Color.WHITE);
					startY.setBackground(Color.WHITE);
//					SetStart.enable();
					repaint();
				}
			}
		});
		startX.setBounds(14, 29, 133, 36);
		panel_1.add(startX);
		startX.setText("2");
		startX.setFont(new Font("Tahoma", Font.ITALIC, 16));
		startX.setToolTipText("X Coodinate");
		
		startY = new JFormattedTextField();
		startY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				startY.setText(startY.getText().replaceAll("\\D+",""));
				startState=calculation.possibleposition(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
				if(startState==-2) {
					startX.setBackground(Color.RED);
					startY.setBackground(Color.RED);
//					SetStart.disable();
					repaint();
				}
				else if(startState==-1) {
					startX.setBackground(Color.ORANGE);
					startY.setBackground(Color.ORANGE);
//					SetStart.disable();
					repaint();
				}
				else {
					startX.setBackground(Color.WHITE);
					startY.setBackground(Color.WHITE);
//					SetStart.enable();
					repaint();
				}
			}
		});
		startY.setBounds(14, 73, 133, 36);
		panel_1.add(startY);
		startY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		startY.setText("2");
		startY.setToolTipText("Y Coodinate");
		
		SetStart = new Button("Set Start");
		SetStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startState=calculation.possibleposition(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
				if(startState==-2) {
					startX.setBackground(Color.RED);
					startY.setBackground(Color.RED);
//					SetStart.disable();
					repaint();
				}
				else if(startState==-1) {
					startX.setBackground(Color.ORANGE);
					startY.setBackground(Color.ORANGE);
//					SetStart.disable();
					repaint();
				}
				else {
					startX.setBackground(Color.WHITE);
					startY.setBackground(Color.WHITE);
//					SetStart.enable();
					repaint();
				}
				if(startState == 0) {
					if(SetStart.getLabel()=="Set Start") {
						calculation.setuplocation(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
						SetStart.setLabel("Reset Start?");
						startX.disable();
						startY.disable();
						SetTarget.enable();
						DistancesRun.enable();
						if(calculation.getdistanceready()==0) {
							if(SetTarget.getLabel()=="Set Target") {
								targetX.enable();
								targetY.enable();
							}
							else {
								targetX.disable();
								targetY.disable();
							}
							setStatus("starten Sie die Distanceberechnung", 0, 20);
						}
						else {
							if(SetTarget.getLabel()=="Set Target") {
								targetX.enable();
								targetY.enable();
								SetTarget.enable();
								setStatus("geben sie einen Zielpunkt an", 0, 60);
							}
							else {
								targetX.disable();
								targetY.disable();
								SetTarget.enable();
								Run.enable();
								setStatus("starten Sie die Wegberechnung", 0, 70);
							}
						}
						repaint();
					}
					else {
						SetStart.setLabel("Set Start");
						disableall();
						SetStart.enable();
						startX.enable();
						startY.enable();
						setStatus("geben sie einen Startpunkt an", 0, 10);
						repaint();
					}
				}
				else if(startState == -1)setStatus("Startpunkt liegt auf Wand", 0, 10);
				else if(startState == -2)setStatus("Startpunkt liegt au�erhalb der Karte", 0, 10);
				else{
					setStatus("Startpunkt f�hrt zu unbekannten Fehler", 0, 10);
				}
			}
		});
		SetStart.setBounds(14, 116, 133, 36);
		panel_1.add(SetStart);
		SetStart.setForeground(Color.WHITE);
		SetStart.setBackground(Color.BLUE);
		
		JLabel Ziel = new JLabel("Targetposition");
		Ziel.setBounds(162, 4, 133, 18);
		panel_1.add(Ziel);
		
		targetX = new JFormattedTextField();
		targetX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				targetX.setText(targetX.getText().replaceAll("\\D+",""));
				if(Integer.parseInt(targetX.getText())==0){
					targetX.setText(""+(externalframe.getlastclickX()));
					targetY.setText(""+(externalframe.getlastclickY()));
				}
				targetState=calculation.possibleposition(Integer.parseInt(targetX.getText()), Integer.parseInt(targetY.getText()));
				if(targetState==-2) {
					targetX.setBackground(Color.RED);
					targetY.setBackground(Color.RED);
//					SetTarget.disable();
					repaint();
				}
				else if(targetState==-1) {
					targetX.setBackground(Color.ORANGE);
					targetY.setBackground(Color.ORANGE);
//					SetTarget.disable();
					repaint();
				}
				else {
					targetX.setBackground(Color.WHITE);
					targetY.setBackground(Color.WHITE);
//					SetTarget.enable();
					repaint();
				}
			}
		});
		targetX.setBounds(162, 29, 133, 36);
		panel_1.add(targetX);
		targetX.setFont(new Font("Tahoma", Font.ITALIC, 16));
		targetX.setText("14");
		targetX.setToolTipText("X Coodinate");
		
		targetY = new JFormattedTextField();
		targetY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				targetY.setText(targetY.getText().replaceAll("\\D+",""));
				targetState=calculation.possibleposition(Integer.parseInt(targetX.getText()), Integer.parseInt(targetY.getText()));
				if(targetState==-2) {
					targetX.setBackground(Color.RED);
					targetY.setBackground(Color.RED);
//					SetTarget.disable();
					repaint();
				}
				else if(targetState==-1) {
					targetX.setBackground(Color.ORANGE);
					targetY.setBackground(Color.ORANGE);
//					SetTarget.disable();
					repaint();
				}
				else {
					targetX.setBackground(Color.WHITE);
					targetY.setBackground(Color.WHITE);
//					SetTarget.enable();
					repaint();
				}
			}
		});
		targetY.setBounds(162, 73, 133, 36);
		panel_1.add(targetY);
		targetY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		targetY.setText("11");
		targetY.setToolTipText("Y Coodinate");
		
		
		SetTarget = new Button("Set Target");
		SetTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetState=calculation.possibleposition(Integer.parseInt(targetX.getText()), Integer.parseInt(targetY.getText()));
				if(targetState==-2) {
					targetX.setBackground(Color.RED);
					targetY.setBackground(Color.RED);
//					SetTarget.disable();
					repaint();
				}
				else if(targetState==-1) {
					targetX.setBackground(Color.ORANGE);
					targetY.setBackground(Color.ORANGE);
//					SetTarget.disable();
					repaint();
				}
				else {
					targetX.setBackground(Color.WHITE);
					targetY.setBackground(Color.WHITE);
//					SetTarget.enable();
					repaint();
				}
				if(targetState == 0) {
					if(SetTarget.getLabel()=="Set Target") {
						SetTarget.setLabel("Reset Target?");
						targetX.disable();
						targetY.disable();
						SetStart.enable();
						if(calculation.getdistanceready()==0) {
							setStatus("starten Sie die Distanceberechnung", 0, 30);
							DistancesRun.enable();
						}
						else {
							Run.enable();
							setStatus("starten Sie die Wegberechnung", 0, 70);
						}
						repaint();
					}
					else {
						SetTarget.setLabel("Set Target");
						disableall();
						SetTarget.enable();
						targetX.enable();
						targetY.enable();
						if(calculation.getdistanceready()==0) {
							setStatus("geben sie einen Zielpunkt an", 0, 20);
						}
						else {
							setStatus("geben sie einen Zielpunkt an", 0, 60);
						}
						repaint();
					}
				}
				else if(calculation.getdistanceready()==0) {
					if(targetState == -1)setStatus("Zielpunkt liegt auf Wand", 0, 20);
					else if(targetState == -2)setStatus("Zielpunkt liegt au�erhalb der Karte", 0, 20);
					else{
						setStatus("Zielpunkt f�hrt zu unbekannten Fehler", 0, 20);
					}
				}
				else {
					if(targetState == -1)setStatus("Zielpunkt liegt auf Wand", 0, 60);
					else if(targetState == -2)setStatus("Zielpunkt liegt au�erhalb der Karte", 0, 60);
					else{
						setStatus("Zielpunkt f�hrt zu unbekannten Fehler", 0, 60);
					}
					
				}
			}
		});
		SetTarget.setBounds(162, 116, 133, 36);
		panel_1.add(SetTarget);
		SetTarget.setForeground(Color.WHITE);
		SetTarget.setBackground(Color.BLUE);
		
//		ExternalViewer externalframe = new ExternalViewer(this);		
//		externalframe.setVisible(true);
		
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				finallydone = 1;
				writetoFile();
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(302, 0, 37, 27);
		contentPane.add(lbl_close);

		JLabel lbl_minimize = new JLabel("_");
		lbl_minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setState(Frame.ICONIFIED);//TODO change Wrong place
				externalframe.setState(Frame.ICONIFIED);
			}
		});
		lbl_minimize.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_minimize.setForeground(new Color(241, 57, 83));
		lbl_minimize.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_minimize.setBounds(282, 0, 37, 27);
		contentPane.add(lbl_minimize);
		
		JLabel lblNewLabel = new JLabel(DEFINES.VERSION + " ready to go");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds(5, 0, 260, 27);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		contentPane.add(label);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            MainWindow.this.setLocation(x - xx, y - xy);
	            externalframe.setLocation(x+ MainWindow.this.getWidth() - xx, y -xy);
			}
		});
		label.setBounds(0, 0, 335, 30);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(MainWindow.class.getResource("/designe/imgsource/picture1.jpg")));
		
		JLabel background = new JLabel("");
		background.setBounds(-112, -48, 600, 600);
		contentPane.add(background);
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setIcon(new ImageIcon(MainWindow.class.getResource("/designe/imgsource/LogoWaterprint.png")));
	
	}
	
	public void disableall() {
//		panel.disable();
//		panel_1.disable();
		Inputname.disable();
//		Outputname.disable();
		startX.disable();
		startY.disable();
		targetY.disable();
		targetX.disable();
		progressBar.disable();
		progressBar_active.disable();
		Run.disable();
		DistancesRun.disable();
//		checkbutton.disable();
		SetStart.disable();
		SetTarget.disable();
	}
	
	public void setStatus(String statusmessage, int activepersentage, int persentage) {
		StatusMesssage.setText(statusmessage);
		progressBar.setValue(persentage);
		progressBar_active.setValue(activepersentage);
		contentPane.repaint();
	}

	public String generateNewOutputname() {
		boolean newfound = true;
		File outputfile = new File(newoutputname);
		System.out.println("Test1");
		for (int i = 0; newfound == true; i++) {
			System.out.println("Test2: " + i);
			newoutputname="output"+ Integer.toString(i) + ".png";
			outputfile = new File(newoutputname);
			newfound=outputfile.exists();
		}
		System.out.println("#"+newoutputname+"#");
		System.out.println("Test3");
		Outputname.setText(newoutputname);
		return newoutputname;
	}
	
	public int getInputfound() {
		System.out.println("checked:" + checkedInput);	
		return checkedInput;
	}
	public String getInputname() {
		return Inputname.getText();
	}
	public String getOutputname() {
		return Outputname.getText();
	}
	public int getStartX() {
		return Integer.parseInt(startX.getText());
	}
	public int getStartY() {
		return Integer.parseInt(startY.getText());
	}
	public int getTagetX() {
		return Integer.parseInt(targetX.getText());
	}
	public int getTagetY() {
		return Integer.parseInt(targetY.getText());
	}
	public void writetoFile() {
		try {
			writingattempt++;
			File transferbuffer = new File("transferbuffer.tmp");
			FileWriter fileWriter = new FileWriter(transferbuffer);	//Textdatei leeren
			fileWriter.write("");
			fileWriter.close();
			PrintStream fileStream = new PrintStream(transferbuffer);
			fileStream.println(writingattempt);
			fileStream.println(getInputfound());
			fileStream.println(startset);//TODO add get
			fileStream.println(targetset);
			fileStream.println(calcingactive);//calcingon war auch nicht schlecht
			fileStream.println(getInputname());
			fileStream.println(getOutputname());
			fileStream.println(getStartX());
			fileStream.println(getStartY());
			fileStream.println(getTagetX());
			fileStream.println(getTagetY());
			fileStream.print(finallydone);
			fileStream.flush();
			fileStream.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
