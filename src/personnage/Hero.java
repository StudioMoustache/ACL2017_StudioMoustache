package personnage;

public class Hero {
	private int x;
	private int y;
	private static int vitesse;
	
	private static Hero hero1 = new Hero(5,5,1);
	private static Hero hero2 = new Hero(5,10,1);
	
	private Hero(){
		this.x = 0;
		this.y = 0;
		this.vitesse = 1;
	}
	
	private Hero(int x, int y, int vitesse){
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
	
	public static Hero getHero1(){
		return hero1;
	}
	
	public static Hero getHero2(){
		return hero2;
	}
	
	public String toString(){
		return "Hero X : "+hero1.getX()+" Y : "+hero1.getY();
	}
}