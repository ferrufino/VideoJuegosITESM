
/**
 * Clase Planeta
 *
 * @author Gustavo Ferrufino
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.lang.String;

public class Bueno extends Base {

    static String DESAPARECE;
    static String PAUSADO;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Figura</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto planeta.
     * @param posY es el <code>posiscion en y</code> del objeto planeta.
     * @param image es la <code>imagen</code> del objeto planeta.
     */
    public Bueno(int posX, int posY) {
        super(posX, posY);

    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param DESAPARECE es la <code>posicion en x</code> del objeto.
     */
    public void setDesaparece(String d) {
        DESAPARECE = d;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param DESAPARECE es la <code>posicion en x</code> del objeto.
     */
    public String getDesaparece() {
        return DESAPARECE;
    }
}
