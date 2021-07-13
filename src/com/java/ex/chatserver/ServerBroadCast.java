package com.java.ex.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

//서버 브로드캐스트 클래스
public class ServerBroadCast extends Thread {
	
	Socket soc = null;
	String nickName = null;
	List<PrintWriter> list = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	
	public ServerBroadCast(Socket soc, List<PrintWriter> list) {
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
					System.out.println("클라이언트 연결 끊김");
					exit(writer);
					break;
				}
				
                String[] tokens = recevieData.split(":");
                
                if("join".equals(tokens[0])) {
                	join(tokens[1], writer);
                    System.out.println("채팅서버 연결");
                }
                else if("message".equals(tokens[0])) {
                	message(tokens[1]);
                }
                else if("server".equals(tokens[0])) {
                	serverMessage(tokens[1]);
                }
                else if("start".equals(tokens[0])) {
                	startMessage();
                }
                else if("quit".equals(tokens[0])) {
                	exit(writer);
                    System.out.println("채팅서버 연결 해제");
                }
			}
		} catch (IOException e) {
			//서버 메시지
			System.out.println(this.nickName + "님이 서버와 연결이 종료되었습니다.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	//채팅서버 입장 시 broadcast 메시지
	private void join(String nickname, PrintWriter writer) {
		this.nickName = nickname;
		
		String data = nickname + "님이 입장했습니다.";
		broadcast(data);
		addWriter(writer);
	}
	//채팅서버 연결 끊길 시 broadcast 메시지
	private void exit(PrintWriter writer) {
		removeClient(writer);
		
		String data = this.nickName + "님이 퇴장했습니다.";
		broadcast(data);
	}
	//채팅서버 연결 시 list에 추가
	private void addWriter(PrintWriter writer) {
		synchronized (writer) {
			list.add(writer);
		}
	}
	//채팅서버 연결 끊길 시 list에서 삭제
	private void removeClient(PrintWriter writer) {
		synchronized (list) {
			list.remove(writer);
		}
	}
	//회원이 채팅창에 입력 시 다른 회원에게도 broadcast
	private void message(String data) {
		broadcast(this.nickName + " : " + data);
	}
	//정답을 맞출 시 다른 회원에게도 broadcast
	private void serverMessage(String data) {
		broadcast("정답" + " : " + data);
	}
	//게임이 시작됐을 때 다른 회원에게도 broadcast
	private void startMessage() {
		broadcast("Server : 게임이 시작되었습니다.\\nServer : 플레이어들은 방장이 그리는 그림을 맞추어주세요.");
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