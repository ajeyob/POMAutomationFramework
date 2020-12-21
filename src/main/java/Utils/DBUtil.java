package Utils;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
   private  CommonUtilities comUtils;
   private OracleDataSource ods;
   private Properties properties;
   private Connection connection;
   private Statement statement;

   public  DBUtil() throws SQLException {
       comUtils=new CommonUtilities();
       ods= new OracleDataSource();
       properties=comUtils.loadPropertiesFileInConfigFolder("config.properties");
       ods.setURL(properties.getProperty("DBUrl"));
       ods.setUser(properties.getProperty("DBUserId"));
       ods.setPassword(properties.getProperty("DBPassword"));


   }

   public List<ArrayList<String>>  getEntireResultSet(String sqlQuery){
      List<ArrayList<String>> entireDBresultOutput=new ArrayList<ArrayList<String>>();
      ArrayList<String> tempRowAsList=new ArrayList<String>();
      try{
         connection=ods.getConnection();
         statement=connection.createStatement();
         ResultSet resultSet= statement.executeQuery(sqlQuery);
         ResultSetMetaData rsmd= resultSet.getMetaData();
         int colCount=rsmd.getColumnCount();
         while(resultSet.next()){
            for(int i=0;i<colCount;i++){
               tempRowAsList.add(resultSet.getString(i));
            }
            entireDBresultOutput.add(tempRowAsList);
         }
         connection.close();

      }catch (Exception e){
         e.printStackTrace();
      }
      return entireDBresultOutput;
   }
   public List<String> getResutBycolumnName(String sqlQuery, String colName){
      List<String> getColumnDetails=new ArrayList<String>();
      try{
         connection=ods.getConnection();
         statement=connection.createStatement();
         ResultSet resultSet= statement.executeQuery(sqlQuery);
         ResultSetMetaData rsmd= resultSet.getMetaData();
         int colCount=rsmd.getColumnCount();
        while(resultSet.next()){
           for(int i=0;i<colCount;i++){
              if(rsmd.getColumnName(i).equalsIgnoreCase(colName)){
                 getColumnDetails.add(resultSet.getString(i));
              }
           }
        }
         connection.close();

      }catch (Exception e){
         e.printStackTrace();
      }
      return getColumnDetails;
   }

}


