/** 
 * Mozart Junio Alves de Sousa
 * Discente de Ciência da Computação
 * Pontifícia Universidade Católica de Minas Gerais
 * Campo Minado em Java
 * Estruturas; Matriz de objetos, Lista dinâmica
 * Classe Lisa, lista dinâmica simples, encadeada apenas a direita, .prox
 */
class Lista{
    //Nó cabeça e referência
    Nodo primeiro, ultimo;
    int n;//quantidade de elementos inseridos.

    //Construtor da classe
    public Lista (){
        primeiro = new Nodo();
        ultimo = primeiro;
    }

    /**
     * Metodo para alocação de dados na estrutura
     */
    void inserirFim( Nodo x )throws Exception{
        Nodo tmp = x; //Nodo a ser inserido
        
        ultimo.prox = tmp; // referência aponta para um dado que aponta para o novo dado a ser inserido na estrutura
        ultimo = tmp; // referência agora aponta para o ultimo dado inserido
        n++;    //inseridos = inseriros + 1
    }//end inserirFim
    
    /**
     * Metodo responsável por marcar como selecionado
     * todos os campos em branco adjacentes presentes na lista
     */
    void showBlanks(){
        CampoMinado cm = new CampoMinado(); //Construtor da classe CampoMinado, para definir campo como selecionado
        //Primeiro é um nó cabeça que aponta para o primeiro dado, por isso, primeiro.prox, enquanto o ponteiro não for nulo, e ponteiro agora aponta para o próximo.
        for (Nodo i = primeiro.prox; i != null; i = i.prox){            
            i.wasSelected = true; //Marcar como selecionado
            
            //Marcar adjacentes como selecionados, verificando para não acessar nulos.
            if( ( (i.y-1) > 0 ) && !cm.campo[i.y-1][i.x  ].isBomb ) cm.campo[i.y-1][i.x  ].wasSelected = true;//adjacente superior   
            if( ( (i.x-1) > 0 ) && !cm.campo[i.y  ][i.x-1].isBomb ) cm.campo[i.y  ][i.x-1].wasSelected = true;//adjacente esquerdo
            if( ( (i.y+1) < 12) && !cm.campo[i.y+1][i.x  ].isBomb ) cm.campo[i.y+1][i.x  ].wasSelected = true;//adjacente inferior
            if( ( (i.x+1) < 9 ) && !cm.campo[i.y  ][i.x+1].isBomb ) cm.campo[i.y  ][i.x+1].wasSelected = true;//adjacente direito
            if( ( (i.y-1) > 0 ) && (i.x-1) > 0 && !cm.campo[i.y-1][i.x-1].isBomb ) cm.campo[i.y-1][i.x-1].wasSelected = true;//adjacente superior esquerdo
            if( ( (i.y+1) < 12) && (i.x-1) > 0 && !cm.campo[i.y+1][i.x-1].isBomb ) cm.campo[i.y+1][i.x-1].wasSelected = true;//adjacente inferior esquerdo
            if( ( (i.y+1) < 12) && (i.x+1) < 9 && !cm.campo[i.y+1][i.x+1].isBomb ) cm.campo[i.y+1][i.x+1].wasSelected = true;//adjacente inferior direito
            if( ( (i.y-1) > 0 ) && (i.x+1) < 9 && !cm.campo[i.y-1][i.x+1].isBomb ) cm.campo[i.y-1][i.x+1].wasSelected = true;//adjacente superior direito
         }//end for
         
    }//end metodo showBlanks

    /**
     * Função responsável por verificar se o quadrado pesquisado
     *  é adjacente aos ja inseridos na lista.
     * @param y valor no eixo a ser procurado
     * @param x valor no eixo a ser procurado
     * @return verdadeiro se encontrar 
     */
    boolean pesquisarAdjacente(int y, int x) {
        boolean retorno = false;
        
        for (Nodo i = primeiro.prox; i != null && !retorno; i = i.prox) {
                retorno = ( y   == i.y && x-1 == i.x && !retorno)? true :retorno;//adjacente esquerdo
                retorno = ( y-1 == i.y && x   == i.x && !retorno)? true :retorno;//adjacente superior
                retorno = ( y-1 == i.y && x-1 == i.x && !retorno)? true :retorno;//adjacente superior esquerdo
                retorno = ( y-1 == i.y && x+1 == i.x && !retorno)? true :retorno;//adjacente superior direito
                retorno = ( y   == i.y && x+1 == i.x && !retorno)? true :retorno;//adjacente  direito
                retorno = ( y+1 == i.y && x   == i.x && !retorno)? true :retorno;//adjacente inferior                
                retorno = ( y+1 == i.y && x+1 == i.x && !retorno)? true :retorno;//adjacente inferior direito
                retorno = ( y+1 == i.y && x-1 == i.x && !retorno)? true :retorno;//adjacente inferior esquerdo
            }

        return retorno;
        
     }//end pesquisarAdjacente

    /**
     * Função boolena que pesquisa se há esse item na fila.
     * @param y valor no eixo a ser procurado
     * @param x valor no eixo a ser procurado
     * @return verdadeira se houver o campo na fila
     */
    public boolean pesquisar(int y, int x)throws Exception {
        boolean retorno = false;
        for (Nodo i = primeiro.prox; i != null && !retorno; i = i.prox) {
            retorno = (i.y == y && i.x == x);
        }
        return retorno;
     }//end função pesquisar

}//end Lista