/**
 *	Applet Colisiona
 *	Colision de un Animal con incremento de velocidad
 *
 *	Tutorial Dibujando en un Applet - Tarea 01
 *
 *	@autor Gustavo Ferrufino
 *      @matricula A00812572
 *	@version 1.0 22/01/2014
 */
 
 
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;

public class Colisiona extends Applet implements Runnable, KeyListener
{
	// Se declaran las variables.
	
        private int x_pos;    // Posicion predeterminada en x del tiger
	private int y_pos;    // Posicion predeterminada en y del tiger
	private int direccion;    // Direccion del elefante
        private boolean crashed;    //Bool que afirma si choco el tigre                  
        private int contRetard;    //Contador temporalmente para imagen choque
	private Image dbImage;	// Imagen a proyectar	
	private Graphics dbg;	// Objeto grafico
	private AudioClip sonido;     // Objeto AudioClip
	private Tigre tiger;    // Objeto de la clase Tigre
        private ImageIcon choque;// Imagen de Choque
    /** 
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public void init() {
                
        resize(500,500);    //Define nuevo tamaño del Applet 
        direccion = 4; //direccion inicial del elefante
        crashed = false;
        contRetard = 0;
	x_pos=(int) (Math.random() *(getWidth() / 4));    // posicion en x es un cuarto del applet;
	y_pos=(int) (Math.random() *(getHeight() / 4));    // posicion en y es un cuarto del applet
		
        URL eURL = this.getClass().getResource("images/tiger.gif");
	tiger = new Tigre(x_pos,y_pos,1,Toolkit.getDefaultToolkit().getImage(eURL));
                
        URL eURL2 = this.getClass().getResource("images/boom.png");
        choque = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL2));
                
        URL eaURL = this.getClass().getResource("sound/roar.wav");
	sonido = getAudioClip (eaURL);
	setBackground (Color.green);
	addKeyListener(this);
		
    }
	
    /** 
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
		
        // Declaras un hilo	
        Thread th = new Thread(this);
        // Empieza el hilo
	th.start();
        
    }
		
    /** 
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y dependiendo de la direccion, finalmente 
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     * 
     */
    public void run () {
		
        while (true) {
                      
            actualiza();
            checaColision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try	{
                // El thread se duerme.
                Thread.sleep (20);
                     
            }
            catch (InterruptedException ex) {
                
                System.out.println("Error en " + ex.toString());
            }
	}
    }
	
    /** 
     * Metodo que actualiza la posicion del objeto elefante 
     */
    public void actualiza() {
	    //Dependiendo de la direccion del elefante es hacia donde se mueve.
	switch(direccion) {
            
            case 1: {               
                tiger.setPosY(tiger.getPosY()-tiger.getNumVelocity()); 
		break;    //se mueve hacia arriba
            }
            case 2: {
		tiger.setPosY(tiger.getPosY()+tiger.getNumVelocity());
		break;    //se mueve hacia abajo
            }
            case 3: {
      		tiger.setPosX(tiger.getPosX()-tiger.getNumVelocity());
		break;    //se mueve hacia izquierda
            }
            case 4: {
      		tiger.setPosX(tiger.getPosX()+tiger.getNumVelocity()); 
		break;    //se mueve hacia derecha	
            }
	}
    }

