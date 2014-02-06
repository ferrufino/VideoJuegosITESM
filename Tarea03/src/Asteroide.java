
/**
 * Clase Asteroide
 *
 * @author Gustavo Ferrufino
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.awt.*;
import java.applet.*;

public class Asteroide extends Figura {

    int recX; //Pos x del rectangulo
    int recY; //Pos Y del rectangulo	
    Rectangle r1;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Figura</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto Asteroide.
     * @param posY es el <code>posiscion en y</code> del objeto Asteroide.
     * @param image es la <code>imagen</code> del objeto Asteroide.
     */

    public Asteroide(int posX, int posY, Image image) {
        super(posX, posY, image);

    }

    public void setRecX(int x) {
        recX = x;
    }

    public int getRecX() {
        return recX;
    }

    public void setRecY(int x) {
        recY = x;
    }

    public int getRecY() {
        return recY;
    }

    public void buildRec(int altura, int ancho) {
        r1 = new Rectangle(posX + 10, posY + altura - 10, ancho - 10, altura / 10);
    }

    public boolean intersectaAbajo(Figura obj) {
        return r1.intersects(obj.getPerimetro());
    }
}


