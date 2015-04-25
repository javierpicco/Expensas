/*
 * ConsultaCuentaCorriente.java
 *
 * Created on 20 de diciembre de 2007, 20:15
 */

package expensav20.GUI;

import Security.Security;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author  Javier
 */
public class ConsultaGastos extends javax.swing.JFrame implements Security {
    
    /** Creates new form ConsultaCuentaCorriente */
    public ConsultaGastos() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTlb_Resultados = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jBtn_Modificar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();
        jBtn_Transferir = new javax.swing.JButton();
        jBtn_Eliminar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCmb_Consorcio = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jBtn_Consultar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTxt_Propietario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxt_Inquilino = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jChk_Propietario = new javax.swing.JCheckBox();
        jChk_Consorcio = new javax.swing.JCheckBox();
        jChk_Unidad = new javax.swing.JCheckBox();
        jChk_Inquilino = new javax.swing.JCheckBox();
        jChk_Periodo = new javax.swing.JCheckBox();
        jChk_Concepto = new javax.swing.JCheckBox();
        jCmb_Unidad = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTxt_Desde = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTxt_Hasta = new javax.swing.JTextField();
        jCmb_Concepto = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jBtn_Popular = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Gastos");
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Gastos Cargados"));
        jTlb_Resultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"12/12/2007", "Alquiler", "1000", "", "1000"},
                {"13/12/2007", "Pago", null, "1000", "0"},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Concepto", "Debe", "Haber", "Saldo"
            }
        ));
        jScrollPane1.setViewportView(jTlb_Resultados);

        jLabel5.setText("Total Gasto:");

        jTextField3.setBackground(new java.awt.Color(204, 204, 204));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(376, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jBtn_Modificar.setText("Modificar");
        jBtn_Modificar.setName("jBtn_Modificar");

        jBtn_Salir.setText("Salir");
        jBtn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SalirActionPerformed(evt);
            }
        });

        jBtn_Transferir.setText("Transferir");
        jBtn_Transferir.setName("jBtn_Transferir");
        jBtn_Transferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_TransferirActionPerformed(evt);
            }
        });

        jBtn_Eliminar.setText("Eliminar");
        jBtn_Eliminar.setName("jBtn_Eliminar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidad"));
        jLabel1.setText("Consorcio:");

        jCmb_Consorcio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Galeria Norte", "Edificio Pajas Blancas" }));
        jCmb_Consorcio.setEnabled(false);

        jLabel2.setText("Unidad:");

        jBtn_Consultar.setText("Consultar");

        jLabel3.setText("Propietario:");

        jTxt_Propietario.setBackground(new java.awt.Color(204, 204, 204));
        jTxt_Propietario.setEnabled(false);
        jTxt_Propietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_PropietarioActionPerformed(evt);
            }
        });

        jLabel4.setText("Inquilino:");

        jTxt_Inquilino.setBackground(new java.awt.Color(204, 204, 204));
        jTxt_Inquilino.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar por:"));
        jChk_Propietario.setText("Propietario");
        jChk_Propietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_PropietarioActionPerformed(evt);
            }
        });

        jChk_Consorcio.setText("Consorcio");
        jChk_Consorcio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_ConsorcioActionPerformed(evt);
            }
        });

        jChk_Unidad.setText("Unidad");
        jChk_Unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_UnidadActionPerformed(evt);
            }
        });

        jChk_Inquilino.setText("Inquilino");
        jChk_Inquilino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_InquilinoActionPerformed(evt);
            }
        });

        jChk_Periodo.setText("Periodo");
        jChk_Periodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_PeriodoActionPerformed(evt);
            }
        });

        jChk_Concepto.setText("Concepto");
        jChk_Concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_ConceptoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jChk_Periodo)
                    .addComponent(jChk_Unidad)
                    .addComponent(jChk_Propietario)
                    .addComponent(jChk_Consorcio)
                    .addComponent(jChk_Inquilino)
                    .addComponent(jChk_Concepto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jChk_Consorcio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Unidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Propietario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Inquilino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Periodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jChk_Concepto))
        );

        jCmb_Unidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Local 1", "Local 2", "Local 3" }));
        jCmb_Unidad.setEnabled(false);

        jLabel6.setText("Desde:");

        jTxt_Desde.setBackground(new java.awt.Color(204, 204, 204));
        jTxt_Desde.setEnabled(false);

        jLabel7.setText("Hasta:");

        jTxt_Hasta.setBackground(new java.awt.Color(204, 204, 204));
        jTxt_Hasta.setEnabled(false);

        jCmb_Concepto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Local 1", "Local 2", "Local 3" }));
        jCmb_Concepto.setEnabled(false);

        jLabel8.setText("Concepto:");

        jBtn_Popular.setText("...");
        jBtn_Popular.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTxt_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxt_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCmb_Unidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxt_Propietario)
                    .addComponent(jTxt_Inquilino)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmb_Concepto, 0, 275, Short.MAX_VALUE))
                    .addComponent(jCmb_Consorcio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtn_Consultar)
                    .addComponent(jBtn_Popular, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCmb_Consorcio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Popular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCmb_Unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTxt_Propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxt_Inquilino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTxt_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmb_Concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Consultar)
                    .addComponent(jLabel8)))
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(263, Short.MAX_VALUE)
                        .addComponent(jBtn_Eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Transferir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Salir))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Salir)
                    .addComponent(jBtn_Modificar)
                    .addComponent(jBtn_Transferir)
                    .addComponent(jBtn_Eliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jChk_ConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_ConceptoActionPerformed
        if(this.jChk_Concepto.isSelected()){
            this.jCmb_Concepto.setEnabled(true);
            this.jCmb_Concepto.requestFocus();
        }
        else{
            this.jCmb_Concepto.setEnabled(false);
            this.jCmb_Concepto.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jChk_ConceptoActionPerformed

    private void jChk_PeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_PeriodoActionPerformed
        if(this.jChk_Periodo.isSelected()){
            this.jTxt_Desde.setEnabled(true);
            this.jTxt_Hasta.setEnabled(true);
            this.jTxt_Desde.requestFocus();
        }
        else{
            this.jTxt_Desde.setEnabled(false);
            this.jTxt_Hasta.setEnabled(false);
            this.jTxt_Desde.setText("");
            this.jTxt_Hasta.setText("");
        }
    }//GEN-LAST:event_jChk_PeriodoActionPerformed

    private void jChk_InquilinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_InquilinoActionPerformed
        if(this.jChk_Inquilino.isSelected()){
            this.jTxt_Inquilino.setEnabled(true);
            this.jTxt_Inquilino.requestFocus();
        }
        else{
            this.jTxt_Inquilino.setEnabled(false);
            this.jTxt_Inquilino.setText("");
        }
    }//GEN-LAST:event_jChk_InquilinoActionPerformed

    private void jChk_PropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_PropietarioActionPerformed
        if (this.jChk_Propietario.isSelected()){
            this.jTxt_Propietario.setEnabled(true);
            this.jTxt_Propietario.requestFocus();
        }
        else{
            this.jTxt_Propietario.setEnabled(false);
            this.jTxt_Propietario.setText("");
        }
    }//GEN-LAST:event_jChk_PropietarioActionPerformed

    private void jChk_UnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_UnidadActionPerformed
        if (this.jChk_Unidad.isSelected()){
            this.jCmb_Consorcio.setEnabled(true);
            this.jCmb_Unidad.setEnabled(true);
            this.jBtn_Popular.setEnabled(true);
            this.jCmb_Unidad.requestFocus();
        }
        else{
            this.jCmb_Unidad.setEnabled(false);
            this.jCmb_Unidad.setSelectedIndex(-1);
            this.jBtn_Popular.setEnabled(false);
            if(!this.jChk_Consorcio.isSelected()){
                this.jCmb_Consorcio.setSelectedIndex(-1);
                this.jCmb_Consorcio.setEnabled(false);
            }
                
        }
    }//GEN-LAST:event_jChk_UnidadActionPerformed

    private void jChk_ConsorcioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_ConsorcioActionPerformed
        if (this.jChk_Consorcio.isSelected()==true){
            this.jCmb_Consorcio.setEnabled(true);
            this.jCmb_Consorcio.requestFocus();
        }
        else{
            this.jCmb_Consorcio.setEnabled(false);
            this.jCmb_Consorcio.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jChk_ConsorcioActionPerformed

    private void jBtn_TransferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_TransferirActionPerformed
        /*AltaTransferencia at = new AltaTransferencia();
        at.setVisible(true);*/
    }//GEN-LAST:event_jBtn_TransferirActionPerformed

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed

    private void jTxt_PropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_PropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_PropietarioActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaCuentaCorriente().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Consultar;
    private javax.swing.JButton jBtn_Eliminar;
    private javax.swing.JButton jBtn_Modificar;
    private javax.swing.JButton jBtn_Popular;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JButton jBtn_Transferir;
    private javax.swing.JCheckBox jChk_Concepto;
    private javax.swing.JCheckBox jChk_Consorcio;
    private javax.swing.JCheckBox jChk_Inquilino;
    private javax.swing.JCheckBox jChk_Periodo;
    private javax.swing.JCheckBox jChk_Propietario;
    private javax.swing.JCheckBox jChk_Unidad;
    private javax.swing.JComboBox jCmb_Concepto;
    private javax.swing.JComboBox jCmb_Consorcio;
    private javax.swing.JComboBox jCmb_Unidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable jTlb_Resultados;
    private javax.swing.JTextField jTxt_Desde;
    private javax.swing.JTextField jTxt_Hasta;
    private javax.swing.JTextField jTxt_Inquilino;
    private javax.swing.JTextField jTxt_Propietario;
    // Fin de declaraci�n de variables//GEN-END:variables
    public Map getObjectsAffected() {
        Map a=new HashMap();
        a.put(this.jBtn_Eliminar.getName(),this.jBtn_Eliminar);
        a.put(this.jBtn_Modificar.getName(),this.jBtn_Modificar);
        a.put(this.jBtn_Transferir.getName(),this.jBtn_Transferir);
        return a;
    }

    public String getUIName() {
        return "ConsultaGastos";
    }
}
