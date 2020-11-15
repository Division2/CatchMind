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

public class ProfileUpdate extends JFrame {
	
	private String userid;
	JPanel profileUpdatePanel = null;
	JLabel lblUserID = null;
	JLabel lblCurrentPassword = null;
	JLabel lblChangePassword = null;
	JLabel lblCurrentEmail = null;
	JLabel lblNickName = null;
	JLabel lblPhoneNum = null;
	JTextField txtUserID = null;
	JTextField txtCurrentPassword = null;
	JTextField txtChangePassword = null;
	JTextField txtCurrentEmail = null;
	JTextField txtNickName = null;
	JTextField txtPhoneNum = null;
	JButton btnModifyOk = null;
	JButton btnModifyCancel = null;
	
	public ProfileUpdate(String userid) {
		this.userid = userid;
		Container ct = getContentPane();
		profileUpdatePanel = new JPanel();
		profileUpdatePanel.setLayout(null);
		
		lblUserID = new JLabel("아이디 : ");
		lblCurrentPassword = new JLabel("현재 비밀번호 : ");
		lblChangePassword = new JLabel("변경할 비밀번호 : ");
		lblCurrentEmail = new JLabel("이메일 : ");
		lblNickName = new JLabel("닉네임 : ");
		lblPhoneNum = new JLabel("전화번호 : ");
		
		txtUserID = new JTextField(10);
		txtCurrentPassword = new JTextField(10);
		txtChangePassword = new JTextField(10);
		txtCurrentEmail = new JTextField(10);
		txtNickName = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		
		btnModifyOk = new JButton("변경");
		btnModifyCancel = new JButton("취소");
		
		lblUserID.setBounds(10, 10, 100, 30);
		lblCurrentPassword.setBounds(10, 30, 150, 30);
		lblChangePassword.setBounds(10, 50, 150, 30);
		lblCurrentEmail.setBounds(10, 70, 50, 30);
		lblNickName.setBounds(10, 90, 100, 30);
		lblPhoneNum.setBounds(10, 110, 100, 30);
		
		txtUserID.setBounds(130, 15, 120, 20);
		txtUserID.setEditable(false);
		txtCurrentPassword.setBounds(130, 35, 120, 20);
		txtCurrentPassword.setEditable(false);
		txtChangePassword.setBounds(130, 55, 120, 20);
		txtCurrentEmail.setBounds(130, 75, 120, 20);
		txtNickName.setBounds(130, 95, 120, 20);
		txtNickName.setEditable(false);
		txtPhoneNum.setBounds(130, 115, 120, 20);
		txtPhoneNum.setEditable(false);
		
		btnModifyOk.setBounds(20, 150, 100, 50);
		btnModifyCancel.setBounds(140, 150, 100, 50);
		
		profileUpdatePanel.add(lblUserID);
		profileUpdatePanel.add(lblCurrentPassword);
		profileUpdatePanel.add(lblChangePassword);
		profileUpdatePanel.add(lblCurrentEmail);
		profileUpdatePanel.add(lblNickName);
		profileUpdatePanel.add(lblPhoneNum);
		
		profileUpdatePanel.add(txtUserID);
		profileUpdatePanel.add(txtCurrentPassword);
		profileUpdatePanel.add(txtChangePassword);
		profileUpdatePanel.add(txtCurrentEmail);
		profileUpdatePanel.add(txtNickName);
		profileUpdatePanel.add(txtPhoneNum);
		
		profileUpdatePanel.add(btnModifyOk);
		profileUpdatePanel.add(btnModifyCancel);
		
		// --------------------- Button Event ---------------------
		btnModifyOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase db = new DataBase();
				db.Update("UPDATE Account SET Password = ?, Email = ? WHERE UserID = ?");
				try {
					db.pstmt.setString(1, txtChangePassword.getText());
					db.pstmt.setString(2, txtCurrentEmail.getText());
					db.pstmt.setString(3, userid);
					int result = db.pstmt.executeUpdate();

					if (txtChangePassword.getText().equals("")) {
						txtChangePassword.setText(txtCurrentPassword.getText());
					}
					if (1 == result) {
						JOptionPane.showMessageDialog(null, "회원정보가 성공적으로 변경되었습니다.", "캐치마인드", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnModifyCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		profileModify();
		
		ct.add(profileUpdatePanel);
		
		setTitle("캐치마인드 정보수정");
		setSize(280, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void profileModify() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE UserID = ?");
	
		try {
			db.pstmt.setString(1, userid);
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				txtUserID.setText(db.rs.getString("UserID"));
				txtCurrentPassword.setText(db.rs.getString("Password"));
				txtCurrentEmail.setText(db.rs.getString("Email"));
				txtNickName.setText(db.rs.getString("NickName"));
				txtPhoneNum.setText(db.rs.getString("PhoneNum"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}