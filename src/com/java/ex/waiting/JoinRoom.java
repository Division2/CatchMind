package com.java.ex.waiting;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.ex.database.DataBase;
import com.java.ex.game.GameRoom;

public class JoinRoom extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JPanel joinRoomPanel = null;
	JLabel lblPassword = null;
	JTextField txtPassword = null;
	JButton btnJoin = null;
	JButton btnCancel = null;
	
	public JoinRoom(String userid, String nickname) {
		Container ct = getContentPane();
		joinRoomPanel = new JPanel();
		joinRoomPanel.setLayout(null);
		
		lblPassword = new JLabel("방 비밀번호 : ");
		txtPassword = new JTextField(10);
		btnJoin = new JButton("참여하기");
		btnCancel = new JButton("돌아가기");
		
		lblPassword.setBounds(30, 10, 80, 20);
		txtPassword.setBounds(120, 10, 130, 20);
		btnJoin.setBounds(30, 60, 100, 70);
		btnCancel.setBounds(150, 60, 100, 70);
		
		joinRoomPanel.add(lblPassword);
		joinRoomPanel.add(txtPassword);
		joinRoomPanel.add(btnJoin);
		joinRoomPanel.add(btnCancel);
		
		//비밀번호 텍스트필드 이벤트
		txtPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				db.Select("SELECT * FROM Game WHERE Password = ?");
				
				try {
					db.pstmt.setString(1, txtPassword.getText());
					db.rs = db.pstmt.executeQuery();
					
					if (db.rs.next()) {
						new GameRoom(userid, nickname);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다!", "캐치마인드", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				db.Close();
			}
		});
		//참여하기 버튼 이벤트
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				db.Select("SELECT * FROM Game WHERE Password = ?");
				
				try {
					db.pstmt.setString(1, txtPassword.getText());
					db.rs = db.pstmt.executeQuery();
					
					if (db.rs.next()) {
						new GameRoom(userid, nickname);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다!", "캐치마인드", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				db.Close();
			}
		});
		//취소 버튼 이벤트
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		ct.add(joinRoomPanel);
		
		setTitle("캐치마인드 참여하기");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}