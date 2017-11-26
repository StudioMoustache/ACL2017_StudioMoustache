package graphiques;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class PanneauMenu extends JPanel {
	private MainFrame fenetre;

	public PanneauMenu(MainFrame f) {
		super();

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;

		// Layout sous forme de grille de 3 lignes et de 1 colonne
		setLayout(new GridLayout(2, 1));

		// Création du JLabel de titre de jeu
		JLabel titre = new JLabel("BERZERKER");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setVerticalAlignment(SwingConstants.CENTER);

		// Ajout du titre du jeu en premiere ligne
		add(titre);
		// Panneau de choix du nombre de joueur, necessaire pour avoir
		// les deux choix cote a cote
		JPanel choixNbJoueurs = new JPanel();

		// Layout sous forme de grille de 1 ligne et de 2 colonnes
		choixNbJoueurs.setLayout(new GridLayout(1, 2));

		// Creation du JButton de choix 1 joueur
		JButton unJoueur = new JButton("1 joueur");
		unJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		unJoueur.setVerticalAlignment(SwingConstants.CENTER);
		unJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetre.setPanneauJeu(1);
			}
		});

		// Ajout du JButton en premiere colonne du panneau
		// de choix du nombre de joueurs
		choixNbJoueurs.add(unJoueur);

		// Creation du JButton de choix 1 joueur
		JButton deuxJoueurs = new JButton("2 joueurs");
		deuxJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
		deuxJoueurs.setVerticalAlignment(SwingConstants.CENTER);
		deuxJoueurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetre.setPanneauJeu(2);
			}
		});

		// Ajout du JLabel en deuxieme colonne du panneau
		// de choix du nombre de joueurs
		choixNbJoueurs.add(deuxJoueurs);

		// Ajout du panneau de choix du nombre de joueur
		// au panneau principal
		add(choixNbJoueurs);
	}
}