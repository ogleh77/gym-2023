package com.example.gym.dto.main;

import com.example.gym.entities.main.PaymentBuilder;
import com.example.gym.entities.main.Payments;
import com.example.gym.entities.service.Pending;
import com.example.gym.helpers.DbConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;


class PaymentServiceTest {
    private static final Connection connection = DbConnection.getConnection();

    @Test
    void fetchCustomersOfflinePayment() {
    }

    @Test
    void fetchCustomersOnlinePayment() {
    }

    @Test
    void fetchAllCustomersPayments() {
    }

    @Test
    void pendPayment() {


    }


    @Test
    void setPaymentOff() throws SQLException {
        Payments payment = PaymentService.fetchCustomersOnlinePayment("4476619").get(0);
        System.out.println(payment);

        PaymentService.setPaymentOff(payment);
    }

    @Test
    void fetchPendedPayment() throws SQLException {
        LocalDate exp = LocalDate.of(2023, 4, 2);
        LocalDate pendingDate = LocalDate.now();
        int daysRemind = Period.between(pendingDate, exp).getDays();

        pend(1, daysRemind);

    }

    @Test
    void updatePendingPayment() throws SQLException {
        unPend(1);

    }


    ///First step check if the payment is already in pending table
    private boolean checkIfExist(int paymentID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM pending WHERE payment_fk=" + paymentID);
        if (rs.next()) {
            System.out.println("Founded");
            return true;
        } else {
            System.out.println("Not exist");
        }
        statement.close();
        rs.close();
        return false;
    }

    //Second Step pend


    private void pend(int paymentId, int daysRemain) throws SQLException {
        boolean alreadyPended = checkIfExist(paymentId);
        String query = "";
        if (alreadyPended) {
            query = "UPDATE pending SET is_pending=true " +
                    ",days_remain='" + daysRemain + "' WHERE payment_fk=" + paymentId;
        } else {
            query = "INSERT INTO pending(days_remain,payment_fk)" + "VALUES (" + daysRemain + "," + paymentId + ")";
        }
        Statement statement = connection.createStatement();
        statement.execute(query);

        System.out.println("Done");
    }


    private void unPend(int paymentID) throws SQLException {

        boolean existed = checkIfExist(paymentID);
        String query = "";

        if (existed) {
            query = "DELETE FROM pending WHERE payment_fk=" + paymentID;

            Statement statement = connection.createStatement();
            statement.execute(query);

            System.out.println("Done");
        } else {
            System.out.println("Not found...");
        }
    }

//    private void updateThePendedOne(int pendId,int daysRemain){
//        String update=""
//    }

    public Pending fetchPend(int paymentID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs =
                statement.executeQuery("SELECT * FROM pending LEFT JOIN payments " +
                        "p on p.payment_id = pending.payment_fk WHERE is_pending=true ;");
        Payments payment;

        Pending pending = null;
        while (rs.next()) {
            payment = getPayments(rs);

            pending = new Pending(rs.getInt("pending_id"), rs.getString("pending_date"), rs.getInt("days_remain"), payment, rs.getBoolean("is_pending"));
        }
        statement.close();
        rs.close();
        return pending;
    }

    public Payments fetchPendedPayment(int paymentID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs =
                statement.executeQuery("SELECT * FROM payments WHERE payment_id=" + paymentID);
        Payments payment;

        payment = getPayments(rs);
        statement.close();
        rs.close();
        return payment;
    }

    private Payments getPayments(ResultSet rs) throws SQLException {
        Payments payment;
        payment = new PaymentBuilder().setPaymentID(rs.getInt("payment_id")).setPaymentDate(rs.getString("payment_date")).setExpDate(LocalDate.parse(rs.getString("exp_date"))).setAmountPaid(rs.getDouble("amount_paid")).setPaidBy(rs.getString("paid_by")).setPoxing(rs.getBoolean("poxing")).setDiscount(rs.getDouble("discount")).setCustomerFK(rs.getString("customer_phone_fk")).setOnline(rs.getBoolean("is_online")).setYear(rs.getString("year")).setPending(rs.getBoolean("pending")).setMonth(rs.getString("month")).build();
        return payment;
    }
}