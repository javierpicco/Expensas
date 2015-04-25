/*
 * AltaConsorcio.java
 *
 * Created on 18 de diciembre de 2007, 20:01
 */

package expensav20.GUI;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import model.Consorcio;

/**
 *
 * @author  Javier
 */
public class AltaConsorcio extends javax.swing.JFrame {
    
    private Consorcio consorcioUpdated=new Consorcio();
    private ConsultaConsorcio cc=new ConsultaConsorcio();
    
    /** Creates new form AltaConsorcio */
    public AltaConsorcio() {
        initComponents();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            MaskFormatter mascara_cp = new MaskFormatter("A####AAA");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cp.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            mascara_cp.install(this.jFTxt_CP);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    public AltaConsorcio(Consorcio cons) {
        initComponents();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            MaskFormatter mascara_cp = new MaskFormatter("A####AAA");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cp.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            mascara_cp.install(this.jFTxt_CP);
            this.jTxt_Barrio.setText(cons.getBarrio());
            this.jTxt_Denominacion.setText(cons.getDenominacion());
            this.jTxt_Domicilio.setText(cons.getDomcilio());
            this.jTxt_Localidad.setText(cons.getLocalidad());
            this.jFTxt_CP.setText(cons.getCp().trim());
            if(cons.getCuit()!=0){
                this.jFTxt_Cuit.setText(String.valueOf(cons.getCuit()));
            }
            else{
                this.jFTxt_Cuit.setText("");
            }
            this.setConsorcioUpdated(cons);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    // Se construye el JFormattedTextField pas�ndole la m�scara
        
 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxt_Denominacion = new javax.swing.JTextField();
        jTxt_Domicilio = new javax.swing.JTextField();
        jTxt_Barrio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTxt_Localidad = new javax.swing.JTextField();
        jFTxt_Cuit = new javax.swing.JFormattedTextField();
        jFTxt_CP = new javax.swing.JFormattedTextField();
        jBtn_Aceptar = new javax.swing.JButton();
        jBtn_Cancelar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alta de Consorcios");
        setResizable(false);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Consorcios"));
        jLabel1.setText("Denominacion:");

        jLabel2.setText("Domicilio:");

        jLabel3.setText("Barrio:");

        jLabel4.setText("Localidad:");

        jLabel5.setText("No. CUIT:");

        jLabel6.setText("CP:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTxt_Localidad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFTxt_Cuit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxt_Denominacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTxt_Domicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTxt_Barrio, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFTxt_CP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGap(45, 45, 45))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxt_Denominacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxt_Domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxt_Barrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jFTxt_CP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxt_Localidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFTxt_Cuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(35, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(310, Short.MAX_VALUE)
                .addComponent(jBtn_Aceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Cancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Salir)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Aceptar)
                    .addComponent(jBtn_Cancelar)
                    .addComponent(jBtn_Salir))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AceptarActionPerformed
        if (this.validateFields()==false)
            return;
        Consorcio cs=new Consorcio();
        cs.setBarrio(this.jTxt_Barrio.getText());
        cs.setCp(this.jFTxt_CP.getText());
        if (this.jFTxt_Cuit.getText().replace("-","").trim().length()>1){        
            cs.setCuit(Long.parseLong(this.jFTxt_Cuit.getText().replace("-","")));
        }
        cs.setDenominacion(this.jTxt_Denominacion.getText());
        if (this.jTxt_Domicilio.getText().length()>30){
            cs.setDomcilio(this.jTxt_Domicilio.getText().substring(0,30));
        }
        else{
            cs.setDomcilio(this.jTxt_Domicilio.getText());
        }
            
        cs.setLocalidad(this.jTxt_Localidad.getText());
        if (this.getConsorcioUpdated().getCodigo()==0){
            cs.guardar();
            if (cs.getCodigo()!=0){
                JOptionPane.showMessageDialog(this.getContentPane(),"El consorcio fue dado de alta");
                this.limpiar();
            }
            else{
                JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, el alta fall�. Intente Nuevamente");
            }
        }
        else{
            cs.setCodigo(this.getConsorcioUpdated().getCodigo());
            if (cs.modificar()!=0){
                JOptionPane.showMessageDialog(this.getContentPane(),"El consorcio fue modificado correctamente");
                this.getCc().actualizar();
              }
            else{
                JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, la modficaci�n fall�. Intente Nuevamente");
            }
        }
            
    }//GEN-LAST:event_jBtn_AceptarActionPerformed

    private void jBtn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CancelarActionPerformed
        this.jTxt_Denominacion.setText("");
        this.jTxt_Domicilio.setText("");
        this.jTxt_Barrio.setText("");
        this.jFTxt_CP.setText("");
        this.jFTxt_Cuit.setText("");
        this.jTxt_Localidad.setText("");
    }//GEN-LAST:event_jBtn_CancelarActionPerformed

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
                new AltaConsorcio().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Aceptar;
    private javax.swing.JButton jBtn_Cancelar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JFormattedTextField jFTxt_CP;
    private javax.swing.JFormattedTextField jFTxt_Cuit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTxt_Barrio;
    private javax.swing.JTextField jTxt_Denominacion;
    private javax.swing.JTextField jTxt_Domicilio;
    private javax.swing.JTextField jTxt_Localidad;
    // Fin de declaraci�n de variables//GEN-END:variables
   
    //M�todo para validar Empty Fields
    private boolean validateFields(){
        
   
        if (this.jTxt_Denominacion.getText().trim().length()==0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una denominaci�n");
          this.jTxt_Denominacion.requestFocus();
          return false;
        }
        if (this.jTxt_Domicilio.getText().trim().length()==0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar un domicilio");
          this.jTxt_Domicilio.requestFocus();
          return false;
        }
        if (this.jTxt_Barrio.getText().trim().length()==0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar un barrio");
          this.jTxt_Barrio.requestFocus();
          return false;
        }
        if (this.jFTxt_CP.getText().trim().length()==0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar un c�digo postal v�lido. 4 D�gitos");
          this.jFTxt_CP.requestFocus();
          return false;
        }
        if (this.jTxt_Localidad.getText().trim().length()==0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar una localidad");
          this.jTxt_Localidad.requestFocus();
          return false;
        }
        if (this.jFTxt_Cuit.getText().replace("-","").trim().length()<11 && this.jFTxt_Cuit.getText().replace("-","").trim().length()>0){
          JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar un CUIT v�lido");
          this.jFTxt_Cuit.requestFocus();
          return false;
        }
        return true;
    }
    
    private void limpiar(){
        this.jTxt_Barrio.setText("");
        this.jTxt_Denominacion.setText("");
        this.jTxt_Domicilio.setText("");
        this.jTxt_Localidad.setText("");
        this.jFTxt_CP.setText("");
        this.jFTxt_Cuit.setText("");
    }

    public Consorcio getConsorcioUpdated() {
        return consorcioUpdated;
    }

    public void setConsorcioUpdated(Consorcio consorcioUpdated) {
        this.consorcioUpdated = consorcioUpdated;
    }

    public ConsultaConsorcio getCc() {
        return cc;
    }

    public void setCc(ConsultaConsorcio cc) {
        this.cc = cc;
    }
    
    
}