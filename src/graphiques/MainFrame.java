package graphiques;

import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame extends JFrame {
	private static int WINDOW_WIDTH = 500;
	private static int WINDOW_HEIGHT = 500;

	private PanneauMenu panneauMenu;
	private PanneauJeu panneauJeu;

	public MainFrame() {
		super("Berzerker");
		// Affichage de la fenetre à la taille voulue
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		// Permet la fermeture de la fenetre à l'aide de la croix
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Chaque panel est un des affichages du jeu (Menu d'avant-jeu, jeu et menu de fin de jeu)
		panneauMenu = new PanneauMenu(this);
		panneauMenu.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		panneauJeu = new PanneauJeu(this);
		panneauJeu.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);


		// Initialement le premier panel est celui du menu d'avant-jeu
		setPanneauMenu();

		// Enlève le bord de fenetre de l'OS
		setUndecorated(true);

		// Met le focus sur la fenetre
		setFocusable(true);

		// Place la fenêtre en 50, 50
		setLocation(50, 50);

		pack();
		setVisible(true);
	}

	public void setPanneauMenu() {
		setContentPane(panneauMenu);
	}

	public void setPanneauJeu(int nbJoueurs) {
		panneauJeu.setNbJoueurs(nbJoueurs);
		panneauJeu.startUpdate();
		setContentPane(panneauJeu);
	}
}
