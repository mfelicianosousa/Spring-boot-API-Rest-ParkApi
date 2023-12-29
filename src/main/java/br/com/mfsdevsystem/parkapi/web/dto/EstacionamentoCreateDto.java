package br.com.mfsdevsystem.parkapi.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class EstacionamentoCreateDto {

	   @NotBlank
	   @Size(min = 8, max = 8)
	   @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa do veiculo deve seguir o padrão 'XXX-0000'")
		private String placa;
	   
	   @NotBlank
	    private String marca;
	   
	   @NotBlank
	    private String modelo;
	   
	   @NotBlank
	    private String cor;
	   
	   @NotBlank
	   @Size(min=11, max=11)
	   @CPF
	   private String clienteCpf;
	   
	   
		
	 public EstacionamentoCreateDto() {
	  }
	  
	 
	public EstacionamentoCreateDto(
			String placa,String	 marca, String modelo, String cor, String clienteCpf) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.clienteCpf = clienteCpf;
	}
	
	 public static Builder builder() {
	        return new Builder();
	    }
	 
	 public static class Builder {
		 
	        private EstacionamentoCreateDto estacionamentoCreateDto;

	        private Builder() {
	            estacionamentoCreateDto = new EstacionamentoCreateDto();
	        }

	        public Builder withPlaca(String placa) {
	            estacionamentoCreateDto.placa = placa;
	            return this;
	        }

	        public Builder withMarca(String marca) {
	            estacionamentoCreateDto.marca = marca;
	            return this;
	        }

	        public Builder withModelo(String modelo) {
	            estacionamentoCreateDto.modelo = modelo;
	            return this;
	        }

	        public Builder withCor(String cor) {
	            estacionamentoCreateDto.cor = cor;
	            return this;
	        }

	        public Builder withClienteCpf(String clienteCpf) {
	            estacionamentoCreateDto.clienteCpf = clienteCpf;
	            return this;
	        }

	        public EstacionamentoCreateDto build() {
	            return estacionamentoCreateDto;
	        }
	 }
	
	 public EstacionamentoCreateDto build() {
	        return this;
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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getClienteCpf() {
		return clienteCpf;
	}

	public void setClienteCpf(String clienteCpf) {
		this.clienteCpf = clienteCpf;
	}
	   
}
