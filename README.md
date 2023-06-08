# Шаги для подключения проекта к ReportPortal

1. Вспомнить, что мы в НЕПРАВИЛЬНОЙ стране, которая слишком много о себе возомнила, и запустить VPN;
2. Проследовать на официальный сайт ReportPortal "Installation" и ознакомиться с инструкцией по запуску образа на разных системах, в моём случае - Windows;
3. Найти по ссылкам файл Docker-compose на официальном репозитории и добавить его в проект с нужными измененями, т.е. закомментировать пункты "for Unix users" и раскомментировать "for Windows users" в разделах "postgres > volumes", "minio > volumes" (также рекомендуется заменить пароль для superadmin);
4. Далее на официальном сайте выбрать нужную тестовую платформу (в моём случае - JUnit5) и перейти по ссылке в соответствующий раздел официального репозитория, где представлены инструкции по интеграции в файле README (дальнейшие шаги воспроизводятся оттуда);
 - В папке test/resources проекта создать папку META-INF/services, по этому пути добавить файл "org.junit.jupiter.api.extension.Extension" со строкой "com.epam.reportportal.junit5.ReportPortalExtension": это самый простой способ обработки расширений без аннотирования тестовых методов/классов;
 - Далее дополнить файл build.gradle:
repositories {
    mavenLocal()
    mavenCentral()
}

testImplementation 'com.epam.reportportal:agent-java-junit5:5.1.7'

опционально для автоматической регстрации расширений добавить следующее:

test {
    useJUnitPlatform()
    systemProperty 'junit.jupiter.extensions.autodetection.enabled', true
}

 - Теперь можно добавить зависимость самого ReportPortal;
 - Также добавляем зависимость wrapper'а для логирования процесса (в моём случае это log4j);
 - Затем добавляем объекты класса выбранного нами логера в тестовые классы: private static final Logger LOGGER = LogManager.getLogger(MyTests.class); в тело тестовых методов - информацию по конкретным тестам для вывода: LOGGER.info("Информация о тесте");
 - После чего по пути src/main/resources создаём файл "log4j2.xml", через который в проект добавляется ReportPortalAppender с настройкой (не вижу смысла копипастить содержимое оного, суть и так понятна);
 - Теперь можно проследовать в браузер (адрес по умолчанию - http://localhost:8080), где мы настраиваем весь проект через интефейс ReportPortal, воспользоваашись данными из пункта 2 для логина под администратором;
 - Находим вкладку с профилем администратора (текущего пользователя) слева внизу, в ней создаём проект, настраиваем нужные пункты через форму, также опционально добавлем других пользователей в проект;
 - Тут возникает некторая вариативность: для создания файла reportportal.properties по пути test/resources мы используем данные или админа, или добавленного пользователя, скопировав со страницы профиля пользователей пункты endpoint, uuid, launch, project, enable с приставкой "rp." перед каждым;
 - Ещё в руковдостве утверждалось, что платформа позволяет следить за ходом выполнения тестов в рельном времени, но у меня логи появились лишь спустя некоторое время после обновления вкладки "Launches";

5. ???
6. PROFIT!


Зорин В. [QAMID-54] — ДЗ к занятию «4.1. Reporting» (на базе 3.2)
[![Build status](https://ci.appveyor.com/api/projects/status/2im807tff501kxva?svg=true)](https://ci.appveyor.com/project/Gvynhartt/hw-patterns-delivery-form-new-date)
