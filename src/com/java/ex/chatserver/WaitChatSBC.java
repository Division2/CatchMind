package com.java.ex.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

//���� ��ε�ĳ��Ʈ Ŭ����
public class WaitChatSBC extends Thread {
	
	Socket soc = null;
	String nickName = null;
	List<PrintWriter> list = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	
	public WaitChatSBC(Socket soc, List<PrintWriter> list) {
		this.soc = soc;
		this.list = list;
	}
	@Override
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()));
			
			while (true) {
				String recevieData = reader.readLine();
				
				if (null == recevieData) {
					System.out.println("Ŭ���̾�Ʈ ���� ����");
					exit(writer);
					break;
				}
				
                String[] tokens = recevieData.split(":");
                if("join".equals(tokens[0])) {
                	join(tokens[1], writer);
                    System.out.println("ä�ü��� ����");
                }
                else if("message".equals(tokens[0])) {
                	message(tokens[1]);
                }
                else if("quit".equals(tokens[0])) {
                	exit(writer);
                    System.out.println("ä�ü��� ���� ����");
                }
			}
		} catch (IOException e) {
			//���� �޽���
			System.out.println(this.nickName + "���� ������ ������ ����Ǿ����ϴ�.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	//ä�ü��� ���� �� broadcast �޽���
	private void join(String nickname, PrintWriter writer) {
		this.nickName = nickname;
		
		String data = nickname + "���� �����߽��ϴ�.";
		broadcast(data);
		addWriter(writer);
	}
	//ä�ü��� ���� ���� �� broadcast �޽���
	private void exit(PrintWriter writer) {
		removeClient(writer);
		
		String data = this.nickName + "���� �����߽��ϴ�.";
		broadcast(data);
	}
	//ä�ü��� ���� �� list�� �߰�
	private void addWriter(PrintWriter writer) {
		synchronized (writer) {
			list.add(writer);
		}
	}
	//ä�ü��� ���� ���� �� list���� ����
	private void removeClient(PrintWriter writer) {
		synchronized (list) {
			list.remove(writer);
		}
	}
	//ȸ���� ä��â�� �Է� �� �ٸ� ȸ�����Ե� broadcast
	private void message(String data) {
		broadcast(this.nickName + " : " + data);
	}
	//broadcast
	private void broadcast(String data) {
		synchronized (list) {
			for (PrintWriter writer : list) {
				writer.println(data);
				writer.flush();
			}
		}
	}

}