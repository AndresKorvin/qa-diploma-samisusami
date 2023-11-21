package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
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

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }
    @AfterEach
    void cleanDataBase() {
        SqlRequest.clearDataBase();
    }

    // ТЕСТЫ С ПОЛЕМ НОМЕРКАРТЫ
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

    String expected = getDeclinedPurchaseStatus();
    String actual = SqlRequest.getDebitPurchaseStatus();
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
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
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
    //     ТЕСТЫ С КАРТОЙ А
    @Test
    void shouldAllowPurchasesWithCardA() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getACardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getSuccessedNotificationMessage());

        String expected = getApprovedPurchaseStatus();
        String actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }
@Test
//        - неверный номер месяца действия карты
void shouldDeclinePurchaseWithWrongMonthA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getInvalidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
}
@Test
//        - месяц больше 12
void shouldDeclinePurchaseWithMonthNum13A() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthNum13(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
}
@Test
//            - месяц 0
void shouldDeclinePurchaseWithMonthNum0A() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getZeroMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
}
@Test
//            - латиница вместо цифр в поле месяц
void shouldDeclinePurchaseWithLatinLettersInTheMonthFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthWithLatinText(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - кириллица вместо цифр в поле месяц
void shouldDeclinePurchaseWithCyrillicLettersInTheMonthFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getMonthWithCyrillicText(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - пустые данные
void shouldDeclinePurchaseWithEmptyMonthA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getEmpty(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - неверный номер года действия карты
void shouldDeclinePurchaseWrongYearA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getPastYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getCardHasExpiredMessage());
}
@Test
//        - год больше текущего
void shouldDeclinePurchaseWithACardValidityPeriodOfMoreThan5YearsA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getFutureYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
}
@Test
//        - латиница вместо цифр в поле год
void shouldDeclinePurchaseWithLatinLettersInFieldYearA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getYearWithLatinText(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - кириллица вместо цифр в поле год
void shouldDeclinePurchaseWithCyrillicLettersInFieldYearA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getYearWithCyrillicText(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - пустые данные
void shouldDeclinePurchaseWithEmptyInFieldYearA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getEmpty(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - неверное заполненное латиницей поле Владелец
void shouldDeclinePurchaseWithWrongOwnerA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getnIvalidFullName(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

    String expected = getDeclinedPurchaseStatus();
    String actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - кириллица вместо латиницы в поле Владелец
void shouldDeclinePurchaseOwnerFieldIsFilledWithCyrillicCharactersA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithCyrillic(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - цифры вместо букв в поле Владелец
void shouldDeclinePurchaseOwnerFieldIsFilledWithDigitsA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithDigits(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - только имя в поле владелец
void shouldDeclinePurchaseOwnerSNameOnlyA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOnlyNameOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

    String expected = getDeclinedPurchaseStatus();
    String actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - фамилия, имя + излишнее отчество
void shouldDeclineWithLastNameFirstNamePlusExtraMiddleNameA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getFullName(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

    String expected = getDeclinedPurchaseStatus();
    String actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - имя фамилия на двух языках
void shouldDeclineWithLastNameAndFirstNameInTwoLanguagesA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getNameInTwoLanguages(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - спецсимволы в поле владелец
void shouldDeclineWithSpecialCharactersInTheOwnerFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getOwnerWithSpecialChars(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - пустые данные
void shouldDeclineWithEmptyInTheOwnerFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getEmpty(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getRequiredFieldForOwnerMessage());
}
@Test
//        - 2 цифры в поле CVC/CVV код
void shouldDeclineWith2DigitsInCvcA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getInvalidFormatCodeWhith2Digits());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - латиница вместо цифр в поле CVC/CVV код
void shouldDeclineWithLatinAlphabetInCvcFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getCodeWithLatinText());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - кириллица вместо цифр в поле CVC/CVV код
void shouldDeclineWithCyrillicAlphabetInCvcFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getCodeWithCyrillicText());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}
@Test
//        - пустые данные
void shouldDeclineWithEmptyInCvcFieldA() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getACardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getEmpty());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
}

//     ТЕСТЫ С КАРТОЙ В
@Test
void shouldDeclinePurchaseWithCardB() {
    paymentFormPage = mainPage.payOnCredit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getBCardNumber(),
            DataHelper.getValidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

    String expected = getDeclinedPurchaseStatus();
    String actual = SqlRequest.getDebitPurchaseStatus();
    assertEquals(expected, actual);
}
@Test
//        - неверный номер месяца действия карты
void shouldDeclinePurchaseWithWrongMonthB() {
    paymentFormPage = mainPage.payOnDebit().clearForm();
    paymentFormPage.fillForm(
            DataHelper.getBCardNumber(),
            DataHelper.getInvalidMonth(),
            DataHelper.getValidYear(),
            DataHelper.getValidOwner(),
            DataHelper.getValidCode());
    paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
}
    @Test
//        - месяц больше 12
    void shouldDeclinePurchaseWithMonthNum13B() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getMonthNum13(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
    }
    @Test
//            - месяц 0
    void shouldDeclinePurchaseWithMonthNum0B() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getZeroMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
    }
    @Test
//            - латиница вместо цифр в поле месяц
    void shouldDeclinePurchaseWithLatinLettersInTheMonthFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getMonthWithLatinText(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - кириллица вместо цифр в поле месяц
    void shouldDeclinePurchaseWithCyrillicLettersInTheMonthFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getMonthWithCyrillicText(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - пустые данные
    void shouldDeclinePurchaseWithEmptyMonthB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getEmpty(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - неверный номер года действия карты
    void shouldDeclinePurchaseWrongYearB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getPastYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getCardHasExpiredMessage());
    }
    @Test
//        - год больше текущего
    void shouldDeclinePurchaseWithACardValidityPeriodOfMoreThan5YearsB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getFutureYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getTheCardExpirationDateIsIncorrectMessage());
    }
    @Test
