package br.unipam.locadora.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.unipam.locadora.dao.ClienteDAO;
import br.unipam.locadora.entity.Cliente;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@RequestScoped
public class ClienteController {

	@EJB
    private ClienteDAO clienteDAO;
    
    private Cliente cliente;
    
    public ClienteController(){
        cliente = new Cliente();
    }
    
    public void salvar(){
        
        String erro = clienteDAO.salvar(cliente);
        
        if(erro == null){ 
            cliente = new Cliente();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void editar(Cliente cliente){
        this.cliente = cliente;
    }
    
    public void excluir(Cliente cliente){
        
        String erro = clienteDAO.excluir(cliente.getIdCliente());
        
        if(erro == null){
            MensagemUtil.addMensagemInfo("Excluido com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao excluir: " + erro);
        }
    }
    
    public List<Cliente> listar(){
        return clienteDAO.todos();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
