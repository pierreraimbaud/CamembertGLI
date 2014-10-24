package model;

//import java.util.ArrayList;
import java.util.Observable;

import javax.swing.table.DefaultTableModel;

public class TIC_Model extends Observable
{	
	String titre;
	//ArrayList<Triplet> tableau;
	Object [][] tableau;
	DefaultTableModel model;
	float total;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
		setChanged();
		notifyObservers(titre);
	}

	/*public ArrayList<Triplet> getTableau() {
		return tableau;
	}

	public void setTableau(ArrayList<Triplet> tableau) {
		this.tableau = tableau;
	}*/

	public Object [][] getTableau() {
		return tableau;
	}

	public void setTableau(Object [][] tableau) {
		this.tableau = tableau;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
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

	public TIC_Model(String titre, Object [][] tableau) {
		this.titre = titre;
		this.tableau = tableau;
	}

	public void setTotalModel(){
		float rep = 0;
		for (int i =0; i< this.tableau.length; i++){
			// On convertit en String et on parse, car dans la table les données sont String
			rep += Float.parseFloat("" + this.getTableau()[i][1]); //en 1 il y a la valeur
		}
		this.setTotal(rep);
	}

	// Calcul de la proportion d'une donnée sur le total
	public float getPourcentage(int pos){
		float valPos = Float.parseFloat(""+ this.getTableau()[pos][1]);
		float total = this.getTotal();
		return valPos/total*100;
	}

	// Edition d'une donnée du modèle
	public void setCellTableau(int i, int j, Object cell) {
		this.tableau[i][j] = cell;
		setTotalModel();
		setChanged();
		notifyObservers(tableau);
	}

	// Ajout d'une nouvelle donnée
	public void addValeur(Object t []){
		Object [][] ancien = this.getTableau();
		int taille = ancien.length;
		int largeur = ancien[0].length;
		Object [][] nouveau = new Object [taille+1][largeur];
		for (int i = 0; i < taille; i++){
			nouveau[i]=ancien[i];
		}
		nouveau[taille] = t;
		// On affecte le nouveau tableau
		this.setTableau(nouveau);
		// On recalcule le total
		this.setTotalModel();
		// On ajoute une colonne au model
		model.addRow(t);
		setChanged();
		notifyObservers(tableau);
		notifyObservers(model);
	}

	public String toString() {
		return "TIC_Model [titre=" + titre + ", tableau=" + tableau
				+ ", total=" + total + "]";
	}
}