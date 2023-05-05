package ru.netology.delivery.data;

import lombok.Data;

@Data
public class UserEntry {
    String deliveryCity = TestDataGenerator.generateCity();
    String nameSurname = TestDataGenerator.generateFirstAndLastNames();
    String phoneNumber = TestDataGenerator.generateCellPhoneNumber();
}