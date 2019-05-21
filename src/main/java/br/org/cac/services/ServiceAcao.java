package br.org.cac.services;

import org.springframework.stereotype.Service;

import br.org.cac.DTO.AcaoDTO;
import br.org.cac.arquitetura.GenericeService;
import br.org.cac.models.Acao;
import br.org.cac.repositories.AcaoRepository;

@Service
public class ServiceAcao extends GenericeService<Acao, AcaoRepository, AcaoDTO> {

}
