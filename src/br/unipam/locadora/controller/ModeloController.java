package br.unipam.locadora.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.unipam.locadora.dao.ModeloDAO;
import br.unipam.locadora.entity.Modelo;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@RequestScoped
public class ModeloController {

	@EJB
    private ModeloDAO modeloDAO;
    
    private Modelo modelo;
    
    public ModeloController(){
        modelo = new Modelo();
    }
    
    public void salvar(){
        
        String erro = modeloDAO.salvar(modelo);
        
        if(erro == null){ 
            modelo = new Modelo();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void editar(Modelo modelo){
        this.modelo = modelo;
    }
    
    public void excluir(Modelo modelo){
        
        String erro = modeloDAO.excluir(modelo.getIdModelo());
        
        if(erro == null){
            MensagemUtil.addMensagemInfo("Excluido com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao excluir: " + erro);
        }
    }
    
    public List<Modelo> listar(){
        return modeloDAO.todos();
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}
