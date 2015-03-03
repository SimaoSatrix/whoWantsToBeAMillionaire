/*
A classe Jogo_QQSM e a base de processamento do jogo, uma vez que contem os metodos
necessarios para gerir o jogo ao longo do tempo.
Jogo QQSM tem 3 variaveis constantes:
-arvore_do_dinheiro- array que contem todos os valores a que as perguntas podem
corresponder
-Estado_de_jogo- que contem o estado do jogo 
(Jogo a comecar->0, Jogo_a_decorrer->1, Jogo_perdido->2, Jogo_desistido->3)
O construtor tem como variaveis de instancia Estado de Jogo e Pessoa, que e um 
objecto do tipo Concorrente.

O metodo Patamar_Array recebe como parametro o objecto Pessoa, retornando o ArrayList
correspondente ao patamar em que a Pessoa se encontra

O metodo gestao de jogo recebe como parametros um objecto do tipo jogo, e o ArrayList 
questoes. Caso o estado de jogo corresponda a 0 (jogo a comecar), entao segnifica que
podemos comecar a jogar normalmente. O jogador podera desistir logo inicia o jogo nao sendo
lancada nenhuma pergunta, o estado de jogo sera alterado para 3 (jogo desistido).
Se o jogador nao desistir, o estado de jogo sera alterado para 1 (jogo a decorrer).
O jogo inicia e o jogador poderá recorrer de 3 ajudas, contudo so podera usar 1 vez cada uma delas.
Poderá tambem utilizar as 3 ajudas numa so pergunta.
A medida do tempo que responde e acerta uma questao, a arvore do dinheiro e incrementada um valor.
Para alem das ajudas o jogador podera desistir pressionando 1 em qualquer altura, levando o
dinheiro da ultima questao respondida acertadamente.
Caso erre a questao o jogo termina.
Em qualquer uma destas situações este metodo chamará o metodo resultado, que indica
ao jogador a quantia que levara para casa.

O metodo incianovojogo recebe como parametro o ArrayList questoes. No caso do jogador
pretender iniciar um novo_jogo o ArryList questoes, e os ArrayList correspondestes aos
patamares serão limpos para que possam ser usados sem qualquer problema no proximo jogo.


O metodo ajudas_concorrentes gere de certa forma as ajudas do concorrente.
Recebe como parametro a pergunta actual, a resposta a pergunta, o objecto Concorrente,
e o objecto Questao_QQSM.
O Switch case tem 3 casos possiveis que correspondem as 3 ajudas disponiveis.
Case "1"- ajuda 50-50
Case "2" - troca pergunta
Case "3" - ajuda do publico
Este metodo interage com a classe Jogo_ajudas.
 */
package qqsm;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Simao Ramos e Marlene Barroso
 */
public class Jogo_QQSM {

    final static int[] arvore_do_dinheiro = {25, 50, 125, 250, 500, 750, 1500, 2500, 5000, 7500, 12500, 25000, 50000, 100000, 250000};
    public int ESTADO_DE_JOGO;
    private Concorrente Pessoa;

    public Jogo_QQSM() {

    }

    /**
     *
     * @param Pessoa
     * @param Estado
     */
    public Jogo_QQSM(Concorrente Pessoa, int Estado) {
        this.ESTADO_DE_JOGO = Estado;
        this.Pessoa = Pessoa;
    }

    /**
     *
     * @return
     */
    public int getESTADO_DE_JOGO() {
        return ESTADO_DE_JOGO;
    }

    /**
     *
     * @param ESTADO_DE_JOGO
     */
    public void setESTADO_DE_JOGO(int ESTADO_DE_JOGO) {
        this.ESTADO_DE_JOGO = ESTADO_DE_JOGO;
    }

    /**
     *
     * @return
     */
    public Concorrente getPessoa() {
        return Pessoa;
    }

    /**
     *
     * @param Pessoa
     */
    public void setPessoa(Concorrente Pessoa) {
        this.Pessoa = Pessoa;
    }

    /**
     *
     * @param Pessoa
     * @return
     */
    public static ArrayList patamar_Array(Concorrente Pessoa) {
        if (Pessoa.getPatamar() == 1) {
            return QQSM.patamar_1;
        } else if (Pessoa.getPatamar() == 2) {
            return QQSM.patamar_2;
        } else {
            return QQSM.patamar_3;
        }

    }

