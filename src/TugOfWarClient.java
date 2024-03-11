import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class TugOfWarClient
{
	private static String address = "localhost";
	private static int port = 1234;

	public static void main (String[] args)
	{
		Socket s;
		DataInputStream  dis = null;
		DataOutputStream dos = null;

		if (args.length > 0)
		{
			address = args[0];
		}
		try
		{
			s = new Socket (address, port);
			dis = new DataInputStream  (s.getInputStream());
			dos = new DataOutputStream (s.getOutputStream());
			System.out.println ("Connexió acceptada.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			// Esperem a que comenci el joc
			String str = dis.readUTF(); // El servidor ens envia l'equip
			System.out.println (str + " comença!");
			BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

			for (;;)
			{
				str = br.readLine();
				dos.writeUTF (str);
				dos.flush();
			}
		}
		catch (Exception e)
		{
			// No fer res
		}
	}

}
