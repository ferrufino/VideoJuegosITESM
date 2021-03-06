
/**
 * @Tarea02
 *
 * Basado en el applet "inteligencia" del
 * profesor Antonio Mejorado
 * 
 * @author Gustavo Ferrufino
 *
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class Main extends Applet implements Runnable, MouseListener,
        MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int vidas;    // vidas del planeta.
    private final int MIN = -5;    //Rango minimo al generar un numero al azar.
    private final int MAX = 6;    //Rango maximo al generar un numero al azar.
    private Image dbImage;    // Imagen a proyectar
    private Image gameover;    //Imagen a desplegar al acabar el juego.
    private Image background;    //Imagen a desplegar de fondo de applet
    private Graphics dbg;	// Objeto grafico
    private AudioClip bomb;    //Objeto AudioClip 
    private Planeta earth;    // Objeto de la clase Planeta
    private Asteroide Aarhus;    //Objeto de la clase asteroide
    private boolean planetClicked; //bool si el mouse fue presionado
    private int difPosX;
    private int difPosY;
    private int mousePosX;
    private int mousePosY;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        resize(800, 500);
        planetClicked = false; //Boolean que controla si el mouse apreta el planeta
        vidas = 5;    // Le asignamos un valor inicial a las vidas
        
        int posX = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet
        int posY = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet
        URL eURL = this.getClass().getResource("images/earth.png");
        earth = new Planeta(posX, posY, Toolkit.getDefaultToolkit().getImage(eURL));
        
        int posrX = (int) (Math.random() * (getWidth() / 4)) + getWidth() / 2;    //posision x es tres cuartos del applet
        int posrY = (int) (Math.random() * (getHeight() / 4)) + getHeight() / 2;    //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("images/asteroid.png");
        Aarhus = new Asteroide(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL), 1);
        Aarhus.setPosX(Aarhus.getPosX() - Aarhus.getAncho());
        Aarhus.setPosY(Aarhus.getPosY() - Aarhus.getAlto());
        
        addMouseListener(this);
        addMouseMotionListener(this);
        //Se carga el sonido.
        URL baURL = this.getClass().getResource("sounds/Explosion.wav");
        bomb = getAudioClip(baURL);
        //Se carga las imagenes
        URL goURL = this.getClass().getResource("images/gameover.jpg");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);

        URL bgURL = this.getClass().getResource("images/spaceStar.jpg");
        background = Toolkit.getDefaultToolkit().getImage(bgURL);
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

    /**
     * Metodo usado para actualizar la posicion de objetos planeta y asteroide.
     *
     */
    public void actualiza() {

        //Acutaliza la posicion del asteroide dependiendo donde este el planeta
        if (earth.getPosX() > Aarhus.getPosX()) {

            Aarhus.setPosX(Aarhus.getPosX() + Aarhus.getincrementoVel());
        } else {

            Aarhus.setPosX(Aarhus.getPosX() - Aarhus.getincrementoVel());
        }

        if (earth.getPosY() > Aarhus.getPosY()) {

            Aarhus.setPosY(Aarhus.getPosY() + Aarhus.getincrementoVel());
        } else {

            Aarhus.setPosY(Aarhus.getPosY() - Aarhus.getincrementoVel());
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto planeta y asteroide con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        //Checa colision del planeta earth con el Applet

        if (earth.getPosX() + earth.getAncho() > getWidth()) {
            earth.setPosX(getWidth() - earth.getAncho());
        }
        if (earth.getPosX() < 0) {
            earth.setPosX(0);
        }
        if (earth.getPosY() + earth.getAlto() > getHeight()) {
            earth.setPosY(getHeight() - earth.getAlto());
        }
        if (earth.getPosY() < 0) {
            earth.setPosY(0);
        }

        //checa colision de asteroide Aarhus con el applet
        if (Aarhus.getPosX() + Aarhus.getAncho() > getWidth()) {
            Aarhus.setPosX(Aarhus.getPosX() - Aarhus.getincrementoVel());

        }
        if (Aarhus.getPosX() < 0) {
            Aarhus.setPosX(Aarhus.getPosX() - Aarhus.getincrementoVel());

        }
        if (Aarhus.getPosY() + Aarhus.getAlto() > getHeight()) {
            Aarhus.setPosY(Aarhus.getPosY() - Aarhus.getincrementoVel());

        }
        if (Aarhus.getPosY() < 0) {
            Aarhus.setPosY(Aarhus.getPosY() - Aarhus.getincrementoVel());

        }

        //Colision entre objetos
        if (earth.intersecta(Aarhus)) {
            bomb.play();    //sonido al colisionar
            //El planeta se mueve al azar en la mitad izquierda del applet.
            earth.setPosX((int) (Math.random() * (getWidth() - earth.getAncho())));
            earth.setPosY((int) (Math.random() * (getHeight()/2  - earth.getAlto())));
            //El asteroide se mueve al azar en la mitad derecha del appler.
            Aarhus.setPosX((int) (Math.random() * getWidth() / 2) + getWidth() / 2 - Aarhus.getAncho());
            Aarhus.setPosY((int) (Math.random() * getHeight() / 2) + getHeight() / 2 - Aarhus.getAlto());
            if (vidas > 0) {
                vidas--;
                Aarhus.setincrementoVel(Aarhus.getincrementoVel() + 1);
                planetClicked = false;
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
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(earth.getImagenI(), earth.getPosX(), earth.getPosY(), this);
                g.drawImage(Aarhus.getImagenI(), Aarhus.getPosX(), Aarhus.getPosY(), this);

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawImage(gameover, 0, 0, this);
        }
    }
    
    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al apretar el boton
     * del mouse
     *
     * @param e es el <code>evento</code> que se genera en apretar el boton
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
        if (earth.intersectaPuntos(e.getX(), e.getY())) {
            difPosX = earth.getPosX() - e.getX();
            difPosY = earth.getPosY() - e.getY();
            planetClicked = true;
        }

    }
    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al apretar el boton
     * del mouse y soltarlo
     *
     * @param e es el <code>evento</code> que se genera en apretar el boton
     * y soltar el boton
     */
    public void mouseClicked(MouseEvent e) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    /**
     * Metodo <I>mouseRelease</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar el boton del
     * mouse 
     *
     * @param e es el <code>evento</code> que se genera en soltar el boton
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        planetClicked = false;

    }

     /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera mover el
     * mouse mientras se apreta el boton
     *
     * @param e es el <code>evento</code> que se genera en mover el mouse
     */   
  
    public void mouseDragged(MouseEvent e) {
        if (planetClicked) {
           
                earth.setPosX(earth.getPosX() + (e.getX() - mousePosX));
                earth.setPosY(earth.getPosY() + (e.getY() - mousePosY));
                mousePosX = e.getX();
                mousePosY = e.getY();
          
        }

    }
     /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando
     * el mouse enters
     *
     * @param e es el <code>evento</code> que se genera al enter el mouse
     */ 
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando el mouse enters
     *
     * @param e es el <code>evento</code> que se genera al enter el mouse
     */
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el mouse
     *
     * @param e es el <code>evento</code> que se genera al mover el mouse
     */ 
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
