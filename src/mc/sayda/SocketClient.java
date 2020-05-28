package mc.sayda;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClient { 
	
	static String Enter = "Type Dir: c:/Users..."
			+ ""; 
	static String FileDir;

  public final static int SOCKET_PORT = 13267;      
  public final static String SERVER = "127.0.0.1"; 
  //public final static String FILE_TO_RECEIVED = "c:/Users/mc_jojo3/Desktop/CAPE.png";

  public final static int FILE_SIZE = 6022386; 

  public static void main (String [] args ) throws IOException {    
	  
	  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	  System.out.println(Enter);  
      String FileDir = reader.readLine(); 

    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      final String FILE_TO_RECEIVED = FileDir;
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}