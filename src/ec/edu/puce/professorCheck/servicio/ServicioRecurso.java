/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.servicio;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

/**
 *
 * @author cristian
 */
@Stateless(name = "ServicioRecurso")
@LocalBean
public class ServicioRecurso {
    
    @Resource(mappedName = "java:jboss/datasources/psicoDS")
    private DataSource dataSource;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Connection obtenerConnection() throws SQLException {
    	Connection con = dataSource.getConnection();
    	con.setAutoCommit(false);
        return con;
    }
    
}
