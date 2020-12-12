package com.java.ex.waiting;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.ex.database.DataBase;

public class CreateRoom extends JFrame{

	private String isStatus = "����";
	
	JPanel createRoomPanel = null;
	JLabel lblRoomName = null;
	JLabel lblRoomState = null;
	JLabel lblRoomPassword = null;
	JCheckBox chkRoomStateCheck = null;
	JTextField txtRoomName = null;
	JTextField txtRoomPassword = null;
	JButton btnMakeRoom = null;
	JButton btnMakeRoomCancel = null;
	
	public CreateRoom(String ownerid, String ownerNickName) {
		// --------------------- Login Form Disign ---------------------
		Container ct = getContentPane();
		createRoomPanel = new JPanel();
		createRoomPanel.setLayout(null);
		
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
		txtRoomPassword.setEnabled(false);
		
		btnMakeRoom.setBounds(20, 100, 100, 50);
		btnMakeRoomCancel.setBounds(160, 100, 100, 50);
		
		createRoomPanel.add(lblRoomName);
		createRoomPanel.add(lblRoomState);
		createRoomPanel.add(lblRoomPassword);
		
		createRoomPanel.add(txtRoomName);
		createRoomPanel.add(chkRoomStateCheck);
		createRoomPanel.add(txtRoomPassword);
		
		createRoomPanel.add(btnMakeRoom);
		createRoomPanel.add(btnMakeRoomCancel);
		
		// --------------------- Button Event ---------------------
		//�游��� ��ư �̺�Ʈ
		btnMakeRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtRoomName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�� ������ �Է����ּ���.", "ĳġ���ε�", JOptionPane.INFORMATION_MESSAGE);
				}
				else if ((txtRoomPassword.isEnabled()) && (txtRoomPassword.getText().equals(""))) {
					JOptionPane.showMessageDialog(null, "����� üũ �� �� ��й�ȣ�� �Է��ؾ��մϴ�.", "ĳġ���ε�", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					makingRoom(ownerNickName);
					dispose();
				}
			}
		});
		//�游��� ��� �̺�Ʈ
		btnMakeRoomCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		//�� ���� & ����� ���� üũ�ڽ� �̺�Ʈ
		chkRoomStateCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chkRoomStateCheck.isSelected()) {
					txtRoomPassword.setEnabled(true);
					isStatus = "�����";
				}
				else {
					txtRoomPassword.setEnabled(false);
					txtRoomPassword.setText("");
					isStatus = "����";
				}
			}
		});
		
		ct.add(createRoomPanel);
		
		setTitle("ĳġ���ε� �游���");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	// --------------------- Method ---------------------
	//�游��� �޼ҵ�
	public void makingRoom(String ownerNickName) {
		DataBase db = new DataBase();
		
		//Game ���̺� �� ����
		db.Insert("INSERT INTO Game(RoomTitle, RoomOwner, Personnel, Status, Password, RoomCheck) VALUES (?, ?, ?, ?, ?, ?)");
		try {
			db.pstmt.setString(1, txtRoomName.getText());
			db.pstmt.setString(2, ownerNickName);
			db.pstmt.setString(3, "1");
			db.pstmt.setString(4, isStatus);
			db.pstmt.setString(5, txtRoomPassword.getText());
			db.pstmt.setString(6, "1");
			int result = db.pstmt.executeUpdate();
			
			if (1 != result) {
				JOptionPane.showMessageDialog(null, "�� ������ ������ �߻��Ͽ����ϴ�.", "ĳġ���ε�", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//RoomMember���̺� �� ����� ������ Player1�� ����
		db.Insert("INSERT INTO RoomMember(RoomTitle, Player1) VALUES(?, ?)");
		try {
			db.pstmt.setString(1, txtRoomName.getText());
			db.pstmt.setString(2, ownerNickName);
			int result = db.pstmt.executeUpdate();
			
			if (1 != result) {
				JOptionPane.showMessageDialog(null, "�� ������ ������ �߻��Ͽ����ϴ�.", "ĳġ���ε�", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.Close();
	}
}
