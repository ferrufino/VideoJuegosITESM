
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
    private Bueno gatoBueno;    // Objeto de la clase Elefante
    private Malo perroMalo;   //Objeto de la clase Raton
    private LinkedList lista;           //lista de perroMaloes
    private int cant;               //cantidad de perroMalos
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
    private boolean PAUSE; //Permite al usuario pausar el juego
    private boolean flag;
    private boolean move;
    private long tiempoActual;
    private long tiempoInicial;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        
        resize(1000, 800);

        PAUSE = true;
        move = false;

        direccion = 0;
        menor = 0;                    //cantidad minima de perroMalos que se generarán al azar
        mayor = 3;                    //cantidad máxima de perroMalos que se generarán al azar
        score = 0;                    //puntaje inicial
        vidas = 5;                    //vidaas iniciales
        cont = 0;                     //contadaor que indica cuantos perroMalos han golpeado el fondo del applet
        x_mayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los perroMalos
        x_menor = 0;           //posicion mínima en x que tendrán los perroMalos
        y_mayor = -100;          //posicion máxima en y que tendrán los perroMalos
        y_menor = -200;        //posicion mínima en y que tendrán los perroMalos
        flag = false;

        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/boom.wav");
        sonido = getAudioClip(beURL);
        URL baURL = this.getClass().getResource("sounds/Explosion.wav");
        bomb = getAudioClip(baURL);

        lista = new LinkedList();

        cant = 3;            //se crea la cantidad de perroMalos al azar
        while (cant != 0) {
            posrX = ((int) (Math.random() * (x_mayor - x_menor))) + x_menor;     //se generarán los perroMalos en posiciones aleatorias fuera del applet
            posrY = ((int) (Math.random() * (y_mayor - y_menor))) + y_menor;

            perroMalo = new Malo(posrX, posrY);
            perroMalo.setPosX(posrX);
            perroMalo.setPosY(posrY);
            lista.add(perroMalo);
            cant--;
        }
        int posX = (getWidth() / 2) - 50;              // posicion inicial del gatoBueno en x
        int posY = getHeight();             // posicion inicial del gatoBueno en y

        gatoBueno = new Bueno(posX, posY);

        URL goURL = this.getClass().getResource("images/game_over.gif");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);

        setBackground(Color.black);

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
        while (vidas > 0) {
            if (!PAUSE) {
                Actualiza();
                ChecaColision();
            }
            repaint();    // Se Actualiza el <code>Applet</code> repintando el contenido.
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

        if (cont >= 10) {                                   //cuando la cantidad de perroMalos que golpearon el piso sea 10..
            vidas--;                                    //las vidas decrementarán y la velocidad de los perroMalos aumentará
            cont = 0;                                     //la cantidad de perroMalos volverá a ser 0
        }

        //Actualiza la animacion creada de los objetos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        gatoBueno.updateS(tiempoTranscurrido);
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);
            perroMalo.updateS(tiempoTranscurrido);
        }

        if (move) {
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

        if (presionado) {                              //si la imagen sigue presionda, se Actualizan las posiciones de x y de y
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
            if (perroMalo.getPosX() < 0) {                                          //cuando el perroMalo llega al lado izquierdo del applet...
                sonido.play();
                perroMalo.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);                                            //se reposiciona en su posicion inicial
                perroMalo.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);
                if (score > 0) {                                                 //si el puntaje es mayor a 0..se quitan 20 puntos
                    score -= 20;
                }
                cont++;                                                         //el contador incrementa en 1 cuando topa en el fondo 

            } else if (perroMalo.getPosY() + perroMalo.getAlto() > getHeight()) {              //cuando el perroMalo llega al lado de abajo del applet...
                sonido.play();
                perroMalo.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);                                           //se reposiciona en su posicion inicial
                perroMalo.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);
                if (score > 0) //si el puntaje es mayor a 0..se quitan 20 puntos
                {
                    score -= 20;
                }
                cont++;                                                          //el contador incrementa en 1 cuando topa en el fondo   

            }

        }

        //Colision entre objetos
        for (int i = 0; i < lista.size(); i++) {
            Malo perroMalo = (Malo) lista.get(i);

            if (gatoBueno.intersecta(perroMalo) && !(gatoBueno.intersek(perroMalo))) {          //si intersectó con el perroMalo pero no al rectangulo chico
                flag = true;                                            //entró por arriba

            } else if (!(gatoBueno.intersecta(perroMalo)) && flag) {     //si dejo de intersectar y la la booleana que checa si entro por arriba esta prendida
                flag = false;                                               //laa bandera se apaga

            } else if (!(flag) && gatoBueno.intersek(perroMalo)) {         // no entró por arriba (o por los lados) e intersecto con el rectangulo chiquito, entró por donde debía de entrar
                bomb.play();
                score += 100;
                perroMalo.setPosX(((int) (Math.random() * (x_mayor - x_menor))) + x_menor);     // se reposiciona el perroMalo
                perroMalo.setPosY(((int) (Math.random() * (y_mayor - y_menor))) + y_menor);

            }

        }
    }

    /**
     * Metodo <I>Update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el contenedor
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
        if (vidas > 0) {
            if (gatoBueno != null && lista != null) {
                //Dibuja la imagen en la posicion Actualizada
                g.drawImage(gatoBueno.getImagenI(), gatoBueno.getPosX(), gatoBueno.getPosY(), this);
                g.setColor(Color.white);
                //X Y
                g.drawString("Vidas = " + vidas, 400, 50);
                g.drawString("Score = " + score, 400, 20);

                if (PAUSE) {
                    g.setColor(Color.black);
                    g.drawString(gatoBueno.getPausado(), gatoBueno.getPosX() + gatoBueno.getAncho() / 3, gatoBueno.getPosY() + gatoBueno.getAlto() / 2);
                }

                for (int i = 0; i < lista.size(); i++) {
                    Malo perroMalo = (Malo) lista.get(i);
                    g.drawImage(perroMalo.getImagenI(), perroMalo.getPosX(), perroMalo.getPosY(), this);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(game_over, -100, -30, this);
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
        move = true;

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

        move = false; //Presiono flecha arriba

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        if (gatoBueno.contiene(e.getX(), e.getY()) & !presionado) { //si hice click dentro del rectangulo y no esta presionado
            coordenada_x = e.getX();            //se procede a guardar coordenadas
            coordenada_y = e.getY();
            off_x = e.getX() - gatoBueno.getPosX(); //se calcula la diferencia para el desface
            off_y = e.getY() - gatoBueno.getPosY(); //se calcula la diferencia para el desface
            presionado = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        presionado = false;    //cuando se deja de picar la bandera se apaga
    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

        if (presionado) {   //si la imagen está presionada y la imagen se mueve, se guardan posiciones
            coordenada_x = e.getX();
            coordenada_y = e.getY();
        }
    }

}
