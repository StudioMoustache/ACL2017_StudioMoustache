package graphiques;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

		// Importation des images du menu
		Image menuImg = null;
		Image onePlayerT = null;
		Image twoPlayerT = null;
		try {
			menuImg = ImageIO.read(new File("src/images/menu.jpg"));
			BufferedImage onePlayer = ImageIO.read(new File("src/images/1_players.png"));
			onePlayerT =  onePlayer.getScaledInstance(245, 245, onePlayer.SCALE_DEFAULT);
			BufferedImage twoPlayer = ImageIO.read(new File("src/images/2_players.png"));
			twoPlayerT =  twoPlayer.getScaledInstance(245, 245, twoPlayer.SCALE_DEFAULT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		// Creation du JLabel de titre de jeu
		JLabel titre = new JLabel(new ImageIcon(menuImg));
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
		
		JButton unJoueur = new JButton(new ImageIcon(onePlayerT));
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
		JButton deuxJoueurs = new JButton(new ImageIcon(twoPlayerT));
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