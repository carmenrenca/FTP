package ejercicio1_Auto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import utiles.trycatch;

public class cliente {
trycatch t = new trycatch();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int Puerto = 6000;
		String Host="localhost";
		trycatch t = new trycatch();
		DataInputStream flujoentrada ;
		DataOutputStream flujosalida;
			String cadena;
			
				System.out.println("CLIENTE INICIADO");
				Socket cliente= new Socket(Host, Puerto);
				do {
				System.out.println("Introduce una cadena");
				
				//CREAR FLUJO DE ENTRADA PARA EL SERVIDOR
				
					
				
				OutputStream salida= null;
				salida= cliente.getOutputStream();
				 flujosalida = new DataOutputStream(salida);
				
				//ENVIAR MESAJE A CLIENTE
				
				flujosalida.writeUTF(t.try_String());
				
				
				
			    flujoentrada = new DataInputStream(cliente.getInputStream());
				cadena=flujoentrada.readUTF();
				System.out.println("RECIBIENDO DEL Servidor: \n\t"+cadena);
		
				}while(!cadena.equalsIgnoreCase("*"));
				
				flujosalida.close();
				cliente.close();
		
			
	}
	}

