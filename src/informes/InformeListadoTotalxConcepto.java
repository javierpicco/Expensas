/*
 * InformeListadoUnidades.java
 *
 * Created on 22 de febrero de 2008, 23:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package informes;

import DAO.ConnectionSing;
import java.awt.Dialog;
import java.util.HashMap;
import java.util.Map;
import javax.print.attribute.standard.OrientationRequested;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Javier
 */
public class InformeListadoTotalxConcepto {
    
    /** Creates a new instance of InformeListadoUnidades */
    public InformeListadoTotalxConcepto() {
    }
    
    private Map parameters=new HashMap();

    public void displayReport(){
        JasperPrint jasperPrint;
        JasperViewer jasperViewer;
        jasperPrint = generateReport();
        jasperViewer = new JasperViewer(jasperPrint,false);
        //jasperViewer.setAlwaysOnTop(true);
        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jasperViewer.setVisible(true);
        jasperViewer=null;
       
    }// TODO: Agrege su codigo aqui:

    private JasperPrint generateReport()
    {
        JasperPrint jasperPrint = null;
        try
        {
            jasperPrint = JasperFillManager.fillReport(        
            System.getProperty("user.dir")+"/informesTemplate/listadoTotalConcepto.jasper", this.getParameters(),
            ConnectionSing.getConection());
        }
        catch (JRException e)
        {
            e.printStackTrace();
        }
        return jasperPrint;
    }        

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    
}