    /**
     * Metodo que checa la colision del objeto elefante al colisionar con 
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        
        switch(direccion) {
            
            case 1: { //Revisa colision cuando sube
			
                if (tiger.getPosY() <= 0) {
                    
                    sonido.stop();
                    sonido.play();  //Inicializa el sonido
                    direccion = 2;  //Cambia a direccion opuesta
                    crashed =true;
		}
		break;    		
            }     
            case 2: { //Revisa colision cuando baja
		
                if (tiger.getPosY() + tiger.getAlto() >= getHeight()) {
                    
                    sonido.stop();
                    sonido.play();  //Inicializa el sonido
                    direccion = 1;  //Cambia a direccion opuesta
                    crashed = true;
		}
		break;    		
            } 
            case 3: { //Revisa colision cuando va izquierda.
		
                if (tiger.getPosX() <= 0) {                    
                    
                    sonido.stop();      
                    sonido.play();  //Inicializa el sonido
                    direccion = 4;  //Cambia a direccion opuesta
                    crashed = true;		
                }
		break;    			
            }    
            case 4: { //Revisa colision cuando va derecha.
		
                if (tiger.getPosX() + tiger.getAncho() >= getWidth()) {
                    
                    sonido.stop();
                    sonido.play();  //Inicializa el sonido
                    direccion = 3;  //Cambia a direccion opuesta
                    crashed = true;	
                }
		break;    	
            }			
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void update(Graphics g) {
        
	// Inicializan el DoubleBuffer	
        if (dbImage == null) {
                    	
            dbImage = createImage (this.getSize().width, this.getSize().height);        
            dbg = dbImage.getGraphics ();	
        }
                
        // Actualiza la imagen de fondo.
	dbg.setColor(getBackground());
	dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

	// Actualiza el Foreground.
	dbg.setColor(getForeground());
	if ((crashed) && (contRetard<=30)) {
            
            paintCrash(dbg);
            contRetard++;
             
        } else {
    
            contRetard=20;
            crashed=false;
            paint(dbg);
                
        }
	// Dibuja la imagen actualizada
	g.drawImage(dbImage, 0, 0, this);
    }
	
    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
		
        if (e.getKeyCode() == KeyEvent.VK_UP) {    //Presiono flecha arriba
	        
            if (direccion == 1) {
               //Aumenta velocidad 
                tiger.setNumVelocity((tiger.getNumVelocity())+1);
                    
            } else if( (direccion == 3) || (direccion == 4)) {
                //Resetea la velocidad al ser una direccion nueva        
                tiger.setNumVelocity(1);
                direccion = 1;
                    
            } else if(tiger.getNumVelocity() > 1) {
                 //Decrementa velocidad
                tiger.setNumVelocity(tiger.getNumVelocity()-1);
                    
            } else {
                 //Resetea la velocidad al ser una direccion nueva
                 direccion = 1;
                 tiger.setNumVelocity(1);
                    
            }
                    
	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
                    
                    
            if (direccion == 2) {
            
                //Aumenta velocidad 
                tiger.setNumVelocity((tiger.getNumVelocity())+1);
                
            } else if( (direccion == 3) || (direccion == 4)) {
                
                //Resetea la velocidad al ser una direccion nueva
                tiger.setNumVelocity(1);
                direccion = 2;
                        
            } else if(tiger.getNumVelocity() > 1) {
                
                //Resetea la velocidad al ser una direccion nueva           
                tiger.setNumVelocity((tiger.getNumVelocity())-1);
            
            } else {
            
                //Decrementa velocidad
                direccion = 2;
                tiger.setNumVelocity(1);
            
            }
               //Presiono flecha izquierda     
	} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    
        
            if (direccion == 3) { //Aumento Velocidad
                //Aumenta velocidad 
                tiger.setNumVelocity((tiger.getNumVelocity())+1);
                
            } else if( (direccion == 1) || (direccion == 2)) {
            
                //Resetea la velocidad al ser una direccion nueva
                tiger.setNumVelocity(1);
                direccion = 3;
                
            } else if(tiger.getNumVelocity() > 1) {
                //Decrementa velocidad
                tiger.setNumVelocity((tiger.getNumVelocity())-1);
            
            } else {
            //Resetea la velocidad al ser una direccion nueva
            direccion = 3;
            tiger.setNumVelocity(1);   
            
            } 
               //Presiono flecha derecha
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    
                    
            if (direccion == 4) {
               //Aumenta velocidad
                tiger.setNumVelocity((tiger.getNumVelocity())+1);
           
            } else if( (direccion == 1) || (direccion == 2)) {
                //Resetea la velocidad al ser una direccion nueva
                tiger.setNumVelocity(1);
                direccion = 4;
                    
            } else if(tiger.getNumVelocity() > 1) {
                //Decrementa velocidad
                tiger.setNumVelocity((tiger.getNumVelocity())-1);
                    
            } else {
                //Resetea la velocidad al ser una direccion nueva
                direccion = 4;
                tiger.setNumVelocity(1);
            }
        }
    }
        
    
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
     * @param e es el <code>evento</code> que se genera en al presionar las teclas.
     */
    public void keyTyped(KeyEvent e) {
    	
    }
    
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla presionada.
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
    	
    }
    
    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen tiger con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
		
        if (tiger != null) {
	//Dibuja la imagen en la posicion actualizada
            g.drawImage(tiger.getImagenI(), tiger.getPosX(), tiger.getPosY(), this);
			
	} else {
	//Da un mensaje mientras se carga el dibujo	
            g.drawString("Estoy cargando..", 10, 10);
	
        }
		
    }
    /**
     * Metodo <I>paintCrash</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen choque con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paintCrash(Graphics g) {
         
        if (choque != null) {
	//Dibuja la imagen en la posicion actualizada
            g.drawImage(choque.getImage(), tiger.getPosX(), tiger.getPosY(), this);

        } else {
	//Da un mensaje mientras se carga el dibujo	
            g.drawString("Estoy cargando..", 10, 10);
        }		
    }
}