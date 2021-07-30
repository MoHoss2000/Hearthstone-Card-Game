package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.CardView;
import view.GameView;
import view.HeroView;
import view.View;
import view.ViewListener;

public class Controller implements ActionListener, GameListener, ViewListener {

	View view;
	Game game;
	
	ArrayList<CardView> field1 = new ArrayList<CardView>();
	ArrayList<CardView> hand1 = new ArrayList<CardView>();
	ArrayList<CardView> field2 = new ArrayList<CardView>();
	ArrayList<CardView> hand2 = new ArrayList<CardView>();
	
	Hero hero1;
	Hero hero2;
	Hero currenthero;
	Hero opponent;

	Object selected;

	public Controller() {
		view = new View();
		view.setListener(this);

	}

	public void startGame(String h1, String h2) throws IOException, CloneNotSupportedException, FullHandException {

		switch (h1) {
		case "Hunter":
			hero1 = new Hunter();
			break;
		case "Mage":
			hero1 = new Mage();
			break;
		case "Paladin":
			hero1 = new Paladin();
			break;
		case "Priest":
			hero1 = new Priest();
			break;
		default:
			hero1 = new Warlock();
			break;
		}

		switch (h2) {
		case "Hunter":
			hero2 = new Hunter();
			break;
		case "Mage":
			hero2 = new Mage();
			break;
		case "Paladin":
			hero2 = new Paladin();
			break;
		case "Priest":
			hero2 = new Priest();
			break;
		default:
			hero2 = new Warlock();
			break;
		}

		game = new Game(hero1, hero2);
		game.setListener(this);

		GameView gameView = new GameView();
		view.add(gameView);
		view.setGameView(gameView);
		updateHand();
		updateStats();
		view.getGameView().getEndTurn().addActionListener(this);
		view.getGameView().getView1().getAttackMe().addActionListener(this);
		view.getGameView().getView2().getAttackMe().addActionListener(this);

		view.getGameView().getView1().getUseHeroPower().addActionListener(this);
		view.getGameView().getView2().getUseHeroPower().addActionListener(this);

		view.getGameView().getView1().getAttackMe().setEnabled(false);
		view.getGameView().getView2().getAttackMe().setEnabled(false);

		view.revalidate();
		view.repaint();

	}

	public void updateField() {
		currenthero = game.getCurrentHero();
		opponent = game.getOpponent();
		field1.clear();
		field2.clear();
		view.getGameView().getView1().getField().removeAll();
		view.getGameView().getView2().getField().removeAll();

		for (int i = 0; i < hero1.getField().size(); i++) {
			Card card = hero1.getField().get(i);
			CardView cardView = new CardView();
			cardView.getButton().addActionListener(this);
			field1.add(cardView);
			view.getGameView().getView1().getField().add(cardView);
			cardView.getButton().setActionCommand("Use Minion");
			cardView.getButton().setText("Use Minion");

			String s = "";
			s += card.getName();
			s += "\nManacost: " + card.getManaCost();
			s += "\nRarity: " + card.getRarity();
			if (card instanceof Minion) {
				s += "\nAttack: " + ((Minion) card).getAttack();
				s += "\nCurrent HP : " + ((Minion) card).getCurrentHP() + "\n";
				if (((Minion) card).isTaunt()) {
					s += "\nTaunt";
				}

				if (((Minion) card).isDivine()) {
					s += "\nDivine";
				}

				if (((Minion) card).isCharge()) {
					s += "\nCharge";
				}

				if (((Minion) card).isSleeping()) {
					s += "\n\nZzZzZzZzZzZzZ";
				}

			}
			cardView.setCardInfo(s);
		}
		for (int i = 0; i < hero2.getField().size(); i++) {
			Card card = hero2.getField().get(i);
			CardView cardView = new CardView();
			cardView.getButton().addActionListener(this);
			field2.add(cardView);
			view.getGameView().getView2().getField().add(cardView);
			cardView.getButton().setActionCommand("Use Minion");
			cardView.getButton().setText("Use Minion");

			String s = "";
			s += card.getName();
			s += "\nManacost: " + card.getManaCost();
			s += "\nRarity: " + card.getRarity();
			if (card instanceof Minion) {
				s += "\nAttack: " + ((Minion) card).getAttack();
				s += "\nCurrent HP : " + ((Minion) card).getCurrentHP() + "\n";

				if (((Minion) card).isTaunt()) {
					s += "\nTaunt";
				}

				if (((Minion) card).isDivine()) {
					s += "\nDivine";
				}

				if (((Minion) card).isCharge()) {
					s += "\nCharge";
				}

				if (((Minion) card).isSleeping()) {
					s += "\n\nZzZzZzZzZzZzZ";
				}
			}

			cardView.setCardInfo(s);
		}

		ArrayList<CardView> opponentField;
		if (opponent == hero1) {

			opponentField = field1;

		} else {

			opponentField = field2;

		}

		for (CardView cardView : opponentField) {
			cardView.getButton().setEnabled(false);
		}

		view.revalidate();
		view.repaint();

	}

