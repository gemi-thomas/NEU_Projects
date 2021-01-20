/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.View;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;


import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Table;
import javax.swing.text.StyleConstants.FontConstants;

/**
 *
 * @author Gemi
 */
public class PdfPostDetailsView extends AbstractPdfView{

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document dcmnt, PdfWriter writer, 
            HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        
        
        Map<String, String> postInfo =  (Map<String, String>) map.get("brochure");
                
        Table table = new Table(2);
      
        table.addCell("Users No.");
        table.addCell("User Information");
        
        for (Map.Entry<String, String> entry : postInfo.entrySet()) {
			table.addCell(entry.getKey());
			table.addCell(entry.getValue());
                }
        
        Paragraph title = new Paragraph("List of All users using University Marketplace!");
      
        dcmnt.add(title);
        dcmnt.add(table);
                
    }   
    
}
