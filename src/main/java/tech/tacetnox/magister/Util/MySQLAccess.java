package tech.tacetnox.magister.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepStatement = null;
    private ResultSet resultSet = null;
    private String SQLPASS = "FILLFORSHADOW";

    public void connect() throws Exception {

        try {
            //Load connection Driver for MySQL
            Class.forName("com.mysql.jdbc.Driver");
            //Setup Connection
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tritemare?user=tacet&password="+SQLPASS);

        } catch(Exception e){
            e.getMessage();
        }finally {
            close();
        }



    }

    private String getAPI0Auth() throws SQLException {
        return "";
    }

    private String getAPIClientID() throws SQLException {
        return "";
    }

    private String getLogin0Auth() throws SQLException {
        return ""
    }

    private String readKnightName(ResultSet resultSet) throws SQLException {
        //ResultSet begins BEFORE the first element, making it easier to loop through from the beginning
        while(resultSet.next()){

        }
        return "";
    }


    private void close(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }


}