	public void updateHand() {
		currenthero = game.getCurrentHero();
		opponent = game.getOpponent();
		hand1.clear();
		hand2.clear();
		view.getGameView().getView1().getHand().removeAll();
		view.getGameView().getView2().getHand().removeAll();
		for (int i = 0; i < hero1.getHand().size(); i++) {
			Card card = hero1.getHand().get(i);
			CardView cardView = new CardView();
			cardView.getButton().addActionListener(this);
			hand1.add(cardView);
			view.getGameView().getView1().getHand().add(cardView);

			String s = "";
			s += card.getName();
			s += "\nManacost: " + card.getManaCost();
			s += "\nRarity: " + card.getRarity();
			if (card instanceof Minion) {
				s += "\nAttack: " + ((Minion) card).getAttack();
				s += "\nCurrent HP : " + ((Minion) card).getCurrentHP()+ "\n";
				if (((Minion) card).isTaunt()) {
					s += "\nTaunt";
				}

				if (((Minion) card).isDivine()) {
					s += "\nDivine";
				}

				if (((Minion) card).isCharge()) {
					s += "\nCharge";
				}
			}
			cardView.setCardInfo(s);
		}
		for (int i = 0; i < hero2.getHand().size(); i++) {
			Card card = hero2.getHand().get(i);
			CardView cardView = new CardView();
			cardView.getButton().addActionListener(this);
			hand2.add(cardView);
			view.getGameView().getView2().getHand().add(cardView);

			String s = "";
			s += card.getName();
			s += "\nManacost: " + card.getManaCost();
			s += "\nRarity: " + card.getRarity();
			if (card instanceof Minion) {
				s += "\nAttack: " + ((Minion) card).getAttack();
				s += "\nCurrent HP : " + ((Minion) card).getCurrentHP() + "\n";

				if (((Minion) card).isTaunt()) {
					s += "\nTaunt";
				}

				if (((Minion) card).isDivine()) {
					s += "\nDivine";
				}

				if (((Minion) card).isCharge()) {
					s += "\nCharge";
				}
			}

			cardView.setCardInfo(s);
		}
		if (opponent == hero1) {
			view.getGameView().getView1().getHand().setVisible(false);
			view.getGameView().getView2().getHand().setVisible(true);
		} else {
			view.getGameView().getView2().getHand().setVisible(false);
			view.getGameView().getView1().getHand().setVisible(true);
		}
		view.revalidate();
		view.repaint();

	}

