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
	
	private int nbUpdates;

	private BufferedImage carte;
	
	// ----- Constructeurs -----
	
	public Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
		this.lesPortails = new ArrayList<Portail>();
		nbUpdates = 0;
		
		this.nexus = new Nexus(50, 0);
		Portail p1 = new Portail(50, 400, 5, 20);
		lesPortails.add(p1);

		try {
			carte = ImageIO.read(new File("src/images/carteTest.png"));
		} catch (IOException e) {
			e.printStackTrace();
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
			Monstre m = new Monstre(p.getX(), p.getY());
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
			}
			// if(lesMonstres.isEmpty()){
			// 	incrementeVague();
			// 	p.rechargerPortail(this.vague);
			// }
		}
	}

	public void incrementeVague(){
		this.vague += 1;
	}
	
	// ----------------------- GESTION DES DEPLACEMENTS ----------------------
	
	public void deplacerHero(int x, int y){
		if (!Hero.getHero1().testCollisionMur(carte, x, y) && !isPaused()) {
			Hero.getHero1().deplacer(x, y);
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
		int vitesse;
		for(Monstre m : lesMonstres) {		
			xNexus = nexus.getX();
			yNexus = nexus.getY();
			
			xMonstre = m.getX();
			yMonstre = m.getY();
			
			vitesse = m.getVitesse();
			if(xNexus < xMonstre)
				deplacementX = -vitesse;
			else if(xNexus > xMonstre)
				deplacementX = vitesse;
			else // xMonstre == XNexus
				deplacementX = 0;
			
			if(yNexus < yMonstre)
				deplacementY = -vitesse;
			else if(yNexus > yMonstre)
				deplacementY = vitesse;
			else // xMonstre == XNexus
				deplacementY = 0;
			
			if (!m.testCollisionMur(carte, deplacementX, deplacementY));
				m.deplacer(deplacementX, deplacementY);
		}
	}
	
	
	// -------------------- GESTION DES COLLISIONS --------------------
	
	// Rassemblement du balayage des monstres en une seule fonction ne le faire qu'une fois par fonction de collision
	public void collisionMonstres() {
		// foreach impossible car conflit d'utilisation quand on delete le monstre
		for(int i = 0; i < lesMonstres.size(); i++) {
			Monstre m = lesMonstres.get(i);		
			collisionHeroMonstre(m, i);
			collisionMonstreNexus(m, i);
		}
	}
	
	public void collisionHeroMonstre(Monstre m, int i) { // volontairement simpliste pour l'instant. HitBox a prendre en compte plus tard
		if(m.getX() == Hero.getHero1().getX() && m.getY() == Hero.getHero1().getY())
			lesMonstres.remove(i);
	
	}

	public void collisionMonstreNexus(Monstre m, int i) {
		if(m.getX() == this.nexus.getX() && m.getY() == nexus.getY()) {
			lesMonstres.remove(i);
			this.perdu = true;
		}
	}
	
	
	// ----------------------------------- UPDATE ET TOSTRING --------------------
	
	// A chaque Tick (rafraichissement), on deplace les monstres et on teste les collisions
	public void update(){
		if(!paused) {
			if(!perdu) {
				nbUpdates += 1;
				deplacementMonstres();
				collisionMonstres();
				checkInvocationMonstres();
				//System.out.println("Vague:"+vague);
				//System.out.println(this.toString());
			}else {
				System.out.println("Nexus detruit. Vous avez perdu.");
				System.out.println("PERDU !!");
				System.exit(0);
			}
		}
	}
	
	
	public String toString(){
		String toReturn = "";	
		toReturn += nexus.toString()+"\n";
		toReturn += Hero.getHero1().toString()+"\n";
		for(Monstre m : lesMonstres)
			toReturn += m.toString()+"\n";
		return toReturn;
	}
	
	// TODO ajouterMonstre en designant un portail
	

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
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPause() {
		paused = !paused;
	}
}
