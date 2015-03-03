/*
 A classe Questao_QQSM faz a gestao do ficheiro.
 Cada pergunta como nao poderia deixar de ser e composta por 4 opcoes e a 
 resposta correcta.
 Como tal esta classe tem um construtor, com as seguintes variaveis de instancia:
 - pergunta
 - opcaoA, opcaoB, opcaoC, opcaoD
 - resposta

 O metodo adquire_questao recebe como parametros o patamar em que o concorrente 
 se encontra, e o ArrayList questoes que contem todas as questoes que o ficheiro 
 "Perguntas_QQSM" continha. Este metodo e responsavel por separar todas as questoes
 pelos patamares correspondentes e adiciona-as a 3 ArrayList diferentes (variaveis
 final static criadas na classe QQSM).

 O metodo adicionaquestao, recebe como parametro o Arraylist questoes, apenas 
 pelo facto de o concorrente pretender iniciar um jogo, após ter inserido a(s)
 nova(s) questao(oes).
 O concorrente tera que indicar em que patamar pretende inserir a questao, e entao
 caso nao ocorra nenhuma excepcao sera adicionada automaticamente a nova questao 
 ao ficheiro.
 Apos a insercao do ficheiro o concorrente sera redirecionado para o metodo accao
 apos pergunta, onde decidira qual sera o seu proximo passo: sair para o menu
 anterior ou introduzir uma nova questao.
 Se o ficheiro Perguntas_QQSM estiver corrompido, sera eliminado e criado um novo
 ficheiro, caso o ficheiro nao exista sera criado um novo ficheiro.

 O metodo valida_numero_perguntas funciona como rampa de lancamento para jogar um jogo,
 serao necessarias no minimo 18 perguntas para jogar e poder completar o jogo. Este 
 metodo verifica se existem questoes suficientes em cada patamar. Caso nao haja o 
 concorrente sera obrigado a adicionar novas questoes (redirecionado para o metodo
 adicona questao). Este metodo e sempre verificado cada vez que se executa o jogo, e 
 retorna true (existem questoes suf.) ou false (nao existe questoes suf.) consoante 
 o resultado.

 O metodo pergunta patamar recebe como argumento um ArrayList patamar que podera 
 corresponder ao patamar1, patamar2 ou patamar3. Aleatoriamente escolhe uma posicao
 que corresponde a uma pergunta, e tuliza essa posicao para alterar a variavel de 
 instancia pergunta, opcaoA, opcaoB, opcaoC, opcaoD e resposta. Criando assim uma 
 pergunta completa.

 O metodo respostaQuestao, funciona como um toString mas apenas retorna a Resposta
 correta a questao.

 */
package qqsm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static qqsm.QQSM.criaficheiro;
/**
 * 
 * @author Simao Ramos e Marlene Barroso
 */
public class Questao_QQSM {

    private String pergunta;
    private String opcaoA;
    private String opcaoB;
    private String opcaoC;
    private String opcaoD;
    private String resposta;

   /**
    * construtor default
    */
    public Questao_QQSM() {

    }

    /**
     *
     * @param pergunta
     * @param opcaoA
     * @param opcaoB
     * @param opcaoC
     * @param opcaoD
     * @param resposta
     */
    public Questao_QQSM(String pergunta, String opcaoA, String opcaoB, String opcaoC, String opcaoD, String resposta) {
        this.pergunta = pergunta;
        this.opcaoA = opcaoA;
        this.opcaoB = opcaoB;
        this.opcaoC = opcaoC;
        this.opcaoD = opcaoD;
        this.resposta = resposta;
    }

    /**
     * *
     *
     * @return
     */
    public String getPergunta() {
        return pergunta;
    }

    public String getOpcaoA() {
        return opcaoA;
    }

    public String getOpcaoB() {
        return opcaoB;
    }

    public String getOpcaoC() {
        return opcaoC;
    }

    public String getOpcaoD() {
        return opcaoD;
    }

    public String getResposta() {
        return resposta;
    }

    /**
     * *
     *
     * @param pergunta
     */
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    /**
     * *
     *
     * @param opcaoA
     */
    public void setOpcaoA(String opcaoA) {
        this.opcaoA = opcaoA;
    }

    /**
     *
     * @param opcaoB
     */
    public void setOpcaoB(String opcaoB) {
        this.opcaoB = opcaoB;
    }

    /**
     *
     * @param opcaoC
     */
    public void setOpcaoC(String opcaoC) {
        this.opcaoC = opcaoC;
    }

    /**
     *
     * @param opcaoD
     */
    public void setOpcaoD(String opcaoD) {
        this.opcaoD = opcaoD;
    }

