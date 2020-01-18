
import java.io.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class ejercicioFTP4_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FTPClient cliente = new FTPClient();
		String servFTP= "localhost";
		System.out.println("Nos conectamos a: "+servFTP);
		String usuario= "carmenrenca";
		String clave="1234";
		
		try {
			cliente.connect(servFTP);
			cliente.enterLocalPassiveMode(); //mode pasivo
			boolean login =cliente.login(usuario, clave);
			if(login) {
				System.out.println("Login correcto...");
				
			}else {
				System.out.println("Login incorrecto...");
			}
			System.out.println("Directorio actual "+ cliente.printWorkingDirectory());
			FTPFile[] files= cliente.listFiles();
			System.out.println("Ficheros en el directorio actual: "+files.length);
			//array para visualozar el tipo de fichero
			
			String tipos[]= {"Fichero","Directorio", "Enlace simb."};
			for(int i=0; i<files.length; i++) {
				System.out.println("\t"+files[i].getName()+" =>"+tipos[files[i].getType()]);
			}
			
			cliente.changeWorkingDirectory("subcarpeta/");
			System.out.println("Directorio actual "+ cliente.printWorkingDirectory());
			FTPFile[] files2= cliente.listFiles();
			System.out.println("Ficheros en el directorio actual: "+files.length);
			//array para visualozar el tipo de fichero
			
			String tipos2[]= {"Fichero","Directorio", "Enlace simb."};
			for(int i=0; i<files2.length; i++) {
				System.out.println("\t"+files2[i].getName()+" =>"+tipos2[files2[i].getType()]);
			}
			
			
			boolean logout =cliente.logout();
			if(logout) System.out.println("Logout del servidor ftp");
			else System.out.println("Error al hacer Logout...");
			
			cliente.disconnect();
			
			System.out.println("Desconectado...");
			
			
		}catch(IOException io) {
			io.printStackTrace();
		}
	}

}
