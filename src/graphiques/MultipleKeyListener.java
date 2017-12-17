package graphiques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import principal.Monde;

public class MultipleKeyListener implements KeyListener {
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getExtendedKeyCode());
		
		if (e.getExtendedKeyCode() == 80) // 80 pour P 
			monde.changePause();
	}
	
	/**
	 * Parcours de l'ensemble contenant les touches entrées au clavier
	 * afin de déplacer le ou les heros
	 */
	public void deplacement(){
		// Deplacements du joueur 1
		int deplacementX = 0, deplacementY = 0;

		if(pressed.contains(37)){
			deplacementX -= 1;
		}
		if(pressed.contains(38)){
			deplacementY -= 1;
		}
		if(pressed.contains(39)){
			deplacementX += 1;
		}
		if(pressed.contains(40)){
			deplacementY += 1;
		}

		if (deplacementX != 0 || deplacementY != 0) 
			monde.deplacerHero1(deplacementX, deplacementY);

		if (deuxJoueurs) {
			// Deplacements du joueur 2
			deplacementX = 0;
			deplacementY = 0;

			if(pressed.contains(81)){
				deplacementX -= 1;
			}
			if(pressed.contains(90)){
				deplacementY -= 1;
			}
			if(pressed.contains(68)){
				deplacementX += 1;
			}
			if(pressed.contains(83)){
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
