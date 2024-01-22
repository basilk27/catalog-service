package com.mbsystems.catalogservice.service;

import com.mbsystems.catalogservice.entity.Book;
import com.mbsystems.catalogservice.exceptions.BooAlreadyExistsException;
import com.mbsystems.catalogservice.exceptions.BookNotFoundException;
import com.mbsystems.catalogservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Iterable<Book> viewBookList() {
        return this.bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return this.bookRepository.findByIsbn( isbn )
                .orElseThrow(() -> new BookNotFoundException( isbn ));
    }

    public Book addBookToCatalog(Book aBook) {
        if (this.bookRepository.existsByIsbn(aBook.isbn())) {
            throw new BooAlreadyExistsException(aBook.isbn());
        }

        return this.bookRepository.save( aBook );
    }

    public void removeBookFromCatalog(String isbn) {
        this.bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book aBook) {
        return this.bookRepository.findByIsbn(isbn)
                .map( existingBook -> {
                    var updateBook = new Book(existingBook.isbn(), aBook.title(), aBook.author(), aBook.price());
                    return this.bookRepository.save(updateBook);
                })
                .orElseGet(() -> this.addBookToCatalog(aBook));
    }
}
