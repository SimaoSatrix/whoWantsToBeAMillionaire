/*
A classe QQSM e a classe principal uma vez que contem o main.
Contem 6 variaveis constantes:
JOGO_POR_COMECAR = 0;
JOGO_A_DECORRER = 1;
JOGO_PERDIDO = 2;
JOGO_DESISTIDO = 3;
ArrayList patamar_1 vazio
ArrayList patamar_2 vazio
ArrayList patamar_3 vazio
O main apenas chama uma funcao. O metodo iniciaJogo();

O metodo inicia jogo tem um menu inicial que permite 
iniciar um novo jogo - 1 -> Jogo_QQSM.gestao_do_jogo(novo, questoes);
inserir perguntas - 2 -> Questao_QQSM.adicionaquestao(questoes);
sair - 3 -> termina o jogo
Consoante a resposta o jogo sera encaminhado para cada um dos metodos.
Quando e inicado um novo jogo, o metodo gestao de jogo recebe a variavel constante
como jogo_por_comecar->0. Enquanto nao forem introduzidos inputs correctos serao
lacadas escpçoes e jogo nao avança.

O metodo intok verifica se o valor recebido String s e uma string ou um inteiro,
retornando true se corresponder a uma string.

O metodo criaArrayList_ficheiro() acessa ao ficheiro Perguntas_QQSM.txt, lê o seu conteudo,
e guarda-o no ArrayList Questoes, cada linha do ficheiro corresponde a uma nova posicao
do ArrayList. No final retorna o ArrayList.
Caso ocorra um erro de leitura do ficheiro o ficheiro podera estar corrompido e entao
sera elimando, apagando todas as questoes nele existentes. Sera criando um novo ficheiro
(criaFicheiro()) com o mesmo nome. 

O metodo criaFicheiro(), cria um ficheiro como o nome Perguntas_QQSM.txt

O metodo inputs, libera o facto de estar sempre a criar um Scannner sempre que
se pretende ler o teclado. Le uma string, e retorna-a.
 */
package qqsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 *
 * @author Simao Ramos & Marlene Barroso
 */
public class QQSM {

    final static int JOGO_POR_COMECAR = 0;
    final static int JOGO_A_DECORRER = 1;
    final static int JOGO_PERDIDO = 2;
    final static int JOGO_DESISTIDO = 3;
    final static ArrayList patamar_1 = new ArrayList();
    final static ArrayList patamar_2 = new ArrayList();
    final static ArrayList patamar_3 = new ArrayList();

    final static ArrayList ajudasdisponiveis = Jogo_ajudas.ajudas_concorrente();
    final static ArrayList op_ajudas_disponiveis = Jogo_ajudas.opcao_ajudas_concorrente();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws qqsm.InputIndefinidoException
     * @throws qqsm.InputNaoIntroduzidoException
     */
    public static void main(String[] args) throws IOException, InterruptedException, InputIndefinidoException {
        iniciaJogo();

    }

    /**
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws InputIndefinidoException
     */

    public static void iniciaJogo() throws IOException, InterruptedException, InputIndefinidoException {
        ArrayList questoes = new ArrayList();
        questoes = QQSM.criaArrayList_ficheiro();

        String input_int;
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.print("--------------------------------Quem Quer Ser Milionário--------------------------------------\n"
                + "1-Novo Jogo\n"
                + "2-Inserir Perguntas\n"
                + "3-Sair\n"
                + "Opção:");
        input_int = inputs();
        String numeromaximo = "3";

        try {
            if (intok(input_int) == false) {
                throw new FormatoInputNaoEspecificadoException("Não é permitida a introdução de Strings no menu, introduza um valor [1-3] depois clique [ENTER]");
            } else if ((Integer.parseInt(input_int) > Integer.parseInt(numeromaximo)) || Integer.parseInt(input_int) == 0) {
                throw new InputIndefinidoException("Valor numerico introduzido não corresponde a nenhuma opção existente no menu!");
            } else if (input_int == "") {
                throw new StringIndexOutOfBoundsException();
            }

            boolean confirma = Questao_QQSM.valida_numero_perguntas(questoes);
            if (confirma == false) {
                Questao_QQSM.adicionaquestao(questoes);
            } else {
                switch (input_int) {
                    case "1":
                        Concorrente Pessoa = new Concorrente();
                        Jogo_QQSM novo = new Jogo_QQSM(Pessoa, JOGO_POR_COMECAR);
                        Jogo_QQSM.gestao_do_jogo(novo, questoes);
                        break;

                    case "2":
                        Questao_QQSM.adicionaquestao(questoes);
                        break;
                    case "3":
                        System.exit(0);
                        break;

                }
            }
        } catch (InputIndefinidoException | FormatoInputNaoEspecificadoException e) {
            System.out.println(e.getMessage());
            iniciaJogo();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Terá que introduzir algum valor no input. Após introduzir um valor [1-3] clique [ENTER]");
            iniciaJogo();
        }

    }

    /**
     *
     * @param s
     * @return
     */
    static boolean intok(String s) {
        if (s.length() > 1) {
            return false;
        } else if (Character.isDigit(s.charAt(0)) == false) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    static ArrayList criaArrayList_ficheiro() {

        File ficheiro = new File("Perguntas_QQSM.txt");
        try {
            //necessario para a leitura de uma InputStreamReader 
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheiro), "UTF-8"));
            //ArrayList que guarda cada uma das linhas do txt
            ArrayList linhas = new ArrayList();
            //variável que contém linha existente no txt

            String lin = br.readLine();
            if (lin == null) {
                System.out.println("Ficheiro de perguntas vazio!");
                return null;
            }

            while (lin != null) {
                linhas.add(lin);
                lin = br.readLine();
            }
            br.close();
            return linhas;
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não existente!");
            criaficheiro();
        } catch (IOException e) {
            System.out.println("Erro de leitura no ficheiro que contém as questões");
            System.out.println("Pedimos desculpa mas o ficheiro com as questoes existentes vai ser eliminado."
                    + "\nSeá criado um novo ficheiro em branco onde poderá introduzir novas questoes.\n");
            File file = new File("Perguntas_QQSM.txt");
            file.delete();
            criaficheiro();
        }
        return null;

    }

    public static void criaficheiro() {
        String nomeArq = "Perguntas_QQSM.txt";
        //tentando criar arquivo
        try {
            Formatter saida = new Formatter(nomeArq);
            saida.close();
            System.out.println("Arquivo " + nomeArq + " criado com sucesso!");
            criaArrayList_ficheiro();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo nao pode ser criado!");
            System.exit(0);
        }
    }

    public static String inputs() {
        String input;
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
        return input;
    }

}
