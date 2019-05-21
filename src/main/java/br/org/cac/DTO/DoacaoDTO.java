package br.org.cac.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import br.org.cac.arquitetura.IDTO;
import br.org.cac.validation.CustomDateConstraint;


public class DoacaoDTO implements IDTO {

	private static final long serialVersionUID = 8943309717046887477L;
	
	private int id;
	
	
	@NotNull(message="DATA: A data da Doação não pode ser nula!")
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@CustomDateConstraint
	private Date cadastro;
	
	//@NotEmpty(message="Campo Total não pode estar em branco!")
	@DecimalMin(value="1.00", message="TOTAL: O valor da doação deve ser maior ou igual a R$ 1,00!")
	@NotNull
	private BigDecimal total;
	
	//@NotEmpty(message="Campo Colaborador não pode estar em branco!")
	@NotNull
	private int colaboradorId;
	private String colaboradorNome;
	
	public DoacaoDTO() {
	}

	public DoacaoDTO(int id, Date cadastro, BigDecimal total, Integer colaboradorId) {
		this.id = id;
		this.cadastro = cadastro;
		this.total = total;
		this.colaboradorId = colaboradorId;
	}
	
	public DoacaoDTO(Date cadastro, BigDecimal total, Integer colaboradorId) {
		this.cadastro = cadastro;
		this.total = total;
		this.colaboradorId = colaboradorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Integer colaboradorId) {
		this.colaboradorId = colaboradorId;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	@Override
	public String toString() {
		return "DoacaoDTO [id=" + id + ", cadastro=" + cadastro + ", total=" + total + ", colaboradorId=" + colaboradorId
				+ "]";
	}
	
	public String getTotalFormatado() {
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.total);
	}
	
	
}
