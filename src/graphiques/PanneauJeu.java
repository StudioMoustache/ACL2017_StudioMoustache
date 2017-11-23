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
	private MainFrame fenetre;
	private Monde monde;
	private Tick tick;
	private MultipleKeyListener mkl;
	private JLabel scoreJ1, scoreJ2;

	private JLabel panneauVagues;


	public PanneauJeu(MainFrame f) {

		super();

		// Le panel de jeu connait le monde pour le dessiner
		monde = new Monde();

		// Chaque panel connait la fenetre pour changer de panel dans l'application
		fenetre = f;

		mkl=new MultipleKeyListener(monde);		
		fenetre.addKeyListener(mkl);

		panneauVagues = new JLabel("Vague : "+m.getVague());
		add(panneauVagues);

		tick = new Tick(monde, this);
		
		Dimension dimensionScore=new Dimension(50,20);//TODO Une fenetre de score adaptable a la taille de la fenetre de jeu
		scoreJ1=new JLabel(""+monde.getScoreHero1());
		scoreJ2=new JLabel(""+monde.getScoreHero2());
		scoreJ1.setPreferredSize(dimensionScore);
		scoreJ2.setPreferredSize(dimensionScore);
		this.add(scoreJ1);
		this.add(scoreJ2);
	}

	public void update() {
		mkl.deplacement();
		scoreJ1.setText(""+monde.getScoreHero1());
		scoreJ2.setText(""+monde.getScoreHero2());
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D)g;
		panneauVagues.setText("Vague : "+monde.getVague());
		monde.dessiner(g2D);
	}
	
	public void startUpdate(){
		tick.start();
	}

}