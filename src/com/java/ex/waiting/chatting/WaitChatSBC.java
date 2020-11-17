package com.java.ex.waiting.chatting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

//º≠πˆ ∫Í∑ŒµÂƒ≥Ω∫∆Æ ≈¨∑°Ω∫
public class WaitChatSBC extends Thread {

	Socket soc = null;
	BufferedReader reader;
	BufferedWriter writer;
	HashMap<String, BufferedWriter> hashmap;

	String nickName;
	
	public WaitChatSBC(Socket soc, HashMap<String, BufferedWriter> hashmap) {
		this.soc = soc;
		this.hashmap = hashmap;
		
		try {
			reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
			
			nickName = reader.readLine();
			broadcast(nickName + "¥‘¿Ã ¡¢º”«œºÃΩ¿¥œ¥Ÿ.");
			
			synchronized (hashmap) {
				hashmap.put(nickName, writer);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String receiveData;
				receiveData = reader.readLine();
				
				if (receiveData.equals("/exit")) {
					break;
				}
				else {
					broadcast(nickName + " : " + receiveData);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			synchronized (hashmap) {
				hashmap.remove(nickName);
			}
			broadcast(nickName + "¥‘¿Ã ≈¿Â«œºÃΩ¿¥œ¥Ÿ.");
			try {
				reader.close();
				writer.close();
				soc.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	public void broadcast(String message) {
		synchronized (hashmap) {
			Collection<BufferedWriter> collection = hashmap.values();
			
			try {
                Iterator<BufferedWriter> iter = collection.iterator();
                while (iter.hasNext()) {
                    BufferedWriter writer = iter.next();
                    writer.write(message + "\n");
                    writer.flush();
                }
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	public void exitChat() {
		synchronized (hashmap) {
			hashmap.remove(nickName);
		}
		broadcast(nickName + "¥‘¿Ã ≈¿Â«œºÃΩ¿¥œ¥Ÿ.");
		try {
			reader.close();
			writer.close();
			soc.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}