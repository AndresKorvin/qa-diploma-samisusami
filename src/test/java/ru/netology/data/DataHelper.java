package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    private static Faker fakerEn;

    private DataHelper() {}

//VALID DATA

    public static String getACardNumber() { return "4444 4444 4444 4441"; }

    public static String getBCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getACardStatus() {
        return "APPROVED";
    }

    public static String getBCardStatus() {
        return "DECLINED";
    }

    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidOwner() {
        return "Ivanov Ivan";
    }

    public static String getValidCode() {
        return "999";
    }

//INVALID DATA CARD NUMBER
//        - неверный номер карты
public static String getInvalidCardNumber() {
    return fakerEn.number().digits(16);
}
//        - номер карты с 1 цирой
public static String getCardNumberWith1Digit() {
    return "4";
}
//        - номер карты с 15 цифрами
public static String getCardNumberWith15Digits() {
    return "4444 4444 4444 444";
}
//        - латиница вместо цифр в номере карты
//        - кириллица вместо цифр в номере карты
//        - пустые данные
public static String getEmptyCardNumber() {
    return "";
}
//
//        - неверный номер месяца действия карты
//        - месяц больше 12
//        - месяц 0
//        - отрицательный месяц
//        - латиница вместо цифр в поле месяц
//        - кириллица вместо цифр в поле месяц
//        - пустые данные
//
//        - неверный номер года действия карты
//        - год больше текущего
//        - отрицательный год
//        - латиница вместо цифр в поле год
//        - кириллица вместо цифр в поле год
//        - пустые данные
//
//        - неверное заполненное латиницей поле Владелец
//        - кириллица вместо латиницы в поле Владелец
//        - цифры вместо букв в поле Владелец
//        - только имя в поле владелец
//        - фамилия, имя + излишнее отчество
//        - имя фамилия на двух языках
//        - спецсимволы в поле владелец
//        - пустые данные
//
//        - 1 цифра в поле CVC/CVV код
//        - 2 цифры в поле CVC/CVV код
//        - 4 цифры в поле CVC/CVV код
//        - латиница вместо цифр в поле CVC/CVV код
//        - кириллица вместо цифр в поле CVC/CVV код
//        - пустые данные



}
