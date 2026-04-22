package school.sptech.config;

import java.net.URI;
import java.net.http.HttpClient; // carteiro
import java.net.http.HttpRequest; // carta
import java.net.http.HttpResponse; //aviso

public class Slack {

    public void enviarAlerta(String mensagem) {
        try {
            String urlDoSlack = "";
            String json = String.format("{\"text\": \"%s\"}", mensagem);


            HttpRequest carta = HttpRequest.newBuilder()
                    .uri(URI.create(urlDoSlack))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();


            HttpClient carteiro = HttpClient.newHttpClient();
            carteiro.send(carta, HttpResponse.BodyHandlers.ofString());

            System.out.println("Mensagem enviada com sucesso!");

        } catch (Exception e) {
            System.out.println("Deu erro: " + e.getMessage());
        }
    }
}

