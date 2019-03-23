package com.example.user.myanotherapp.Mysql;


import android.database.Cursor;
import android.util.Log;

import com.example.user.myanotherapp.MainActivity;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
    private static final String URL = "jdbc:mysql://" + REMOTE_HOST + ":" + LOCAL_PORT + "/";
    List<Bullet> bulletList = new ArrayList<Bullet>();
    int userID = MainActivity.userID;
    public static String conError;

    /**
     * Establishes DB connection to praktikum server
     */
    public String connectToDBServer() {
        String user = "ak18a";
        String password = "u0k(1NtwKp";
        String host = "pcai042.informatik.uni-leipzig.de";
        String tt = null;
        int port = 22;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            //System.out.println("Establishing Connection...");
            session.connect();
            tt = "Establshing conncation";
            int assinged_port = session.setPortForwardingL(LOCAL_PORT, REMOTE_HOST, REMOTE_PORT);
            //System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
        } catch (Exception e) {
            System.err.print(e);
            conError = "Please check Your Internet connection";
        }
        return tt;
    }

    /**
     * Importing User Data from the database
     * @return UserOnline object
     */
    public UserOnline importDataUser() {
        Connection con;
        UserOnline userDB = null;

        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            String query = "" + "SELECT * FROM User where email='" + MainActivity.userEmail + "'";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                userDB = new UserOnline(rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));
            }



        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return userDB;

    }

    /**
     * Importing Bullet data from the database
     * @return List of Bullet Objects.
     */

    public List<Bullet> importDataBullet() {
        Connection con;
        List<Bullet> bulletDB = null;
        try {

            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL + DB, DB_USER, DB_PASS);
            String query = "" + "SELECT * FROM Bullet where userID=" + userID;
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
            con.close();
            con.close();


        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return bulletDB;

    }

    /**
     * Getting Bullet object compere the date to daysList in dailylog and show the Title in the right date in Dailylog
     * @param d week days
     * @return List with Bullet titles
     */

    public ArrayList<ArrayList<String>> getData(ArrayList<String> d) {
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {
            ArrayList<String> k = new ArrayList<>();
            for (Bullet bullet : importDataBullet()) {
                String check = bullet.getDateFrom();
                if (d.get(i).equals(check)) {

                    k.add(bullet.getTitle());
                }
            }
            b.add(k);
        }
        return b;
    }

    /**
     *Getting Bullet object
     *  compere the date to MonthlyList in MonthlyLog
     * checking if it's important Bullet
     * and show the Title in the right date in MonthlLog
     * @param daysofMonths List of Months Days
     * @return List with Bullet title.
     * @throws ParseException
     */
    public ArrayList<ArrayList<String>> getImportantBullets(ArrayList<String> daysofMonths) throws ParseException {
        ArrayList<ArrayList<String>> importantBullets = new ArrayList<>();
        for (int i = 0; i < daysofMonths.size(); i++) {
            ArrayList<String> importantBulletsforDay = new ArrayList<>();
            for (Bullet bullet : importDataBullet()) {
                String check1 = bullet.getDateFrom();
                String check2 = String.valueOf(bullet.getImportance());
                if (daysofMonths.get(i).equals(check1) && check2.equals("1")) {
                    importantBulletsforDay.add(bullet.getTitle());
                }
            }
            importantBullets.add(importantBulletsforDay);
        }
        return importantBullets;
    }




    public String[] getVeryImportantBullets(ArrayList<Integer> monthNumbers,int currentYear){
    String veryImportnatBullets[]=new String[12];
    for(int i=0;i<12;i++){
        for(Bullet bullet:importDataBullet()){
            String check1=String.valueOf(bullet.getImportance());
            Log.i("Importance",String.valueOf(bullet.getImportance()));
            StringBuilder check2=new StringBuilder(bullet.getDateFrom().substring(5,7));
            if(check2.charAt(0)=='0'){
                check2=check2.deleteCharAt(0);
            }
            Log.i(String.valueOf(monthNumbers.get(i)),check2.toString());
            Log.i("Booooooooolean",String.valueOf(check2.toString().equals(String.valueOf(monthNumbers.get(i)))));
            if(check2.toString().equals(String.valueOf(monthNumbers.get(i)))&&check1.equals("2")&&bullet.getDateFrom().substring(0,4).equals(String.valueOf(currentYear))){
                veryImportnatBullets[i]=(bullet.getTitle());
                Log.i("Helllllllllllllllo",bullet.getTitle());
            }
        }
    }

       return veryImportnatBullets;
    }

}










