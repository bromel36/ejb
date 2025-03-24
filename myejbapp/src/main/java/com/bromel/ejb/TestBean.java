package com.bromel.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Stateless
public class TestBean {

    @Resource(lookup = "java:/MySqlDS") // JNDI name bạn đã khai báo
    private DataSource dataSource;

    public String testQuery() {
        String result = "";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NOW()")) {

            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return "Current Time from DB: " + result;
    }
}

