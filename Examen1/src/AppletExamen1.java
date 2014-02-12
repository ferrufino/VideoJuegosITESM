
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
    private AudioClip sonido;    // Objeto AudioClip
    private AudioClip rat;    // Objeto AudioClip
    private AudioClip bomb;    //Objeto AudioClip 
    private Bueno planeta;    // Objeto de la clase Elefante
    private Malo raton;   //Objeto de la clase Raton
    private LinkedList lista;           //lista de ratones
    private int cant;               //cantidad de asteroides
    private int mayor;
    private int menor;
    private boolean presionado;
    private int coordenada_x;
    private int coordenada_y;
    private int off_x;
    private int off_y;
    private int vidas;
    private Image game_over;
    private int direccion;
    private int posrX;
    private int posrY;
    private int score;
    private int cont;
    private int x_mayor;
    private int x_menor;
    private int y_mayor;
    private int y_menor;
    private boolean flag;
    private boolean move;
    private boolean pausa;
    private long tiempoActual;
    private long tiempoInicial;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        pausa = false;
        move = false;
        this.setSize(800, 500);
        direccion = 0;
        menor = 0;                    //cantidad minima de asteroides que se generarán al azar
        mayor = 10;                    //cantidad máxima de asteroides que se generarán al azar
        score = 0;                    //puntaje inicial
        vidas = 5;                    //vidaas iniciales
        cont = 0;                     //contadaor que indica cuantos asteroides han golpeado el fondo del applet
        x_mayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los asteroides
        x_menor = 0;           //posicion mínima en x que tendrán los asteroides
        y_mayor = -100;          //posicion máxima en y que tendrán los asteroides
        y_menor = -200;        //posicion mínima en y que tendrán los asteroides
        flag = false;
        int posX = getWidth() / 2;              // posicion inicial del planeta en x
        int posY = getHeight();             // posicion inicial del planeta en y
        URL eURL = this.getClass().getResource("Images/frame_0_00.gif");
        planeta = new Bueno(posX, posY);

        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //Se cargan los sonidos.

        URL beURL = this.getClass().getResource("sounds/boom.wav");
        sonido = getAudioClip(beURL);
        URL baURL = this.getClass().getResource("sounds/Explosion.wav");
        bomb = getAudioClip(baURL);

        lista = new LinkedList();

        cant = 10;            //se crea la cantidad de asteroides al azar
        while (cant != 0) {
            posrX = ((int) (Math.random() * (x_mayor - x_menor))) + x_menor;     //se generarán los asteroides en posiciones aleatorias fuera del applet
            posrY = ((int) (Math.random() * (y_mayor - y_menor))) + y_menor;
            
            raton = new Malo(posrX, posrY);
            raton.setPosX(posrX);
            raton.setPosY(posrY);
            lista.add(raton);
            cant--;
        }
        URL goURL = this.getClass().getResource("images/game_over.gif");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);

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
        while (vidas > 0) {
            if (!pausa) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void actualiza() {

        if (cont >= 10) {                                   //cuando la cantidad de asteroides que golpearon el piso sea 10..
            vidas--;                                    //las vidas decrementarán y la velocidad de los asteroides aumentará
            cont = 0;                                     //la cantidad de asteroides volverá a ser 0
        }
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        planeta.actualiza(tiempoTranscurrido);
        raton.actualiza(tiempoTranscurrido);
        
        if (move) {
            switch (direccion) {
                case 3: {

                    planeta.setPosX(planeta.getPosX() - 1);
                    break; //se mueve hacia la izquierda
                }
                case 4: {

                    planeta.setPosX(planeta.getPosX() + 1);
                    break; //se mueve hacia la derecha
                }
            }
        }

        if (presionado) {                              //si la imagen sigue presionda, se actualizan las posiciones de x y de y
            planeta.setPosY(coordenada_y - off_y);
            planeta.setPosX(coordenada_x - off_x);

        }

        for (int i = 0; i < lista.size(); i++) {
            Malo asteroide = (Malo) lista.get(i);                         // se hace el cast para cada asteroide
            if (asteroide.getPosY() < getHeight()) {
                //si la Y de los asteroides son menores a la altura del applet
                //asteroide.setPosY(asteroide.getPosY() + (int) (Math.random() * 5 + 1));
                asteroide.setPosY(asteroide.getPosY() + asteroide.getSpeed());       //iran bajando 
            }

        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {

        //checa colision con el applet
        if (planeta.getPosY() < 0) {              //si se pasa de del borde de arriba, la imagen se pone al raz
            planeta.setPosY(0);
        }

        if (planeta.getPosY() + planeta.getAlto() > getHeight()) {       //si se pasa del borde de abajo, la imagen se pone al raz
            planeta.setPosY(getHeight() - planeta.getAlto());
        }

        if (planeta.getPosX() < 0) {                             //si se pasa del borde de la izquierda, la imagen se ponse al raz
            planeta.setPosX(0);
        }

        if (planeta.getPosX() + planeta.getAncho() > getWidth()) {      //si se pasa del borde de la derecha, la imagen se pone al raz
            planeta.setPosX(getWidth() - planeta.getAncho());
        }

        for (int i = 0; i < lista.size(); i++) {
            Malo asteroide = (Malo) lista.get(i);
            if (asteroide.getPosX() < 0) {                                          //cuando el asteroide llega al lado izquierdo del applet...
                sonido.play();
                asteroide.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);                                            //se reposiciona en su posicion inicial
                asteroide.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);
                if (score > 0) {                                                 //si el puntaje es mayor a 0..se quitan 20 puntos
                    score -= 20;
                }
                cont++;                                                         //el contador incrementa en 1 cuando topa en el fondo 

            } else if (asteroide.getPosY() + asteroide.getAlto() > getHeight()) {              //cuando el asteroide llega al lado de abajo del applet...
                sonido.play();
                asteroide.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);                                           //se reposiciona en su posicion inicial
                asteroide.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);
                if (score > 0) //si el puntaje es mayor a 0..se quitan 20 puntos
                {
                    score -= 20;
                }
                cont++;                                                          //el contador incrementa en 1 cuando topa en el fondo   

            }

        }

        //Colision entre objetos
        for (int i = 0; i < lista.size(); i++) {
            Malo asteroide = (Malo) lista.get(i);

            if (planeta.intersecta(asteroide) && !(planeta.intersek(asteroide))) {          //si intersectó con el asteroide pero no al rectangulo chico
                flag = true;                                            //entró por arriba

            } else if (!(planeta.intersecta(asteroide)) && flag) {     //si dejo de intersectar y la la booleana que checa si entro por arriba esta prendida
                flag = false;                                               //laa bandera se apaga

            } else if (!(flag) && planeta.intersek(asteroide)) {         // no entró por arriba (o por los lados) e intersecto con el rectangulo chiquito, entró por donde debía de entrar
                bomb.play();
                score += 100;
                asteroide.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);     // se reposiciona el asteroide
                asteroide.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);

            }

        }
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

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            direccion = 3;
            //Presiono flecha abajo
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            direccion = 4;
            //Presiono flecha izquierda

        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;

        }
        move = true;

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        //Presiono flecha arriba
        move = false;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        if (planeta.contiene(e.getX(), e.getY()) & !presionado) { //si hice click dentro del rectangulo y no esta presionado
            coordenada_x = e.getX();            //se procede a guardar coordenadas
            coordenada_y = e.getY();
            off_x = e.getX() - planeta.getPosX(); //se calcula la diferencia para el desface
            off_y = e.getY() - planeta.getPosY(); //se calcula la diferencia para el desface
            presionado = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        presionado = false;                 //cuando se deja de picar la bandera se apaga
    }

    public void mouseMoved(MouseEvent e) {                   //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {                //metodos de MouseMotionListener

        if (presionado) {                       //si la imagen está presionada y la imagen se mueve, se guardan posiciones
            coordenada_x = e.getX();
            coordenada_y = e.getY();
        }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        if (vidas > 0) {
            if (planeta != null && lista != null) {
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(planeta.getImagenI(), planeta.getPosX(), planeta.getPosY(), this);
                g.setColor(Color.white);
                g.drawString("Score = " + score, 20, 20);
                g.drawString("Vidas = " + vidas, 20, 50);
                if (pausa) {
                    g.setColor(Color.black);
                    g.drawString(planeta.getPausado(), planeta.getPosX() + planeta.getAncho() / 3, planeta.getPosY() + planeta.getAlto() / 2);
                }

                for (int i = 0; i < lista.size(); i++) {
                    Malo asteroide = (Malo) lista.get(i);
                    g.drawImage(asteroide.getImagenI(), asteroide.getPosX(), asteroide.getPosY(), this);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(game_over, -100, -30, this);
        }
    }

}
