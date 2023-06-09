package ru.netology.delivery.data;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import com.github.javafaker.Faker;
import lombok.Value;
import lombok.experimental.UtilityClass;

@SuppressWarnings("Lombok")
@UtilityClass
public class TestDataGenerator {
    public static String generateDateForInput(long daysToAdd) {
        String datePattern = "dd.MM.yyyy";
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(datePattern));
    }

    public static String generateCity() {
        String[] regionalCentres = {"Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород", "Биробиджан",
                "Благовещенск", "Брянск", "Великий Новгород", "Владивосток", "Владикавказ", "Владимир", "Волгоград", "Вологда",
                "Воронеж", "Санкт-Петербург", "Горно-Алтайск", "Грозный", "Екатеринбург", "Иваново", "Ижевск",
                "Иркутск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Калуга", "Киров", "Кострома", "Краснодар",
                "Красноярск", "Курган", "Курск", "Кызыл", "Липецк", "Магадан", "Магас", "Майкоп", "Махачкала",
                "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород", "Новосибирск", "Омск",
                "Орёл", "Оренбург", "Пенза", "Пермь", "Петрозаводск", "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону",
                "Рязань", "Салехард", "Самара", "Санкт-Петербург", "Саранск", "Саратов", "Севастополь", "Симферополь", "Смоленск",
                "Ставрополь", "Сыктывкар", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Улан-Удэ", "Ульяновск", "Уфа",
                "Хабаровск", "Ханты-Мансийск", "ЧЕБОКСАРЫ", "Челябинск", "Черкесск", "Чита", "Элиста", "Южно-Сахалинск",
                "Якутск", "Ярославль"
        };
        Random random = new Random();
        int aCertainIndex = random.nextInt(regionalCentres.length);
        String deliveryCity = regionalCentres[aCertainIndex];
        return deliveryCity;
    }

    public static String generateFirstAndLastNames() { // Здесь имеет место некоторый костыль, т.к. отчество вводить не требуется
        Faker faker = new Faker(new Locale("ru"));
        String nameSurname = faker.name().lastName();
        nameSurname = nameSurname + " ";
        nameSurname = nameSurname + faker.name().firstName();
        return nameSurname; // Хотя если его ввести, форма и так сожрёт, хех. #ПРОТЕСТИРОВАНО
        // Букву "ё", кстати, можно отсеять фильтром самого Faker'а при генерации. Но я понятия не имею, как его включить.
    }

    public static String generateCellPhoneNumber() { // а здесь номер почему-то из десяти цифр
        Faker faker = new Faker(new Locale("ru"));
        String phoneNumber = faker.phoneNumber().cellPhone();
        phoneNumber = phoneNumber + faker.number().numberBetween(0, 9); // поэтому тоже костыль
        return phoneNumber;
    }

    public static UserEntry generateNewUser() { // теперь поля объекта нового пользоватеял заполняются в генераторе из методов последнего
        UserEntry newUser = new UserEntry(generateCity(), generateFirstAndLastNames(), generateCellPhoneNumber());
//        newUser.deliveryCity = generateCity();
//        newUser.nameSurname = generateFirstAndLastNames();
//        newUser.phoneNumber = generateCellPhoneNumber();
        return newUser;
    }

    @Data
    @Value
    public static class UserEntry {
        String deliveryCity;
        String nameSurname;
        String phoneNumber;
    }
}
