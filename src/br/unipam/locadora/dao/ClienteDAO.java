package br.unipam.locadora.dao;

import javax.ejb.Stateless;

import br.unipam.locadora.entity.Cliente;

@Stateless
public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO() {
		super(Cliente.class);
	}

}
