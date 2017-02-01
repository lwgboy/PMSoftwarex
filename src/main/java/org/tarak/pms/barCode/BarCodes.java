package org.tarak.pms.barCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class BarCodes {
    public static final String DEST = "E:\\barcode_table12.pdf";
 
    public static void mainx(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new BarCodes().createPdf(DEST);
    }
 
    public void createPdf(String dest) throws IOException, DocumentException 
    {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        long code = 1234567890;
        PdfContentByte cb = writer.getDirectContent();
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        for (int i = 0; i < 64; i++) {
            table.addCell(createBarcode(cb, ""+code++));
        }
        document.add(table);
        document.close();
    }
 
    public static PdfPCell createBarcode(PdfContentByte cb, String code) throws DocumentException, IOException {
    	Barcode128 code128 = new Barcode128();
        code128.setBaseline(-1);
        code128.setSize(12);
        code128.setCode(code);
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        PdfPCell cell = new PdfPCell(code128Image);
        cell.setPadding(10);
        return cell;
    }
}
