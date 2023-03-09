package com.example.gym.models.services;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DailyReportDTO {
    private static LocalDate today = LocalDate.now();

    public static void dailyReportMaleWithBox(Statement st) throws SQLException {

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1),vip_box=(vip_box+1) " + "WHERE report_date ='" + today + "'") > 0) {
            System.out.println("Updated male with box....");

        } else {
            String query = "INSERT INTO daily_report(report_date,registration,male,vip_box)VALUES ('" + today + "',1,1,1)";
            st.executeUpdate(query);
            System.out.println("Saved male with box....");
        }
    }


    public static void dailyReportFemaleWithBox(Statement st) throws SQLException {

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1),vip_box=(vip_box+1) " + "WHERE report_date ='" + today + "'") > 0) {
            System.out.println("Updated Female with box....");

        } else {
            String query = "INSERT INTO daily_report(registration,female,vip_box)VALUES (1,1,1)";
            st.executeUpdate(query);
            System.out.println("Saved Female with box....");

        }
    }


    public static void dailyReportMaleWithOutBox(Statement st) throws SQLException {

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1) WHERE report_date='" + today + "'") > 0) {
            System.out.println("Updated male with out box....");

        } else {
            String query = "INSERT INTO daily_report(registration,male)VALUES (1,1)";

            st.executeUpdate(query);
            System.out.println("Saved male with out box....");

        }
    }


    public static void dailyReportFemaleWithOutBox(Statement st) throws SQLException {
        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1)" + "WHERE report_date ='" + today + "'") > 0) {
            System.out.println("Updated female with out box....");
        } else {
            String query = "INSERT INTO daily_report(registration,female)VALUES (1,1)";
            st.executeUpdate(query);

            System.out.println("Saved female with out box....");

        }
    }
}
