package graphiques;

import principal.Monde;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanneauJeu extends JPanel {
	private MainFrame fenetre;
	private Monde monde;
	private Tick tick;
	private MultipleKeyListener mkl;

	public PanneauJeu(Monde m, MainFrame f) {
		super();

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;
		
		mkl=new MultipleKeyListener();
		fenetre.addKeyListener(mkl);
		// Le panel de jeu connait le monde pour le dessiner
		monde = m;

		tick = new Tick(monde, this);
	}

	

	/*public void keyPressed(KeyEvent e) {
		boolean gauche=false;
		boolean droite=false;
		boolean haut =false;
		boolean bas=false;
		
		//int keycode=e.getKeyCode();
		
		// gauche
		if(keycode==37){
			monde.deplacerHero(-1, 0);
			gauche=true;
		}
		// haut
		if(keycode==38){
			monde.deplacerHero(0, -1);
			haut=true;
		}
		// droite
		if(keycode==39){
			monde.deplacerHero(1, 0);
			droite=true;
		}
		// bas
		if(keycode==40){
			monde.deplacerHero(0, 1);
			bas=true;
		}
		
		switch (e.getKeyCode()) {
		case 37:// gauche
			gauche=true;
			break;
		case 38:// haut
			haut=true;
			break;
		case 39:// droite
			droite=true;
			break;
		case 40:// bas
			bas=true;
			break;
		}
		
		if(gauche){
			monde.deplacerHero(-1, 0);
		}
		if(haut){
			monde.deplacerHero(0, -1);
		}
		if(droite){
			monde.deplacerHero(1, 0);
		}
		if(bas){
			monde.deplacerHero(0, 1);
		}
		if(gauche&&haut){
			monde.deplacerHero(-1,-1);
		}
		if(droite&&haut){
			monde.deplacerHero(1,-1);
		}
		if(gauche&&bas){
			monde.deplacerHero(-1,1);
		}
		if(droite&&bas){
			monde.deplacerHero(1,1);
		}
	}*/

	/*public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}*/
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		mkl.deplacement(monde);
		Graphics2D g2D = (Graphics2D)g;

		monde.dessiner(g2D);
	}

	public void startUpdate(){
		tick.start();
	}

}