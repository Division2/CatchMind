package com.java.ex.paintserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class GamePaintHandler extends Thread {

	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private List<GamePaintHandler> list;

	public GamePaintHandler(Socket socket, List<GamePaintHandler> list) {
		this.socket = socket;
		this.list = list;

		try {
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				GamePaintDTO dto = (GamePaintDTO)reader.readObject();
				
				if (dto.getSignal() == 1) {
					writer.writeObject(dto);
					broadcast(dto);
				}

				if (dto.getSignal() == 2) {
					broadcast(dto);
				}

				if (dto.getSignal() == 3) {
					GamePaintDTO exitDTO = new GamePaintDTO();
					exitDTO.setSignal(3);
					writer.writeObject(exitDTO);
					writer.flush();

					reader.close();
					writer.close();
					socket.close();

					list.remove(GamePaintHandler.this);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcast(GamePaintDTO dto) {
		for (GamePaintHandler handler : list) {
			try {
				handler.writer.writeObject(dto);
				handler.writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}