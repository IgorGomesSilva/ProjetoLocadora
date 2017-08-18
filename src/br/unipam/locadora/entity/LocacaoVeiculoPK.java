package br.unipam.locadora.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LocacaoVeiculoPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idLocacao;
	private Short idVeiculo;

	public Integer getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(Integer idLocacao) {
		this.idLocacao = idLocacao;
	}

	public Short getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Short idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLocacao == null) ? 0 : idLocacao.hashCode());
		result = prime * result + ((idVeiculo == null) ? 0 : idVeiculo.hashCode());
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
		LocacaoVeiculoPK other = (LocacaoVeiculoPK) obj;
		if (idLocacao == null) {
			if (other.idLocacao != null)
				return false;
		} else if (!idLocacao.equals(other.idLocacao))
			return false;
		if (idVeiculo == null) {
			if (other.idVeiculo != null)
				return false;
		} else if (!idVeiculo.equals(other.idVeiculo))
			return false;
		return true;
	}
	
	
}
