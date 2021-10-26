package com.itext.demo.utils;

import com.itext.demo.entity.Book;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
