/*
 * ConsultaPropietario.java
 *
 * Created on 26 de diciembre de 2007, 11:55
 */

package expensav20.GUI;

import Security.Security;
import informes.InformeListadoPropietarios;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import model.Consorcio;
import model.Propietario;
import model.Unidad;

/**
 *
 * @author  Javier
 */
public class ConsultaPropietario extends javax.swing.JFrame implements Security{
    private ConsultaCuentaCorriente cc;
    private ConsultaPagos cp;
    private ImpIngresosBrutos ig;
    private ListadoExpensasComunes lec;
    private InformeAnexoIGUI iii;
    /** Creates new form ConsultaPropietario */
    public ConsultaPropietario() {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
    }
    
    public ConsultaPropietario(ConsultaCuentaCorriente ccc) {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jBtn_Listar.setEnabled(false);
        this.jBtn_Modificar.setEnabled(false);
        this.jBtn_Eliminar2.setEnabled(false);
        this.setCc(ccc);
    }

    public ConsultaPropietario(ConsultaPagos cp) {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jBtn_Listar.setEnabled(false);
        this.jBtn_Modificar.setEnabled(false);
        this.jBtn_Eliminar2.setEnabled(false);
        this.setCp(cp);
    }
    
    public ConsultaPropietario(ListadoExpensasComunes exp) {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jBtn_Listar.setEnabled(false);
        this.jBtn_Modificar.setEnabled(false);
        this.jBtn_Eliminar2.setEnabled(false);
        this.setLec(exp);
    }


