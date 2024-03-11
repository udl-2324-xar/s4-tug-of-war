import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TugOfWarServer
{
	private static int port = 1234;
	private static final int LIMIT = 10;
	private static final int SLEEP_TIME = 50; // Per evitar esperes actives i forçar a anar canviant de fil
	private static int sum = 0; // Variable per a la suma compartida
	private static Object sumLock = new Object(); // Token per a compartir la suma

	public static void main (String[] args)
	{
		try
		{
			ServerSocket ss = new ServerSocket (port);

			Socket sPos = ss.accept(); // Equip positiu acceptat
			Thread tPos = new Thread (new Server (sPos, +1), "Positiu");

			Socket sNeg = ss.accept(); // Equip negatiu acceptat
			Thread tNeg = new Thread (new Server (sNeg, -1), "Negatiu");

			Thread.sleep (10 * SLEEP_TIME);
			// Iniciem els fils
			tPos.start();
			tNeg.start();

			int currentSum; // Per a guardar la suma actual en cada iteració
			int sleepCounter = 0; // comptador de temps

			for (;;)
			{
				Thread.sleep (SLEEP_TIME);
				sleepCounter += SLEEP_TIME;
				synchronized (sumLock)
				{
					currentSum = sum;
				}
				if (sleepCounter % 1000 == 0){
					// mostra el valor cada segon
					System.out.println("Current value: " + currentSum);
				}
				if (currentSum < -LIMIT)
				{
					System.out.println ("\u0007L'equip negatiu ha guanyat!");
					// El \u0007 fa sonar la campana, si el terminal ho admet
					break;
				}
				else if (currentSum > LIMIT)
				{
					System.out.println ("\u0007L'equip positiu ha guanyat!");
					// El \u0007 fa sonar la campana, si el terminal ho admet
					break;
				}
			}
			sPos.close();
			sNeg.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static class Server implements Runnable
	{
		private Socket s;
		private int team;

		public Server (Socket s, int team)
		{
			this.s = s;
			this.team = team;
		}

		public void run()
		{
			try
			{
				String name = Thread.currentThread().getName();
				System.err.println (name + ": Connexió acceptada.");
				DataInputStream  dis = new DataInputStream  (s.getInputStream());
				DataOutputStream dos = new DataOutputStream (s.getOutputStream());

				String str = "";
				dos.writeUTF (name); // Enviem l'equip al jugador

				for (;;)
				{
					Thread.sleep (SLEEP_TIME);
					str = dis.readUTF();
					synchronized (sumLock)
					{
						sum += team;
					}
				}
			}
			catch (Exception e)
			{
				// No fer res
			}
		}
	}

}
