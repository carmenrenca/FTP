package EJERCICIO5;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ejercicio5 extends JFrame {
	
	static ejercicio5 frame = new ejercicio5();
	private JPanel contentPane;
	public JTextField textField;
	public JTextField textField_1;
	static PrintWriter fsalida;

	static BufferedReader fentrada;

	static BufferedReader entrada;
	String cadena = null;
	String textrecibo=null;
	static Socket Cliente;
	static String Host="localhost";
	static int puerto=6000;
	private JButton btnSalir;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		System.out.println("Cliente Iniciado");
		Cliente= new Socket(Host,puerto);
		fsalida=new PrintWriter(Cliente.getOutputStream(), true);

		fentrada= new BufferedReader(new InputStreamReader(Cliente.getInputStream()));

		entrada= new BufferedReader(new InputStreamReader(System.in));
	
	
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
	
	public void entrada() throws UnknownHostException, IOException {
	 this.cadena= this.textField.getText();

		
			fsalida.println(cadena);
		
		this.textField_1.setText(this.recibo());
	}
	
	public void cerrar() throws IOException {
		
		
		System.out.println("Cerrando cliente...");
		 this.cadena= "*";

			
			fsalida.println(cadena);
		System.out.println("       =>Desconecta IP "+Cliente.getInetAddress()+" Puerto remoto: "+Cliente.getPort());
		fsalida.close();

		fentrada.close();

		System.out.println("FIN");

		entrada.close();

		Cliente.close();
		
		System.exit( 0 );
		frame.setVisible(false);
	}
	
	public String recibo() throws IOException {
		textrecibo=fentrada.readLine();
		return textrecibo;
	}
	/**
	 * Create the frame.
	 */
	public ejercicio5() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(46, 62, 187, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(46, 107, 187, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(299, 61, 89, 23);
		contentPane.add(btnEnviar);
		btnEnviar.addActionListener(new ActionListener() {
			 public void actionPerformed (ActionEvent e) {
				
				 try {
					entrada();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 }
		});
		
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(299, 106, 89, 23);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			 public void actionPerformed (ActionEvent e) {
				
				 try {
					 cerrar();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 }
		});
	}

	}



