package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.date.Helper.name;
import static ru.netology.date.Helper.*;


public class CardDeliveryOrderDateChange {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void mustScheduleDateAndChangeItToAnotherOneWithConfirmation() {
        String date1 = data();
        String date2 = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(date1);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().append(date2);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(textCaseSensitive("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date2))
                .shouldHave(visible, Duration.ofSeconds(15));

    }
    @Test
    void mustScheduleDateAndChangeItToTheSameDateWithConfirmation() {
        String date1 = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(date1);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().append(date1);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(textCaseSensitive("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible, Duration.ofSeconds(15));

    }

    @Test
    void mustScheduleDate() {
        String date1 = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(date1);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1));
    }

    @Test
    void RedWarningLabelShouldAppearWhenEnteringNonAdministrativeCity() {
        $("[data-test-id=city] input").setValue(invalidCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void RedWarningMessageShouldAppearIfYouEnterAnIncorrectDateFormat() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(invalidData());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Неверно введена дата"));
    }

    @Test
    void ItShouldShowThatThereIsNoValidValueForThePhoneNumber() {
        String data = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(invalidPhone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + data));

    }
    @Test
    void RedWarningMessageShouldAppearWhenTheCitiesFieldIsEmpty1() {
    $("[data-test-id=city] input").setValue(validCity());
    $("[data-test-id=date] input").doubleClick().append(data());
    $("[data-test-id=name] input").setValue(invalidName());
    $("[data-test-id=phone] input").setValue(phone());
    $("[data-test-id=agreement] span.checkbox__box").click();
    $$("button").find(exactText("Запланировать")).click();
    $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
            .shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void RedWarningMessageShouldAppearWhenTheCitiesFieldIsEmpty() {
        $("[data-test-id=city] input").setValue(invalidCityZero());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phoneZero());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void RedWarningMessageShouldAppearWhenTheFirstNameLastNameFieldIsEmpty() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(invalidNameZero());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void RedWarningMessageShouldAppearWhenThePhoneFieldIsEmpty() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phoneZero());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void RedWarningMessageShouldAppearWhenFillingInFieldWithDateEarlierThanThreeDaysFromCurrentDate() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(invalidDataMinusDaysThree());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void RedWarningMessageShouldAppearIfThereIsNoConsentCheckMark() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid span.checkbox__text")
                .shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
