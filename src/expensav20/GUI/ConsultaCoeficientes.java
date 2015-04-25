/*
 * ConsultaCoeficientes.java
 *
 * Created on 26 de diciembre de 2007, 12:15
 */

package expensav20.GUI;

import Security.Security;
import informes.InformeListadoCoeficientes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import model.Coeficiente;

/**
 *
 * @author  Javier
 */
public class ConsultaCoeficientes extends javax.swing.JFrame implements Security{
    
    /** Creates new form ConsultaCoeficientes */
    public ConsultaCoeficientes() {
        initComponents();
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        this.jTlb_Resultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.sizeTable();
        this.jTlb_Resultados.updateUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxt_Coeficiente = new javax.swing.JTextField();
        jBtn_Consultar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTlb_Resultados = new javax.swing.JTable();
        jBtn_Listar = new javax.swing.JButton();
        jBtn_Activar = new javax.swing.JButton();
        jBtn_Modificar = new javax.swing.JButton();
        jBtn_Eliminar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Coeficientes");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar por:"));
        jLabel1.setText("Coeficiente:");

        jBtn_Consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/zoom.png")));
        jBtn_Consultar.setText("Consultar");
        jBtn_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxt_Coeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Consultar)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxt_Coeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Consultar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));
        jTlb_Resultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "T�tulo 1", "T�tulo 2", "T�tulo 3", "T�tulo 4"
            }
        ));
        jScrollPane1.setViewportView(jTlb_Resultados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtn_Listar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/report_add.png")));
        jBtn_Listar.setText("Listar");
        jBtn_Listar.setName("jBtn_Listar");
        jBtn_Listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ListarActionPerformed(evt);
            }
        });

        jBtn_Activar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_go.png")));
        jBtn_Activar.setText("Activar");
        jBtn_Activar.setName("jBtn_Activar");
        jBtn_Activar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ActivarActionPerformed(evt);
            }
        });

        jBtn_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_edit.png")));
        jBtn_Modificar.setText("Modificar");
        jBtn_Modificar.setName("jBtn_Modificar");
        jBtn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ModificarActionPerformed(evt);
            }
        });

        jBtn_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_delete.png")));
        jBtn_Eliminar.setText("Eliminar");
        jBtn_Eliminar.setName("jBtn_Eliminar");
        jBtn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_EliminarActionPerformed(evt);
            }
        });

        jBtn_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/door_out.png")));
        jBtn_Salir.setText("Salir");
        jBtn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtn_Listar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Activar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Salir))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Salir)
                    .addComponent(jBtn_Eliminar)
                    .addComponent(jBtn_Modificar)
                    .addComponent(jBtn_Activar)
                    .addComponent(jBtn_Listar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ListarActionPerformed
        InformeListadoCoeficientes ilc=new InformeListadoCoeficientes();
        Map param=new HashMap();
        param.put("nombre","%"+this.jTxt_Coeficiente.getText()+"%");
        ilc.setParameters(param);
        ilc.displayReport();
    }//GEN-LAST:event_jBtn_ListarActionPerformed

    private void jBtn_ActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ActivarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente previamente");
            return;
        }
        Coeficiente coe = (Coeficiente)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        if (coe.getFechaBaja()==null){
            JOptionPane.showMessageDialog(this.getContentPane(),"El coeficiente ya se encuentra activo");
            return;
        }
        if (coe.activar()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
            return;
        }
        this.actualizar();
        
    }//GEN-LAST:event_jBtn_ActivarActionPerformed

    private void jBtn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_EliminarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente previamente");
            return;
        }
        Coeficiente coe = (Coeficiente)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        if (coe.getFechaBaja()!=null){
            JOptionPane.showMessageDialog(this.getContentPane(),"El coeficiente ya se encuentra dado de baja");
            return;
        }
        if (coe.eliminar()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
            return;
        }
        this.actualizar();
    }//GEN-LAST:event_jBtn_EliminarActionPerformed

    private void jBtn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ModificarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente previamente");
            return;
        }
        Coeficiente coe = (Coeficiente)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        AltaCoeficientes ac=new AltaCoeficientes(coe,this);
        ac.setAlwaysOnTop(true);
        ac.setVisible(true);
        ac.setCc(this);

    }//GEN-LAST:event_jBtn_ModificarActionPerformed

    private void jBtn_ConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ConsultarActionPerformed
        Coeficiente coe=new Coeficiente();
        List lst=coe.buscarxCriteria(this.jTxt_Coeficiente.getText().trim());
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(lst);
        this.jTlb_Resultados.updateUI();
        
    }//GEN-LAST:event_jBtn_ConsultarActionPerformed

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaCoeficientes().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Activar;
    private javax.swing.JButton jBtn_Consultar;
    private javax.swing.JButton jBtn_Eliminar;
    private javax.swing.JButton jBtn_Listar;
    private javax.swing.JButton jBtn_Modificar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTlb_Resultados;
    private javax.swing.JTextField jTxt_Coeficiente;
    // Fin de declaraci�n de variables//GEN-END:variables
    
        class TableResultadosModel implements TableModel{
        
        Coeficiente coef= new Coeficiente();
        protected Object selected;
        private List lst=new ArrayList();
         
        TableResultadosModel(){
           
        }
        
        public List getLst() {
            return lst;
        }

        public void setLst(List lst) {
            this.lst = lst;
        }

        public int getRowCount() {
            return this.getLst().size();
        }

        public int getColumnCount() {
            return 2;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex){
                case 0: 
                    return "Coeficiente";
                case 1: 
                    return "Fecha Baja";
            }
            return "";
        }
        public Class<?> getColumnClass(int columnIndex) {
            return this.getClass();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
       

        public Object getValueAt(int rowIndex, int columnIndex) {
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            switch(columnIndex){
                case 0:
                    return ((Coeficiente)this.getLst().get(rowIndex)).getDenominacion();
                case 1:
                    if (((Coeficiente)this.getLst().get(rowIndex)).getFechaBaja()!=null){
                        return sdf.format(((Coeficiente)this.getLst().get(rowIndex)).getFechaBaja());                    
                    }
                    else{
                        return "";
                    }
            }
            
            return null;
        }
        

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }
    }
        
    public void sizeTable()
   {
        this.jTlb_Resultados.getColumnModel().getColumn(1).setPreferredWidth(80);
        this.jTlb_Resultados.getColumnModel().getColumn(0).setPreferredWidth(350);
   }
   public void actualizar(){
        Coeficiente coe=new Coeficiente();
        List lst=coe.buscarxCriteria(this.jTxt_Coeficiente.getText().trim());
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(lst);
        this.jTlb_Resultados.updateUI();
    }

    public Map getObjectsAffected() {
        Map a=new HashMap();
        a.put(this.jBtn_Activar.getName(),this.jBtn_Activar);
        a.put(this.jBtn_Eliminar.getName(),this.jBtn_Eliminar);
        a.put(this.jBtn_Listar.getName(),this.jBtn_Listar);
        a.put(this.jBtn_Modificar.getName(),this.jBtn_Modificar);
        return a;
    }

    public String getUIName() {
        return "ConsultaCoeficientes";
    }

}
