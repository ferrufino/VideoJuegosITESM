/*
 *Class AppletExamen1
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package javaframeexamen1;

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
import javax.swing.JFrame;// 

public class AppletExamen1 extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip fail;    // Objeto AudioClip
    private SoundClip collide;    //Objeto AudioClip 
    private Bueno gatoBueno;    // Objeto de la clase Elefante
    private Malo perroMalo;   //Objeto de la clase Raton
   //listas
    private LinkedList listaIzq;           //lista de perroMalos por la Izquierda
    private LinkedList listaDer;           //lista de perrosMalos por la derecha
    
    private int cantidad;               //cantidadidad de perroMalos
    private int timeRetard;    //Contador para retrazar aparicion de DESAPARECE
    private boolean IconPressed;
    private int coordenada_x;
    private int coordenada_y;
    private int off_x;
    private int off_y;
    private int VIDAS;
    //imagenes
    private Image gameover;
    private Image background;
    private Image chocan;
    
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
    private int CUADRANTE;

//constructor
    public AppletExamen1() {

        setSize(1000, 700);
        crashed = false;
        BEGIN = true;
        PAUSE = false;
        ACTION = false;
        timeRetard = 0;
        direccion = 0;

        SCORE = 0;                    //puntaje inicial
        VIDAS = 1;                    //vida inicial
        xMayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los perroMalos
        xMenor = 0;           //posicion mínima en x que tendrán los perroMalos
        yMayor = (getHeight() - (getHeight() / 10));          //posicion máxima en y que tendrán los perroMalos
        yMenor = 10;        //posicion mínima en y que tendrán los perroMalos

        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/fail-buzzer-03.wav");
        fail = new SoundClip("sounds/fail-buzzer-03.wav");
        URL baURL = this.getClass().getResource("sounds/Choque.wav");
        collide = new SoundClip("sounds/Choque.wav");

        //Se cargan la lista de objetos malos Izq
        listaIzq = new LinkedList();

        int[] array1 = new int[]{6, 10, 12};
        cantidad = array1[(int) Math.random() * 3];
        while (cantidad != 0) {
            posX = (-10);     //se generarán los perroMalos en posiciones aleatorias fuera del applet
            posY = ((int) (Math.random() * (yMayor - yMenor))) + yMenor;

            perroMalo = new Malo(posX, posY);
            perroMalo.setPosX(posX);
            perroMalo.setPosY(posY);
            listaIzq.add(perroMalo);
            cantidad--;
        }

        //Se cargan la lista de objetos malos Der
        listaDer = new LinkedList();

        int[] array2 = new int[]{6, 10, 12};
        cantidad = array2[(int) Math.random() * 3];
        while (cantidad != 0) {
            posX = (1100);     //se generarán los perroMalos en posiciones aleatorias fuera del applet
            posY = ((int) (Math.random() * (yMayor - yMenor))) + yMenor;

            perroMalo = new Malo(posX, posY);
            perroMalo.setPosX(posX);
            perroMalo.setPosY(posY);
            listaDer.add(perroMalo);
            cantidad--;
        }
        int posX = (getWidth() / 2) - 50;              // posicion inicial del gatoBueno en x
        int posY = getHeight();             // posicion inicial del gatoBueno en y

        gatoBueno = new Bueno(posX, posY);

        URL xuURL = this.getClass().getResource("images/gOVER.png");
        gameover = Toolkit.getDefaultToolkit().getImage(xuURL);

        URL bgURL = this.getClass().getResource("images/background.jpg");
        background = Toolkit.getDefaultToolkit().getImage(bgURL);

        URL cHURL = this.getClass().getResource("images/boom.png");
        chocan = Toolkit.getDefaultToolkit().getImage(cHURL);

        //Inicializadores 
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        /**
         * 
         */
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
        
        switch (CUADRANTE) {
            case 3: {

                gatoBueno.setPosY(gatoBueno.getPosY() + gatoBueno.getSpeed());
                break; //se mueve hacia arriba
            }
            case 2: {

                gatoBueno.setPosX(gatoBueno.getPosX() + gatoBueno.getSpeed());
                break; //se mueve hacia la derecha
            }
            case 1: {

                gatoBueno.setPosY(gatoBueno.getPosY() - gatoBueno.getSpeed());
                break; //se mueve hacia abajo
            }
            case 4: {

                gatoBueno.setPosX(gatoBueno.getPosX() - gatoBueno.getSpeed());
                break; //se mueve hacia la izquierda
            }
        }

        //Actualiza la animacion creada de los objetos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        gatoBueno.updateS(tiempoTranscurrido);
        for (int i = 0; i < listaIzq.size(); i++) {
            Malo perroMalo = (Malo) listaIzq.get(i);
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

        if (IconPressed) {
            gatoBueno.setPosY(coordenada_y - off_y);
            gatoBueno.setPosX(coordenada_x - off_x);

        }
        //lista de Malos que se mueven de izq a derecha 
        for (int i = 0; i < listaIzq.size(); i++) {
            Malo perroMalo = (Malo) listaIzq.get(i);
            if (perroMalo.getPosY() < getHeight()) {
                perroMalo.setPosX(perroMalo.getPosX() + perroMalo.getSpeed());       
            }

        }
        //lista de Malos que se mueven de der a izq
        for (int i = 0; i < listaDer.size(); i++) {
            Malo perroMalo = (Malo) listaDer.get(i);
            if (perroMalo.getPosY() < getHeight()) {
                perroMalo.setPosX(perroMalo.getPosX() - perroMalo.getSpeed());       
            }

        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y perroMalo
     * con las orillas del <code>Applet</code>.
     */
    public void ChecaColision() {

        //checa colision con el applet
        if (gatoBueno.getPosY() < 0) {              //choca borde de arriba
            gatoBueno.setPosY(0);
        }

        if (gatoBueno.getPosY() + gatoBueno.getAlto() > getHeight()) {       //si se pasa del borde de abajo
            gatoBueno.setPosY(getHeight() - gatoBueno.getAlto());
        }

        if (gatoBueno.getPosX() < 0) {                             //si se pasa del borde de la izquierda
            gatoBueno.setPosX(0);
        }

        if (gatoBueno.getPosX() + gatoBueno.getAncho() > getWidth()) {      //si se pasa del borde de la derecha
            gatoBueno.setPosX(getWidth() - gatoBueno.getAncho());
        }
        //Listas encadenada de malos de Izq a Derecha
        for (int i = 0; i < listaIzq.size(); i++) {
            Malo perroMalo = (Malo) listaIzq.get(i);
            if (perroMalo.getPosX() + perroMalo.getAncho() > getWidth()) {    //perroMalo colisiona a la derecha del applet
                fail.play();
                perroMalo.setPosX(-10);                                           //se reposiciona en su posicion inicial
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);
            }

        }
        //Listas encadenada de malos de Derecha a Izq
        for (int i = 0; i < listaDer.size(); i++) {
            Malo perroMalo = (Malo) listaDer.get(i);
            if (perroMalo.getPosX() + perroMalo.getAncho() < 0) {    //perroMalo colisiona a la derecha del applet
                fail.play();
                perroMalo.setPosX(1100);                                           //se reposiciona en su posicion inicial
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);
            }

        }

        //Colision entre objetos
        //Lista Izq
        for (int i = 0; i < listaIzq.size(); i++) {
            Malo perroMalo = (Malo) listaIzq.get(i);

            if (gatoBueno.intersecta(perroMalo)) {
                //&&dragged up
                crashed = true;
                collide.play();
                SCORE += 100;
                perroMalo.setConteo(perroMalo.getConteo() + 1);
                perroMalo.setPosX(-10);     // se reposiciona el perroMalo
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);

            }

        }
        //Lista Der
        for (int i = 0; i < listaDer.size(); i++) {
            Malo perroMalo = (Malo) listaDer.get(i);

            if (gatoBueno.intersecta(perroMalo)) {

                crashed = true;
                collide.play();
                SCORE += 100;
                perroMalo.setConteo(perroMalo.getConteo() + 1);
                perroMalo.setPosX(1100);     // se reposiciona el perroMalo
                perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);

            }

        }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el Paint
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
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion Actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (VIDAS > 0) {
            if (gatoBueno != null && listaIzq != null) {

                g.drawImage(background, 0, 0, this);

                if (PAUSE) {

                    g.setFont(new Font("Avenir Black", Font.BOLD, 60));
                    g.setColor(Color.white);
                    g.drawString(gatoBueno.getPausado(), 400, 400);

                } else {

                    //Dibuja string Score
                    g.setColor(Color.black);
                    g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                    g.drawString("Score: " + perroMalo.getConteo(), 850, 60);

                    //Dibuja la imagen en la posicion Actualizada
                    if (!crashed && (timeRetard < 20)) {
                        g.drawImage(gatoBueno.getImagenI(), gatoBueno.getPosX(), gatoBueno.getPosY(), this);
                    } else {
                        g.drawString(gatoBueno.getGone(), gatoBueno.getPosX(), gatoBueno.getPosY());
                        timeRetard++;
                        if (timeRetard == 19) {
                            crashed = false;
                            timeRetard = 0;
                        }
                    }
                    g.setColor(Color.white);
                    // Lista de Izq a Der
                    for (int i = 0; i < listaIzq.size(); i++) {
                        Malo perroMalo = (Malo) listaIzq.get(i);
                        g.drawImage(perroMalo.getImagenI(), perroMalo.getPosX(), perroMalo.getPosY(), this);
                    }
                    //Lista de Derecha a Izq
                    for (int i = 0; i < listaDer.size(); i++) {
                        Malo perroMalo = (Malo) listaDer.get(i);
                        g.drawImage(perroMalo.getImagenI(), perroMalo.getPosX(), perroMalo.getPosY(), this);
                    }

                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(gameover, -200, 0, this);
        }
    }
   /*
     *Metodo keyPressed
     *Cuando una tecla esta apretada
     *recibe de param un evento, en este caso se busca que sea la p
     *para pausar el juego
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {

            PAUSE = !PAUSE;

        }
        ACTION = true;

    }

    public void keyTyped(KeyEvent e) { //metodo cuando una tecla fue typeada
    }

    public void keyReleased(KeyEvent e) {

        ACTION = false; //Presiono flecha arriba

    }
    /*
     *Metodo mouseClicked
     *Cuando el mouse es apretado
     *recibe de param un evento, que ayudara a definir donde fue picado
     *dentro del applet
     */
    public void mouseClicked(MouseEvent e) {

        if ((e.getX() < getWidth() / 2) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 4; // El mouse fue presionado

        } else if ((e.getX() >= (getWidth() / 2)) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 1; // El mouse fue presionado

        } else if ((e.getX() < getWidth() / 2) && (e.getY() >= getHeight() / 2)) {

            CUADRANTE = 3; // El mouse fue presionado

        } else {

            CUADRANTE = 2; // El mouse fue presionado

        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        if (gatoBueno.intersectaPuntos(e.getX(), e.getY()) & !IconPressed) {
            coordenada_x = e.getX();
            coordenada_y = e.getY();
            off_x = e.getX() - gatoBueno.getPosX();
            off_y = e.getY() - gatoBueno.getPosY();
            IconPressed = true;
            //draggedUP=true;
        }
    }

    public void mouseReleased(MouseEvent e) {//metodo cuando el mouse es soltado
        IconPressed = false;
    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

        if (IconPressed) {   //si la imagen está presionada y la imagen se mueve, se guardan posiciones
            coordenada_x = e.getX();
            coordenada_y = e.getY();
        }
    }

}
