
import java.awt.Image;
import java.awt.Toolkit;

public class Malo extends Base {

    private static int conteo;
    private int speed;

    public Malo(int posX, int posY) {
        super(posX, posY);

        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/bad_dog1.png"));
        Image malo2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/bad_dog2.png"));
        Image malo3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/bad_dog3.png"));
        Image malo4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/bad_dog4.png"));

        
        animacion = new Animacion();
        animacion.sumaCuadro(malo1, 100);
        animacion.sumaCuadro(malo2, 100);
        animacion.sumaCuadro(malo3, 100);
        animacion.sumaCuadro(malo4, 100);
      
        speed = (int) ((Math.random() * (9))+ 1);
    }
    

    public static int getConteo() {
        return conteo;
    }

    public static void setConteo(int cont) {
        conteo = cont;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int vel) {
        speed = vel;
    }
}

