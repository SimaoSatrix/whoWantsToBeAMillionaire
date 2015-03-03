/*
 * A classe concorrente e responsavel pr definir o concorrente/jogador.
 * E uma estrutura que permite nomear o jogador pelo nome de jogador.
 * Indica o patamar em que o concorrente se encontra a cada momento do jogo.
 */
package qqsm;

import java.util.Objects;

/**
 *
 * @author Simao Ramos e Marlene Barroso
 * @
 */
/**
 * Variaveis de instancia:
 *NOME - nome do concorrente ao longo do jogo 
 *PATAMAR - patamar em que o jogador se encontra ao longo do jogo
 */
public class Concorrente {

    private String nome; 
    private int patamar;
  
    /**
     * COnstrutor por defeito
     */
    public Concorrente() {

    }

    
    /**
     * Construtor com parametros
     * @param nome
     * @param patamar 
     */
    public Concorrente(String nome, int patamar) {
        this.nome = nome;
        this.patamar = patamar;

    }

    public String getNome() {
        return nome;
    }

    public int getPatamar() {
        return patamar;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPatamar(int patamar) {
        this.patamar = patamar;
    }

    /*
    *Caso o concorrente tenha respondido a menos de 5 questoes encontra-se no 
    *patamar 1
    *Caso o concorrente tenha respondido a menos de 10 e mais de 5 questoes 
    *encontra-se no patamar 2
    *Caso o concorrente tenha respondido a mais de 10 questoes encontra-se no 
    *patamar 3
    */
    public void setPatamar_atraves_nivel(Concorrente concorrente, int nivel_atual) {
        if (nivel_atual < 5) {
            concorrente.setPatamar(1);
        } else if (nivel_atual < 10 && nivel_atual >= 5) {
            concorrente.setPatamar(2);
        } else {
            concorrente.setPatamar(3);
        }
    }
    
    /*
     *classe responsavel por registar um concorrente perguntando ao utilizador
     *o nome pelo qual quer ser identificado, e atribuindo-lhe o nivel minimo
     *no comeco do jogo (nivel 0)
     */

    public void registarConcorrente() {
        String resposta;
        do {
            System.out.print("Nome:");
            resposta = QQSM.inputs();
        } while (resposta.equals(""));

        setNome(resposta);
        setPatamar(0);
    }

    /*
    * Retorna o nome actual do jogador
    */
    @Override
    public String toString() {
        String s = nome + " o seu nível atual é o " + patamar;
        return s;
    }

    /*
    * 
    */
    @Override
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        }
        if (getClass() != x.getClass()) {
            return false;
        }
        
        final Concorrente outro;
        outro = (Concorrente) x;
        if (!Objects.equals(this.nome, outro.nome)) {
            return false;
        }
        if (this.patamar != outro.patamar) {
            return false;
        }
        return true;
    }

    /*
    hashCode é principalmente para excluir igualdade
    */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + this.patamar;
        return hash;
    }

}
