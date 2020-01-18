package Ejercicio3_11;

import java.net.Socket;

public class ComunHilos {
int CONEXIONES;
int ACTUALES;
int MAXIMO;
Socket tabla[]= new Socket[MAXIMO];
String mensajes;
public ComunHilos(int maximo, int conexiones, int actuales, Socket[]tabla) {
	this.MAXIMO=maximo;
	this.ACTUALES=actuales;
	this.CONEXIONES=conexiones;
	this.tabla=tabla;
	
	
}
public int getCONEXIONES() {
	return CONEXIONES;
}
public synchronized void setCONEXIONES(int cONEXIONES) {
	CONEXIONES = cONEXIONES;
}
public int getACTUALES() {
	return ACTUALES;
}
public synchronized void setACTUALES(int aCTUALES) {
	ACTUALES = aCTUALES;
}
public int getMAXIMO() {
	return MAXIMO;
}
public void setMAXIMO(int mAXIMO) {
	MAXIMO = mAXIMO;
}
public Socket[] getTabla() {
	return tabla;
}
public void setTabla(Socket[] tabla) {
	this.tabla = tabla;
}
public String getMensajes() {
	return mensajes;
}
public void setMensajes(String mensajes) {
	this.mensajes = mensajes;
}

public synchronized void addtabla(Socket tabla2, int i) {
	this.tabla[i]=tabla2;
}
public Socket getelementotabla(int i ) {
	return this.tabla[i];
}

}
