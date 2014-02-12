
/**
 * @(#)Examen1
 *
 * AppletExamen1
 *
 * @author Gustavo Ferrufino
 * @matricula A00812572
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

public class AppletExamen1 extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private AudioClip FAILAUDIO;    // Objeto AudioClip
    private AudioClip COLLIDE;    //Objeto AudioClip 
    private Bueno gatoBueno;    // Objeto de la clase Elefante
    private Malo perroMalo;   //Objeto de la clase Raton
    private LinkedList lista;           //lista de perroMaloes
    private int cantidad;               //cantidadidad de perroMalos
    private int contRetard;    //Contador temporalmente para imagen choque
    private boolean ICONPRESSED;
    private int crdndax; //coordenada en x
    private int crdnday; //coordenada en y
    private int offsetx;
    private int offsety;
    private int VIDAS;
    private Image GAMEOVER;
    private Image BACKGROUND;
    private Image Chocan;
    private int direccion;
    private int posX;
    private int posY;
    private int SCORE;
    private int FAIL;
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

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {

        resize(1000, 717);
        crashed = false;
        BEGIN = true;
        PAUSE = false;
        ACTION = false;
        contRetard = 0;
        direccion = 0;

        SCORE = 0;    //Puntaje inicial
        VIDAS = 5;    //Vidas
        FAIL = 0;    //indica cuantos perroMalos han golpeado el fondo del applet
        xMayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los perroMalos
        xMenor = 0;           //posicion mínima en x que tendrán los perroMalos
        yMayor = -100;          //posicion máxima en y que tendrán los perroMalos
        yMenor = -200;        //posicion mínima en y que tendrán los perroMalos

        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/fail-buzzer-03.wav");
        FAILAUDIO = getAudioClip(beURL);
        URL baURL = this.getClass().getResource("sounds/Choque.wav");
        COLLIDE = getAudioClip(baURL);

        //Se cargan las imagenes
        lista = new LinkedList();

        cantidad = 6;            //cantidad de perroMalos al azar
        while (cantidad != 0) {
            posX = ((int) (Math.random() * (xMayor - xMenor))) + xMenor * (cantidad + 1);
            posY = ((int) (Math.random() * (yMayor - yMenor))) + yMenor;

            if (cantidad > cantidad / 2) {

                perroMalo = new Malo(posX, posY);
                perroMalo.setPosX(posX);
                perroMalo.setPosY(posY);
            } else {

                perroMalo.setPosX(posX);
                perroMalo.setPosY(posY);

            }

            lista.add(perroMalo);
            cantidad--;
        }
        int posX = (getWidth() / 2) - 90;              // posicion inicial del gatoBueno en x
        int posY = getHeight() / 2;             // posicion inicial del gatoBueno en y

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
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
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
            repaint();    // Se Actualiza el <code>Applet</code> repintando el FAILenido.
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

        //Actualiza la animacion creada de los objetos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        gatoBueno.updateS(tiempoTranscurrido);
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);
            perroMalo.updateS(tiempoTranscurrido);
        }

        switch (CUADRANTE) {
            case 1: {

                gatoBueno.setPosY(gatoBueno.getPosY() + gatoBueno.getSpeed());
                break; //se mueve hacia arriba
            }
            case 2: {

                gatoBueno.setPosX(gatoBueno.getPosX() + gatoBueno.getSpeed());
                break; //se mueve hacia la derecha
            }
            case 3: {

                gatoBueno.setPosY(gatoBueno.getPosY() - gatoBueno.getSpeed());
                break; //se mueve hacia abajo
            }
            case 4: {

                gatoBueno.setPosX(gatoBueno.getPosX() - gatoBueno.getSpeed());
                break; //se mueve hacia la izquierda
            }
        }

        if (ICONPRESSED) {                              //si la imagen sigue presionda, se Actualizan las posiciones de x y de y
            gatoBueno.setPosY(crdnday - offsety);
            gatoBueno.setPosX(crdndax - offsetx);

        }

        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);  // 
            if (perroMalo.getPosY() < getHeight()) {

                perroMalo.setPosY(perroMalo.getPosY() + perroMalo.getSpeed());
            }

        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y perroMalo
     * con las orillas del <code>Applet</code>.
     */
    public void ChecaColision() {

        //checa colision con el applet
        if (gatoBueno.getPosY() < 0) {
            gatoBueno.setPosY(0);
        }

        if (gatoBueno.getPosY() + gatoBueno.getAlto() > getHeight()) {
            gatoBueno.setPosY(getHeight() - gatoBueno.getAlto());
        }

        if (gatoBueno.getPosX() < 0) {
            gatoBueno.setPosX(0);
        }

        if (gatoBueno.getPosX() + gatoBueno.getAncho() > getWidth()) {
            gatoBueno.setPosX(getWidth() - gatoBueno.getAncho());
        }

        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);
            if (perroMalo.getPosY() + perroMalo.getAlto() > getHeight()) {    //perroMalo colisiona abajo del applet
                FAILAUDIO.play();
                if (i < 3) {

                    perroMalo.setPosX(((int) (Math.random() * (xMayor / 2 - xMenor))) + xMenor);     // se reposiciona el perroMalo
                    perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMayor);
                } else {

                    perroMalo.setPosX(((int) (Math.random() * (xMayor - xMenor))) + xMenor);     // se reposiciona el perroMalo
                    perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMayor);

                }

                FAIL++;     //el FAIL incrementa en 1 cuando topa en el fondo   

            }

        }

        //Colision entre objetos
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);

            if (gatoBueno.intersecta(perroMalo)) {
                //&&dragged up
                crashed = true;
                COLLIDE.play();
                if (i < 3) {
                    perroMalo.setConteo(perroMalo.getConteo() + 1);
                    perroMalo.setPosX(((int) (Math.random() * (xMayor / 2 - xMenor))) + xMenor);     // se reposiciona el perroMalo
                    perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMayor);
                } else {
                    perroMalo.setConteo(perroMalo.getConteo() + 1);
                    perroMalo.setPosX(((int) (Math.random() * (xMayor - xMenor))) + xMenor);     // se reposiciona el perroMalo
                    perroMalo.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMayor);

                }

            }

        }
    }

    /**
     * Metodo <I>Update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el FAIL
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void Update(Graphics g) {
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
        paint(dbg);

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
    public void paint(Graphics g) {

        if (gatoBueno != null && lista != null) {

            g.drawImage(BACKGROUND, 0, 0, this);

            if (PAUSE) {

                g.setFont(new Font("Avenir Black", Font.BOLD, 60));
                g.setColor(Color.white);
                g.drawString(gatoBueno.getPausado(), 400, 400);

            } else {

                //Dibuja los string de VIDAS y puntos // X,Y
                g.setColor(Color.black);
                g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                g.drawString("Score: " + perroMalo.getConteo(), 850, 20);

                //Dibuja la imagen en la posicion Actualizada
                if (!crashed && (contRetard < 20)) {
                    g.drawImage(gatoBueno.getImagenI(), gatoBueno.getPosX(), gatoBueno.getPosY(), this);
                } else {
                    g.setColor(Color.red);
                    g.drawString(gatoBueno.getGone(), gatoBueno.getPosX(), gatoBueno.getPosY());
                    contRetard++;
                    if (contRetard == 19) {
                        crashed = false;
                        contRetard = 0;
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

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

        ACTION = false; //Presiono flecha arriba

    }

    public void mouseClicked(MouseEvent e) { //Mouse fue presionado

        if ((e.getX() < getWidth() / 2) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 4;

        } else if ((e.getX() >= (getWidth() / 2)) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 1;

        } else if ((e.getX() < getWidth() / 2) && (e.getY() >= getHeight() / 2)) {

            CUADRANTE = 3;

        } else {

            CUADRANTE = 2;

        }

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        // ICONPRESSED = false;
    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

        if (ICONPRESSED) {   
            crdndax = e.getX();
            crdnday = e.getY();
        }
    }

}
