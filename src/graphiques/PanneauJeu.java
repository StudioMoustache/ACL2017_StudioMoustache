package graphiques;

import principal.Monde;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JLabel;

import personnage.Hero;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;


public class PanneauJeu extends JPanel {
	final private static Monde monde = Monde.getInstance();

	private MainFrame fenetre;
	private Tick tick;
	private MultipleKeyListener mkl;
	private JLabel scoreJ1, scoreJ2;
	private JLabel numeroVague;

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
	}

	public void update() {
		mkl.deplacement();
		scoreJ1.setText(""+monde.getScoreHero1());
		scoreJ2.setText(""+monde.getScoreHero2());
		numeroVague.setText("Vague : "+monde.getVague());
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		monde.dessiner(g2D);
	}
	
	public void startUpdate(){
		fenetre.addKeyListener(mkl);
		tick.start();
	}

	public void setNbJoueurs(int nbJoueurs) {
		if (nbJoueurs > 1) {
			monde.setDeuxJoueurs();
			mkl.setDeuxJoueurs();
		}
	}

}