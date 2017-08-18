package br.unipam.locadora.dao;

import javax.ejb.Stateless;

import br.unipam.locadora.entity.Opcional;

@Stateless
public class OpcionalDAO extends GenericDAO<Opcional> {

	public OpcionalDAO() {
		super(Opcional.class);
	}

}
