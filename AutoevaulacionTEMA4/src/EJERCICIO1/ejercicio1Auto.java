package EJERCICIO1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ejercicio1Auto extends JFrame{
	
	static JTextField cab= new JTextField();
	static JTextField cab2= new JTextField();
	static JTextField cab3= new JTextField();
	
	private static JTextField campo=new JTextField();
	private static JTextField campo2=new JTextField();
	
	JButton botonCargar=new JButton("Subir fichero");
	static JButton botonDescargar=new JButton("Descargar fichero");
	static JButton botonBorrar=new JButton("Eliminar fichero");
	static JButton botonCreaDir=new JButton("Crear carpeta");
	JButton botonDelDir=new JButton("Eliminar carpeta");
	static JButton botonSalir=new JButton("Salir");
	JButton botonLogin=new JButton("conectar");
	//lista para los datos del directorio
	static JList listaDirec= new JList();
	
	private final Container c= getContentPane();
	
		//DATOS DEL SERVIDOR FTP -SERVIDOR LOCAL
	static FTPClient cliente=new FTPClient();
	String servidor="localhost";
	String user="usuario2000";
	String pass="1234";
	boolean login;
	
	static String direcInicial="/";
	//para saber el directorio del fichero selecionado
	static String direcSelec=direcInicial;
	static String ficheroSelec="";
	public ejercicio1Auto() throws SocketException, IOException {
		super("Cliente Basico FTP");
		
		//para ver los comandos que se originan
		
		cliente.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		cliente.connect(servidor);//conexion al servidor
		cliente.enterLocalPassiveMode();//modo pasivo
		//CREAMOS UN PANEL DE LOGUEO 
		JFrame login= new JFrame();
		login.setSize(400, 400);
		login.setVisible(true);
		JLabel titulo=new JLabel("Introduce usuario y la contraseña");
		JTextField usuario=new JTextField();
		
		JPasswordField contraseña=new JPasswordField();
	
		login.add(titulo);
		login.add(usuario);
		login.add(contraseña);
		login.add(botonLogin);
		
		Dimension size=titulo.getPreferredSize();
		titulo.setBounds(120, 50,size.width, size.height);
		titulo.setVisible(true);
		usuario.setBounds(100,90,180,20);
		usuario.setVisible(true);
		contraseña.setBounds(100,130,180,20);
		contraseña.setVisible(true);
		botonLogin.setBounds(120, 300, 130, 30);
		botonLogin.setVisible(true);
	
	
		botonLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				user=usuario.getText();
				pass=contraseña.getText().toString();
			
				try {
					conectar(user,pass, login);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//Se nos muestra una alerta  en la que escribiremos el nombre de la carpeta que vayamos a eliminar
		//solo se puede borrar carpetas vacias 
		
		botonDelDir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombreCarpeta=JOptionPane.showInputDialog(null,"Introduce nombre de directorio a eliminar","carpeta");
				if(!(nombreCarpeta==null)) {
					String directorio=direcSelec;
					if(!direcSelec.contentEquals("/"))
						directorio=directorio+"/";

					directorio+=nombreCarpeta.trim();

					try {
						if(cliente.removeDirectory(directorio)) {
							String m=nombreCarpeta.trim()+" => Se ha eliminado correctamtente";
							JOptionPane.showMessageDialog(null, m);
							campo.setText(m);
							cliente.changeWorkingDirectory(direcSelec);

							FTPFile[] ff2= cliente.listFiles();
							llenarLista(ff2,direcSelec);
						}else {
							JOptionPane.showMessageDialog(null, "No ha podido eliminarse el directorio");
						}
					} catch (IOException e2) {
					
						e2.printStackTrace();
					}
				}
			}
		});
		listaDirec.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				// TODO Auto-generated method stub
				if(lse.getValueIsAdjusting()) {
					ficheroSelec="";
					//elemento que se ha selecionado de la lista
					String fic=listaDirec.getSelectedValue().toString();

					if(listaDirec.getSelectedIndex()==0) {
						if(!fic.equals(direcInicial)) {
							try {
								cliente.changeToParentDirectory();
								direcSelec=cliente.printWorkingDirectory();
								FTPFile[] ff2=null;

								cliente.changeWorkingDirectory(direcSelec);
								ff2=cliente.listFiles();
								campo.setText("");

								llenarLista(ff2,direcSelec);
							} catch (IOException e) {
					
								e.printStackTrace();
							}
						}
						}else {
							if(fic.substring(0,6).equals("(DIR) ")) {
								//OBTENEMOS UN DIRECTORIO
								try {
									fic=fic.substring(6);
									String direcSelec2="";
									if(direcSelec.equals("/"))
										direcSelec2=direcSelec+fic;
									else
										direcSelec2=direcSelec+"/"+fic;


										cliente.changeWorkingDirectory(direcSelec2);
										FTPFile[] ff2=cliente.listFiles();
										campo.setText("DIRECTORIO: "+fic+", "+ff2.length+" elementos");
										direcSelec=direcSelec2;
										llenarLista(ff2,direcSelec);
								} catch (IOException e2) {
									// TODO Auto-generated catch block
										e2.printStackTrace();
								}
							}else {
								//OBTENEMOS IN FICHERO
								ficheroSelec=direcSelec;
								if(direcSelec.equals("/"))
									ficheroSelec+=fic;
								else
									ficheroSelec+="/"+fic;
							}
						}
						campo2.setText("DIRECTORIO ACTUAL: "+direcSelec);
					}
				}
		});
		
		botonCargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser f=new JFileChooser();

				f.setFileSelectionMode(JFileChooser.FILES_ONLY);
				f.setDialogTitle("Selecciona el Fichero a SUBIR AL SERVIDOR FTP");

				int returnVal=f.showDialog(f, "Cargar");
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					File file=f.getSelectedFile();
					String archivo=file.getAbsolutePath();
					String nombreArchivo=file.getName();
					try {
						SubirFichero(archivo,nombreArchivo);
					} catch (IOException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
		});
	}
	//En este metodo se crea un stream de entrada con los datos del fichero y se oasa como
	//argumento al metodo storeFile()
	
	public boolean SubirFichero(String archivo, String soloNombre)throws IOException {
		cliente.setFileType(FTP.BINARY_FILE_TYPE);
		BufferedInputStream in=new BufferedInputStream(new FileInputStream(archivo));
		boolean ok=false;
//establecemos le directorio de trabajo actual
		cliente.changeWorkingDirectory(direcSelec);
		if(cliente.storeFile(soloNombre, in)) {
			String s=" "+soloNombre+" => Subido correctamente";
			campo.setText(s);
			campo2.setText("Se va a actualizar el arbol de directorios");
			JOptionPane.showMessageDialog(null, s);

			FTPFile[] ff2=cliente.listFiles();
			llenarLista(ff2,direcSelec);
			ok=true;
		}else
			campo.setText("No se ha podido subir el archivo: "+soloNombre);
		return ok;
	}
	
	private static void llenarLista(FTPFile[] files,String direc2) {
		if(files==null) return;
		
		DefaultListModel modeloLista=new DefaultListModel();
		//definimos algunas propiedades para la lista
		listaDirec.setForeground(Color.blue);
		Font fuente=new Font("Courier", Font.PLAIN, 12);
		listaDirec.setFont(fuente);
		
		listaDirec.removeAll();

		try {
			//nos posicionamos en el direcorio
			cliente.changeWorkingDirectory(direc2);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		direcSelec=direc2;

		modeloLista.addElement(direc2);
		//recorremos el array con los ficheros y directorios
		for (int i = 0; i < files.length; i++) {
			if(!(files[i].getName()).equals(".") && !(files[i].getName()).equals("..")) {
				String f=files[i].getName();

				if(files[i].isDirectory()) f="(DIR) "+f;

				modeloLista.addElement(f);
			} 
		}

		try {
			listaDirec.setModel(modeloLista);
		} catch (NullPointerException n) {
			;
			// TODO: handle exception
		}
		
		//cerramos la conexion
		botonSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					cliente.disconnect();
				} catch (IOException e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				System.exit(0);

			}
		});
		
		
		botonDescargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String directorio=direcSelec;
				if(!direcSelec.equals("/"))
					directorio=directorio+"/";
				if(!ficheroSelec.contentEquals("")) {
					DescargarFichero(directorio+ficheroSelec,ficheroSelec);
				}

			}
		});
		botonBorrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String directorio=direcSelec;
				if(!direcSelec.equals("/"))
					directorio=directorio+"/";
				if(!ficheroSelec.equals(""))
					BorrarFichero(directorio + ficheroSelec,ficheroSelec);
			}
		});
	}
	
	//elegimos la carpeta donde descargar el fichero con JFileChooser() 
	//creamos un stream de salida sobre el directorio que hemos elegido 
	private static void DescargarFichero(String NombreCompleto, String NombreFichero) {
		String archivoyCarpetaDestino="";
		String carpetaDestino="";
		JFileChooser f=new JFileChooser();

		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.setDialogTitle("Selecciona el Directorio donde DESCARGAR el fichero");

		int returnVal=f.showDialog(null, "Descargar");
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			File file=f.getSelectedFile();
			carpetaDestino=(file.getAbsolutePath()).toString();
			archivoyCarpetaDestino=carpetaDestino+File.separator+NombreFichero;
			try {
				cliente.setFileType(FTP.BINARY_FILE_TYPE);
				BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(archivoyCarpetaDestino));
				if(cliente.retrieveFile(NombreCompleto, out))
					JOptionPane.showMessageDialog(null, NombreFichero+" se ha descargado con exito");
				else
					JOptionPane.showMessageDialog(null, "No se ha podido descargar: "+NombreFichero);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		botonCreaDir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombreCarpeta=JOptionPane.showInputDialog(null,"Introduce nombre de directorio","carpeta");
				if(!(nombreCarpeta==null)) {
					String directorio=direcSelec;
					if(!direcSelec.contentEquals("/"))
						directorio=directorio+"/";

					directorio+=nombreCarpeta.trim();

					try {
						if(cliente.makeDirectory(directorio)) {
							String m=nombreCarpeta.trim()+" => Se ha creado correctamtente";
							JOptionPane.showMessageDialog(null, m);
							campo.setText(m);
							cliente.changeWorkingDirectory(direcSelec);
							FTPFile[] ff2= cliente.listFiles();
							llenarLista(ff2,direcSelec);
						}else {
							JOptionPane.showMessageDialog(null, "No ha podido crearse el directorio");
						}
					} catch (IOException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}

			}
		});
	}
	
	//CREAMOS LA CONEXION CON EL SERVIDOR
	private void conectar(String usuario, String contraseña, JFrame plogin) throws IOException {
		login=cliente.login(usuario, contraseña);
		if(login) {
			plogin.setVisible(false);
			cliente.changeWorkingDirectory(direcInicial);

			FTPFile[] files=cliente.listFiles();

			llenarLista(files,direcInicial);
			//prepara campos de pantalla
			campo.setText("<<ARBOL DE DIRECTORIOS CONSTRUIDO>>");
			cab.setText("Servidor FTP: "+servidor);
			cab2.setText("Usuario: "+user);
			cab3.setText("DIRECTORIO RAIZ: "+direcInicial);
			//preparacion de la lista
			//con setSelectionMode nos aseguramos de que el usuario solo coge un elemento de la lista
			listaDirec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane barraDesplazamiento=new JScrollPane(listaDirec);
			barraDesplazamiento.setPreferredSize(new Dimension(335,420));
			barraDesplazamiento.setBounds(new Rectangle(5,65,335,420));
			c.add(barraDesplazamiento);
			c.setLayout(null);

			//AÑADIMOS LOS DIFERENTES BOTONES EN EL PLANO
			botonCargar.setBounds(370, 65, 150, 30);
			add(botonCargar);

			botonDescargar.setBounds(370, 115, 150, 30);
			add(botonDescargar);

			botonBorrar.setBounds(370, 165, 150, 30);
			add(botonBorrar);

			botonCreaDir.setBounds(370, 215, 150, 30);
			add(botonCreaDir);

			botonDelDir.setBounds(370, 265, 150, 30);
			add(botonDelDir);

			botonSalir.setBounds(370, 315, 150, 30);
			add(botonSalir);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(580,600);
			setVisible(true);
		}
	}

	//lanzamos una advertencia antes de borrar el archivo
	//con deleteFile() lo eliminamos 
	
	private static void BorrarFichero(String nombreCompleto, String nombreFichero) {
		int seleccion=JOptionPane.showConfirmDialog(null, "Desea eliminar el fichero seleccionado?");
		if(seleccion==JOptionPane.OK_OPTION) {
			try {
			
				if(cliente.deleteFile(nombreCompleto)) {
					String m=nombreFichero+" => Eliminado correctamente";
					JOptionPane.showMessageDialog(null, m);
					campo.setText(m);
					cliente.changeWorkingDirectory(direcSelec);
					FTPFile[] ff2=cliente.listFiles();
					llenarLista(ff2, direcSelec);
				}else
					JOptionPane.showMessageDialog(null, nombreFichero+" => No se ha podido eliminar");
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		// TODO Auto-generated method stub
		new ejercicio1Auto();
	}
	
}

