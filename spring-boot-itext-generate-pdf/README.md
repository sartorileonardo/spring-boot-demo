# spring-boot-itext

> Spring boot with IText to generate a simple PDF report

## BookResource.java
```java

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
```

## GeneratePdfReport.java
```java
@Slf4j
public class GeneratePdfReport {

    public static ByteArrayInputStream booksReport(List<Book> books) {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            PdfPTable table = getPdfPTable(4, 1, 8, 8, 3);
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            createHeaderColumns(table, headFont);

            books.stream().forEach( book -> {
                createCellsOfTable(table, book);
            });

            PdfWriter.getInstance(doc, out);
            doc.open();
            addHeader(doc);
            doc.add(table);
            addFooter(doc);
            addGeneratedAtDate(doc);
            doc.addTitle("Books Report");
            doc.addAuthor("Leonardo Sartori");
            doc.addCreationDate();
            doc.close();

        } catch (DocumentException | URISyntaxException ex){
            log.error("Error ocurred: {0}", ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addGeneratedAtDate(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_RIGHT);

        paragraph.add(Paragraph.getInstance(0, "Generated at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE), FontFactory.getFont(FontFactory.COURIER)));
        document.add(paragraph);
    }

    private static void createCellsOfTable(PdfPTable table, Book book) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(book.getId().toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(book.getName()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(book.getAuthor().toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingLeft(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(book.getCost().toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(5);
        table.addCell(cell);
    }

    private static void createHeaderColumns(PdfPTable table, Font headFont) {
        PdfPCell headerCell;
        headerCell = new PdfPCell(new Phrase("Id", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Name", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Author", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Price", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(headerCell);
    }

    private static PdfPTable getPdfPTable(int numColumns, int... lengthColumns) throws DocumentException {
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(80);
        table.setWidths(lengthColumns);
        return table;
    }

    private static void addFooter(Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(14);

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(Paragraph.getInstance(0, "End of report", font));

        PdfPCell cellFinishedReport = new PdfPCell();
        cellFinishedReport.setFixedHeight(30);
        cellFinishedReport.setPhrase(paragraph);
        cellFinishedReport.setHorizontalAlignment(1);

        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.addCell(cellFinishedReport);
        pdfPTable.setPaddingTop(50);
        pdfPTable.setHorizontalAlignment(1);

        document.add(pdfPTable);
    }

    private static void addHeader(Document document) throws DocumentException, URISyntaxException {

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(28);

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(Paragraph.getInstance(0, "Books Report", font));
        paragraph.setPaddingTop(100);

        PdfPCell cellImage = new PdfPCell();
        addImage(cellImage);

        PdfPCell cellTitleReport = new PdfPCell();
        cellTitleReport.setPhrase(paragraph);
        cellTitleReport.setPadding(50);

        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.addCell(cellImage);
        pdfPTable.addCell(cellTitleReport);

        document.add(pdfPTable);
    }

    private static void addImage(PdfPCell cellImage) throws DocumentException {
        String imageDirectory = "images/logo.png";
        try {
            cellImage.setImage(Image.getInstance(imageDirectory));
        } catch (IOException e) {
            log.error("Ocurred error to load image from directory: " + imageDirectory + "\n" + e.getMessage());
        }
    }

}


```


## BookRepository.java
```java
public interface BookRepository extends JpaRepository<Book, Integer> {
}
```

## BookRepositoryTest.java
```java

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;

    @Test
    public void getAllBooks(){
        assertNotNull(bookRepository.findAll());
    }

    @Test
    public void getOneBook(){
        Integer bookId = 1;
        assertNotNull(bookRepository.findById(bookId));
    }

    @Test
    public void addBook(){
        PublishingCompany publishingCompany = publishingCompanyRepository.getById(1);
        List<Author> authors = authorRepository.findAllById(List.of(1, 2));
        Integer bookId = 5;
        Book book = new Book(bookId, UUID.randomUUID().toString(), "Inteligência artificial", new BigDecimal("29.90"), authors, publishingCompany);
        Book savedBook = bookRepository.save(book);
        assertEquals(bookId, savedBook.getId());
    }


    @Test
    public void updateBook(){
        Integer bookId = 4;
        Book book = bookRepository.findById(bookId).get();
        BigDecimal newCost = new BigDecimal("75.20");
        book.setCost(newCost);
        Book bookUpdated = bookRepository.save(book);
        assertEquals(newCost, bookUpdated.getCost());
    }

    @Test
    public void deleteBook(){
        Integer bookId = 4;
        bookRepository.deleteById(bookId);
        assertTrue(bookRepository.findById(bookId).isEmpty());
    }

}
```


## application.yml

```properties
server.port=8888
spring.datasource.initialize=true
spring.datasource.platform=h2
spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
server.error.include-message=always
spring.main.banner-mode="off"
logging.level.org.springframework=ERROR
```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>

## Report Output
![print_report](images/print_report.png)