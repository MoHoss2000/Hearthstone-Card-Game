package view;

import java.io.IOException;

import exceptions.FullHandException;

public interface ViewListener {
	public void startGame(String hero1, String hero2) throws IOException, CloneNotSupportedException, FullHandException;
}
