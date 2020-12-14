package com.java.ex.login;

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

public class LostPW extends JFrame{
	
	JPanel lostPW = null;
	JLabel lblLostPW = null;
	JLabel lblEmail = null;
	JLabel lblPhoneNum = null;
	JTextField txtEmail = null;
	JTextField txtPhoneNum = null;
	JButton btnLostPW = null;
	
	public LostPW() {
		// --------------------- Login Form Disign ---------------------
		Container ct = getContentPane();
		lostPW = new JPanel();
		lostPW.setLayout(null);
		
		lblLostPW = new JLabel("��й�ȣ ã��");
		lblEmail = new JLabel("�̸��� :");
		lblPhoneNum = new JLabel("��ȭ��ȣ :");
		btnLostPW = new JButton("ã��");
		
		txtEmail = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		
		lblLostPW.setBounds(110, 25, 100, 20);
		lblEmail.setBounds(31, 55, 100, 20);
		lblPhoneNum.setBounds(20, 75, 100, 20);
		txtEmail.setBounds(90, 55, 100, 20);
		txtPhoneNum.setBounds(90, 75, 100, 20);
		btnLostPW.setBounds(195, 55, 70, 40);
		
		lostPW.add(lblLostPW);
		lostPW.add(lblEmail);
		lostPW.add(lblPhoneNum);
		lostPW.add(txtEmail);
		lostPW.add(txtPhoneNum);
		lostPW.add(btnLostPW);
		
		// --------------------- Button Event ---------------------
		//��й�ȣ ã�� ��ư �̺�Ʈ
		btnLostPW.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFindPW();
			}
		});
		
		ct.add(lostPW);
		
		setTitle("��й�ȣ ã��");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// --------------------- Method ---------------------
	//��й�ȣ ã�� �޼ҵ�
	public void isFindPW() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE Email = ? AND PhoneNum = ?");
		try {
			db.pstmt.setString(1, txtEmail.getText());
			db.pstmt.setString(2, txtPhoneNum.getText());
			db.rs = db.pstmt.executeQuery();
			
			if(db.rs.next()) {
				JOptionPane.showMessageDialog(null, "�ش� ������ �˻��� ��й�ȣ : " + db.rs.getString("Password"), "��й�ȣ ã��", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else if(txtEmail.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�̸����� �Է����ּ���.", "��й�ȣ ã��", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtPhoneNum.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է����ּ���.", "��й�ȣ ã��", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "�Է��� ȸ�� ������ ��ġ���� �ʽ��ϴ�.", "��й�ȣ ã��", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
}