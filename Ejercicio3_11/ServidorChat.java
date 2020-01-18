package Ejercicio3_11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {
static final int MAXIMO = 10;//MAXIMO DE CONEXIONES PERMITIDAS
	public static void main (String args[]) throws IOException{
		int PUERTO=4444;
		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");
		
		Socket tabla[] = new Socket[MAXIMO];
		ComunHilos comun = new ComunHilos(MAXIMO, 0,0, tabla);
		
		while(comun.getCONEXIONES()<MAXIMO) {
			Socket socket= new Socket();
			socket= servidor.accept(); //esperando cliente
			
			//OBKETO COMPARTIDO POR LOS HILOS
			
			comun.addtabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES()+1);
			comun.setCONEXIONES(comun.getCONEXIONES()+1);
			
			HiloservidorChat hilo = new HiloservidorChat(socket, comun);
			hilo.start();
		}
		servidor.close();
	}
	
}
