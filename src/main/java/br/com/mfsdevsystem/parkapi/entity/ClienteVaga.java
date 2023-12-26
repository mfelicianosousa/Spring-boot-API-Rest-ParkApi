package br.com.mfsdevsystem.parkapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@EntityListeners( AuditingEntityListener.class)
@Table(name = "clientes_vagas")
public class ClienteVaga  implements  Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private Long id;
	
	@Column( name = "numero_recibo", nullable = false,  unique= true, length=15)
	private String recibo;

	@Column( name = "placa", nullable = false,  length=8)
	private String placa;
	
	@Column( name = "marca", nullable = false,  length=40)
	private String marca;
	
	@Column( name = "modelo", nullable = false,  length=40)
	private String model;
	
	@Column( name = "cor", nullable = false,  length=40)
	private String cor;
	
	@Column( name = "data_entrada", nullable = false)
	private LocalDateTime dataEntrada;
	
	@Column( name = "data_saida")
	private LocalDateTime dataSaida;
	
	@Column( name = "valor", columnDefinition = "decimal(7,2)")
    private BigDecimal valor;
 
	@Column( name = "valor_desconto", columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "vaga_id", nullable = false)
	private Vaga vaga;
    
	/* dados de auditoria */
	@CreatedDate
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@LastModifiedDate
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	
	@CreatedBy
	@Column(name = "criado_por")
	private String criadoPor;
	
	@LastModifiedBy
	@Column(name = "modificado_por")
	private String modificadoPor;
	
	public ClienteVaga() {
	}
	
	

	public ClienteVaga(Long id, String recibo, String placa, String marca, String model, String cor,
			LocalDateTime dataEntrada, LocalDateTime dataSaida, BigDecimal valor, BigDecimal desconto, Cliente cliente,
			Vaga vaga, LocalDateTime dataCriacao, LocalDateTime dataModificacao, String criadoPor,
			String modificadoPor) {
		this.id = id;
		this.recibo = recibo;
		this.placa = placa;
		this.marca = marca;
		this.model = model;
		this.cor = cor;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.desconto = desconto;
		this.cliente = cliente;
		this.vaga = vaga;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.criadoPor = criadoPor;
		this.modificadoPor = modificadoPor;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteVaga other = (ClienteVaga) obj;
		return Objects.equals(id, other.id);
	}
}
