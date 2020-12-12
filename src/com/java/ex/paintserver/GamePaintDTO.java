package com.java.ex.paintserver;

import java.io.Serializable;

public class GamePaintDTO implements Serializable {
	private int signal;
	private int x1, y1, x2, y2;
	private int stroke;
	private int color;
	
	public void setStroke(int stroke) {this.stroke = stroke;}
	public void setColor(int color) {this.color = color;}
	public void setSignal(int signal) {this.signal = signal;}
	public void setX1(int x1){	this.x1 = x1;}
	public void setY1(int y1){	this.y1 = y1;}
	public void setX2(int x2){	this.x2 = x2;}
	public void setY2(int y2){	this.y2 = y2;}

	public int getStroke() {return stroke;}
	public int getColor() {return color;}
	public int getSignal() {return signal;}
	public int getX1(){	return x1;}
	public int getY1(){	return y1;}
	public int getX2(){	return x2;}
	public int getY2(){	return y2;}
}