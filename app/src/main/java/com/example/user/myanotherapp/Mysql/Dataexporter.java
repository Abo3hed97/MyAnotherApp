package com.example.user.myanotherapp.Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used to export data to the online database.
 */
public class Dataexporter {
    private static final int LOCAL_PORT = 4342;
    private static final String REMOTE_HOST = "localhost";
    private static final int REMOTE_PORT = 3306;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB = "ak18a";
    private static final String DB_USER = "ak18a";
    private static final String DB_PASS = "doh2ahMa";
    private static final String URL = "jdbc:mysql://" + REMOTE_HOST +":" + LOCAL_PORT + "/";
    Dataimport dataimport = new Dataimport();

    /**
     * Inserting the data from Useronline object to the database.
     */
    public void addUser (UserOnline user)
    {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            Statement myStmt = con.createStatement();
            String sql = "insert into User (username, password, email) values" +
                    " ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getEmail()+ "')";
            myStmt.executeUpdate(sql);
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * checking if the user (username and password ) already exists
     * and returning true if the user not exists.
     */

    /**
     * Inserting the data from Bullet object to the database.
     */

    public void addBullet (Bullet bullet)
    {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            Statement myStmt = con.createStatement();
            String sql = "insert into Bullet (userID,content, title, dateFrom,dateTo,timeFrom,timeTo,bulletType,importance) values" +
                    " (" +bullet.getUserID()+", '"+ bullet.getContent()+ "', '" + bullet.getTitle() + "', '" +
                    bullet.getDateFrom() + "', '" + bullet.getDateTo() + "', '" + bullet.getTimeFrom() + "', '"+
                     bullet.getTimeTo() + "', '" + bullet.getBulletType() + "', " + bullet.getImportance() + ")";
            myStmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
