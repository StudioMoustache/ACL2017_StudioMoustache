package graphiques;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class PanneauMenu extends JPanel implements KeyListener {
	private MainFrame fenetre;

	public PanneauMenu(MainFrame f) {
		super();
		setLayout(new GridLayout(2, 1));

		JLabel titre = new JLabel("BERZERKER");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setVerticalAlignment(SwingConstants.CENTER);

		add(titre);

		JLabel message = new JLabel("Appuyez sur ENTER");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setVerticalAlignment(SwingConstants.CENTER);		

		add(message);

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;
		fenetre.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		// 10 est le code de ENTER
		if (e.getKeyCode() == 10) {
			fenetre.setPanneauJeu();
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		
	}
}