package com.java.ex.waiting;

import java.awt.Container;

import javax.swing.JFrame;

public class WaitingRoom extends JFrame{
	public WaitingRoom() {
		Container ct = getContentPane();
		
		setTitle("캐치마인드 대기실");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}