package mapnavigation.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;

import mapnavigation.calculation.Algorithm;


public class MainWindow extends JFrame {
	
	public static final long serialVersionUID = 1;
	public JPanel contentPane;
	public JTextField Inputname;
	public JTextField Outputname;
	public JFormattedTextField startX;
	public JFormattedTextField startY;
	public JFormattedTextField targetY;
	public JFormattedTextField targetX;
	public JProgressBar progressBar;
	public JProgressBar progressBar_active;
	
	private int xx,xy;
	
	private int activeprogressbarvalue=75;
	private int progressbarvalue=50;
	
	String newoutputname = "out.png";
	
	private int checkedInput;	
	private int writingattempt = 0;
	private int startset = 0;
	private int targetset = 0;
	private int calcingactive = 0;
	private int finallydone = 0;
	/**
	 * Create the frame.
	 */
	public MainWindow(Algorithm calculation) {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335*1, 525*1);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Button Run = new Button("Run Waycalc");
		Run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(calcingactive == 0) {
					calcingactive = 1;
				}
				calculation.doeveything();
				writetoFile();
			}
		});
		
		JPanel out_panel_0 = new JPanel();
		out_panel_0.setBounds(335, 0, 314, 450);
		contentPane.add(out_panel_0);
		
		JPanel out_panel_1 = new JPanel();
		out_panel_1.setBounds(0, 525, 581, 99);
		contentPane.add(out_panel_1);
		Run.setForeground(Color.WHITE);
		Run.setBackground(new Color(241, 57, 83));
		Run.setBounds(25, 465, 283, 36);
		contentPane.add(Run);
		
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
//				this.frame.setState(Frame.ICONIFIED);//TODO change Wrong place
			}
		});
		lbl_minimize.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_minimize.setForeground(new Color(241, 57, 83));
		lbl_minimize.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_minimize.setBounds(282, 0, 37, 27);
		contentPane.add(lbl_minimize);
		
		JLabel lblNewLabel = new JLabel("MapNavigation V1.2 ready to go");
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
			}
		});
		label.setBounds(0, 0, 335, 30);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(MainWindow.class.getResource("/designe/imgsource/picture1.jpg")));
		
		JPanel panel = new JPanel();
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
		
		JLabel StatusMesssage = new JLabel("we got you");
		StatusMesssage.setHorizontalAlignment(SwingConstants.LEFT);
		StatusMesssage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		StatusMesssage.setBackground(Color.GRAY);
		StatusMesssage.setBounds(70, 12, 200, 20);
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
		
		Button checkbutton = new Button("load?");
		checkbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkbutton.getLabel()=="load?") {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
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
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
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
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
//						checkbutton.setLabel("load?");		
//						checkbutton.setBackground(Color.DARK_GRAY);
//						checkbutton.setForeground(Color.WHITE);
//						checkedInput = 0;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
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
		
		JPanel panel_1 = new JPanel();
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
				if(0>Integer.parseInt(startX.getText())||Integer.parseInt(startX.getText())>1/*pixelX*/) {
					startX.setBackground(Color.ORANGE);
				}
				else {
					startX.setBackground(Color.WHITE);
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
				if(0>Integer.parseInt(startY.getText())||Integer.parseInt(startY.getText())>1/*pixelY*/) {
					startY.setBackground(Color.ORANGE);
				}
				else {
					startY.setBackground(Color.WHITE);
				}
			}
		});
		startY.setBounds(14, 73, 133, 36);
		panel_1.add(startY);
		startY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		startY.setText("2");
		startY.setToolTipText("Y Coodinate");
		
		Button SetStart = new Button("Set Start");
		SetStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(startset==0) {
					startset = 1;
					calcingactive = 0;
				}
				writetoFile();
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
				if(0>Integer.parseInt(targetX.getText())||Integer.parseInt(targetX.getText())>1/*pixelX*/) {
					targetX.setBackground(Color.ORANGE);
				}
				else {
					targetX.setBackground(Color.WHITE);
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
				if(0>Integer.parseInt(targetY.getText())||Integer.parseInt(targetY.getText())>1/*pixelY*/) {
					targetY.setBackground(Color.ORANGE);
				}
				else {
					targetY.setBackground(Color.WHITE);
				}
			}
		});
		targetY.setBounds(162, 73, 133, 36);
		panel_1.add(targetY);
		targetY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		targetY.setText("11");
		targetY.setToolTipText("Y Coodinate");
		
		
		Button SetTarget = new Button("Set Target");
		SetTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(targetset == 0) {
					targetset = 1;
				}
				writetoFile();
			}
		});
		SetTarget.setBounds(162, 116, 133, 36);
		panel_1.add(SetTarget);
		SetTarget.setForeground(Color.WHITE);
		SetTarget.setBackground(Color.BLUE);
		
		
		JLabel background = new JLabel("");
		background.setBounds(-112, -48, 600, 600);
		contentPane.add(background);
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setIcon(new ImageIcon(MainWindow.class.getResource("/designe/imgsource/LogoWaterprint.png")));
		
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