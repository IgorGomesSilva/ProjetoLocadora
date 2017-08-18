package br.unipam.locadora.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Veiculo  implements Serializable{


	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Short idVeiculo;
    
    @NotNull(message = "Informe a placa")
    private String placa;
    
    @NotNull(message = "Informe o valor")
    private Double valor;
    
    @NotNull(message = "Informe o modelo")
    @ManyToOne
    @JoinColumn(name="idModelo")
    private Modelo modelo;
    
    @ManyToMany
    @JoinTable(name="veiculo_opcional",
    	joinColumns = @JoinColumn(name="idVeiculo"),
    	inverseJoinColumns = @JoinColumn(name="idOpcional"))
    private List<Opcional> opcionais;
    
    @OneToMany(mappedBy="veiculo")
    private List<LocacaoVeiculo> locacaoVeiculos;

	public Short getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Short idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Opcional> getOpcionais() {
		return opcionais;
	}

	public void setOpcionais(List<Opcional> opcionais) {
		this.opcionais = opcionais;
	}

	public List<LocacaoVeiculo> getLocacaoVeiculos() {
		return locacaoVeiculos;
	}

	public void setLocacaoVeiculos(List<LocacaoVeiculo> locacaoVeiculos) {
		this.locacaoVeiculos = locacaoVeiculos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVeiculo == null) ? 0 : idVeiculo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (idVeiculo == null) {
			if (other.idVeiculo != null)
				return false;
		} else if (!idVeiculo.equals(other.idVeiculo))
			return false;
		return true;
	}
  
    
    
}