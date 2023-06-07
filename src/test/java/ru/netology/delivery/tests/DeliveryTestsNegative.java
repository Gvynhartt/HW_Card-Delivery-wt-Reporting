package ru.netology.delivery.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.TestDataGenerator;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class DeliveryTestsNegative {

    private static Faker faker;

    @BeforeAll
    static void setUpAllFaker() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeAll
    static void setUpAllLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAllLogger() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Negative: replan but with data mismatch")
    public void shdTestReplanningWithMismatchingData() {

        TestDataGenerator.UserEntry newUser = TestDataGenerator.generateNewUser();
        TestDataGenerator.UserEntry newErUser = TestDataGenerator.generateNewUser(); // заводим второго пользлвателя, чтобы при перепланировании ввести несовпадающие данные

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='Город']")).setValue(newUser.getDeliveryCity());
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.BACK_SPACE);
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

        // теперь мы заменяем, например, город, прежде чем заменить дату, и берём мы его из поля второго пользователя (хотя можно и просто сгенерировать)

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@type='text']")).click();
        $(By.xpath("//span[@data-test-id='city']/descendant::input[@type='text']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $(By.xpath("//span[@data-test-id='city']/descendant::input[@type='text']")).sendKeys(Keys.BACK_SPACE);
        // ранее введённое значение также нужно удалить
        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='Город']")).setValue(newErUser.getDeliveryCity());

        // собственно, данные заполнены, теперь нужно заменить дату
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // предварительно удаляем введённое ранее значение сочетанием клавиш "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='Дата встречи']")).setValue(TestDataGenerator.generateDateForInput(7));
        // теперь задаём срок на 7 дней позже текущей даты
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"Запланировать\"]")).click(); // снова жмём на кнопку для самых внимательных
        $x("//div[@data-test-id='replan-notification']/descendant::div[@class='notification__content']")
                .shouldNotBe(Condition.visible); // уведомление о перепланировании не должно появляться
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + TestDataGenerator.generateDateForInput(7))); // после чего должно вылезти
        // предыдущее уведомление, но с новой датой, коей наличие мы и проверяем
    }

}
