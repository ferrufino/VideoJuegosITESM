/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaframeexamen1;
/**
 * @(#)Colisiones.java
 *
 * Colisiones Applet application
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/18
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Point;
import javax.swing.JFrame;

public class AppletExamen1 extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip FAIL;    // Objeto AudioClip
    private SoundClip rat;    // Objeto AudioClip
    private SoundClip COLLIDE;    //Objeto AudioClip 
    private Bueno gatoBueno;    // Objeto de la clase Elefante
    private Malo perroMalo;   //Objeto de la clase Raton
    private LinkedList lista;           //lista de perroMaloes
    private int cantidad;               //cantidadidad de perroMalos
    private int mayor;
    private int menor;
    private int POINTSRetard;    //Contador temporalmente para imagen choque
    private boolean ICONPRESSED;
    private int coordenada_x;
    private int coordenada_y;
    private int off_x;
    private int off_y;
    private int VIDAS;
    private Image GAMEOVER;
    private Image BACKGROUND;
     private Image Chocan;
    private int direccion;
    private int posX;
    private int posY;
    private int SCORE;
    private int POINTS;
    private int xMayor;
    private int xMenor;
    private int yMayor;
    private int yMenor;
    private boolean PAUSE; //Permite al usuario pausar el juego
    private boolean ACTION;
    private long tiempoActual;
    private long tiempoInicial;
    private boolean BEGIN;
    private boolean crashed;
    
//constructor
public void AppletExamen1(){

 
        crashed=false;
        BEGIN = true;
        PAUSE = true;
        ACTION = false;
        POINTSRetard = 0;
        direccion = 0;
        
        SCORE = 0;                    //puntaje inicial
        VIDAS = 1;                    //vidas iniciales
        POINTS = 0;                     //POINTSadaor que indica cuantos perroMalos han golpeado el fondo del applet
        xMayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los perroMalos
        xMenor = 0;           //posicion mínima en x que tendrán los perroMalos
        yMayor = -100;          //posicion máxima en y que tendrán los perroMalos
        yMenor = -200;        //posicion mínima en y que tendrán los perroMalos


        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/fail-buzzer-03.wav");
        FAIL = new SoundClip("sounds/fail-buzzer-03.wav");
        URL baURL = this.getClass().getResource("sounds/Choque.wav");
        COLLIDE = new SoundClip("sounds/Choque.wav");
        
        //Se cargan las imagenes
        lista = new LinkedList();

        cantidad = 10;            //cantidad de perroMalos al azar
        while (cantidad != 0) {
            posX = ((int) (Math.random() * (xMayor - xMenor))) + xMenor;     //se generarán los perroMalos en posiciones aleatorias fuera del applet
            posY = ((int) (Math.random() * (yMayor - yMenor))) + yMenor;

            perroMalo = new Malo(posX, posY);
            perroMalo.setPosX(posX);
            perroMalo.setPosY(posY);
            lista.add(perroMalo);
            cantidad--;
        }
        int posX = (getWidth() / 2) - 50;              // posicion inicial del gatoBueno en x
        int posY = getHeight();             // posicion inicial del gatoBueno en y

        gatoBueno = new Bueno(posX, posY);

        URL xuURL = this.getClass().getResource("images/gOVER.png");
        GAMEOVER = Toolkit.getDefaultToolkit().getImage(xuURL);

        URL bgURL = this.getClass().getResource("images/background.jpg");
        BACKGROUND = Toolkit.getDefaultToolkit().getImage(bgURL);
     
        URL cHURL = this.getClass().getResource("images/boom.png");
        Chocan = Toolkit.getDefaultToolkit().getImage(cHURL);

        //Inicializadores 
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
       // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();


}



    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (VIDAS > 0) {
            if (!PAUSE) {
                Actualiza();
                ChecaColision();
            }
            repaint();    // Se Actualiza el <code>Applet</code> repintando el POINTSenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para Actualizar la posicion de objetos elefante y perroMalo.
     *
     */
    public void Actualiza() {

        if (POINTS >= 10) {                                   //cuando la cantidadidad de perroMalos que golpearon el piso sea 10..
            VIDAS--;                                    //las VIDAS decrementarán y la velocidad de los perroMalos aumentará
            POINTS = 0;                                     //la cantidadidad de perroMalos volverá a ser 0
        }

        //Actualiza la animacion creada de los objetos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        gatoBueno.updateS(tiempoTranscurrido);
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);
            perroMalo.updateS(tiempoTranscurrido);
        }

        if (ACTION) {
            switch (direccion) {
                case 3: {

                    gatoBueno.setPosX(gatoBueno.getPosX() - 1);
                    break; //se mueve hacia la izquierda
                }
                case 4: {

                    gatoBueno.setPosX(gatoBueno.getPosX() + 1);
                    break; //se mueve hacia la derecha
                }
            }
        }

        if (ICONPRESSED) {                              //si la imagen sigue presionda, se Actualizan las posiciones de x y de y
            gatoBueno.setPosY(coordenada_y - off_y);
            gatoBueno.setPosX(coordenada_x - off_x);

        }

        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);                         // se hace el cast para cada perroMalo
            if (perroMalo.getPosY() < getHeight()) {
                //si la Y de los perroMalos son menores a la altura del applet
                //perroMalo.setPosY(perroMalo.getPosY() + (int) (Math.random() * 5 + 1));
                perroMalo.setPosY(perroMalo.getPosY() + perroMalo.getSpeed());       //iran bajando 
            }

        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y perroMalo
     * con las orillas del <code>Applet</code>.
     */
    public void ChecaColision() {

        //checa colision con el applet
        if (gatoBueno.getPosY() < 0) {              //si se pasa de del borde de arriba, la imagen se pone al raz
            gatoBueno.setPosY(0);
        }

        if (gatoBueno.getPosY() + gatoBueno.getAlto() > getHeight()) {       //si se pasa del borde de abajo, la imagen se pone al raz
            gatoBueno.setPosY(getHeight() - gatoBueno.getAlto());
        }

        if (gatoBueno.getPosX() < 0) {                             //si se pasa del borde de la izquierda, la imagen se ponse al raz
            gatoBueno.setPosX(0);
        }

        if (gatoBueno.getPosX() + gatoBueno.getAncho() > getWidth()) {      //si se pasa del borde de la derecha, la imagen se pone al raz
            gatoBueno.setPosX(getWidth() - gatoBueno.getAncho());
        }

        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);
           if (perroMalo.getPosY() + perroMalo.getAlto() > getHeight()) {    //perroMalo colisiona abajo del applet
                FAIL.play();
                perroMalo.setPosX(((int) (Math.random() * (xMayor - xMenor))) + xMenor);                                           //se reposiciona en su posicion inicial
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);
                if (SCORE > 0) //si el puntaje es mayor a 0..se quitan 20 puntos
                {
                    SCORE -= 20;
                }
                POINTS++;                                                          //el POINTSador incrementa en 1 cuando topa en el fondo   

            }

        }

        //Colision entre objetos
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);

            if (gatoBueno.intersecta(perroMalo)) {
                //&&dragged up
                crashed=true;
                COLLIDE.play();
                SCORE += 100;
                perroMalo.setPosX(((int) (Math.random() * (xMayor - xMenor))) + xMenor);     // se reposiciona el perroMalo
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);

            }

        }
    }

    /**
     * Metodo <I>Update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el POINTSenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Update la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Update el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen Actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion Actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (VIDAS > 0) {
            if (gatoBueno != null && lista != null) {
                
                   g.drawImage(BACKGROUND, 0, 0, this);
           
                      
                

                if (PAUSE) {
                    

                   
                        g.setColor(Color.black);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 30));
                        g.drawString("Dale click a la pantalla solo una vez", 250, 100);
                        g.drawString("y Oprime 'P' para Comenzar/Pausar el juego!", 250, 150);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 60));
                         g.setColor(Color.white);
                        g.drawString(gatoBueno.getPausado(), 400, 400);
                 
                        
                   

                } else {

                    
                    //Dibuja los string de VIDAS y puntos // X,Y
                    g.setColor(Color.black);
                    g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                    g.drawString("Score: " + SCORE, 850, 20);
                    g.drawString("Vidas: " + VIDAS, 850, 60);

                    //Dibuja la imagen en la posicion Actualizada
                    if(!crashed && (POINTSRetard<20)){
                    g.drawImage(gatoBueno.getImagenI(), gatoBueno.getPosX(), gatoBueno.getPosY(), this);
                    } else {
                        g.drawImage(Chocan, gatoBueno.getPosX(), gatoBueno.getPosY(), this);
                        POINTSRetard++;
                        if(POINTSRetard == 19){
                            crashed=false;
                            POINTSRetard=0;
                        }
                    }
                    g.setColor(Color.white);

                    for (int i = 0; i < lista.size(); i++) {
                        Malo perroMalo = (Malo) lista.get(i);
                        g.drawImage(perroMalo.getImagenI(), perroMalo.getPosX(), perroMalo.getPosY(), this);
                    }

                }

   

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(GAMEOVER, -200, 0, this);
        }
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            direccion = 3; //Presiono flecha abajo
            
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            direccion = 4; //Presiono flecha izquierda

        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            
            PAUSE = !PAUSE;

        }
        ACTION = true;

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

        ACTION = false; //Presiono flecha arriba

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        if (gatoBueno.intersectaPuntos(e.getX(), e.getY()) & !ICONPRESSED) { 
            coordenada_x = e.getX();           
            coordenada_y = e.getY();
            off_x = e.getX() - gatoBueno.getPosX(); 
            off_y = e.getY() - gatoBueno.getPosY(); 
            ICONPRESSED = true;
            //draggedUP=true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        ICONPRESSED = false;    
    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

        if (ICONPRESSED) {   //si la imagen está presionada y la imagen se mueve, se guardan posiciones
            coordenada_x = e.getX();
            coordenada_y = e.getY();
        }
    }

}