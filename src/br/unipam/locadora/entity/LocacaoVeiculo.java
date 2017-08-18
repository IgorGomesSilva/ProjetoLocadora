package br.unipam.locadora.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Locacao_Veiculo")
public class LocacaoVeiculo implements Serializable{


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocacaoVeiculoPK id;
	
	@NotNull(message="Informe o valor")
	private Double valor;
	
	@NotNull(message="Informe a quantidade de dias")
	private Short quantidadeDia;
	
	@MapsId("idLocacao")
	@ManyToOne
	@JoinColumn(name="idLocacao")
	private Locacao locacao;
	
	@MapsId("idVeiculo")
	@ManyToOne
	@JoinColumn(name="idVeiculo")
	private Veiculo veiculo;

	public LocacaoVeiculoPK getId() {
		return id;
	}

	public void setId(LocacaoVeiculoPK id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	

	public Short getQuantidadeDia() {
		return quantidadeDia;
	}

	public void setQuantidadeDia(Short quantidadeDia) {
		this.quantidadeDia = quantidadeDia;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocacaoVeiculo other = (LocacaoVeiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
