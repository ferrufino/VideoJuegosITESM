/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan
 */
      import java.awt.Image;
      import java.awt.Toolkit;

    public class Bueno extends Base{
        
    public Bueno(int posX,int posY){
	super(posX,posY);	
        Image bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat1.png"));
        Image bueno2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat2.png"));
        Image bueno3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat3.png"));
        Image bueno4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat4.png"));
        Image bueno5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/cat5.png"));
        animacion = new Animacion();
        animacion.sumaCuadro(bueno1, 100);
        animacion.sumaCuadro(bueno2, 100);
        animacion.sumaCuadro(bueno3, 100);
        animacion.sumaCuadro(bueno4, 100);
        animacion.sumaCuadro(bueno5, 100);
	}
    
    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";
    
    public static String getPausado(){
        return PAUSADO;
    }
    
    public static String getDesaparece(){
        return DESAPARECE;
    }
    
}
