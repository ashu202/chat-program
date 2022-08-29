package chatProgram;
import java.io.*;
import java.net.*;
public class  ClientTicTac
{
	public static void main(String args[]) throws Exception
	{
		char a[][]=new char [3][3];
		int i,j,s;char k;
		Socket sk=new Socket("localhost",2000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream sout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
		while (  true )
		{
			System.out.print("Client : ");
			s=Integer.parseInt(stdin.readLine());s=s-1;
	        	i=s/3;
			j=s%3;
			do{
			k='0';
			}while(k=='\n');
			a[i][j]=k;
		
			System.out.println("Client side game stage");
			for(i=0;i<3;i++)
			{
				for(j=0;j<3;j++)
				{
					System.out.print(a[i][j]);System.out.print("\t");
				}
				System.out.println();
			}
				if(((a[0][0]==k)&(a[0][1]==k)&(a[0][2]==k))|((a[0][0]==k) & (a[1][0]==k) & (a[2][0]==k))|((a[0][0]==k) & (a[1][1]==k) & (a[2][2]==k))|((a[0][1]==k) & (a[1][1]==k) & (a[2][1]==k))|((a[0][2]==k) & (a[1][2]==k) & (a[2][2]==k))|((a[0][2]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[1][0]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[2][0]==k) & (a[2][1]==k) & (a[2][2]==k)))
				{
			        System.out.print("Winner is client =");
			        sout.println(s);
				System.out.println(k);
			         break;
				}
			sout.println(s);
			s=Integer.parseInt(sin.readLine());
	        	i=s/3;
			j=s%3;
			do{
			k='*';
			}while(k=='\n');
			a[i][j]=k;
		
			System.out.println("Server Side game stage");
			for(i=0;i<3;i++)
			{
				for(j=0;j<3;j++)
				{
					System.out.print(a[i][j]);System.out.print("\t");
				}
				System.out.println();
			}
				if(((a[0][0]==k)&(a[0][1]==k)&(a[0][2]==k))|((a[0][0]==k) & (a[1][0]==k) & (a[2][0]==k))|((a[0][0]==k) & (a[1][1]==k) & (a[2][2]==k))|((a[0][1]==k) & (a[1][1]==k) & (a[2][1]==k))|((a[0][2]==k) & (a[1][2]==k) & (a[2][2]==k))|((a[0][2]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[1][0]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[2][0]==k) & (a[2][1]==k) & (a[2][2]==k)))
				{
			        System.out.println("Winner is server");
				System.out.println(k);
			         break;
				}
	}
	
	sk.close();sin.close();sout.close();
}
}
