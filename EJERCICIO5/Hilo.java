package EJERCICIO5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import utiles.trycatch;

public class Hilo extends Thread{

	
	String Host = "localhost";
	int puerto= 6000;


	String cadena= "";


	

	int id=1;
	Socket socket=null;
	BufferedReader fentrada;
	PrintWriter fsalida;


	
	public Hilo(Socket s) throws IOException{
		  this.socket=s;
		  this.id=id;
		  this.cadena=cadena;
		  System.out.println(cadena);
		 
		  fsalida= new PrintWriter(socket.getOutputStream(),true);

			fentrada=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}


	public void run() {

		while(!cadena.trim().equals("*")) {

			try {

				cadena=fentrada.readLine();
System.out.println("cadenaa"+cadena );
			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}
			fsalida.println(cadena.trim().toUpperCase());

			System.out.println("Escribo "+cadena.trim().toUpperCase());
		}
		
		fsalida.close();
		
		try {
			fentrada.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	

		
		
	}
}
