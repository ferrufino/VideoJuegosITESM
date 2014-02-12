
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

    /**
     * 
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto planeta.
     * @param posY es el <code>posiscion en y</code> del objeto planeta.
     */
    public Bueno(int posX, int posY) {
       
       super(posX, posY);
        
        //Se cargan las imágenes(cuadros) para la animación
        Image cat1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat1.png"));
        Image cat2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat2.png"));
        Image cat3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat3.png"));
        Image cat4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat4.png"));
        Image cat5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat5.png"));
        Image cat6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat6.png"));
        Image cat7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat7.png"));

        //Se crea la animación
        anim = new Animacion();
        anim.sumaCuadro(cat1, 100);
        anim.sumaCuadro(cat2, 100);
        anim.sumaCuadro(cat3, 100);
        anim.sumaCuadro(cat4, 100);
        anim.sumaCuadro(cat5, 100);
        anim.sumaCuadro(cat6, 100);
        anim.sumaCuadro(cat7, 100);
        
       //Se guarda la animacion
        super.setAnimacion(anim);

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
