package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HeroView extends JPanel {

	private JTextArea stats;
	private JButton usePower;
	private JButton attackMe;
	private JPanel hand;
	private JPanel field;

	public HeroView() {
		setLayout(null);
		
		
		
		stats = new JTextArea(5,20);
		usePower = new JButton("Use Power");
		stats.setBounds(10, 10, 150, 300);
		usePower.setBounds(10, 320, 150,50);

		attackMe = new JButton("Attack me");
		attackMe.setBounds(10, 400, 150, 50);
		
		
		hand = new JPanel();
		hand.setBackground(Color.black);
		hand.setBounds(200, 10, 1500, 200);
		
		
		field = new JPanel();
		field.setBackground(Color.darkGray);
		field.setPreferredSize(new Dimension(500,100));
		field.setBounds(375, 250, 1050, 200);
		
		stats.setEditable(false);
		stats.setFont(new Font("Arial", Font.PLAIN, 14));
		
		add(stats);
		add(usePower);
		add(hand);
		add(attackMe);
		add(field);
	}
	
	public JPanel getHand() {
		return hand;
	}
	public void setStats(String s){
		stats.setText(s);
	}
	public JPanel getField(){
		return field;
	}
	
	public JButton getUseHeroPower() {
		return usePower;
	}
	
	public JButton getAttackMe() {
		return attackMe;
	}

}
