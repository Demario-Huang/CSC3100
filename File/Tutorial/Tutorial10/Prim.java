package tutorial10;
//package bfs_prim;

public class Prim {

  int n;
  int [][] adjMatrix;
  public void addEdge(int v1, int v2, int weight){

      adjMatrix[v1][v2] = weight;
      adjMatrix[v2][v1] = weight;
  }
  public void initGraph(){
      n = 7; // the number of vertices
      adjMatrix = new int[n][n]; //adjacent matrix
      for(int i = 0; i < n; i++){
          for(int j = 0; j < n; j++)
              adjMatrix[i][j] = 999;
      }
      //add edges
      addEdge(0,1,4);
      addEdge(0,7,8);
      addEdge(1,2,8);
      addEdge(1,7,11);
      addEdge(2,3,7);
      addEdge(2,5,4);
      addEdge(2,8,2);
      addEdge(3,4,9);
      addEdge(3,5,14);
      addEdge(4,5,10);
      addEdge(5,6,2);
      addEdge(6,7,1);
      addEdge(6,8,6);
      addEdge(7,8,7);
     
  }

  public void prim(){

      int[] key = new int[n]; //weights of crossing edges
      int[] preNode= new int[n];//previous nodes
      boolean[] Va = new boolean[n]; //chosen flags, true for chosen, false for not chosen
      Va[0] = true;

      for(int i=1;i<n;i++){
          key[i] = adjMatrix[0][i];   //initialized weights of cross edges
          preNode[i] = 0; //The first node is selected
          Va[i] = false;
      }


      for(int i=1;i<n;i++){ // find the next n-1 edges
          int minWeight = 999;// use a sufficiently large number to represent infinite
          int minNode = 0;
          //find the light crossing edge
          for(int j=1;j<n;j++){
              if(!Va[j] && key[j]<minWeight){
                  minWeight = key[j];
                  minNode = j;
              }
          }

          Va[minNode] = true;
          System.out.println(preNode[minNode]+ "---" +minNode + ", weights:" + minWeight);

          //update new weights of cross edges
          for(int j=1;j<n;j++){
              if(!Va[j] && key[j]>adjMatrix[minNode][j]){
                  key[j] = adjMatrix[minNode][j];
                  preNode[j] = minNode;
              }
          }
      }
  }

  public static void main(String[] args) {
      Prim prim = new Prim();
      prim.initGraph();
      prim.prim();
  }
}
