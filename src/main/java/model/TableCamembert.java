package model;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableCamembert extends JTable implements Observer {

	private static final long serialVersionUID = 7113127637278850258L;
	private Object [][] lt;
	private TIC_Model m;

	public Object[][] getLt() {
		return lt;
	}

	public void setLt(Object[][] lt) {
		this.lt = lt;
	}

	public TIC_Model getM() {
		return m;
	}

	public void setM(TIC_Model m) {
		this.m = m;
	}


	public TableCamembert (TIC_Model m){
		super();
		this.m=m;

	}

	public TableCamembert (Object[] col, Object [][] data, TIC_Model m){
		super(data, col);
		this.lt = data;
		this.m=m;
	}

	public TableCamembert (DefaultTableModel model, TIC_Model m){
		super(model);
		this.m=m;
		// Création des colonnes 
		for (int i =0; i < this.getM().getTableau()[0].length; i++){
			model.addColumn(this.getM().getTableau()[i]);
		}
		// Ajout de lignes
		for (int i =0; i < this.getM().getTableau().length; i++){
			model.addRow(new Object[]{this.getM().getTableau()[i][0],
					this.getM().getTableau()[i][1],
					this.getM().getTableau()[i][2]});
		}
	}

	public void setModelToModel(DefaultTableModel model) {
		this.getM().setModel(model);
	}

	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}