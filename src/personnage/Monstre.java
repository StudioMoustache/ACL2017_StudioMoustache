package personnage;

public class Monstre {
	private int x;
	private int y;
	private static int vitesse;
	
	
	// ----- Constructeurs -----
	
	public Monstre(){
		this.x = 0;
		this.y = 0;
		this.vitesse = 1;
	}
	
	public Monstre(int x, int y) {
		this.x = x;
		this.y = y;
		this.vitesse = 1;
	}
	
	public Monstre(int x, int y, int vitesse){;
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
	}

	
	// ----- Fonctions -----
	
	public void deplacer(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	
	// ----- Getters & Setters ----- 
	
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
	
	public String toString(){
		return "Monstre("+this.getX()+":"+this.getY()+")";
	}
	
}
