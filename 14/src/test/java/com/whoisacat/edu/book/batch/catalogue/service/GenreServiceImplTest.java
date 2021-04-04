package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.repository.mongo.GenreMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@DisplayName("Сервис для работы с жанрами должен")
@Import(GenreServiceImpl.class)
@DataMongoTest
class GenreServiceImplTest {

    @Autowired GenreServiceImpl service;

    @MockBean GenreMongoRepository repository;
}
