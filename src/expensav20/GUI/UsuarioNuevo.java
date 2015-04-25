/*
 * UsuarioNuevo.java
 *
 * Created on 2 de abril de 2008, 17:40
 */

package expensav20.GUI;

import Security.Role;
import Security.User;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;

/**
 *
 * @author  Javier
 */
public class UsuarioNuevo extends javax.swing.JFrame {
    private Usuarios scr;
    /** Creates new form UsuarioNuevo */
    public UsuarioNuevo(Usuarios scrUsuarios) {
        initComponents();
        this.setScr(scrUsuarios);
        this.jCmb_TipoUsuario.setModel(new ComboRolesModel());
        this.jCmb_TipoUsuario.updateUI();
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
        jTxt_Nombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPsw_Password = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jPsw_Repetir = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jCmb_TipoUsuario = new javax.swing.JComboBox();
        jBtn_Salir = new javax.swing.JButton();
        jBtn_Cancelar = new javax.swing.JButton();
        jBtn_Aceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Usuario");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));
        jLabel1.setText("Nombre:");

        jLabel2.setText("Password:");

        jLabel3.setText("Repetir Password:");

        jLabel4.setText("Tipo Usuario:");

        jCmb_TipoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Art\u00edculo 1", "Art\u00edculo 2", "Art\u00edculo 3", "Art\u00edculo 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCmb_TipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxt_Nombre)
                    .addComponent(jPsw_Repetir)
                    .addComponent(jPsw_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jCmb_TipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPsw_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPsw_Repetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtn_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/door_out.png")));
        jBtn_Salir.setText("Salir");
        jBtn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SalirActionPerformed(evt);
            }
        });

        jBtn_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/cross.png")));
        jBtn_Cancelar.setText("Cancelar");
        jBtn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_CancelarActionPerformed(evt);
            }
        });

        jBtn_Aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/tick.png")));
        jBtn_Aceptar.setText("Aceptar");
        jBtn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_AceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Aceptar)
                    .addComponent(jBtn_Cancelar)
                    .addComponent(jBtn_Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AceptarActionPerformed
        if (!this.validateFields())
            return;

        User usr=new User();
        usr.setName(this.jTxt_Nombre.getText());
        usr.setPassword(this.jPsw_Password.getText());
        Role rol=(Role)((ComboRolesModel)this.jCmb_TipoUsuario.getModel()).getLst().get(this.jCmb_TipoUsuario.getSelectedIndex());
        usr.setRole(rol);  
        usr.guardar();
        if (usr.getId()!=0){
            JOptionPane.showMessageDialog(this.getContentPane(),"El usuario fue guardado correctamente");
        }
        else{
            JOptionPane.showMessageDialog(this.getContentPane(),"Ocurri� un problema, por favor intente nuevamente");
        }
        this.getScr().refresh();
        
    }//GEN-LAST:event_jBtn_AceptarActionPerformed

    private void jBtn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CancelarActionPerformed
        this.jTxt_Nombre.setText("");
        this.jPsw_Password.setText("");
        this.jPsw_Repetir.setText("");
    }//GEN-LAST:event_jBtn_CancelarActionPerformed

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed
    

    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Aceptar;
    private javax.swing.JButton jBtn_Cancelar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JComboBox jCmb_TipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPsw_Password;
    private javax.swing.JPasswordField jPsw_Repetir;
    private javax.swing.JTextField jTxt_Nombre;
    // Fin de declaraci�n de variables//GEN-END:variables
    public boolean validateFields(){
        if(this.jTxt_Nombre.getText().length()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el nombre del usuario");
            this.jTxt_Nombre.requestFocus();
            return false;
        }
        if(this.jPsw_Password.getText().length()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el password");
            this.jPsw_Password.requestFocus();
            return false;
        }
        if(this.jPsw_Repetir.getText().length()==0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe repetir el password ingresado en el campo password");
            this.jPsw_Repetir.requestFocus();
            return false;
        }
        if(this.jPsw_Password.getText().compareTo(this.jPsw_Repetir.getText())!=0){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el mismo password en el campo password y el campo repetir");
            this.jPsw_Repetir.requestFocus();
            return false;
        }
        if (this.jCmb_TipoUsuario.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un tipo de usuario");
            this.jCmb_TipoUsuario.requestFocus();
            return false;
        }
        return true;
    }
    
    class ComboRolesModel implements ComboBoxModel{
        
        Role rol=new Role();
        protected Object selected;
        private List lst;
         
        ComboRolesModel(){
            lst=rol.buscarTodos();
        }
        public void setSelectedItem(Object item) {
               this.selected=item;
        }
	public Object getSelectedItem() {
		return this.selected;
	}

	public String getElementAt(int index) {
                Role rol=new Role();
                rol=(Role)getLst().get(index);
		return rol.getName();
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

    public Usuarios getScr() {
        return scr;
    }

    public void setScr(Usuarios scr) {
        this.scr = scr;
    }
}
