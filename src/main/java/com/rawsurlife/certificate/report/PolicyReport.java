package com.rawsurlife.certificate.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class PolicyReport {
    
    @Autowired
    private DataSource dataSource;

    

    public String exportFile( String format, int id )  throws FileNotFoundException, JRException, SQLException  {
        
        long elapsedMilliseconds = System.currentTimeMillis();
        String name ="Policy_" + elapsedMilliseconds;
        
        

		String tempDir = System.getProperty("java.io.tmpdir");

        if(  !tempDir.endsWith("/") && !tempDir.endsWith( "\\") ) {
			tempDir = tempDir+"//";
		}

        String path=tempDir+"PolicyFile";

        File file = ResourceUtils.getFile("classpath:kimia_indiv.jrxml");
        
        JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id_param", id );

        
        Connection conn = dataSource.getConnection();

        JasperPrint jasperPrint = JasperFillManager.fillReport( jasper, params, conn );

        /**
         * Check File Format Input
         */
        switch( format.toLowerCase() ) {
            case "html":
                name+=".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path+name);
                break;

            case "pdf":
                name+=".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, path+name);
                break;
            
            case "xlsx":
                name+=".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path+name));
                SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
                //		configuration.setOnePagePerSheet(true);
                reportConfigXLS.setSheetNames(new String[] { "sheet1" });
                reportConfigXLS.setDetectCellType(true);
                reportConfigXLS.setCollapseRowSpan(false);
                reportConfigXLS.setRemoveEmptySpaceBetweenRows(true);
                exporter.setConfiguration(reportConfigXLS);
                exporter.exportReport();
                break;
            
            case "csv":
                name+=".csv";
                JRCsvExporter exp = new JRCsvExporter();
                // Set input ...
                exp.setExporterInput(new SimpleExporterInput(jasperPrint));
                exp.setExporterOutput(new SimpleWriterExporterOutput(path+name));
                exp.exportReport();
                break;

        }               

        return path+name;
    }

}
