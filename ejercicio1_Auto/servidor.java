package ejercicio1_Auto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.IIOException;

public class servidor {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataOutputStream flujosalida;
		DataInputStream flujoentrada;
		int Puerto = 6000;
		ServerSocket servidor= null;
		
		try {
			servidor= new ServerSocket(Puerto);
			String cadena;
			Socket cliente = null;
			System.out.println("Esperando CLiente...");
			do {
				
			
			cliente = servidor.accept();
			
			//RECIBO DEL CLIENTE
			 flujoentrada = new DataInputStream(cliente.getInputStream());
			cadena=flujoentrada.readUTF();
			System.out.println("RECIBIENDO DEL Cliente: \n\t"+cadena);
			
			
			//FLUJO DE SALIDA AL  CLIENTE
			
			flujosalida = new DataOutputStream(cliente.getOutputStream());
	//ENVIO MENSAJE EN MINUSCULA PARA EL SERVIDOR
			
			flujosalida.writeUTF(cadena.toLowerCase());
		
			
			//CERRAR STREAMS Y SOCKETS
			}while(!cadena.equalsIgnoreCase("*"));
		
			flujoentrada.close();
			flujosalida.close();
			cliente.close();
			servidor.close();
		}catch(IIOException io){
			
		}
	}
	}