//        - латиница вместо цифр в поле год
    void shouldDeclinePurchaseWithLatinLettersInFieldYearB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYearWithLatinText(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - кириллица вместо цифр в поле год
    void shouldDeclinePurchaseWithCyrillicLettersInFieldYearB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getYearWithCyrillicText(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - пустые данные
    void shouldDeclinePurchaseWithEmptyInFieldYearB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getEmpty(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - неверное заполненное латиницей поле Владелец
    void shouldDeclinePurchaseWithWrongOwnerB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getnIvalidFullName(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

        String expected = getDeclinedPurchaseStatus();
        String actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }
    @Test
//        - кириллица вместо латиницы в поле Владелец
    void shouldDeclinePurchaseOwnerFieldIsFilledWithCyrillicCharactersB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOwnerWithCyrillic(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - цифры вместо букв в поле Владелец
    void shouldDeclinePurchaseOwnerFieldIsFilledWithDigitsB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOwnerWithDigits(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - только имя в поле владелец
    void shouldDeclinePurchaseOwnerSNameOnlyB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOnlyNameOwner(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

        String expected = getDeclinedPurchaseStatus();
        String actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }
    @Test
//        - фамилия, имя + излишнее отчество
    void shouldDeclineWithLastNameFirstNamePlusExtraMiddleNameB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getFullName(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getFailedNotificationMessage());

        String expected = getDeclinedPurchaseStatus();
        String actual = SqlRequest.getDebitPurchaseStatus();
        assertEquals(expected, actual);
    }
    @Test
//        - имя фамилия на двух языках
    void shouldDeclineWithLastNameAndFirstNameInTwoLanguagesB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getNameInTwoLanguages(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - спецсимволы в поле владелец
    void shouldDeclineWithSpecialCharactersInTheOwnerFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOwnerWithSpecialChars(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - пустые данные
    void shouldDeclineWithEmptyInTheOwnerFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getEmpty(),
                DataHelper.getValidCode());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getRequiredFieldForOwnerMessage());
    }
    @Test
//        - 2 цифры в поле CVC/CVV код
    void shouldDeclineWith2DigitsInCvcB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getInvalidFormatCodeWhith2Digits());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - латиница вместо цифр в поле CVC/CVV код
    void shouldDeclineWithLatinAlphabetInCvcFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getCodeWithLatinText());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - кириллица вместо цифр в поле CVC/CVV код
    void shouldDeclineWithCyrillicAlphabetInCvcFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getCodeWithCyrillicText());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    @Test
//        - пустые данные
    void shouldDeclineWithEmptyInCvcFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getEmpty());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
    // добавить в кредит
    @Test
//        - спецсимволы
    void shouldDeclineWithSpecialCharsInCvcFieldB() {
        paymentFormPage = mainPage.payOnDebit().clearForm();
        paymentFormPage.fillForm(
                DataHelper.getBCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getCvcWithSpecialChars());
        paymentFormPage.waitForNotificationMesage(paymentFormPage.getWrongFormatMessage());
    }
}
