package com.java.ex.waiting;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.java.ex.database.DataBase;
import com.java.ex.game.GameRoom;
import com.java.ex.login.Login;

public class WaitingRoom extends JFrame {
	
	private String userid;
	private String nickname;
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 5000;
	boolean inChat = false;
	
	Socket soc = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	String message;

	JPanel waitingRoomPanel = null;
	JButton btnJoinRoom = null;
	JButton btnCreateRoom = null;
	JButton btnRanking = null;
	JButton btnUpdateProfile = null;
	JButton btnLogout = null;
	JButton btnExit = null;
	DefaultTableModel roomModel = null;
	DefaultTableModel userModel = null;
	JTable tabRoom = null;
	JTable tabUser = null;
	JTextArea chattingRoom = null;
	JTextField chatting = null;
	JButton btnSendChatting = null;
	JLabel lblMyProfile = null;
	JLabel lblMyNickName = null;
	JLabel lblMyLevel = null;
	JLabel lblMyExp = null;
	JTextField txtMyNickName = null;
	JTextField txtMyLevel = null;
	JTextField txtMyExp = null;
	
	public WaitingRoom(String userid, String nickname) {
		// --------------------- Login Form Disign ---------------------
		this.userid = userid;
		this.nickname = nickname;
		
		if (null == userid) {
			JOptionPane.showMessageDialog(null, "비정상적인 접근입니다!", "캐치마인드", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		Container ct = getContentPane();
		waitingRoomPanel = new JPanel();
		waitingRoomPanel.setLayout(null);

		btnJoinRoom = new JButton("참여하기");
		btnCreateRoom = new JButton("방만들기");
		btnRanking = new JButton("랭킹");
		btnUpdateProfile = new JButton("정보 수정");
		btnLogout = new JButton("로그아웃");
		btnExit = new JButton("게임종료");
		btnSendChatting = new JButton("보내기");
		lblMyProfile = new JLabel("내 정보");
		lblMyNickName = new JLabel("닉네임");
		lblMyLevel = new JLabel("레벨");
		lblMyExp = new JLabel("경험치");
		txtMyNickName = new JTextField(10);
		txtMyLevel = new JTextField(10);
		txtMyExp = new JTextField(10);
		
		btnJoinRoom.setBounds(20, 20, 100, 50);
		btnCreateRoom.setBounds(140, 20, 100, 50);
		btnRanking.setBounds(260, 20, 100, 50);
		btnUpdateProfile.setBounds(380, 20, 100, 50);
		btnLogout.setBounds(769, 20, 100, 50);
		btnExit.setBounds(889, 20, 100, 50);
		btnSendChatting.setBounds(535, 660, 85, 20);
		lblMyProfile.setBounds(785, 455, 100, 50);
		lblMyProfile.setFont(new Font("맑은 고딕", Font.BOLD, 15));;
		lblMyNickName.setBounds(720, 500, 100, 50);
		lblMyLevel.setBounds(720, 540, 100, 50);
		lblMyExp.setBounds(720, 580, 100, 50);
		txtMyNickName.setBounds(770, 515, 100, 20);
		txtMyNickName.setEditable(false);
		txtMyNickName.setHorizontalAlignment(JTextField.CENTER);
		txtMyLevel.setBounds(770, 555, 100, 20);
		txtMyLevel.setEditable(false);
		txtMyLevel.setHorizontalAlignment(JTextField.CENTER);
		txtMyExp.setBounds(770, 595, 100, 20);
		txtMyExp.setEditable(false);
		txtMyExp.setHorizontalAlignment(JTextField.CENTER);
		
		waitingRoomPanel.add(btnJoinRoom);
		waitingRoomPanel.add(btnCreateRoom);
		waitingRoomPanel.add(btnRanking);
		waitingRoomPanel.add(btnUpdateProfile);
		waitingRoomPanel.add(btnLogout);
		waitingRoomPanel.add(btnExit);
		waitingRoomPanel.add(btnSendChatting);
		waitingRoomPanel.add(lblMyProfile);
		waitingRoomPanel.add(lblMyNickName);
		waitingRoomPanel.add(lblMyLevel);
		waitingRoomPanel.add(lblMyExp);
		waitingRoomPanel.add(txtMyNickName);
		waitingRoomPanel.add(txtMyLevel);
		waitingRoomPanel.add(txtMyExp);
		
		//대기방 테이블
		String[] roomList = { "No", "방 제목", "방장", "인원", "비고" };
		roomModel = new DefaultTableModel(roomList, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tabRoom = new JTable(roomModel);
		tabRoom.setRowHeight(50);
		tabRoom.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabRoom.getColumnModel().getColumn(1).setPreferredWidth(300);
		tabRoom.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabRoom.getTableHeader().setReorderingAllowed(false);
		JScrollPane scroll1 = new JScrollPane(tabRoom);
		scroll1.setBounds(20, 90, 600, 350);
		waitingRoomPanel.add(scroll1);
		//대기방 테이블
		
		//유저목록 테이블
		String[] userList = { "닉네임", "레벨"};
		userModel = new DefaultTableModel(userList, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tabUser = new JTable(userModel);
		tabUser.setRowHeight(25);
		tabUser.getTableHeader().setReorderingAllowed(false);
		JScrollPane scroll2 = new JScrollPane(tabUser);
		scroll2.setBounds(640, 90, 350, 350);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		waitingRoomPanel.add(scroll2);
		//유저목록 테이블
		
		// 채팅창 및 채팅 입력창
		chattingRoom = new JTextArea();
		chattingRoom.setEditable(false);
		chattingRoom.setBounds(22, 465, 653, 184);
		JScrollPane scroll3 = new JScrollPane(chattingRoom);
		scroll3.setBounds(20, 450, 600, 200);
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatting = new JTextField();
		chatting.setBounds(20, 660, 500, 20);
		waitingRoomPanel.add(scroll3);
		waitingRoomPanel.add(chatting);
		// 채팅창 및 채팅 입력창
		
		// --------------------- Button Event ---------------------
		//참여하기 버튼 이벤트
		btnJoinRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tabRoom.getSelectedRow();
				Object test = tabRoom.getValueAt(row, 4);
					
				System.out.println(test);
				if (test.equals("비공개")) {
					new JoinRoom(userid, nickname);
				}
				else {
					new GameRoom(userid, nickname);
					dispose();
				}
			}
		});
		tabRoom.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = tabRoom.getSelectedRow();
					Object test = tabRoom.getValueAt(row, 4);
					
					System.out.println(test);
					if (test.equals("비공개")) {
						new JoinRoom(userid, nickname);
					}
					else {
						new GameRoom(userid, nickname);
						dispose();
					}
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		//방만들기 버튼 이벤트
		btnCreateRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateRoom(nickname);
			}
		});
		//랭킹 버튼 이벤트
		btnRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Ranking();
			}
		});
		//정보수정 버튼 이벤트
		btnUpdateProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProfileUpdate(userid);
			}
		});
		//로그아웃 버튼 이벤트
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				Object[] options = {"예", "아니오"};
				int logout = JOptionPane.showOptionDialog(null, "정말 로그아웃 하시겠습니까?", "캐치마인드", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (JOptionPane.YES_OPTION == logout) {
					try {
						db.setOffline(userid);
						writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
						String receiveData = "quit\r\n";
						writer.println(receiveData);
						new Login();
						dispose();
					} catch (Exception e2) {
					}
				}
			}
		});
		//게임종료 버튼 이벤트
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				Object[] options = {"예", "아니오"};
				int exit = JOptionPane.showOptionDialog(null, "정말 종료하시겠습니까?", "캐치마인드", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (JOptionPane.YES_OPTION == exit) {
	                try {
						db.setOffline(userid);
	                    writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
	                    String receiveData = "quit\r\n";
	                    writer.println(receiveData);
	                    System.exit(0);
	                }
	                catch (Exception e2) {
	                    e2.printStackTrace();
	                }
				}
			}
		});
		//채팅 보내기 버튼 이벤트
		btnSendChatting.addActionListener(new ActionListener() {
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
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});
		//채팅 보내기 텍스트 이벤트(엔터)
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
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});
		//프로필(내정보) 업데이트 스레드 메소드
		Thread myProfile = new Thread(new Runnable() {
			@Override
			public void run() {
				myProfile();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		myProfile.start();
		//접속자 리스트 스레드 메소드
		Thread onlineUserCheck = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!waitingRoomPanel.isVisible()) {
						break;
					}
					tabUser.setModel(online());
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		});
		onlineUserCheck.start();
		//게임방 리스트 스레드 메소드
		Thread onlineRoomCheck = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!waitingRoomPanel.isVisible()) {
						break;
					}
					tabRoom.setModel(roomCheck());
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		});
		onlineRoomCheck.start();
		
		waitChatting();
		waitChatReceive(soc);			
		
		ct.add(waitingRoomPanel);
		
		setTitle("캐치마인드 대기실");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	// --------------------- Method ---------------------
	//채팅방 접속 메소드
	public void waitChatting() {
		try {
			soc = new Socket(SERVER_IP, SERVER_PORT);
			System.out.println(nickname + "님이 서버와 연결되었습니다.");
			
			writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
			String receiveData = "join:" + nickname + "\r\n";
			writer.println(receiveData);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	//채팅방 브로드캐스트 수신 메소드
	public void waitChatReceive(Socket soc) {
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
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		};
		Thread clThread = new Thread(receiver);
		clThread.start();
	}
	//내 정보 불러오는 메소드
	public void myProfile() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE UserID = ?");
		
		try {
			db.pstmt.setString(1, userid);
			db.rs = db.pstmt.executeQuery();
			if (db.rs.next()) {
				txtMyNickName.setText(db.rs.getString("NickName"));
				txtMyLevel.setText(db.rs.getString("Level"));
				txtMyExp.setText(db.rs.getString("Exp"));
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
		db.Close();
	}
	//JTable(tabUser)에 접속자 리스트 넣는 메소드
	public DefaultTableModel online() {
		//유저목록 테이블
		String[] userList = { "닉네임", "레벨"};
		userModel = new DefaultTableModel(userList, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		DataBase db = new DataBase();
		db.Select("SELECT NickName, Level FROM Account WHERE isOnline = 1");
		try {
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				Object data[] = { db.rs.getString("NickName"), db.rs.getString("Level") + "Lv" };
				userModel.addRow(data);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
		return userModel;
	}
	//JTable(tabRoom)에 방 리스트 넣는 메소드
	public DefaultTableModel roomCheck() {
		//대기방 테이블
		String[] roomList = { "No", "방 제목", "방장", "인원", "비고" };
		roomModel = new DefaultTableModel(roomList, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Game WHERE roomcheck = 1");
		int roomNum = 1;
		try {
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				Object data[] = {roomNum, db.rs.getString("RoomTitle"), db.rs.getString("RoomOwner"), db.rs.getString("Personnel"), db.rs.getString("Status") };
				roomModel.addRow(data);
				roomNum++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
		return roomModel;
	}

	//getter & setter
	public String getUserid() { 
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}