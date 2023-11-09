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

public class PurchaseOnDebitTest {
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
    void thePurchaseShouldBeMadeUsingTheCardA() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getACardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.successedNotificationMessage);

        val expected = DataHelper.getACardStatus();
        val actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }

}
