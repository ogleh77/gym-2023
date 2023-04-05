package com.example.gym.dto.main;

import com.example.gym.entities.main.Payments;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class PaymentServiceTest {

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
    void updatePendingPayment() {
    }

    @Test
    void fetchPendedPayment() {
    }

    @Test
    void setPaymentOff() throws SQLException {
        Payments payment = PaymentService.fetchCustomersOnlinePayment("4476619").get(0);
        System.out.println(payment);

        PaymentService.setPaymentOff(payment);
    }
}