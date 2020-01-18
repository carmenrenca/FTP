package Ejercicio3_11;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.CORBA.DataOutputStream;

public class ClienteChat extends JFrame implements ActionListener, Runnable{

	private static final long serialVersionUID =1L;
	Socket socket= null;
	
	//strams 
	DataInputStream fentrada; //PARA LEER LOS MENSAJES
	DataOutputStream fsalida; //PARA ESCRIBIR MENSAJES
	String nombre;
	static JTextField mensaje = new JTextField();
	private JScrollPane scrollpane1;
	static JTextArea textarea1;
	JButton botonenviar = new JButton ("Enviar");
	JButton botonsalir = new JButton("Salir");
	
	boolean repetir= true;
	
	//contructor 
	public ClienteChat (Socket s, String nombre) {
		super("CONEXION DEL CLIENTE CHAT: "+nombre);
		setLayout(null);
		mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);
		textarea1= new JTextArea();


		scrollpane1=new JScrollPane(textarea1);

		scrollpane1.setBounds(10,50,400,300);

		add(scrollpane1);

		

		botonenviar.setBounds(420,10,100,30);

		add(botonenviar);

		botonsalir.setBounds(420,50,100,30);

		add(botonsalir);

		

		textarea1.setEditable(false);

		botonenviar.addActionListener(this);

		botonsalir.addActionListener(this);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		

		socket=s;

		this.nombre=nombre;
		
		try {

			fentrada=new DataInputStream(socket.getInputStream());

			fsalida=new DataOutputStream(socket.getOutputStream());

			String texto="> Entra en el chat... "+nombre;

			fsalida.writeUTF(texto);
				
		}catch(IOException e) {

			System.out.println("ERROR DE E/S");

			e.printStackTrace();

			System.exit(0);

		}
	}
	
	@Override

	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

		if(e.getSource()==botonenviar) {

			if(mensaje.getText().trim().length()==0)

				return;

			String texto=nombre + "> "+mensaje.getText();

			try {

				mensaje.setText("");

				fsalida.writeUTF(texto);

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		}

		if (e.getSource()==botonsalir) {

			String texto= "> Abandona el chat ... "+nombre;

			try {

				fsalida.writeUTF(texto);

				fsalida.writeUTF("*");

				repetir=false;

			} catch (IOException e2) {

				// TODO: handle exception

				e2.printStackTrace();

			}

		}

	}
	@Override

	public void run() {

		String texto="";

		while(repetir) {

			try {

				texto=fentrada.readUTF();

				textarea1.setText(texto);

			} catch (Exception e) {

				// TODO: handle exception

				JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n"+e.getMessage(), "<<MENSAJE DE ERROR:2>>",JOptionPane.ERROR_MESSAGE);

				repetir=false;

			}

		}

		

		try {

			socket.close();

			System.exit(0);

		} catch (IOException e) {

			e.printStackTrace();

		}

		

		

	}



	public static void main(String args[]) {

		//Puerto 

		int puerto=44444;

		Socket s=null;

		//Introducimos un nombre

		String nombre=JOptionPane.showInputDialog("Introduzca un nick: ");

		

		if(nombre.trim().length()==0) {

			System.out.println("El nombre esta vacio.....");

			return;

		}

		

		//Le 'damos' un hilo al usuario que se conecta, y seguimos esperando la conexion de mas clientes

		try {

			s=new Socket("localhost",puerto);

			ClienteChat cliente=new ClienteChat(s, nombre);

			cliente.setBounds(0,0,540,400);

			cliente.setVisible(true);

			new Thread(cliente).start();

		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n"+e.getMessage(), "<<MENSAJE DE ERROR:1>>",JOptionPane.ERROR_MESSAGE);

		}

	}
	

	
}
