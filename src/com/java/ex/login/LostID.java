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
import com.java.ex.waiting.WaitingRoom;

public class LostID extends JFrame{
	
	JPanel lostID = null;
	JLabel lblLostID = null;
	JLabel lblName = null;
	JLabel lblPhoneNum = null;
	JTextField txtName = null;
	JTextField txtPhoneNum = null;
	JButton btnLostID = null;
	
	public LostID() {
		// --------------------- Login Form Disign ---------------------
		Container ct = getContentPane();
		lostID = new JPanel();
		lostID.setLayout(null);
		
		lblLostID = new JLabel("���̵� ã��");
		lblName = new JLabel("�г��� :");
		lblPhoneNum = new JLabel("��ȭ��ȣ :");
		btnLostID = new JButton("ã��");
		
		txtName = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		
		lblLostID.setBounds(115, 25, 100, 20);
		lblName.setBounds(31, 55, 100, 20);
		lblPhoneNum.setBounds(20, 75, 100, 20);
		txtName.setBounds(90, 55, 100, 20);
		txtPhoneNum.setBounds(90, 75, 100, 20);
		btnLostID.setBounds(195, 55, 70, 40);
		
		lostID.add(lblLostID);
		lostID.add(lblName);
		lostID.add(lblPhoneNum);
		lostID.add(txtName);
		lostID.add(txtPhoneNum);
		lostID.add(btnLostID);
		
		// --------------------- Button Event ---------------------
		btnLostID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFindID();
			}
		});
		
		
		ct.add(lostID);
		
		setTitle("���̵� ã��");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// --------------------- Method ---------------------
	//���̵� ã�� �޼ҵ�
	public void isFindID() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account WHERE NickName = ? AND PhoneNum = ?");
		try {
			db.pstmt.setString(1, txtName.getText());
			db.pstmt.setString(2, txtPhoneNum.getText());
			db.rs = db.pstmt.executeQuery();
			
			if (db.rs.next()) {
				JOptionPane.showMessageDialog(null, "�ش� ������ �˻��� ���̵� : " + db.rs.getString("UserID"), "���̵� ã��", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(txtName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�г����� �Է����ּ���.", "���̵� ã��", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtPhoneNum.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է����ּ���.", "���̵� ã��", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "�Է��� ȸ�� ������ ��ġ���� �ʽ��ϴ�.", "���̵� ã��", JOptionPane.ERROR_MESSAGE);
			}
				

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
}