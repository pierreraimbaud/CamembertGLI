package model;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		String titre = "Dépenses du mois";

		ArrayList<Triplet> lt = new ArrayList<Triplet>();

		Triplet e = new Triplet ("Loyer", 200, "Pour mamie");
		lt.add(e);

		Triplet f = new Triplet ("Nourriture", 150, "Pour estomac");
		lt.add(f);

		Triplet g = new Triplet ("Vetements", 20, "Pour mon corps");
		lt.add(g);

		Triplet h = new Triplet ("Chachou", 60, "Pour le gros chachou");
		lt.add(h);

		TIC_Model m = new TIC_Model(titre, lt);
		m.setTotalModel();

		// création de l'application
		JFrame fenetre = new JFrame();

		// affectation du titre
		fenetre.setTitle("Camembert Interactif Application");

		// affectation de l'opération à effectuer lors de la fermeture de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// taille et position
		fenetre.setPreferredSize(new Dimension(640, 480));

		TIC_View v = new TIC_View();
		
		fenetre.getContentPane().add(v);

		
		TIC_Controller c = new TIC_Controller(m, v);
		v.setTIC_Controller(c);
				
		// rendre la fenêtre visible, pack fait en sorte que tous les composants de l'application soient à
		// leur preferredSize, ou au dessus
		fenetre.pack();
		fenetre.setVisible(true);
		
	}

}
