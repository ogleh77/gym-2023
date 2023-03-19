package com.example.gym.models.main;

import com.example.gym.dto.services.BoxService;
import com.example.gym.entities.main.PaymentBuilder;
import com.example.gym.entities.main.Payments;
import com.example.gym.entities.service.Box;
import com.example.gym.entities.service.Pending;
import com.example.gym.helpers.CustomException;
import com.example.gym.helpers.DbConnection;
import com.example.gym.models.services.DailyReportDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PaymentModel {
    private static final Connection connection = DbConnection.getConnection();

    public void insertPayment(String customerPhone, String customerGender, Payments payment) throws SQLException {
        connection.setAutoCommit(false);
        try {
            String insertPaymentQuery = "INSERT INTO payments(exp_date, amount_paid, paid_by," + "discount,poxing,box_fk, customer_phone_fk) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insertPaymentQuery);
            ps.setString(1, payment.getExpDate().toString());
            ps.setDouble(2, payment.getAmountPaid());
            ps.setString(3, payment.getPaidBy());
            ps.setDouble(4, payment.getDiscount());
            ps.setBoolean(5, payment.isPoxing());

            if (payment.getBox() == null) {
                ps.setString(6, null);
            } else {
                ps.setInt(6, payment.getBox().getBoxId());
                BoxService.updateBox(payment.getBox());
            }

            ps.setString(7, customerPhone);
            ps.executeUpdate();

            System.out.println("Payment inserted");
            //-------------make the payment's report-------------
            makeReport(payment, customerGender);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
            //   System.out.println(e.getMessage());
        }
    }

    public void pendPayment(Box box, int paymentId, int daysRemind) throws SQLException {

        connection.setAutoCommit(false);

        //First check if the payment is already in pending table

        boolean alreadyPended = checkIfExist(paymentId);

        if (alreadyPended) {

        }


        try (Statement statement = connection.createStatement()) {

            String pendQuery = "INSERT INTO pending(days_remain,payment_fk)" + "VALUES (" + daysRemind + "," + paymentId + ")";

            String paymentQuery = "UPDATE payments SET is_online=false,pending=true WHERE payment_id=" + paymentId;

            // TODO: 03/03/2023 "Make the box update during the off insha Allah "


            if (box != null) {
                paymentQuery = "UPDATE payments SET is_online=false,pending=true,box_fk=null WHERE payment_id=" + paymentId;
                BoxService.updateBox(box);
            }

            statement.addBatch(pendQuery);
            statement.addBatch(paymentQuery);
            statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private boolean checkIfExist(int paymentID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM pending WHERE payment_fk=" + paymentID);
        if (rs.next()) {
            System.out.println("Founded");
            return true;
        } else {
            System.out.println("Not exist");
        }
        return false;
    }

    public void updatePendingPayment(LocalDate remainDate, Payments payment) throws SQLException {
        connection.setAutoCommit(false);

        try {
            String unPendPayment = "UPDATE payments SET is_online=true, pending=false," + "exp_date='" + remainDate + "' WHERE payment_id=" + payment.getPaymentID();


            String setPendingFalse = "UPDATE pending SET is_pending=false WHERE payment_fk=" + payment.getPaymentID();

            Statement statement = connection.createStatement();

            statement.addBatch(setPendingFalse);
            statement.addBatch(unPendPayment);


            statement.executeBatch();

            connection.commit();
            System.out.println("Payment re online");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }


    public void offPayment(Payments payment) throws SQLException {
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE payments SET is_online=false WHERE payment_id=" + payment.getPaymentID();
            if (payment.getBox() != null) {
                BoxService.updateBox(payment.getBox());
            }
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new CustomException("Khalad ayaa dhacay mmarka lama off garayn paymentkan");
        }


        // TODO: 09/03/2023 Make paymenr off insha Allah
    }

    public ObservableList<Pending> fetchPendedPayment() throws SQLException {
        ObservableList<Pending> pendings = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM pending LEFT JOIN payments p on p.payment_id = pending.payment_fk WHERE is_pending=true ;");
        Payments payment;
        while (rs.next()) {

            payment = getPayments(rs);

            Pending pending = new Pending(rs.getInt("pending_id"), rs.getString("pending_date"), rs.getInt("days_remain"), payment, rs.getBoolean("is_pending"));

            pendings.add(pending);

        }

        statement.close();
        rs.close();
        return pendings;
    }

    public ObservableList<Payments> fetchCustomersOfflinePayment(String customerPhone) throws SQLException {

        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " + "WHERE customer_phone_fk=" + customerPhone + "  AND pending=false AND is_online=false");


        return getPayments(payments, statement, rs);

    }

    public ObservableList<Payments> fetchCustomersOnlinePayment(String customerPhone) throws SQLException {

        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " + "WHERE customer_phone_fk=" + customerPhone + "  AND pending=false AND is_online=true");

        return getPayments(payments, statement, rs);
    }

    public ObservableList<Payments> fetchAllCustomersPayments(String phone) throws SQLException {
        //-------Fetch payments according to customer that belongs--------tested......

        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " + "WHERE customer_phone_fk=" + phone + " ORDER BY exp_date DESC");

        return getPayments(payments, statement, rs);
    }

    public Payments getSinglePayments(int paymentID) throws SQLException {
        Payments payment = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM payments WHERE payment_id=" + paymentID);
        while (rs.next()) {

            Box box = null;
            if (rs.getString("box_fk") != null) {
                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
            }
            payment = getPayments(rs);
            payment.setBox(box);
        }
        statement.close();
        rs.close();
        return payment;
    }


    //-------------------helpers------------------
    private ObservableList<Payments> getPayments(ObservableList<Payments> payments, Statement
            statement, ResultSet rs) throws SQLException {
        Payments payment;
        while (rs.next()) {

            Box box = null;
            if (rs.getString("box_fk") != null) {
                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
            }

            payment = getPayments(rs);
            payment.setBox(box);
            payments.add(payment);

        }

        statement.close();
        rs.close();
        return payments;
    }

    private Payments getPayments(ResultSet rs) throws SQLException {
        Payments payment;
        payment = new PaymentBuilder().setPaymentID(rs.getInt("payment_id")).setPaymentDate(rs.getString("payment_date")).setExpDate(LocalDate.parse(rs.getString("exp_date"))).setAmountPaid(rs.getDouble("amount_paid")).setPaidBy(rs.getString("paid_by")).setPoxing(rs.getBoolean("poxing")).setDiscount(rs.getDouble("discount")).setCustomerFK(rs.getString("customer_phone_fk")).setOnline(rs.getBoolean("is_online")).setYear(rs.getString("year")).setPending(rs.getBoolean("pending")).setMonth(rs.getString("month")).build();
        return payment;
    }

    private void makeReport(Payments payment, String customerGender) throws SQLException {
        Statement st = connection.createStatement();
        if (customerGender.equals("Male") && payment.getBox() != null) {
            DailyReportDTO.dailyReportMaleWithBox(st);
        } else if (customerGender.equals("Female") && payment.getBox() != null) {
            DailyReportDTO.dailyReportFemaleWithBox(st);
        } else if (payment.getBox() == null && customerGender.equals("Male")) {
            DailyReportDTO.dailyReportMaleWithOutBox(st);
        } else if (payment.getBox() == null && customerGender.equals("Female")) {
            DailyReportDTO.dailyReportFemaleWithOutBox(st);
        }
        int[] arr = st.executeBatch();
        System.out.println(Arrays.toString(arr));
        st.close();
    }


}
