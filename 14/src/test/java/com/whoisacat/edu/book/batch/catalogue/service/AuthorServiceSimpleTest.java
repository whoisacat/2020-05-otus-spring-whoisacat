package com.whoisacat.edu.book.batch.catalogue.service;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с авторами должен")
@Import(AuthorServiceImpl.class)
@DataMongoTest
class AuthorServiceSimpleTest{

}