    /**
     *
     * @param resposta
     */
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    /**
     *
     * @param pat
     * @param questoes
     */
    public static void adquirequestao(int pat, ArrayList questoes) {
        String valor_linha;

        int posicao_pergunta;

        int j;

        for (int i = 0; i < questoes.size(); i = i + 8) {
            posicao_pergunta = i + 1;
            j = i;
            if (Integer.toString(pat).equals(questoes.get(i))) {
                do {
                    valor_linha = (String) questoes.get(posicao_pergunta);
                    if (pat == 1) {
                        QQSM.patamar_1.add(valor_linha);
                    } else if (pat == 2) {
                        QQSM.patamar_2.add(valor_linha);
                    } else {
                        QQSM.patamar_3.add(valor_linha);
                    }
                    posicao_pergunta = posicao_pergunta + 1;
                } while (posicao_pergunta < j + 7);
            }
        }
    }

    /**
     *
     * @param questoes
     * @throws IOException
     * @throws InterruptedException
     * @throws InputIndefinidoException
     */
    public static void adicionaquestao(ArrayList questoes) throws IOException, InterruptedException, InputIndefinidoException {
        Scanner teclado = new Scanner(System.in);
        String patamar_questao = null;

        System.out.println("------Aqui podera adicionar uma nova questao--------\n"
                + "A que patamar acha que deve pertencer a sua questao?\n"
                + "Patamar 1 - Perguntas fáceis\n"
                + "-------------\n"
                + "- 1  25     -\n"
                + "- 2  50     -\n"
                + "- 3  125    -\n"
                + "- 4  250    -\n"
                + "- 5  500    -\n"
                + "-------------\n"
                + "Patamar 2 - Perguntas de dificuldade media\n"
                + "-------------\n"
                + "- 6  750    -\n"
                + "- 7  1500   -\n"
                + "- 8  2500   -\n"
                + "- 9  5000   -\n"
                + "- 10 7500   -\n"
                + "-------------\n"
                + "Patamar 3 - Perguntas de dificuldade media\n"
                + "-------------\n"
                + "- 11 12500  -\n"
                + "- 12 25000  -\n"
                + "- 13 50000  -\n"
                + "- 14 100000 -\n"
                + "- 15 250000 -\n"
                + "-------------\n");

        String pergunta;

        String opcaoA;
        String opcaoB;
        String opcaoC;
        String opcaoD;
        String resp;
        String op;
        boolean repete = false;
        do {

            do {
                try {
                    System.out.print("Indique um patamar (1-3) de acordo com a tabela acima: ");
                    patamar_questao = QQSM.inputs();

                    if (QQSM.intok(patamar_questao) == false) {
                        throw new FormatoInputNaoEspecificadoException("Não é permitida a introdução de Strings neste menu");

                    } else if (patamar_questao == "") {
                        throw new StringIndexOutOfBoundsException("");
                    } else if (Integer.parseInt(patamar_questao) < 1 || Integer.parseInt(patamar_questao) > 3) {
                        throw new InputIndefinidoException("Valor numerico introduzido não corresponde a nenhuma opção existente no menu!");
                    } else {
                        repete = true;
                    }
                } catch (FormatoInputNaoEspecificadoException e) {
                    System.out.println(e.getMessage());

                    repete = false;

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Terá que introduzir algum valor no input. Após introduzir um valor [1-3] clique [ENTER]");

                    repete = false;
                } catch (InputIndefinidoException e) {
                    System.out.println(e.getMessage());

                    repete = false;
                }
            } while (repete == false);

            System.out.println("Insira a questão pretedida!");

            System.out.print("P:");
            pergunta = teclado.nextLine();
            System.out.print("A:");
            opcaoA = teclado.nextLine();
            System.out.print("B:");
            opcaoB = teclado.nextLine();
            System.out.print("C:");
            opcaoC = teclado.nextLine();
            System.out.print("D:");
            opcaoD = teclado.nextLine();

            String RA = "A";
            String RB = "B";
            String RC = "C";
            String RD = "D";

            do {
                System.out.print("R:");
                resp = teclado.nextLine().toUpperCase();
            } while ((!RA.equals(resp)) && (!RB.equals(resp)) && (!RC.equals(resp)) && (!RD.equals(resp)));

            System.out.println("");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("- Tem a certeza que deseja introduzir a seguinte questao no jogo? -");
            System.out.println("\n  P:" + pergunta + "\n  A:" + opcaoA + "\n  B:" + opcaoB + "\n  C:" + opcaoC + "  \n  D:" + opcaoD + "\n  R:" + resp + "  ");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------\n"
                    + "-        S-SIM          -"
                    + "\n-  Qualquer tecla -NAO  -"
                    + "\n-------------------------");
            System.out.print("Resposta:");
            op = teclado.nextLine();
            op = op.toUpperCase();
        } while (!"S".equals(op));
        try {

            String newLine = System.getProperty("line.separator");

            OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream("Perguntas_QQSM.txt", true), "UTF-8");
            System.out.println(pergunta);
            bw.write(patamar_questao + newLine);
            bw.write("P:" + pergunta.substring(0, pergunta.length()) + newLine);
            bw.write("A:" + opcaoA.substring(0, opcaoA.length()) + newLine);
            bw.write("B:" + opcaoB.substring(0, opcaoB.length()) + newLine);
            bw.write("C:" + opcaoC.substring(0, opcaoC.length()) + newLine);
            bw.write("D:" + opcaoD.substring(0, opcaoD.length()) + newLine);
            bw.write("R:" + resp + newLine);
            bw.write("\n");

