package school.sptech.app;

import school.sptech.config.Jira;
import school.sptech.config.Slack;

public class App {
    public static void main(String[] args) throws Exception {
        String baseUrl = "";
        String email = "";
        String apiToken = "";
        Jira jira = new Jira(baseUrl, email, apiToken);

        String response = jira.createIssue(
                "KAN", // Substitua pela key do seu projeto que estará presente na URL do seu site
                // ex: "https://java-integration.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog" -> a parte "SCRUM" é a key desse projeto
                "Issue criada via Java", // Nome da issue
                "Task" // Tipo da issue, pode ser "Task", "Bug", "Story", etc. Verifique os tipos disponíveis no seu projeto para usar o correto
        );


        Slack slack = new Slack();

        slack.enviarAlerta("\uD83D\uDEA8 ALERTA: RAM EM ESTADO CRÍTICO \uD83D\uDEA8\n" +
                "\n" +
                "Servidor: TikTok US-West-1 (California, USA)\n" +
                "Status: \uD83D\uDD34 Crítico\n" +
                "Uso de RAM: 75%\n" +
                "\n" +
                "Atenção: O limite de segurança foi ultrapassado. " +
                "Verifique os processos ativos para evitar travamentos ou reinicializações inesperadas na instância de homologação/produção.");

        System.out.println("O comando de envio foi disparado!");

        System.out.println(response);
        // Se a requisição for bem-sucedida confira o backlog do seu projeto para ver a nova issue que foi criada
    }
}
