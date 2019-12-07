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
    private String SQLPASS = "FILLFORBUILD";

    public Connection connect() throws Exception {
        System.out.println("Reached the connection statement");
        try {
            System.out.println("Started Try/Catch Block");
            //Load connection Driver for MySQL
            Class.forName("com.mysql.jdbc.Driver");
            //Setup Connection
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tritemare?characterEncoding=UTF-8&user=tacet&password="+SQLPASS);
            System.out.println("Connected without exceptions!");
            return connect;
        } catch(Exception e){
            out(e.getMessage());
        }
        return null;
    }
    void out (String x){
        System.out.println(x);
    }

    public String getAPI0Auth(Connection conn) throws SQLException {
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from tritemare.twitchsettings");
        out(resultSet.getMetaData().getTableName(1));
        System.out.println(resultSet.getMetaData().getColumnCount());

        resultSet.next();
        out(resultSet.getString("api0Auth"));
        out(resultSet.getString("login0Auth"));
        out(resultSet.getString("apiClientID"));

        return "";
    }

    public String getAPIClientID() throws SQLException {
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select * from tritemare.twitchsettings");

        return resultSet.getString(1);
    }

    public String getLogin0Auth() throws SQLException {
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select * from tritemare.twitchsettings");
        resultSet.next();
        return resultSet.getString(1);
    }

    public String readKnightName(ResultSet resultSet) throws SQLException {
        //ResultSet begins BEFORE the first element, making it easier to loop through from the beginning
        while(resultSet.next()){

        }
        return "";
    }


    public void close(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                System.out.println("Closing Connection!");
                connect.close();
            }
        } catch (Exception e) {

        }
    }


}
