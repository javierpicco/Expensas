/*
 * informeLiquidacion.java
 *
 * Created on 28 de enero de 2008, 20:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package informes;

import DAO.ConnectionSing;
import java.awt.Dialog;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Javier
 */
public class InformePagos {
    
    /** Creates a new instance of informeLiquidacion */
    public InformePagos() {
    }
    
    private Map parameters=new HashMap();

    public void displayReport(JTable TableJasper){
        JasperPrint jasperPrint;
        JasperViewer jasperViewer;
        jasperPrint = generateReport(TableJasper);
        jasperViewer = new JasperViewer(jasperPrint,false);
        //jasperViewer.setAlwaysOnTop(true);
        jasperViewer.setZoomRatio(0.55f);
        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jasperViewer.setVisible(true);
        jasperViewer=null;
       
    }// TODO: Agrege su codigo aqui:

    private JasperPrint generateReport(JTable TableJasper)
    {
        JasperPrint jasperPrint = null;
        try
        {
            this.getParameters().put("SUBREPORT_DIR",System.getProperty("user.dir")+"/informesTemplate/");
            //this.getParameters().put("consorcio_id","5");
            JRTableModelDataSource Tabledata = new JRTableModelDataSource(TableJasper.getModel());
            jasperPrint = JasperFillManager.fillReport(        
            System.getProperty("user.dir")+"/informesTemplate/listadoPagos.jasper", this.getParameters(),
            Tabledata);
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
