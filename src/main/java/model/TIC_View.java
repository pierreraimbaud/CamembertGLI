package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JComponent;


public class TIC_View extends JComponent implements MouseListener, Observer
{

	TIC_Controller c;
	TIC_Model m;

	private static final long serialVersionUID = 1L;

	private static ArrayList<Color> lc = new ArrayList<Color>();
	private static ArrayList<Arc2D.Double> la = new ArrayList<Arc2D.Double>();
	private static Rectangle2D.Double b1 =null;
	private static Rectangle2D.Double b2 =null;
	private static Rectangle2D.Double b3 =null;
	private static Rectangle2D.Double b4 =null;
	private static float angle = 0;
	private static boolean mouseDown = false;
	private static boolean isRunning = false;
	public TIC_View(){
		/*Color c = new Color (210,100,170);
		Color d = new Color (180,70,140);
		Color e = new Color (150,50,120);
		Color f = new Color (120,30,90);
		Color g = new Color (110,10,80);
		Color h = new Color (90,0,60);
		Color i = new Color (60,0,30);
		lc.add(c);
		lc.add(d);
		lc.add(e);
		lc.add(f);
		lc.add(g);
		lc.add(h);
		lc.add(i);*/
		this.addMouseListener(this);
	}

	public TIC_Controller getTIC_Controller(){
		return this.c;
	}

	public void setTIC_Controller(TIC_Controller c){
		this.c=c;
	}

	public TIC_Model getTIC_Model(){
		return this.m;
	}

	public void setTIC_Model(TIC_Model m){
		this.m=m;
	}

	protected void paintComponent (Graphics g){
		la.clear();
		Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(2.0f));
		TIC_Model m = this.getTIC_Model();
		//ArrayList <Triplet> t = m.getTableau();
		Object [][] t = m.getTableau();
		float pourcentAngle = 0;

		int x = 80;
		int y = 80;
		int w = 270;
		int h = 270;
		int pie = Arc2D.PIE;		

		int xl = 450;
		int yl = 200;
		int wl = 12;
		int hl = 12;

		int xd = 380;
		int yd = 50;
		int wd = 200;
		int hd = 120;

		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(xd, yd , wd , hd ));
		g2.setPaint(Color.BLACK);
		g2.drawString("Description",xd, yd - 5);

		if(this.getTIC_Controller().getTouchee() != -1){
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

		b3 = new Rectangle2D.Double(150, 450, 20, 20);
		b4 = new Rectangle2D.Double(265, 450, 20, 20);
		g2.setPaint(new Color(40,40,80));
		g2.fill(b3);
		g2.setPaint(new Color(40,40,80));
		g2.fill(b4);
		g2.setPaint(Color.WHITE);
		g2.drawString("<", 155, 465);
		g2.drawString(">", 270, 465);


		double delta = 1.5;

		//for (int i = 0; i< t.size() ;i++){
		for (int i = 0; i< t.length ;i++){
			pourcentAngle = m.getPourcentage(i)*3.6f;
			Arc2D.Double a = new Arc2D.Double(x, y, w, h, angle,pourcentAngle-delta, pie);
			if(this.getTIC_Controller().getTouchee() == i){
				a = new Arc2D.Double(x-w/8, y-h/8, 5*w/4, 5*h/4, angle,pourcentAngle- delta, pie);
				g2.setPaint(Color.BLACK);
				g2.drawString((String)this.getTIC_Model().getTableau()[i][2],xd, yd+15);
			}
			if (i<lc.size()){
				g2.setPaint(lc.get(i));
			}
			else {
				//Color c = new Color(10,80,80);
				Random rd = new Random();
				int rr = rd.nextInt(255);
				int gg  = rd.nextInt(255);
				int bb = rd.nextInt(255);
				Color c = new Color (rr,gg,bb);
				lc.add(c);
				g2.setPaint(lc.get(i));
			}
			g2.fill(a);
			la.add(a);
			g2.fill(new Rectangle2D.Double(xl, yl, wl, hl));
			g2.setPaint(Color.BLACK);
			//g2.drawString(this.getTIC_Model().getTableau().get(i).getNom(),xl+30, yl+15);
			g2.drawString((String)this.getTIC_Model().getTableau()[i][0],xl+25, yl+10);
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
		g2.drawString(this.getTIC_Model().getTitre(), x+w/2-50, y+h/2);
		g2.drawString(this.getTIC_Model().getTotal()+" euros", x+w/2-50, y+h/2+20);		
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
		/*if (b3.contains(x, y)){
			System.out.println("gauche");
			angle -= 20;
			repaint();
		}*/
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	Date pressedTime;
	long timeClicked;


	private synchronized boolean checkAndMark() {
		if (isRunning) return false;
		isRunning = true;
		return true;
	}

	private void initThread(final boolean sens) {
		if (checkAndMark()) {
			new Thread() {
				public void run() {
					do {
						try {
							sleep(7);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (sens){
							angle -= 1;
						}
						else angle += 1;
						repaint();               
					} while (mouseDown);
					isRunning = false;
				}
			}.start();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (b3.contains(x, y)){
			if (e.getButton() == MouseEvent.BUTTON1) {
				mouseDown = true;
				initThread(true);
			}
		}
		if (b4.contains(x, y)){
			if (e.getButton() == MouseEvent.BUTTON1) {
				mouseDown = true;
				initThread(false);
			}
		}
		//pressedTime = new Date();
	}
	/*@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if (b3.contains(x, y)){
			System.out.println("ga2uche");
			while(e.isControlDown()){
				angle -= 10;
				repaint();
			}
		}
		System.out.println("fini");
	}*/

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseDown = false;
		}
		/*// TODO Auto-generated method stub
		timeClicked = new Date().getTime() - pressedTime.getTime();
		if (timeClicked >= 30) {
			// DO YOUR ACTION HERE
			int x = e.getX();
			int y = e.getY();
			if (b3.contains(x, y)){
					angle -= 10;
					repaint();
			}
			System.out.println("hola");
		}*/
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}