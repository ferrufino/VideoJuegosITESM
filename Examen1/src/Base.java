
/**
 * Clase Base
 *
 * @author Gustavo Ferrufino
 * @version 1.00 26/01/2014
 */
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Base {

    int posX;    //posicion en x.       
    int posY;	//posicion en y.
    private ArrayList cuadros;
    private int indiceCuadroActual;
    private long tiempoDeAnimacion;
    private long duracionTotal;

    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param image es la <code>imagen</code> del objeto.
     */
    public Base(int posX, int posY, Image image) {
        this.posX = posX;
        this.posY = posY;

        cuadros = new ArrayList();
        duracionTotal = 0;
        iniciar();
    }

    /**
     * Añade una cuadro a la animación con la duración indicada (tiempo que se
     * muestra la imagen).
     */
    public synchronized void sumaCuadro(Image imagen, long duracion) {
        duracionTotal += duracion;
        cuadros.add(new cuadroDeAnimacion(imagen, duracionTotal));
    }

    // Inicializa la animación desde el principio. 
    public synchronized void iniciar() {
        tiempoDeAnimacion = 0;
        indiceCuadroActual = 0;
    }

    /**
     * Actualiza la imagen (cuadro) actual de la animación, si es necesario.
     */
    public synchronized void actualiza(long tiempoTranscurrido) {
        if (cuadros.size() > 1) {
            tiempoDeAnimacion += tiempoTranscurrido;

            if (tiempoDeAnimacion >= duracionTotal) {
                tiempoDeAnimacion = tiempoDeAnimacion % duracionTotal;
                indiceCuadroActual = 0;
            }

            while (tiempoDeAnimacion > getCuadro(indiceCuadroActual).tiempoFinal) {
                indiceCuadroActual++;
            }
        }
    }

    /**
     * Captura la imagen actual de la animación. Regeresa null si la animación
     * no tiene imágenes.
     */
    public synchronized Image getImagen() {
        if (cuadros.size() == 0) {
            return null;
        } else {
            return getCuadro(indiceCuadroActual).imagen;
        }
    }

    private cuadroDeAnimacion getCuadro(int i) {
        return (cuadroDeAnimacion) cuadros.get(i);
    }

    public class cuadroDeAnimacion {

        Image imagen;
        long tiempoFinal;

        public cuadroDeAnimacion() {
            this.imagen = null;
            this.tiempoFinal = 0;
        }

        public cuadroDeAnimacion(Image imagen, long tiempoFinal) {
            this.imagen = imagen;
            this.tiempoFinal = tiempoFinal;
        }

        public Image getImagen() {
            return imagen;
        }

        public long getTiempoFinal() {
            return tiempoFinal;
        }

        public void setImagen(Image imagen) {
            this.imagen = imagen;
        }

        public void setTiempoFinal(long tiempoFinal) {
            this.tiempoFinal = tiempoFinal;
        }
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho() {
        return (new ImageIcon(animacion.getImagen())).getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto() {
        return (new ImageIcon(animacion.getImagen())).getIconHeight();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Checa si el objeto <code>Figura</code> intersecta con una coordenadas
     *
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersectaPuntos(int X, int Y) {
        return getPerimetro().contains(X, Y);
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo Creado como base de la <code>Base</code> Asteroide
     */
    public Rectangle getParamRec() {
        return new Rectangle(getPosX() + 10, getPosY() + getAlto(), getAncho() - 20, 1);
    }

}	