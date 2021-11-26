package com.java.ex.paintserver;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.JButton;

import com.java.ex.game.GameRoom;

public class GamePaintCanvas {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int PAINT_SERVER_PORT = 5002;

	// 그림
	int x1, y1, x2, y2, z1, z2;
	int color;
	int thickness = 5;
	Socket socket = null;
	Canvas canvas;
	Image bufferImg;
	Graphics2D bufferG;
	List<GamePaintDTO> list;
	ObjectInputStream oreader;
	ObjectOutputStream owriter;
	GameRoom gameRoom;

	JButton btnCanvasBlack = null;
	JButton btnCanvasRed = null;
	JButton btnCanvasGreen = null;
	JButton btnCanvasBlue = null;
	JButton btnCanvasYellow = null;
	JButton btnCanvasEraser = null;
	JButton btnCanvasClear = null;

	public GamePaintCanvas(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
		this.btnCanvasBlack = gameRoom.getBtnCanvasBlack();
		this.btnCanvasRed = gameRoom.getBtnCanvasRed();
		this.btnCanvasGreen = gameRoom.getBtnCanvasGreen();
		this.btnCanvasBlue = gameRoom.getBtnCanvasBlue();
		this.btnCanvasYellow = gameRoom.getBtnCanvasYellow();
		this.btnCanvasEraser = gameRoom.getBtnCanvasEraser();
		this.btnCanvasClear = gameRoom.getBtnCanvasClear();

		list = new ArrayList<GamePaintDTO>();

		try {
			socket = new Socket(SERVER_IP, PAINT_SERVER_PORT);
			owriter = new ObjectOutputStream(socket.getOutputStream());
			oreader = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.out.println("서버 찾을 수 없음");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("서버 연결 실패");
			e.printStackTrace();
			System.exit(0);
		}

		canvas = new Canvas() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(bufferImg, 0, 0, this);
			}

			@Override
			public void update(Graphics g) {
				Dimension d = canvas.getSize();
				if (bufferG == null) {
					bufferImg = canvas.createImage(d.width, d.height);
					bufferG = (Graphics2D) bufferImg.getGraphics();
				}

				try {
					if (list == null) {
						return;
					}

					for (GamePaintDTO dto : list) {
						int color = dto.getColor();
						if (color == 0) {
							bufferG.setColor(Color.black);
						}
						if (color == 1) {
							bufferG.setColor(Color.red);
						}
						if (color == 2) {
							bufferG.setColor(Color.green);
						}
						if (color == 3) {
							bufferG.setColor(Color.blue);
						}
						if (color == 4) {
							bufferG.setColor(Color.yellow);
						}
						if (color == 5) {
							bufferG.setColor(canvas.getBackground());
						}

						int stroke = dto.getStroke();
						bufferG.setStroke(new BasicStroke(stroke));

						x1 = dto.getX1();
						y1 = dto.getY1();
						x2 = dto.getX2();
						y2 = dto.getY2();
						bufferG.drawLine(x1, y1, x2, y2);
					}
				} catch (ConcurrentModificationException e) {}
				paint(g);
			}
		};
		canvas.setBackground(Color.white);

		// 검정색 펜 버튼
		btnCanvasBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 0;
			}
		});
		// 빨간색 펜 버튼
		btnCanvasRed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 1;
			}
		});
		// 초록색 펜 버튼
		btnCanvasGreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 2;
			}
		});
		// 파란색 펜 버튼
		btnCanvasBlue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 3;
			}
		});
		// 노란색 펜 버튼
		btnCanvasYellow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 4;
			}
		});
		// 지우개 버튼
		btnCanvasEraser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = 5;
			}
		});
		// 초기화 버튼
		btnCanvasClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePaintDTO dto = new GamePaintDTO();
				dto.setSignal(1);

				try {
					owriter.writeObject(dto);
					owriter.flush();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				color = 0;
			}
		});

		// 마우스 프레스
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				z1 = e.getX();
				z2 = e.getY();
			}
		});

		// 마우스 드래그
		canvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();

				GamePaintDTO dto = new GamePaintDTO();
				dto.setX1(z1);
				dto.setY1(z2);
				dto.setX2(x2);
				dto.setY2(y2);
				dto.setSignal(2);
				dto.setColor(color);
				dto.setStroke(5);

				try {
					owriter.writeObject(dto);
					owriter.flush();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				z1 = e.getX();
				z2 = e.getY();
			}
		});

		Thread paintReceiveThread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						GamePaintDTO dto = (GamePaintDTO) oreader.readObject();

						if (dto.getSignal() == 1) {
							setNewCanvas();
						}
						if (dto.getSignal() == 2) {
							canvas.repaint();
							list.add(dto);
						}
						if (dto.getSignal() == 3) {
							owriter.close();
							oreader.close();
							socket.close();
							break;
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		paintReceiveThread.start();
	}
	
	public void setNewCanvas() {
		list.clear();
		bufferG = null;
		canvas.repaint();
	}

	public Socket getSocket() {return socket;}
	public Canvas getCanvas() {return canvas;}
	public ObjectInputStream getOreader() {return oreader;}
	public ObjectOutputStream getOwriter() {return owriter;}
}