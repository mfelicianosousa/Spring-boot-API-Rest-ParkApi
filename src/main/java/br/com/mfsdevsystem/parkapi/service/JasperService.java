package br.com.mfsdevsystem.parkapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class JasperService {

	private static Logger logger = LoggerFactory.getLogger( JasperService.class );
	private Map<String, Object> params = new HashMap<>();

	
	private static final String JASPER_DIRETORIO = "classpath:reports/"; 
	
	private final ResourceLoader resourceLoader;
	private final DataSource dataSource;
	
	public JasperService( ResourceLoader resourceLoader, DataSource dataSource) {
		this.resourceLoader = resourceLoader;
		this.dataSource = dataSource;
	}

	 public void addParams(String key, Object value) {
	     this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
		 this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));
	        this.params.put(key, value);
	        logger.info("Jasper Reports ::: ", JASPER_DIRETORIO );
	    }

	    public byte[] gerarPdf() {
	        byte[] bytes = null;
	        try {
	            //Resource resource = resourceLoader.getResource( "classpath:/reports/".concat("estacionamentos.jasper"));
	            Resource resource = resourceLoader.getResource( JASPER_DIRETORIO.concat("estacionamentos.jasper"));
	            InputStream stream = resource.getInputStream();
	            JasperPrint print = JasperFillManager.fillReport(stream, params, dataSource.getConnection());
	            bytes = JasperExportManager.exportReportToPdf(print);
	        } catch (IOException | JRException | SQLException e) {
	        	
	            logger.error("Jasper Reports ::: ", e.getCause());
	            throw new RuntimeException(e);
	        }
	        return bytes;
	    }
	
}
