package test;

import java.awt.event.*;

import javax.swing.JFrame;

import graphiques.MainFrame;
import personnage.Hero;
import principal.Monde;

public class Test {
	public static void main(String[] args){
		testDeplacementHero();
	
	}
	
	private static void testDeplacementHero() {
		final Monde monde = new Monde();
		final MainFrame frame = new MainFrame(monde);
		
		frame.pack();
		frame.setVisible(true);

	}

}
