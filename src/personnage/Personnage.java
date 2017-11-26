package personnage;

import graphiques.AffichageSprite;
import principal.Monde;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Personnage {
	protected int x;
	protected int y;
	protected int vitesse;

	// Un personnage connait le monde dans lequel il se trouve
	protected Monde monde;

	// Classe permettant de dessiner le personnage
	protected AffichageSprite sprite;

	// ------ CONSTRUCTEUR ------ // 

	public Personnage(int x, int y, int vitesse, AffichageSprite sprite, Monde monde) {
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
		this.sprite = sprite;
		this.monde = monde;
	}

	// ------ GETTERS & SETTERS ------ //

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	// ----- FONCTION ABSTRAITE ----- // 

	// Fonction de verification de collision avec l'objectif
	// Objectif : 
	// 	Hero -> les monstres
	// 	Monstre -> le(s) nexus
	// x, y : position courante du personnage
	public abstract void collisionObjectif(int x, int y);

	// ----- FONCTION DE COLLISION AVEC MUR ----- //

	// Fonction qui test pour un x et un y donné s'il y a collision
	// Fonction utilisée par la fonction calculTrajectoire
	private boolean testCollisionMur(BufferedImage bi) {
		boolean collision = false;
		boolean tour = false;

		int currentpixelX = x, currentpixelY = y;
		int signeX = 1, signeY = 0;

		// cette boucle fait le tour de la périphérie du carré du personnage pour détecter une collision
		// signeX = 1, on parcourt les X de gauche à droite
		// signeY = 1, on parcourt les Y de haut en bas (inversé avec swing)
		while (!tour && !collision) {
			// Parcours de la partie droite du carré
			if (currentpixelX == x + sprite.getWidth()-1 && currentpixelY == y) {
				signeX = 0;
				// 0,0 en haut à gauche de l'image, donc on augmente en y quand on descend
				signeY = 1;
			}

			// Parcours de la partie basse du carré
			if (currentpixelX == x + sprite.getWidth()-1 && currentpixelY == y + sprite.getHeight()-1) {
				signeX = -1;
				signeY = 0;
			}

			// Parcours de la partie gauche du carré
			if (currentpixelX == x && currentpixelY == y + sprite.getHeight()-1) {
				signeX = 0;
				signeY = -1;
			}	

			// Le pixel courant est dans une partie noire de la map = collision
			if ((0x000000FF & bi.getRGB(currentpixelX, currentpixelY)) == 0) {
				collision = true;
			} else {
				currentpixelX += signeX;
				currentpixelY += signeY;
			}

			// Le pixel courant est le pixel de départ, donc on a fait un tour, pas de collision détectée
			if (currentpixelX == x && currentpixelY == y) {
				tour = true;
			}
		}
		
		return collision;
	}

	// cette fonction parcourt les pixels de la trajectoire du personnage un par un pour l'arrêter 
	// lorsqu'il est contre un mur
	// categoriePersonnage permet de savoir si la fonction est appelée par un héro ou pas un monstre
	public void deplacementTrajectoire(BufferedImage bi, int deplacementx, int deplacementy) {
		boolean collision = false;
		int xArrivee = this.x + (deplacementx*this.vitesse);
		int yArrivee = this.y + (deplacementy*this.vitesse);

		while (!collision) {
			// Appel de la fonction de vérification de collision avec les murs
			collision = this.testCollisionMur(bi);

			// Appel de la fonction de vérification de collision avec : 
			// - soit les monstres (pour le héro)
			// - soit le nexus (pour les monstres)
			this.collisionObjectif(x, y);

			if (!collision) { // Il n'y a pas collision, on ajoute à la position courante
							  // le deplacement en x et en y
				this.x += deplacementx;
				this.y += deplacementy;
			} else { // Il y a collision à la position courante du personnage
					 // donc on le recule d'un pixel
				this.x -= deplacementx;
				this.y -= deplacementy;
			}

			// On est arrivé à la position max de la trajectoire donc 
			// on ne teste plus la collision
			if (this.x == xArrivee && this.y == yArrivee) {
				collision = true;
			}
		}
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, this.x, this.y);
	}
}