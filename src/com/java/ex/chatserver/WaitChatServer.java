package com.java.ex.chatserver;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

//채팅 서버 클래스
public class WaitChatServer {
	
	ServerSocket svSoc = null;
	Socket soc = null;
	List<PrintWriter> list = new ArrayList<PrintWriter>();
	private static final int SERVER_PORT = 5000;
	
	public WaitChatServer() {
		try {
			//서버 소켓 생성
			svSoc = new ServerSocket(SERVER_PORT);
			
			//클라이언트 요청 대기
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