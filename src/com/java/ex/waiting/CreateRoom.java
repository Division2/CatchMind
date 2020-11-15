package com.java.ex.waiting;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateRoom extends JFrame {

	JPanel createRoom = null;
	
	JLabel lblRoomName = null;
	JLabel lblRoomState = null;
	JLabel lblRoomPassword = null;
	JCheckBox chkRoomStateCheck = null;
	JTextField txtRoomName = null;
	JTextField txtRoomPassword = null;
	JButton btnMakeRoom = null;
	JButton btnMakeRoomCancel = null;
	
	public CreateRoom() {
		Container ct = getContentPane();
		createRoom = new JPanel();
		createRoom.setLayout(null);
		
		lblRoomName = new JLabel("�� ���� : ");
		lblRoomState = new JLabel("���� : ");
		lblRoomPassword = new JLabel("��й�ȣ : ");
		
		txtRoomName = new JTextField(10);
		chkRoomStateCheck = new JCheckBox("�����");
		txtRoomPassword = new JTextField(10);
		
		btnMakeRoom = new JButton("�� ����");
		btnMakeRoomCancel = new JButton("���");
		
		lblRoomName.setBounds(20, 10, 100, 30);
		lblRoomState.setBounds(20, 40, 100, 30);
		lblRoomPassword.setBounds(20, 70, 100, 30);
		
		txtRoomName.setBounds(90, 15, 170, 20);
		chkRoomStateCheck.setBounds(86, 45, 100, 20);
		txtRoomPassword.setBounds(90, 75, 170, 20);
		
		btnMakeRoom.setBounds(20, 100, 100, 50);
		btnMakeRoomCancel.setBounds(160, 100, 100, 50);
		
		createRoom.add(lblRoomName);
		createRoom.add(lblRoomState);
		createRoom.add(lblRoomPassword);
		
		createRoom.add(txtRoomName);
		createRoom.add(chkRoomStateCheck);
		createRoom.add(txtRoomPassword);
		
		createRoom.add(btnMakeRoom);
		createRoom.add(btnMakeRoomCancel);
		
		ct.add(createRoom);
		
		setTitle("ĳġ���ε� �游���");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}