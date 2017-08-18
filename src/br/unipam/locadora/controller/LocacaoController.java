package br.unipam.locadora.controller;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.unipam.locadora.dao.LocacaoDAO;
import br.unipam.locadora.dao.VeiculoDAO;
import br.unipam.locadora.entity.Locacao;
import br.unipam.locadora.entity.LocacaoVeiculo;
import br.unipam.locadora.entity.Veiculo;
import br.unipam.locadora.util.GraficoLinha;
import br.unipam.locadora.util.MensagemUtil;

@ManagedBean
@SessionScoped
public class LocacaoController implements Serializable{


	private static final long serialVersionUID = 1L;

	@EJB
    private LocacaoDAO locacaoDAO;
	@EJB
    private VeiculoDAO veiculoDAO;
	
    private Locacao locacao;
    private LocacaoVeiculo locacaoVeiculo;
    private Integer ano;
    
    private LineChartModel graficoLinha;
    
    private List<Locacao> locacoes;
    
    public LocacaoController(){
        locacao = new Locacao();
        locacaoVeiculo = new LocacaoVeiculo();
        graficoLinha = new LineChartModel();
    }
    
    public void salvar(){
        
        String erro = locacaoDAO.salvar(locacao);
        
        if(erro == null){ 
            locacao = new Locacao();
            locacaoVeiculo = new LocacaoVeiculo();
            MensagemUtil.addMensagemInfo("Salvo com sucesso!");
        }else{
            MensagemUtil.addMensagemInfo("Erro ao salvar: " + erro);
        }
    }
   
    public void listar(){
        locacoes =  locacaoDAO.listar(locacao.getCliente(), locacao.getDataInicio());
    }
    
    
    public void relatorio() throws Exception{
        ByteArrayOutputStream output = locacaoDAO.relatorio(locacao.getCliente(), locacao.getDataInicio());
        
        
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        res.setContentType("application/pdf");  

        res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");     
        res.getOutputStream().write(output.toByteArray());  
      //  res.getCharacterEncoding();  
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void ficha(int idLocacao) throws Exception{
        ByteArrayOutputStream output = locacaoDAO.ficha(idLocacao);
        
        
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        res.setContentType("application/pdf");  

        res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");   
        res.getOutputStream().write(output.toByteArray());  
       // res.getCharacterEncoding();  
        FacesContext.getCurrentInstance().responseComplete();
    }

    
    public void adicionar(){
    	
    	if(locacao.getLocacaoVeiculos() == null){
    		locacao.setLocacaoVeiculos(new ArrayList<LocacaoVeiculo>());
    	}
    	
    	boolean achou = false;
    	for (LocacaoVeiculo l : locacao.getLocacaoVeiculos()) {
			
    		if(l.getVeiculo().getIdVeiculo() == locacaoVeiculo.getVeiculo().getIdVeiculo()){
    			Short dias =  (short) (l.getQuantidadeDia() + locacaoVeiculo.getQuantidadeDia());
    			l.setQuantidadeDia(dias);
    			achou= true;
    			break;
    		}
    		
		}
    	
    	if(achou == false){
    		locacao.getLocacaoVeiculos().add(locacaoVeiculo);
    	}
    	
    	locacaoVeiculo = new LocacaoVeiculo();
    }
    
    public PieChartModel graficoPorVeiculo() {
    	PieChartModel model = new PieChartModel();
    	
    	
    	List<Veiculo> veiculos = veiculoDAO.todos();
    	
    	for (Veiculo veiculo : veiculos) {
    		model.set(veiculo.getPlaca() + " - "+veiculo.getModelo().getDescricao(),
    				veiculo.getLocacaoVeiculos().size());
		}
         
    	
         
    	model.setTitle("Quantidade de locações por veículo");
    	model.setLegendPosition("e");
    	model.setShowDataLabels(true);
   
    	return model;
    }
    
    
    public void graficoLinha() {
    	
    	graficoLinha = new LineChartModel();
    	List<Veiculo> veiculos = veiculoDAO.todos();
    	graficoLinha.getAxes().put(AxisType.X, new CategoryAxis("Meses"));
    	for (Veiculo veiculo : veiculos) {
    		 LineChartSeries series = new LineChartSeries();
    		 series.setLabel(veiculo.getPlaca()+ " - "+veiculo.getModelo().getDescricao());
    		 
    		 List<GraficoLinha> linhas = locacaoDAO.listar(ano, veiculo);
    		 
    		 for (GraficoLinha graficoLinha : linhas) {
    			 series.set(graficoLinha.getMes().toString(), graficoLinha.getTotal());
			}
    		
    	    graficoLinha.addSeries(series);
		}
   
    	graficoLinha.setLegendPosition("n");
    	graficoLinha.setShowPointLabels(true);
    }
    
    
    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

	public LocacaoVeiculo getLocacaoVeiculo() {
		return locacaoVeiculo;
	}

	public void setLocacaoVeiculo(LocacaoVeiculo locacaoVeiculo) {
		this.locacaoVeiculo = locacaoVeiculo;
	}

	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public LineChartModel getGraficoLinha() {
		return graficoLinha;
	}

	public void setGraficoLinha(LineChartModel graficoLinha) {
		this.graficoLinha = graficoLinha;
	}
    
    
}
