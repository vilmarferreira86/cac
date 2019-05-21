package br.org.cac.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import br.org.cac.enums.interfaces.IEnumModel;

public enum PermissoesEnum implements IEnumModel<Integer>{
	//Permissões módulo colaborador
	COLABORADOR_LISTAR(1,"COLABORADOR_LISTAR","/colaborador"),
	COLABORADOR_CRIAR(2,"COLABORADOR_CRIAR","/colaborador/novo"),
	COLABORADOR_SALVAR(3,"COLABORADOR_SALVAR","/colaborador/salvar"),
	COLABORADOR_EDITAR(4,"COLABORADOR_EDITAR","/colaborador/**/editar"),
	COLABORADOR_ALTERAR(5,"COLABORADOR_ALTERAR","/colaborador/**/salvar"),
	COLABORADOR_EXCLUIR(6,"COLABORADOR_EXCLUIR","/colaborador/delete"),
	COLABORADOR_EXIBIR(7,"COLABORADOR_EXIBIR","/colaborador/**/show"),
	
	//Permissões Gerais
	SISTEMA_PROXIMO(8,"SISTEMA_PROXIMO","**/proximo"),
	SISTEMA_ANTERIOR(9,"SISTEMA_ANTERIOR","**/anterior"),
	SISTEMA_BUSCAR_POR(10,"SISTEMA_BUSCAR_POR","**/buscarpor"),
	SISTEMA_RESETAR_BUSCA(11,"SISTEMA_RESETAR_BUSCA","**/resetabusca"),
	SISTEMA_LOGIN(12,"SISTEMA_LOGIN","/login"),
	SISTEMA_LOGOUT(13,"SISTEMA_LOGOUT","/logout"),
	SISTEMA_ERROR(14,"SISTEMA_ERROR","/error"),
	
	
	;
	
	private Integer valor;
	private String descricao;
	private String url;
	
	private PermissoesEnum(Integer valor, String descricao, String url) {
		this.valor = valor;
		this.descricao = descricao;
		this.url = url;
	}

	@Override
	public Integer getValor() {
		return valor;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
	
	public String getUrl() {
		return url;
	}
	
	@Bean
	public static List<PermissoesEnum> listaPermissoes(){
		List<PermissoesEnum> list = new ArrayList<PermissoesEnum>();
		for(PermissoesEnum p : PermissoesEnum.values()) {
			list.add(p);
		}
		return list;
	}
	

}
