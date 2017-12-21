package graphiques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.TreeSet;

import principal.Monde;

public class MultipleKeyListener implements KeyListener {
	private final int TOUCHE_QUITTER = 27; // Esc
	private final int TOUCHE_PAUSE = 80; // P
	private final int TOUCHE_GAUCHE = 37; // fleche gauche
	private final int TOUCHE_DROITE = 39; // fleche droite
	private final int TOUCHE_HAUT = 38; // fleche haut
	private final int TOUCHE_BAS = 40; // fleche bas
	private final int TOUCHE_GAUCHE_J2 = 81; // Q
	private final int TOUCHE_DROITE_J2 = 68; // D
	private final int TOUCHE_HAUT_J2 = 90; // Z
	private final int TOUCHE_BAS_J2 = 83; // S
	
	private Monde monde;

	private final TreeSet<Integer>pressed =new TreeSet<Integer>();

	private boolean deuxJoueurs = false;

	public MultipleKeyListener(Monde m){
		this.monde=m;
	}

	public void setDeuxJoueurs() {
		deuxJoueurs = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Fonction non utilisee mais doit etre implementee pour
		// l'interface KeyListener
	}

	@Override
	public void keyPressed(KeyEvent e) {
        pressed.add(e.getExtendedKeyCode());
        if(e.getExtendedKeyCode() == TOUCHE_QUITTER){
        	monde.quitter();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getExtendedKeyCode());

		if (e.getExtendedKeyCode() == TOUCHE_PAUSE)
			monde.changePause();
	}

	/**
	 * Parcours de l'ensemble contenant les touches entrées au clavier
	 * afin de déplacer le ou les heros
	 */
	public void deplacement(){
		// Deplacements du joueur 1
		int deplacementX = 0, deplacementY = 0;

		if(pressed.contains(TOUCHE_GAUCHE)){
			deplacementX -= 1;
		}
		if(pressed.contains(TOUCHE_HAUT)){
			deplacementY -= 1;
		}
		if(pressed.contains(TOUCHE_DROITE)){
			deplacementX += 1;
		}
		if(pressed.contains(TOUCHE_BAS)){
			deplacementY += 1;
		}

		if (deplacementX != 0 || deplacementY != 0)
			monde.deplacerHero1(deplacementX, deplacementY);

		if (deuxJoueurs) {
			// Deplacements du joueur 2
			deplacementX = 0;
			deplacementY = 0;

			if(pressed.contains(TOUCHE_GAUCHE_J2)){
				deplacementX -= 1;
			}
			if(pressed.contains(TOUCHE_HAUT_J2)){
				deplacementY -= 1;
			}
			if(pressed.contains(TOUCHE_DROITE_J2)){
				deplacementX += 1;
			}
			if(pressed.contains(TOUCHE_BAS_J2)){
				deplacementY += 1;
			}

			if (deplacementX != 0 || deplacementY != 0)
				monde.deplacerHero2(deplacementX, deplacementY);
		}
	}

	public String toString(){
		String s="";
		Iterator<Integer> it=pressed.iterator();
		while(it.hasNext()){
			s+=""+it.next();
		}
		return s;
	}
}
