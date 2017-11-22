package principal;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import java.io.IOException;

import java.io.File;

import graphiques.Tick;
import personnage.Hero;
import personnage.Monstre;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Monde {
	private ArrayList<Monstre> lesMonstres;
	private ArrayList<Portail> lesPortails;
	private Nexus nexus;
	private int vague;
	private final int HAUTEUR = 500;
	private final int LARGEUR = 500;
	
	boolean perdu = false;
	boolean paused = false;
	boolean debutVague = false;

	private int nbUpdates;

	private BufferedImage carte;
	
	// ----- Constructeurs -----
	
	public Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
		this.lesPortails = new ArrayList<Portail>();
		nbUpdates = 0;
		
		this.nexus = new Nexus(50,50);
		Portail p1 = new Portail(50, 450, 5, 20);
		lesPortails.add(p1);

		Hero.initialisationHeros(this);

		// récupération du fichier contenant la carte du monde
		try {
			carte = ImageIO.read(new File("src/images/carteTest.png"));
		} catch (IOException e) {
			carte = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		}
		
		// On fait bien toutes les initialisations AVANT la creation du Tick qui va declencher les update
		
		// Tick monTick = new Tick(this);
	}
	
	
	
	// ----------------------- FONCTIONS DE INVOCATION MONSTRES ----------------------------
	
	/**
	 * On recupere le premier monstre dans le portail p
	 * On ajoute le monstre m a lesMonstres
	 * Des qu'un monstre figure dans cette liste, il est sur le terrain.
	 */
	public void invoquerMonstre(Portail p){
		if(p.getNbMonstres() > 0) {
			p.decMonstres();
			Monstre m = new Monstre(p.getX(), p.getY(), this);
			lesMonstres.add(m);
		}
	}
	
	/**
	 * Fonction d'automatisation de l'invocation des monstres
	 */
	public void checkInvocationMonstres() {
		for(Portail p : lesPortails) {
			// si il reste des monstres au portail et si c'est l'heure pour lui d'invoquer un monstre
			if(p.getNbMonstres() > 0 && this.nbUpdates % p.getFrequence() == 0) {
				invoquerMonstre(p);
				debutVague = true;
			}
			if(lesMonstres.isEmpty() && debutVague){
				debutVague = false;
			 	incrementeVague();
			 	p.rechargerPortail(this.vague);
			}
		}
	}

	public void incrementeVague(){
		this.vague += 1;
	}
	
	// ----------------------- GESTION DES DEPLACEMENTS ----------------------
	
	public void deplacerHero1(int x, int y){
		if (!isPaused()) {
			Hero.getHero1().calculTrajectoire(carte, x, y);
		}
	}

	public void deplacerHero2(int x, int y){
		if (!isPaused()) {
			Hero.getHero2().calculTrajectoire(carte, x, y);
		}
	}
	
	/**
	 * 	Fonction de deplacement des monstres
	 *  A : point de depart et B : point d'arrivee
	 *	equation d'une droite entre deux points A et B : y = mx + p
	 *	avec m = (yB - Ya) / (xB - xA)
	 *	et p = yA - m*xA
	 */
	public void deplacementMonstres() {
		// Fonction avec equation de droite : Ne fonctionne pas (encore)
		/*int xMonstre; // xA
		int yMonstre; // yA
		int xNexus; // xB
		int yNexus; // yB
		float m, p;
		int deplacementX, deplacementY;
		
		for(Monstre monstre : lesMonstres) {
			
			xMonstre = monstre.getX(); // xA
			yMonstre = monstre.getY(); // yA
			xNexus = nexus.getX(); // xB
			yNexus = nexus.getY(); // yB
			
			if(xNexus == xMonstre)
				m = Math.abs((float)yNexus - (float)yMonstre);
			else
				m = Math.abs(((float)yNexus - (float)yMonstre)) / Math.abs((float)xNexus - (float)xMonstre);
			p = yMonstre - m*xMonstre;
			
			deplacementX = monstre.getVitesse();
			deplacementY = (int)((m * deplacementX + p));
			
			monstre.deplacer(deplacementX, deplacementY);
		}*/
		
		
		// Fonction un peu plus basique qui marche
		
		int xNexus;
		int yNexus;
		int xMonstre;
		int yMonstre;
		int deplacementX;
		int deplacementY;
		for(int i=0; i<lesMonstres.size(); i++) {
			Monstre m = lesMonstres.get(i);

			xNexus = nexus.getX();
			yNexus = nexus.getY();
			
			xMonstre = m.getX();
			yMonstre = m.getY();

			if(xNexus < xMonstre)
				deplacementX = -1;
			else if(xNexus > xMonstre)
				deplacementX = 1;
			else // xMonstre == XNexus
				deplacementX = 0;
			
			if(yNexus < yMonstre)
				deplacementY = -1;
			else if(yNexus > yMonstre)
				deplacementY = 1;
			else // xMonstre == XNexus
				deplacementY = 0;
			
			m.calculTrajectoire(carte, deplacementX, deplacementY);
		}
	}
	
	
	// -------------------- GESTION DES COLLISIONS --------------------

	// Fonction appelée par une instance de Montre lors de son déplacement
	public void collisionMonstreNexus(Monstre m, int x, int y) {
		int xMonstre = m.getX() + m.getWidth()/2;
		int yMonstre = m.getY() + m.getHeight()/2;

		if ((xMonstre >= this.nexus.getX() && xMonstre <= this.nexus.getX() + this.nexus.getWidth()) && 
			(yMonstre >= this.nexus.getY() && yMonstre <= this.nexus.getY() + this.nexus.getHeight())) {

			lesMonstres.remove(m);
			this.nexus.retirerVie(1);
		}
	}

	// Fonction de vérification de collision entre le héro et un ou plusieurs monstre(s)
	// x, y : position courante du héro
	public void collisionHeroMonstres(Hero hero, int x, int y) {
		for (int i = 0; i < lesMonstres.size() ; i++) {
			collisionHeroMonstre(hero, lesMonstres.get(i), x, y);
		}
	}
	
	public void collisionHeroMonstre(Hero hero, Monstre m, int x, int y) {
		int xMonstre = m.getX() + m.getWidth()/2;
		int yMonstre = m.getY() + m.getHeight()/2;

		if ((xMonstre >= hero.getX() && xMonstre <= hero.getX() + hero.getWidth()) && 
			(yMonstre >= hero.getY() && yMonstre  <= hero.getY() + hero.getHeight())) {
			lesMonstres.remove(m);
			hero.gainPoint(m.getValeurPoint());// ajout des point du monstre tué, aux score du joueur 1
		}
	}
	
	
	// ----------------------------------- UPDATE ET TOSTRING --------------------
	
	// A chaque Tick (rafraichissement), on deplace les monstres et on teste les collisions
	public void update(){
		if(!paused) {
			if(!perdu) {
				nbUpdates += 1;
				deplacementMonstres();
				checkInvocationMonstres();
				//System.out.println(this.toString());


				if (!this.nexus.estVivant()) {
					this.perdu = true;
				}
			}else {
				System.out.println("Nexus detruit. Vous avez perdu.");
				System.out.println("PERDU !!");
				System.exit(0);
			}
		}
	}
	
	// TODO ajouterMonstre en designant un portail

	// ---- Getters & Setters ---- //
	
	public boolean isPaused() {
		return paused;
	}
	
	public void changePause() {
		paused = !paused;
	}

	public int getScoreHero1() {
		return Hero.getHero1().getScore();
	}

	public int getScoreHero2() {
		return Hero.getHero2().getScore();
	}

	// fonction d'affichage du monde
	public void dessiner(Graphics2D g) {
		g.drawImage(carte, 0, 0, null);

		nexus.dessiner(g);

		for (Portail p : lesPortails) {
			p.dessiner(g);
		}

		for (Monstre m : lesMonstres) {
			m.dessiner(g);
		}

		Hero.getHero1().dessiner(g);
	}

	public String toString(){
		String toReturn = "";	
		toReturn += nexus.toString()+"\n";
		toReturn += Hero.getHero1().toString()+"\n";
		for(Monstre m : lesMonstres)
			toReturn += m.toString()+"\n";
		return toReturn;
	}

	public int getVague() {
		return vague;
	}
}
