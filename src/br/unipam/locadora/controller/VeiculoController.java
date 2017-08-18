package br.unipam.locadora.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.unipam.locadora.dao.VeiculoDAO;
import br.unipam.locadora.entity.Veiculo;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@RequestScoped
public class VeiculoController {

	@EJB
    private VeiculoDAO veiculoDAO;
    
    private Veiculo veiculo;
    
    public VeiculoController(){
        veiculo = new Veiculo();
    }
    
    public void salvar(){
        
        String erro = veiculoDAO.salvar(veiculo);
        
        if(erro == null){ 
            veiculo = new Veiculo();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void editar(Veiculo veiculo){
        this.veiculo = veiculo;
    }
    
    public void excluir(Veiculo veiculo){
        
        String erro = veiculoDAO.excluir(veiculo.getIdVeiculo());
        
        if(erro == null){
            MensagemUtil.addMensagemInfo("Excluido com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao excluir: " + erro);
        }
    }
    
 
    public List<Veiculo> listar(){
        return veiculoDAO.todos();
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
