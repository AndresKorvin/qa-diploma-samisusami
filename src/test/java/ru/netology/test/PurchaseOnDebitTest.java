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
import static ru.netology.data.DataHelper.*;

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

    // Позитивные тесты
    @Test
    void shouldAllowPurchasesWithCardA() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getACardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.successedNotificationMessage);

        val expected = getApprovedPurchaseStatus();
        val actual = SqlRequest.getDebitPurchaseStatus();
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
        val actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }

@Test
//        - неверный номер карты
void shouldDeclinePurchaseWithCardAWithInvalidCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getInvalidCardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.failedNotificationMessage);

    val expected = getDeclinedPurchaseStatus();
    val actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - номер карты с 1 цирой
void shouldDeclinePurchaseWithCardAWithOneDigitInTheCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getCardNumberWith1Digit(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - номер карты с 15 цифрами
void shouldDeclinePurchaseWithCardAWith15DigitInTheCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getCardNumberWith15Digits(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - латиница вместо цифр в номере карты
void shouldDeclinePurchaseWithCardAWithLatinLettersInsteadOfNumbersInTheCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getCardNumberWithLatinText(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - кириллица вместо цифр в номере карты
void shouldDeclinePurchaseWithCardAWithCyrillicLettersInsteadOfNumbersInTheCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getCardNumberWithCyrillicText(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - пустые данные
void shouldDeclinePurchaseWithCardAWithEmptyCardNumber() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getEmpty(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - неверный номер месяца действия карты
void shouldDeclinePurchaseWithWrongMonth() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getInvalidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.theCardExpirationDateIsIncorrectMessage);
}
@Test
//        - месяц больше 12
void shouldDeclinePurchaseWithMonthNum13() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthNum13(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.theCardExpirationDateIsIncorrectMessage);
}
@Test
//            - месяц 0
void shouldDeclinePurchaseWithMonthNum0() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getZeroMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.theCardExpirationDateIsIncorrectMessage);
}
@Test
//            - латиница вместо цифр в поле месяц
void shouldDeclinePurchaseWithLatinLettersInTheMonthField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthWithLatinText(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - кириллица вместо цифр в поле месяц
void shouldDeclinePurchaseWithCyrillicLettersInTheMonthField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthWithCyrillicText(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - пустые данные
void shouldDeclinePurchaseWithEmptyMonth() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getEmpty(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - неверный номер года действия карты
void shouldDeclinePurchaseWrongYear() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getPastYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.cardHasExpiredMessage);
}
@Test
//        - год больше текущего
void shouldDeclinePurchaseWithACardValidityPeriodOfMoreThan5Years() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getFutureYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.theCardExpirationDateIsIncorrectMessage);
}
@Test
//        - латиница вместо цифр в поле год
void shouldDeclinePurchaseWithLatinLettersInFieldYear() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getYearWithLatinText(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - кириллица вместо цифр в поле год
void shouldDeclinePurchaseWithCyrillicLettersInFieldYear() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getYearWithCyrillicText(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - пустые данные
void shouldDeclinePurchaseWithEmptyInFieldYear() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getEmpty(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - неверное заполненное латиницей поле Владелец
void shouldDeclinePurchaseWithWrongOwner() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getnIvalidFullName(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.failedNotificationMessage);

    val expected = getDeclinedPurchaseStatus();
    val actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - кириллица вместо латиницы в поле Владелец
void shouldDeclinePurchaseOwnerFieldIsFilledWithCyrillicCharacters() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithCyrillic(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - цифры вместо букв в поле Владелец
void shouldDeclinePurchaseOwnerFieldIsFilledWithDigits() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithDigits(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - только имя в поле владелец
void shouldDeclinePurchaseOwnerSNameOnly() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOnlyNameOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.failedNotificationMessage);

    val expected = getDeclinedPurchaseStatus();
    val actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - фамилия, имя + излишнее отчество
void shouldDeclineWithLastNameFirstNamePlusExtraMiddleName() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getFullName(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.failedNotificationMessage);

    val expected = getDeclinedPurchaseStatus();
    val actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - имя фамилия на двух языках
void shouldDeclineWithLastNameAndFirstNameInTwoLanguages() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getNameInTwoLanguages(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - спецсимволы в поле владелец
void shouldDeclineWithSpecialCharactersInTheOwnerField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithSpecialChars(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - пустые данные
void shouldDeclineWithEmptyInTheOwnerField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getEmpty(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.requiredFieldForOwnerMessage);
}
@Test
//        - 2 цифры в поле CVC/CVV код
void shouldDeclineWith2DigitsInCvc() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getInvalidFormatCodeWhith2Digits());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - латиница вместо цифр в поле CVC/CVV код
void shouldDeclineWithLatinAlphabetInCvcField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getCodeWithLatinText());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - кириллица вместо цифр в поле CVC/CVV код
void shouldDeclineWithCyrillicAlphabetInCvcField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getCodeWithCyrillicText());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}
@Test
//        - пустые данные
void shouldDeclineWithEmptyInCvcField() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getEmpty());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.wrongFormatMessage);
}

}
