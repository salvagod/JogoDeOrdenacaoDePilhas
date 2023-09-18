package pilhaordenada;

import java.util.Random;
import java.util.Scanner;

public class JogoDeOrdenacaoDePilhas { // Classe principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para pegar informações do teclado
        Random random = new Random(); // Variável para gerar números randômicos

        System.out.println("Bem-vindo ao Jogo de Ordenação de Pilhas!");
        System.out.println("Você deve fazer a ordenação na pilha 3.");

        System.out.print("Digite a quantidade de elementos da pilha: "); // Inserção da quantidade de elementos da pilha
        int tamanhoPilhas = scanner.nextInt();

        System.out.print("Digite 'crescente' ou 'decrescente' para a ordem de ordenação do jogo: "); // Define se o jogo deverá ser crescente ou decrescente
        String ordem = scanner.next();

        Pilha pilha1 = new Pilha(); // Cria as pilhas
        Pilha pilha2 = new Pilha();
        Pilha pilha3 = new Pilha();

        
        for (int i = 0; i < tamanhoPilhas; i++) { // Preenche a pilha1 com números aleatórios de 1 a 100 de acordo com o tamanho definido anteriormente
            pilha1.push(new Node(random.nextInt(100) + 1));
        }

        if (ordem.equals("decrescente")) { // Ordena a pilha inicial (1) de acordo com o modo escolhido pelo usuário
            pilha1.ordenarDecrescente();
        } else if (ordem.equals("crescente")) {
            pilha1.ordenarCrescente();
        }

        int movimentos = 0; // Variável contadora de movimentos
        boolean ordenacaoConcluida = false; // Variável booleana para encerrar o jogo

        while (true) { // Loop infinito para a execução do jogo
            System.out.print("Pilha 1: "); // Mostra a disposição inicial das pilhas
            pilha1.imprimirPilha();
            System.out.print("Pilha 2: ");
            pilha2.imprimirPilha();
            System.out.print("Pilha 3: ");
            pilha3.imprimirPilha();

            System.out.println("Menu de Opções:"); // Mostra as opções que o jogador têm para escolher
            System.out.println("0 - Sair do Jogo");
            System.out.println("1 - Movimentar");
            System.out.println("2 - Solução Automática");

            System.out.print("Escolha uma opção: "); // Lê opção escolhida pelo usuário
            int opcao = scanner.nextInt();

            if (opcao == 0) { // Opção que encerra o jogo
                System.out.println("Jogo encerrado.");
                break;
            } else if (opcao == 1) { // Opção para ordenar manualmente as pilhas
                System.out.print("Digite a pilha de origem, 1, 2 ou 3: "); // Define de qual pilha o topo será retirado
                int origem = scanner.nextInt();

                System.out.print("Digite a pilha de destino, 1, 2 ou 3: "); // Define para qual pilha esse topo irá
                int destino = scanner.nextInt();

                Pilha pilhaOrigem = null; // Variáveis ponteiro para definir origem e destino
                Pilha pilhaDestino = null;

                if (origem == 1) { // A pilhaOrigem recebe o valor da pilha de acordo com a pilha de origem escolhida anteriormente
                    pilhaOrigem = pilha1;
                } else if (origem == 2) {
                    pilhaOrigem = pilha2;
                } else if (origem == 3) {
                    pilhaOrigem = pilha3;
                }

                if (destino == 1) { // A pilhaDestino recebe o valor da pilha de acordo com a pilha de destino escolhida anteriormente
                    pilhaDestino = pilha1;
                } else if (destino == 2) {
                    pilhaDestino = pilha2;
                } else if (destino == 3) {
                    pilhaDestino = pilha3;
                }

                if (pilhaOrigem != null && pilhaDestino != null) { // Se tanto a pilha de origem quanto a de destino não estiverem vazias, executa o código abaixo
                    if (ordem.equals("decrescente") && !pilhaOrigem.estaVazia() && (pilhaDestino.estaVazia() || pilhaOrigem.olharTopo().getInformacao() > pilhaDestino.olharTopo().getInformacao())) {
                        pilhaDestino.push(pilhaOrigem.pop()); 
                        /* Caso o modo de jogo seja decrescente, a pilha de origem não esteja vazia, e a pilha de destino ou estiver vazia, 
                        ou o topo da pilha de origem seja maior que o topo da pilha de destino, o topo da pilha de destino será o topo da pilha de origem*/
                        movimentos++; // Aumenta os movimentos feitos em 1
                    } else if (ordem.equals("crescente") && !pilhaOrigem.estaVazia() && (pilhaDestino.estaVazia() || pilhaOrigem.olharTopo().getInformacao() < pilhaDestino.olharTopo().getInformacao())) {
                        pilhaDestino.push(pilhaOrigem.pop());
                        /* Caso o modo de jogo seja crescente, a pilha de origem não esteja vazia, e a pilha de destino ou estiver vazia, 
                        ou o topo da pilha de origem seja menor que o topo da pilha de destino, o topo da pilha de destino será o topo da pilha de origem*/
                        movimentos++; 
                    } else {
                        System.out.println("Movimento inválido!"); // Caso o movimento seja inválido, avisará o usuário
                    }
                } else {
                    System.out.println("Pilha de origem ou destino inválida!"); // Avisará o usuário caso alguma das pilhas escolhidas não exista
                }

                
                if ((pilha3.isOrdenada() || pilha3.isOrdenadaDecrescente()) && pilha3.tamanho() == tamanhoPilhas) { 
                    /* Caso a pilha3 (pilha de destino final) esteja ordenada, ou crescente ou decrescentemente, e com todos os elementos definidos na inicialização,
                    encerrará o jogo e mostrará a quantidade de movimentos feitos pelo jogador
                    */
                    ordenacaoConcluida = true;
                    System.out.println("Ordenação concluída em " + movimentos + " jogadas!");
                    break;
                }
            } else if (opcao == 2) { // Opção para resolução automática
                resolverAutomaticoPilha resolver = new resolverAutomaticoPilha(); // Cria uma instância para resolver o problema
                movimentos += resolver.ordenarAutomaticamente(pilha1, pilha3, pilha2, tamanhoPilhas, ordem); // Chama a função para resolução com todas as informações e soma os movimentos
                ordenacaoConcluida = (pilha3.isOrdenada() || pilha3.isOrdenadaDecrescente()) && pilha3.tamanho() == tamanhoPilhas; 
                /*  Define se a ordenação foi concluída com base em se está ordenada (crescente ou decrescente) e se está com todos os elementos*/
                if (ordenacaoConcluida) { // Caso a ordenação tenha sido terminada, encerra o jogo e mostra os movimentos, alertando o usuário que esses são os movimentos mínimos
                    System.out.println("Ordenação concluída em " + movimentos + " jogadas, esse é o número mínimo possível!");
                    break;
                }
            } else {
                System.out.println("Opção inválida!");
            }
        }