            bw.flush();
            bw.close();
            accaoapospergunta(questoes);
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
    }

    /**
     *
     * @param questoes
     * @throws IOException
     * @throws InterruptedException
     * @throws InputIndefinidoException
     */
    public static void accaoapospergunta(ArrayList questoes) throws IOException, InterruptedException, InputIndefinidoException {
        String accao = null;
        System.out.println("O que pretende fazer?\n"
                + "0-Sair\n"
                + "1-Introduzir nova questão");

        accao = QQSM.inputs();

        try {
            if ((Integer.parseInt(accao)) > 1 || (Integer.parseInt(accao)) < 0) {
                throw new FormatoInputNaoEspecificadoException("Tera que introduzir um valor existente no menu!");
            } else if ("".equals(accao)) {
                throw new StringIndexOutOfBoundsException();
            } else if (QQSM.intok(accao) == false) {
                throw new NumberFormatException("Não é permitida a introdução de Strings no menu, introduza um valor [0/1] depois clique [ENTER]");
            }

            if ("0".equals(accao)) {
                questoes.clear();
                QQSM.patamar_1.clear();
                QQSM.patamar_2.clear();
                QQSM.patamar_3.clear();

                System.gc();
                QQSM.iniciaJogo();
            } else if ("1".equals(accao)) {
                adicionaquestao(questoes);
            }
        } catch (StringIndexOutOfBoundsException | FormatoInputNaoEspecificadoException e) {
            System.out.println(e.getMessage());
            accaoapospergunta(questoes);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            e.getMessage();
            accaoapospergunta(questoes);
        }
    }

    /**
     *
     * @param questoes
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws InputIndefinidoException
     */
    public static boolean valida_numero_perguntas(ArrayList questoes) throws IOException, InterruptedException, InputIndefinidoException {
        boolean existemquestoes = false;
        int pat1 = 1;
        int pat2 = 2;
        adquirequestao(pat1, questoes);
        int pat3 = 3;
        adquirequestao(pat2, questoes);
        adquirequestao(pat3, questoes);

        int questoes_falta_patamar1 = 0;
        int questoes_falta_patamar2 = 0;
        int questoes_falta_patamar3 = 0;

        if (QQSM.patamar_1.size() < 36 || QQSM.patamar_2.size() < 36 || QQSM.patamar_3.size() < 36) {

            questoes_falta_patamar1 = 6 - (QQSM.patamar_1.size()) / 6;
            questoes_falta_patamar2 = 6 - (QQSM.patamar_2.size()) / 6;
            questoes_falta_patamar3 = 6 - (QQSM.patamar_3.size()) / 6;

            questoes_falta_patamar1 = 6 - (questoes_falta_patamar1);
            questoes_falta_patamar2 = 6 - (questoes_falta_patamar2);
            questoes_falta_patamar3 = 6 - (questoes_falta_patamar3);

            System.out.println("Cada patamar tera que ter pelo menos 6 questoes para poder ser o novo milionario...Neste momento cada patamar tem:"
                    + "\nPatamar 1: " + questoes_falta_patamar1 + " questoes a mais."
                    + "\nPatamar 2: " + questoes_falta_patamar2 + " questoes a mais."
                    + "\nPatamar 3: " + questoes_falta_patamar3 + " questoes a mais.");
            Thread.sleep(3000);
            adicionaquestao(questoes);
        } else {
            existemquestoes = true;
        }

        return existemquestoes;
    }
    static Questao_QQSM q = new Questao_QQSM();
    
    /**
     * 
     * @param patamar
     * @throws IOException
     * @throws InterruptedException 
     */

    public static void pergunta_patamar(ArrayList patamar) throws IOException, InterruptedException {
        Random rand = new Random();

        int pos;

        pos = rand.nextInt(patamar.size() / 6) * 6;

        q.pergunta = (String) patamar.get(pos);
        q.setPergunta(q.pergunta);
        patamar.remove(pos);

        q.opcaoA = (String) patamar.get(pos);
        q.setOpcaoA(q.opcaoA);
        patamar.remove(pos);

        q.opcaoB = (String) patamar.get(pos);
        q.setOpcaoB(q.opcaoB);
        patamar.remove(pos);

        q.opcaoC = (String) patamar.get(pos);
        q.setOpcaoC(q.opcaoC);
        patamar.remove(pos);

        q.opcaoD = (String) patamar.get(pos);
        q.setOpcaoD(q.opcaoD);
        patamar.remove(pos);

        q.resposta = (String) patamar.get(pos);
        q.setResposta(q.resposta);
        patamar.remove(pos);

    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return ("" + q.getPergunta() + "\n\n" + q.getOpcaoA() + "\n" + q.getOpcaoB() + "\n" + q.getOpcaoC() + "\n" + q.getOpcaoD() + "\n");
    }

    /**
     *
     * @return
     */
    public String respostaQuestao() {
        return ("" + q.getResposta());
    }

}
