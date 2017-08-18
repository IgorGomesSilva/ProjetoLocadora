package br.unipam.locadora.dao;

import javax.ejb.Stateless;

import br.unipam.locadora.entity.Modelo;

@Stateless
public class ModeloDAO extends GenericDAO<Modelo> {

	public ModeloDAO() {
		super(Modelo.class);
	}

}