    /**
     *
     * @param jogo
     * @param questoes
     * @throws InputIndefinidoException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void gestao_do_jogo(Jogo_QQSM jogo, ArrayList questoes) throws InputIndefinidoException, IOException, InterruptedException {

        Concorrente Pessoa = jogo.getPessoa();
        int estado_atual = jogo.getESTADO_DE_JOGO();

        if (estado_atual == QQSM.JOGO_POR_COMECAR) {
            System.out.println("----------------------------------------------------------------------------------------------\n");
            System.out.println("------                       Bem vindo ao Quem Quer Ser Milionário                      ------\n"
                    + "                        Com quem tenho eu o prazer de desafiar hoje?                          \n");
            Pessoa.registarConcorrente();

            System.out.println("               É um prazer estar consigo hoje. " + Pessoa.toString() + "\n"
                    + "--------                Vamos então jogar ao Quem Quer ser Milionário.                --------\n"
                    + "--------                Temos uma árvore do dinheiro com 3 patamares,                 --------\n"
                    + "------              havendo duas perguntas de segurança, 500 e 7500 euros.              ------\n\n"
                    + "--        A qualquer altura pode desistir e levar o dinheiro que angariou para casa.        --\n\n"
                    + "-----           Caso falhe levará o dinheiro do último patamar de segurança.             -----\n"
                    + "                                  Vamos lá então " + Pessoa.getNome() + "\n"
                    + "----------------------------------------------------------------------------------------------\n"
                    + "                                 1-Começar      2-Desistir                                    ");

            int escolha = Integer.parseInt(QQSM.inputs());
            if (escolha == 1) {
                jogo.setESTADO_DE_JOGO(QQSM.JOGO_A_DECORRER);
                gestao_do_jogo(jogo, questoes);
            } else if (escolha == 2) {
                jogo.setESTADO_DE_JOGO(QQSM.JOGO_DESISTIDO);
                inicianovojogo(questoes);
            }
        }
        if (estado_atual == QQSM.JOGO_A_DECORRER) {

            String r = "R:";
            String resposta_utilizador;

            String resp = null;
            int i;

            for (i = 0; i < arvore_do_dinheiro.length; i++) {
                Pessoa.setPatamar_atraves_nivel(Pessoa, i);
                Questao_QQSM Pergunta = new Questao_QQSM();
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println("                                  Pergunta para " + arvore_do_dinheiro[i] + " euros               \n");
                if (Pessoa.getPatamar() == 1) {

                    System.out.println("---------             Ainda nao atingiu nenhum patamar de seguranca...               ---------\n");
                } else if (Pessoa.getPatamar() == 2) {
                    System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("--- Encontra-se no primeiro patamar de segurança. E se desistir pode levar para casa " + arvore_do_dinheiro[i - 1] + "euros ---\n");
                } else if (Pessoa.getPatamar() == 3) {
                    System.out.println("---   Encontra-se no segundo patamar de segurança. E se desistir pode levar para casa " + arvore_do_dinheiro[i - 1] + "euros ---\n");
                    if (i == arvore_do_dinheiro.length - 1) {
                        System.out.println("-----  Vamos para a última pergunta... Sei que está ancioso... mas pense com calma...    -----\n");

                    } else {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println("                           Vamos para a próxima pergunta...");
                        System.out.println("----------------------------------------------------------------------------------------------\n");
                    }
                }
                System.out.println("----------------------------------------------------------------------------------------------");

                System.out.println("Ajudas disponiveis:");

                Questao_QQSM.pergunta_patamar(patamar_Array(Pessoa));
                String perg = Pergunta.toString();
                String resp_p = Pergunta.respostaQuestao();
                System.out.println(perg);
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + ", para desistir pressiona 1");
                System.out.println("Para selecionar um das ajudas pressione H");
                System.out.println("----------------------------------------------------------------------------------------------");
                do {
                    do {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.print("Qual e a sua resposta: ");
                        resposta_utilizador = QQSM.inputs();
                        System.out.println("----------------------------------------------------------------------------------------------");
                        resposta_utilizador = resposta_utilizador.toUpperCase();

                        if (resposta_utilizador.equals("H")) {
                            System.out.println("**********************************************************************************************");
                            ajudas_concorrente(perg, resp_p, Pessoa, Pergunta);
                            System.out.println("**********************************************************************************************");
                            resp_p = Pergunta.respostaQuestao();
                        }

                    } while (!"A".equals(resposta_utilizador) & !"B".equals(resposta_utilizador) & !"C".equals(resposta_utilizador) & !"D".equals(resposta_utilizador) & !"1".equals(resposta_utilizador) & !"H".equals(resposta_utilizador));
                } while ("H".equals(resposta_utilizador));
                System.out.println("----------------------------------------------------------------------------------------------");
                resp = r.concat(resposta_utilizador);

                if (resposta_utilizador.equals("1")) {
                    jogo.setESTADO_DE_JOGO(QQSM.JOGO_DESISTIDO);
                    resultado(Pessoa, jogo.getESTADO_DE_JOGO(), i, questoes);

                } else if (!resp.equals(resp_p)) {

                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.println("A sua reposta esta ERRADA!!!");
                    System.out.println("A respota correcta era " + resp_p + "\n");
                    System.out.println("----------------------------------------------------------------------------------------------");
                    jogo.setESTADO_DE_JOGO(QQSM.JOGO_PERDIDO);
                    resultado(Pessoa, jogo.getESTADO_DE_JOGO(), i, questoes);
                } else if (!resp.equals(resp_p)) {
                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.println("A sua reposta está ERRADA!!!");
                    System.out.println("A resposta correcta era " + resp_p + "\n");
                    System.out.println("----------------------------------------------------------------------------------------------");
                    jogo.setESTADO_DE_JOGO(QQSM.JOGO_PERDIDO);
                    resultado(Pessoa, jogo.getESTADO_DE_JOGO(), i, questoes);
                } else if (resp.equals(resp_p)) {
                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.println("A resposta está correcta... Avancemos para a próxima questao!\n");
                    System.out.println("----------------------------------------------------------------------------------------------");
                    if (i == arvore_do_dinheiro.length - 1) {
                        System.out.println("------------------------------------------------------------------------------------------");
                        System.out.println("------------------------------------------------------------------------------------------");
                        System.out.println("Parabéns " + Pessoa.getNome() + " e o novo millionário! Cuidado com a TROIKA");
                        System.out.println("------------------------------------------------------------------------------------------");
                        System.out.println("------------------------------------------------------------------------------------------");
                        QQSM.iniciaJogo();
                    }
                }

            }

        }
    }

    /**
     *
     * @param Pessoa
     * @param estado_de_jogo
     * @param ind
     * @param questoes
     * @throws IOException
     * @throws InterruptedException
     * @throws InputIndefinidoException
     */
    public static void resultado(Concorrente Pessoa, int estado_de_jogo, int ind, ArrayList questoes) throws IOException, InterruptedException, InputIndefinidoException {

        if (estado_de_jogo == QQSM.JOGO_DESISTIDO) {
            if (ind == 0) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + " parabéns vai levar para casa 0 euros");
                System.out.println("----------------------------------------------------------------------------------------------");
            } else {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + " parabéns vai levar para casa " + arvore_do_dinheiro[ind - 1] + " euros");
                System.out.println("----------------------------------------------------------------------------------------------");
            }
            inicianovojogo(questoes);

        } else {
            if (Pessoa.getPatamar() == 1) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + ", com muita pena minha só levará 0 euros");
                System.out.println("----------------------------------------------------------------------------------------------");

                inicianovojogo(questoes);

            } else if (Pessoa.getPatamar() == 2) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + ", com muita pena minha só levará 500 euros ");
                System.out.println("----------------------------------------------------------------------------------------------");

                inicianovojogo(questoes);

            } else if (Pessoa.getPatamar() == 3) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println(Pessoa.getNome() + ", com muita pena minha só levará 7500 euros");
                System.out.println("----------------------------------------------------------------------------------------------");
                inicianovojogo(questoes);

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
    public static void inicianovojogo(ArrayList questoes) throws IOException, InterruptedException, InputIndefinidoException {
        String novojogo;
        System.out.print("Deseja iniciar um novo jogo? (pressione ENTER, caso contrário pressione uma tecla qualquer):");
        novojogo = QQSM.inputs();

        if (!"".equals(novojogo)) {
            System.out.println("A sair de jogo...");
            Thread.sleep(2000);
            System.exit(0);
        } else {
            questoes.clear();
            QQSM.patamar_1.clear();
            QQSM.patamar_2.clear();
            QQSM.patamar_3.clear();
            System.gc();
            QQSM.iniciaJogo();
        }

    }

    /**
     *
     * @param perg
     * @param resp_p
     * @param Pessoa
     * @param Pergunta
     * @throws IOException
     * @throws InterruptedException
     */
    public static void ajudas_concorrente(String perg, String resp_p, Concorrente Pessoa, Questao_QQSM Pergunta) throws IOException, InterruptedException {

        if (QQSM.ajudasdisponiveis.get(0) == "Ajuda utilizada" && QQSM.ajudasdisponiveis.get(1) == "Ajuda utilizada" && QQSM.ajudasdisponiveis.get(2) == "Ajuda utilizada") {

            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.println("Já não tem ajudas disponiveis...");

            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.print("Qual e a sua resposta: ");

        } else {
            for (int k = 0; k < QQSM.ajudasdisponiveis.size(); k++) {
                System.out.println(QQSM.ajudasdisponiveis.get(k));
            }

            String opcao_ajuda;
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.println("Qual a ajuda que deseja pedir: ");
            opcao_ajuda = QQSM.inputs();

            switch (opcao_ajuda) {

                case "1":
                    if (QQSM.op_ajudas_disponiveis.get(0) != "Ajuda utilizada" && QQSM.ajudasdisponiveis.get(0) != "Ajuda utilizada") {
                        System.out.println(Jogo_ajudas.ajuda_50_50(perg, resp_p));

                        resp_p = Pergunta.respostaQuestao();
                        Jogo_ajudas.remove_ajuda_concorrente(0);
                        Jogo_ajudas.remove_opcao_ajuda_concorrente(0);

                        break;
                    } else {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println("Ajuda nao disponivel...");
                        System.out.println("----------------------------------------------------------------------------------------------");

                        break;
                    }
                case "2":
                    if (QQSM.op_ajudas_disponiveis.get(1) != "Ajuda utilizada" && QQSM.ajudasdisponiveis.get(1) != "Ajuda utilizada") {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println("Vamos entao trocar a sua pergunta!");
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println(Pessoa.getNome() + ", para desistir pressiona 1");
                        System.out.println("Para selecionar um das ajudas pressione H");
                        System.out.println("----------------------------------------------------------------------------------------------");
                        Questao_QQSM.pergunta_patamar(patamar_Array(Pessoa));
                        perg = Pergunta.toString();
                        String resposta;
                        resposta = Pergunta.respostaQuestao();
                        resp_p = resposta;
                        System.out.println(perg);

                        Jogo_ajudas.remove_ajuda_concorrente(1);
                        Jogo_ajudas.remove_opcao_ajuda_concorrente(1);

                        break;
                    } else {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println("Ajuda nao disponivel...");
                        System.out.println("----------------------------------------------------------------------------------------------");

                        break;
                    }
                case "3":
                    if (QQSM.op_ajudas_disponiveis.get(2) != "Ajuda utilizada" && QQSM.ajudasdisponiveis.get(2) != "Ajuda utilizada") {
                        ArrayList perc = new ArrayList();
                        perc = Jogo_ajudas.Ajuda_Publico();
                        for (int p = 0; p < perc.size(); p++) {
                            System.out.println(perc.get(p));
                        }

                        resp_p = Pergunta.respostaQuestao();
                        Jogo_ajudas.remove_ajuda_concorrente(2);
                        Jogo_ajudas.remove_opcao_ajuda_concorrente(2);

                        break;
                    } else {
                        System.out.println("----------------------------------------------------------------------------------------------");
                        System.out.println("Ajuda nao disponivel...");
                        System.out.println("----------------------------------------------------------------------------------------------");

                        break;
                    }
            }
        }

    }

}
