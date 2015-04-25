package expensav20;
import expensav20.GUI.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.UIManager;
import model.Consorcio;



/**
 *
 * @author Javier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("Quaqua.tabLayoutPolicy","wrap");
         try {
              UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
         } catch (Exception e) {
             e.printStackTrace();
         }
        Acceso ac=new Acceso();
        Utilidades.centrarVentana(ac);
        ac.setVisible(true);
    }

}

