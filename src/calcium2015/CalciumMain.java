/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcium2015;

/**
 *
 * @author Владимир
 */
public class CalciumMain {
    public static void main(String[] args){
        int K = 0;
        int[] A = new int[9];
        int[] B = new int[9];
          A[0] = 5;    B[0] = 1;
  A[1] = 1 ;   B[1] = 0;
  A[2] = 0;    B[2] = 7;
  A[3] = 2;   B[3] = 4;
  A[4] = 7 ;   B[4] = 2;
  A[5] = 0;    B[5] = 6;
  A[6] = 6;    B[6] = 8;
  A[7] = 6  ;  B[7] = 3;
  A[8] = 1 ;   B[8] = 9;
        /*int[] A = new int[6];
        int[] B = new int[6];
         A[0] = 0;    B[0] = 1;
  A[1] = 1 ;   B[1] = 2;
  A[2] = 2 ;   B[2] = 3;
   A[3] = 3;    B[3] = 4;
  A[4] = 4 ;   B[4] = 5;
  A[5] = 5 ;   B[5] = 6;*/
    /*int[] A = new int[1];
        int[] B = new int[1];   
        A[0] = 0;
        B[0] = 1;*/
  Solution solution = new Solution();
  int test = solution.solution(A, B, K);
  System.out.print(test);
    }
}
