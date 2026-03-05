//Aqui importamos três bibliotecas,
// a primeira nos permite pegar o tempo exato em que a ação do objeto está sendo realizada.
//A segunda é uma biblioteca que nos permite formatar esta data
// e a terceira é o .random que nos permite trabalhar com numeros aleatorios.

package com.br;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SimuladorLogSistema {

    //Aqui criamos duas classes com dois arrays de strings,
    // entao primeiro criei a classe, deixei ela public pois como apenas vamos usar essa classe para o projeto,
    // nao precisamos deixar ela privada, coloquei o static para informar que ela pertence apenas a essa classe,
    // e a tag final siginifica que ela nao pode ser atribuida.
    public static final String[] niveis = {"INFO","WARN","ERROR"};
    public static final String[] observabilidade =
            {       "[event: erro de autenticação], [Hostname: server01] , [ip : 192.168.1.1]",
                    "[event: erro de conexão com AWS] , [Hostname: server07], [ip : 192.168.1.3 ]",
                    "[event: Autenticação realizada com sucesso] , [Hostname: server07], [ip : 192.168.1.3]",
                    "[event: CPU com temperatura acima do normal(Novo registro no jira)] , [Hostname: server10], [ip : 192.168.1.14]"};

    //Aqui é a main da nossa classe, e é onde o sistema vai rodar,
    // nela definimos uma variavel com o random para usarmos ela e formataremos a data.
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        DateTimeFormatter data_hora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Iniciando logs do sistema...");
//Aqui definimos um while true pois enquanto for verdade, ele irá rodar para sempre,
// e dentro dele definimos a data formatada, e usamos o random para retornar um indice aleatorio
// entre 0 até a quantidade de indice no array de strings.
        while (true) {
            String tempoReal = LocalDateTime.now().format(data_hora);
            String nivelAviso = niveis[random.nextInt(niveis.length)];
            String mensagem_observabilidade = observabilidade[random.nextInt(observabilidade.length)];

            // Aqui indicamos o formato do print, colocando as tres categorias,
            // como tempo, nivel do aviso e a mensagem
            System.out.printf("[%s] [%s] - %s%n", tempoReal, nivelAviso, mensagem_observabilidade);

            //Nesta parte foi definida o intervalo entre os logs, porém usei o .random
            // e um intervalo entre 2 a 3 segundos,
            // para os logs serem exibidos em intervalos diferentes
            Thread.sleep(2000 + random.nextInt(3000));
        }
    }
}
