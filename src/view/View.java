package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import exceptions.FullHandException;

public class View extends JFrame implements ActionListener {


	private ViewListener listener;

	private ChooseHero chooseHero;
	private GameView gameView;

	private String hero1 = "";
	private String hero2 = "";

	public View() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBounds(0, 0, screenSize.width, screenSize.height);
		setTitle("Choose first hero");
		chooseHero = new ChooseHero();

		ArrayList<JButton> heros = chooseHero.getHeros();

		for (JButton button : heros) {
			button.addActionListener(this);
		}

		add(chooseHero);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e){
		JButton b = (JButton) e.getSource();

		if (hero1.equals("")) {
			hero1 = b.getActionCommand();
			setTitle("Choose second hero");
		}
		else if (hero2.equals("")) {
			hero2 = b.getActionCommand();
			remove(chooseHero);

			
//			gameView = new GameView(hero1, hero2);
			
			
//			add(gameView);
			setTitle("Hearthstone");
			try {
				listener.startGame(hero1, hero2);
			} catch (FullHandException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
			
			revalidate();
			repaint();
		}
	}

	public void setListener(ViewListener listener) {
		this.listener = listener;
	}
	
	public GameView getGameView() {
		return gameView;
	}
	
	public void setGameView(GameView g) {
		this.gameView = g;
	}
	

}
