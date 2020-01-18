package Ejercicio3_11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloservidorChat extends Thread{

	ComunHilos comun;
	DataInputStream fentrada;
	Socket socket = null;
	
public HiloservidorChat(Socket s, ComunHilos comun) {
	this.socket=s;
	this.comun=comun;
	
	try {
		//CREO FLUJO DE ENTRADA PARA LEER LOS MENSAJES
		
		fentrada= new DataInputStream(socket.getInputStream());
		
	}catch(IOException e) {
		System.out.println("ERROR E/S");
		e.printStackTrace();
	
}
	
}

public void run() {
	System.out.println("NUMERO DE CONEZIONES ACTUALES: "+comun.getACTUALES());
	//ENVIAMOS TODOS LOS MENSAJES
	
	String texto= comun.getMensajes();
	EnviarMensajesaTodos(texto);
	
	while(true) {
		String cadena="";
		try {
			cadena=fentrada.readUTF();
			if(cadena.trim().equals("")) {
				//CLIENTE SE DESCONECTA
				comun.setACTUALES(comun.getACTUALES() -1);
				System.out.println("NUMERO DE CONEXIONES ACTUALES: "+comun.getACTUALES());
				
				break;
				//sale del bucle 
			
				
			}
			
			comun.setMensajes(comun.getMensajes()+cadena+"\n");
			EnviarMensajesaTodos(comun.getMensajes());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//fin while
	
	try {
		socket.close();
	}catch(IOException e) {
		e.printStackTrace();
	}
	
}

private void EnviarMensajesaTodos(String texto) {
	int i;
	//recorremos tabla de socket para enviarles los mensajes
	
	for(i=0; i<comun.getCONEXIONES();i++) {
		Socket s1= comun.getelementotabla(i);
		if(!s1.isClosed()) {
			try {
				DataOutputStream fsalida2= new DataOutputStream(s1.getOutputStream());
				fsalida2.writeUTF(texto);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
}
