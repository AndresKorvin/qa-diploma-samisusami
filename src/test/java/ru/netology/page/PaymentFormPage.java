package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentFormPage {

    private SelenideElement cardNumberField = $(byText("Номер карты"));
    private SelenideElement monthCardField = $(byText("Месяц"));
    private SelenideElement yearCardField = $(byText("Год"));
    private SelenideElement cardOwnerField = $(byText("Владелец"));
    private SelenideElement cvcCodeField = $(byText("CVC/CVV"));
    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement requiredFieldForOwnerMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement cardHasExpiredMessage = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldContainsInvalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));

    private SelenideElement theCardExpirationDateIsIncorrectMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement containsInvalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));

    private SelenideElement failedNotificationMessage = $(byText("Ошибка! Банк отказал в проведении операции."));
    public SelenideElement successedNotificationMessage = $(byText("Операция одобрена Банком."));

    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        cardNumberField.sendKeys(cardNumber);
        monthCardField.sendKeys(month);
        yearCardField.sendKeys(year);
        cardOwnerField.sendKeys(cardOwner);
        cvcCodeField.sendKeys(code);
        continueButton.click();
    }

    public PaymentFormPage clearForm() {
//        clearFields();
        return new PaymentFormPage();
    }

    public void clearFields() {
        cardNumberField.doubleClick().sendKeys(Keys.DELETE);
        monthCardField.doubleClick().sendKeys(Keys.DELETE);
        yearCardField.doubleClick().sendKeys(Keys.DELETE);
        cardOwnerField.doubleClick().sendKeys(Keys.DELETE);
        cvcCodeField.doubleClick().sendKeys(Keys.DELETE);
    }

    public void waitForNotificationMesage(SelenideElement notificationMesage) {
        notificationMesage.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

}
