package br.org.cac.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cac.DTO.DoacaoDTO;
import br.org.cac.arquitetura.AbstractRestController;
import br.org.cac.models.Doacao;
import br.org.cac.services.ServiceDoacao;

@RestController
@RequestMapping("/api/v1/doacoes")
public class DoacaoRest extends AbstractRestController<Doacao, ServiceDoacao, DoacaoDTO> {

	
}
