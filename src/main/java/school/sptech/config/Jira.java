package school.sptech.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

public class Jira {
//    Variaveis constantes para autenticação e requisicao
    private final String baseUrl;
    private final String authHeader;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

//    Construtor da classe
    public Jira(String baseUrl, String email, String apiToken) {
        this.baseUrl = baseUrl;

        String auth = email + ":" + apiToken;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        this.objectMapper = new ObjectMapper();
    }

//    Metodo para criar o corpo da reuisicao
    private HttpRequest.Builder baseRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/rest/api/3/issue"))
                .timeout(Duration.ofSeconds(60))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

//    Envio do da requisicao juntamente com o tratamento do erro do request
    private String sendRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();

        if (status >= 200 && status < 300) {
            return response.body();
        }

        throw new RuntimeException("Jira request failed: " + status + " - " + response.body());
    }

//    Criacao do chamado atraves do envio da requisicao
    public String createIssue(String projectKey, String summary, String issueType) throws Exception {
        Map<String, Object> payload = Map.of(
                "fields",
                Map.of(
                        "project",
                        Map.of("key", projectKey),
                        "summary",
                        summary,
                        "issuetype",
                        Map.of("name", issueType)
                )
        );

        String json = objectMapper.writeValueAsString(payload);

        HttpRequest request = baseRequest()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return sendRequest(request);
    }
}