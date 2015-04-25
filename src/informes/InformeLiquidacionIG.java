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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Javier
 */
public class InformeLiquidacionIG {
    
    /** Creates a new instance of InformeListadoUnidades */
    public InformeLiquidacionIG() {
    }
    
    private Map parameters=new HashMap();

    public void displayReport(List l){
        JasperPrint jasperPrint;
        JasperViewer jasperViewer;
        jasperPrint = generateReport(l);
        jasperViewer = new JasperViewer(jasperPrint,false);
        //jasperViewer.setAlwaysOnTop(true);
        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jasperViewer.setVisible(true);
        jasperViewer=null;
       
    }// TODO: Agrege su codigo aqui:

    private JasperPrint generateReport(List l)
    {
        JasperPrint jasperPrint = null;
        try
        {
            String query="SELECT *FROM tmpig WHERE tmpig_codigo IN";
            Iterator it = l.iterator();
            int x=0;
            while (it.hasNext()){
                Long codigo=(Long)it.next();    
                
                if (x==0){
                    query=query+"("+codigo;    
                }
                else{
                    query=query+","+codigo;    
                }
                x++;
            }
            query=query+")";
            PreparedStatement statement;
            ResultSet resultSet=null;
            try {
                statement = ConnectionSing.getConection().prepareStatement(query);
                resultSet = statement.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
 
            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
            jasperPrint = JasperFillManager.fillReport(        
            System.getProperty("user.dir")+"/informesTemplate/IngresosBrutos.jasper", new HashMap(),
            resultSetDataSource);
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
