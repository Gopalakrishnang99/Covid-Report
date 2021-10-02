package data;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {
   private static ConnectionPool dataSource;
   private ComboPooledDataSource comboPooledDataSource;

   private ConnectionPool() {
      try {
         comboPooledDataSource = new ComboPooledDataSource();
         comboPooledDataSource
            .setDriverClass("com.mysql.jdbc.Driver");
         comboPooledDataSource
            .setJdbcUrl("jdbc:mysql://localhost:3306/covidapp");
         comboPooledDataSource.setUser("covid");
         comboPooledDataSource.setPassword("gotohell13!!");}
      catch (PropertyVetoException ex1) {
         ex1.printStackTrace();
      	}
      
   }

   public static ConnectionPool getInstance() {
      if (dataSource == null)
         dataSource = new ConnectionPool();
      return dataSource;
   }

   public Connection getConnection() {
      Connection con = null;
      try {
         con = comboPooledDataSource.getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return con;
   }
}