/** 
 * Mozart Junio Alves de Sousa
 * Discente de Ciência da Computação
 * Pontifícia Universidade Católica de Minas Gerais
 * Campo Minado em Java
 * Estruturas; Matriz de objetos, Lista dinâmica
 * Classe Nodo, objetos do Campo Minado
 */
class Nodo {
    Nodo prox; //Utilizado para encadear na Lista
    boolean selected; //Se verdadeiro o terminal irá mostrar o campo na tela envolvido por colchetes [ ]
    boolean wasSelected; //Se verdadeiro o terminal irá mostrar seu conteúdo
    boolean isBomb; //Se verdadeiro, o campo é uma bomba;
    boolean isEmpty; //Se verdadeiro, o campo não contém valores.
    boolean isAvailable; //Utilizado para verificar disponibilidade dos campos adjacentes de acordo com a jogada inicial do jogador.
    int number; //NUmero de bombas ao redor do campo
    int y; //Posição no eixo y do plano cartesiano
    int x; //Posição no eixo x do plano cartesiano

    /**
     * Construtor da classe Nodo
     */
    public Nodo() {
        this.prox = null;
        this.selected = false;
        this.wasSelected = false;
        this.isBomb = false;
        this.isEmpty = true;
        this.isAvailable = true;
        this.number = 0;

    }//end construtor Nodo
}// end class nodo