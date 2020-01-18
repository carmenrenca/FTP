

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ejercicio4_3 extends JFrame {
	FTPClient cliente = new FTPClient ();
	static ejercicio4_3 frame = new ejercicio4_3();
	public JPanel contentPane;
	public JTextField usuario;
	public JTextField contrasena;
	static String  user;
	static String pass;
	JList list = new JList();
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ejercicio4_3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usuario = new JTextField();
		usuario.setBounds(133, 21, 159, 20);
		contentPane.add(usuario);
		usuario.setColumns(10);

	
		contrasena = new JTextField();
		contrasena.setBounds(133, 52, 159, 20);
		contentPane.add(contrasena);
		contrasena.setColumns(10);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(27, 52, 84, 14);
		contentPane.add(lblContrasea);
		JLabel lblUsuario = new JLabel("usuario");
		lblUsuario.setBounds(27, 24, 84, 14);
		contentPane.add(lblUsuario);
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(315, 83, 89, 23);
		contentPane.add(btnContinuar);
		btnContinuar.addActionListener(new ActionListener() {
			 public void actionPerformed (ActionEvent e) {
				
				 conexion();
			 }
		});
		
		list.setBounds(27, 86, 159, 117);
		contentPane.add(list);
		
		JButton btnDescargar = new JButton("Descargar");
		btnDescargar.setBounds(283, 166, 89, 23);
		contentPane.add(btnDescargar);
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					descargar();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		JButton btnSali = new JButton("Salir");
		btnSali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit( 0 );
				frame.setVisible(false);
				
			}
		});
		btnSali.setBounds(283, 214, 89, 23);
		contentPane.add(btnSali);
		
		textField = new JTextField();
		textField.setBounds(69, 230, 144, 20);
		contentPane.add(textField);
		textField.setColumns(10);
	}
	
	public void conexion () {
	
		String servidor= "localhost";
	
		try {
			System.out.println("Conectandose a  "+servidor);
			cliente.connect(servidor);
			cliente.enterLocalActiveMode();
			boolean login= cliente.login( usuario.getText(), contrasena.getText());
			cliente.setFileType(FTP.BINARY_FILE_TYPE);
			
			
	
			if(login) {
				System.out.println("Login correto");
		
				
				System.out.println("Directorio actual "+ cliente.printWorkingDirectory());
				FTPFile[] files= cliente.listFiles();
				System.out.println("Ficheros en el directorio actual: "+files.length);
				//array para visualozar el tipo de fichero
				//listamos todo los archivos que tenemos en la carpeta del ftp
				DefaultListModel modelo = new DefaultListModel();
				String tipos[]= {"Fichero"};
				String ficheritos;
				for(int i=0; i<files.length; i++) {
					ficheritos=files[i].getName();
					modelo.addElement(ficheritos);
				}
				list.setModel(modelo);
				
			
				MouseListener mouseListener = new MouseAdapter() 
				{
				    public void mouseClicked(MouseEvent e) 
				    {
				    	textField.setText((String) list.getSelectedValue());
				    }
				};
				 list.addMouseListener(mouseListener);

				
			
		}else {
			System.out.println("Login incorrecto");
		}
	}catch(IOException io) {
		io.printStackTrace();
	}
}
	//este metodo se encargara de elegir el lugar de destino y guardarlo 
	public void descargar() throws IOException {
		String ruta="";
		String namearchivo="";
		System.out.println(list.getSelectedValue());	
		String sele= (String) list.getSelectedValue();
		//STREAM DE ENTRADA CON EL FICHERO A SUBIR
		//Creamos selector de apertura

		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new java.io.File("."));

		//Titulo que llevara la ventana

		chooser.setDialogTitle("Selecciona el Directorio donde DESCARGAR el fichero");

		//Elegiremos archivos del directorio

		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		chooser.setAcceptAllFileFilterUsed(false);

		//Si seleccionamos algún archivo retornaremos su directorio

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		ruta= chooser.getSelectedFile().getAbsolutePath().toString();
		namearchivo= chooser.getSelectedFile().getName().toString();
	
		System.out.println(chooser.getSelectedFile().getAbsolutePath());
		System.out.println("Nombre archivo "+chooser.getSelectedFile().getName().toString());
		//selecionamos el archivo que queremos y lo enviaremos a la ruta de destino
		BufferedOutputStream out= new BufferedOutputStream(new FileOutputStream (ruta+"/"+list.getSelectedValue()));
		System.out.println(ruta+list.getSelectedValue());
		if(cliente.retrieveFile((String) list.getSelectedValue(),out)) System.out.println("RECUPERADDO ");
		else System.out.println("NO SE HA PODIDO DESCARGAR"); 
		out.close();
	}else {

		System.out.println("No seleccion ");

		}
}
	
}

