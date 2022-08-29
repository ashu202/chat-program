package chatProgram;
import java.net.*;
import java.io.*;
 
public class  ServerTicTac
{
	public static void main(String args[]) throws Exception
	{
		char a[][]=new char [3][3];
		int i,j;char k;
		ServerSocket ss=new ServerSocket(2000);
		Socket sk=ss.accept();
		BufferedReader cin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream cout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
		int s;
		while (  true )
		{
			s=Integer.parseInt(cin.readLine());
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

				if(((a[0][0]==k)&(a[0][1]==k)&(a[0][2]==k))|((a[0][0]==k) & (a[1][0]==k) & (a[2][0]==k))|((a[0][0]==k) & (a[1][1]==k) & (a[2][2]==k))|((a[0][1]==k) & (a[1][1]==k) & (a[2][1]==k))|((a[0][2]==k) & (a[1][2]==k) & (a[2][2]==k))|((a[0][2]==k) & (a[1][1]==k) & (a[2][0]==k))|((a[1][0]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[2][0]==k) & (a[2][1]==k) & (a[2][2]==k)))
				{
			        System.out.println("Winner is client =");
				System.out.println(k);
			         break;
				}

			System.out.print("Server : ");
			s=Integer.parseInt(stdin.readLine());s=s-1;
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
				if(((a[0][0]==k)&(a[0][1]==k)&(a[0][2]==k))|((a[0][0]==k) & (a[1][0]==k) & (a[2][0]==k))|((a[0][0]==k) & (a[1][1]==k) & (a[2][2]==k))|((a[0][1]==k) & (a[1][1]==k) & (a[2][1]==k))|((a[0][2]==k) & (a[1][2]==k) & (a[2][2]==k))|((a[0][2]==k) & (a[1][1]==k) & (a[2][0]==k))|((a[1][0]==k) & (a[1][1]==k) & (a[1][2]==k))|((a[2][0]==k) & (a[2][1]==k) & (a[2][2]==k)))
				{
			        System.out.println("Winner is Server");
			        cout.println(s);
				System.out.println(k);
			         break;
				}
	       

			cout.println(s);

			}
		ss.close();
 		sk.close();
 		cin.close();
		cout.close();
 		stdin.close();
	}
}

	

