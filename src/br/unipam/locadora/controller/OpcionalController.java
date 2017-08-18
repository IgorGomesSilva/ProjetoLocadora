package br.unipam.locadora.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.unipam.locadora.dao.OpcionalDAO;
import br.unipam.locadora.entity.Opcional;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@RequestScoped
public class OpcionalController {

	@EJB
    private OpcionalDAO opcionalDAO;
    
    private Opcional opcional;
    
    public OpcionalController(){
        opcional = new Opcional();
    }
    
    public void salvar(){
        
        String erro = opcionalDAO.salvar(opcional);
        
        if(erro == null){ 
            opcional = new Opcional();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void editar(Opcional opcional){
        this.opcional = opcional;
    }
    
    public void excluir(Opcional opcional){
        
        String erro = opcionalDAO.excluir(opcional.getIdOpcional());
        
        if(erro == null){
            MensagemUtil.addMensagemInfo("Excluido com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao excluir: " + erro);
        }
    }
    
    public List<Opcional> listar(){
        return opcionalDAO.todos();
    }

    public Opcional getOpcional() {
        return opcional;
    }

    public void setOpcional(Opcional opcional) {
        this.opcional = opcional;
    }
}
