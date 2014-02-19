/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaframeexamen1;

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

    public boolean intersecta2(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    public boolean intersectaPuntos(int posX, int posY) {
        return getPerimetro().contains(posX, posY);
    }

    public Rectangle getPerimRec() {                             //se hace el rectangulo pequeño 

        return new Rectangle(getPosX() + 10, getPosY() + getAlto(), getAncho() - 20, 1);
    }

    public boolean intersecta(Malo obj) {
        // return getPerimRec().intersects(obj.getPerimetro());
        return getPerimetro().intersects(obj.getPerimetro());
    }

    public void updateS(long t) {
        animacion.actualiza(t);
    }
}