	public void updateStats() {
		String s1 = "";
		s1 += hero1.getName();
		s1 += "\nCurrentHP: " + hero1.getCurrentHP();
		s1 += "\nMana: " + hero1.getCurrentManaCrystals() + "/" + hero1.getTotalManaCrystals();
		s1 += "\nCards in Deck : " + hero1.getDeck().size();
		s1 += "\nCards in Hand: " + hero1.getHand().size();
		view.getGameView().getView1().setStats(s1);
		String s2 = "";
		s2 += hero2.getName();
		s2 += "\nCurrentHP: " + hero2.getCurrentHP();
		s2 += "\nMana: " + hero2.getCurrentManaCrystals() + "/" + hero2.getTotalManaCrystals();
		s2 += "\nCards in Deck : " + hero2.getDeck().size();
		s2 += "\nCards in Hand: " + hero2.getHand().size();
		view.getGameView().getView2().setStats(s2);

		if (opponent == hero1) {
			view.getGameView().getView1().getUseHeroPower().setEnabled(false);
			view.getGameView().getView2().getUseHeroPower().setEnabled(true);

		} else {
			view.getGameView().getView2().getUseHeroPower().setEnabled(false);
			view.getGameView().getView1().getUseHeroPower().setEnabled(true);

		}
		view.getGameView().getView1().getAttackMe().setEnabled(false);
		view.getGameView().getView2().getAttackMe().setEnabled(false);

		view.getGameView().getView1().getUseHeroPower().setText("Use Power");
		view.getGameView().getView2().getUseHeroPower().setText("Use Power");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		currenthero = game.getCurrentHero();
		opponent = game.getOpponent();

		JButton b = (JButton) e.getSource();

		ArrayList<CardView> currentHand;
		ArrayList<CardView> currentField;
		ArrayList<CardView> opponentHand;
		ArrayList<CardView> opponentField;

		HeroView currentView;
		HeroView opponentView;

		if (hero1 == currenthero) {
			currentView = view.getGameView().getView1();
			opponentView = view.getGameView().getView2();
			currentHand = hand1;
			currentField = field1;
			opponentHand = hand2;
			opponentField = field2;

		} else {
			currentView = view.getGameView().getView2();
			opponentView = view.getGameView().getView1();
			currentHand = hand2;
			currentField = field2;
			opponentHand = hand1;
			opponentField = field1;

		}

		if (b.getActionCommand().equals("End Turn")) {
			try {
				game.endTurn();
			} catch (FullHandException e1) {
				// TODO Auto-generated catch block
				showFullHandError(e1.getMessage(), e1.getBurned());
			} catch (CloneNotSupportedException e1) {
				showError(e1.getMessage());
			}
			selected = null;
		}

		if (selected == null) {
			switch (b.getActionCommand()) {
			case "Use Minion":
				selected = viewToEngine((CardView) b.getParent());

				opponentView.getAttackMe().setEnabled(true);

				for (CardView cardView : opponentField) {
					cardView.getButton().setEnabled(true);
				}

				for (CardView cardView : currentField) {
					cardView.getButton().setEnabled(false);
				}

				for (CardView cardView : currentHand) {
					cardView.getButton().setEnabled(false);
				}

				currentView.getUseHeroPower().setEnabled(false);
				b.setEnabled(true);
				b.setText("Cancel Selection");
				return;

			case "Play Card":
				Card card = viewToEngine((CardView) b.getParent());
				if (card instanceof Minion) {
					try {
						currenthero.playMinion((Minion) card);
					} catch (NotYourTurnException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());

					} catch (NotEnoughManaException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullFieldException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());

					}
				}

				else if (card instanceof Spell) {
					Spell spell = (Spell) card;
					if (spell instanceof FieldSpell) {
						try {
							currenthero.castSpell((FieldSpell) spell);
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							// TODO Auto-generated catch block
							showError(e1.getMessage());
						}
					}

					else if (spell instanceof AOESpell) {
						try {
							currenthero.castSpell((AOESpell) spell, opponent.getField());
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							// TODO Auto-generated catch block
							showError(e1.getMessage());
						}
					}

					else {
						if (spell instanceof HeroTargetSpell) {
							opponentView.getAttackMe().setEnabled(true);
							currentView.getAttackMe().setEnabled(true);
						}

						for (CardView cardView : opponentField) {
							cardView.getButton().setEnabled(true);
						}

						for (CardView cardView : currentHand) {
							cardView.getButton().setEnabled(false);
						}

						b.setEnabled(true);
						b.setText("Cancel Selection");
						currentView.getUseHeroPower().setEnabled(false);

						selected = spell;
						return;
					}

				}
				break;

			case "Use Power":
				if (currenthero instanceof Mage || currenthero instanceof Priest) {
					view.getGameView().getView1().getAttackMe().setEnabled(true);
					view.getGameView().getView2().getAttackMe().setEnabled(true);

					for (CardView cardView : currentHand) {
						cardView.getButton().setEnabled(false);
					}

					for (CardView cardView : opponentField) {
						cardView.getButton().setEnabled(true);
					}
					b.setEnabled(true);
					b.setText("Cancel Selection");

					selected = currenthero;
					return;

				} else {
					try {
						currenthero.useHeroPower();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullFieldException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						showFullHandError(e1.getMessage(), e1.getBurned());
					}
				}

				break;

			}
		} else if (selected instanceof Minion) {

			switch (b.getActionCommand()) {
			case "Use Minion":
				Minion attacked = (Minion) viewToEngine((CardView) b.getParent());

				if (attacked == (Minion) selected) {
					selected = null;
					updateField();
					updateHand();
					updateStats();
					return;
				}

				try {
					currenthero.attackWithMinion((Minion) selected, attacked);
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
						| NotSummonedException e1) {
					// TODO Auto-generated catch block
					showError(e1.getMessage());
				}

				selected = null;
				break;

			case "Attack me":
				try {
					currenthero.attackWithMinion((Minion) selected, opponent);
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
						| InvalidTargetException e1) {
					// TODO Auto-generated catch block
					showError(e1.getMessage());
				}
				selected = null;
				break;

			}

		} else if (selected instanceof Spell) {
			Spell spell = (Spell) selected;
			selected = null;
			if(b.getActionCommand().equals("Play Card")) {
				updateHand();
				updateField();
				updateStats();
				return;
				
			}
			if (spell instanceof MinionTargetSpell) {
				if (b.getActionCommand().equals("Use Minion")) {
					Minion castedOn = (Minion) viewToEngine((CardView) b.getParent());
					try {
						currenthero.castSpell((MinionTargetSpell) spell, castedOn);
					} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					}
				}
			}

			if (spell instanceof LeechingSpell) {
				Minion castedOn = (Minion) viewToEngine((CardView) b.getParent());
				try {
					currenthero.castSpell((LeechingSpell) spell, castedOn);
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					// TODO Auto-generated catch block
					showError(e1.getMessage());
				}
			}

			if (spell instanceof HeroTargetSpell) {
				Hero castedOn;

				if (b.getActionCommand().equals("Attack me")) {
					if (currentView.getAttackMe() == b) {
						castedOn = currenthero;
					} else
						castedOn = opponent;

					try {
						currenthero.castSpell((HeroTargetSpell) spell, castedOn);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					}
				}
			}

		} else if (selected instanceof Hero) {
			selected = null;
			switch (b.getActionCommand()) {
			case "Use Power":
				
				updateStats();
				updateField();
				updateHand();
				return;

			case "Use Minion":
				Minion castedOn = (Minion) viewToEngine((CardView) b.getParent());
				if (currenthero instanceof Mage) {
					try {
						((Mage) currenthero).useHeroPower(castedOn);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullFieldException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						showFullHandError(e1.getMessage(), e1.getBurned());
					}
				} else {
					try {
						((Priest) currenthero).useHeroPower(castedOn);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullFieldException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						showFullHandError(e1.getMessage(), e1.getBurned());
					}
				}

				break;

			case "Attack me":
				Hero castedOn1;

				if (currentView.getAttackMe() == b) {
					castedOn1 = currenthero;
				} else
					castedOn1 = opponent;

				if (currenthero instanceof Mage) {
					try {
						((Mage) currenthero).useHeroPower(castedOn1);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullFieldException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						showFullHandError(e1.getMessage(), e1.getBurned());
					}
				} else {
					try {
						((Priest) currenthero).useHeroPower(castedOn1);
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullFieldException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						showError(e1.getMessage());
					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						showFullHandError(e1.getMessage(), e1.getBurned());
					}

				}

				break;

			}
		}

		updateHand();
		updateStats();
		updateField();

	}

	public Card viewToEngine(CardView cardView) {
		int index = -1;

		if (hand1.contains(cardView)) {
			index = hand1.indexOf(cardView);
			return hero1.getHand().get(index);
		} else if (hand2.contains(cardView)) {
			index = hand2.indexOf(cardView);
			return hero2.getHand().get(index);
		}

		else if (field1.contains(cardView)) {
			index = field1.indexOf(cardView);
			return hero1.getField().get(index);
		}

		else {
			index = field2.indexOf(cardView);
			return hero2.getField().get(index);
		}

	}

	public void showFullHandError(String e, Card card) {

		CardView cardView = new CardView();

		String s = "";
		s += card.getName();
		s += "\nManacost: " + card.getManaCost();
		s += "\nRarity: " + card.getRarity();
		if (card instanceof Minion) {
			s += "\nAttack: " + ((Minion) card).getAttack();
			s += "\nCurrent HP : " + ((Minion) card).getCurrentHP() + "\n";
			if (((Minion) card).isTaunt()) {
				s += "\nTaunt";
			}

			if (((Minion) card).isDivine()) {
				s += "\nDivine";
			}

			if (((Minion) card).isCharge()) {
				s += "\nCharge";
			}
		}

		cardView.setCardInfo(s);

		JOptionPane.showMessageDialog(view, cardView, e, JOptionPane.NO_OPTION);

	}

	public void showError(String e) {

		JOptionPane.showMessageDialog(view, e);

	}

	@Override
	public void onGameOver() {
		String winner;

		if (currenthero.getCurrentHP() <= 0) {
			winner = opponent.getName();
		} else
			winner = currenthero.getName();

		updateStats();
		JOptionPane.showMessageDialog(view, "The winner is " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);

		System.exit(0);

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Controller controller = new Controller();

	}

}
