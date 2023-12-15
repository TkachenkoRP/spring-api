# Новостной блог

Приложение представляет REST API для новостного сервиса.

## Используемые технологии

* REST API приложение, разработанное с использованием Spring MVC.
* Валидация входящих запросов от клиентов.
* AOP (Аспектно-ориентированное программирование).
* Слой приложения для работы с базой данных, использующий Spring Boot Data JPA.
* Спецификации для фильтрации новостей при запросе.
* Маппинг сущностей с использованием MapStruct.
* Документация API 

## Функциональность API
API предоставляет следующие функции:
1. Создание пользователей и управление ими.
2. Создание категорий новостей и управление ими.
3. Создание новостей и управление ими.
4. Создание комментариев для новостей и управление ими.

### Документация API
Документацию API можно найти по следующим ссылкам:
* Для локального запуска: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* В Docker-контейнере: [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)