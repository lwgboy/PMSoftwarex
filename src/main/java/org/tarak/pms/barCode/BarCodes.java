package org.tarak.pms.barCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class BarCodes {
    public static final String DEST = "barcode_table.pdf";
 
    public static void mainx(String[] args) throws IOException,
            DocumentException {
       /* File file = new File(DEST);
        file.getParentFile().mkdirs();*/
        List<String> code = new ArrayList<String>();
        code.add("1234567890");
        createPdf(DEST,code);
    }
 
    public static void createPdf(String dest,List<String> codes) throws IOException, DocumentException 
    {
    	if(codes!=null)
        {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        
        PdfContentByte cb = writer.getDirectContent();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        
        	
        
        for (String code:codes) {
            table.addCell(createBarcode(cb, code));
        }
        /*for (int i = 0; i < 12; i++) {
            table.addCell(createBarcode(cb, String.format("%08d", i)));
        }*/
        document.add(table);
        document.close();
        }
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
