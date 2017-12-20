package graphiques;

import principal.Monde;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class PanneauJeu extends JPanel {
	final private static Monde monde = Monde.getInstance();

	// Fenetre principale de l'application
	private MainFrame fenetre;
	// Instance permettant le raffraichissement
	// automatique du monde et du panneau
	private Tick tick;
	// Permet la lecture de plusieurs entrees a la fois
	// sur le clavier
	private MultipleKeyListener mkl;
	// JLabel contenant les scores des deux joueurs
	private JLabel scoreJ1, scoreJ2;
	// JLabel contenant le numero de la vague courante
	private JLabel numeroVague;

	private BufferedImage pauseImg;
	private JLabel zonePause;

	public PanneauJeu(MainFrame f) {
		super();

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;

		mkl = new MultipleKeyListener(monde);

		tick = new Tick(monde, this);

		Dimension dimensionScore=new Dimension(50,20);//TODO Une fenetre de score adaptable a la taille de la fenetre de jeu
		scoreJ1=new JLabel(""+monde.getScoreHero1());
		scoreJ2=new JLabel(""+monde.getScoreHero2());
		scoreJ1.setPreferredSize(dimensionScore);
		scoreJ2.setPreferredSize(dimensionScore);
		this.add(scoreJ1);
		this.add(scoreJ2);

		numeroVague = new JLabel("Vague : "+monde.getVague());
		this.add(numeroVague);

		// initialisation de l'image => Peut etre un img factory a faire pour les images du menu et celle-ci?
		try {
			pauseImg = ImageIO.read(new File("src/images/pause.png"));
			Dimension dimensionEcran = new Dimension(500, 520);
			zonePause = new JLabel(new ImageIcon(pauseImg));
			zonePause.setPreferredSize(dimensionEcran);
			this.add(zonePause);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		// Appelle les fonctions de deplacement des heros
		// selon les entrees du clavier
		mkl.deplacement();
		// Modifie la valeur des JLabel en fonction des
		// valeurs du monde
		scoreJ1.setText(""+monde.getScoreHero1());
		scoreJ2.setText(""+monde.getScoreHero2());
		numeroVague.setText("Vague : "+monde.getVague());
		zonePause.setVisible(monde.isPaused());
		// Affiche le filtre pause ne fonction du l'etat du monde (pause ou pas)

	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		monde.dessiner(g2D);
	}

	public void startUpdate(){
		// On assigne le keylistener de la fenetre de l'application
		// au keylistener que l'on a dans cette classe
		fenetre.addKeyListener(mkl);
		// On demarre les update du monde
		tick.start();
	}

	public void setNbJoueurs(int nbJoueurs) {
		if (nbJoueurs > 1) {
			// Indique au monde que le jeu est en mode
			// deux joueurs
			monde.setDeuxJoueurs();
			// Indique au keylistener que le jeu est en mode
			// deux joueurs
			mkl.setDeuxJoueurs();
		}
	}

}
