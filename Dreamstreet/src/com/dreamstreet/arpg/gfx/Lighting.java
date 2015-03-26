package com.dreamstreet.arpg.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
	//nav finals
	private int x,y;
	private BufferedImage img;

	public Lighting(BufferedImage img, int x, int y){
		this.setX(x);
		this.setY(y);
		this.img = img;
	}

	public Lighting(BufferedImage img){
		this.img = img;
		this.setX(0);
		this.setY(0);
	}

	public void draw(Graphics g, int scale){
		g.drawImage(img, this.x, this.y,66*scale,36*scale, null);
	}

	public BufferedImage getImage(){
		return this.img;
	}
	
	public void setImage(BufferedImage img){
		this.img = img;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	
}
