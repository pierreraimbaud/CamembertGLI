package model;

import java.util.ArrayList;

public class TIC_Model
{	
	String titre;
	ArrayList<Triplet> tableau;
	float total;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public ArrayList<Triplet> getTableau() {
		return tableau;
	}

	public void setTableau(ArrayList<Triplet> tableau) {
		this.tableau = tableau;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public TIC_Model() {
		this.titre = "Mon titre";
		this.tableau = null;
		this.total = 0;
	}

	public TIC_Model(String titre, ArrayList<Triplet> tableau) {
		this.titre = titre;
		this.tableau = tableau;
	}

	public void setTotalModel(){
		float rep = 0;
		for (int i =0; i< this.tableau.size(); i++){
			rep += this.getTableau().get(i).valeur;
		}
		this.setTotal(rep);
	}

	public void addValeur(Triplet t){
		this.getTableau().add(t);
		this.total += t.getValeur();
	}

	public String toString() {
		return "TIC_Model [titre=" + titre + ", tableau=" + tableau
				+ ", total=" + total + "]";
	}

	public int getPourcentage(int pos){
		float valPos = this.getTableau().get(pos).valeur;
		float total = this.getTotal();
		return (int) (valPos/total*100+1);
	}
}