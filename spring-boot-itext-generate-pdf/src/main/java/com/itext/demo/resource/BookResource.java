package com.itext.demo.resource;

import com.itext.demo.entity.Book;
import com.itext.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books){
        bookService.saveBooks(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("id")  Integer id){
        Optional<Book> bookSaved = bookService.getBook(id);
        if(!bookSaved.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id")  Integer id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks(){
        bookService.deleteBooks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Integer id) {
        return bookService.getBook(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getBooksReport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Contant-Disposition", "inline; filename=books_report.pdf");
        ResponseEntity<InputStreamResource> body = ResponseEntity
                .ok()
                .header(String.valueOf(headers))
                .contentType(MediaType.APPLICATION_PDF)
                .body(bookService.getContentBooksReport());
        return body;
    }

}
