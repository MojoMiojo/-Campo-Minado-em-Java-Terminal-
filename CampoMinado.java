/** 
 * Mozart Junio Alves de Sousa
 * Discente de Ciência da Computação
 * Pontifícia Universidade Católica de Minas Gerais
 * Campo Minado em Java
 * Estruturas; Matriz de objetos, Lista dinâmica
 */

import java.util.Random;
import java.util.Scanner;

/**
 * Classe CampoMinado
 */
public class CampoMinado extends Nodo {
    public static Nodo[][] campo; //Matriz de objetos que armazenará os valores do jogo
    static terminal t1 = new terminal(); //Objeto da classe Terminal que é responsável por atualizar os valroes da tela, padrão cmd
    static Lista l1; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l2; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l3; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l4; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l5; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l6; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static Lista l7; //Listas dinamicas de armazenamento dos campos vazios para que possam ser revelados em conjuto
    static int x = 0;//Eixo X do plano cartesiano, largura da matriz.
    static int y = 0;//Eixo Y do plano cartesiano, altura da matriz.
    static int nMoves = 0;// Variavel utilizada para verificar se é a primeira ação do jogador.

    /**
     * Metodo responsável por garantir que o primeiro
     * campo selecionado e seus adjacente não sejam 
     * bombas ou valores numéricos.
     * Teste em sentido anti-horário, também
     * com testes para certificar que o campo selecionado
     * não gere exceção. NUllPointerException
     */
    public static void isAvailable( ){
        
        campo[y][x].isAvailable = false; // Primeiro campo selecionado pelo usuário
            
        if( y > 0 ){
            campo[y-1][ x ].isAvailable = false;} //adjacente superior

        if( y > 0 && x > 0 ){
            campo[y-1][x-1].isAvailable = false;} //adjacente superior esquerdo
        
        if( x > 0 ){
            campo[ y ][x-1].isAvailable = false;} //adjacente esquerdo

        if(y < 12 && x > 0){
            campo[y+1][x-1].isAvailable = false;} //adjacente inferior esquerdo

        if( y < 12){
            campo[y+1][ x ].isAvailable = false;} //adjacente inferior
            
        if( y < 12 && x < 9){
            campo[y+1][x+1].isAvailable = false;} //adjacente inferior direito

        if(x < 9 ){
            campo[ y ][x+1].isAvailable = false;} //adjacente direito

        if( y > 0 && x < 9 ){
            campo[y-1][x+1].isAvailable = false;} //adjacente superior direito
    }//end metodo isAvailable

    /**
     * Metodo responsável por gerar as bombas aleatóriamente
     * certificando que não haverá inserção de bombas
     * no mesmo local, garantido a quantidade de bombas
     * pré determinadas.
     */
    public static void createBomb() {
        Random gerador = new Random(); // Construtor da classe Random responsável pelo sorteio do campo

        int x = 0, y = 0;
        //loop de geração das bombas
        for (int i = 0; i < 15; i++) {
            //Enquanto houver bomba no local ou se o campo faz parte dos campos adjacentes protegidos de bombas
            do {
                x = gerador.nextInt(10);// eixo x __
                y = gerador.nextInt(13);// eixo y |
            } while (campo[y][x].isBomb || !(campo[y][x].isAvailable) );// se já houver bomba no local, criar nova.
            //Selecionar que o campo escolhido agora é uma bomba.
            campo[y][x].isBomb = true;
            //Selecionar que o campo não mais é vazio.
            campo[y][x].isEmpty = false;
        } // end for i < 15
    }//end metodo createBomb

