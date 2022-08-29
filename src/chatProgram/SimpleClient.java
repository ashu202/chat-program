package chatProgram;

import java.io.*;
import java.net.*;

class SimpleClient {
	public static void main(String args[]) throws IOException {
//ServerSocket s= new ServerSocket(1234);
		Socket s1 = new Socket("localhost", 1234);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input string");
		String st = br.readLine();
		System.out.println(st);
		PrintStream p = new PrintStream(s1.getOutputStream());
		p.println(st);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		st = br1.readLine();
		System.out.println(st);
		s1.close();
//s.close();
	}
}
