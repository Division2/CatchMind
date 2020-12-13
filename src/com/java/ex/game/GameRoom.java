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

	private String userid;
	private String nickname;

	private static final String SERVER_IP = "127.0.0.1";
	private static final int CHAT_SERVER_PORT = 5001;

	// ä�ù�
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
	JLabel memberScore1 = null;
	JLabel memberScore2 = null;
	JLabel memberScore3 = null;
	JLabel memberScore4 = null;
	JLabel lblanwser = null;

	public GameRoom(String userid, String nickname) {
		this.userid = userid;
		this.nickname = nickname;

		Container ct = getContentPane();
		gameRoomPanel = new JPanel();
		gameRoomPanel.setLayout(null);

		btnStart = new JButton("���� ����");
		btnStart.setBounds(660, 10, 150, 40);
		btnStart.setVisible(false);

		btnExit = new JButton("������");
		btnExit.setBounds(820, 10, 150, 40);

		btnCanvasBlack = new JButton("����");
		btnCanvasBlack.setBounds(235, 530, 60, 40);

		btnCanvasRed = new JButton("����");
		btnCanvasRed.setBounds(305, 530, 60, 40);

		btnCanvasGreen = new JButton("�ʷ�");
		btnCanvasGreen.setBounds(375, 530, 60, 40);

		btnCanvasBlue = new JButton("�Ķ�");
		btnCanvasBlue.setBounds(445, 530, 60, 40);

		btnCanvasYellow = new JButton("���");
		btnCanvasYellow.setBounds(515, 530, 60, 40);

		btnCanvasEraser = new JButton("���찳");
		btnCanvasEraser.setBounds(585, 530, 80, 40);

		btnCanvasClear = new JButton("��ü �����");
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

		lblanwser = new JLabel();
		lblanwser.setBounds(450, 30, 100, 30);
		lblanwser.setFont(new Font("����", Font.BOLD, 15));
//		lblanwser.setVisible(false);

		// ä��â �� ä�� �Է�â
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
		// ä��â �� ä�� �Է�â

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
		gameRoomPanel.add(lblanwser);

		// --------------------- Button Event ---------------------
		// ���ӽ��� ��ư �̺�Ʈ
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
								lblanwser.setVisible(true);
								lblanwser.setText(randomAnswer.get(ran2));

								btnStart.setEnabled(false);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "���ӽ��ۿ� �ʿ��� �ο��� �����մϴ�.\n�ּ� �ο��� 2���Դϴ�.", "ĳġ���ε�", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				db.Close();
			}
		});
		// ������ ��ư �̺�Ʈ
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				try {
					// ���� �г������� �� �� ã��
					db.Select("SELECT * FROM Game WHERE RoomOwner = ?");

					db.pstmt.setString(1, nickname);
					db.rs = db.pstmt.executeQuery();

					if (db.rs.next()) {
						// ������ ���� ������ ����г����� �� ���ڵ� ����(Game)
						db.Delete("DELETE FROM Game WHERE RoomOwner = ?");
						db.pstmt.setString(1, nickname);

						int result = db.pstmt.executeUpdate();

						// ������ ���� ������ ����г����� �� ���ڵ� ����2(RoomMember)
						db.Delete("DELETE FROM RoomMember WHERE Player1 = ?");
						db.pstmt.setString(1, nickname);

						int result2 = db.pstmt.executeUpdate();

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
		// ä�ù� ���� �̺�Ʈ
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
						String test = chattingRoom.getText().substring(chattingRoom.getText().length() - lblanwser.getText().length());
						
						if (lblanwser.getText().length() == test.length()) {
							System.out.println("��ġ");
						} else {
							System.out.println("No");
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
		// ������ ������ �� �濡 ���ӵǾ� �ִ� ������ �߹�
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
		// �÷��̾� �ҷ����� ������
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

		setTitle("ĳġ���ε�");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	// --------------------- Method ---------------------
	// ä�ù� ���� �޼ҵ�
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

	// ä�ù� ��ε�ĳ��Ʈ ���� �޼ҵ�
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

	// ���常 ��ư�� ���̰� �ϴ� �޼ҵ�
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