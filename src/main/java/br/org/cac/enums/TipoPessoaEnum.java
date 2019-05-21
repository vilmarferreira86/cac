package br.org.cac.enums;

import br.org.cac.enums.interfaces.IEnumModel;


public enum TipoPessoaEnum implements IEnumModel<String> {
	FISICA("F", "Pessoa Física"),
	JURIDICA("J", "Pessoa Jurídica");

	private String valor;

	private String descricao;

	private TipoPessoaEnum(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return this.descricao;
	}

	@Override
	public String getValor() {
		return this.valor;
	}
	
	public static TipoPessoaEnum parse(String tPessoa) {
		TipoPessoaEnum tp = null;
		for(TipoPessoaEnum item : TipoPessoaEnum.values()) {
			if(item.getValor().equals(tPessoa)) {
				tp = item;
			}
		}
		return tp;
	}

}
