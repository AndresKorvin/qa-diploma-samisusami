package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    private SelenideElement purchaseOnDebit = $(byText("Купить"));
    private SelenideElement purchaseOnCredit = $(byText("Купить в кредит"));
    private SelenideElement paymentFormHeading = $("[class='heading heading_size_m heading_theme_alfa-on-white']");
    private String buttonTextPayOnCredit = "Оплата по карте";
    private String buttonTextPayOnDebit = "Кредит по данным карты";


    public MainPage() {
        heading.shouldBe(visible);
    }

    public PaymentFormPage payOnDebit() {
        purchaseOnDebit.click();
        paymentFormHeading.shouldHave(exactText(buttonTextPayOnCredit));
        return new PaymentFormPage();
    }

    public PaymentFormPage payOnCredit() {
        purchaseOnCredit.click();
        paymentFormHeading.shouldHave(exactText(buttonTextPayOnCredit));
        return new PaymentFormPage();
    }
}