package com.mbsystems.catalogservice.repository;

import com.mbsystems.catalogservice.config.DataConfig;
import com.mbsystems.catalogservice.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    void findBookByIsbnWhenExisting() {
        //given
        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", 12.90, "Basil.co");

        jdbcAggregateTemplate.insert(book);

        //when
        Optional<Book> results = this.bookRepository.findByIsbn(bookIsbn);

        //then
        assertThat(results).isPresent();
        assertThat(results.get().isbn()).isEqualTo(book.isbn());
    }

    @Test
    void existsByIsbn() {
    }

    @Test
    void deleteByIsbn() {
    }
}