    public ConsultaPropietario(ImpIngresosBrutos ig) {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jBtn_Listar.setEnabled(false);
        this.jBtn_Modificar.setEnabled(false);
        this.jBtn_Eliminar2.setEnabled(false);
        this.setIg(ig);
    }
    public ConsultaPropietario(InformeAnexoIGUI ig) {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Unidad.setModel(new ComboUnidadesModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Unidad.updateUI();
        try {
            MaskFormatter mascara_cuit = new MaskFormatter("##-########-#");
            mascara_cuit.setValueContainsLiteralCharacters(false);
            mascara_cuit.install(this.jFTxt_Cuit);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        this.jTlb_Resultados.setModel(new TableResultadosModel());
        sizeTable();
        this.jTlb_Resultados.updateUI();
        this.jBtn_Listar.setEnabled(false);
        this.jBtn_Modificar.setEnabled(false);
        this.jBtn_Eliminar2.setEnabled(false);
        this.setIii(ig);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Código Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxt_Nombre = new javax.swing.JTextField();
        jBtn_Consultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jChk_Nombre = new javax.swing.JCheckBox();
        jChk_Cuit = new javax.swing.JCheckBox();
        jChk_Cosorcio = new javax.swing.JCheckBox();
        jChk_Unidad = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCmb_Consorcio = new javax.swing.JComboBox();
        jCmb_Unidad = new javax.swing.JComboBox();
        jFTxt_Cuit = new javax.swing.JFormattedTextField();
        jBtn_Popular = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTlb_Resultados = new javax.swing.JTable();
        jBtn_Listar = new javax.swing.JButton();
        jBtn_Modificar = new javax.swing.JButton();
        jBtn_Salir = new javax.swing.JButton();
        jBtn_Eliminar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Propietarios");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Propietarios"));
        jLabel1.setText("CUIT:");

        jTxt_Nombre.setEnabled(false);

        jBtn_Consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/zoom.png")));
        jBtn_Consultar.setText("Consultar");
        jBtn_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ConsultarActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar por:"));
        jChk_Nombre.setText("Nombre");
        jChk_Nombre.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Nombre.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_NombreActionPerformed(evt);
            }
        });

        jChk_Cuit.setText("CUIT");
        jChk_Cuit.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Cuit.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Cuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_CuitActionPerformed(evt);
            }
        });

        jChk_Cosorcio.setText("Consorcio");
        jChk_Cosorcio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Cosorcio.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Cosorcio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_CosorcioActionPerformed(evt);
            }
        });

        jChk_Unidad.setText("Unidad");
        jChk_Unidad.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jChk_Unidad.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jChk_Unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChk_UnidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jChk_Nombre)
                    .addComponent(jChk_Cuit)
                    .addComponent(jChk_Cosorcio)
                    .addComponent(jChk_Unidad))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jChk_Nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Cuit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Cosorcio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChk_Unidad)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setText("Consorcio:");

        jLabel4.setText("Unidad:");

        jCmb_Consorcio.setEditable(true);
        jCmb_Consorcio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Art\u00edculo 1", "Art\u00edculo 2", "Art\u00edculo 3", "Art\u00edculo 4" }));
        jCmb_Consorcio.setEnabled(false);
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

        jCmb_Unidad.setEditable(true);
        jCmb_Unidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Art\u00edculo 1", "Art\u00edculo 2", "Art\u00edculo 3", "Art\u00edculo 4" }));
        jCmb_Unidad.setEnabled(false);
        jCmb_Unidad.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jCmb_UnidadPopupMenuWillBecomeVisible(evt);
            }
        });
        jCmb_Unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmb_UnidadActionPerformed(evt);
            }
        });

        jFTxt_Cuit.setEnabled(false);

        jBtn_Popular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/hourglass_add.png")));
        jBtn_Popular.setEnabled(false);
        jBtn_Popular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_PopularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTxt_Nombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFTxt_Cuit, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCmb_Consorcio, 0, 327, Short.MAX_VALUE)))
                    .addComponent(jCmb_Unidad, 0, 327, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtn_Popular, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Consultar)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jFTxt_Cuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmb_Consorcio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtn_Popular))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jBtn_Consultar))
                            .addComponent(jCmb_Unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43)))
                .addContainerGap())
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
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        jScrollPane1.setViewportView(jTlb_Resultados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
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

        jBtn_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_edit.png")));
        jBtn_Modificar.setText("Modificar");
        jBtn_Modificar.setName("jBtn_Modificar");
        jBtn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ModificarActionPerformed(evt);
            }
        });

        jBtn_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/door_out.png")));
        jBtn_Salir.setText("Salir");
        jBtn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SalirActionPerformed(evt);
            }
        });

        jBtn_Eliminar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/page_white_delete.png")));
        jBtn_Eliminar2.setText("Eliminar");
        jBtn_Eliminar2.setName("jBtn_Eliminar2");
        jBtn_Eliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_Eliminar2ActionPerformed(evt);
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBtn_Listar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Eliminar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtn_Salir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Salir)
                    .addComponent(jBtn_Eliminar2)
                    .addComponent(jBtn_Modificar)
                    .addComponent(jBtn_Listar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_Eliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_Eliminar2ActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un propietario previamente");
            return;
        }
        if(JOptionPane.showConfirmDialog(this.getContentPane(),"Está seguro que desea eliminar el propietario?")==JOptionPane.YES_OPTION){
            Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
            if (prop.eliminar()!=0){
                JOptionPane.showMessageDialog(this.getContentPane(),"El propietario fue eliminado correctamente");
                this.actualizar();
            } else{
                JOptionPane.showMessageDialog(this.getContentPane(),"Ocurrió un problema, por favor intente nuevamente");
            }
        }
    }//GEN-LAST:event_jBtn_Eliminar2ActionPerformed

    private void jBtn_ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ListarActionPerformed
        InformeListadoPropietarios ilp=new InformeListadoPropietarios();
        Map param=new HashMap();
        String nombre=new String("");
        String cuit=new String("");
        long uni_codigo=0;
        long con_codigo=0;
        if(this.jChk_Nombre.isSelected()){
            if(this.jTxt_Nombre.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el nombre del propietario");
                this.jTxt_Nombre.requestFocus();
            }
        }
      
        if(this.jChk_Cuit.isSelected()){
            if (this.jFTxt_Cuit.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el cuit del propietario");
                this.jFTxt_Cuit.requestFocus();
            }
        }
        
        if(this.jChk_Cosorcio.isSelected()){
            if(this.jCmb_Consorcio.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un consorcio");
                this.jCmb_Consorcio.requestFocus();
            }
        }
        if(this.jChk_Unidad.isSelected()){
            if(this.jCmb_Unidad.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar una unidad");
                this.jCmb_Unidad.requestFocus();
            }
        }
        if (this.jChk_Nombre.isSelected()){
            nombre=this.jTxt_Nombre.getText();
        }
        if(this.jChk_Cuit.isSelected()){
            cuit=this.jFTxt_Cuit.getText().replace("-","").trim();
        }
        if(this.jChk_Cosorcio.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
        }
        if(this.jChk_Unidad.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
            uni_codigo=((Unidad)((ComboUnidadesModel)this.jCmb_Unidad.getModel()).getLst().get(this.jCmb_Unidad.getSelectedIndex())).getCodigo();
        }
        param.put("nombre","%"+nombre+"%");
        param.put("cuit","%"+cuit+"%");
        param.put("consorcio_id",String.valueOf(con_codigo));
        param.put("unidad_id",String.valueOf(uni_codigo));
        ilp.setParameters(param);
        ilp.displayReport();
    }//GEN-LAST:event_jBtn_ListarActionPerformed

    private void jBtn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ModificarActionPerformed
        if (this.jTlb_Resultados.getSelectedRow()==-1){
           JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un propietario previamente");
           return;
        }
        Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
        AltaPropietarios ap=new AltaPropietarios(prop,this);
        ap.setVisible(true);
    }//GEN-LAST:event_jBtn_ModificarActionPerformed

    private void jBtn_ConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ConsultarActionPerformed

        String nombre=new String("");
        String cuit=new String("");
        long uni_codigo=0;
        long con_codigo=0;
        if(this.jChk_Nombre.isSelected()){
            if(this.jTxt_Nombre.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el nombre del propietario");
                this.jTxt_Nombre.requestFocus();
            }
        }
      
        if(this.jChk_Cuit.isSelected()){
            if (this.jFTxt_Cuit.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el cuit del propietario");
                this.jFTxt_Cuit.requestFocus();
            }
        }
        
        if(this.jChk_Cosorcio.isSelected()){
            if(this.jCmb_Consorcio.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un consorcio");
                this.jCmb_Consorcio.requestFocus();
            }
        }
        if(this.jChk_Unidad.isSelected()){
            if(this.jCmb_Unidad.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar una unidad");
                this.jCmb_Unidad.requestFocus();
            }
        }
        if (this.jChk_Nombre.isSelected()){
            nombre=this.jTxt_Nombre.getText();
        }
        if(this.jChk_Cuit.isSelected()){
            cuit=this.jFTxt_Cuit.getText().replace("-","").trim();
        }
        if(this.jChk_Cosorcio.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
        }
        if(this.jChk_Unidad.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
            uni_codigo=((Unidad)((ComboUnidadesModel)this.jCmb_Unidad.getModel()).getLst().get(this.jCmb_Unidad.getSelectedIndex())).getCodigo();
        }
        Propietario prop=new Propietario();
        List lst=prop.buscarxCriteria(nombre,cuit,con_codigo,uni_codigo);
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(lst);
        this.jTlb_Resultados.updateUI();

    }//GEN-LAST:event_jBtn_ConsultarActionPerformed

    private void jCmb_UnidadPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jCmb_UnidadPopupMenuWillBecomeVisible
        this.searchCombo(this.jCmb_Unidad);
    }//GEN-LAST:event_jCmb_UnidadPopupMenuWillBecomeVisible

    private void jCmb_UnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmb_UnidadActionPerformed
        ((JTextField)this.jCmb_Unidad.getEditor().getEditorComponent()).setText(((String)this.jCmb_Unidad.getSelectedItem()));        
    }//GEN-LAST:event_jCmb_UnidadActionPerformed

    private void jCmb_ConsorcioPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jCmb_ConsorcioPopupMenuWillBecomeVisible
        this.searchCombo(this.jCmb_Consorcio);
    }//GEN-LAST:event_jCmb_ConsorcioPopupMenuWillBecomeVisible

    private void jCmb_ConsorcioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmb_ConsorcioActionPerformed
        ((JTextField)this.jCmb_Consorcio.getEditor().getEditorComponent()).setText(((String)this.jCmb_Consorcio.getSelectedItem()));        
    }//GEN-LAST:event_jCmb_ConsorcioActionPerformed

    private void jBtn_PopularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_PopularActionPerformed
        Consorcio cons=new Consorcio();
        if (this.jCmb_Consorcio.getSelectedIndex()!=-1){
            cons=(Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex());
            Unidad uni=new Unidad();
            ((ComboUnidadesModel)this.jCmb_Unidad.getModel()).setLst(uni.buscarxConsorcioActivas(cons));
            this.jCmb_Unidad.setSelectedIndex(-1);
            this.jCmb_Unidad.updateUI();
        }
    }//GEN-LAST:event_jBtn_PopularActionPerformed

    private void jChk_UnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_UnidadActionPerformed
        if(this.jChk_Unidad.isSelected()){
            this.jCmb_Unidad.setEnabled(true);
            this.jCmb_Consorcio.setEnabled(true);
            this.jBtn_Popular.setEnabled(true);
            this.jCmb_Unidad.requestFocus();
        }
        else{
            this.jCmb_Unidad.setEnabled(false);
            this.jBtn_Popular.setEnabled(false);
            this.jCmb_Unidad.setSelectedIndex(-1);
            if(!this.jChk_Cosorcio.isSelected()){
                this.jCmb_Consorcio.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jChk_UnidadActionPerformed

    private void jChk_CosorcioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_CosorcioActionPerformed
        if(this.jChk_Cosorcio.isSelected()){
            this.jCmb_Consorcio.setEnabled(true);
            this.jCmb_Consorcio.requestFocus();
        }
        else{
            if (!this.jChk_Unidad.isSelected()){
                this.jCmb_Consorcio.setEnabled(false);
                this.jCmb_Consorcio.setSelectedIndex(-1);
            }
        }
    }//GEN-LAST:event_jChk_CosorcioActionPerformed

    private void jChk_CuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_CuitActionPerformed
        if(this.jChk_Cuit.isSelected()){
            this.jFTxt_Cuit.setEnabled(true);
            this.jFTxt_Cuit.requestFocus();
        }
        else{
            this.jFTxt_Cuit.setEnabled(false);
            this.jFTxt_Cuit.setText("");
        }
    }//GEN-LAST:event_jChk_CuitActionPerformed

    private void jChk_NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChk_NombreActionPerformed
        if (this.jChk_Nombre.isSelected()){
            this.jTxt_Nombre.setEnabled(true);
            this.jTxt_Nombre.requestFocus();
        }
        else{
            this.jTxt_Nombre.setEnabled(false);
            this.jTxt_Nombre.setText("");
        }
    }//GEN-LAST:event_jChk_NombreActionPerformed

    private void jBtn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_SalirActionPerformed
        this.setVisible(false);
        if (this.getCc()!=null){
            if (this.jTlb_Resultados.getSelectedRow()!=-1){
                Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
                this.getCc().setPropietario(prop);
                this.getCc().setearPropietario(prop.getNombre());    
            }
        }
        if(this.getCp()!=null){
            if (this.jTlb_Resultados.getSelectedRow()!=-1){
                Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
                this.getCp().setPropietario(prop);
                this.getCp().setearPropietario(prop.getNombre());    
            }
            
        }
        if(this.getIg()!=null){
            if (this.jTlb_Resultados.getSelectedRow()!=-1){
                Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
                this.getIg().setPropietario(prop);
                this.getIg().setearPropietario(prop.getNombre());    
            }
        }
        if (this.getLec()!=null){
            if (this.jTlb_Resultados.getSelectedRow()!=-1){
                Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
                this.getLec().setPropietario(prop);
                this.getLec().setearPropietario(prop.getNombre());    
            }
        }
        if (this.getIii()!=null){
            if (this.jTlb_Resultados.getSelectedRow()!=-1){
                Propietario prop=(Propietario)((TableResultadosModel)this.jTlb_Resultados.getModel()).getLst().get(this.jTlb_Resultados.getSelectedRow());
                this.getIii().setPropietario(prop);
                this.getIii().setearPropietario(prop.getNombre());    
            }
        }
        this.dispose();
    }//GEN-LAST:event_jBtn_SalirActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPropietario().setVisible(true);
            }
        });
    }
    
    // Declaración de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Consultar;
    private javax.swing.JButton jBtn_Eliminar;
    private javax.swing.JButton jBtn_Eliminar1;
    private javax.swing.JButton jBtn_Eliminar2;
    private javax.swing.JButton jBtn_Listar;
    private javax.swing.JButton jBtn_Modificar;
    private javax.swing.JButton jBtn_Popular;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JCheckBox jChk_Cosorcio;
    private javax.swing.JCheckBox jChk_Cuit;
    private javax.swing.JCheckBox jChk_Nombre;
    private javax.swing.JCheckBox jChk_Unidad;
    private javax.swing.JComboBox jCmb_Consorcio;
    private javax.swing.JComboBox jCmb_Unidad;
    private javax.swing.JFormattedTextField jFTxt_Cuit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTlb_Resultados;
    private javax.swing.JTextField jTxt_Nombre;
    // Fin de declaración de variables//GEN-END:variables
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
    
    class ComboUnidadesModel implements ComboBoxModel{
        
        Unidad uni=new Unidad();
        protected Object selected;
        private List lst;
         
        ComboUnidadesModel(){
            lst=new ArrayList();
        }
        public void setSelectedItem(Object item) {
               this.selected=item;
        }
	public Object getSelectedItem() {
		return this.selected;
	}

	public String getElementAt(int index) {
                Unidad uni=new Unidad();
                uni=(Unidad)getLst().get(index);
		return uni.getConsorcio().getDenominacion() + "-" + uni.getDescripcion();
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
      

      
    public void searchCombo(JComboBox jc){   
        String st=((JTextField)jc.getEditor().getEditorComponent()).getText();
        for(int i=0;i<jc.getModel().getSize();i++)
        {
           if(((String)jc.getItemAt(i)).contains(st)){
           jc.setSelectedIndex(i); 
           ((JTextField)jc.getEditor().getEditorComponent()).setText((String)jc.getSelectedItem());
           }
                  
        }
   }
 
    
    class TableResultadosModel implements TableModel{
        
        Propietario prop= new Propietario();
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
            return 5;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex){
                case 0: 
                    return "Nombre";
                case 1: 
                    return "Domicilio";
                case 2: 
                    return "Localidad";
                case 3: 
                    return "Telefono";
                case 4: 
                    return "CUIT";
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
            switch(columnIndex){
                case 0:
                    return ((Propietario)this.getLst().get(rowIndex)).getNombre();
                case 1:
                    return ((Propietario)this.getLst().get(rowIndex)).getDomicilio();
                case 2:
                    return ((Propietario)this.getLst().get(rowIndex)).getLocalidad();
                case 3:
                    return ((Propietario)this.getLst().get(rowIndex)).getTelefono();
                case 4:
                    if (((Propietario)this.getLst().get(rowIndex)).getCuit()==0){
                        return "";
                    }
                    else{
                        return ((Propietario)this.getLst().get(rowIndex)).getCuit();
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
        String nombre=new String("");
        String cuit=new String("");
        long uni_codigo=0;
        long con_codigo=0;
        if(this.jChk_Nombre.isSelected()){
            if(this.jTxt_Nombre.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el nombre del propietario");
                this.jTxt_Nombre.requestFocus();
            }
        }
      
        if(this.jChk_Cuit.isSelected()){
            if (this.jFTxt_Cuit.getText().length()==0){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe ingresar el cuit del propietario");
                this.jFTxt_Cuit.requestFocus();
            }
        }
        
        if(this.jChk_Cosorcio.isSelected()){
            if(this.jCmb_Consorcio.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un consorcio");
                this.jCmb_Consorcio.requestFocus();
            }
        }
        if(this.jChk_Unidad.isSelected()){
            if(this.jCmb_Unidad.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar una unidad");
                this.jCmb_Unidad.requestFocus();
            }
        }
        if (this.jChk_Nombre.isSelected()){
            nombre=this.jTxt_Nombre.getText();
        }
        if(this.jChk_Cuit.isSelected()){
            cuit=this.jFTxt_Cuit.getText().replace("-","").trim();
        }
        if(this.jChk_Cosorcio.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
        }
        if(this.jChk_Unidad.isSelected()){
            con_codigo=((Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex())).getCodigo();
            uni_codigo=((Unidad)((ComboUnidadesModel)this.jCmb_Unidad.getModel()).getLst().get(this.jCmb_Unidad.getSelectedIndex())).getCodigo();
        }
        Propietario prop=new Propietario();
        List lst=prop.buscarxCriteria(nombre,cuit,con_codigo,uni_codigo);
        ((TableResultadosModel)this.jTlb_Resultados.getModel()).setLst(lst);
        this.jTlb_Resultados.updateUI();
   }

    public ConsultaCuentaCorriente getCc() {
        return cc;
    }

    public void setCc(ConsultaCuentaCorriente cc) {
        this.cc = cc;
    }

    public ConsultaPagos getCp() {
        return cp;
    }

    public void setCp(ConsultaPagos cp) {
        this.cp = cp;
    }

    public ImpIngresosBrutos getIg() {
        return ig;
    }

    public void setIg(ImpIngresosBrutos ig) {
        this.ig = ig;
    }
      public Map getObjectsAffected() {
        Map a=new HashMap();
        a.put(this.jBtn_Listar.getName(),this.jBtn_Listar);
        a.put(this.jBtn_Modificar.getName(),this.jBtn_Modificar);
        a.put(this.jBtn_Eliminar2.getName(),this.jBtn_Eliminar2);
        return a;
    }

    public String getUIName() {
        return "ConsultaPropietario";
    }

    public ListadoExpensasComunes getLec() {
        return lec;
    }

    public void setLec(ListadoExpensasComunes lec) {
        this.lec = lec;
    }

    public InformeAnexoIGUI getIii() {
        return iii;
    }

    public void setIii(InformeAnexoIGUI iii) {
        this.iii = iii;
    }
    public void sizeTable()
    {
        this.jTlb_Resultados.getColumnModel().getColumn(0).setPreferredWidth(158);
        this.jTlb_Resultados.getColumnModel().getColumn(1).setPreferredWidth(179);
        this.jTlb_Resultados.getColumnModel().getColumn(2).setPreferredWidth(58);
        this.jTlb_Resultados.getColumnModel().getColumn(3).setPreferredWidth(60);
        this.jTlb_Resultados.getColumnModel().getColumn(4).setPreferredWidth(83);
    }
}
