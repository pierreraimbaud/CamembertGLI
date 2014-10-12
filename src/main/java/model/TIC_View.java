package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;


public class TIC_View extends JComponent implements MouseListener 
{

	TIC_Controller c;

	private static final long serialVersionUID = 1L;

	private static ArrayList<Color> lc = new ArrayList<Color>();
	private static ArrayList<Arc2D.Double> la = new ArrayList<Arc2D.Double>();
	private static Rectangle2D.Double b1 =null;
	private static Rectangle2D.Double b2 =null;

	public TIC_View(){
		Color c = new Color (150,50,120);
		Color d = new Color (130,30,100);
		Color e = new Color (110,10,80);
		Color f = new Color (90,0,60);
		Color g = new Color (10,60,10);
		Color h = new Color (80,10,10);
		Color i = new Color (50,250,10);
		lc.add(c);
		lc.add(d);
		lc.add(e);
		lc.add(f);
		lc.add(g);
		lc.add(h);
		lc.add(i);
		this.addMouseListener(this);
	}

	public TIC_Controller getTIC_Controller(){
		return this.c;
	}

	public void setTIC_Controller(TIC_Controller c){
		this.c=c;
	}


	protected void paintComponent (Graphics g){
		la.clear();
		Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(2.0f));
		TIC_Model m = this.getTIC_Controller().getTIC_Model();
		ArrayList <Triplet> t = m.getTableau();
		double pourcentAngle = 0.0;

		int x = 80;
		int y = 80;
		int w = 270;
		int h = 270;
		int pie = Arc2D.PIE;		
		int angle = 0;

		int xl = 450;
		int yl = 200;
		int wl = 20;
		int hl = 20;

		int xd = 400;
		int yd = 50;
		int wd = 200;
		int hd = 120;

		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(xd, yd , wd , hd ));
		g2.setPaint(Color.BLACK);
		g2.drawString("Description",xd, yd - 5);

		if(this.getTIC_Controller().getTouchee() != -1){
			g2.setPaint(Color.BLACK);
			b1 = new Rectangle2D.Double(150, 400, 20, 20);
			b2 = new Rectangle2D.Double(265, 400, 20, 20);
			g2.setPaint(lc.get(0));
			g2.fill(b1);
			g2.setPaint(lc.get(0));
			g2.fill(b2);
			g2.setPaint(Color.WHITE);
			g2.drawString("<", 155, 415);
			g2.drawString(">", 270, 415);
		}

		for (int i = 0; i< t.size() ;i++){
			pourcentAngle = m.getPourcentage(i) * 3.6;
			Arc2D.Double a = new Arc2D.Double(x, y, w, h, angle,pourcentAngle, pie);
			if(this.getTIC_Controller().getTouchee() == i){
				a = new Arc2D.Double(x-w/8, y-h/8, 5*w/4, 5*h/4, angle,pourcentAngle, pie);
				g2.setPaint(Color.BLACK);
				g2.drawString(c.getTIC_Model().getTableau().get(i).getDescription(),xd, yd+15);
			}
			g2.setPaint(lc.get(i));
			g2.fill(a);
			la.add(a);
			g2.fill(new Rectangle2D.Double(xl, yl, wl, hl));
			g2.setPaint(Color.BLACK);
			g2.drawString(c.getTIC_Model().getTableau().get(i).getNom(),xl+30, yl+15);
			yl+=40;
			angle += pourcentAngle;
		}

		int touchee = c.getTouchee();
		c.setTouchee(-1);
		c.setToucheePassee(touchee);

		g2.setPaint(Color.WHITE);
		g2.fill(new Arc2D.Double(x+w/6, y+h/6, 2*w/3, 2*h/3, 0, 360, Arc2D.PIE));

		g2.setPaint(lc.get(0));
		g2.fill(new Arc2D.Double(x+w/4, y+h/4, w/2, h/2, 0, 360, Arc2D.PIE));

		g2.setPaint(Color.WHITE);
		g2.drawString(c.getTIC_Model().getTitre(), x+w/2-50, y+h/2);
		g2.drawString(c.getTIC_Model().getTotal()+" euros", x+w/2-50, y+h/2+20);		
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int i =0;
		while (i < la.size() && !la.get(i).contains(x, y)){
			i++;
		}
		if (i < la.size()){
			c.setTouchee(i);
			if (c.controlCamembert()){
				repaint();
			}
		}
		i =0;

		if (b1 != null){
			boolean prec = b1.contains(x, y);
			boolean suiv = b2.contains(x, y);
			if (prec) {
				c.setMouvement(-1);
			}
			if(suiv){
				c.setMouvement(1);
			}

			if (prec || suiv){
				if (c.controlMouvement()){
					repaint();
				}
			}
		}
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}