        if (!ordenacaoConcluida) {
            System.out.println("Jogo encerrado sem concluir a ordenação.");
        }

        scanner.close(); // Fecha a entrada do teclado
    }
}

// Classe Node
class Node {
    private Integer informacao;
    private Node proximo;

    public Node(Integer informacao) {
        this.informacao = informacao;
        this.proximo = null;
    }

    public Integer getInformacao() {
        return informacao;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }

    public void setInformacao(Integer informacao) {
        this.informacao = informacao;
    }
}

// Classe Pilha
class Pilha {
    private Node topo;

    public Pilha() {
        topo = null;
    }

    public void push(Node elemento) { // Define o elemento recebido como novo topo
        elemento.setProximo(topo);
        topo = elemento;
    }

    public Node pop() { // Retira o topo da pilha
        if (estaVazia()) {
            System.out.println("A pilha está vazia.");
        }
        Node valor = topo;
        topo = topo.getProximo();
        return valor;
    }

    public Node olharTopo() { // Retorna o valor atual do topo
        if (estaVazia()) {
            System.out.println("A pilha está vazia.");
        }
        return topo;
    }

    public boolean estaVazia() { // Verifica se a pilha está vazia
        return topo == null;
    }

    public int tamanho() { // Verifica o tamanho da pilha
        int tamanho = 0;
        Node atual = topo;
        while (atual != null) {
            tamanho++;
            atual = atual.getProximo();
        }
        return tamanho;
    }

