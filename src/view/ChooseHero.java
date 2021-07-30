package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ChooseHero extends JPanel{
	
	
	private ArrayList<JButton> heros = new ArrayList<JButton>();
	
	
	public ChooseHero() {
		setLayout(new GridLayout());
		
		JButton hunter = new JButton("Hunter");
		JButton mage = new JButton("Mage");
		JButton paladin = new JButton("Paladin");
		JButton priest = new JButton("Priest");
		JButton warlock = new JButton("Warlock");
		
		add(hunter);
		add(mage);
		add(paladin);
		add(priest);
		add(warlock);
	
		heros.add(hunter);
		heros.add(mage);
		heros.add(paladin);
		heros.add(priest);
		heros.add(warlock);
		
		for (JButton jButton : heros) {
			 try {
				 	Icon icon = new ImageIcon("src/view/heroes/" + jButton.getActionCommand() +  ".png");
				    jButton.setIcon(icon);
				    jButton.setForeground(Color.white);
				    jButton.setBackground(Color.black);
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
		}
	}
	
	public ArrayList<JButton> getHeros(){
		return heros;
		
	}

}
