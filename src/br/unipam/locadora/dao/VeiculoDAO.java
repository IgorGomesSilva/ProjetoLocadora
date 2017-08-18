package br.unipam.locadora.dao;

import javax.ejb.Stateless;

import br.unipam.locadora.entity.Veiculo;

@Stateless
public class VeiculoDAO extends GenericDAO<Veiculo> {

	public VeiculoDAO() {
		super(Veiculo.class);
	}

}
