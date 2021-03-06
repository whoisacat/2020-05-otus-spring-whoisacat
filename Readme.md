---
Цыпляшов Антон
---
##### Какой-то курс по спрингу в Отусе

###### группа, скорее всего, spring-2020-05 -> spring-2020-11
___
### в папке 1 лежит первая работа
Приложение по проведению тестирования студентов (только вывод вопросов)
Цель: создать приложение с помощью Spring IoC, чтобы познакомиться с основной функциональностью IoC, на которой строится весь Spring.
Результат: простое приложение, сконфигурированное XML-контекстом.
Описание задание:

В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов).
Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.
Приложение должна просто вывести вопросы теста из CSV-файла с возможными вариантами ответа.
 
 Требования:
 0. В приложении должна присутствовать объектная модель (отдаём предпочтение объектам и классам, а не строчкам и массивам/спискам строчек).
 1. Все классы в приложении должны решать строго определённую задачу (см. п. 18-19 "Правила оформления кода.pdf", прикреплённые к материалам занятия).
 2. Контекст описывается XML-файлом.
 3. Все зависимости должны быть настроены в IoC контейнере.
 4. Имя ресурса с вопросами (CSV-файла) необходимо захардкодить строчкой в XML-файле с контекстом.
 5. CSV с вопросами читается именно как ресурс, а не как файл.
 6. Scanner, PrintStream и другие стандартные типы в контекст класть не нужно!
 7. Весь ввод-вывод осуществляется на английском языке.
 8. Крайне желательно написать юнит-тест какого-нибудь сервиса (оцениваться будет только попытка написать тест).
 9. Помним - "без фанатизма".
 
 Опционально (задание со "звёздочкой"):
 1*. Приложение должно корректно запускаться с помощью "java -jar"

___
### в папке 2 лежит вторая работа
Приложение по проведению тестирования студентов (с самим тестированием)
Цель: Цель: конфигурировать Spring-приложения современным способом, как это и делается в современном мире
Результат: готовое современное приложение на чистом Spring
Новый функционал:

Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования.

Выполняется на основе предыдущего домашнего задания + , собственно, сам функционал тестирования.

Требования:
1. Переписать конфигурацию в виде Java + Annotation-based конфигурации.
2. Добавить функционал тестирования студента.
3. Добавьте файл настроек для приложения тестирования студентов.
4. В конфигурационный файл можно поместить путь до CSV-файла, количество правильных ответов для зачёта - на Ваше усмотрение.
5. Если Вы пишите интеграционные тесты, то не забудьте добавить аналогичный файл и для тестов.
6. Scanner, PrintStream и другие стандартные типы в контекст класть не нужно!
7. Ввод-вывод на английском языке.
8. Помним, "без фанатизма" :)

___
### в папке 3 лежит третья работа
Перенести приложение для тестирования студентов на Spring Boot
Цель: Цель: использовать возможности Spring Boot, чтобы разрабатывать современные приложения, так, как их сейчас и разрабатывают.
Результат: Production-ready приложение на Spring Boot
Это домашнее задание выполняется на основе предыдущего.

1. Создать проект, используя Spring Boot Initializr (https://start.spring.io)
2. Перенести приложение проведения опросов из прошлого домашнего задания.
3. Перенести все свойства в application.yml
4. Локализовать выводимые сообщения и вопросы (в CSV-файле). MesageSource должен быть из автоконфигурации Spring Boot.
5. Сделать собственный баннер для приложения.
6. Перенести тесты и использовать spring-boot-test-starter для тестирования

*Опционально:
- использовать ANSI-цвета для баннера.
- если Ваш язык отличается от русского и английского - локализовать в нём.
___
### четвертая работа зачтена в пятой
Перевести приложение для проведения опросов на Spring Shell
Цель: Цель: После выполнения ДЗ вы сможете использовать Spring Shell, чтобы писать интерфейс приложения без Web.
Результат: Приложение на Spring Shell
Домашнее задание выполняется на основе предыдущего.

Необходимо:
1. Подключить Spring Shell, используя spring-starter.
2. Написать набор команд, позволяющий проводить опрос.
3. Написать Unit-тесты с помощью spring-boot-starter-test, учесть, что Spring Shell в тестах нужно отключить.

Набор команд зависит только от Вашего желания. Вы можете сделать одну команду, запускающую Ваш Main, а можете построить полноценный интерфейс на Spring Shell.
___
### в папке 5 лежит пятая работа
Создать приложение хранящее информацию о книгах в библиотеке
Цель: Цель: использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных
Результат: приложение с хранением данных в реляционной БД, которое в дальнейшем будем развивать
Это домашнее задание выполняется НЕ на основе предыдущего.

1. Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД). Настоятельно рекомендуем использовать NamedParametersJdbcTemplate
2. Предусмотреть таблицы авторов, книг и жанров.
3. Предполагается отношение многие-к-одному (у книги один автор и жанр). Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).
4. Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).
5. Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться
с помощью spring-boot-starter-jdbc.
6. Покрыть тестами, насколько это возможно.

