package com.java.ex.waiting;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.java.ex.database.DataBase;

public class Ranking extends JFrame {
	JPanel rankingPanel = null;
	JTable tabRanking = null;
	DefaultTableModel rankingModel = null;
	
	public Ranking() {
		Container ct = getContentPane();
		rankingPanel = new JPanel();
		rankingPanel.setLayout(null);


		
		String[] rankingList = {"순위", "닉네임", "레벨", "경험치"};
		rankingModel = new DefaultTableModel(rankingList, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabRanking = new JTable(rankingModel);
		JScrollPane scroll1 = new JScrollPane(tabRanking);
		scroll1.setBounds(10, 10, 265, 440);
		rankingPanel.add(scroll1);
		
		viewRanking();
		
		ct.add(rankingPanel);
		
		setTitle("캐치마인드 랭킹");
		setSize(300, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void viewRanking() {
		DataBase db = new DataBase();
		db.Select("SELECT * FROM Account order by Level desc");
		
		int rankNum = 0;
		try {
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				rankNum++;
				String nickName = db.rs.getString("NickName");
				String level = db.rs.getString("Level");
				String exp = db.rs.getString("Exp");
				Object data[] = {rankNum, nickName, level, exp};
				rankingModel.addRow(data);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		db.Close();
	}
}