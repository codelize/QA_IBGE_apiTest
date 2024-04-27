package org.estudos.br;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";

    // @Test
    // @DisplayName("Teste para consulta única de um estado")
    // public void testConsultarEstado() throws IOException {
    //     // Arrange
    //     String uf = "SP"; // Define o estado a ser consultado

    //     // Act
    //     String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

    //     // Assert
    //     // Verifica se a resposta não está vazia
    //     assert !resposta.isEmpty();

    //     // Verifica se o status code é 200 (OK)
    //     HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
    //     int statusCode = connection.getResponseCode();
    //     assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    // }



    @Mock
    private HttpURLConnection connectionMock;

    // JSON de resposta simulada
    private static final String JSON_RESPONSE = "{\"id\":16,\"sigla\":\"AP\",\"nome\":\"Amapá\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}";    // Método executado antes de cada teste
    @BeforeEach
    public void setup() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    @DisplayName("Consulta usando o Mock")
    public void testConsultarEstadoComMock() throws IOException {
        // Sigla do estado a ser consultado
        String estadoUf = "AP";

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarEstado(estadoUf);

        // Verificamos se o JSON retornado é o mesmo que o JSON de resposta simulada
        assertEquals(JSON_RESPONSE, response, "O JSON retornado não corresponde ao esperado.");
    }

    @Test
    @DisplayName("Teste para validar opção escolhida do menu")
    public void testValidarEscolha() {

        InputStream input = new ByteArrayInputStream("100\n3\n".getBytes());
        System.setIn(input);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.main(new String[]{});

        assertTrue(output.toString().contains("Opção inválida."));
    }

    @Test
    @DisplayName("Teste para opção de sair do menu")
    public void testSair() {
        InputStream in = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        assertTrue(true);
    }
}