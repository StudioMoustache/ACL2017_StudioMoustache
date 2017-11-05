package personnage;

public class Monstre {
	private int x;
	private int y;
	private static int vitesse;
	private String nom; // pour les reperer sur la console. Peut etre utile pour la toute fin aussi
	
	public Monstre(String nom){
		this.nom = nom;
		this.x = 0;
		this.y = 0;
		this.vitesse = 1;
	}
	
	public Monstre(int x, int y, int vitesse, String nom){
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
	}

	
	public void deplacer(int x, int y){ // a changer peut etre
		this.x += x;
		this.y += y;
	}
	
	
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
		return "Monstre X : "+this.getX()+" Y : "+this.getY();
	}
	
}
