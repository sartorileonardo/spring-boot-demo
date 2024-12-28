package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.exception.ReportException;
import com.jpa.demo.exception.ResourceNotFoundException;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookService {

    public static final String MSG_NO_RECORDS_FOUND_FOR_THIS_ID = "No records found for this ID!";

    private static final String REPORT_SHEET_NAME = "Book worksheet";
    private static final String REPORT_FILE_NAME = "books-report.xlsx";
    private static final String MSG_ERROR_REPORT = "Failed to generate the report - error: %s";

    private final String[] headersFile = {
            "ID",
            "ISBN",
            "NAME",
            "COST",
            "AUTHOR",
            "PUB. COMPANY",
    };

    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")));
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MSG_NO_RECORDS_FOUND_FOR_THIS_ID));

        entity.setName(book.getName());
        entity.setIsbn(book.getIsbn());
        entity.setCost(book.getCost());
        entity.setAuthor(book.getAuthor());
        entity.setPublishingCompany(book.getPublishingCompany());

        return bookRepository.save(entity);
    }

    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }


    public ResponseEntity<byte[]> getBooksReport() {

        List<Book> books = bookRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = ExcelUtils.createSheet(workbook, REPORT_SHEET_NAME);

            ExcelUtils.createHeaderRow(sheet, headersFile, workbook);

            AtomicInteger rowIndex = new AtomicInteger(1);
            for (Book book : books){
                Object[] rowData = {
                       /* getValorData(book.ge),
                        getValorTexto(book.getCodigo()),
                        *
                        */
                };

                ExcelUtils.populateRow(sheet, rowIndex.getAndIncrement(), rowData);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=%s", REPORT_FILE_NAME));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(out.toByteArray());

        } catch (Exception e) {
            String msgError = String.format(MSG_ERROR_REPORT, e.getMessage());
            throw new ReportException(msgError);
        }
    }
}
