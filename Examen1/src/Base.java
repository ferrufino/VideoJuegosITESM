/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan
 */
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Base {

    private int posX;    //posicion en x.       
    private int posY;	//posicion en y.
    protected Animacion animacion;    //icono.

    public Base(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public int getAncho() {
        return (new ImageIcon(animacion.getImagen()).getIconWidth());
    }

    public int getAlto() {
        return (new ImageIcon(animacion.getImagen()).getIconHeight());
    }

    public Image getImagenI() {
        return (new ImageIcon(animacion.getImagen()).getImage());
    }

    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    public boolean intersecta(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    public boolean contiene(int posX, int posY) {
        return getPerimetro().contains(posX, posY);
    }

    public Rectangle getRect() {                             //se hace el rectangulo pequeño 
        int x = getPosX() + getAncho() / 4;
        int y = getPosY() + (getAlto() * 3 / 4);
        int wi = getAncho() / 2;
        int ht = getAlto() / 4;

        return new Rectangle(x, y, wi, ht);

    }

    public boolean intersek(Base obj) {
        return getPerimetro().intersects(obj.getRect());
    }
    
    public void actualiza(long t){
        animacion.actualiza(t);
    }
}
