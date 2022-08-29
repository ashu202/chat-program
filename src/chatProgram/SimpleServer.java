package chatProgram;
import java.io.*;
import java.net.*;
class SimpleServer
{
public static void main(String args[]) throws IOException
{
ServerSocket s= new ServerSocket(1234);
Socket s1=s.accept();
System.out.println("Client get connected");
//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
//System.out.println("enter text which you want to send");
String st=br.readLine();
System.out.println("Received String is");
System.out.println(st);
st=st.toUpperCase();
PrintStream p=new PrintStream(s1.getOutputStream());
p.println(st);
s1.close();
s.close();
}
}
