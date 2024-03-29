package tech.tacetnox.magister.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {
    private static Logger o = LoggerFactory.getLogger(MySQLAccess.class);

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepStatement = null;
    private ResultSet resultSet = null;
    private String SQLPASS;

    public Connection connect(String password) throws Exception {
        o.debug("Assigning SQL password from config");
        SQLPASS = password;
        System.out.println();
        try {
            System.out.println("Started Try/Catch Block");
            //Load connection Driver for MySQL
            Class.forName("com.mysql.jdbc.Driver");
            //Setup Connection
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tritemare?characterEncoding=UTF-8&user=tacet&password="+SQLPASS);
            System.out.println("");
            o.info("Connected without exceptions!");
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
        /*statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from tritemare.twitchsettings");
        out("Table name: " +resultSet.getMetaData().getTableName(1));
        System.out.println("Column Count: " +resultSet.getMetaData().getColumnCount());

        resultSet.next();
        out("api0Auth" + resultSet.getString("api0Auth"));
        out("login0Auth" + resultSet.getString("login0Auth"));
        out("apiClientID" + resultSet.getString("apiClientID"));
        */
        return "";

    }

    public String getAPIClientID(Connection conn) throws SQLException {

        return resultSet.getString(1);
    }

    public String getLogin0Auth(Connection conn) throws SQLException {

        return resultSet.getString(1);
    }

    public String getKnightName(Connection conn, String username) throws SQLException {
        out(username);
        //ResultSet begins BEFORE the first element, making it easier to loop through from the beginning
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from tritemare.subs WHERE username=\""+username.trim()+"\"");
        //Stepping into the actual result as it starts prior to the returned result
        if(resultSet.next() == false){
            return "That user has no knightly name!";
        } else {
            do {
            return username + "'s knighted name is " + resultSet.getString("subname") + " %s and they've been subscribed for" +
                    " " + resultSet.getString("months_subbed") + " months!";

            }while (resultSet.next());
        }
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
