package model;

public class TIC_Controller
{
	TIC_Model m;
	TIC_View v;
	int toucheePassee = -1;
	int touchee = -1;
	int mouvement = 0;

	
	public int getToucheePassee() {
		return touchee;
	}

	public void setToucheePassee(int toucheePassee) {
		this.touchee = toucheePassee;
	}
	
	public int getTouchee() {
		return touchee;
	}

	public void setTouchee(int touchee) {
		this.toucheePassee = this.touchee;
		this.touchee = touchee;
	}

	public int getMouvement() {
		return mouvement;
	}

	public void setMouvement(int mouvement) {
		this.mouvement = mouvement;
	}

	public TIC_Controller(TIC_Model m, TIC_View v){
		this.m=m;
		this.v=v;
	}

	public TIC_Model getTIC_Model () {
		return m;
	}

	public void setTIC_Model(TIC_Model m) {
		this.m = m;
	}
	
	public boolean controlCamembert(){
		return this.getTouchee() != -1;
	}
	
	/*public boolean controlMouvement(){
		touchee = (touchee + mouvement)%m.getTableau().size();
		if (touchee == -1){
			touchee = m.getTableau().size()-1;			
		}
		return this.getMouvement() != 0;
	}*/
	public boolean controlMouvement(){
	touchee = (touchee + mouvement)%m.getTableau().length;
	if (touchee == -1){
		touchee = m.getTableau().length-1;			
	}
	return this.getMouvement() != 0;
}
}

