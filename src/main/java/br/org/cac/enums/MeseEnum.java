package br.org.cac.enums;

import br.org.cac.enums.interfaces.IEnumModel;

public enum MeseEnum implements IEnumModel<Integer> {
	JANEIRO(1,"Janeiro"),
	FEVEREIRO(2,"Feveiro"),
	MARCO(3,"Março"),
	ABRIL(4,"Abril"),
	MAIO(5,"Maio"),
	JUNHO(6,"Junho"),
	JULHO(7,"Julho"),
	AGOSTO(8,"Agosto"),
	SETEMBRO(9,"Setembro"),
	OUTUBRO(10,"Outubro"),
	NOVEMBRO(11,"Novembro"),
	DEZEMBRO(12,"Dezembro");
	
	private Integer id;
	private String nomeMes;
	
	MeseEnum(Integer _id, String _nomeMes){
		id = _id;
		nomeMes = _nomeMes;
	}

	@Override
	public Integer getValor() {
		return id;
	}

	@Override
	public String getDescricao() {
		return nomeMes;
	}

}
