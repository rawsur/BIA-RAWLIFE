package com.rawsurlife.certificate.controllers.ResourceController;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.rawsurlife.certificate.report.PolicyReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("api/v1/report/policy/")
@CrossOrigin(origins = "*" , allowedHeaders = "*" )
public class PolicyReportController {
    
    @Autowired
    private PolicyReport report;

    /**
	*Generate Export File in %TMP% directory
     * @throws SQLException
	*/
    @GetMapping(value="export/{format}/{id}", produces="text/plain" )
    private String exportReport(@PathVariable String format , @PathVariable int id ) throws FileNotFoundException, JRException, SQLException{
        return report.exportFile(format,id);
    }

    /**
	*Download File Exported function above
	*/
    @GetMapping("export/file")
    public void downloadFile(String fileName, HttpServletResponse res) throws Exception {
        
        res.setHeader("Content-Disposition","attachment;filename="+fileName);
        res.getOutputStream().write(contentOf(fileName));
    }
    
    private byte[] contentOf(String fileName) throws Exception{
        return Files.readAllBytes(Paths.get(fileName));
    }

}
