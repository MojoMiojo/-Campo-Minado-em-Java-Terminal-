
/** 
 * Mozart Junio Alves de Sousa
 * Discente de Ciência da Computação
 * Pontifícia Universidade Católica de Minas Gerais
 * Campo Minado em Java
 * Estruturas; Matriz de objetos, Lista dinâmica
 * Classe terminal, essa classe contém as funções que simulam uma tela no terminak
 */

import java.io.IOException;
/**
 * Classe terminal
 */
class terminal{
   public static char matriz[][] = new char [25][80];  //Matriz que simula a tela 
  
   /**
    * Metodo responsável por limpar o terminal do 
    * sistema operacional em questão para que
    * a simulação de movimento do cursor
    * pelo campo se torne possível
    */
   public static void cls ( ) throws IOException, InterruptedException {
      if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
   }//end metodo cls

   /**
    * Metodo responsável por escrever no
    * terminal a matriz que simula uma tela
    */
   public void toScreen(  )throws Exception{
      cls( ); //Limpa o terminal antes de começar

      for(int i = 0; i < 25; i++){
         for( int j = 0; j < 80; j++){
            System.out.print(matriz[i][j]);
         }//end for j   
            System.out.println( ); 
      }//end for i
   }//end metodo toScreen

   /**
    * O alterScreen é o metodo que reescreve a matriz
    * de acordo com a necessidade do jogo, seja pela tecla 1
    * pressionado para mostrar todo o campo, ou pelas demais
    * e atualização da visualização e posição do colchete []
    * @param x posição no eixo X
    * @param y posição no eixo Y
    * @param insert matriz de caracteres interpretados a partir da matriz de objetos
    */
   public void alterScreen(int x, int y, char[][]insert )throws Exception{
      CampoMinado cm = new CampoMinado();
      int aux = y; //O valor de Y será alterado para que possamos simular os "trilhos" dos colchetes

      /*Vale lembrar que o tamanho da matriz de objetos não é o mesmo 
      *da matriz do terminal, sendo assim, é necessário termos mais
      *variaveis auxiliares para percorremos a matriz, em outra palavras
      a posição a se inserir não é a mesma da matriz de objetos;*/
      for( int i = 0; i < insert.length; i++, x++){
         for( int j = 0; j < insert[0].length; j++, y+=3){
            //Se o campo for selecionado, envolver em colchetes
            if( cm.campo[i][j].selected ){
               matriz[x][y] =  '[';
               matriz[x][y+1] = insert[i][j];
               matriz[x][y+2] = ']';
            }else{//Senão, deixar os espaços dos colchetes livres e escrever seu conteudo
               matriz[x][y] =  ' ';
               matriz[x][y+1] = insert[i][j];
               matriz[x][y+2] = ' ';
            }//end else
         }//end for j
         y = aux; //Retornar Y ao seu valor original
      }
      toScreen();
   }//end metodo alterScreen
}//end class terminal

/**
 * Obrigado pela oportunidade!!
 */