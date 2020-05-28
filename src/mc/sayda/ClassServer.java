package mc.sayda;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClassServer {
	
  static String Enter = "Type Dir: c:/Users..."
		  + ""; 
  static String FileDir;

  public final static int SOCKET_PORT = 13267; 
  //public final static String FILE_TO_SEND = "c:/Users/mc_jojo3/Documents/bo2hps5go6h21.png"; 

  public static void main (String [] args ) throws IOException {
	  
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	System.out.println(Enter);  
    String FileDir = reader.readLine(); 
	  
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
          
          // send file
          final String FILE_TO_SEND = FileDir; 
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          //if (fis!=null) fis.close();
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}