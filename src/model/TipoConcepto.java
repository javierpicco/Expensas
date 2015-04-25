/*
 * TipoConcepto.java
 *
 * Created on 15 de marzo de 2008, 9:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.TipoConceptoDAO;
import java.util.List;

/**
 *
 * @author Javier
 */
public class TipoConcepto {
    
    /** Creates a new instance of TipoConcepto */
    public TipoConcepto() {
    }
    private long codigo;
    private String descripcion;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List buscarTodos(){
         TipoConceptoDAO tcd=new TipoConceptoDAO();
         return tcd.buscarTodos();
     }
    
    public TipoConcepto buscarxId(long codigo){
         TipoConceptoDAO tcd=new TipoConceptoDAO();
         return tcd.buscarxId(codigo);
    }
}
