package principal;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import java.io.IOException;

import java.io.File;

import personnage.Hero;
import personnage.Monstre;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Monde {

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
	// Numero courant de vague
	private int vague;
	// Indique combien de fois on a changé de map lorsque la vague est a 10
	int nbChangeMap;
	// Le numéro de la carte actuel.
	int numCarteActuel;
	// Indique la dernière vague pour laquelle on a rajouté un portail
	private int vagueDernierPortail;
	// Indique si le joueur a perdu (point de vie du nexus à 0)
	private boolean perdu = false;
	// Indique si le jeu est en pause
	private boolean paused = false;
	private boolean debutVague = false;

	// Indique si le jeu est en 1 joueur ou 2 joueurs
	private boolean deuxJoueurs = false;

	// Nombre d'updates courant
	private int nbUpdates;
	private ArrayList<BufferedImage> listeCarte;
	private ArrayList<BufferedImage> listeCarteS;
	// carte du monde actuel
	private BufferedImage carte; // pour les collisions
	private BufferedImage carteS;  // pour le skin

	// Il n'existe que deux heros dans le jeu, ils sont donc crees ici
	// en final et static
	final protected static Hero hero1 = new Hero(250, 250, 5, instance);
	final protected static Hero hero2 = new Hero(270, 250, 5, instance);

	private final int DEFAULT_NB_MONSTRES = 5;
	private final int DEFAULT_PORTAL_FREQUENCY = 20;
	
	// ----- Constructeurs -----

	private Monde(){
		this.nbChangeMap=0;
		this.lesMonstres = new ArrayList<Monstre>();
		this.lesPortails = new ArrayList<Portail>();
	
		// recuperation du fichier contenant la carte du monde
		// choix aleatoire d'une des 5 cartes disponibles
		chargeCarteAleatoire();
		
		vagueDernierPortail = -1;

		nbUpdates = 0;
		numCarteActuel=-1;
		this.nexus = placerNexus();
		placerNexus();
		
		ajouterPortail(DEFAULT_NB_MONSTRES, DEFAULT_PORTAL_FREQUENCY);

		//Stock toute les cartes dans deux listes
		//stockCartes();
		
	}

	// ----------------------- FONCTIONS DE INVOCATION MONSTRES ----------------------------

	/**
	 * On recupere le premier monstre dans le portail p
	 * On ajoute le monstre m a lesMonstres
	 * Des qu'un monstre figure dans cette liste, il est sur le terrain.
	 */
	public void invoquerMonstre(Portail p, int type){
		lesMonstres.add(p.invoquerMonstre(p.getX(), p.getY(), type, this));
	}

	/**
	 * Fonction d'automatisation de l'invocation des monstres
	 */
	public void checkInvocationMonstres() {
		if(portailsVides() && lesMonstres.isEmpty())
			incrementeVague();
		for(Portail p : lesPortails) {
			// si il reste des monstres au portail et si c'est l'heure pour lui d'invoquer un monstre
			if(p.getNbMonstres() > 0 && this.nbUpdates % p.getFrequence() == 0) {
				int type;
				if(p.getNbMonstres() - 10 > 0){
					type = 3;
				}else{
					type = 2;
				}
				invoquerMonstre(p,type);
			}		
			if(portailVide(p) && lesMonstres.isEmpty()){
			 	p.rechargerPortail(this.vague-1);
			}
		}
	}

	/**
	* Fonction qui verifie que tous les portails sont vides
	*/
	public boolean portailsVides(){
		for(Portail p : lesPortails) {
			if(p.getNbMonstres() > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Fonction qui vérifie qu'un portail est vide
	 */
	public boolean portailVide(Portail p){
		if(p.getNbMonstres() > 0) {
			return false;
		}
		return true;	
	}

	/**
	 * Incremente le numero de la vague courante
	 */
	public void incrementeVague(){
		this.vague += 1;
	}
	
	public void ajouterPortail(int nbMonstres, int frequence){
		Portail p = placerPortail(nbMonstres, frequence);
		lesPortails.add(p);
	}

	// ----------------------- GESTION DES DEPLACEMENTS ----------------------

	/**
	 * Deplace le hero 1 en x et/ou en y
	 * @param x direction sur l'axe des abscisses
	 * @param y direction sur l'axe des ordonnees
	 */
	public void deplacerHero1(int x, int y){
		if (!isPaused()) {
			hero1.deplacementTrajectoire(carte, x, y);
		}
	}

	/**
	 * Deplace le hero 2 en x et/ou en y
	 * @param x direction sur l'axe des abscisses
	 * @param y direction sur l'axe des ordonnees
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

	// ----- FONCTION DE COLLISION AVEC MUR ----- //

/**
 * Fonction qui test pour la position suivante d'un personnage
 * s'il est en collision avec un mur
 * @param  int posX          position x courante du personnage qui test la collisions
 * @param  int posY          position y courante du personnage qui test la collision
 * @param  int directionx    direction x de la prochaine position du Personnage
 * @param  int directiony    direction y de la prochaine position du Personnage
 * @return     true s'il y a collision, false s'il n'y a pas.
 */
	public boolean testCollisionMur(int posX, int posY, int directionx, int directiony, int dimensionSprite) {
		boolean collision = false;
		boolean tour = false;

		int xTest = posX + directionx;
		int yTest = posY + directiony;

		int currentpixelX = xTest, currentpixelY = yTest;
		int signeX = 1, signeY = 0;

		// cette boucle fait le tour de la peripherie du carre du personnage pour detecter une collision
		// signeX = 1, on parcourt les X de gauche à droite
		// signeY = 1, on parcourt les Y de haut en bas (inversé avec swing)
		while (!tour && !collision) {
			// Parcours de la partie droite du carre
			if (currentpixelX == xTest + dimensionSprite-1 && currentpixelY == yTest) {
				signeX = 0;
				// 0,0 en haut à gauche de l'image, donc on augmente en y quand on descend
				signeY = 1;
			}

			// Parcours de la partie basse du carre
			if (currentpixelX == xTest + dimensionSprite-1 && currentpixelY == yTest + dimensionSprite-1) {
				signeX = -1;
				signeY = 0;
			}

			// Parcours de la partie gauche du carre
			if (currentpixelX == xTest && currentpixelY == yTest + dimensionSprite-1) {
				signeX = 0;
				signeY = -1;
			}

			// Le pixel courant est dans une partie noire de la map = collision
			if ((0x000000FF & carte.getRGB(currentpixelX, currentpixelY)) == 0) {
				collision = true;
			} else {
				currentpixelX += signeX;
				currentpixelY += signeY;
			}

			// Le pixel courant est le pixel de depart, donc on a fait un tour, pas de collision detectee
			if (currentpixelX == xTest && currentpixelY == yTest) {
				tour = true;
			}
		}

		return collision;
	}

	/**
	 * Pour un monstre donne, une position en x et en y donnee, test s'il y a collision
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
			System.out.println(nexus.getVie());
			this.nexus.retirerVie(m.getVie());
		}
	}

	/**
	 * Verification de collision entre un hero et la liste des monstres
	 * @param hero hero ayant appele cette fonction
	 */
	public void collisionHeroMonstres(Hero hero) {
		for (int i = 0; i < lesMonstres.size() ; i++) {
			Monstre m = lesMonstres.get(i);
			if(m.estInvincible()){
				m.decInvincibilite();
			}
			collisionHeroMonstre(hero, m);
		}
	}

	/**
	 * Verification de collision entre un hero et un monstre
	 * @param hero hero ayant appele cette fonction
	 * @param m    monstre faisant parti de la liste des monstres
	 */
	public void collisionHeroMonstre(Hero hero, Monstre m) {
		int xMonstre = m.getX() + m.getWidth()/2;
		int yMonstre = m.getY() + m.getHeight()/2;

		if ((xMonstre >= hero.getX() && xMonstre <= hero.getX() + hero.getWidth()) &&
			(yMonstre >= hero.getY() && yMonstre  <= hero.getY() + hero.getHeight()) && !m.estInvincible()) {
			m.decVie();
			if(m.getVie() == 0){
				lesMonstres.remove(m);
				hero.gainPoint(m.getValeurPoint());// ajout des point du monstre tué, aux score du joueur
			}
		}
	}


	// ----------------------------------- UPDATE ET TOSTRING --------------------

	/**
	 * Fonction de mise a jour du monde a chaque rafraichissement de la classe Tick
	 */
	public void update(){
		if(!paused) {
			if(!perdu) {
				nbUpdates += 1;
				deplacementMonstres();
				checkInvocationMonstres();
				checkChangementMap();
				checkAjoutPortail();
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
		g.drawImage(carteS, 0, 0, null);

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

	public void checkChangementMap (){
		//On change de map toute les 10vagues
		if((int)vague % 10 == 0 && vague!=0){
			//Une condition pour bien vérifier que le Monde change qu'une seul fois la map
			//lorsqu'on arrive a un nombre de map multiple de 10
			if(nbChangeMap==0){
				chargeCarteAleatoire();
				nbChangeMap++;
				placerNexus();
				placerHero(1);
				replacerPortails();
			}

		}else{
			//on remet l'entier a 0 pour la prochaine fois qu'on arrive à un nombre de vague multiple de 10
			nbChangeMap=0;
		}
	}
	
	public void checkAjoutPortail (){
		//On change de map toute les 10vagues
		if((int)vague % 3 == 0 && vague!=0){
			//Une condition pour bien vérifier que le Monde change qu'une seul fois la map 
			//lorsqu'on arrive a un nombre de map multiple de 10
			if(vagueDernierPortail != vague){
				ajouterPortail(DEFAULT_NB_MONSTRES, DEFAULT_PORTAL_FREQUENCY);
				vagueDernierPortail = vague;
				}				
		}
	}

	public void chargeCarteAleatoire(){

		int numCarte = (int)( Math.random()*( 5 - 1 + 1 ) ) + 1;
		//On évite d'avoirs deux fois la même carte(numCarte==numCarteActuel), lors du chargement de carte aléatoire.
		//si numCarteActuel==-1 c'est qu'on a pas encore de carte Actuel donc on autorise le random sans vérification
		while(numCarte==numCarteActuel && numCarteActuel!=-1){
			numCarte = (int)( Math.random()*( 5 - 1 + 1 ) ) + 1;
		}
		numCarteActuel=numCarte;
		try {
			carte = ImageIO.read(new File("src/images/carte"+numCarte+".png"));
			carteS = ImageIO.read(new File("src/images/carte"+numCarte+"S.png"));
		} catch (IOException e) {
			carte = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		}
	}
	//Fonction pour stocker toute les cartes dans les liste, non utilisé pour le moment.
	/*public void stockCartes(){
		try {
			int i;
			System.out.println(listeCarte.size());
			for(i=1;i<=5;i++){
				BufferedImage c = ImageIO.read(new File("src/images/carte"+i+".png"));
				BufferedImage cS = ImageIO.read(new File("src/images/carte"+i+"S.png"));
				listeCarte.add(c);
				listeCarteS.add(cS);
				i++;
			}
			System.out.println(listeCarte.size());
		} catch (IOException e) {
			carte = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		}
	}*/
	
	
	/**
	 * Cette fonction sert a placer le nexus de facon aleatoire sur la map
	 * On prend garde a ne pas le placer dans un mur
	 */
	private Nexus placerNexus(){
		boolean estCorrect = false;
		int sizeX = this.carte.getWidth();
		int sizeY = this.carte.getHeight();
		Nexus n = new Nexus(0,0);
		int testX;
		int testY;
		while(estCorrect == false){
			testX = 1 + (int)(Math.random() * ((sizeX - 1)));
			testY = 1 + (int)(Math.random() * ((sizeY - 1)));
			System.out.println("testX : "+testX+" testY : "+testY);
			
			// Si un des 4 coins du nexus est sur un pixel noir 
			if((0x000000FF & carte.getRGB(testX, testY)) == 0 || 
					((0x000000FF & carte.getRGB(testX + n.getWidth(), testY)) == 0) || 
					((0x000000FF & carte.getRGB(testX, testY + n.getHeight())) == 0) ||
					((0x000000FF & carte.getRGB(testX + n.getWidth(), testY + n.getHeight())) == 0)){
				// cas incorrect, donc on laisse boucler
				
			}else{
				estCorrect = true;
				n = new Nexus(testX, testY);
			}
		}
		return n;
	}
	
	/**
	 * Cette fonction sert a placer un portail de facon aleatoire sur la map
	 * On prend garde a ne pas le placer sur un mur
	 * On prend garde aussi a ne pas le placer trop pres du nexus
	 */
	private Portail placerPortail(int nbMonstres, int frequence){
		boolean estCorrect = false;
		int sizeX = this.carte.getWidth();
		int sizeY = this.carte.getHeight();
		Portail p = new Portail(0,0,0,0);
		int testX;
		int testY;
		while(estCorrect == false){
			testX = 1 + (int)(Math.random() * ((sizeX - 1)));
			testY = 1 + (int)(Math.random() * ((sizeY - 1)));
			System.out.println("testX : "+testX+" testY : "+testY);
			
			// Si un des 4 coins du portail est sur un pixel noir 
			if((0x000000FF & carte.getRGB(testX, testY)) == 0 || 
					((0x000000FF & carte.getRGB(testX + p.getWidth(), testY)) == 0) || 
					((0x000000FF & carte.getRGB(testX, testY + p.getHeight())) == 0) ||
					((0x000000FF & carte.getRGB(testX + p.getWidth(), testY + p.getHeight())) == 0)){
				// cas incorrect, donc on laisse boucler
				
			}else{
				int distancePortailNexus = (int) Math.sqrt(Math.pow(Math.abs(this.nexus.getX()-testX),2) + 
													Math.pow(Math.abs(this.nexus.getY()-testY), 2));
				
				if(distancePortailNexus > sizeX / 4){ // si la distance portail - nexus est supérieure à 1/4 de la taille de la map
					estCorrect = true;
					p = new Portail(testX, testY, nbMonstres, frequence);
				}
			}
		}
		return p;
	}
	
	/**
	 * Cette fonction sert à placer le hero autour du nexus
	 * On prend garde a ne pas le placer dans un mur
	 */
	private void placerHero(int numeroHero) {
		boolean estCorrect = false;
		int testX;
		int testY;

		testX = (int) (this.nexus.getX() + this.nexus.getWidth() * 1.5);
		testY = (int) (this.nexus.getY() + this.nexus.getHeight() * 1.5);

		while (estCorrect == false) {
			// on cherche aleatoirement une position libre autour du nexus
			testX = (int) (this.nexus.getX() - this.nexus.getWidth() * 1.5)
					+ (int) (Math.random() * (((this.nexus.getX() + this.nexus
							.getWidth() * 1.5) - (this.nexus.getX() - this.nexus
							.getWidth() * 1.5)) + 1));
			testY = (int) (this.nexus.getY() - this.nexus.getHeight() * 1.5)
					+ (int) (Math.random() * (((this.nexus.getY() + this.nexus
							.getHeight() * 1.5) - (this.nexus.getY() - this.hero1
							.getHeight() * 1.5)) + 1));

			if ((0x000000FF & carte.getRGB(testX, testY)) == 0
					|| ((0x000000FF & carte.getRGB(testX + nexus.getWidth(),
							testY)) == 0)
					|| ((0x000000FF & carte.getRGB(testX,
							testY + nexus.getHeight())) == 0)
					|| ((0x000000FF & carte.getRGB(testX + nexus.getWidth(),
							testY + nexus.getHeight())) == 0)) {
			} else {
				estCorrect = true;
				if (numeroHero == 1) {
					hero1.setX(testX);
					hero1.setY(testY);
				} else {
					hero2.setX(testX);
					hero2.setY(testY);
				}
			}

		}

	}
	
	private void replacerPortails(){
		int nbPortails = lesPortails.size();
		lesPortails.clear();
		for(int i = 0; i < nbPortails; i++)
			ajouterPortail(DEFAULT_NB_MONSTRES, DEFAULT_PORTAL_FREQUENCY);
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
