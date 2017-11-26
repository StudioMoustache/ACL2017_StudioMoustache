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
	private final int HAUTEUR = 500;
	private final int LARGEUR = 500;

	// Il n'y a qu'une seule instance du monde dans le jeu
	final private static Monde instance = new Monde();

	/**
	 * Retourne la seule instance de la classe Monde
	 * @return instance de la classe Monde
	 */
	public static Monde getInstance() {
		return instance;
	}

	// Liste contenant les monstres du monde
	private ArrayList<Monstre> lesMonstres;
	// Liste contenant les portails du monde
	private ArrayList<Portail> lesPortails;
	// Instance du nexus (objectif) du monde
	private Nexus nexus;
	// Numéro courant de vague
	private int vague;
	
	// Indique si le joueur a perdu (point de vie du nexus à 0)
	private boolean perdu = false;
	// Indique si le jeu est en pause
	private boolean paused = false;
	private boolean debutVague = false;

	// Indique si le jeu est en 1 joueur ou 2 joueurs
	private boolean deuxJoueurs = false;

	// Nombre d'updates courant
	private int nbUpdates;

	// carte du monde
	private BufferedImage carte;

	// Il n'existe que deux héros dans le jeu, ils sont donc crées ici
	// en final et static
	final private static Hero hero1 = new Hero(250, 250, 5, instance);
	final private static Hero hero2 = new Hero(270, 250, 5, instance);
	
	// ----- Constructeurs -----
	
	private Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
		this.lesPortails = new ArrayList<Portail>();
		nbUpdates = 0;
		
		this.nexus = new Nexus(50,50);
		Portail p1 = new Portail(50, 450, 5, 20);
		lesPortails.add(p1);

		// récupération du fichier contenant la carte du monde
		try {
			carte = ImageIO.read(new File("src/images/carteTest.png"));
		} catch (IOException e) {
			carte = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		}
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

	/**
	 * Incrémente le numéro de la vague courante
	 */
	public void incrementeVague(){
		this.vague += 1;
	}
	
	// ----------------------- GESTION DES DEPLACEMENTS ----------------------
	
	/**
	 * Deplace le héro 1 en x et/ou en y
	 * @param x direction sur l'axe des abscisses
	 * @param y direction sur l'axe des ordonnées
	 */
	public void deplacerHero1(int x, int y){
		if (!isPaused()) {
			hero1.deplacementTrajectoire(carte, x, y);
		}
	}

	/**
	 * Deplace le héro 2 en x et/ou en y
	 * @param x direction sur l'axe des abscisses
	 * @param y direction sur l'axe des ordonnées
	 */
	public void deplacerHero2(int x, int y){
		if (!isPaused()) {
			hero2.deplacementTrajectoire(carte, x, y);
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
			
			m.deplacementTrajectoire(carte, deplacementX, deplacementY);
		}
	}
	
	
	// -------------------- GESTION DES COLLISIONS --------------------

	/**
	 * Pour un monstre donné, une position en x et en y donnée, test s'il y a collision
	 * entre ce monstre et le nexus
	 * @param m monstre
	 * @param x position en x du monstre
	 * @param y position en y du monstre
	 */
	public void collisionMonstreNexus(Monstre m) {
		int xMonstre = m.getX() + m.getWidth()/2;
		int yMonstre = m.getY() + m.getHeight()/2;

		if ((xMonstre >= this.nexus.getX() && xMonstre <= this.nexus.getX() + this.nexus.getWidth()) && 
			(yMonstre >= this.nexus.getY() && yMonstre <= this.nexus.getY() + this.nexus.getHeight())) {

			lesMonstres.remove(m);
			this.nexus.retirerVie(1);
		}
	}

	/**
	 * Verification de collision entre un héro et la liste des monstres
	 * @param hero héro ayant appelé cette fonction
	 */
	public void collisionHeroMonstres(Hero hero) {
		for (int i = 0; i < lesMonstres.size() ; i++) {
			collisionHeroMonstre(hero, lesMonstres.get(i));
		}
	}
	
	/**
	 * Verification de collision entre un héro et un monstre
	 * @param hero héro ayant appelé cette fonction
	 * @param m    monstre faisant parti de la liste des monstres
	 */
	public void collisionHeroMonstre(Hero hero, Monstre m) {
		int xMonstre = m.getX() + m.getWidth()/2;
		int yMonstre = m.getY() + m.getHeight()/2;

		if ((xMonstre >= hero.getX() && xMonstre <= hero.getX() + hero.getWidth()) && 
			(yMonstre >= hero.getY() && yMonstre  <= hero.getY() + hero.getHeight())) {
			lesMonstres.remove(m);
			hero.gainPoint(m.getValeurPoint());// ajout des point du monstre tué, aux score du joueur
		}
	}
	
	
	// ----------------------------------- UPDATE ET TOSTRING --------------------

	/**
	 * Fonction de mise à jour du monde à chaque rafraichissement de la classe Tick
	 */
	public void update(){
		if(!paused) {
			if(!perdu) {
				nbUpdates += 1;
				deplacementMonstres();
				checkInvocationMonstres();

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
	
	public void setDeuxJoueurs() {
		deuxJoueurs = true;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void changePause() {
		paused = !paused;
	}

	public int getScoreHero1() {
		return hero1.getScore();
	}

	public int getScoreHero2() {
		return hero2.getScore();
	}

	public int getVague() {
		return vague;
	}

	// ---- Fonctions tierces ---- //

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

		hero1.dessiner(g);
		if (deuxJoueurs)
			hero2.dessiner(g);
	}

	public String toString(){
		String toReturn = "";	
		toReturn += nexus.toString()+"\n";
		toReturn += hero1.toString()+"\n";
		for(Monstre m : lesMonstres)
			toReturn += m.toString()+"\n";
		return toReturn;
	}
}