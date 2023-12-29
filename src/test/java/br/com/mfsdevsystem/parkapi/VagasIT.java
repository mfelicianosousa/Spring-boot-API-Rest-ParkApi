package br.com.mfsdevsystem.parkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.mfsdevsystem.parkapi.web.dto.UsuarioCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioResponseDto;
import br.com.mfsdevsystem.parkapi.web.dto.VagaCreateDto;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts="/sql/vagas/vagas-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts="/sql/vagas/vagas-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagasIT {

	@Autowired
	WebTestClient testClient;
	
	@Test
	public void criarVaga_ComoDadosValidos_RetornarLocationComStatus201() {
		testClient
		   .post()
		   .uri("/api/v1/vagas")
		   .contentType(MediaType.APPLICATION_JSON)
		    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( new VagaCreateDto("A-05", "LIVRE"))
		   .exchange()
		   .expectStatus().isCreated()
		   .expectHeader().exists(HttpHeaders.LOCATION);
	}
	
	@Test
	public void criarVaga_ComCodigoJaExistente_RetornarErrorMessageStatus409() {
		testClient
		   .post()
		   .uri("/api/v1/vagas")
		   .contentType(MediaType.APPLICATION_JSON)
   	       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( new VagaCreateDto("A-01", "LIVRE"))
		   .exchange()
		   .expectStatus().isEqualTo(409)
		   .expectBody()
		   .jsonPath("status").isEqualTo(409)
		   .jsonPath("method").isEqualTo("POST")
		   .jsonPath("path").isEqualTo("/api/v1/vagas");
		   
	}
	
	@Test
	public void criarVaga_ComDadosInvalidos_RetornarErrorMessageStatus422() {
		testClient
		   .post()
		   .uri("/api/v1/vagas")
		   .contentType(MediaType.APPLICATION_JSON)
   	       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( new VagaCreateDto("", ""))
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo(422)
		   .jsonPath("method").isEqualTo("POST")
		   .jsonPath("path").isEqualTo("/api/v1/vagas");
		
		testClient
		   .post()
		   .uri("/api/v1/vagas")
		   .contentType(MediaType.APPLICATION_JSON)
	       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .bodyValue( new VagaCreateDto("A-501", "DESOCUPADO"))
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody()
		   .jsonPath("status").isEqualTo(422)
		   .jsonPath("method").isEqualTo("POST")
		   .jsonPath("path").isEqualTo("/api/v1/vagas");
		   
	}
	
	@Test
	public void buscarVaga_ComCodigoExistente_RetornarVagaComStatus200() {
		testClient
		   .get()
		   .uri("/api/v1/vagas/{codigo}","A-01")
   	       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isOk()
		   .expectBody()
		   .jsonPath("id").isEqualTo(10)
		   .jsonPath("codigo").isEqualTo("A-01")
		   .jsonPath("statu").isEqualTo("LIVRE");
		   
	}
	
	@Test
	public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404() {
		testClient
		   .get()
		   .uri("/api/v1/vagas/{codigo}", "A-10")
   	       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody()
		   .jsonPath("status").isEqualTo(404)
		   .jsonPath("method").isEqualTo("GET")
		   .jsonPath("path").isEqualTo("/api/v1/vagas/A-10");
		   
	}
	
}
