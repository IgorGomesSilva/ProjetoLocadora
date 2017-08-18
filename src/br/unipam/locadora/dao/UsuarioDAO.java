package br.unipam.locadora.dao;

import javax.ejb.Stateless;

import br.unipam.locadora.entity.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario> {

	public UsuarioDAO() {
		super(Usuario.class);
	}

}
