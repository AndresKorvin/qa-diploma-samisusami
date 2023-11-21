## Дипломный проект профессии «Тестировщик ПО»

## Описание процедуры запуска тестов

### Начало работы

- Клонировать проект с Github себе на ПК. Ссылка на проект https://github.com/AndresKorvin/qa-diploma-samisusami

Для запуска тестов на  ПК должно быть установлено следующее ПО:
- IntelliJ IDEA
- Git
- Docker Desktop
- Google Chrome (или другой браузер)



### Запуск тестов
проводиться командами в терминале IntelliJ IDEA
1. Для формирования окружения запустить три контейнера: c MySQL, с PostgreSQL и эмулятором банковских сервисов командой:  
   **docker-compose up**

2. Проверить, что контейнеры запустились командой:  
   **docker-compose ps**  
   Ожидаемый статус контейнеров: **UP**

3. Запустить SUT командой:

   | для MySQL                                                                        | для PostgreSQL                                                                         |
   |---------|------------|
   | java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar | java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar |

4. Запустить авто-тесты командой в терминале:

    | для MySQL                                                                | для PostgreSQL |
    |--------------------------------------------------------------------------|----------|
    | ./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"  | ./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app" |

    Сервис будет доступен в браузере по адресу: *http://localhost:8080/*  


5. Генерируем отчёт по итогам тестирования с помощью **Allure** с помощью команды: **./gradlew allureServe**  
    При завершении работы с отчетом остановить работу **allureServe** в терминале сочетанием клавиш *CTRL + C*