/*
 * DnDTest14.java
 *
 * Created on March 10, 2007, 12:51 PM
 */

package test;

/**
 *
 * @author  werni
 */
public class DnDTest14 extends javax.swing.JPanel {
    
    /**
     * Creates new form DnDTest14
     */
    public DnDTest14() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Nelly Furtado", "Loose", null, null},
                {"Avril Lavigne", "Under My Skin", null, null},
                {"Jem", "Finally Woken", null, null},
                {"Kim Wilde", "Never Say Never", null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setDragEnabled(true);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    
}
