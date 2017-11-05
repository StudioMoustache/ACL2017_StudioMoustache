package graphiques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import principal.Monde;

public class MainFrame extends JFrame implements KeyListener {
	private Monde monde;

	public MainFrame(Monde monde) {
		// this.draw=new mainDraw(); //a voir ensuite

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		this.monde = monde;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37:// gauche
			monde.deplacerHero(-1, 0);
			break;
		case 38:// haut
			monde.deplacerHero(0, -1);
			break;
		case 39:// droite
			monde.deplacerHero(1, 0);
			break;
		case 40:// bas
			monde.deplacerHero(0, 1);
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 's') {
			System.out.println("S pressed. Goodbye !");
			this.dispose();
			System.exit(0);
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
