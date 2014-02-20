/*
 *Class Base
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
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

    /*
     *Clase setPosX
     *Sirve para definir la posX
     * recibe de parametro un entero
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /*
     *Clase getPosx
     *regresa la posicion en X
     * 
     */
    public int getPosX() {
        return posX;
    }
    
    /*
     *Clase setPosY
     *Sirve para definir la posicion en Y
     * recibe de parametro un entero
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    /*
     *Clase getPosY
     *regresa la posicion en Y
     * 
     */
    public int getPosY() {
        return posY;
    }

    /*
     *Clase getAncho
     *regresa lo ancho de un objeto ImageIcon
     *en este caso de la animacion creada. Malo/Bueno
     */
    public int getAncho() {

        return (new ImageIcon(animacion.getImagen()).getIconWidth());
    }
    
    /*
     *Clase getAlto
     *regresa la altura de un objeto ImageIcon
     *en este caso de la animacion creada. Malo/Bueno
     */
    public int getAlto() {

        return (new ImageIcon(animacion.getImagen()).getIconHeight());
    }

    /*
     *Clase getImagenI
     *regresa un objeto ImageIcon
     *de la animacion creada. Malo/Bueno
     */
    public Image getImagenI() {
        return (new ImageIcon(animacion.getImagen()).getImage());
    }

    /*
     *Clase getPerimetro
     *regresa el perimetro de un objeto formado por 
     *la altura, ancho, posX, posY
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }
    
    /*
     *Clase instersecta2
     *recibe un objeto tipo base 
     *regresa un boleano si intersecta dentro de un permetro definido
     */
    public boolean intersecta2(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /*
     *Clase instersectaPuntos
     *recibe dos enteros
     *regresa un boleano si intersecta dentro la coordenadas dadas como dato
     *de entrada
     */
    public boolean intersectaPuntos(int posX, int posY) {
        return getPerimetro().contains(posX, posY);
    }

    /*
     *Clase intersecta
     *recibe onjeto malo
     *Metodo que sirve para saber si un objeto malo
     *Intersecta con un perimetro
     */
    public boolean intersecta(Malo obj) {
        // return getPerimRec().intersects(obj.getPerimetro());
        return getPerimetro().intersects(obj.getPerimetro());
    }
    /*
     *Clase updates
     *recibe un long
     *Actualiza la animacion
     */
    public void updateS(long t) {
        animacion.actualiza(t);
    }
}
