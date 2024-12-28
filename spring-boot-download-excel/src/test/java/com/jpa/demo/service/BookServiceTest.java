package com.jpa.demo.service;

import com.jpa.demo.entity.Author;
import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.PublishingCompany;
import com.jpa.demo.repository.BookRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private List<Author> authors;
    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        // Given
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(1L)
                .authors(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @Test
    @DisplayName("Should return a list of saved books")
    void getAllBooks() {
        // Given
        given(bookRepository.findAll()).willReturn(List.of(book));

        // When
        List<Book> books = bookService.findAll();

        // Then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Should return an existing book")
    public void getOneBook() {
        // Given
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));

        // When
        Optional<Book> savedBook = bookService.findById(book.getId());

        // Then
        assertTrue(savedBook.isPresent());
        assertEquals(book.getId(), savedBook.get().getId());
    }

    @Test
    @DisplayName("Should save a new book with success")
    void addBook() {
        // Given
        given(bookRepository.save(any(Book.class))).willReturn(book);

        // When
        Book savedBook = bookService.save(book);

        // Then
        assertNotNull(savedBook);
        assertEquals(book.getName(), savedBook.getName());

        // Optionally, verify that the mock was called
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should update an existing book successfully")
    void updateBook() {
        // Given
        book.setName("updatedBookName");
        given(bookRepository.save(any(Book.class))).willReturn(book);

        // When
        Book savedBook = bookService.save(book);

        // Then
        assertNotNull(savedBook);
        assertEquals(book.getName(), savedBook.getName());

        // Optionally, verify that the mock was called
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() {
        // Given
        willDoNothing().given(bookRepository).deleteById(book.getId());

        // When
        bookService.deleteById(book.getId());

        // Then
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    @DisplayName("Should generate a report of books")
    void getBooksReport() throws Exception {
        // Given
        // Simulate a list of books for the report
        given(bookRepository.findAll()).willReturn(List.of(book));

        // When
        ResponseEntity<byte[]> response = bookService.getBooksReport();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());  // Ensure HTTP Status is OK (200)

        byte[] reportContent = response.getBody();
        assertNotNull(reportContent);
        assertTrue(reportContent.length > 0);  // Ensure the report content is not empty

        // Optionally, verify the report content type (Excel file)
        assertTrue(response.getHeaders().getContentType().includes(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM));

        // Optionally, you can verify the content of the generated report
        // For instance, if you want to check the first cell's content:
        try (Workbook workbook = new XSSFWorkbook(new java.io.ByteArrayInputStream(reportContent))) {
            assertEquals("ID", workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
        }
    }
}
