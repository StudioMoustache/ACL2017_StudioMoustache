package graphiques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import principal.Monde;

public class MultipleKeyListener implements KeyListener {
	private Monde monde;
	
	private final TreeSet<Integer>pressed =new TreeSet<Integer>();
	
	public MultipleKeyListener(Monde m){
		this.monde=m;
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
        pressed.add(e.getExtendedKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getExtendedKeyCode());

		if (e.getExtendedKeyCode() == 80)
			monde.changePause();
	}
	
	public String toString(){
		String s="";
		Iterator<Integer> it=pressed.iterator();
		while(it.hasNext()){
			s+=""+it.next();	
		}
		return s;
		
		
	}
	
	public void deplacement(){
		int deplacementX = 0, deplacementY = 0;

		if(pressed.contains(37)){
			deplacementX -= 1;
		}
		if(pressed.contains(38)){
			deplacementY -= 1;
		}
		if(pressed.contains(39)){
			deplacementX += 1;
		}
		if(pressed.contains(40)){
			deplacementY += 1;
		}
/*
		if(pressed.contains(80)) { // lettre p pour la pause
			monde.changePause();
		}
*/
		if (deplacementX != 0 || deplacementY != 0) 
			monde.deplacerHero1(deplacementX, deplacementY);
	}
	
}