Рекомендации к выполнению работы:
1. НЕ делать AbstractDao.
2. НЕ делать наследования в тестах
___
### в папке 6 лежит шестая работа
Переписать приложение для хранения книг на ORM
Цель: Цель: полноценно работать с JPA + Hibernate для подключения к реляционным БД посредством ORM-фреймворка
Результат: Высокоуровневое приложение с JPA-маппингом сущностей
Домашнее задание выполняется переписыванием предыдущего на JPA.

Требования:
1. Использовать JPA, Hibernate только в качестве JPA-провайдера.
2. Для решения проблемы N+1 можно использовать специфические для Hibernate аннотации @Fetch и @BatchSize.
3. Добавить сущность "комментария к книге", реализовать CRUD для новой сущности.
4. Покрыть репозитории тестами, используя H2 базу данных и соответствующий H2 Hibernate-диалект для тестов.
5. Не забудьте отключить DDL через Hibernate
6. @Transactional рекомендуется ставить только на методы сервиса.
___
### в папке 7 лежит седьмая работа
Библиотеку на Spring Data JPA
Цель: Цель: максимально просто писать слой репозиториев с применением современных подходов
Результат: приложение со слоем репозиториев на Spring Data JPA
Домашнее задание выполняется переписыванием предыдущего на JPA.

Требования:
1. Переписать все репозитории по работе с книгами на Spring Data JPA репозитории.
2. Используйте spring-boot-starter-data-jpa.
3. Кастомные методы репозиториев (или с хитрым @Query) покрыть тестами, используя H2.
4. @Transactional рекомендуется ставить на методы сервисов, а не репозиториев.

___
### в папке 8 лежит восьмая работа
Использовать MongoDB и spring-data для хранения информации о книгах
Цель: Цель: После выполнения ДЗ вы сможете использовать Spring Data MongoDB и саму MongoDB для разработки приложений с хранением данных в нереляционной БД.
Результат: Приложение с использованием MongoDB
Задание может выполняться на основе предыдущего, а может быть выполнено самостоятельно

Требования:
1. Использовать Spring Data MongoDB репозитории, а если не хватает функциональности, то и *Operations
2. Тесты можно реализовать с помощью Flapdoodle Embedded MongoDB
3. Hibernate, равно, как и JPA, и spring-boot-starter-data-jpa не должно остаться в зависимостях, если ДЗ выполняется на основе предыдущего.
4. Как хранить книги, авторов, жанры и комментарии решать Вам. Но перенесённая с реляционной базы структура не всегда будет подходить для MongoDB.
___
### в папке 9 лежит девятая работа
CRUD приложение с Web UI и хранением данных в БД
Цель: Цель: разрабатывать полноценные классические Web-приложения
Результат: Web-приложение полностью на стеке Spring
Необходимо:
1. Создать приложение с хранением сущностей в БД (можно взять библиотеку и DAO/репозитории из прошлых занятий)
2. Использовать классический View на Thymeleaf, classic Controllers.
3. Для книг (главной сущности) на UI должны быть доступные все CRUD операции. CRUD остальных сущностей - по желанию/необходимости.
4. Локализацию делать НЕ нужно - она строго опциональна.
___
### в папке 9 лежит и десятая работа тоже
Переписать приложение с использованием AJAX и REST-контроллеров
Цель: Цель: использовать Spring MVC для разработки современных AJAX/SPA приложений c помощью Spring MVC
Результат: современное приложение на стеке Spring
Домашнее задание выполняется на основе предыдущего.

1. Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.
2. Минимум: получение одной сущности и отображение её на странице с помощью XmlHttpRequest, fetch api или jQuery
3. Опционально максимум: полноценное SPA приложение на React/Vue/Angular, только REST-контроллеры.
---
### в папке 11 лежит одиннадцатая работа
Использовать WebFlux
Цель: Цель: разрабатывать Responsive и Resilent приложения на реактивном стеке Spring c помощью Spring Web Flux и Reactive Spring Data Repositories
Результат: приложение на реактивном стеке Spring
1. За основу для выполнения работы можно взять ДЗ с Ajax + REST (классическое веб-приложение для этого луче не использовать).
2. Но можно выбрать другую доменную модель (не библиотеку).
3. Необходимо использовать Reactive Spring Data Repositories.
4. В качестве БД лучше использовать MongoDB или Redis. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
5. RxJava vs Project Reactor - на Ваш вкус.
6. Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
7. Опционально: реализовать на Functional Endpoints
___
### в папке 12 лежит двенадцатая работа
В CRUD Web-приложение добавить механизм аутентификации
Цель: Цель: защитить Web-приложение аутентифкацией и простой авторизацией
Результат: приложение с использованием Spring Security
Внимание! Задание выполняется на основе нереактивного приложения Sping MVC!

