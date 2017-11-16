package graphiques;

import principal.Monde;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanneauJeu extends JPanel implements KeyListener {
	private MainFrame fenetre;
	private Monde monde;
	private Tick tick;

	public PanneauJeu(Monde m, MainFrame f) {
		super();

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;
		fenetre.addKeyListener(this);

		// Le panel de jeu connait le monde pour le dessiner
		monde = m;

		tick = new Tick(monde, this);
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
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D)g;

		monde.dessiner(g2D);
	}

	public void startUpdate(){
		tick.start();
	}

}