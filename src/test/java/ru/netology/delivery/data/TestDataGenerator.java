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
        String[] regionalCentres = {"������", "�������", "�����������", "���������", "�������", "��������", "����������",
                "������������", "������", "������� ��������", "�����������", "�����������", "��������", "���������", "�������",
                "�������", "�����-���������", "�����-�������", "�������", "������������", "�������", "������",
                "�������", "������-���", "������", "�����������", "������", "������", "�����", "��������", "���������",
                "����������", "������", "�����", "�����", "������", "�������", "�����", "������", "���������"
                /** ����������, ������, �������, ������ - remove ukrop, ������. � ��� ������ �������� �� �������� � �� ��
                 * ��������������. �-��-��, ������. ������ �����, ��� ������ ��� ��� ����� ������� ������ ���� �����, ���� ��
                 * ����� ��������. ��� ��������� ��� ���� �����, ��� ����� �������� ���� �� �����, ���� ���� � ��� �� ��������.
                 * �� ���� ������. */, "������", "��������", "�������", "������-���", "������ ��������", "�����������", "����",
                "���", "��������", "�����", "�����", "������������", "�������������-����������", "�����", "������-��-����",
                "������", "��������", "������", "�����-���������", "�������", "�������", "�����������", "�����������", "��������",
                "����������", "���������", "������", "�����", "�����", "����", "������", "����-���", "���������", "���",
                "���������", "�����-��������", "���������", "���������", "��������", "����", "������", "����-���������",
                "������", "���������"
        };
        Random random = new Random();
        int aCertainIndex = random.nextInt(regionalCentres.length);
        String deliveryCity = regionalCentres[aCertainIndex];
        return deliveryCity;
    }

    public static String generateFirstAndLastNames() { // ����� ����� ����� ��������� �������, �.�. �������� ������� �� ���������
        Faker faker = new Faker(new Locale("ru"));
        String nameSurname = faker.name().lastName();
        nameSurname = nameSurname + " ";
        nameSurname = nameSurname + faker.name().firstName();
        return nameSurname; // ���� ���� ��� ������, ����� � ��� �����, ���. #��������������
        // ���� �� ����� ����� "�". ������� ������������ ������.
    }

    public static String generateCellPhoneNumber() { // � ����� ����� ������-�� �� ������ ����
        Faker faker = new Faker(new Locale("ru"));
        String phoneNumber = faker.phoneNumber().cellPhone();
        phoneNumber = phoneNumber + faker.number().numberBetween(0, 9); // ������� ���� �������
        return phoneNumber;
    }

    public static UserEntry generateNewUser() {
        return new UserEntry();
    }
}