    /**
     * Metodo responsável pela criação de numeros
     * verificando a quantidade de bombas ao redor
     * de cada campo, por meio de teste de seus adjacentes.
     */
    public static void createNumbers() {
        int bombAround = 0;
        //Loop para percorrer cada campo da matriz de objetos
        for (int i = 0; i < campo.length; i++){
            for (int j = 0; j < campo[0].length; j++) {
                //Se o campo não for uma bomba faça...
                if (campo[i][j].isBomb == false) {
                    if (i == 0) { //Primeria linha da matriz
                        if (j == 0) {
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior
                            bombAround = ( campo[i+1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito
                        } // se primeira linha && primeira coluna
                        else if( j != campo[0].length-1 ){
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                            bombAround = ( campo[i+1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior esquerdo
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior
                            bombAround = ( campo[i+1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito          
                        } // Colunas fora dos extremos esquerdo e direito.
                        else{
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                            bombAround = ( campo[i+1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior esquerdo
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior
                        }//coluna ao extremo direito.
                    } //fim if (i == 0)
                    else if( i != campo.length-1)
                    { 
                        if (j == 0) {
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior
                            bombAround = ( campo[i+1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito
                            bombAround = ( campo[i-1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior direito
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                        } // Linhas entre o topo e a base da matriz && primeira coluna
                        else if( j != campo[0].length-1 ){
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                            bombAround = ( campo[i-1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior esquerdo
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                            bombAround = ( campo[i+1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior esquerdo
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior
                            bombAround = ( campo[i+1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito
                            bombAround = ( campo[i-1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior direito                            
                        }//// Linhas entre o topo e a base da matriz
                        else{
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                            bombAround = ( campo[i-1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior esquerdo
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                            bombAround = ( campo[i+1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior esquerdo
                            bombAround = ( campo[i+1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente inferior

                        }//se estivermos na última coluna
                    }//fim do teste entre a primeira e ultima linha
                    else{
                        //Se estivermos na ultima linha, base da matriz.
                        if (j == 0) {
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                            bombAround = ( campo[i-1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito
                        } // se primeira linha && primeira coluna
                        else if( j != campo[0].length-1 ){
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                            bombAround = ( campo[i-1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior esquerdo
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                            bombAround = ( campo[i-1][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior direito
                            bombAround = ( campo[ i ][j+1].isBomb == true)?bombAround+=1:bombAround;//adjacente direito          
                        }//senão a primeira coluna tampouco a última
                        else{
                            bombAround = ( campo[i-1][ j ].isBomb == true)?bombAround+=1:bombAround;//adjacente superior
                            bombAround = ( campo[i-1][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente superior esquerdo
                            bombAround = ( campo[ i ][j-1].isBomb == true)?bombAround+=1:bombAround;//adjacente esquerdo
                        }//se ultima coluna
                    }//se ultima linha
                } // se campo não for uma bomba
                campo[i][j].number = bombAround; //Definir numero de bombas adjacentes no campo.
                campo[i][j].isEmpty= false; //Definir que o campo não mais é vazio.
                bombAround = 0; //Reiniciar contador de bombas adjacentes
            } // end for j
        } // end for i

    }//end metodo createNumbers

    /**
     * Metodo responsável por escrever na matriz de impressão na tela
     * da classe terminal.
     */
    static void writeOnScreen( String str, int y, int x ){
        int cont = 0;
        while( cont < str.length()){
            t1.matriz[y][x+cont] = str.charAt(cont);
            cont++;
        }
    }

    /**
     * Criação do matriz de objetos
     * na qual armazenará valores e bombas
     */
    public static void createField() {
        campo = new Nodo[13][10];

        String creator = "Mozart Junio Alves de Sousa";
        String info = "Discente de Ciencia da Computacao";
        String local = "Pontificia Universidade Catolica de Minas Gerais";
        int cont = 0;

        writeOnScreen(creator, 1, 2);
        writeOnScreen(info, 2,2 );
        writeOnScreen(local, 3, 2);
        //Loop para preenchimento da matriz
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                campo[i][j] = new Nodo(); //alocação da classe Nodo 
                campo[i][j].y = i; //posição do eixo y em que o objeto se encontra
                campo[i][j].x = j; //posição do eixo x em que o objeto se encontra
            } // end colunas(vertical)
        } // end Linhas(horizontal)
        //Colchete inicial
        campo[0][0].selected = true; //Os objetos cujo selected seja igual a true, irão imprimir consigo os colchetes [ ]
    }// end campo

    /*
     * Função responsável por retornar uma matriz de 
     * caracteres interpretados a paritr da matriz de Nodo's
     * será utilizada para enviar o que será impresso ao terminal
     * ao ler a tecla 1
     */
    public static char [][] showField(){
        char show[][] = new char[campo.length][campo[0].length];

        for (int i = 0; i < campo.length; i++){
            for (int j = 0; j < campo[0].length; j++) {
                if (campo[i][j].isBomb == true) // Se o campo for uma bomba, interpretar como '!'
                {
                    show[i][j] = '!';
                }//senão se bomba
                else //se for um numero
                {
                    if(campo[i][j].number == 0){ // se 0 interpretar como _
                        show[i][j] = '_';
                    }else{ //senão como o numero que é
                        show[i][j] = Character.forDigit(campo[i][j].number, 10);
                    }
                }//senão se não vazio tampouco bomba (numero)
            }//end colunas(vertical)
        } // end Linhas(horizontal)
        return show; //retorno da matriz de char
    }// end metodo showField

    /**
     * Função responsável por interpretar o campo
     * e enviar ao terminal para que possa ser impresso
     */
    public static char[][] convertToString( ){
        char[][] converted = new char[campo.length][campo[0].length];
        char aux;

        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                //Se for uma bomba selecionada em algum momento do jogo.
                if (campo[i][j].isBomb == true && campo[i][j].wasSelected ){
                     converted[i][j] = '!';
                }//senão se bomba
                else{
                    if( campo[i][j].wasSelected ){ //se o campo foi selecionado em algum momento do jogo
                        if(campo[i][j].number == 0){ //se for 0 interpretar como _
                            converted[i][j] = '_';
                        }else{ //senão como o numero que é
                            aux = Character.forDigit(campo[i][j].number, 10);
                            converted[i][j] = aux;
                        }//end else number == 0
                    }else{
                        converted[i][j] = '#';
                    }
                }//senão se não vazio tampouco bomba (numero)
            } // end colunas(vertical)
        } // end Linhas(horizontal)
        return converted; // retorno da matriz de char interpretada
    }// end convertToString

    /**
     * Função responsável por agrupar os campos 
     * vazios em listas dinamicas, existem 7 listas pois
     * cada grupo de adjacentes semelhante é armazenada em sua
     * respectiva. Não mais do que 7 grupos foram gerados em testes.
     */
    public static void setBlanks( )throws Exception{
        //A variavel blanks é utilizada para verificar se aquela lista é ou não vazia.
        //Se vazia acrescentar primeiro valor. Senão, buscar por semelhantes e agrupar.
        int blanks  = 0; l1 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks2 = 0; l2 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks3 = 0; l3 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks4 = 0; l4 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks5 = 0; l5 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks6 = 0; l6 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.
        int blanks7 = 0; l7 = new Lista(); //Definição local dos contrutores para que ao reiniciar o jogo todos tenham seu conteúdo zerado.

        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if( campo[i][j].number == 0 && !(campo[i][j].isBomb) ){
                    //pesquisarAdjacente é uma função booleana da classe Lista, que verifica se o campo deve sera adicionado a lista de teste.
                    //inserirFim insere sempre no final da lista.
                    if( blanks == 0){ //Se primeira lista vazia
                        l1.inserirFim(campo[i][j]);
                        blanks = 1;
                    }else if( l1.pesquisarAdjacente(i, j)){
                            l1.inserirFim(campo[i][j]);
                    
                    }else if( blanks2 == 0){//Se segunda lista vazia
                            l2.inserirFim(campo[i][j]);
                            blanks2 = 1;
                    }else if( l2.pesquisarAdjacente(i, j)){
                            l2.inserirFim(campo[i][j]);
                    }//end if primeira lista
                    
                    else if( blanks3 == 0){//Se terceira lista vazia
                            l3.inserirFim(campo[i][j]);
                            blanks3 = 1;
                    }else if( l3.pesquisarAdjacente(i, j)){
                            l3.inserirFim(campo[i][j]);
                    }//end if primeira lista

                    else if( blanks4 == 0){//Se quarta lista vazia
                            l4.inserirFim(campo[i][j]);
                            blanks4 = 1;
                    }else if( l4.pesquisarAdjacente(i, j)){
                            l4.inserirFim(campo[i][j]);
                    }//end blanks 4

                    else if( blanks5 == 0){//Se quinta lista vazia
                            l5.inserirFim(campo[i][j]);
                            blanks5 = 1;
                    }else if( l5.pesquisarAdjacente(i, j)){
                            l5.inserirFim(campo[i][j]);
                    }//end if primeira lista

                    else if( blanks6 == 0){//Se sexta lista vazia
                            l6.inserirFim(campo[i][j]);
                            blanks6 = 1;
                    }else if( l6.pesquisarAdjacente(i, j)){
                            l6.inserirFim(campo[i][j]);
                    }//end if primeira lista

                    else if( blanks7 == 0){//Se sétima lista vazia
                            l7.inserirFim(campo[i][j]);
                            blanks7 = 1;
                    }else if( l7.pesquisarAdjacente(i, j)){
                            l7.inserirFim(campo[i][j]);
                    }//end if primeira lista

                }//end if isBlank
            }//end for j
        }//end for i
    }//end metodo show blanks

    
    static void showAround()throws Exception{
        boolean blank1 = l1.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank2 = l2.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank3 = l3.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank4 = l4.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank5 = l5.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank6 = l6.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        boolean blank7 = l7.pesquisar( y,x );//Busca na lista se existe o objeto na posição y,x escolhida pelo usuário.
        if( blank1 ){ l1.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank2 ){ l2.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank3 ){ l3.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank4 ){ l4.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank5 ){ l5.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank6 ){ l6.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
        if( blank7 ){ l7.showBlanks( ); }//Se existir marcar todos a sua volta como selecionados em algum momento do jogo, wasSelected = true
    }//end metodo showAround

    /**
     * Função inteira que verifica se o jogador
     * já abriu todos os campos que não são bombas.
     * Verificar se o mesmo ganhou a partida.
     */
    public static int gameStatus() {
        int cont = 0;

        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if( campo[i][j].wasSelected )
                    cont++; 
            }//end for J
        }//end for I
        //Retorna o numero de campos restante como 130, caso o jogador não tiver efetuado nenhuma ação.
        if( nMoves == 0){
            return(130);
        }else{// retorna o numero numero de campos totais menos a quantidade de bombas menos os campos selecionados
            return(115-cont);
        }//end gameStatus
    }//end metodo gameStatus

    /**
     * Metodo que irá reiniciar a partida
     * caso 2 seja pressionado, ou se a partida
     * tiver chegado ao fim.
     */
    public static void reset(){
        createField(); // Recria o campo
        nMoves = 0; // Retorna a chave de primeira ação para 0
        x = 0; y = 0;// Retorna o cursos para a posição inicial.
    }//end reset;

    /**
     * Metodo responsável pela movimentação do cursor "[!] "
     * e seleção dos campos, também, pela situação da partida.
     */
    static void cursor()throws Exception{
        Scanner leia = new Scanner( System.in );
        String str;
        char c;

        while(true){ //O enunciado da questão não solicita um momento para que o jogo seja finalizado, portanto o loop é infinito.
            c = ' '; //inicializado da variável, para que ao reiniciar a partida a chave de movimento esteja vazia.
            
            while( c != 'v'){ //flag para o fim da partida.
                
                do{
                    str = leia.nextLine();    
                }while(str.length( ) < 1); //Enquanto houver ausência de qualquer valor repetir a leitura

                //Adicionar o primeiro caractere para seleção
                c = str.charAt(0); 

                //O campo selecionado será alterado a seguir, apenas um par de colchetes deve existir
                campo[y][x].selected = false;
            // O jogador tem a opção de se movimentar até surgir do lado oposto do campo.
            switch (c) {
                case 'w': //Movimentar para cima
                    if( y == 0){
                        y = campo.length-1;
                    }else{
                        y--;
                    }
                break;

                case 's': //Movimentar para baixo
                    if( y == campo.length-1 ){
                        y = 0;
                    }else{
                        y++;
                    }
                break;

                case 'a': //Movimentar para a esquerda
                    if( x == 0){
                        x = campo[0].length-1;
                    }else{
                        x--;
                    }
                break;

                case 'd': //Movimentar para a direita
                    if( x == campo[0].length-1){
                        x = 0;
                    }else{
                        x++;
                    }
                break;

                case '1': // Mostrar o campo e pausar a execução por 2 segundos
                    t1.alterScreen( 8, 25, showField( ) );
                    Thread.sleep(2000);
                break;
                
                case '2': // Reiniciar a partida
                    reset( );
                break;
                
                case ' ': // Selecionar o campo
                    campo[y][x].wasSelected = true;

                    if(nMoves == 0){      //Se for a primeira ação do jogador
                        isAvailable( );   //Certificar que não haverá bombas ou numeros
                        createBomb( );    //Criar as bombas
                        createNumbers( ); //Criar os numeros
                        setBlanks( );     //Definir filas de campos em branco junto com seus adjacentes
                        showAround( );    //O primeiro campo sempre estará em branco, portanto, mostrar seus adjacentes.                   
                        nMoves++;         //Definir que o jogar ja fez a jogada inicial
                    }
                    else
                    {// Se não for a primeria ação do jogador.
                        if( campo[y][x].isBomb ){ //Se for uma bomba...
                            //Jogar na tela o campo atual.
                            t1.alterScreen( 8, 25, convertToString() ); 
                            System.out.println("\t\t\t\t     Perdeu"); //Informar o fim da partida
                            c = 'v'; 
                            Thread.sleep(2000); //Interromper a execução por 2 segundos
                            reset(); 
                        }
                        //se todos os campos foram liberados
                        else if( gameStatus() == 0 ){
                            System.out.println("\t\t\t\t     Venceu"); // Anunciar a vitória do jogador
                            c = 'v'; 
                            Thread.sleep(2000);
                            reset();
                        }if(campo[y][x].number == 0 && c != 'v'){
                            showAround(); //Se o campo for vazio, buscar por adjacentes também vazios
                        }
                    }
                break;

                default:
                    break;
            }//end switch

            if( c != 'v') //Se a partida não acabou
                campo[y][x].selected = true; //Definir como selecionado
                t1.alterScreen( 8, 25, convertToString() ); //Atualizar na tela
                //Informar posição do cursor e quantos campos faltam para o fim da partida.
                System.out.println("y: "+y+" x: "+x+" Remains: "+ gameStatus());
            }//end while
        }//end loop infinito
    }//end metodo cursor
    
    public static void main(String[] args)throws Exception{
        //Campo Inicial
        createField();
        //Tela Inicial
        t1.alterScreen( 8, 25, convertToString() );
        cursor( ); //Inicializar o jogo
    }// end main
}// end class