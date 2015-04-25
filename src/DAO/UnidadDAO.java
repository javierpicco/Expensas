/*
 * UnidadDAO.java
 *
 * Created on 11 de enero de 2008, 6:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Coeficiente;
import model.CoeficientesAsignados;
import model.Consorcio;
import model.Inquilino;
import model.Propietario;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class UnidadDAO {
    
   
    private ConnectionSing cs;
    /** Creates a new instance of UnidadDAO */
    public UnidadDAO() {
        this.setCon(cs.getConection());
        
    }
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
     public Unidad guardar(Unidad uni){
        String mysql_var="insert into unidad(uni_descripcion,uni_con_codigo) values(?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,uni.getDescripcion());
            st.setInt(2,uni.getConsorcio().getCodigo());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                uni.setCodigo(rs.getInt(1));
            }
            return uni;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
     
    public List buscarxConsorcioNoAlocadas(Consorcio cons){
        List uni_list=new ArrayList();
        String mysql_var="SELECT * FROM unidad WHERE uni_con_codigo=? AND uni_fecha_baja IS NULL AND uni_prop_codigo IS NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Unidad uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                uni.setConsorcio(cons);
                uni.setDescripcion(rs.getString("uni_descripcion"));
                uni_list.add(uni); 
            }
            return uni_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public int modificar(Unidad uni){
        String mysql_var="UPDATE unidad SET uni_descripcion=?,uni_con_codigo=? WHERE uni_codigo=?";
        PreparedStatement st;
        System.out.print(mysql_var);
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,uni.getDescripcion());
            st.setLong(2,uni.getConsorcio().getCodigo());
            st.setLong(3,uni.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

    public int eliminar(Unidad uni){
        String mysql_var="UPDATE unidad SET uni_fecha_baja=now() WHERE uni_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,uni.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int activar(Unidad uni){
        String mysql_var="UPDATE unidad SET uni_fecha_baja=null WHERE uni_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,uni.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
     public List buscarxConsorcioNoAlquiladas(Consorcio cons){
        List uni_list=new ArrayList();
        String mysql_var="SELECT * FROM unidad WHERE uni_con_codigo=? AND uni_fecha_baja IS NULL AND uni_inq_codigo IS NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Unidad uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                uni.setConsorcio(cons);
                uni.setDescripcion(rs.getString("uni_descripcion"));
                uni_list.add(uni); 
            }
            return uni_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarxConsorcioActivas(Consorcio cons){
        List uni_list=new ArrayList();
        String mysql_var="SELECT * FROM unidad WHERE uni_con_codigo=? AND uni_fecha_baja IS NULL AND uni_prop_codigo IS NOT NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Unidad uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                uni.setConsorcio(cons);
                uni.setDescripcion(rs.getString("uni_descripcion"));
                Propietario prop=new Propietario();
                uni.setPropietario(prop.buscarxCodigo(rs.getLong("uni_prop_codigo")));
                Inquilino inq = new Inquilino();
                if (rs.getLong("uni_inq_codigo")!=0){
                    uni.setInquilino(inq.buscarxCodigo(rs.getLong("uni_inq_codigo")));      
                }
                uni_list.add(uni); 
            }
            return uni_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarxPropietario(Propietario prop){
        List uni_list=new ArrayList();
        String mysql_var="SELECT * FROM unidad WHERE uni_prop_codigo = ? AND uni_fecha_baja IS NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,prop.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Unidad uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                Consorcio cons=new Consorcio();
                uni.setConsorcio(cons.buscarxCodigo(rs.getLong("uni_con_codigo")));
                uni.setDescripcion(rs.getString("uni_descripcion"));
                uni_list.add(uni); 
            }
            return uni_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
        public List buscarxInquilino(Inquilino inq){
        List uni_list=new ArrayList();
        String mysql_var="SELECT * FROM unidad WHERE uni_inq_codigo = ? AND uni_fecha_baja IS NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,inq.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Unidad uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                Consorcio cons=new Consorcio();
                uni.setConsorcio(cons.buscarxCodigo(rs.getLong("uni_con_codigo")));
                uni.setDescripcion(rs.getString("uni_descripcion"));
                uni_list.add(uni); 
            }
            return uni_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
     public Unidad buscarxCodigo(long codigo){
        Unidad uni=null;
        String mysql_var="SELECT * FROM unidad WHERE uni_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                Consorcio cons=new Consorcio();
                uni.setConsorcio(cons.buscarxCodigo(rs.getLong("uni_con_codigo")));
                uni.setDescripcion(rs.getString("uni_descripcion"));
                Propietario prop=new Propietario();
                uni.setPropietario(prop.buscarxCodigo(rs.getLong("uni_prop_codigo")));
            }
            return uni;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
     public List buscarxCriteria(Consorcio cons,String unidad,String propietario, String inquilino){
        Unidad uni=null;
        List result=new ArrayList();
        StringBuffer mysql_var=new StringBuffer("SELECT DISTINCT(uni.uni_codigo),uni_descripcion,uni_con_codigo,uni_fecha_baja,uni_prop_codigo,uni_inq_codigo FROM unidad uni, propietario prop,inquilino inq, consorcio cons WHERE uni.uni_con_codigo=cons.con_codigo"); 
        if (cons!=null){
            mysql_var.append(" AND uni.uni_con_codigo="+cons.getCodigo());
        }
        else{
            mysql_var.append(" AND uni.uni_con_codigo=uni.uni_con_codigo");
        }
       
        mysql_var.append(" AND uni.uni_descripcion like '%"+unidad+"%'");
        
        if (propietario.trim().length()!=0){
            mysql_var.append(" AND uni.uni_prop_codigo = prop.prop_codigo");    
            mysql_var.append(" AND prop.prop_nombre like '%"+propietario+"%'");
        }
        if (inquilino.trim().length()!=0){
            mysql_var.append(" AND uni.uni_inq_codigo=inq.inq_codigo");     
            mysql_var.append(" AND inq.inq_nombre like '%"+inquilino+"%'");    
        }
        mysql_var.append(" ORDER BY cons.con_denominacion");    
        System.out.print(mysql_var.toString());
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                uni=new Unidad();
                uni.setCodigo(rs.getInt("uni_codigo"));
                Consorcio consor=new Consorcio();
                uni.setConsorcio(consor.buscarxCodigo(rs.getLong("uni_con_codigo")));
                uni.setDescripcion(rs.getString("uni_descripcion"));
                Propietario prop=new Propietario();
                uni.setPropietario(prop.buscarxCodigo(rs.getLong("uni_prop_codigo")));
                Inquilino inq = new Inquilino();
                uni.setInquilino(inq.buscarxCodigo(rs.getLong("uni_inq_codigo")));  
                uni.setFechabaja(rs.getDate("uni_fecha_baja"));
                result.add(uni);
            }
            return result;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    public void popularCoeficientes(Unidad uni,Coeficiente coe){
        List uni_list=new ArrayList();
        StringBuffer sb=new StringBuffer();
        sb.append("SELECT * FROM coeficientexunidad WHERE coeuni_uni_codigo=?");
        PreparedStatement st;
        try {
           if (coe!=null){
                sb.append(" AND coeuni_coe_codigo=?");
            }
            st = con.prepareStatement(sb.toString());
            st.setLong(1,uni.getCodigo());
            if (coe!=null){
                st.setLong(2,coe.getCodigo());
            }
            ResultSet rs= st.executeQuery();
            if (rs.next()==false){
                CoeficientesAsignados coeas=new CoeficientesAsignados();
                coeas.setCoeficiente(coe);
                coeas.setValor(0d);
                uni_list.add(coeas); 
            }
            rs.previous();
            while (rs.next()){
                CoeficientesAsignados coeas=new CoeficientesAsignados();
                Coeficiente coef=new Coeficiente();
                coeas.setCodigo(rs.getLong("coeuni_codigo"));
                coeas.setCoeficiente(coef.buscarCodigo(rs.getLong("coeuni_coe_codigo")));
                coeas.setValor(rs.getDouble("coeuni_porcentaje"));
                uni_list.add(coeas); 
            }
            uni.setCoeficientesAsignados(uni_list);
        }
        catch (SQLException ex) {
             ex.printStackTrace();

        }
    }
    
    public Unidad actualizarPropietario(long prop_codigo,Unidad uni){
        String mysql_var="UPDATE unidad SET uni_prop_codigo=? WHERE uni_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,prop_codigo);
            st.setInt(2,uni.getCodigo());
            st.executeUpdate();
            return uni;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public void guardarCoeficientesAsignados(Unidad uni)
    {
        String mysql_var=new String();
        try {
        PreparedStatement st = null;  
        int bandera=0;
        int entra=0;
            if (((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getCodigo()!=0){
                mysql_var="UPDATE coeficientexunidad SET coeuni_porcentaje=? WHERE coeuni_codigo=?";
                st = con.prepareStatement(mysql_var);
                st.setDouble(1,((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getValor());
                st.setLong(2,((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getCodigo());
                entra=1;
            }
              
            if (((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getValor()!=0d && ((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getCodigo()==0){
                mysql_var="insert into coeficientexunidad(coeuni_coe_codigo, coeuni_uni_codigo,coeuni_porcentaje) values (?, ?,?)";
                st = con.prepareStatement(mysql_var);
                st.setLong(1,((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getCoeficiente().getCodigo());
                st.setLong(2,uni.getCodigo());
                st.setDouble(3,((CoeficientesAsignados)uni.getCoeficientesAsignados().get(0)).getValor());               
                bandera=1;
                entra=1;
            }
            if (entra==0){
                return;
            }
            st.executeUpdate();
            if (bandera==1){
                ResultSet rs= st.getGeneratedKeys();
                while (rs.next()){
                    uni.setCodigo(rs.getInt(1));
                }
            }
        }
        catch (SQLException ex) {
                ex.printStackTrace();
            }
            
    }
}
