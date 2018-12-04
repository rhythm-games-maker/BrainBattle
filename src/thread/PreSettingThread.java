package thread; 


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.net.Socket; 
import gui.MainFrame;

public class PreSettingThread extends Thread{ 
	private Socket socket;  
	private MainFrame mainFrame; 
	private PrintWriter writer;
	public PreSettingThread(MainFrame mainFrame, Socket socket) { 
		this.mainFrame = mainFrame; 
		this.socket = socket; 
	} 
	@Override 
	public void run() { 
		try { 
			writer = new PrintWriter(socket.getOutputStream(),true); 

			mainFrame.getChat().getTf().addActionListener(new SendEvent());//ä���� ���� enterŰ�� ���� �� �̺�Ʈ
			mainFrame.getChat().getEnter().addActionListener(new SendEvent()); //ä���� ���� ���۹�ư�� ���� �� �̺�Ʈ

			while(true) { //����ڰ� �г����� �ʱ�ȭ �Ҷ� ���� ��� �Ͽ� ����.
				Thread.sleep(50); 
				if(mainFrame.getScore().getNickState()) {  //�ʱ�ȭ ���� ��
					if(mainFrame.getScore().getMyNickName().trim().length() == 0) //�г����� �������� �Է����� ��
						break; //while�� ��������
					writer.println("nickName/"+mainFrame.getScore().getMyNickName()); //������ �ƴ� �� receiveThread�� ������ �г����� Ű����� �Բ� ������.
					break;  //while�� ��������
				} 
			}

			mainFrame.getChat().getTa().append("System :����Ǿ����ϴ�! ���� ���Ӽ����� �о��ּ���.\nSystem :�غ� �Ϸ�Ǹ� Ready��ư�� �����ּ���.\n");
			
			while(true) { //�غ�Ϸ�  ��ư�� ���� �� ���� ��� ����.
				Thread.sleep(100); 
				if(mainFrame.getGame().getPlayOk()) { //����ڰ� �غ�Ϸ� ��ư�� ������ playOk�� true�� ���� ���
					writer.println("ready/"); //��뿡�� ready Ű���带 ������.
					mainFrame.getChat().getTa().append("System :�غ�Ϸ�!\n"); //ä��â�� �غ�Ϸᰡ ó�������� �˸���. 
					break; //while�� ��������
				}
			}
		} catch (IOException  | InterruptedException e) { 
			e.printStackTrace(); 
		} 
	}
	
	class SendEvent implements ActionListener{ //enterŰ�� ���� ���� "����"��ư�� ������ ���� �̺�Ʈ Ŭ����
		@Override
		public void actionPerformed(ActionEvent e) { 
			String 	sendString =mainFrame.getChat().getTf().getText(); //�ý�Ʈ �ʵ忡 �ִ� ���ڿ��� sendString�� �����Ѵ�.
			writer.println("chat/"+mainFrame.getScore().getMyNickName() +" :"+ sendString ); //������ �ƿ�ǲ��Ʈ���� sendString�� ������.
			mainFrame.getChat().getTa().append(mainFrame.getScore().getMyNickName() +" :"+ sendString +"\n");  //JTextArea�� sendString�� �߰��Ѵ�.
			mainFrame.getChat().getTf().setText(""); //�ؽ�Ʈ �ʵ带 �ٽ� �ƹ��͵� ���� ���·� �����.
			writer.flush(); //���۸��Ǿ� ���� ��ϵ��� ���� �����͸� ��� ��Ʈ���� ��� ����Ѵ�.
			mainFrame.getChat().setVisible(true);  //chat�г��� ȭ�鿡 �����ش�.
		}
	}
}
