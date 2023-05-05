package ru.netology.delivery.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.TestDataGenerator;
import ru.netology.delivery.data.UserEntry;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTestsPositive {

    private static Faker faker;

    @BeforeAll
    static void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Positive: short path")
    public void shdTestPositiveShortPath() {

        UserEntry newUser = TestDataGenerator.generateNewUser();

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='Город']")).setValue(newUser.getDeliveryCity());
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // предварительно удаляем введённое по умолчанию значение сочетанием клавиш "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).setValue(TestDataGenerator.generateDateForInput(3));
        // сперва задаём срок на 3 дня позже текущей даты
        $(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).setValue(newUser.getNameSurname());
        $(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).setValue(newUser.getPhoneNumber());
        $(By.xpath("//label[@data-test-id='agreement']")).click();
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"Запланировать\"]")).click();
        $(By.xpath("//div[@data-test-id='success-notification']/descendant::div[text()=\"Успешно!\"]")).should(Condition.appear);
        // проверяем наличие уведомления с нужным текстом
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + TestDataGenerator.generateDateForInput(3))); // а вот и знание русского языка прорезалось

        // собственно, данные заполнены, теперь нужно заменить дату
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // предварительно удаляем введённое ранее значение сочетанием клавиш "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).setValue(TestDataGenerator.generateDateForInput(7));
        // теперь задаём срок на 7 дней позже текущей даты
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"Запланировать\"]")).click(); // снова жмём на кнопку для самых внимательных
        $x("//div[@data-test-id='replan-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")); // теперь опеределям наличие второго уведомления с новым текстом
        $x("//div[@data-test-id='replan-notification']/descendant::button[@role='button']").click();
        // нажимаем на большое красное кнопко
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + TestDataGenerator.generateDateForInput(7))); // после чего должно вылезти
        // предыдущее уведомление, но с новой датой, коей наличие мы и проверяем
    }

    @Test
    @DisplayName("Positive: same date twice")
    public void shdTestPositiveShortPathWithSameDate() {

        UserEntry newUser = TestDataGenerator.generateNewUser();

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='Город']")).setValue(newUser.getDeliveryCity());
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // предварительно удаляем введённое по умолчанию значение сочетанием клавиш "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).setValue(TestDataGenerator.generateDateForInput(3));
        // сперва задаём срок на 3 дня позже текущей даты
        $(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).setValue(newUser.getNameSurname());
        $(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).setValue(newUser.getPhoneNumber());
        $(By.xpath("//label[@data-test-id='agreement']")).click();
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"Запланировать\"]")).click();
        $(By.xpath("//div[@data-test-id='success-notification']/descendant::div[text()=\"Успешно!\"]")).should(Condition.appear);
        // проверяем наличие уведомления с нужным текстом
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + TestDataGenerator.generateDateForInput(3))); // а вот и знание русского языка прорезалось

        // повторно вводим дату
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // предварительно удаляем введённое ранее значение сочетанием клавиш "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).setValue(TestDataGenerator.generateDateForInput(3));
        // задаём ту же дату
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"Запланировать\"]")).click(); // снова жмём на кнопку для самых внимательных
        $x("//div[@data-test-id='replan-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")); // теперь опеределям наличие второго уведомления с новым текстом
        $x("//div[@data-test-id='replan-notification']/descendant::button[@role='button']").click();
        // нажимаем на большое красное кнопко
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + TestDataGenerator.generateDateForInput(3)));
        // текст на уведомлении должен быть тот же
    }
}
