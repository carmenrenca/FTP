import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ejercicioFTP4_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	FTPClient cliente = new FTPClient ();
	String servidor= "localhost";
	String user= "carmenrenca";
	String pasw="1234";
	String areaTexto;
	String	ruta = null;
	String namearchivo = null;
	
	try {
		System.out.println("Conectandose a  "+servidor);
		cliente.connect(servidor);
		cliente.enterLocalActiveMode();
		boolean login= cliente.login(user, pasw);
		cliente.setFileType(FTP.BINARY_FILE_TYPE);
		
		String direc= "/NUEVODIRECT";
		if(login) {
			System.out.println("Login correto");
			//COMPROBAMOS SI EXSTE EL DIRECTORIO SI NO, LO CREAMOS 
			
		if(!cliente.changeWorkingDirectory(direc)) {
				String directorio="NuevoDirec";
				if(cliente.makeDirectory(directorio)) {
					System.out.println("Directorio: "+directorio+" creado....");
					cliente.changeWorkingDirectory(directorio);
				}else {
					
					cliente.changeWorkingDirectory(directorio);
				}
			}
			System.out.println("Directorio actual: "+ cliente.printWorkingDirectory());
			
			//STREAM DE ENTRADA CON EL FICHERO A SUBIR
			//Creamos selector de apertura

			JFileChooser chooser = new JFileChooser();

			chooser.setCurrentDirectory(new java.io.File("."));

			//Titulo que llevara la ventana

			chooser.setDialogTitle("Titulo");

			//Elegiremos archivos del directorio

			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			chooser.setAcceptAllFileFilterUsed(false);

			//Si seleccionamos algún archivo retornaremos su directorio

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			ruta= chooser.getSelectedFile().getAbsolutePath().toString();
			namearchivo= chooser.getSelectedFile().getName().toString();
		
			System.out.println(chooser.getSelectedFile().getAbsolutePath());
			System.out.println("Nombre archivo "+chooser.getSelectedFile().getName().toString());
			//Si no seleccionamos nada retornaremos No seleccion

			} else {

			System.out.println("No seleccion ");

			}
			String archivo=ruta;
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));
		
			
			if(cliente.storeFile(namearchivo, in)) 	JOptionPane.showMessageDialog(null, "Subido correctamente....");
		
			else System.out.println("No se ha podido subir....");
			FTPFile[] files= cliente.listFiles();
			System.out.println("Ficheros en el directorio actual: "+files.length);
			//array para visualozar el tipo de fichero
		
			String tipos[]= {"Fichero","Directorio", "Enlace simb."};
			for(int i=0; i<files.length; i++) {
				System.out.println("\t"+files[i].getName()+" =>"+tipos[files[i].getType()]);
			}
			
			in.close(); //cerrrar  flujo 
			cliente.logout(); //logout del usuario
			cliente.disconnect(); //desconexion del servidor
		}
	}catch(IOException ioe) {
		ioe.printStackTrace();
	}
	}


}