    public void ordenarCrescente() { // Função para ordenar a pilha de maneira crescente
        if (estaVazia() || tamanho() == 1) {
            return; // Não executa nenhuma ação caso a pilha esteja vazia ou somente com um elemento
        }

        Pilha auxiliar = new Pilha(); // Cria pilha auxililar

        while (!estaVazia()) { // Enquanto a pilha não estiver vazia, ordenará
            Node atual = pop();

            
            while (!auxiliar.estaVazia() && auxiliar.olharTopo().getInformacao() > atual.getInformacao()) { 
                push(auxiliar.pop());
            }

            auxiliar.push(atual);
        }

        
        while (!auxiliar.estaVazia()) { // Copia os elementos de volta para a pilha original
            push(auxiliar.pop());
        }
    }

    public void ordenarDecrescente() {
        if (estaVazia() || tamanho() == 1) {
            return;
        }

        Pilha auxiliar = new Pilha();

        while (!estaVazia()) {
            Node atual = pop();

            while (!auxiliar.estaVazia() && auxiliar.olharTopo().getInformacao() < atual.getInformacao()) {
                push(auxiliar.pop());
            }

            auxiliar.push(atual);
        }

        while (!auxiliar.estaVazia()) {
            push(auxiliar.pop());
        }
    }

    public boolean isOrdenada() { // Verifica se está ordenada crescentemente
        Node atual = topo;
        while (atual != null && atual.getProximo() != null) {
            if (atual.getInformacao() > atual.getProximo().getInformacao()) { // Caso o valor atual seja maior que o próximo, a pilha não está ordenada
                return false;
            }
            atual = atual.getProximo(); // Passa para o próximo valor caso não entre no IF anterior
        }
        return true; // Se tudo estiver ordenado retornará verdadeiro
    }

    public boolean isOrdenadaDecrescente() {// Verifica se está ordenada decrescentemente
        Node atual = topo;
        while (atual != null && atual.getProximo() != null) {
            if (atual.getInformacao() < atual.getProximo().getInformacao()) { // Caso o valor atual seja menor que o próximo, a pilha não está ordenada
                return false;
            }
            atual = atual.getProximo(); // Passa para o próximo valor caso não entre no IF anterior
        }
        return true; // Se tudo estiver ordenado retornará verdadeiro
    }

    public void imprimirPilha() { // Função para impressão de pilhas
        Node atual = topo;
        
        while (atual != null) {
            System.out.print(atual.getInformacao() + " ");
            atual = atual.getProximo();
        }
        
        System.out.println();
    }
}


class resolverAutomaticoPilha {// Classe Resolução
    private int movimentos = 0; // Variável para contar os movimentos

    public int ordenarAutomaticamente(Pilha origem, Pilha destino, Pilha auxiliar, int tamanhoPilhas, String ordem) {
        movimentos = 0; 
        
        moverElementos(origem, destino, auxiliar, tamanhoPilhas, ordem); // Função para mover os elementos automaticamente
        return movimentos;
    }

    public void moverElementos(Pilha origem, Pilha destino, Pilha auxiliar, int n, String ordem) {
        if (n > 0) {
            
            moverElementos(origem, auxiliar, destino, n - 1, ordem); // Move elementos da origem para auxiliar, usando destino como auxiliar

            
            if (!origem.estaVazia()) { // Verifica se a pilha de origem não está vazia antes de tentar mover um elemento
                boolean movimentoValido = false; // Verifica se o movimento é válido
                if (ordem.equals("crescente") && (destino.estaVazia() || origem.olharTopo().getInformacao() < destino.olharTopo().getInformacao())) {
                    movimentoValido = true; 
                    /* Caso o modo de jogo seja crescente, a pilha de destino esteja vazia, e o topo da pilha de origem 
                    seja menor que o topo da pilha de destino, o movimento é válido */
                } else if (ordem.equals("decrescente") && (destino.estaVazia() || origem.olharTopo().getInformacao() > destino.olharTopo().getInformacao())) {
                    movimentoValido = true;
                    /* Caso o modo de jogo seja decrescente, a pilha de destino esteja vazia, e o topo da pilha de origem 
                    seja maior que o topo da pilha de destino, o movimento é válido */
                }

                if (movimentoValido) { // Se o movimento for válido, colocará o topo da pilha de origem no topo da pilha de destino
                    Node elemento = origem.pop();
                    destino.push(elemento);
                    movimentos++;
                }
            }

            
            moverElementos(auxiliar, destino, origem, n - 1, ordem); // Move elemento de auxiliar para destino, definindo a origem como auxiliar
        }
    }
}