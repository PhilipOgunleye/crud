/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolclass;

import com.sun.istack.internal.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author philip
 */
public class credentials {
    public static Connection getConnection(){
        
        
        Connection conn = null;
        try{
        Class.forName("com.mysql.jdbc.Driver");
        String jdbc = "jdbc:mysql://localhost/DatabaseName";
        String user="root";
                String pass="";
                conn=(Connection) DriverManager.getConnection(jdbc, user, pass);
        }catch(ClassNotFoundException | SQLException ex){
            java.util.logging.Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return conn;
    }
    
}
