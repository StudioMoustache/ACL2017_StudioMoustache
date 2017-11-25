package graphiques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import java.awt.Graphics;

import javax.swing.Timer;

import principal.Monde;

/**
 * 
 * Classe utilis�e pour automatiquement update le monde (et ensuite la frame) tous les REFRESH temps (en ms)
 *
 */
public class Tick implements ActionListener {
	private Monde monde;
	private Timer timer;
	private final int REFRESH = 50;

	// permet l'appel de la mise à jour du panneau et du dessin du monde
	private PanneauJeu panneauJeu;
	
	/**
	 * Constructeur du Tick, qu'on n'utilise qu'une fois, qui initialise le Timer et le lance
	 */
	public Tick(Monde monde, PanneauJeu pj) {
		this.monde = monde;
		this.panneauJeu = pj;
		this.timer = new Timer(REFRESH, (ActionListener) this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		this.monde.update();	
		panneauJeu.update();
		panneauJeu.repaint();
	}

	public void start(){
		timer.start();
	}
}