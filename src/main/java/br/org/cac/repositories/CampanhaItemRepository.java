package br.org.cac.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.org.cac.models.Campanha;
import br.org.cac.models.CampanhaItem;

public interface CampanhaItemRepository extends JpaRepository<CampanhaItem, Integer>, JpaSpecificationExecutor<CampanhaItem>{

	List<CampanhaItem> findAllByCampanha(Campanha campanha);

}
