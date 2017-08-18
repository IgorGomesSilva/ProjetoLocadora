package br.unipam.locadora.util;

public class GraficoLinha {

	
	public GraficoLinha(Integer mes, Double total) {
		super();
		this.mes = mes;
		this.total = total;
	}

	private Integer mes;
	
	private Double total;

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
