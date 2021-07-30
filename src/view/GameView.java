package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exceptions.NotEnoughManaException;

public class GameView extends JPanel{


	private HeroView view1 = new HeroView();
	private HeroView view2 = new HeroView();
	
	private JButton endTurn = new JButton("End Turn");
	
	public GameView() {
		setBackground(Color.yellow);
		setLayout(new BorderLayout());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = new Dimension(screenSize.width - 150, screenSize.height/2);
		view1.setPreferredSize(d);
		view2.setPreferredSize(d);
		
		add(view1, BorderLayout.NORTH);
		add(view2, BorderLayout.CENTER);
		
		view1.setBackground(Color.LIGHT_GRAY);
		view2.setBackground(Color.LIGHT_GRAY);
		
		add(endTurn, BorderLayout.EAST);
		revalidate();
		repaint();
		
	
	}
	
	public HeroView getView1() {
		return view1;
	}
	public HeroView getView2(){
		return view2;
	}
	public JButton getEndTurn(){
		return endTurn;
	}
	
}
