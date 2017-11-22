package test;

import java.awt.event.*;

import javax.swing.JFrame;

import graphiques.MainFrame;

public class Test {
	public static void main(String[] args){
		testDeplacementHero();
	
	}
	
	private static void testDeplacementHero() {
		final MainFrame frame = new MainFrame();
		
		frame.pack();
		frame.setVisible(true);

	}

}
