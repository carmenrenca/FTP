package EJERCICIO2;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Writer;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class EJERCICIO2 {

	private JFrame frame;
	private JTextField servidor;
	private JTextField puerto;
	private JTextField usuario;
	private JTextField clave;
	private JTextField remitente;
	private JTextField destinatario;
	private JTextField asunto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EJERCICIO2 window = new EJERCICIO2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EJERCICIO2() {
		 
		frame = new JFrame();
		frame.setBounds(100, 100, 532, 446);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Servidor SMTP:");
		lblNewLabel.setBounds(27, 28, 126, 14);
		frame.getContentPane().add(lblNewLabel);
		
		servidor = new JTextField();
		servidor.setBounds(110, 25, 86, 20);
		frame.getContentPane().add(servidor);
		servidor.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setBounds(226, 28, 46, 14);
		frame.getContentPane().add(lblPuerto);
		
		puerto = new JTextField();
		puerto.setBounds(282, 25, 86, 20);
		frame.getContentPane().add(puerto);
		puerto.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(27, 70, 46, 14);
		frame.getContentPane().add(lblUsuario);
		
		usuario = new JTextField();
		usuario.setBounds(110, 67, 86, 20);
		frame.getContentPane().add(usuario);
		usuario.setColumns(10);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(226, 70, 46, 14);
		frame.getContentPane().add(lblClave);
		
		clave = new JTextField();
		clave.setBounds(282, 67, 86, 20);
		frame.getContentPane().add(clave);
		clave.setColumns(10);
		
		JButton conectar = new JButton("Conectar");
		conectar.setBounds(405, 66, 89, 23);
		frame.getContentPane().add(conectar);
		
		JLabel lblRemitente = new JLabel("Remitente");
		lblRemitente.setBounds(27, 120, 73, 14);
		frame.getContentPane().add(lblRemitente);
		
		remitente = new JTextField();
		remitente.setBounds(110, 117, 126, 20);
		frame.getContentPane().add(remitente);
		remitente.setColumns(10);
		
		JLabel lblDestinatario = new JLabel("Destinatario");
		lblDestinatario.setBounds(259, 120, 79, 14);
		frame.getContentPane().add(lblDestinatario);
		
		destinatario = new JTextField();
		destinatario.setBounds(348, 117, 86, 20);
		frame.getContentPane().add(destinatario);
		destinatario.setColumns(10);
		
		JLabel lblAsunto = new JLabel("Asunto");
		lblAsunto.setBounds(27, 165, 62, 14);
		frame.getContentPane().add(lblAsunto);
		
		asunto = new JTextField();
		asunto.setBounds(110, 162, 313, 20);
		frame.getContentPane().add(asunto);
		asunto.setColumns(10);
		
		JTextArea cuerpo = new JTextArea();
		cuerpo.setBounds(70, 222, 400, 116);
		frame.getContentPane().add(cuerpo);
		
		JLabel lblRedataElCuerpo = new JLabel("Redata el cuerpo del mensaje:");
		lblRedataElCuerpo.setBounds(182, 197, 197, 14);
		frame.getContentPane().add(lblRedataElCuerpo);
		
		JButton enviar = new JButton("Enviar mensaje");
		enviar.setBounds(282, 349, 212, 23);
		frame.getContentPane().add(enviar);
		
        AuthenticatingSMTPClient client= new AuthenticatingSMTPClient();
		conectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    int respuesta;
	            //creacion de la clave para establecer un canal seguro
	            KeyManagerFactory kmf = null;
				try {
					kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				} catch (NoSuchAlgorithmException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	           try {
				kmf.init(null, null);
			} catch (UnrecoverableKeyException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (KeyStoreException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NoSuchAlgorithmException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	           
	            KeyManager km= kmf.getKeyManagers()[0];
	            //conectamos con el servidor smtp
	            try {
					client.connect(servidor.getText(),Integer.parseInt(puerto.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	            System.out.println("1 -"+client.getReplyString());
	            //se establece la clave para la comunicacion segura
	            client.setKeyManager(km);
	            respuesta=client.getReplyCode();
	            if(!SMTPReply.isPositiveCompletion(respuesta)){
	                try {
						client.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.out.println("CONEXION RECHAZADA.");
	                System.exit(1);
	            }
	            //se envia el comando EHLO
	            try {
					client.ehlo(servidor.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("2 - "+client.getReplyStrings());
	            
			}
			
		});
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					
				
				   if(client.execTLS()) {
		            	System.out.println("3 - "+client.getReplyString());
		           
		            //comprobamos la autentificacion de nuestra cuenta de correo
		            if(client.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, usuario.getText(), clave.getText())) {
		            	System.out.println("4 - "+client.getReplyStrings());
		            
		            	
		            	//creamos la cabecera del mensaje
		            	SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente.getText(), destinatario.getText(), asunto.getText());
		            
		            	client.setSender(remitente.getText());
		            	client.addRecipient(destinatario.getText());
		            	System.out.println("5 - "+client.getReplyStrings());
		         
		            	Writer writer = client.sendMessageData();
		            	if(writer==null) {//fallo
		            		System.out.println("FALLO AL ENVIAR DATA.");
		            		System.exit(1);
		            		
		            	}
		            	writer.write(cabecera.toString());//cabecera
		            	writer.write(cuerpo.getText());//luego mensaje
		            	try {
							writer.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	System.out.println("6 - "+client.getReplyString());
		            	
		            	
							boolean	exito = client.completePendingCommand();
							System.out.println("7 - "+client.getReplyString());

						
		            	if(!exito) {//fallo
		            		System.out.println("FALLO AL FINALIZAR TRANSACCION.");
		            		System.exit(1);
		            		
		            	}else {
		            		System.out.println("MENSAJE ENVIADO CON EXITO.....");
		            	}
		            }else {
		            	System.out.println("USUARIO NO AUTENTICADO.");
		            }
		            }else {
		            	System.out.println("FALLO AL EJECUTAR STARTTLS.");
		            }
		        
				
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
		            
			      
			}
			
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
	}
	
	

}
