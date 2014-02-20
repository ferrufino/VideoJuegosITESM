/*
 *Class JavaFrameExamen1
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package javaframeexamen1;

import javax.swing.JFrame;

public class JavaFrameExamen1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppletExamen1 variable = new AppletExamen1();//creo un objeto
        variable.setVisible(true); //Aparezca mi codigo en clase AppletExamen1
        variable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
