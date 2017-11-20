package personnage;

import graphiques.AffichageSprite;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Personnage {
	protected int x;
	protected int y;
	protected int vitesse;

	protected AffichageSprite sprite;

	// ------ CONSTRUCTEUR ------ // 

	public Personnage(int x, int y, int vitesse, AffichageSprite s) {
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
		sprite = s;
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

	// ----- Fonctions -----
	
	public void deplacer(int x, int y){
		this.x += x;
		this.y += y;
	}

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
			if (currentpixelX == x + sprite.getWidth()-1 && currentpixelY == y) {
				signeX = 0;
				// 0,0 en haut à gauche de l'image, donc on augmente en y quand on descend
				signeY = 1;
			}

			if (currentpixelX == x + sprite.getWidth()-1 && currentpixelY == y + sprite.getHeight()-1) {
				signeX = -1;
				signeY = 0;
			}

			if (currentpixelX == x && currentpixelY == y + sprite.getHeight()-1) {
				signeX = 0;
				signeY = -1;
			}

			// Le pixel courant est dans une partie noire de la map = collision
			if ((0x000000FF & bi.getRGB(currentpixelX, currentpixelY)) == 0) {
				collision = true;
			}

			currentpixelX += signeX;
			currentpixelY += signeY;

			// Le pixel courant est le pixel de départ, donc on a fait un tour, pas de collision détectée
			if (currentpixelX == x && currentpixelY == y) {
				tour = true;
			}
		}
		
		return collision;
	}

	// cette fonction parcourt les pixels de la trajectoire du personnage un par un pour l'arrêter 
	// lorsqu'il est contre un mur
	public void calculTrajectoire(BufferedImage bi, int deplacementx, int deplacementy) {
		boolean collision = false;
		int xArrivee = x + (deplacementx*vitesse);
		int yArrivee = y + (deplacementy*vitesse);

		while (!collision) {
			collision = testCollisionMur(bi);

			if (!collision) { // Il n'y a pas collision, on ajoute à la position courante
							  // le deplacement en x et en y
				x += deplacementx;
				y += deplacementy;
			} else { // Il y a collision à la position courante du personnage
					 // donc on le recule d'un pixel
				x -= deplacementx;
				y -= deplacementy;
			}

			// On est arrivé à la position max de la trajectoire donc 
			// on ne teste plus la collision
			if (x == xArrivee && y == yArrivee) {
				collision = true;
			}
		}
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, x, y);
	}
}