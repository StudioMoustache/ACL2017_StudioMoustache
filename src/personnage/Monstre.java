package personnage;

import principal.Monde;

public class Monstre extends Personnage {
	private int dimensionSprite;
	private int vie;
	private int invincibilite = 0;
	private int valeurPoint; //le nombre de point que le hero gagne en tuant le monstre
	
	// ----- Constructeurs -----
	
	/*public Monstre(Monde monde){
		super(0, 0, 20, new AffichageSprite(Color.RED, dimensionSprite), monde);
		this.valeurPoint=10;
	}
	
	public Monstre(int x, int y, Monde monde) {
		super(x, y, 1, new AffichageSprite(Color.RED, dimensionSprite), monde);
		this.valeurPoint=10; //le nombre de point que le hero gagne en tuant le monstre est de 10 de base
	}*/
	
	public Monstre(int x, int y, int type, Monde monde){
		super(x, y, 1, type, monde);

		switch(type){
			case 2 :	this.valeurPoint = 10;
						this.vie = 1; break;
			case 3 :	this.valeurPoint = 20;
						this.vie = 2; break;
		}
	}

	// ----- Fonction ------ //

	// Fonction expliquee dans la classe abstraite Personnage
	public void collisionObjectif() {
		monde.collisionMonstreNexus(this);
	}
	
	// ----- Getters & Setters ----- 
	
	public void decVie(){
		this.invincibilite = 10;
		this.vie--;
	}

	public void decInvincibilite(){
		this.invincibilite--;
	}

	public boolean estInvincible(){
		if(this.invincibilite > 0){
			return true;
		}
		return false;
	}

	public int getVie(){
		return vie;
	}
	
	public int getValeurPoint(){
		return valeurPoint;
	}

	public int getDimension(){
		return dimensionSprite;
	}
	
	public String toString(){
		return "Monstre("+this.getX()+":"+this.getY()+")";
	}
}
