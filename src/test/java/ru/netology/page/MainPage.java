package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    public SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    public SelenideElement purchaseOnDebit = $(byText("Купить"));
    public SelenideElement purchaseOnCredit = $(byText("Купить в кредит"));
    public SelenideElement paymentFormHeading = $("[class='heading heading_size_m heading_theme_alfa-on-white']");
    public String buttonTextPayOnCredit = "Кредит по данным карты";
    public String buttonTextPayOnDebit = "Оплата по карте";


    public MainPage() {
        heading.shouldBe(visible);
    }

    public PaymentFormPage payOnDebit() {
        purchaseOnDebit.click();
        paymentFormHeading.shouldHave(exactText(buttonTextPayOnDebit));
        return new PaymentFormPage();
    }

    public PaymentFormPage payOnCredit() {
        purchaseOnCredit.click();
        paymentFormHeading.shouldHave(exactText(buttonTextPayOnCredit));
        return new PaymentFormPage();
    }
}