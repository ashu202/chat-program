package chatProgram;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
class Client1
{
	public static void main(String args[])
	{
		JFrame frame= new JFrame("TextArea frame");
		JPanel panel=new JPanel();
		JTextArea jt= new JTextArea();
		frame.add(panel);
		panel.add(jt);
		frame.setSize(250,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	


		try
		{
			Socket skt= new Socket ("localhost",1234);
			//Socket skt=srvr.accept();
			BufferedReader in =new BufferedReader(new InputStreamReader(skt.getInputStream()));
		    //textArea t= new textArea (skt.getInputStream());
			System.out.println("Server has connected!");
			PrintWriter out=new PrintWriter(skt.getOutputStream(),true);
			//System.out.println("Sending String"+data+"\n");
			//out.print(data);
			out.close();
			skt.close();
			//srvr.close();
			System.out.println("Received String");
			while(!in.ready()){}
		          //String str=new String(in.readLine());
			//	         jt.append(in.readLine());
                           System.out.println(in.readLine());

			//System.out.println(in.readLine());
			in.close();
		}
		catch (Exception e)
		{
		         // String str=new String(in.readLine());
		         // jt.append("It Didn't work");
                          System.out.println("It Didn't work");
		//	System.out.println("Whoops!It Didn't work!\n");
		}

	}
}
