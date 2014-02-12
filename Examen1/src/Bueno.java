
/**
 *
 * @author Ferrufino
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Bueno extends Base {

    private int SPEED;

    public Bueno(int posX, int posY) {
        super(posX, posY);

        Image bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat1.png"));
        Image bueno2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat2.png"));
        Image bueno3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat3.png"));
        Image bueno4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat4.png"));
        Image bueno5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat5.png"));
        animacion = new Animacion();
        animacion.sumaCuadro(bueno1, 100);
        animacion.sumaCuadro(bueno2, 100);
        animacion.sumaCuadro(bueno3, 100);
        animacion.sumaCuadro(bueno4, 100);
        animacion.sumaCuadro(bueno5, 100);

        SPEED = 5;

    }

    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getGone() {
        return DESAPARECE;
    }
    
       public int getSpeed() {
        return SPEED;
    }

    public void setSpeed(int cant) {
        SPEED = cant;
    }

}
