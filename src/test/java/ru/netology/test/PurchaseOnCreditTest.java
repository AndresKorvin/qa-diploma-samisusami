package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentFormPage;
import ru.netology.sql.SqlRequest;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getApprovedPurchaseStatus;
import static ru.netology.data.DataHelper.getDeclinedPurchaseStatus;

public class PurchaseOnCreditTest {
    private MainPage mainPage;
    private PaymentFormPage paymentFormPage;

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }
    @AfterEach
    void cleanDataBase() {
        SqlRequest.clearDataBase();
    }
    @Test
    void shouldAllowPurchasesWithCardA() {
        paymentFormPage = mainPage.payOnCredit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getACardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.successedNotificationMessage);

        val expected = getApprovedPurchaseStatus();
        val actual = SqlRequest.getCreditPurchaseStatus();
        assertEquals(expected, actual);
    }
    @Test
    void shouldDeclinePurchaseWithCardB() {
        paymentFormPage = mainPage.payOnCredit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.failedNotificationMessage);

        val expected = getDeclinedPurchaseStatus();
        val actual = SqlRequest.getCreditPurchaseStatus();
        assertEquals(expected, actual);
    }

}
