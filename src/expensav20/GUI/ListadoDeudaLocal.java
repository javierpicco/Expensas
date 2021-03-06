/*
 * ListadoSaldos.java
 *
 * Created on 18 de abril de 2008, 19:16
 */

package expensav20.GUI;

import informes.InformeListadoDeudaxLocal;
import informes.InformeListadoSaldos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import model.Consorcio;

/**
 *
 * @author  Javier
 */
public class ListadoDeudaLocal extends javax.swing.JFrame {
    
    /** Creates new form ListadoSaldos */
    public ListadoDeudaLocal() {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Consorcio.updateUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jCmb_Consorcio = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jBtn_Generar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Deuda por Local");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado Saldos"));
        jCmb_Consorcio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Galeria Norte", "Edificio Pajas Blancas" }));
        jCmb_Consorcio.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jCmb_ConsorcioPopupMenuWillBecomeVisible(evt);
            }
        });
        jCmb_Consorcio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmb_ConsorcioActionPerformed(evt);
            }
        });

        jLabel1.setText("Consorcio:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmb_Consorcio, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCmb_Consorcio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBtn_Generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/report_add.png")));
        jBtn_Generar.setText("Generar");
        jBtn_Generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_GenerarActionPerformed(evt);
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
                .addContainerGap(166, Short.MAX_VALUE)
                .addComponent(jBtn_Generar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Salir)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Generar)
                    .addComponent(jBtn_Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed

    private void jBtn_GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_GenerarActionPerformed
        if (this.jCmb_Consorcio.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un cosorcio previamente");
            return;
        }
        int consorcio_id=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
        Map a = new HashMap();
        a.put("consorcio_id",String.valueOf(consorcio_id));
        InformeListadoDeudaxLocal idl=new InformeListadoDeudaxLocal();
        idl.setParameters(a);
        idl.displayReport();
        
    }//GEN-LAST:event_jBtn_GenerarActionPerformed

    private void jCmb_ConsorcioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmb_ConsorcioActionPerformed
    
    }//GEN-LAST:event_jCmb_ConsorcioActionPerformed

    private void jCmb_ConsorcioPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jCmb_ConsorcioPopupMenuWillBecomeVisible
    
    }//GEN-LAST:event_jCmb_ConsorcioPopupMenuWillBecomeVisible
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListadoSaldos().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Generar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JComboBox jCmb_Consorcio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // Fin de declaraci�n de variables//GEN-END:variables
    
    class ComboConsorciosModel implements ComboBoxModel{    
        Consorcio cons=new Consorcio();
        protected Object selected;
        private List lst;
         
        ComboConsorciosModel(){
            this.setLst(cons.buscarActivos());
        }
        public void setSelectedItem(Object item) {
               this.selected=item;
        }
	public Object getSelectedItem() {
		return this.selected;
	}

	public String getElementAt(int index) {
            Consorcio consor=new Consorcio();
            consor=(Consorcio)getLst().get(index);
            return consor.getDenominacion();
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
}
