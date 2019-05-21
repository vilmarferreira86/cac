package br.org.cac.enums;

import br.org.cac.enums.interfaces.IEnumModel;

public enum PorPaginaEnum implements IEnumModel<Integer> {
	CINCO_POR_PAGINA(5,"5 por página"),
	DEZ_POR_PAGINA(10,"10 por página"),
	QUIZE_POR_PAGINA(15,"15 por página"),
	VINTE_POR_PAGINA(20,"20 por página")
	;
	
	private Integer valor;
	private String descricao;
	
	PorPaginaEnum(Integer _valor,String _descricao ){
		valor = _valor;
		descricao = _descricao;
	}

	@Override
	public Integer getValor() {
		return valor;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

}
