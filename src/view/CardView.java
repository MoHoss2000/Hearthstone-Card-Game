package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CardView extends JPanel{

	private JTextArea cardInfo;
	private JButton selectCard;
	
	public CardView() {
		setPreferredSize(new Dimension(145,195));
//		setPreferredSize(new Dimension(105,140));
	
		setLayout(new BorderLayout());
		
		selectCard = new JButton("Play Card");
		cardInfo = new JTextArea("Card info");
		add(cardInfo, BorderLayout.CENTER);
		add(selectCard, BorderLayout.SOUTH);
		cardInfo.setEditable(false);
		cardInfo.setFont(new Font("Arial", Font.PLAIN, 14));

	}
	
	public void setCardInfo(String s) {
		cardInfo.setText(s);

	}
	public JButton getButton(){
		return selectCard;
	}

	public String getCardInfo() {
		return cardInfo.getText();
	}

}
