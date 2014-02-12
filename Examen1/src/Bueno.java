
/**
 * Clase Planeta
 *
 * @author Gustavo Ferrufino
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.lang.String;
import java.awt.Toolkit;

public class Bueno extends Base {

    static String DESAPARECE;
    static String PAUSADO;
    Animacion anim;

    /**ñ
     * Metodo constructor que hereda los atributos de la clase
     * <code>Figura</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto planeta.
     * @param posY es el <code>posiscion en y</code> del objeto planeta.
     * @param image es la <code>imagen</code> del objeto planeta.
     */
    public Bueno(int posX, int posY) {
        super(posX, posY);
        //Se cargan las imágenes(cuadros) para la animación
		Image cat1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse1.png"));
		Image cat2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse2.png"));
		Image raton3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse3.png"));
		Image raton4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse4.png"));
		Image raton5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse5.png"));
		Image raton6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse6.png"));
		Image raton7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse7.png"));
		Image raton8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse8.png"));
		Image raton9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse9.png"));
		Image raton10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse10.png"));
		Image raton11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse11.png"));
		Image raton12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse12.png"));
                
                //Se crea la animación
		anim = new Animacion();
		anim.sumaCuadro(cat1, 100);
		anim.sumaCuadro(cat2, 100);
		anim.sumaCuadro(raton3, 100);
		anim.sumaCuadro(raton4, 100);
		anim.sumaCuadro(raton5, 100);
		anim.sumaCuadro(raton6, 100);
		anim.sumaCuadro(raton7, 100);
		anim.sumaCuadro(raton8, 100);
		anim.sumaCuadro(raton9, 100);
		anim.sumaCuadro(raton10, 100);
		anim.sumaCuadro(raton11, 100);
		anim.sumaCuadro(raton12, 100);


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

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param PAUSADO es la <code>posicion en x</code> del objeto.
     */
    public void setPausado(String p) {
        PAUSADO = p;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param PAUSADO es la <code>posicion en x</code> del objeto.
     */
    public String getPausado() {
        return PAUSADO;
    }
}
