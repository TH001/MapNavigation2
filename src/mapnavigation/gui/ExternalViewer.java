package mapnavigation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExternalViewer extends JFrame {

	private JPanel contentPane;
	private JPanel mappanel;
	private JLabel picturein;
	private JLabel pictureout;
	
	private ImageIcon iconin;
	
	private String picture="nopicture.png";
	
	private int lastclickX;
	private int lastclickY;
	
	private double scalefactor = 1;
	
//	public Graphics map(String picture) {
//		ImageIcon mapimg = new ImageIcon(picture);
//		mapimg.paintIcon(this, map(picture), 300, 300);
//		return null;
//	}

	/**
	 * Create the frame.
	 */
	public ExternalViewer(MainWindow mainwindow) {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(mainwindow);
		iconin = new ImageIcon(MainWindow.class.getResource("/designe/imgsource/nopicture.png"));
		scalefactor=mainwindow.getHeight()/iconin.getIconHeight();
		iconin.setImage(iconin.getImage().getScaledInstance(iconin.getIconWidth()*mainwindow.getHeight()/iconin.getIconHeight(),mainwindow.getHeight(),Image.SCALE_DEFAULT));
		System.out.println(scalefactor);
		
		setlastclick(-1, -1);
		
		setBounds(mainwindow.getWidth()+100, 100, iconin.getIconWidth(), iconin.getIconHeight()/*mainwindow.getHeight()*/);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		mappanel = new JPanel();
//		mappanel.paint(map(picture));
		mappanel.setLocation(0,0);
		contentPane.add(mappanel);
		
		picturein = new JLabel("");
		picturein.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setlastclick((int)(arg0.getX()/scalefactor),(int)(arg0.getY()/scalefactor));
				System.out.println(getlastclickX()+"|"+getlastclickY());
			}
		});
		picturein.setLocation(0, 0);
		picturein.setIcon(iconin);
//		reloadeinputmap(""+MainWindow.class.getResource("/designe/imgsource/nopicture.png"));
		mappanel.add(picturein);
		
	}
	
//	public void reloadeinputmap(MainWindow mainwindow) {
//		picturein.setIcon(new ImageIcon(mainwindow.Inputname.getText()));
//		repaint();
//	}
	public void reloadeinputmap(String filename,MainWindow mainwindow) {
		iconin = new ImageIcon(filename);
		scalefactor=mainwindow.getHeight()/iconin.getIconHeight();
		iconin.setImage(iconin.getImage().getScaledInstance(iconin.getIconWidth()*mainwindow.getHeight()/iconin.getIconHeight(),mainwindow.getHeight(),Image.SCALE_DEFAULT));
		picturein.setIcon(iconin);
		
		setBounds(mainwindow.getWidth()+100, 100, iconin.getIconWidth(), iconin.getIconHeight());
		repaint();
	}
	
	public void setlastclick(int tempX, int tempY) {
		lastclickX=tempX;
		lastclickY=tempY;
	}
	
	public void resetlastclick() {
		lastclickX=-1;
		lastclickY=-1;
	}
	
	public int getlastclickX() {
		return lastclickX;
	}
	public int getlastclickY() {
		return lastclickY;
	}

}
