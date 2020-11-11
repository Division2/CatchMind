package com.java.ex.waiting;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class WaitingRoom extends JFrame{
	
	JPanel waitingRoomPanel = null;
	JButton btnJoinRoom = null;
	JButton btnCreateRoom = null;
	JButton btnRanking = null;
	JButton btnUpdateProfile = null;
	JButton btnLogout = null;
	JButton btnExit = null;
	JTable tabRoom = null;
	JTable tabUser = null;
	
	public WaitingRoom() {
		Container ct = getContentPane();
		waitingRoomPanel = new JPanel();
		waitingRoomPanel.setLayout(null);

		btnJoinRoom = new JButton("�����ϱ�");
		btnCreateRoom = new JButton("�游���");
		btnRanking = new JButton("��ŷ");
		btnUpdateProfile = new JButton("�� ���� ����");
		
		btnJoinRoom.setBounds(20, 20, 100, 50);
		btnCreateRoom.setBounds(140, 20, 100, 50);
		btnRanking.setBounds(260, 20, 100, 50);
		btnUpdateProfile.setBounds(380, 20, 100, 50);
		
		waitingRoomPanel.add(btnJoinRoom);
		waitingRoomPanel.add(btnCreateRoom);
		waitingRoomPanel.add(btnRanking);
		waitingRoomPanel.add(btnUpdateProfile);
				
		ct.add(waitingRoomPanel);
		
		setTitle("ĳġ���ε� ����");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		//���� ��ư�� ���� �� ���� �ּ�ó��
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}