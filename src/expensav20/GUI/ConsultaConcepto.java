/*
 * ConsultaConcepto.java
 *
 * Created on 26 de diciembre de 2007, 12:23
 */

package expensav20.GUI;

import Security.Security;
import informes.InformeListadoConceptos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import model.Coeficiente;
import model.Concepto;

/**
 *
 * @author  Javier
 */
public class ConsultaConcepto extends javax.swing.JFrame implements Security{
    
    /** Creates new form ConsultaConcepto */
    public ConsultaConcepto() {
        initComponents();
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        this.sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jCmb_Origen.setModel(new ComboOrigenesModel());
        this.jCmb_Origen.updateUI();
        this.jCmb_Coeficiente.setModel(new ComboCoeficientesModel());
        this.jCmb_Coeficiente.updateUI();
     
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
        jBtn_Consultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jChk_Descripcion = new javax.swing.JCheckBox();
        jChk_Origen = new javax.swing.JCheckBox();
        jChk_Prioridad = new javax.swing.JCheckBox();
        jChk_Coeficiente = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTxt_Prioridad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxt_Descripcion = new javax.swing.JTextField();
        jCmb_Origen = new javax.swing.JComboBox();
        jCmb_Coeficiente = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTlb_Resultados = new javax.swing.JTable();
        jBtn_Listar = new javax.swing.JButton();
        jBtn_Activar = new javax.swing.JButton();
        JBtn_Modificar = new javax.swing.JButton();
        jBtn_Eliminar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Conceptos");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Conceptos"));
        jLabel1.setText("Origen:");

        jBtn_Consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/zoom.png")));
        jBtn_Consultar.setText("Consultar");
        jBtn_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ConsultarActionPerformed(evt);
            }
        });

        jLabel2.setText("Descripcion:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar por:"));
        jChk_Descripcion.setText("Descripcion");
        jChk_Descripcion.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Descripcion.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Descripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_DescripcionActionPerformed(evt);
            }
        });

        jChk_Origen.setText("Origen");
        jChk_Origen.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Origen.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Origen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_OrigenActionPerformed(evt);
            }
        });

        jChk_Prioridad.setText("Prioridad");
        jChk_Prioridad.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Prioridad.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Prioridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_PrioridadActionPerformed(evt);
            }
        });

        jChk_Coeficiente.setText("Coeficiente");
        jChk_Coeficiente.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Coeficiente.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Coeficiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_CoeficienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jChk_Origen)
                    .addComponent(jChk_Descripcion)
                    .addComponent(jChk_Prioridad)
                    .addComponent(jChk_Coeficiente))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jChk_Descripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Origen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Prioridad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Coeficiente)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel3.setText("Prioridad:");

        jTxt_Prioridad.setEnabled(false);

        jLabel4.setText("Coeficiente:");

        jTxt_Descripcion.setEnabled(false);

        jCmb_Origen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Debe", "Haber" }));
        jCmb_Origen.setEnabled(false);

        jCmb_Coeficiente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Debe", "Haber" }));
        jCmb_Coeficiente.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmb_Coeficiente, 0, 172, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTxt_Prioridad, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCmb_Origen, javax.swing.GroupLayout.Alignment.LEADING, 0, 80, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Consultar))
                    .addComponent(jTxt_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxt_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCmb_Origen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTxt_Prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jBtn_Consultar)
                    .addComponent(jCmb_Coeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
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

        JBtn_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_edit.png")));
        JBtn_Modificar.setText("Modificar");
        JBtn_Modificar.setName("jBtn_Modificar");
        JBtn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBtn_ModificarActionPerformed(evt);
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
                .addGap(155, 155, 155)
                .addComponent(jBtn_Listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Activar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JBtn_Modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Salir))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JBtn_Modificar)
                        .addComponent(jBtn_Eliminar)
                        .addComponent(jBtn_Activar)
                        .addComponent(jBtn_Listar))
                    .addComponent(jBtn_Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ListarActionPerformed
        InformeListadoConceptos ilc=new InformeListadoConceptos();
        Map param=new HashMap();
        String nombre=new String();
        String origen = new String();
        int prioridad=0;
        Coeficiente coe=new Coeficiente();
        long coeficiente_id=0;
        if(this.jChk_Descripcion.isSelected()){
            if(this.jTxt_Descripcion.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una descripci�n");
                this.jTxt_Descripcion.requestFocus();
            }
            else{
                nombre=this.jTxt_Descripcion.getText();
            }
        }
        if(this.jChk_Origen.isSelected()){
            if(this.jCmb_Origen.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un origen");
                this.jCmb_Origen.requestFocus();
            }
            else{
                origen=String.valueOf(this.jCmb_Origen.getSelectedItem());
            }
        }
        if(this.jChk_Prioridad.isSelected()){
            if(this.jTxt_Prioridad.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una prioridad");
                this.jTxt_Prioridad.requestFocus();
            }
            else{
                prioridad=Integer.parseInt(this.jTxt_Prioridad.getText());
            }
        }
        if(this.jChk_Coeficiente.isSelected()){
            if(this.jCmb_Coeficiente.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente");
                this.jCmb_Coeficiente.requestFocus();
            }
            else{
                coe=((Coeficiente)((ComboCoeficientesModel)this.jCmb_Coeficiente.getModel()).getLst().get(this.jCmb_Coeficiente.getSelectedIndex()));
                coeficiente_id=coe.getCodigo();
            }
        }
        else{
            coe=null;
            coeficiente_id=0;
        }

        param.put("nombre","%"+nombre+"%");
        param.put("origen","%"+origen+"%");
        param.put("prioridad",String.valueOf(prioridad));
        param.put("coeficiente_id",String.valueOf(coeficiente_id));
        ilc.setParameters(param);
        ilc.displayReport();
    }//GEN-LAST:event_jBtn_ListarActionPerformed

    private void jBtn_ActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ActivarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un concepto previamente");
            return;
        }
        Concepto conc = (Concepto)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        if (conc.getFechaBaja()==null){
            JOptionPane.showMessageDialog(this.getContentPane(),"El concepto ya se encuentra activo");
            return;
        }
        if (conc.activar()!=0){
            JOptionPane.showMessageDialog(this.getContentPane(),"El concepto fue activado correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
        }
        this.actualizar();
    }//GEN-LAST:event_jBtn_ActivarActionPerformed

    private void jBtn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_EliminarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un concepto previamente");
            return;
        }
        Concepto conc = (Concepto)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        if (conc.getFechaBaja()!=null){
            JOptionPane.showMessageDialog(this.getContentPane(),"El concepto ya fue dado de baja");
            return;
        }
        if (conc.eliminar()!=0){
            JOptionPane.showMessageDialog(this.getContentPane(),"El concepto fue dado de baja correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
        }
        this.actualizar();
        
    }//GEN-LAST:event_jBtn_EliminarActionPerformed

    private void JBtn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBtn_ModificarActionPerformed
        
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un concepto previamente");
            return;
        }
        Concepto conc = (Concepto)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        AltaConceptos ac=new AltaConceptos(conc,this);
        ac.setAlwaysOnTop(true);
        ac.setVisible(true);
    }//GEN-LAST:event_JBtn_ModificarActionPerformed

    private void jBtn_ConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ConsultarActionPerformed
        String nombre=new String();
        String origen = new String();
        int prioridad=0;
        Coeficiente coe=new Coeficiente();
        if(this.jChk_Descripcion.isSelected()){
            if(this.jTxt_Descripcion.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una descripci�n");
                this.jTxt_Descripcion.requestFocus();
            }
            else{
                nombre=this.jTxt_Descripcion.getText();
            }
        }
        if(this.jChk_Origen.isSelected()){
            if(this.jCmb_Origen.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un origen");
                this.jCmb_Origen.requestFocus();
            }
            else{
                origen=String.valueOf(this.jCmb_Origen.getSelectedItem());
            }
        }
        if(this.jChk_Prioridad.isSelected()){
            if(this.jTxt_Prioridad.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una prioridad");
                this.jTxt_Prioridad.requestFocus();
            }
            else{
                prioridad=Integer.parseInt(this.jTxt_Prioridad.getText());
            }
        }
        if(this.jChk_Coeficiente.isSelected()){
            if(this.jCmb_Coeficiente.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente");
                this.jCmb_Coeficiente.requestFocus();
            }
            else{
                coe=((Coeficiente)((ComboCoeficientesModel)this.jCmb_Coeficiente.getModel()).getLst().get(this.jCmb_Coeficiente.getSelectedIndex()));
            }
        }
        else{
            coe=null;
        }

        Concepto con=new Concepto();
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(con.buscarxCriteria(nombre,origen,prioridad,coe));
        this.jTlb_Resultados.updateUI();
    }//GEN-LAST:event_jBtn_ConsultarActionPerformed

    private void jChk_CoeficienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_CoeficienteActionPerformed
        if(this.jChk_Coeficiente.isSelected()){
            this.jCmb_Coeficiente.setEnabled(true);
            this.jCmb_Coeficiente.requestFocus();
        }
        else{
            this.jCmb_Coeficiente.setEnabled(false);
            this.jCmb_Coeficiente.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jChk_CoeficienteActionPerformed

    private void jChk_PrioridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_PrioridadActionPerformed
        if(this.jChk_Prioridad.isSelected()){
            this.jTxt_Prioridad.setEnabled(true);
            this.jTxt_Prioridad.requestFocus();
        }
        else{
            this.jTxt_Prioridad.setText("");
            this.jTxt_Prioridad.setEnabled(false);
        }
    }//GEN-LAST:event_jChk_PrioridadActionPerformed

    private void jChk_OrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_OrigenActionPerformed
        if(this.jChk_Origen.isSelected()){
            this.jCmb_Origen.setEnabled(true);
            this.jCmb_Origen.requestFocus();
        }
        else{
            this.jCmb_Origen.setSelectedIndex(-1);
            this.jCmb_Origen.setEnabled(false);
        }
    }//GEN-LAST:event_jChk_OrigenActionPerformed

    private void jChk_DescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_DescripcionActionPerformed
        if (this.jChk_Descripcion.isSelected()){
            this.jTxt_Descripcion.setEnabled(true);
            this.jTxt_Descripcion.requestFocus();
        }
        else{
            this.jTxt_Descripcion.setEnabled(false);
            this.jTxt_Descripcion.setText("");
        }
    }//GEN-LAST:event_jChk_DescripcionActionPerformed

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
                new ConsultaConcepto().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton JBtn_Modificar;
    private javax.swing.JButton jBtn_Activar;
    private javax.swing.JButton jBtn_Consultar;
    private javax.swing.JButton jBtn_Eliminar;
    private javax.swing.JButton jBtn_Listar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JCheckBox jChk_Coeficiente;
    private javax.swing.JCheckBox jChk_Descripcion;
    private javax.swing.JCheckBox jChk_Origen;
    private javax.swing.JCheckBox jChk_Prioridad;
    private javax.swing.JComboBox jCmb_Coeficiente;
    private javax.swing.JComboBox jCmb_Origen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTlb_Resultados;
    private javax.swing.JTextField jTxt_Descripcion;
    private javax.swing.JTextField jTxt_Prioridad;
    // Fin de declaraci�n de variables//GEN-END:variables
    class ComboOrigenesModel implements ComboBoxModel{
        
        protected Object selected;
         
        ComboOrigenesModel(){
        }
        public void setSelectedItem(Object item) {
               this.selected=item;
        }
	public Object getSelectedItem() {
		return this.selected;
	}

	public String getElementAt(int index) {
                if (index==0){
                    return "Debe";
                }
                else{
                    return "Haber";
                }
	}
        
	public int getSize() {
		return 2;
	}        

        public void addListDataListener(ListDataListener l) {
        }

        public void removeListDataListener(ListDataListener l) {
        }

    }
      
    class ComboCoeficientesModel implements ComboBoxModel{
        
        Coeficiente coe=new Coeficiente();
        protected Object selected;
        private List lst;
         
        ComboCoeficientesModel(){
            this.setLst(coe.buscarActivos());
        }
        public void setSelectedItem(Object item) {
               this.selected=item;
        }
	public Object getSelectedItem() {
		return this.selected;
	}

	public String getElementAt(int index) {
                Coeficiente coef=new Coeficiente();
                coef=(Coeficiente)getLst().get(index);
		return coef.getDenominacion();
	}
        
	public int getSize() {
		return getLst().size();
	}        

        public void addListDataListener(ListDataListener l) {
        }

        public void removeListDataListener(ListDataListener l) {
        }

        public List getLst() {
            return lst;
        }

        public void setLst(List lst) {
            this.lst = lst;
        }
        
    }

      
    
    class TableResultadosModel implements TableModel{
        
        Concepto con= new Concepto();
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
            return 8;
        }
        public String getColumnName(int columnIndex) {
            switch (columnIndex){
                case 0: 
                    return "Descripcion";
                case 1: 
                    return "Origen";
                case 2: 
                    return "Coeficiente";
                case 3: 
                    return "Prioridad";
                case 4: 
                    return "Desc. Si/No";
                case 5: 
                    return "IG Si/No";
                case 6: 
                    return "Iva Si/No";
                case 7: 
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
                    return ((Concepto)this.getLst().get(rowIndex)).getNombre();
                case 1:
                    return ((Concepto)this.getLst().get(rowIndex)).getOrigen();
                case 2:
                    if (((Concepto)this.getLst().get(rowIndex)).getCoeficiente()!=null){
                        return ((Concepto)this.getLst().get(rowIndex)).getCoeficiente().getDenominacion();    
                    }
                    else{
                        return "";   
                    }
                    
                case 3:
                    return String.valueOf(((Concepto)this.getLst().get(rowIndex)).getPrioridad());
                case 4:
                    if (((Concepto)this.getLst().get(rowIndex)).isDescripcion()){
                        return "Si";
                    }
                    else{
                        return "No";
                    }
                case 5:
                    if (((Concepto)this.getLst().get(rowIndex)).isIg()){
                        return "Si";
                    }
                    else{
                        return "No";
                    }

                case 6:
                    if (((Concepto)this.getLst().get(rowIndex)).isIva()){
                        return "Si";
                    }
                    else{
                        return "No";
                    }
                case 7:
                    if (((Concepto)this.getLst().get(rowIndex)).getFechaBaja()!=null){
                        return sdf.format(((Concepto)this.getLst().get(rowIndex)).getFechaBaja());
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

    public void actualizar(){
        String nombre=new String();
        String origen = new String();
        int prioridad=0;
        Coeficiente coe=new Coeficiente();
        if(this.jChk_Descripcion.isSelected()){
            if(this.jTxt_Descripcion.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una descripci�n");
                this.jTxt_Descripcion.requestFocus();
            }
            else{
                nombre=this.jTxt_Descripcion.getText();
            }
        }
        if(this.jChk_Origen.isSelected()){
            if(this.jCmb_Origen.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un origen");
                this.jCmb_Origen.requestFocus();
            }
            else{
                origen=String.valueOf(this.jCmb_Origen.getSelectedItem());
            }
        }
        if(this.jChk_Prioridad.isSelected()){
            if(this.jTxt_Prioridad.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una prioridad");
                this.jTxt_Prioridad.requestFocus();
            }
            else{
                prioridad=Integer.parseInt(this.jTxt_Prioridad.getText());
            }
        }
        if(this.jChk_Coeficiente.isSelected()){
            if(this.jCmb_Coeficiente.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente");
                this.jCmb_Coeficiente.requestFocus();
            }
            else{
                coe=((Coeficiente)((ComboCoeficientesModel)this.jCmb_Coeficiente.getModel()).getLst().get(this.jCmb_Coeficiente.getSelectedIndex()));
            }
        }
        else{
            coe=null;
        }

        Concepto con=new Concepto();
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(con.buscarxCriteria(nombre,origen,prioridad,coe));
        this.jTlb_Resultados.updateUI();

   }
    public Map getObjectsAffected() {
        Map a=new HashMap();
        a.put(this.jBtn_Activar.getName(),this.jBtn_Activar);
        a.put(this.jBtn_Eliminar.getName(),this.jBtn_Eliminar);
        a.put(this.jBtn_Listar.getName(),this.jBtn_Listar);
        a.put(this.JBtn_Modificar.getName(),this.JBtn_Modificar);
        return a;
    }

    public String getUIName() {
        return "ConsultaConcepto";
    }
    
   public void sizeTable()
   {
        this.jTlb_Resultados.getColumnModel().getColumn(0).setPreferredWidth(175);
        this.jTlb_Resultados.getColumnModel().getColumn(1).setPreferredWidth(42);
        this.jTlb_Resultados.getColumnModel().getColumn(2).setPreferredWidth(85);
        this.jTlb_Resultados.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.jTlb_Resultados.getColumnModel().getColumn(4).setPreferredWidth(62);
        this.jTlb_Resultados.getColumnModel().getColumn(5).setPreferredWidth(46);
        this.jTlb_Resultados.getColumnModel().getColumn(6).setPreferredWidth(46);
        this.jTlb_Resultados.getColumnModel().getColumn(7).setPreferredWidth(86);
   }
}
