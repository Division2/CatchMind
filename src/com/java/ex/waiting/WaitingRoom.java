package com.java.ex.waiting;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import com.java.ex.login.Login;

public class WaitingRoom extends JFrame{
	
	private String userid;

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
	
	public WaitingRoom(String userid) {
		this.userid = userid;
		
		if (null == userid) {
			JOptionPane.showMessageDialog(null, "���������� �����Դϴ�!", "ĳġ���ε�", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	
		Container ct = getContentPane();
		waitingRoomPanel = new JPanel();
		waitingRoomPanel.setLayout(null);

		btnJoinRoom = new JButton("�����ϱ�");
		btnCreateRoom = new JButton("�游���");
		btnRanking = new JButton("��ŷ");
		btnUpdateProfile = new JButton("���� ����");
		btnLogout = new JButton("�α׾ƿ�");
		btnExit = new JButton("��������");
		btnSendChatting = new JButton("������");
		lblMyProfile = new JLabel("�� ����");
		lblMyNickName = new JLabel("�г���");
		lblMyLevel = new JLabel("����");
		lblMyExp = new JLabel("����ġ");
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
		lblMyProfile.setFont(new Font("���� ���", Font.BOLD, 15));;
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
		
		//���� ���̺�
		String[] roomList = { "No", "�� ����", "����", "�ο�", "���" };
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
		//���� ���̺�
		
		//������� ���̺�
		String[] userList = { "�г���", "����"};
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
		//������� ���̺�
		
		// ä��â �� ä�� �Է�â
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
		// ä��â �� ä�� �Է�â
		
		// --------------------- Button Event ---------------------
		//��ŷ ��ư �̺�Ʈ
		btnRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Ranking();
			}
		});
		//�������� ��ư �̺�Ʈ
		btnUpdateProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProfileUpdate(userid);
			}
		});
		//�α׾ƿ� ��ư �̺�Ʈ
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"��", "�ƴϿ�"};
				int logout = JOptionPane.showOptionDialog(null, "���� �α׾ƿ� �Ͻðڽ��ϱ�?", "ĳġ���ε�", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if(JOptionPane.YES_OPTION == logout) {
					new Login();
					dispose();
				}
			}
		});
		//�������� ��ư �̺�Ʈ
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"��", "�ƴϿ�"};
				int exit = JOptionPane.showOptionDialog(null, "���� �����Ͻðڽ��ϱ�?", "ĳġ���ε�", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (JOptionPane.YES_OPTION == exit) {
					System.exit(0);
				}
			}
		});
		btnSendChatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chattingRoom.append(chatting.getText() + "\n");
			}
		});
		chatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chattingRoom.append(chatting.getText() + "\n");
				chatting.setText("");
			}
		});
		
		myProfile();

		ct.add(waitingRoomPanel);
		
		setTitle("ĳġ���ε� ����");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
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
	
	//getter & setter
	public String getUserid() { 
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}