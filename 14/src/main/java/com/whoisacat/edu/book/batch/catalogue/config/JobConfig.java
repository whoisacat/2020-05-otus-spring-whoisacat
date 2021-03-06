package com.whoisacat.edu.book.batch.catalogue.config;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.BookSQL;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;
import com.whoisacat.edu.book.batch.catalogue.service.AuthorService;
import com.whoisacat.edu.book.batch.catalogue.service.BookService;
import com.whoisacat.edu.book.batch.catalogue.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;

@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static final String MIGRATE_BOOK_CATALOGUE_JOB_NAME = "migrateBookCatalogueJob";

    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @StepScope
    @Bean
    public MongoItemReader<Author> mongoAuthorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoAuthorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Genre> mongoGenreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoGenreReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> mongoBookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoBookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorSQL> authorProcessor(AuthorService authorService) {
        return authorService::convert;
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreSQL> genreProcessor(GenreService genreService) {
        return genreService::convert;
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookSQL> bookProcessor(BookService bookService) {
        return bookService::convert;
    }

    @StepScope
    @Bean
    public JpaItemWriter<AuthorSQL> jpaAuthorWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<AuthorSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<GenreSQL> jpaGenreWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<GenreSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<BookSQL> jpaBookWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<BookSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Job migrateBookCatalogueJob(@Qualifier("splitFlow") Flow splitFlow,
            @Qualifier("convertBooks") Step booksConverter) {
        return jobBuilderFactory.get(MIGRATE_BOOK_CATALOGUE_JOB_NAME)
                                .incrementer(new RunIdIncrementer())
                                .start(splitFlow)
                                .next(booksConverter)
                                .end()
                                .listener(new JobExecutionListener() {
                                    @Override
                                    public void beforeJob(JobExecution jobExecution) {
                                        logger.info("Начало job");
                                    }

                                    @Override
                                    public void afterJob(JobExecution jobExecution) {
                                        logger.info("Конец job");
                                    }
                                })
                                .build();
    }

    @Bean
    public Flow splitFlow(@Qualifier("taskExecutor") TaskExecutor taskExecutor,
            @Qualifier("convertAuthorsFlow") Flow convertAuthorsFlow,
            @Qualifier("convertGenresFlow") Flow convertGenresFlow) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor)
                .add(convertAuthorsFlow, convertGenresFlow)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    @Bean
    public Flow convertAuthorsFlow(@Qualifier("convertAuthors") Step authorsConverter) {
        return new FlowBuilder<SimpleFlow>("convertAuthorsFlow")
                .start(authorsConverter)
                .build();
    }

    @Bean
    public Flow convertGenresFlow(@Qualifier("convertGenres") Step genresConverter) {
        return new FlowBuilder<SimpleFlow>("convertGenresFlow")
                .start(genresConverter)
                .build();
    }


    @Bean
    public Step convertAuthors(JpaItemWriter<AuthorSQL> jpaAuthorWriter, ItemReader<Author> mongoAuthorReader,
            ItemProcessor<Author, AuthorSQL> authorProcessor) {
        return stepBuilderFactory.get("convertAuthors")
                .<Author, AuthorSQL> chunk(CHUNK_SIZE)
                .reader(mongoAuthorReader)
                .processor(authorProcessor)
                .writer(jpaAuthorWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }
                    public void afterRead(Author o) {
                        logger.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Author o) {
                        logger.info("Начало обработки");
                    }
                    public void afterProcess(Author o, AuthorSQL o2) {
                        logger.info("Конец обработки");
                    }
                    public void onProcessError(Author o, Exception e) {
                        logger.info("Ошбка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }
                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }
                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public Step convertGenres(JpaItemWriter<GenreSQL> jpaGenreWriter, ItemReader<Genre> mongoGenreReader,
            ItemProcessor<Genre, GenreSQL> genreProcessor) {
        return stepBuilderFactory.get("convertGenres")
                .<Genre, GenreSQL> chunk(CHUNK_SIZE)
                .reader(mongoGenreReader)
                .processor(genreProcessor)
                .writer(jpaGenreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }
                    public void afterRead(Genre o) {
                        logger.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Genre o) {
                        logger.info("Начало обработки");
                    }
                    public void afterProcess(Genre o, GenreSQL o2) {
                        logger.info("Конец обработки");
                    }
                    public void onProcessError(Genre o, Exception e) {
                        logger.info("Ошбка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }
                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }
                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public Step convertBooks(JpaItemWriter<BookSQL> jpaGenreWriter, ItemReader<Book> mongoGenreReader,
            ItemProcessor<Book, BookSQL> genreProcessor) {
        return stepBuilderFactory.get("convertBooks")
                .<Book, BookSQL> chunk(CHUNK_SIZE)
                .reader(mongoGenreReader)
                .processor(genreProcessor)
                .writer(jpaGenreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }
                    public void afterRead(Book o) {
                        logger.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Book o) {
                        logger.info("Начало обработки");
                    }
                    public void afterProcess(Book o, BookSQL o2) {
                        logger.info("Конец обработки");
                    }
                    public void onProcessError(Book o, Exception e) {
                        logger.info("Ошбка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }
                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }
                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }
}
