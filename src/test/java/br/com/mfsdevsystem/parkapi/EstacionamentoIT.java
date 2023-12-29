package br.com.mfsdevsystem.parkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.mfsdevsystem.parkapi.web.dto.EstacionamentoCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.PageableDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts="/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts="/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

	@Autowired
	WebTestClient testClient;
	
	@Test
	public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0").withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isCreated()
		   .expectHeader().exists(HttpHeaders.LOCATION)
		   .expectBody()
		   .jsonPath("placa").isEqualTo("WER-1111")
		   .jsonPath("marca").isEqualTo("FIAT")
		   .jsonPath("modelo").isEqualTo("PALIO 1.0")
		   .jsonPath("cor").isEqualTo("AZUL")
		   .jsonPath("clienteCpf").isEqualTo("09191773016")
		   .jsonPath("recibo").exists()
		   .jsonPath("dataEntrada").exists()
		   .jsonPath("vagaCodigo").exists();
	}
	
	@Test
	public void criarCheckin_ComRoleCliente_RetornarErrorStatus403() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0").withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody()
		   .jsonPath("status").isEqualTo("403")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_ComDadosInvalidos_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("")
				.withMarca("")
				.withModelo("").withCor("")
				.withClienteCpf("")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_DadosDaPlacaInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0").withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_DadosDaMarcaInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_DadosDoModeloInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("")
				.withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_DadosDaCorInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("")
				.withClienteCpf("09191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_DadosDoCpfInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_CpfMaior11Digitos_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("009191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_CpfMenor11Digitos_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("9191773016")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	@Test
	public void criarCheckin_CpfDigitoVerificadorInvalido_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("09191773019")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo("422")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Test
	public void criarCheckin_ComCpfInexistente_RetornarErrorStatus404() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("33838667000")
				.build();
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody()
		   .jsonPath("status").isEqualTo("404")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
		
	}
	
	@Sql(scripts="/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts="/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Test
	public void criarCheckin_ComVagasOcupadas_RetornarErrorStatus404() {
		EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
				.withPlaca("WER-1111")
				.withMarca("FIAT")
				.withModelo("PALIO 1.0")
				.withCor("AZUL")
				.withClienteCpf("09191773016")
				.build();
		
		
		testClient.post()
		   .uri("/api/v1/estacionamentos/check-in")
		   .contentType(MediaType.APPLICATION_JSON)
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( createDto )
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody()
		   .jsonPath("status").isEqualTo("404")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		   .jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void buscarCheckin_ComPerfilAdmin_RetornarDadosStatus200() {
		
		testClient.get()
		   .uri("/api/v1/estacionamentos/check-in/{recibo}","20230313-101300")
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody()
		   .jsonPath("placa").isEqualTo("FIT-1020")
		   .jsonPath("marca").isEqualTo("FIAT")
		   .jsonPath("modelo").isEqualTo("PALIO")
		   .jsonPath("cor").isEqualTo("VERDE")
		   .jsonPath("clienteCpf").isEqualTo("98401203015")
		   .jsonPath("recibo").isEqualTo("20230313-101300")
		   .jsonPath("dataEntrada").isEqualTo("2023-03-13 11:15:00")
		   .jsonPath("vagaCodigo").isEqualTo("A-01");
	}
	
	@Test
	public void buscarCheckin_ComPerfilCliente_RetornarDadosStatus200() {
		
		testClient.get()
		   .uri("/api/v1/estacionamentos/check-in/{recibo}","20230313-101300")
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody()
		   .jsonPath("placa").isEqualTo("FIT-1020")
		   .jsonPath("marca").isEqualTo("FIAT")
		   .jsonPath("modelo").isEqualTo("PALIO")
		   .jsonPath("cor").isEqualTo("VERDE")
		   .jsonPath("clienteCpf").isEqualTo("98401203015")
		   .jsonPath("recibo").isEqualTo("20230313-101300")
		   .jsonPath("dataEntrada").isEqualTo("2023-03-13 11:15:00")
		   .jsonPath("vagaCodigo").isEqualTo("A-01");
	}
	
	@Test
	public void buscarCheckin_ComReciboInexistente_RetornarErrorStatus404() {
		
		testClient.get()
		   .uri("/api/v1/estacionamentos/check-in/{recibo}","20230313-999999")
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody()
		   .jsonPath("status").isEqualTo("404")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20230313-999999")
		   .jsonPath("method").isEqualTo("GET");
	}
	
	/* EstacionamentoController : Checkout */
	
	@Test
	public void criarCheckOut_ComReciboExistente_RetornarSucesso() {
		
		testClient.put()
		   .uri("/api/v1/estacionamentos/check-out/{recibo}","20230313-101300")
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody()
		   .jsonPath("placa").isEqualTo("FIT-1020")
		   .jsonPath("marca").isEqualTo("FIAT")
		   .jsonPath("modelo").isEqualTo("PALIO")
		   .jsonPath("cor").isEqualTo("VERDE")
		   .jsonPath("clienteCpf").isEqualTo("98401203015")
		   .jsonPath("recibo").isEqualTo("20230313-101300")
		   .jsonPath("dataEntrada").isEqualTo("2023-03-13 11:15:00")
		   .jsonPath("vagaCodigo").isEqualTo("A-01")
		   .jsonPath("dataSaida").exists()
		   .jsonPath("valor").exists()
		   .jsonPath("desconto").exists();
		 
	}
	
	@Test
	public void criarCheckOut_ComReciboInexistente_RetornarErrorStatus404() {
		
		testClient.put()
		   .uri("/api/v1/estacionamentos/check-out/{recibo}","20230313-000000") // recibo inexistente
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody()
		   .jsonPath("status").isEqualTo("404")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-000000")
		   .jsonPath("method").isEqualTo("PUT");
	}
	
	@Test
	public void criarCheckOut_ComRoleCliente_RetornarErrorStatus403() {
		
		testClient.put()
		   .uri("/api/v1/estacionamentos/check-out/{recibo}","20230313-101300") // recibo existente
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody()
		   .jsonPath("status").isEqualTo("403")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101300")
		   .jsonPath("method").isEqualTo("PUT");
	}
	
	@Test
	public void buscarEstacionamento_porClienteCpf_RetornarSucesso() {
		
		PageableDto responseBody = testClient.get()
		   .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0","98401203015") // cpf válido
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody(PageableDto.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo( 1 );
		org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo( 2 );
		org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
		org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
		
		responseBody = testClient.get()
				   .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=1","98401203015") // cpf válido
				   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
				   .exchange()
				   .expectStatus().isOk()
				   .expectBody(PageableDto.class)
				   .returnResult().getResponseBody();
				
				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo( 1 );
				org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo( 2 );
				org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
				org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);   

	}
	
	@Test
	public void buscarEstacionamentos_PorClienteCpf_comPerfilCliente_RetornarErrorStatus403() {
		
		testClient.get()
		   .uri("/api/v1/estacionamentos/cpf/{cpf}","98401203015") // cpf válido
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody()
		   .jsonPath("status").isEqualTo("403")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos/cpf/98401203015")
		   .jsonPath("method").isEqualTo("GET");
	}
	
	/*Estacionamento dos Clientes*/
	
	@Test
	public void buscarEstacionamento_DoClienteLogado_RetornarSucesso() {
		
		PageableDto responseBody = testClient.get()
		   .uri("/api/v1/estacionamentos?size=1&page=0") 
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody(PageableDto.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo( 1 );
		org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo( 2 );
		org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
		org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
		
		responseBody = testClient.get()
				   .uri("/api/v1/estacionamentos?size=1&page=1") 
				   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
				   .exchange()
				   .expectStatus().isOk()
				   .expectBody(PageableDto.class)
				   .returnResult().getResponseBody();
				
				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo( 1 );
				org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo( 2 );
				org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
				org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);   

	}
	
	@Test
	public void buscarEstacionamentos_DoClienteLogadoPerfilAdmin_RetornarErrorStatus403() {
		
		testClient.get()
		   .uri("/api/v1/estacionamentos") 
		   .headers( JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody()
		   .jsonPath("status").isEqualTo("403")
		   .jsonPath("path").isEqualTo("/api/v1/estacionamentos")
		   .jsonPath("method").isEqualTo("GET");
	}
}
