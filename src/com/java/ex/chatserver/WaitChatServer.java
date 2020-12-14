package com.java.ex.chatserver;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

//ä�� ���� Ŭ����
public class WaitChatServer {
	
	ServerSocket svSoc = null;
	Socket soc = null;
	List<PrintWriter> list = new ArrayList<PrintWriter>();
	private static final int SERVER_PORT = 5000;
	
	public WaitChatServer() {
		try {
			//���� ���� ����
			svSoc = new ServerSocket(SERVER_PORT);
			
			//Ŭ���̾�Ʈ ��û ���
			while (true) {
				soc = svSoc.accept();
				new ServerBroadCast(soc, list).start();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			try {
				if (svSoc != null && !svSoc.isClosed()) {svSoc.close();}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new WaitChatServer();
	}
}