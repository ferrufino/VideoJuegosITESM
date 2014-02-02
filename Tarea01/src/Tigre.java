/**
 *	Class Tigre
 *
 *	Tutorial Dibujando en un Applet - Tarea 01
 *
 *	@autor Gustavo Ferrufino
 *      @matricula A00812572
 *	@version 1.0 22/01/2014
 */
 
import javax.swing.ImageIcon;
import java.awt.Image;

public class Tigre {
	
	private int posX;   //posicion en x.       
	private int posY;   //posicion en y.
	private int numVelocity;    //velocidad en que se desplaza el Tigre
        private ImageIcon icono;    //icono.
        
	
	/**
	 * Metodo constructor usado para crear el objeto elefante
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
	public Tigre(int posX, int posY , int numVelocity ,Image image) {
		this.posX=posX;
		this.posY=posY;
		icono = new ImageIcon(image);
                this.numVelocity=numVelocity;
	}

    Tigre(Image image) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
	
	/**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param posX es la <code>posicion en x</code> del objeto.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return posX es la <code>posicion en x</code> del objeto.
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param posY es la <code>posicion en y</code> del objeto.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	public int getPosY() {
		return posY;
	}
        
        /** 
         * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param numVelocity es la <code>posicion en y</code> del objeto.
	 */
	public void setNumVelocity(int numVelocity) {
		this.numVelocity = numVelocity;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return numVelocity es la <code>posicion en y</code> del objeto.
	 */
	public int getNumVelocity() {
		return numVelocity;
	}
   
	/**
	 * Metodo modificador usado para cambiar el icono del objeto 
	 * @param icono es el <code>icono</code> del objeto.
	 */
	public void setImageIcon(ImageIcon icono) {
		this.icono = icono;
	}
	
	/**
	 * Metodo de acceso que regresa el icono del objeto 
	 * @return icono es el <code>icono</code> del objeto.
	 */
	public ImageIcon getImageIcon() {
		return icono;
	}
	
	/**
	 * Metodo de acceso que regresa el ancho del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del icono.
	 */
	public int getAncho() {
		return icono.getIconWidth();
	}
	
	/**
	 * Metodo de acceso que regresa el alto del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el alto del icono.
	 */
	public int getAlto() {
		return icono.getIconHeight();
	}
	
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenI() {
		return icono.getImage();
	}
	
}