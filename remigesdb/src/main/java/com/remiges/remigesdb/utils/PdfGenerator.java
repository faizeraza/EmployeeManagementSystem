package com.remiges.remigesdb.utils;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.remiges.remigesdb.dto.EmployeeDTO;

import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {

    public void generate(List<EmployeeDTO> empList, HttpServletResponse response) throws DocumentException, IOException {
        // Create a Document object
        Document document = new Document(PageSize.A4);
        // Get PdfWriter instance
        PdfWriter.getInstance(document, response.getOutputStream());
        // Open document
        document.open();

        // Set title font
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD);
        // Title paragraph
        Paragraph title = new Paragraph("List of Employees", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Create a table with 4 columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 3, 3, 3});
        table.setSpacingBefore(10);

        // Set header cell styles
        Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(BaseColor.BLUE);
        headerCell.setPadding(5);

        // Add header cells
        headerCell.setPhrase(new Phrase("ID", headerFont));
        table.addCell(headerCell);
        headerCell.setPhrase(new Phrase("First Name", headerFont));
        table.addCell(headerCell);
        headerCell.setPhrase(new Phrase("Last Name", headerFont));
        table.addCell(headerCell);
        headerCell.setPhrase(new Phrase("Email", headerFont));
        table.addCell(headerCell);

        // Populate table rows
        Font rowFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
        for (EmployeeDTO emp : empList) {
            table.addCell(new PdfPCell(new Phrase(emp.getEmpid(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(emp.getFname(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(emp.getFullname(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(emp.getReportsTo(), rowFont)));
        }

        // Add table to document
        document.add(table);
        // Close document
        document.close();
    }
}
