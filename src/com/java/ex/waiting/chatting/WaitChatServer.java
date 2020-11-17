package com.java.ex.waiting.chatting;

import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JOptionPane;

//채팅 서버 클래스
public class WaitChatServer {
	
	ServerSocket svSoc = null;
	Socket soc = null;
	HashMap<String, BufferedWriter> hashmap;
	private static final int SERVER_PORT = 4444;
	
	public WaitChatServer() {
	
		try {
			svSoc = new ServerSocket(SERVER_PORT);
			hashmap = new HashMap<String, BufferedWriter>();
			
			while (true) {
				soc = svSoc.accept();
				
				if (soc != null) {
					WaitChatSBC waitChatSBC = new WaitChatSBC(soc, hashmap);
					waitChatSBC.start();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new WaitChatServer();
	}
}