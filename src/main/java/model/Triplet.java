package model;

public class Triplet {
	String nom;
	float valeur;
	String description;

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public float getValeur() {
		return valeur;
	}
	public void setValeur(float valeur) {
		this.valeur = valeur;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Triplet(String nom, float valeur, String description) {
		super();
		this.nom = nom;
		this.valeur = valeur;
		this.description = description;
	}
	
	public String toString() {
		return "Triplet [nom=" + nom + ", valeur=" + valeur + ", description="
				+ description + "]";
	}
	
}