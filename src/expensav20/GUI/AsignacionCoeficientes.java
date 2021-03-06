/*
 * AsignacionCoeficientes.java
 *
 * Created on 21 de diciembre de 2007, 7:17
 */

package expensav20.GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.NullCipher;
import javax.swing.ComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import model.Coeficiente;
import model.CoeficientesAsignados;
import model.Consorcio;
import model.Unidad;

/**
 *
 * @author  Javier
 */
public class AsignacionCoeficientes extends javax.swing.JFrame {
    
    /** Creates new form AsignacionCoeficientes */
    public AsignacionCoeficientes() {
        initComponents();
        this.jCmb_Consorcio.setModel(new ComboConsorciosModel());
        this.jCmb_Consorcio.updateUI();
        this.jCmb_Coeficiente.setModel(new ComboCoeficientesModel());
        this.jCmb_Coeficiente.updateUI();
        this.jTbl_Porcentajes.setModel(new TableCoeAsignadosModel());
        this.jTbl_Porcentajes.updateUI();
        
    }
    
    private Double suma;
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCmb_Consorcio = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbl_Porcentajes = new javax.swing.JTable();
        jBtn_Consultar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCmb_Coeficiente = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTxt_Total = new javax.swing.JTextField();
        jBtn_Salir = new javax.swing.JButton();
        jBtn_Aceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asignaci\u00f3n Coeficientes");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Coeficientes"));
        jLabel1.setText("Consorcio:");

        jCmb_Consorcio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Edificio Alegro" }));

        jTbl_Porcentajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Local 1", "25"},
                {"Local 2", "25"},
                {"Local 3", "25"},
                {"Local 4", "25"}
            },
            new String [] {
                "Unidad", "% Asignado"
            }
        ));
        jTbl_Porcentajes.setEditingColumn(1);
        jScrollPane1.setViewportView(jTbl_Porcentajes);

        jBtn_Consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/zoom.png")));
        jBtn_Consultar.setText("Consultar");
        jBtn_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_ConsultarActionPerformed(evt);
            }
        });

        jLabel3.setText("Coefic.:");

        jCmb_Coeficiente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Coeficiente A", "Coeficiente B", "Coeficiente C" }));

        jLabel2.setText("Total:");

        jTxt_Total.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCmb_Consorcio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCmb_Coeficiente, 0, 242, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtn_Consultar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCmb_Consorcio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmb_Coeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jBtn_Consultar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jBtn_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expensav20/GUI/Icons/door_out.png")));
        jBtn_Salir.setText("Salir");
        jBtn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_SalirActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(258, Short.MAX_VALUE)
                .addComponent(jBtn_Aceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtn_Salir)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_Salir)
                    .addComponent(jBtn_Aceptar))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_AceptarActionPerformed
        /*if(Double.valueOf(this.jTxt_Total.getText().replace(",","."))>100){
            JOptionPane.showMessageDialog(this.getContentPane(),"La asignaci�n de coeficientes no debe ser mayor al 100%");
            return;
        }*/
        Iterator i = ((TableCoeAsignadosModel)this.jTbl_Porcentajes.getModel()).getLst().iterator();
        while(i.hasNext()){
            Unidad uni=(Unidad)i.next();
           //((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).setValor(((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getValor()*100);
            uni.guardarCoeficientesAsignados();
        }
        JOptionPane.showMessageDialog(this.getContentPane(),"Los coeficientes se guardaron correctamente");
    }//GEN-LAST:event_jBtn_AceptarActionPerformed

    private void jBtn_ConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_ConsultarActionPerformed
        if (this.jCmb_Consorcio.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un consorcio");
            this.jCmb_Consorcio.requestFocus();
            return;
        }
        if(this.jCmb_Coeficiente.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(this.getContentPane(),"Debe seleccionar un coeficiente");
            this.jCmb_Coeficiente.requestFocus();
            return;
        }
        Unidad uni = new Unidad();
        Consorcio cons=new Consorcio();
        cons=(Consorcio)((ComboConsorciosModel)this.jCmb_Consorcio.getModel()).getLst().get(this.jCmb_Consorcio.getSelectedIndex());
        List unidades= uni.buscarxConsorcioActivas(cons);
        Iterator i = unidades.iterator();
        
        while(i.hasNext()){
            ((Unidad)i.next()).popularCoeficientes((Coeficiente)((ComboCoeficientesModel)this.jCmb_Coeficiente.getModel()).getLst().get(this.jCmb_Coeficiente.getSelectedIndex()));
        }
        ((TableCoeAsignadosModel)this.jTbl_Porcentajes.getModel()).setLst(unidades);
        this.jTbl_Porcentajes.updateUI();
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
                new AsignacionCoeficientes().setVisible(true);
            }
        });
    }
    
    // Declaraci�n de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jBtn_Aceptar;
    private javax.swing.JButton jBtn_Consultar;
    private javax.swing.JButton jBtn_Salir;
    private javax.swing.JComboBox jCmb_Coeficiente;
    private javax.swing.JComboBox jCmb_Consorcio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbl_Porcentajes;
    private javax.swing.JTextField jTxt_Total;
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
    
    class ComboCoeficientesModel implements ComboBoxModel{
        
        Coeficiente coe=new Coeficiente();
        protected Object selected;
        private List lst;
         
        ComboCoeficientesModel(){
            this.setLst(coe.buscarDistribuiblesActivos());
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
    
    class TableCoeAsignadosModel implements TableModel{
        
        Unidad uni=new Unidad();     
        protected Object selected;
        private List lst=new ArrayList();
        private Double sumaint;
         
        TableCoeAsignadosModel(){
           
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
                    return "Unidad";
                case 1: 
                    return "Porcentaje";
            }
            return "";
        }
        public Class<?> getColumnClass(int columnIndex) {
            return  getValueAt(0, columnIndex).getClass();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if(columnIndex==1){
                return true;
            }
            else{
                return false;
            }
        }
       

        public Object getValueAt(int rowIndex, int columnIndex) {
            NumberFormat nf = null;
            nf=NumberFormat.getInstance();
            nf.setMaximumFractionDigits(7);
            switch(columnIndex){
                case 0:
                    return ((Unidad)this.getLst().get(rowIndex)).getDescripcion();
                case 1:
                    sumar();
                    return nf.format((((CoeficientesAsignados)((Unidad)this.getLst().get(rowIndex)).getCoeficientesAsignados().get(0)).getValor()));
                    
            }
            return null;
        }
        

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            
            Unidad uni = (Unidad)this.getLst().get(rowIndex);
            switch (columnIndex) {
                case 0: //Name
                uni.setDescripcion((String)aValue);
                break;
            case 1: //value
                Double value=null;
                if (aValue.toString().trim().length()>0){
                    value = Double.valueOf(aValue.toString());
                }
                else{
                    value=0d;
                }
                NumberFormat nf = null;
                nf=NumberFormat.getInstance();
                nf.setMaximumFractionDigits(7);
                String var2= nf.format(value.doubleValue());
                ((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).setValor(Double.valueOf(var2.replace(",",".")));
                this.sumar();
                break;
    
           }
        }
        private void sumar(){
            this.sumaint=0d;
            Iterator it = this.getLst().iterator();
            NumberFormat nf = null;
            nf=NumberFormat.getInstance();
            nf.setMaximumFractionDigits(7);
            while (it.hasNext()){
                Unidad uni=new Unidad();
                Double doub=((CoeficientesAsignados)((Unidad)it.next()).getCoeficientesAsignados().get(0)).getValor();
                this.sumaint=sumaint+Double.valueOf(nf.format(doub.doubleValue()).replace(",","."));
            }
            
            jTxt_Total.setText(nf.format(this.sumaint.doubleValue()));
            
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }

        public Double getSumaint() {
            return sumaint;
        }

        public void setSumaint(Double sumaint) {
            this.sumaint = sumaint;
        }
    }

}
