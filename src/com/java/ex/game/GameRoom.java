package com.java.ex.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import com.java.ex.database.DataBase;
import com.java.ex.paintserver.GamePaintCanvas;
import com.java.ex.paintserver.GamePaintDTO;
import com.java.ex.waiting.WaitingRoom;

public class GameRoom extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String nickname;

	private static final String SERVER_IP = "127.0.0.1";
	private static final int CHAT_SERVER_PORT = 5001;

	// 채팅방
	Socket soc = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	String message;
	
	Canvas canvas;

	GamePaintCanvas gamePaintCanvas;

	String player1, player2, player3, player4;

	JPanel gameRoomPanel = null;
	JTextArea chattingRoom = null;
	JTextField chatting = null;
	JButton btnStart = null;
	JButton btnExit = null;
	JButton btnCanvasBlack = null;
	JButton btnCanvasRed = null;
	JButton btnCanvasGreen = null;
	JButton btnCanvasBlue = null;
	JButton btnCanvasYellow = null;
	JButton btnCanvasEraser = null;
	JButton btnCanvasClear = null;
	JLabel memberField1 = null;
	JLabel memberField2 = null;
	JLabel memberField3 = null;
	JLabel memberField4 = null;
	JLabel memberNick1 = null;
	JLabel memberNick2 = null;
	JLabel memberNick3 = null;
	JLabel memberNick4 = null;
	JLabel lblanswer = null;

	public GameRoom(String userid, String nickname) {
		this.userid = userid;
		this.nickname = nickname;

		Container ct = getContentPane();
		gameRoomPanel = new JPanel();
		gameRoomPanel.setLayout(null);

		btnStart = new JButton("게임 시작");
		btnStart.setBounds(660, 10, 150, 40);
		btnStart.setVisible(false);

		btnExit = new JButton("나가기");
		btnExit.setBounds(820, 10, 150, 40);

		btnCanvasBlack = new JButton("검정");
		btnCanvasBlack.setBounds(235, 530, 60, 40);

		btnCanvasRed = new JButton("빨강");
		btnCanvasRed.setBounds(305, 530, 60, 40);

		btnCanvasGreen = new JButton("초록");
		btnCanvasGreen.setBounds(375, 530, 60, 40);

		btnCanvasBlue = new JButton("파랑");
		btnCanvasBlue.setBounds(445, 530, 60, 40);

		btnCanvasYellow = new JButton("노랑");
		btnCanvasYellow.setBounds(515, 530, 60, 40);

		btnCanvasEraser = new JButton("지우개");
		btnCanvasEraser.setBounds(585, 530, 80, 40);

		btnCanvasClear = new JButton("전체 지우기");
		btnCanvasClear.setBounds(675, 530, 100, 40);

		gamePaintCanvas = new GamePaintCanvas(GameRoom.this);
		canvas = gamePaintCanvas.getCanvas();
		canvas.setBounds(200, 70, 610, 450);

		memberField1 = new JLabel();
		memberField1.setBounds(40, 70, 150, 100);
		memberField1.setOpaque(true);
		memberField1.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		memberField2 = new JLabel();
		memberField2.setBounds(40, 180, 150, 100);
		memberField2.setOpaque(true);
		memberField2.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		memberField3 = new JLabel();
		memberField3.setBounds(820, 70, 150, 100);
		memberField3.setOpaque(true);
		memberField3.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		memberField4 = new JLabel();
		memberField4.setBounds(820, 180, 150, 100);
		memberField4.setOpaque(true);
		memberField4.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		lblanswer = new JLabel();
		lblanswer.setBounds(450, 30, 300, 30);
		lblanswer.setFont(new Font("����", Font.BOLD, 15));
		lblanswer.setVisible(true);

		// 채팅창 및 채팅 입력창
		chattingRoom = new JTextArea();
		chattingRoom.setEditable(false);
		chattingRoom.setBounds(22, 465, 653, 184);
		JScrollPane scroll1 = new JScrollPane(chattingRoom);
		scroll1.setBounds(200, 580, 610, 100);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatting = new JTextField();
		chatting.setBounds(200, 690, 610, 20);
		gameRoomPanel.add(scroll1);
		gameRoomPanel.add(chatting);

		// 채팅창 및 채팅 입력창
		gameRoomPanel.add(canvas);
		gameRoomPanel.add(btnStart);
		gameRoomPanel.add(btnExit);
		gameRoomPanel.add(btnCanvasBlack);
		gameRoomPanel.add(btnCanvasRed);
		gameRoomPanel.add(btnCanvasGreen);
		gameRoomPanel.add(btnCanvasBlue);
		gameRoomPanel.add(btnCanvasYellow);
		gameRoomPanel.add(btnCanvasEraser);
		gameRoomPanel.add(btnCanvasClear);
		gameRoomPanel.add(memberField1);
		gameRoomPanel.add(memberField2);
		gameRoomPanel.add(memberField3);
		gameRoomPanel.add(memberField4);
		gameRoomPanel.add(lblanswer);

		// --------------------- Button Event ---------------------
		// 게임시작 버튼 이벤트
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				db.Select("SELECT * FROM RoomMember");
				try {
					db.rs = db.pstmt.executeQuery();

					if (db.rs.next()) {
						if (db.rs.getString("Player2") != null) {
							db.Select("SELECT * FROM answer");
							try {
								db.rs = db.pstmt.executeQuery();

								ArrayList<String> randomAnswer = new ArrayList<String>();
								while (db.rs.next()) {
									randomAnswer.add(db.rs.getString("SolveProblems"));
								}

								Random ran = new Random();
								int ran2 = ran.nextInt(randomAnswer.size());
								
								String answer = randomAnswer.get(ran2);
								lblanswer.setText(answer);
								btnStart.setEnabled(false);
								
								db.Select("SELECT * FROM RoomMember WHERE Player1 = ?");
								db.pstmt.setString(1, nickname);
								db.rs = db.pstmt.executeQuery();
								
								if (db.rs.next()) {
									chatting.setEnabled(false);
								}
								writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
								writer.println("start:");
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "게임시작에 필요한 인원이 부족합니다.\n최소 인원은 2명입니다.", "캐치마인드", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				db.Close();
			}
		});
		// 나가기 버튼 이벤트
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				try {
					// 방장 닉네임으로 된 방 찾기
					db.Select("SELECT * FROM Game WHERE RoomOwner = ?");

					db.pstmt.setString(1, nickname);
					db.rs = db.pstmt.executeQuery();

					if (db.rs.next()) {
						// 방장이 방을 나오면 방장닉넴으로 된 레코드 삭제(Game)
						db.Delete("DELETE FROM Game WHERE RoomOwner = ?");
						db.pstmt.setString(1, nickname);
						db.pstmt.executeUpdate();

						// 방장이 방을 나오면 방장닉넴으로 된 레코드 삭제2(RoomMember)
						db.Delete("DELETE FROM RoomMember WHERE Player1 = ?");
						db.pstmt.setString(1, nickname);
						db.pstmt.executeUpdate();

					} else {
						db.Select("SELECT * FROM Game WHERE RoomOwner = ?");
						db.pstmt.setString(1, player1);
						db.rs = db.pstmt.executeQuery();

						if (db.rs.next()) {
							db.Select("SELECT * FROM RoomMember WHERE RoomTitle = ?");
							db.pstmt.setString(1, db.rs.getString("RoomTitle"));
							db.rs = db.pstmt.executeQuery();

							if (db.rs.next()) {
								if (db.rs.getString("Player2").equals(nickname)) {
									db.Delete("UPDATE RoomMember SET Player2 = ? WHERE Player2 = ?");
									db.pstmt.setString(1, null);
									db.pstmt.setString(2, nickname);

									int result = db.pstmt.executeUpdate();

									if (1 == result) {
										new WaitingRoom(userid, nickname);
										dispose();
									}
								}
								if (db.rs.getString("Player3").equals(nickname)) {
									db.Delete("UPDATE RoomMember SET Player3 = ? WHERE Player3 = ?");
									db.pstmt.setString(1, null);
									db.pstmt.setString(2, nickname);

									int result = db.pstmt.executeUpdate();

									if (1 == result) {
										new WaitingRoom(userid, nickname);
										dispose();
									}
								}
								if (db.rs.getString("Player4").equals(nickname)) {
									db.Delete("UPDATE RoomMember SET Player4 = ? WHERE Player4 = ?");
									db.pstmt.setString(1, null);
									db.pstmt.setString(2, nickname);

									int result = db.pstmt.executeUpdate();

									if (1 == result) {
										new WaitingRoom(userid, nickname);
										dispose();
									}
								}
							}
						}
					}
				} catch (NullPointerException e2) {
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				GamePaintDTO gamePaintDTO = new GamePaintDTO();
				gamePaintDTO.setSignal(3);
				try {
					gamePaintCanvas.getOwriter().writeObject(gamePaintDTO);
					gamePaintCanvas.getOwriter().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				db.Close();
			}
		});
		// 채팅방 전송 이벤트
		chatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chatting.getText().equals("")) {

				} else {
					try {
						writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);

						message = chatting.getText();
						writer.println("message:" + message);
						chatting.setText("");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		Thread rightAnswer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						String chatLastField = chattingRoom.getText().substring(chattingRoom.getText().length() - lblanswer.getText().length()-1);
						System.out.println(chatLastField);
						
						if ((lblanswer.getText().equals(chatLastField.trim()) & (!lblanswer.getText().equals("")))) {
							btnStart.setEnabled(true);
							if (btnStart.isEnabled()) {
								chatting.setEnabled(true);
							}
							lblanswer.setText(chatLastField + "정답입니다.");
							
							writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);

							message = chatLastField;
							writer.println("server:" + message);
						}
					} catch (Exception e) {}

					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		rightAnswer.start();
		// 방장이 나갔을 때 방에 접속되어 있는 유저를 추방
		Thread disRoom = new Thread(new Runnable() {
			@Override
			public void run() {
				DataBase db = new DataBase();

				db.Select("SELECT * FROM RoomMember");
				while (true) {
					try {
						db.rs = db.pstmt.executeQuery();

						if (!db.rs.next()) {
							if (gameRoomPanel.isVisible()) {
								new WaitingRoom(userid, nickname);
								dispose();
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		disRoom.start();
		// 플레이어 불러오는 스레드
		Thread playerLoad = new Thread(new Runnable() {
			@Override
			public void run() {
				DataBase db = new DataBase();
				db.Select("SELECT * FROM RoomMember");
				while (true) {
					try {
						db.rs = db.pstmt.executeQuery();

						if (db.rs.next()) {
							player1 = db.rs.getString("Player1");
							player2 = db.rs.getString("Player2");
							player3 = db.rs.getString("Player3");
							player4 = db.rs.getString("Player4");

							memberField1.setText(player1);
							memberField2.setText(player2);
							memberField3.setText(player3);
							memberField4.setText(player4);
						}

						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		playerLoad.start();

		btnStartVisible();
		gameChatting();
		gameChatReceive(soc);

		ct.add(gameRoomPanel);

		setTitle("캐치마인드");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	// --------------------- Method ---------------------
	// 채팅방 접속 메소드
	public void gameChatting() {
		try {
			soc = new Socket(SERVER_IP, CHAT_SERVER_PORT);
			System.out.println(nickname + "���� ������ ����Ǿ����ϴ�.");

			writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
			String receiveData = "join:" + nickname + "\r\n";
			writer.println(receiveData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 채팅방 브로드캐스트 수신 메소드
	public void gameChatReceive(Socket soc) {
		this.soc = soc;
		Runnable receiver = new Runnable() {
			@Override
			public void run() {
				try {
					reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					while (true) {
						String msg = reader.readLine();
						chattingRoom.append(msg + "\n");
						chattingRoom.setCaretPosition(chattingRoom.getText().length());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread clThread = new Thread(receiver);
		clThread.start();
	}

	// 방장만 버튼이 보이게 하는 메소드
	public void btnStartVisible() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM RoomMember WHERE Player1 = ?");

		try {
			db.pstmt.setString(1, nickname);
			db.rs = db.pstmt.executeQuery();

			if (db.rs.next()) {
				btnStart.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JButton getBtnCanvasBlack() {return btnCanvasBlack;}
	public JButton getBtnCanvasRed() {return btnCanvasRed;}
	public JButton getBtnCanvasGreen() {return btnCanvasGreen;}
	public JButton getBtnCanvasBlue() {return btnCanvasBlue;}
	public JButton getBtnCanvasYellow() {return btnCanvasYellow;}
	public JButton getBtnCanvasEraser() {return btnCanvasEraser;}
	public JButton getBtnCanvasClear() {return btnCanvasClear;}
}