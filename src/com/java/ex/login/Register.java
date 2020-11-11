package com.java.ex.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.java.ex.database.DataBase;

public class Register extends JFrame implements Runnable{

	JPanel registerPanel = null;
	
	JLabel lblValidPassword = null;
	JLabel lblValidNickName = null;
	JLabel lblValidEmail = null;
	JLabel lblValidPhoneNum = null;
	
	JLabel lblRegister = null;
	JLabel lblAccount = null;
	JLabel lblAuthStatus = null;
	JLabel lblPassword1st = null;
	JLabel lblPassword2rd = null;
	JLabel lblEmail = null;
	JLabel lblNickName = null;
	JLabel lblPhoneNum = null;
	JTextField txtRegister = null;
	JTextField txtAccount = null;
	JTextField txtAuthStatus = null;
	JPasswordField txtPassword1st = null;
	JPasswordField txtPassword2rd = null;
	JTextField txtEmail = null;
	JTextField txtNickName = null;
	JTextField txtPhoneNum = null;
	JButton btnRegister = null;
	JButton btnCancel = null;
	JButton btnDuplicate = null;
	JButton btnAuthStatus = null;

	public Register() {
		// --------------------- Login Form Disign ---------------------
		Container ct = getContentPane();
		registerPanel = new JPanel();
		registerPanel.setLayout(null);
		
		lblValidPassword = new JLabel();
		lblValidEmail = new JLabel();
		lblValidNickName = new JLabel();
		lblValidPhoneNum = new JLabel();
		
		lblRegister = new JLabel("회원가입");
		lblAccount = new JLabel("사용할 아이디 :");
		lblAuthStatus = new JLabel("인증코드 :");
		lblPassword1st = new JLabel("비밀번호 :");
		lblPassword2rd = new JLabel("비밀번호 확인 :");
		lblEmail = new JLabel("이메일 :");
		lblNickName = new JLabel("닉네임 :");
		lblPhoneNum = new JLabel("전화번호 :");
		txtAccount = new JTextField(10);
		txtAuthStatus = new JTextField(10);
		txtPassword1st = new JPasswordField(10);
		txtPassword2rd = new JPasswordField(10);
		txtEmail = new JTextField(10);
		txtNickName = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		btnRegister = new JButton("가입하기");
		btnCancel = new JButton("취소");
		btnDuplicate = new JButton("중복확인");
		btnAuthStatus = new JButton("확인");
		
		lblValidPassword.setBounds(131, 180, 200, 30);
		lblValidEmail.setBounds(131, 240, 200, 30);
		lblValidNickName.setBounds(131, 330, 200, 30);
		lblValidPhoneNum.setBounds(131, 390, 200, 30);
		
		lblRegister.setBounds(170, 20, 100, 30);
		lblAccount.setBounds(40, 60, 100, 30);
		lblPassword1st.setBounds(40, 120, 100, 30);
		lblPassword2rd.setBounds(40, 150, 100, 30);
		lblEmail.setBounds(40, 210, 100, 30);
		lblAuthStatus.setBounds(40, 270, 100, 30);
		lblNickName.setBounds(40, 300, 100, 30);
		lblPhoneNum.setBounds(40, 360, 100, 30);
		txtAccount.setBounds(130, 60, 120, 30);
		txtPassword1st.setBounds(130, 120, 120, 30);
		txtPassword2rd.setBounds(130, 150, 120, 30);
		txtEmail.setBounds(130, 210, 120, 30);
		txtAuthStatus.setBounds(130, 270, 120, 30);
		txtNickName.setBounds(130, 300, 120, 30);
		txtPhoneNum.setBounds(130, 360, 120, 30);
		btnDuplicate.setBounds(270, 60, 85, 30);
		btnAuthStatus.setBounds(270, 270, 85, 30);
		btnRegister.setBounds(80, 450, 100, 50);
		btnCancel.setBounds(200, 450, 100, 50);
		
		registerPanel.add(lblValidPassword);
		registerPanel.add(lblValidEmail);
		registerPanel.add(lblValidNickName);
		registerPanel.add(lblValidPhoneNum);
		
		registerPanel.add(lblRegister);
		registerPanel.add(lblAccount);
		registerPanel.add(lblAuthStatus);
		registerPanel.add(lblPassword1st);
		registerPanel.add(lblPassword2rd);
		registerPanel.add(lblEmail);
		registerPanel.add(lblNickName);
		registerPanel.add(lblPhoneNum);
		registerPanel.add(txtAccount);
		registerPanel.add(txtAuthStatus);
		registerPanel.add(txtPassword1st);
		registerPanel.add(txtPassword2rd);
		registerPanel.add(txtEmail);
		registerPanel.add(txtNickName);
		registerPanel.add(txtPhoneNum);
		registerPanel.add(btnRegister);
		registerPanel.add(btnCancel);
		registerPanel.add(btnDuplicate);
		registerPanel.add(btnAuthStatus);

		// --------------------- Button Event ---------------------
		btnDuplicate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isDuplicate();
			}
		});
		
		btnAuthStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isAuthStatusChecked();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCancel();
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRegister();
			}
		});
		txtPhoneNum.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char phoneNum = e.getKeyChar();
				if (((phoneNum < '0') || (phoneNum > '9')) && (phoneNum != '\b')) {
					e.consume();
					JOptionPane.showMessageDialog(null, "숫자 외 입력은 제한됩니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
				}
				if (txtPhoneNum.getText().length() > 10) {
					e.consume();
				}
			}
		});
		
		Thread th = new Thread(this);
		th.start();
		
		ct.add(registerPanel);
		
		setTitle("회원가입");
		setSize(400, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// --------------------- Method ---------------------
	//회원가입 메소드
	public void isRegister() {
		DataBase db = new DataBase();
		db.Insert("INSERT INTO account(UserID, Password, NickName, Email, PhoneNum, Level, Exp, AuthStatus) VALUES(?, ?, ?, ?, ?, 1, 0, 0)");
		
		if (!lblValidPassword.getForeground().equals(Color.blue) ||
				!lblValidEmail.getForeground().equals(Color.blue) ||
				!lblValidNickName.getForeground().equals(Color.blue) ||
				!lblValidPhoneNum.getForeground().equals(Color.blue) ||
				btnDuplicate.isEnabled() ||
				btnAuthStatus.isEnabled()) {
			JOptionPane.showMessageDialog(null, "입력한 정보를 확인해주세요.");
			return;
		}
		
		try {
			db.pstmt.setString(1, txtAccount.getText());
			db.pstmt.setString(2, txtPassword1st.getText());
			db.pstmt.setString(3, txtNickName.getText());
			db.pstmt.setString(4, txtEmail.getText());
			db.pstmt.setString(5, txtPhoneNum.getText());
			int result = db.pstmt.executeUpdate();
			
			if (1 != result) {
				JOptionPane.showMessageDialog(null, "회원가입이 실패하였습니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다!", "회원가입", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//인증번호 체크 메소드
	public void isAuthStatusChecked() {
		String test = "123";
		if (txtAuthStatus.getText().equals(test)) {
			JOptionPane.showMessageDialog(null, "인증되었습니다.");
			txtAuthStatus.setEnabled(false);
			btnAuthStatus.setEnabled(false);
		}
		else if(txtAuthStatus.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "코드가 입력되지 않았습니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "일치하지 않는 코드입니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//아이디 중복검사 메소드
	public void isDuplicate() {		
		DataBase db = new DataBase();
		db.Select("SELECT * FROM account WHERE UserID = ?");
		
		try {
			db.pstmt.setString(1, txtAccount.getText());
			db.rs = db.pstmt.executeQuery();
			if (db.rs.next()) {
				JOptionPane.showMessageDialog(null, "이미 사용중인 아이디입니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtAccount.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "회원가입", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "생성 가능한 아이디입니다.", "회원가입", JOptionPane.INFORMATION_MESSAGE);
				txtAccount.setEnabled(false);
				btnDuplicate.setEnabled(false);
			}
		} catch (Exception e) {
		}
		db.Close();
	}
	
	//이메일 형식 검증 메소드
	public boolean isValidEmail(String email) {
		boolean result = false; 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); Matcher m = p.matcher(email); 
		if(m.matches()) { 
			result = true; 
		} 
		return result; 
	}
	
	//입력한 정보 취소 메소드
	public void isCancel() {
		 txtPassword1st.setText("");
		 txtPassword2rd.setText("");
		 txtEmail.setText("");
		 txtNickName.setText("");
		 txtPhoneNum.setText("");
	}
	// --------------------- Thread Method ---------------------
	//이메일 유효성 검사 메소드
	public void emailVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE Email = ?");
		
		//이메일 공백 검증
		if (txtEmail.getText().equals("")) {
			lblValidEmail.setText("");
			return;
		}
		
		//이메일 형식 검증
		if (!isValidEmail(txtEmail.getText())) {
			lblValidEmail.setForeground(Color.red);
			lblValidEmail.setText("유효하지 않은 이메일 형식입니다.");
			return;
		}

		//이메일 중복 검증
		try {
			db.pstmt.setString(1, txtEmail.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidEmail.setForeground(Color.red);
				lblValidEmail.setText("이미 사용중인 이메일입니다.");
			}
			else {
				lblValidEmail.setForeground(Color.blue);
				lblValidEmail.setText("사용 가능한 이메일입니다.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
	
	//비밀번호 동일성 검사 메소드
	public void passwordVerifier() {
		
		//비밀번호 공백 검증
		if (txtPassword1st.getText().equals("") || txtPassword2rd.getText().equals("")) {
			lblValidPassword.setText("");
			return;
		}
		
		//비밀번호 동일성 검증
		if (txtPassword1st.getText().equals(txtPassword2rd.getText())) {
			lblValidPassword.setForeground(Color.blue);
			lblValidPassword.setText("비밀번호가 일치합니다.");
		}
		else {
			lblValidPassword.setForeground(Color.red);
			lblValidPassword.setText("비밀번호가 일치하지 않습니다.");
		}
	}
	
	//닉네임 유효성 검사 메소드
	public void nickNameVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE NickName = ?");
		
		//닉네임 공백 검증
		if (txtNickName.getText().equals("")) {
			lblValidNickName.setText("");
			return;
		}
		
		//닉네임 중복 검증
		try {
			db.pstmt.setString(1, txtNickName.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidNickName.setForeground(Color.red);
				lblValidNickName.setText("이미 사용중인 닉네임입니다.");
			}
			else {
				lblValidNickName.setForeground(Color.blue);
				lblValidNickName.setText("사용 가능한 닉네임입니다.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
	
	//전화번호 유효성 검사 메소드
	public void phoneNumVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE PhoneNum = ?");
		
		//전화번호 공백 검증
		if (txtPhoneNum.getText().equals("")) {
			lblValidPhoneNum.setText("");
			return;
		}
		
		//전화번호 중복 검증
		try {
			db.pstmt.setString(1, txtPhoneNum.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidPhoneNum.setForeground(Color.red);
				lblValidPhoneNum.setText("이미 사용중인 전화번호입니다.");
			}
			else {
				lblValidPhoneNum.setForeground(Color.blue);
				lblValidPhoneNum.setText("사용 가능한 전화번호입니다.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (!registerPanel.isVisible()) {
				break;
			}
			if (txtEmail.isFocusOwner()) {
				emailVerifier();
			}
			if (txtPassword1st.isFocusOwner() || txtPassword2rd.isFocusOwner()) {
				passwordVerifier();
			}
			if (txtNickName.isFocusOwner()) {
				nickNameVerifier();
			}
			if (txtPhoneNum.isFocusOwner()) {
				phoneNumVerifier();
			}
		}
	}
}