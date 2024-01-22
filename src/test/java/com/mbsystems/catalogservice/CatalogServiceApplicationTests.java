package com.mbsystems.catalogservice;

import com.mbsystems.catalogservice.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public RestClient restClient() {
            return RestClient.create("https://jsonplaceholder.typicode.com");
        }
    }

    @Autowired
    private RestClient restClient;

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        var bookIsbn = "1231231230";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        Book expectedBook = restClient
                .post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookToCreate)
                .retrieve()
                .body(Book.class);

        Book expectedBook2 = restClient
                .get()
                .uri("/books/" + bookIsbn)
                .retrieve()
                .body(Book.class);

        assertThat(expectedBook2).isNotNull();
        assertThat(expectedBook2.isbn()).isEqualTo(Objects.requireNonNull(expectedBook).isbn());
    }
    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book("1231231231", "Title", "Author", 9.90);

        Book expectedBook2 = restClient
                .post()
                .uri("/books")
                .body(expectedBook)
                .retrieve()
                .body(Book.class);

        assertThat(expectedBook2).isNotNull();
        assertThat(expectedBook2.isbn()).isEqualTo(expectedBook.isbn());
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        var bookIsbn = "1231231232";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        Book createdBook = restClient
                .post()
                .uri("/books")
                .body(bookToCreate)
                .retrieve()
                .body(Book.class);

        assertThat(createdBook).isNotNull();

        var bookToUpdate = new Book(createdBook.isbn(), createdBook.title(), createdBook.author(), 7.95);

        Book bookToUpdate2 = restClient
                .put()
                .uri("/books/" + bookIsbn)
                .body(bookToUpdate)
                .retrieve()
                .body(Book.class);

        assertThat(bookToUpdate2).isNotNull();
        assertThat(bookToUpdate2.price()).isEqualTo(bookToUpdate.price());
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        var bookIsbn = "1231231233";
        var bookToCreate = new Book(bookIsbn, "Title", "Author", 9.90);
        Book bookToCreate2 = restClient
                .post()
                .uri("/books")
                .body(bookToCreate)
                .retrieve()
                .body(Book.class);

        restClient
                .delete()
                .uri("/books/" + bookIsbn)
                .retrieve();

//TODO need to write the error casew
//        Book bookToCreate3 = restClient
//                .get()
//                .uri("/books/" + bookIsbn)
//                .retrieve()
//                .body(List.class);
//
//        .value(errorMessage ->
//                        assertThat(errorMessage).isEqualTo("The book with ISBN " + bookIsbn + " was not found.")
//                );
    }

}
