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
                "Красноярск", "Курган", "Курск", "Кызыл", "Липецк", "Магадан", "Магас", "Майкоп", "Махачкала"
                /** Мелитополь, Донецк, Луганск, Херсон - remove ukrop, кстати. А ещё авторы джарника не патриоты и ВС РФ
                 * дискредитируют. А-та-та, однако. Причём пофиг, что писали его для курса минимум четыре года назад, судя по
                 * датам коммитов. Юра Хованский вон тоже думал, что закон обратной силы не имеет, пока щами в пол не положили.
                 * Но едем дальше. */, "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород", "Новосибирск", "Омск",
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
        // Зато не сожрёт букву "ё". Слишком качественная локаль.
    }

    public static String generateCellPhoneNumber() { // а здесь номер почему-то из десяти цифр
        Faker faker = new Faker(new Locale("ru"));
        String phoneNumber = faker.phoneNumber().cellPhone();
        phoneNumber = phoneNumber + faker.number().numberBetween(0, 9); // поэтому тоже костыль
        return phoneNumber;
    }

    public static UserEntry generateNewUser() {
        return new UserEntry();
    }
}
