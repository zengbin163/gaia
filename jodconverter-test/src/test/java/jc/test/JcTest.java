package jc.test;

import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.OfficeException;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 张少昆 on 2018/5/16.
 */
public class JcTest {
    @Test
    public void doc2pdf(){
//        File inputFile = new File("document.doc");
//        File outputFile = new File("document.pdf");
//
//// connect to an OpenOffice.org instance running on port 8100
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
//        connection.connect();
//
//// convert
//        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//        converter.convert(inputFile, outputFile);
//
//// close the connection
//        connection.disconnect();
    }

    //FIXME 报错，也许是没有安装openoffice导致的？
    @Test
    public void r1() throws OfficeException{
        File inputFile = new File("d:/集群数据管理详细设计说明书.doc");
        File outputFile = new File("d:/集群数据管理详细设计说明书.pdf");
        JodConverter
                .convert(inputFile)
                .to(outputFile)
                .execute();
    }

    @Test
    public void r2() throws FileNotFoundException, OfficeException{
        InputStream inputStream = new FileInputStream("d:/集群数据管理详细设计说明书.doc");
        OutputStream outputStream = new FileOutputStream("d:/集群数据管理详细设计说明书.pdf");
        JodConverter
                .convert(inputStream)
                .as(DefaultDocumentFormatRegistry.DOC)
                .to(outputStream)
                .as(DefaultDocumentFormatRegistry.PDF)
                .execute();
    }

//    @Test
//    public void r3(){
//        // create a PDF DocumentFormat (as normally configured in document-formats.xml)
//        DocumentFormat customPdfFormat =
//                new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
//        customPdfFormat.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
//
//// now set our custom options
//        Map pdfOptions = new HashMap();
//        pdfOptions.put("EncryptFile", Boolean.TRUE);
//        pdfOptions.put("DocumentOpenPassword", "mysecretpassword");
//        customPdfFormat.setExportOption(DocumentFamily.TEXT, "FilterData", pdfOptions);
//
//// and convert using our custom format
//        converter.convert(inputFile, outputFile, customPdfFormat);
//    }
}
