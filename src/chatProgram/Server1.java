package chatProgram;
import java.io.*;
import java.net.*;
class Server1	
{
	public static void main(String args[]){
	try
	{
	ServerSocket srvr= new ServerSocket (1234);
	System.out.println("Send msg to client");
	Socket skt=srvr.accept();
	PrintWriter cout=new PrintWriter(skt.getOutputStream(),true);
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String str=br.readLine();
	cout.print(str); cout.close(); skt.close(); srvr.close();
    }
catch (Exception e)
{
System.out.println("Didn't Work Server" );
}
}}

