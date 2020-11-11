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
		
		lblRegister = new JLabel("ȸ������");
		lblAccount = new JLabel("����� ���̵� :");
		lblAuthStatus = new JLabel("�����ڵ� :");
		lblPassword1st = new JLabel("��й�ȣ :");
		lblPassword2rd = new JLabel("��й�ȣ Ȯ�� :");
		lblEmail = new JLabel("�̸��� :");
		lblNickName = new JLabel("�г��� :");
		lblPhoneNum = new JLabel("��ȭ��ȣ :");
		txtAccount = new JTextField(10);
		txtAuthStatus = new JTextField(10);
		txtPassword1st = new JPasswordField(10);
		txtPassword2rd = new JPasswordField(10);
		txtEmail = new JTextField(10);
		txtNickName = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		btnRegister = new JButton("�����ϱ�");
		btnCancel = new JButton("���");
		btnDuplicate = new JButton("�ߺ�Ȯ��");
		btnAuthStatus = new JButton("Ȯ��");
		
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
					JOptionPane.showMessageDialog(null, "���� �� �Է��� ���ѵ˴ϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
				}
				if (txtPhoneNum.getText().length() > 10) {
					e.consume();
				}
			}
		});
		
		Thread th = new Thread(this);
		th.start();
		
		ct.add(registerPanel);
		
		setTitle("ȸ������");
		setSize(400, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// --------------------- Method ---------------------
	//ȸ������ �޼ҵ�
	public void isRegister() {
		DataBase db = new DataBase();
		db.Insert("INSERT INTO account(UserID, Password, NickName, Email, PhoneNum, Level, Exp, AuthStatus) VALUES(?, ?, ?, ?, ?, 1, 0, 0)");
		
		if (!lblValidPassword.getForeground().equals(Color.blue) ||
				!lblValidEmail.getForeground().equals(Color.blue) ||
				!lblValidNickName.getForeground().equals(Color.blue) ||
				!lblValidPhoneNum.getForeground().equals(Color.blue) ||
				btnDuplicate.isEnabled() ||
				btnAuthStatus.isEnabled()) {
			JOptionPane.showMessageDialog(null, "�Է��� ������ Ȯ�����ּ���.");
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
				JOptionPane.showMessageDialog(null, "ȸ�������� �����Ͽ����ϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�!", "ȸ������", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//������ȣ üũ �޼ҵ�
	public void isAuthStatusChecked() {
		String test = "123";
		if (txtAuthStatus.getText().equals(test)) {
			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
			txtAuthStatus.setEnabled(false);
			btnAuthStatus.setEnabled(false);
		}
		else if(txtAuthStatus.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "�ڵ尡 �Էµ��� �ʾҽ��ϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "��ġ���� �ʴ� �ڵ��Դϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//���̵� �ߺ��˻� �޼ҵ�
	public void isDuplicate() {		
		DataBase db = new DataBase();
		db.Select("SELECT * FROM account WHERE UserID = ?");
		
		try {
			db.pstmt.setString(1, txtAccount.getText());
			db.rs = db.pstmt.executeQuery();
			if (db.rs.next()) {
				JOptionPane.showMessageDialog(null, "�̹� ������� ���̵��Դϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtAccount.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���.", "ȸ������", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "���� ������ ���̵��Դϴ�.", "ȸ������", JOptionPane.INFORMATION_MESSAGE);
				txtAccount.setEnabled(false);
				btnDuplicate.setEnabled(false);
			}
		} catch (Exception e) {
		}
		db.Close();
	}
	
	//�̸��� ���� ���� �޼ҵ�
	public boolean isValidEmail(String email) {
		boolean result = false; 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); Matcher m = p.matcher(email); 
		if(m.matches()) { 
			result = true; 
		} 
		return result; 
	}
	
	//�Է��� ���� ��� �޼ҵ�
	public void isCancel() {
		 txtPassword1st.setText("");
		 txtPassword2rd.setText("");
		 txtEmail.setText("");
		 txtNickName.setText("");
		 txtPhoneNum.setText("");
	}
	// --------------------- Thread Method ---------------------
	//�̸��� ��ȿ�� �˻� �޼ҵ�
	public void emailVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE Email = ?");
		
		//�̸��� ���� ����
		if (txtEmail.getText().equals("")) {
			lblValidEmail.setText("");
			return;
		}
		
		//�̸��� ���� ����
		if (!isValidEmail(txtEmail.getText())) {
			lblValidEmail.setForeground(Color.red);
			lblValidEmail.setText("��ȿ���� ���� �̸��� �����Դϴ�.");
			return;
		}

		//�̸��� �ߺ� ����
		try {
			db.pstmt.setString(1, txtEmail.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidEmail.setForeground(Color.red);
				lblValidEmail.setText("�̹� ������� �̸����Դϴ�.");
			}
			else {
				lblValidEmail.setForeground(Color.blue);
				lblValidEmail.setText("��� ������ �̸����Դϴ�.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
	
	//��й�ȣ ���ϼ� �˻� �޼ҵ�
	public void passwordVerifier() {
		
		//��й�ȣ ���� ����
		if (txtPassword1st.getText().equals("") || txtPassword2rd.getText().equals("")) {
			lblValidPassword.setText("");
			return;
		}
		
		//��й�ȣ ���ϼ� ����
		if (txtPassword1st.getText().equals(txtPassword2rd.getText())) {
			lblValidPassword.setForeground(Color.blue);
			lblValidPassword.setText("��й�ȣ�� ��ġ�մϴ�.");
		}
		else {
			lblValidPassword.setForeground(Color.red);
			lblValidPassword.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
	}
	
	//�г��� ��ȿ�� �˻� �޼ҵ�
	public void nickNameVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE NickName = ?");
		
		//�г��� ���� ����
		if (txtNickName.getText().equals("")) {
			lblValidNickName.setText("");
			return;
		}
		
		//�г��� �ߺ� ����
		try {
			db.pstmt.setString(1, txtNickName.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidNickName.setForeground(Color.red);
				lblValidNickName.setText("�̹� ������� �г����Դϴ�.");
			}
			else {
				lblValidNickName.setForeground(Color.blue);
				lblValidNickName.setText("��� ������ �г����Դϴ�.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
	
	//��ȭ��ȣ ��ȿ�� �˻� �޼ҵ�
	public void phoneNumVerifier() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE PhoneNum = ?");
		
		//��ȭ��ȣ ���� ����
		if (txtPhoneNum.getText().equals("")) {
			lblValidPhoneNum.setText("");
			return;
		}
		
		//��ȭ��ȣ �ߺ� ����
		try {
			db.pstmt.setString(1, txtPhoneNum.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				lblValidPhoneNum.setForeground(Color.red);
				lblValidPhoneNum.setText("�̹� ������� ��ȭ��ȣ�Դϴ�.");
			}
			else {
				lblValidPhoneNum.setForeground(Color.blue);
				lblValidPhoneNum.setText("��� ������ ��ȭ��ȣ�Դϴ�.");
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