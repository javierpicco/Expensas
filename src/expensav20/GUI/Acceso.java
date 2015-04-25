/*
 * Acceso.java
 *
 * Created on 21 de diciembre de 2007, 7:54
 */

package expensav20.GUI;

import Security.Role;
import Security.SecurityManagerFwk;
import Security.User;
import expensav20.Utilidades;
import javax.swing.JOptionPane;

/**
 *
 * @author  Javier
 */
public class Acceso extends javax.swing.JFrame {
    private static User Usuario;
    /** Creates new form Acceso */
    public Acceso() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jBtn_Cambiar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxt_Usuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPsw_Password = new javax.swing.JPasswordField();
        jBtn_Acceder = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        jBtn_Cambiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/group_key.png")));
        jBtn_Cambiar.setText("Cambiar Contrase\u00f1a");
        jBtn_Cambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_CambiarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));
        jLabel1.setText("Usuario:");

        jLabel2.setText("Contrase\u00f1a:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxt_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jPsw_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPsw_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jBtn_Acceder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/tick.png")));
        jBtn_Acceder.setText("Acceder");
        jBtn_Acceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_AccederActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jBtn_Acceder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Cambiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Salir)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Acceder)
                    .addComponent(jBtn_Cambiar)
                    .addComponent(jBtn_Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed

    private void jBtn_CambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CambiarActionPerformed
        if (this.jTxt_Usuario.getText().length()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el nombre de usuario para cambiar el password");
            this.jTxt_Usuario.requestFocus();
            return;
        }
        User usr=new User();
        usr.setName(this.jTxt_Usuario.getText());
        CambiarPass cp=new CambiarPass(usr);
        Utilidades.centrarVentana(cp);
        cp.setVisible(true);
    }//GEN-LAST:event_jBtn_CambiarActionPerformed

    private void jBtn_AccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AccederActionPerformed
        Menu mn=new Menu();
        User usr=new User();
        usr=usr.validarUser(this.jTxt_Usuario.getText(),String.valueOf(this.jPsw_Password.getPassword()));        
        if (usr==null){
            JOptionPane.showMessageDialog(this.getContentPane(),"El usuario/password ingresado es incorrecto");
            this.jTxt_Usuario.requestFocus();
            return;
        }
        this.Usuario=usr;
        mn.setUsuario(usr);
        SecurityManagerFwk sm=new SecurityManagerFwk();
        sm.validateSecurity(this.Usuario, mn);
        mn.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_AccederActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Acceso().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Acceder;
    private javax.swing.JButton jBtn_Cambiar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPsw_Password;
    private javax.swing.JTextField jTxt_Usuario;
    // Fin de declaraci�n de variables//GEN-END:variables
    
}
