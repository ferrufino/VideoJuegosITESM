/**
 * Clase Asteroide
 *
 * @author Gustavo Ferrufino
 * @version 1.00 2008/6/13
 */
import java.awt.Image;

public class Asteroide extends Figura {

private int incrementoVel;    // Incremento Velocidad de Asteroide	
    /**
     * Metodo constructor que hereda los atributos de la clase <code>Figura</code>.
     * @param posX es la <code>posiscion en x</code> del objeto Asteroide.
     * @param posY es el <code>posiscion en y</code> del objeto Asteroide.
     * @param image es la <code>imagen</code> del objeto Asteroide.
     */
        
    public Asteroide(int posX,int posY,Image image, int incrementoVel){
		super(posX,posY,image);
                this.incrementoVel=incrementoVel;
	}
        
	/**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param incrementoVel es la <code>posicion en x</code> del objeto.
	 */
	public void setincrementoVel(int incrementoVel) {
		this.incrementoVel = incrementoVel;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return incrementoVel es la <code>posicion en x</code> del objeto.
	 */
	public int getincrementoVel() {
		return incrementoVel;
	}
	
}


