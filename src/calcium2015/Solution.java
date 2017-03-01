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
public class Solution {
    public int solution(int[] A, int[] B, int K){
        int edgesCount = A.length;
        // Hint: if we have enough kameras, it is meaningless to check
        if (K >= edgesCount){
            return 0;
        }
        CityMap cityMap = new CityMap(edgesCount + 1);
        // Convert to a tree representation
        for (int i = 0; i < edgesCount ; i++){
            cityMap.addEdge(A[i], B[i]);
        }
        return cityMap.claculatePath(K);         
    }
}
