package br.com.mfsdevsystem.parkapi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.mfsdevsystem.parkapi.web.dto.UsuarioCreatedDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioPasswordDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioResponseDto;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts="/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts="/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createUsuario_ComoUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
		UsuarioResponseDto responseBody = testClient
		   .post()
		   .uri("/api/v1/usuarios")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioCreatedDto("tody@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isCreated()
		   .expectBody( UsuarioResponseDto.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@gmail.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");	
		   
	}
	
	@Test
	public void createUsuario_ComoUsernameInvalido_RetornarErrorMessageStatus422() {
		ErrorMessage responseBody = testClient
		   .post()
		   .uri("/api/v1/usuarios")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioCreatedDto("", "123456"))
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				   .post()
				   .uri("/api/v1/usuarios")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioCreatedDto("tody@", "123456"))
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
				
		responseBody = testClient
				   .post()
				   .uri("/api/v1/usuarios")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioCreatedDto("tody@email", "123456"))
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		   
	}
	
	@Test
	public void createUsuario_ComoPasswordInvalido_RetornarErrorMessageStatus422() {
		ErrorMessage responseBody = testClient
		   .post()
		   .uri("/api/v1/usuarios")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioCreatedDto("tody@gmail.com", "")) // password null
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				   .post()
				   .uri("/api/v1/usuarios")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioCreatedDto("tody@gmail.com", "1234")) // password < 6 caracteres
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
				
		responseBody = testClient
				   .post()
				   .uri("/api/v1/usuarios")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioCreatedDto("tody@email.com", "123456789$")) // password > 8 caracteres
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		   
	}
	
	@Test
	public void createUsuario_ComoUsernameRepetido_RetornarUsuarioCriadoComStatus409() {
		ErrorMessage responseBody = testClient
		   .post()
		   .uri("/api/v1/usuarios")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioCreatedDto("ana@gmail.com", "123456"))
		   .exchange()
		   .expectStatus().isEqualTo(409)
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

	}
	
	    @Test
	    public void buscarUsuario_ComIdExistente_RetornarUsuarioComStatus200() {
	        UsuarioResponseDto responseBody = testClient
	                .get()
	                .uri("/api/v1/usuarios/4")
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody(UsuarioResponseDto.class)
	                .returnResult().getResponseBody();

	        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(4);
	        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@gmail.com");
	        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

	        /* Testar o ADMIN buscando um usuário do tipo CLIENT*/
	        responseBody = testClient
	                .get()
	                .uri("/api/v1/usuarios/3")
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody(UsuarioResponseDto.class)
	                .returnResult().getResponseBody();

	        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(3);
	        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("bia@gmail.com");
	        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

	        /* Teste feito pelo CLIENTE buscando os proprios dados dele */
	        responseBody = testClient
	                .get()
	                .uri("/api/v1/usuarios/3")
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody(UsuarioResponseDto.class)
	                .returnResult().getResponseBody();

	        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(3);
	        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("bia@email.com");
	        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
	    }


	@Test
	public void BuscarUsuario_ComIdInexistente_RetornarErrorMessageComStatus404() {
		ErrorMessage responseBody = testClient
		   .get()
		   .uri("/api/v1/usuarios/4")
		   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		
		   .exchange()
		   .expectStatus().isNotFound()
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
 
	}
	
	@Test
	public void BuscarUsuario_ComUsuarioClienteBuscandoOutroCliente_RetornarErrorMessageComStatus403() {
		ErrorMessage responseBody = testClient
		   .get()
		   .uri("/api/v1/usuarios/5")
		   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))	
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
 
	}
	
	@Test
	public void editarSenha_ComDadosValidos_RetornarStatus204() {
			testClient
			   .patch()
			   .uri("/api/v1/usuarios/4")
			   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
			   .contentType(MediaType.APPLICATION_JSON)
			   .bodyValue( new UsuarioPasswordDto("123456", "123456","123456"))
			   .exchange()
			   .expectStatus().isNoContent();
			
			testClient
			   .patch()
			   .uri("/api/v1/usuarios/3")
			   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
			   .contentType(MediaType.APPLICATION_JSON)
			   .bodyValue( new UsuarioPasswordDto("123456", "123456","123456"))
			   .exchange()
			   .expectStatus().isNoContent();
		   
	}
	
	@Test
	public void editarSenha_ComUsuariosDiferente_RetornarErrorMessageComStatus403() {
		ErrorMessage responseBody = testClient
		   .patch()
		   .uri("/api/v1/usuarios/0")
		   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioPasswordDto("123456", "123456","123456"))
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
 
		testClient
		   .patch()
		   .uri("/api/v1/usuarios/0")
		   .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioPasswordDto("123456", "123456","123456"))
		   .exchange()
		   .expectStatus().isForbidden()
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
	}
	
	@Test
	public void editarSenha_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
		ErrorMessage responseBody = testClient
		   .patch()
		   .uri("/api/v1/usuarios/100")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioPasswordDto("", "",""))
		   .exchange()
		   .expectStatus().isEqualTo(422)
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				   .patch()
				   .uri("/api/v1/usuarios/100")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioPasswordDto("12345", "12345","12345"))
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				   .patch()
				   .uri("/api/v1/usuarios/100")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioPasswordDto("123456789", "123456789","123456789"))
				   .exchange()
				   .expectStatus().isEqualTo(422)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
 
	}
	
	@Test
	public void editarSenha_ComSenhasInvalidass_RetornarErrorMessageComStatus400() {
		ErrorMessage responseBody = testClient
		   .patch()
		   .uri("/api/v1/usuarios/100")
		   .contentType(MediaType.APPLICATION_JSON)
		   .bodyValue( new UsuarioPasswordDto("123456", "123456","000000"))
		   .exchange()
		   .expectStatus().isEqualTo(400)
		   .expectBody( ErrorMessage.class)
		   .returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		
		responseBody = testClient
				   .patch()
				   .uri("/api/v1/usuarios/100")
				   .contentType(MediaType.APPLICATION_JSON)
				   .bodyValue( new UsuarioPasswordDto("000000", "123456","123456"))
				   .exchange()
				   .expectStatus().isEqualTo(400)
				   .expectBody( ErrorMessage.class)
				   .returnResult().getResponseBody();
				
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
 
	}
	
	@Test
    public void listarUsuarios_SemQualquerParametro_RetornarListaDeUsuariosComStatus200() {
        List<UsuarioResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }
}
