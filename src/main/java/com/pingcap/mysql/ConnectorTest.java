package com.pingcap.mysql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by iamxy on 2016/10/28.
 */
public class ConnectorTest {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:4000/test";
        String user = "root";
        String password = "";

        try {
            con = DriverManager.getConnection(url, user, password);
            if (con.isReadOnly()) {
                System.out.println("isReadOnly: ON");
            } else {
                System.out.println("isReadOnly: OFF");
            }
            st = con.createStatement();
            rs = st.executeQuery("select @@session.tx_read_only");
            if(rs.next()) {
                System.out.println(rs.getInt(1));
            }
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(ConnectorTest.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ConnectorTest.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
