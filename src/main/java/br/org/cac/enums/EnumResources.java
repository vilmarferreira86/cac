package br.org.cac.enums;

public enum EnumResources {
	
	COLABORADOR(0, "colaborador");
	
	private Integer valor;
	
	private String descricao;	
	
	private EnumResources(Integer valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}	
	
	public Integer getValor() {
		return valor;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
