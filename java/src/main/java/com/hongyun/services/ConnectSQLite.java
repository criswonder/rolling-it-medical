package com.hongyun.services;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement;  
  
/** 
 * @author www.javaworkspace.com 
 *  
 */  
public class ConnectSQLite {  
    public static void main(String[] args) {  
  
        Connection connection = null;  
        ResultSet resultSet = null;  
        Statement statement = null;  
  
        try {  
            Class.forName("org.sqlite.JDBC");  
            connection = DriverManager  
                    .getConnection("jdbc:sqlite:E:\\work\\tools\\sqllite\\rolling.db");  
            statement = connection.createStatement();  
            resultSet = statement  
                    .executeQuery("SELECT EMPNAME FROM EMPLOYEEDETAILS");  
            while (resultSet.next()) {  
                System.out.println("EMPLOYEE NAME:"  
                        + resultSet.getString("EMPNAME"));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                resultSet.close();  
                statement.close();  
                connection.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  