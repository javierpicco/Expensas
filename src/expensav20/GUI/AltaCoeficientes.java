/*
 * AltaCoeficientes.java
 *
 * Created on 18 de diciembre de 2007, 20:21
 */

package expensav20.GUI;

import javax.swing.JOptionPane;
import model.Coeficiente;

/**
 *
 * @author  Javier
 */
public class AltaCoeficientes extends javax.swing.JFrame {
    private ConsultaCoeficientes cc=new ConsultaCoeficientes();
    private Coeficiente coeficienteUpdated = new Coeficiente();
    /** Creates new form AltaCoeficientes */
    public AltaCoeficientes() {
        initComponents();
    }
    public AltaCoeficientes(Coeficiente coe,ConsultaCoeficientes cc) {
        initComponents();
        this.jTxt_Denominacion.setText(coe.getDenominacion());
        this.jChk_Distribuible.setSelected(coe.isDistribuible());
        this.setCoeficienteUpdated(coe);
        this.setCc(cc);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPnl_Coeficientes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxt_Denominacion = new javax.swing.JTextField();
        jChk_Distribuible = new javax.swing.JCheckBox();
        jBtn_Aceptar = new javax.swing.JButton();
        jBtn_Cancelar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alta de Coeficientes");
        jPnl_Coeficientes.setBorder(javax.swing.BorderFactory.createTitledBorder("Coeficientes"));
        jLabel1.setText("Denominaci\u00f3n:");

        jChk_Distribuible.setText("Distribuible:");
        jChk_Distribuible.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jChk_Distribuible.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPnl_CoeficientesLayout = new javax.swing.GroupLayout(jPnl_Coeficientes);
        jPnl_Coeficientes.setLayout(jPnl_CoeficientesLayout);
        jPnl_CoeficientesLayout.setHorizontalGroup(
            jPnl_CoeficientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_CoeficientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxt_Denominacion, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jChk_Distribuible, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPnl_CoeficientesLayout.setVerticalGroup(
            jPnl_CoeficientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_CoeficientesLayout.createSequentialGroup()
                .addGroup(jPnl_CoeficientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxt_Denominacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChk_Distribuible))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtn_Aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/tick.png")));
        jBtn_Aceptar.setText("Aceptar");
        jBtn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_AceptarActionPerformed(evt);
            }
        });

        jBtn_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/cross.png")));
        jBtn_Cancelar.setText("Cancelar");
        jBtn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_CancelarActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnl_Coeficientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(180, Short.MAX_VALUE)
                        .addComponent(jBtn_Aceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Salir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnl_Coeficientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Salir)
                    .addComponent(jBtn_Cancelar)
                    .addComponent(jBtn_Aceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CancelarActionPerformed
        this.limpiar();
    }//GEN-LAST:event_jBtn_CancelarActionPerformed

    private void jBtn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AceptarActionPerformed
        if (this.validateFields()==false)
            return;
        Coeficiente coe=new Coeficiente();
        coe.setDenominacion(this.jTxt_Denominacion.getText());
        coe.setDistribuible(this.jChk_Distribuible.isSelected());
        if (this.getCoeficienteUpdated().getCodigo()==0){
            coe.guardar();
            if (coe.getCodigo()!=0){
                JOptionPane.showMessageDialog(this.getContentPane(),"El Coeficiente se guard� correctamente");
                this.limpiar();
            }
            else{
                JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
            }
        }
        else{
            coe.setCodigo(this.getCoeficienteUpdated().getCodigo());
            if(coe.modificar()!=0){
                JOptionPane.showMessageDialog(this.getContentPane(),"El coeficiente se ha modificado correctamente");
                this.getCc().actualizar();
            }
            else{
                JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
            }
        }
        
    }//GEN-LAST:event_jBtn_AceptarActionPerformed

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
                new AltaCoeficientes().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Aceptar;
    private javax.swing.JButton jBtn_Cancelar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JCheckBox jChk_Distribuible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPnl_Coeficientes;
    private javax.swing.JTextField jTxt_Denominacion;
    // Fin de declaraci�n de variables//GEN-END:variables
 
    public void limpiar(){
        this.jTxt_Denominacion.setText("");
        this.jChk_Distribuible.setSelected(false);
    }
    
    private boolean validateFields(){ 

        if(this.jTxt_Denominacion.getText().trim().length()==0 || this.jTxt_Denominacion.getText().trim().length()>30){
            JOptionPane.showMessageDialog(this.getContentPane(),"La denominaci�n no puede estar vac�a o contener mas de 30 caracteres");
            this.jTxt_Denominacion.requestFocus();
            return false;
        }
        
        return true;
    }

    public ConsultaCoeficientes getCc() {
        return cc;
    }

    public void setCc(ConsultaCoeficientes cc) {
        this.cc = cc;
    }

    public Coeficiente getCoeficienteUpdated() {
        return coeficienteUpdated;
    }

    public void setCoeficienteUpdated(Coeficiente coeficienteUpdated) {
        this.coeficienteUpdated = coeficienteUpdated;
    }
    
}