1. Добавить в приложение новую сущность - пользователь. Не обязательно реализовывать методы по созданию пользователей - допустимо добавить пользователей только через БД-скрипты.
2. В существующее CRUD-приложение добавить механизм Form-based аутентификации.
3. UsersServices реализовать самостоятельно.
4. Авторизация на всех страницах - для всех аутентифицированных. Форма логина - доступна для всех.
---
### в папке 12 лежит и тринадцатая работа тоже
Ввести авторизацию на основе URL и/или доменных сущностей
Цель: Цель: научиться защищать приложение с помощью полноценной авторизации и разграничением прав доступа
Результат: полноценное приложение с безопасностью на основе Spring Security
Внимание! Задание выполняется на основе нереактивного приложения Sping MVC!

1. Минимум: настроить в приложении авторизацию на уровне URL.
2. Максимум: настроить в приложении авторизацию на основе доменных сущностей и методов сервиса.

Рекомендации по выполнению:
1. Не рекомендуется выделять пользователей с разными правами в разные классы - т.е. просто один класс пользователя.
2. В случае авторизации на основе доменных сущностей и PostgreSQL не используйте GUID для сущностей.
---
### в папке 14 лежит четырнадцатая работа
На основе Spring Batch разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот

Цель: мигрировать данные с помощью Spring Batch Результат: приложение для пакетных обработок данных на Spring Batch

Задание может быть выполнено в отдельном репозитории, с сущностями из ДЗ JPA и MongoDB.
Вы можете выбрать другую доменную модель
Не обязательно добавлять процесс миграции в веб-приложение. Приложение может быть оформлено в виде отдельной утилиты.
Используя Spring Batch, следите, чтобы связи сущностей сохранились.
Опционально: Сделать restart задачи с помощью Spring Shell.
---
### в папке 15 лежит пятнадцатая работа
Реализовать обработку доменной сущности через каналы Spring Integration
Цель:
Участники смогут осуществлять "интеграцию" частей приложения с помощью EIP
Результат: приложение c применением EIP на Spring Integration
Выберите другую доменную область и сущности. Пример: превращение гусеницы в бабочку.
Опишите/сконфигурируйте процесс (IntegrationFlow) с помощью инструментария предоставляемого Spring Integration.
Желательно использование MessagingGateway и subfolw (при необходимости).
---
### в папке 16 лежит шестнадцатая работа
Использовать метрики, healthchecks и logfile
Цель:
Цель: реализовать production-grade мониторинг и прозрачность в приложении
Результат: приложение с применением Spring Boot Actuator
Данное задание выполняется на основе одного из реализованных Web-приложений
Подключить Spring Boot Actuator в приложение.
Включить метрики, healthchecks и logfile.
Реализовать свой собственный HealthCheck индикатор
UI для данных от Spring Boot Actuator реализовывать не нужно.
Опционально: переписать приложение на HATEOAS принципах с помощью Spring Data REST Repository
---
### в папке 16 лежит и семнадцатая работа
Обернуть приложение в docker-контейнер
Цель:
Цель: деплоить приложение в современном DevOps-стеке Результат: обёртка приложения в Docker
Внимание! Задание выполняется на основе любого сделанного Web-приложения
Обернуть приложение в docker-контейнер. Dockerfile принято располагать в корне репозитория. В image должна попадать JAR-приложения. Сборка в контейнере рекомендуется, но не обязательна.
БД в собственный контейнер оборачивать не нужно (если только Вы не используете кастомные плагины)
Настроить связь между контейнерами, с помощью docker-compose
Опционально: сделать это в локальном кубе.
Приложение желательно реализовать с помощью всех Best Practices Docker (логгирование в stdout и т.д.)
---
### в папке 16 лежит и восемнадцатая работа тоже
Обернуть внешние вызовы в Hystrix
Цель:
Цель: сделать внешние вызовы приложения устойчивыми к ошибкам Результат: приложение с изолированными с помощью Hystrix внешними вызовами
Обернуть все внешние вызовы в Hystrix, Hystrix Javanica.
Возможно использование Resilent4j
Возможно использование Feign Client Опционально: Поднять Turbine Dashboard для мониторинга.
---
### в папке 17 лежит проектная работа
Название - Book Sharing provider.
Предполагается взять последнее дз (по книгам), допилить регистрацию, холдера, его места регулярного посещения, личный кабинет для их правки и кнопку взять книгу, а именно:
Проектная работа представляет из себя web-приложение хранящее информацию о книгах у пользователей и позволяющее забронировать интересующие пользователей книги и договориться о передаче книги новому держателю. После бронирования держателем книги становится пользователь-приобретатель. Поиск книг осуществляется с учетом мест регулярного посещения книгодержателей (пока только город, возможно и расстояние от пользователя, бронирующего книгу до мест регярного посещения книгодержателей).
Предусмотрены таблицы авторов, книг, жанров, комментариев, пользователей (книгодержателей), мест регулярного посещения (точек обмена книгами).
Технологически приложение представляет собой JPA CRUD приложение с Web UI (Spring MVC, Thymeleaf) и хранением данных в БД PostgreSQL, в приложении предусмотрена аутентификация и авторизация на основе Spring Security с формой аутентификации и регистрации. В приложении подключен Spring Boot Actuator с метриками healthchecks и logfile, реализован свой собственный HealthCheck индикатор. Готовое приложение в современном DevOps-стеке (docker, docker-compose)
