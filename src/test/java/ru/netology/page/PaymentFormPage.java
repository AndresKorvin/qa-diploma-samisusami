package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class PaymentFormPage {

    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthCardField = $("[placeholder='08']");
    private final SelenideElement yearCardField = $("[placeholder='22']");
    private final SelenideElement cardOwnerField = $$("[class='input__control']").get(3);
    private final SelenideElement cvcCodeField = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement requiredFieldForOwnerMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement cardHasExpiredMessage = $(byText("Истёк срок действия карты"));
    private SelenideElement theCardExpirationDateIsIncorrectMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement failedNotificationMessage = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successedNotificationMessage = $(byText("Операция одобрена Банком."));

    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        cardNumberField.sendKeys(cardNumber);
        monthCardField.sendKeys(month);
        yearCardField.sendKeys(year);
        cardOwnerField.sendKeys(cardOwner);
        cvcCodeField.sendKeys(code);
        continueButton.click();
    }

    public PaymentFormPage clearForm() {
        return new PaymentFormPage();
    }

    public void waitForNotificationMesage(SelenideElement notificationMesage) {
        notificationMesage.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
