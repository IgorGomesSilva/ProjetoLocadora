package br.unipam.locadora.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Opcional  implements Serializable{


	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Short idOpcional;
    
    @NotNull(message="Informe a descriçao")
    private String descricao;
    
    @ManyToMany(mappedBy="opcionais")
    private List<Veiculo> veiculos;

	public Short getIdOpcional() {
		return idOpcional;
	}

	public void setIdOpcional(Short idOpcional) {
		this.idOpcional = idOpcional;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	
	
	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOpcional == null) ? 0 : idOpcional.hashCode());
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
		Opcional other = (Opcional) obj;
		if (idOpcional == null) {
			if (other.idOpcional != null)
				return false;
		} else if (!idOpcional.equals(other.idOpcional))
			return false;
		return true;
	}
    
   
}
