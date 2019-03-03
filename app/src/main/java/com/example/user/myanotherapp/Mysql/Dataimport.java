package com.example.user.myanotherapp.Mysql;

import android.content.pm.PackageInstaller;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dataimport {
    private Session session;
    private static final int LOCAL_PORT = 4342;
    private static final String REMOTE_HOST = "localhost";
    private static final int REMOTE_PORT = 3306;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB = "ak18a";
    private static final String DB_USER = "ak18a";
    private static final String DB_PASS = "doh2ahMa";
    private static final String URL = "jdbc:mysql://" + REMOTE_HOST +":" + LOCAL_PORT + "/";

    public String connectToDBServer(){
        String user = "ak18a";
        String password = "u0k(1NtwKp";
        String host = "pcai042.informatik.uni-leipzig.de";
        String tt = null;
        int port=22;
        try
        {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            //System.out.println("Establishing Connection...");
            session.connect();
            tt = "Establshing conncation";
            int assinged_port = session.setPortForwardingL(LOCAL_PORT, REMOTE_HOST, REMOTE_PORT);
            //System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
        }
        catch(Exception e){
            System.err.print(e);
        }
        return tt;
    }

    public List<User> importDataUser() {
        Connection con;
        List<User> userDB = null;
        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            String query = "" + "SELECT * FROM User";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            userDB = new ArrayList<>();
            while (rs.next()) {
                userDB.add(new User(
                                rs.getInt("userID"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email")

                        )
                );
            }


        } catch (ClassNotFoundException cnfEx){
            cnfEx.printStackTrace();
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return userDB;

    }


    public List<Bullet> importDataBullet() {
        Connection con;
        List<Bullet> bulletDB = null;
        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            String query = "" + "SELECT * FROM Bullet";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            bulletDB = new ArrayList<>();
            while (rs.next()) {
                bulletDB.add(new Bullet(
                                rs.getInt("bulletID"),
                                rs.getInt("userID"),
                                rs.getString("content"),
                                rs.getString("title"),
                                rs.getString("dateFrom"),
                                rs.getString("dateTo"),
                                rs.getString("timeFrom"),
                                rs.getString("timeTo"),
                                rs.getString("bulletType"),
                                rs.getInt("importance"),
                                rs.getInt("isMigrated"),
                                rs.getInt("isChecked")
                        )
                );
            }


        } catch (ClassNotFoundException cnfEx){
            cnfEx.printStackTrace();
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return bulletDB;

    }


}

