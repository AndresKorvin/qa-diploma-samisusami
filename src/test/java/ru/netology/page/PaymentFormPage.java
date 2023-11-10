package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentFormPage {

    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthCardField = $("[placeholder='08']");
    private final SelenideElement yearCardField = $("[placeholder='22']");
    private final SelenideElement cardOwnerField = $$("[class='input__control']").get(3);
    private final SelenideElement cvcCodeField = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));

    public SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    public SelenideElement requiredFieldForOwnerMessage = $(byText("Поле обязательно для заполнения"));
    public SelenideElement cardHasExpiredMessage = $(byText("Истёк срок действия карты"));
    public SelenideElement fieldContainsInvalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));

    public SelenideElement theCardExpirationDateIsIncorrectMessage = $(byText("Неверно указан срок действия карты"));
    public SelenideElement containsInvalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));

    public SelenideElement failedNotificationMessage = $(byText("Ошибка! Банк отказал в проведении операции."));
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
