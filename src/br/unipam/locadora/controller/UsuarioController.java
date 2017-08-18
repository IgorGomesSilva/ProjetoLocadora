package br.unipam.locadora.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.unipam.locadora.dao.UsuarioDAO;
import br.unipam.locadora.entity.Usuario;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@RequestScoped
public class UsuarioController {

	@EJB
    private UsuarioDAO usuarioDAO;
    
    private Usuario usuario;
    
    public UsuarioController(){
        usuario = new Usuario();
    }
    
    public void salvar(){
        
        String erro = usuarioDAO.salvar(usuario);
        
        if(erro == null){ 
            usuario = new Usuario();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void editar(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void excluir(Usuario usuario){
        
        String erro = usuarioDAO.excluir(usuario.getIdUsuario());
        
        if(erro == null){
            MensagemUtil.addMensagemInfo("Excluido com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao excluir: " + erro);
        }
    }
    
    public List<Usuario> listar(){
        return usuarioDAO.todos();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
