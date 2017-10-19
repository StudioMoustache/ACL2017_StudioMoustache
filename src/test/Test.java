package test;

import java.awt.event.*;

import javax.swing.JFrame;

import personnage.Hero;
import principal.Monde;

public class Test {
	public static void main(String[] args){
		testDeplacementHero();
	
	}
	
	private static void testDeplacementHero() {
		final Monde monde = new Monde();
		
		
		final JFrame frame = new JFrame();
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case 37:// gauche
					monde.deplacerHero(-1, 0);
					System.out.println(monde.toString());
					break;
				case 38:// haut
					monde.deplacerHero(0, -1);
					System.out.println(monde.toString());
					break;
				case 39:// droite
					monde.deplacerHero(1, 0);
					System.out.println(monde.toString());
					break;
				case 40:// bas
					monde.deplacerHero(0, 1);
					System.out.println(monde.toString());
					break;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == 's')
					frame.dispose();			
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.pack();
		frame.setVisible(true);
	}

}