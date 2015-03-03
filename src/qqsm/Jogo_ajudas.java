/*
 * A classe jogo_ajudas e responsavel por gerir as ajudas que o concorrente tem 
 * ao longo do jogo
 * O concorrente dispoem de 3 ajudas diferentes
 * 50-50 Duas resposta sao imediatamente eliminadas
 * Troca pergunto, troca a pergunta corrente por uma outra do mesmo patamar
 * Ajuda publico gera percentagens aleatorias para cada uma das respostas
 */
package qqsm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * @author Simao Ramos & Marlene Barroso
 */
public class Jogo_ajudas {

    final static String A_50_50 = "1. 50-50";
    final static String A_trocaPergunta = "2. Troca pergunta";
    final static String A_publico = "3. Ajuda do publico";
    final static ArrayList ajudas = new ArrayList();
    final static ArrayList op_ajuda = new ArrayList();


    /*
     Adiciona ao arrayList ajudas, as 3 ajudas inciais que o concorrente dipoem 
     inicialmente
     return ajudas
     */
    /**
     *
     * @return
     */
    public static ArrayList ajudas_concorrente() {
        ajudas.add(A_50_50);
        ajudas.add(A_trocaPergunta);
        ajudas.add(A_publico);

        return ajudas;

    }

    /*
     Adiciona ao arrayList op_ajudas, os 3 imputs necessario para selecionar cada
     uma das ajudas
     */
    /**
     *
     * @return
     */
    public static ArrayList opcao_ajudas_concorrente() {

        op_ajuda.add("1");
        op_ajuda.add("2");
        op_ajuda.add("3");
        return op_ajuda;

    }

    /*
     Recebe o indice da ajuda que foi utilizada e substitui por uma string 
     dizendo que a ajuda ja foi tuilizada
     */
    /**
     *
     * @param ind
     * @return
     */
    public static ArrayList remove_ajuda_concorrente(int ind) {
        ajudas.set(ind, "Ajuda utilizada");
        return ajudas;
    }

    /*
     Recebe o indice do input da ajuda correspondente e substitui por uma string 
     dizendo que a ajuda ja foi tuilizada
     */
    /**
     *
     * @param ind
     * @return
     */
    public static ArrayList remove_opcao_ajuda_concorrente(int ind) {

        op_ajuda.set(ind, "Ajuda Utilizada");
        return op_ajuda;
    }

    /*
     Este metodo gere a ajuda 50-50
     Recebe por parametro a pergunta correspondente e a sua resposta
     Sabendo qual a resposta correcta, escolhe aleatoriamente de entre as outras 
     3 opçoes uma delas
     Retorna uma string que contem a pergunta, a resposta correcta e a resposta
     aleatoria
     */
    /**
     *
     * @param pergunta
     * @param resposta
     * @return
     */
    public static String ajuda_50_50(String pergunta, String resposta) {
        Random rand = new Random();
        String[] t = pergunta.split(Pattern.quote("\n"));
        ArrayList opcoes = new ArrayList();
        if ("R:A".equals(resposta)) {
            opcoes.add(t[3]);
            opcoes.add(t[4]);
            opcoes.add(t[5]);
        } else if ("R:B".equals(resposta)) {
            opcoes.add(t[2]);
            opcoes.add(t[4]);
            opcoes.add(t[5]);
        } else if ("R:C".equals(resposta)) {
            opcoes.add(t[2]);
            opcoes.add(t[3]);
            opcoes.add(t[5]);
        } else if ("R:D".equals(resposta)) {
            opcoes.add(t[2]);
            opcoes.add(t[3]);
            opcoes.add(t[4]);
        }
        String r;
        int pos;
        pos = rand.nextInt(opcoes.size());
        String selecionada = (String) opcoes.get(pos);
        String q_r = null;
        if ("R:A".equals(resposta)) {
            r = t[2];
            q_r = t[0] + "\n" + r + "\n" + selecionada;
        } else if (pos == 0 && "R:B".equals(resposta)) {
            r = t[3];
            q_r = t[0] + "\n" + selecionada + "\n" + r;
        } else if (pos != 0 && "R:B".equals(resposta)) {
            r = t[3];
            q_r = t[0] + "\n" + r + "\n" + selecionada;
        } else if ("R:C".equals(resposta) && pos < 1) {
            r = t[4];
            q_r = t[0] + "\n" + selecionada + "\n" + r;
        } else if ("R:C".equals(resposta) && pos > 1) {
            r = t[4];
            q_r = t[0] + "\n" + r + "\n" + selecionada;
        } else if ("R:D".equals(resposta)) {
            r = t[5];
            q_r = t[0] + "\n" + selecionada + "\n" + r;
        }
        return q_r;
    }

    /*
     Gera de forma aleatoria quatro valores, correspondentes a uma percentagem
     que e referente supostamente ao numero de pessoas que escolheu cada uma 
     das opçoes.Mostra ainda a percentagem de pessoa que nao responderam
     Retorna um array que contem as percentagens por opcao e a percentagem de 
     pessoas que nao responderam
     */
    /**
     *
     * @return @throws InterruptedException
     */
    public static ArrayList Ajuda_Publico() throws InterruptedException {
        System.out.println("A aguardar a resposta do público...");
        Thread.sleep(2000);
        double valor1;
        double valor2;
        double valor3;
        double valor4;
        double pessoasquenaoreponderam;

        valor1 = Math.random() * 50;
        valor2 = Math.random() * (50 - valor1);
        valor3 = Math.random() * 50;
        valor4 = Math.random() * (50 - valor3);
        pessoasquenaoreponderam = 100 - valor1 - valor2 - valor3 - valor4;

        DecimalFormat decimal = new DecimalFormat("0.##");
        String a = decimal.format(valor1);
        String b = decimal.format(valor2);
        String c = decimal.format(valor3);
        String d = decimal.format(valor4);
        String e = decimal.format(pessoasquenaoreponderam);
        ArrayList percentagens = new ArrayList();
        percentagens.add("A:" + a);
        percentagens.add("B:" + b);
        percentagens.add("C:" + c);
        percentagens.add("D:" + d);
        percentagens.add("Percentagem de publico que nao respondeu: " + e);
        return percentagens;
    }

}
