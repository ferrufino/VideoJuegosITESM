/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Ferrufino
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Rectangle;

public class pruebas extends Applet implements Runnable/*, MouseListener,
        MouseMotionListener */ {
    
     private Image dbImage;    // Imagen a proyectar
public void init() {

}

   public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }
    
      public void run() {
        while (vidas > 0) {
            actualiza();
            checaColision();

            // Se actualiza el <code>Applet</code> repintando el contenido.
            repaint();

            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    public void actualiza() {

        //Acutaliza la posicion del asteroide dependiendo donde este el planeta
        for (int i = 0; i < Asteroides.size(); i++) {
            Asteroide temp = (Asteroide) Asteroides.get(i);
            //Acutalizo la posicion del raton
            temp.setPosY(temp.getPosY() + incrementoVelocidad);

        }

    }
    public void checaColision() {
       


    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void update(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        if (vidas > 0) {
            if (earth != null && Aarhus != null) {

                g.drawImage(background, 0, 0, this);
                //Dibuja los string de vidas y puntos // X,Y
                g.setColor(Color.white);
                g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                g.drawString("Puntos: " + marcador, 950, 20);
                g.drawString("Vidas: " + vidas, 950, 60);
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(earth.getImagenI(), earth.getPosX(), earth.getPosY(), this);
                for (int i = 0; i < Asteroides.size(); i++) {
                    Asteroide temp = (Asteroide) Asteroides.get(i);
                    g.drawImage(temp.getImagenI(), temp.getPosX(), temp.getPosY(), this);

                }
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawImage(gameover, 0, 0, this);
            g.setColor(Color.white);
            g.setFont(new Font("Avenir Black", Font.BOLD, 40));
            if (marcador < 0) {
                marcador = 0;
            }
            g.drawString("Puntos: " + marcador, 400, 650);
        }
    }

    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al apretar el boton del
     * mouse
     *
     * @param e es el <code>evento</code> que se genera en apretar el boton
     */
    public void mousePressed(MouseEvent e) {

        mousePosX = e.getX();
        mousePosY = e.getY();
        if (earth.intersectaPuntos(e.getX(), e.getY())) {
            difPosX = earth.getPosX() - e.getX();
            difPosY = earth.getPosY() - e.getY();
            planetClicked = true;
            draggedUp = true;
        }

    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al apretar el boton del
     * mouse y soltarlo
     *
     * @param e es el <code>evento</code> que se genera en apretar el boton y
     * soltar el boton
     */
    public void mouseClicked(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo <I>mouseRelease</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar el boton del
     * mouse
     *
     * @param e es el <code>evento</code> que se genera en soltar el boton
     */
    public void mouseReleased(MouseEvent e) {
        planetClicked = false;
        draggedUp = false;
    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera mover el mouse mientras se
     * apreta el boton
     *
     * @param e es el <code>evento</code> que se genera en mover el mouse
     */
    public void mouseDragged(MouseEvent e) {
        if (planetClicked) {
            AntiguaPosY = earth.getPosY();
            AntiguaPosX = earth.getPosX();
            earth.setPosX(earth.getPosX() + (e.getX() - mousePosX));
            earth.setPosY(earth.getPosY() + (e.getY() - mousePosY));
            mousePosX = e.getX();
            mousePosY = e.getY();
            if ((AntiguaPosY > mousePosY) && (!(mousePosX > AntiguaPosX + 100) || !(mousePosX < AntiguaPosX - 100))) {
                draggedUp = true;
            } else {
                draggedUp = false;
            }

        }

    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando el mouse enters
     *
     * @param e es el <code>evento</code> que se genera al enter el mouse
     */
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando el mouse enters
     *
     * @param e es el <code>evento</code> que se genera al enter el mouse
     */
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el mouse
     *
     * @param e es el <code>evento</code> que se genera al mover el mouse
     */
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

    
    
}
