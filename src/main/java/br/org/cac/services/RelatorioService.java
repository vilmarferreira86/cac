package br.org.cac.services;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	@Value("classpath:/templates/relatorios/doacoes.jasper")
	private Resource resource;

	@Autowired
	private DataSource dataSource;

	public void doRelatorio(OutputStream stream, Date dataInicial, Date dataFinal) {

		JasperPrint jprint = null;
		try {
			 HashMap parametro = new HashMap();
			
			 parametro.put("dataInicio", dataInicial);
			 parametro.put("dataFinal", dataFinal);
			 System.out.println("dataInicial---> "+dataInicial);

			 System.out.println("Parametro---> "+parametro);
			 System.out.println("Data SOurce--->"+dataSource.getConnection());
			jprint = JasperFillManager.fillReport(resource.getInputStream(), parametro,
					dataSource.getConnection());
			JasperExportManager.exportReportToPdfStream(jprint, stream);
		} catch (JRException | IOException | SQLException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
		}
	}

}
