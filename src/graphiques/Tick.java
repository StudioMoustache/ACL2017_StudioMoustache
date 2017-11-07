package graphiques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private final int REFRESH = 250;
	
	/**
	 * Constructeur du Tick, qu'on n'utilise qu'une fois, qui initialise le Timer et le lance
	 */
	public Tick(Monde monde) {
		this.monde = monde;
		this.timer = new Timer(REFRESH, (ActionListener) this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.monde.update();		
	}
}