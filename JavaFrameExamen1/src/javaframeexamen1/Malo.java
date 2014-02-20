/*
 *Class Malo
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package javaframeexamen1;

import java.awt.Image;
import java.awt.Toolkit;

public class Malo extends Base {

    private static int CONTEO;
    private int SPEED;

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

        SPEED = (int) ((Math.random() * (3)) + 3);

        //CONTEO
    }

//Regresa el conteo del socre
    public static int getConteo() {
        return CONTEO;
    }
//Establece o modifica el score/conteo

    public static void setConteo(int cont) {
        CONTEO = cont;
    }

    //Devuelve la velocidad
    public int getSpeed() {
        return SPEED;
    }

    //Define la velocidad
    public void setSpeed(int cant) {
        SPEED = cant;
    }
}
