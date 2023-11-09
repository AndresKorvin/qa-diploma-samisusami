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
public static String getCardNumberWithLatinText() {
    return "qwer tyui asdf ghjk";
}
//        - кириллица вместо цифр в номере карты
public static String getCardNumberWithCyrillicText() {
    return "йцук енгш фыва прол";
}
//        - пустые данные
public static String getEmpty() {
    return "";
}
//
//        - неверный номер месяца действия карты
public static String getInvalidMonth() { return LocalDate.now().minusDays(35).format(DateTimeFormatter.ofPattern("MM")); }
//        - месяц больше 12
public static String getMonthNum13() { return "13"; }
//        - месяц 0
public static String getZeroMonth() { return "00"; }
//        - латиница вместо цифр в поле месяц
public static String getMonthWithLatinText() { return LocalDate.now().format(DateTimeFormatter.ofPattern("MMM")); }
//        - кириллица вместо цифр в поле месяц
public static String getMonthWithCyrillicText() { return LocalDate.now().format(DateTimeFormatter.ofPattern("МММ")); }
//        - пустые данные
//
//        - неверный номер года действия карты
public static String getPastYear() { return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")); }
//        - год больше текущего
public static String getFutureYear() { return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy")); }
//        - латиница вместо цифр в поле год
public static String getYearWithLatinText() {
    return "ye";
}
//        - кириллица вместо цифр в поле год
public static String getYearWithCyrillicText() {
    return "дв";
}
//        - пустые данные
//
//        - неверное заполненное латиницей поле Владелец
public static String getnIvalidFullName() {
    return fakerEn.name().fullName();
}
//        - кириллица вместо латиницы в поле Владелец
public static String getCyrillicDataOwner() {
    return "Иван Иванов";
}
//        - цифры вместо букв в поле Владелец
public static String getOwnerWithDigits() {
    return fakerEn.number().digits(10);
}
//        - только имя в поле владелец
public static String getOnlyNameOwner() {
    return "Ivan";
}
//        - фамилия, имя + излишнее отчество
public static String getFullName() {
    return "Ivan Ivanovich Ivanov";
}
//        - спецсимволы в поле владелец
public static String getOwnerWithSpecialChars() {
    return "!№%?*";
}
//        - пустые данные
//
//        - 2 цифры в поле CVC/CVV код
public static String getInvalidFormatCodeWhith2Digits() {
    return fakerEn.number().digits(2);
}
//        - латиница вместо цифр в поле CVC/CVV код
public static String getCodeWithLatinText() {
    return "cod";
}
//        - кириллица вместо цифр в поле CVC/CVV код
public static String getCodeWithCyrillicText() {
    return "код";
}
//        - спецсимволы в поле CVC/CVV код
public static String getCvcWithSpecialChars() {
    return "!99";
}
//        - пустые данные



}
