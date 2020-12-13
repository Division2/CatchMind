package com.java.ex.paintserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GamePaintServer {
	
	ServerSocket svSoc = null;
	Socket soc = null;
	private List<GamePaintHandler> list;
	private static final int SERVER_PORT = 5002;
	
	public GamePaintServer() {
		try {
			svSoc = new ServerSocket(SERVER_PORT);
			System.out.println(SERVER_PORT);
			
			list = new ArrayList<GamePaintHandler>();
			while (true) {
				soc = svSoc.accept();
				GamePaintHandler handler = new GamePaintHandler(soc, list);
				handler.start();
				list.add(handler);
			}
		} catch (Exception e) {}
	}
	
	public static void main(String[] args) {
		new GamePaintServer();
	}
}