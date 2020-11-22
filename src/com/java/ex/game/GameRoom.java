package com.java.ex.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.java.ex.waiting.WaitingRoom;

public class GameRoom extends JFrame {
	
	private Canvas canvas;
	
	JPanel gameRoomPanel = null;
	
	JTextArea chattingRoom = null;
	JTextField chatting = null;
	
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
	

	public GameRoom(String userid, String nickname) {
		Container ct = getContentPane();
		gameRoomPanel = new JPanel();
		gameRoomPanel.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(200, 70, 610, 450);
		canvas.setBackground(Color.white);
		
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
	
		// --------------------- Button Event ---------------------
		//게임종료 버튼 이벤트
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WaitingRoom(userid, nickname);
				dispose();
			}
		});
		//채팅방 전송 이벤트
		chatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chattingRoom.append(chatting.getText() + "\n");
				chatting.setText("");
			}
		});
		
		ct.add(gameRoomPanel);
		
		setTitle("캐치마인드");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}