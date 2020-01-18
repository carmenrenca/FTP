package EJERCICIO5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import utiles.trycatch;

public class Servidor {

	public static void main(String[] args) throws IOException {

	int puerto=6000;
	ServerSocket servidor= new ServerSocket(puerto);
	Socket ClienteConectado=null;

	System.out.println("Servidor Iniciado.....");
	
	
while(true) {
	

		
		ClienteConectado=servidor.accept();
	System.out.println("=>Conecta IP "+ClienteConectado.getInetAddress()+" Puerto Remoto: "+ClienteConectado.getPort());
	
		Hilo hilo = new Hilo(ClienteConectado);
		hilo.start();
	
	
}

			
	}

}
