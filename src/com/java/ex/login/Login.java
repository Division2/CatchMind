package com.java.ex.login;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.ex.database.DataBase;
import com.java.ex.waiting.WaitingRoom;

public class Login extends JFrame {

	JPanel loginPanel = null;
	JLabel lblLoginLabel = null;
	JLabel lblIDLabel = null;
	JLabel lblPWLabel = null;
	JLabel lblRegister = null;
	JLabel lblLostID = null;
	JLabel lblLostPW = null;
	JTextField txtID = null;
	JPasswordField txtPW = null;
	JButton btnLogin = null;
	
	public Login() {
		// --------------------- Login Form Disign ---------------------
		Container ct = getContentPane();
		loginPanel = new JPanel();
		loginPanel.setLayout(null);
		
		lblLoginLabel = new JLabel("로그인");
		lblIDLabel = new JLabel("Account :");
		lblPWLabel = new JLabel("Password :");
		txtID = new JTextField(15);
		txtPW = new JPasswordField(15);
		btnLogin = new JButton("로그인");
		lblLostID = new JLabel("아이디 찾기 |");
		lblLostPW = new JLabel("비밀번호 찾기 |");
		lblRegister = new JLabel("회원가입");
		
		lblLoginLabel.setBounds(125, 25, 100, 20);
		lblIDLabel.setBounds(31, 55, 100, 20);
		lblPWLabel.setBounds(20, 75, 100, 20);
		txtID.setBounds(90, 55, 100, 20);
		txtPW.setBounds(90, 75, 100, 20);
		btnLogin.setBounds(195, 55, 70, 40);
		lblLostID.setBounds(40, 100, 70, 20);
		lblLostPW.setBounds(110, 100, 85, 20);
		lblRegister.setBounds(195, 100, 70, 20);
		
		loginPanel.add(lblLoginLabel);
		loginPanel.add(lblIDLabel);
		loginPanel.add(lblPWLabel);
		loginPanel.add(txtID);
		loginPanel.add(txtPW);
		loginPanel.add(btnLogin);
		loginPanel.add(lblLostID);
		loginPanel.add(lblLostPW);
		loginPanel.add(lblRegister);
		
		// --------------------- Button Event ---------------------
		//로그인 버튼 이벤트
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		//아이디 찾기 이벤트
		lblLostID.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				new LostID();
            }
		});
		//비밀번호 찾기 이벤트
		lblLostPW.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				new LostPW();
            }
		});
		//회원가입 이벤트
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				new Register();
            }
		});
		
		ct.add(loginPanel);
		
		setTitle("캐치마인드 로그인");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	// --------------------- Method ---------------------
	//로그인 체킹 메소드
	public void isLoginCheck() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE UserID = ? AND Password = ?");
		try {
			db.pstmt.setString(1, txtID.getText());
			db.pstmt.setString(2, txtPW.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				if(db.rs.getString("AuthStatus").equals("0")){
					JOptionPane.showMessageDialog(null, "인증되지 않은 사용자입니다.", "로그인", JOptionPane.ERROR_MESSAGE);
				}
				else {
				new WaitingRoom(txtID.getText());
				dispose();
				JOptionPane.showMessageDialog(null, "로그인 성공하였습니다.", "로그인", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if(txtID.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디가 입력되지 않았습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
			}	
			else if(txtPW.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
}