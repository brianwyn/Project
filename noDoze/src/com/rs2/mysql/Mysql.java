package com.rs2.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
	private static ThreadLocal<Connection> con = new ThreadLocalConnection();
	private static String url = "jdbc:mysql://hostingtb.com:3306/brianw_server";
    private static String user = "brianw_brianw";
    private static String pass = "thisismine123z";

    public static Connection getConnection() {
        return con.get();
    }

    public static void release() throws SQLException {
        con.get().close();
        con.remove();
    }

    private static class ThreadLocalConnection extends ThreadLocal<Connection> {
        static {
            try {
                Class.forName("com.mysql.jdbc.Driver"); 
            } catch (ClassNotFoundException e) {
                System.out.println("[SQL] Could not locate the JDBC mysql driver.");
            }
        }

        @Override
        protected Connection initialValue() {
            return getConnection();
        }

        private Connection getConnection() {
            DriverManager.setLoginTimeout(15); 
            try {
                return DriverManager.getConnection(url, user, pass);
            } catch (SQLException sql) {
                System.out.println("[SQL] Error establishing connection. Please make sure you've correctly configured db.properties.");
                return null;
            }
        }

        @Override
        public Connection get() {
            Connection con = super.get();
            try {
                if (!con.isClosed()) {
                    return con;
                }
            } catch (SQLException sql) {
            }
            con = getConnection();
            super.set(con);
            return con;
        }
    }
}
