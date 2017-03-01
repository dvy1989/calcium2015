/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcium2015;

import java.util.Arrays;
import java.util.Collections;

/*
    A class, that contains a tree representation of city's map
*/
public class CityMap {
    // A collection of map nodes (intersections)
    private final MapNode[] _nodes;
    
    // This array is used to prevent loops
    // When a intersection is passed twice
    private final int[] _visitedNodes;
    
    // Maximal ID of intersection (expected, that there are no holes 
    // in intersections sequence
    private int _maxNodeId;
    
    // Number of algorithm runs (counted to escape loops)
    private int _runsCount;
    
    // Sizes of subtrees
    // Used to store longest pathes of subtrees, connected
    // to a particular intersection
    // Storag is persistant
    private final Integer[] _subTreeSizes;
    
    // Claculates diameters of subtrees
    // Recalculated at each iteraction
    private final int[] _diameters;   
    
    public CityMap(int nodesCount) {
        _nodes = new MapNode[nodesCount];
        _visitedNodes = new int[nodesCount];
        _subTreeSizes = new Integer[nodesCount];
        _diameters = new int[nodesCount];
        _maxNodeId = 0;
        _runsCount = 1;
    }
    
    // Adds an edge to the tree
    public void addEdge(int A, int B) {
        insertNode(A);
        insertNode(B);
        _nodes[A].addEdge(B);
        _nodes[B].addEdge(A);
        if (A > _maxNodeId){
            _maxNodeId = A;
        }
        if (B > _maxNodeId){
            _maxNodeId = B;
        }
    }
    
    // Inserts a node if not inserted yet
    private MapNode insertNode(int Id){
        if (_nodes[Id] == null){
            MapNode node = new MapNode();
            node.Id = Id;
            _nodes[Id] = node;
        }
        return _nodes[Id];
    }
    
    // K is a number of cameras
    public int claculatePath(int K){  
        // Path can not be longer, than 900
        // and in the same time can not be longer,
        // than number of intersections
        int maxPathSize = Math.min(900, _maxNodeId);
        int minPathSize = 0;
               
        int resultPath = maxPathSize;        
        int testPathSize;   
        
        // Now we test the tree with different path sizes
        // to find a suitable one        
        while (maxPathSize >= minPathSize){
            // A binary search used
            testPathSize = (minPathSize + maxPathSize) / 2;
            if (testWithPath(K, testPathSize)){
                // If path of given length is possible with less or equal number of cameras
                // check that shorter one is possible
                resultPath = Math.min(resultPath, testPathSize);                
                maxPathSize = testPathSize - 1;
            }
            else{
                // Less path is not possible. Increase the minimal path size
                minPathSize = testPathSize + 1;                 
            }
            // Next run
            _runsCount++;
        }         
        return resultPath;
    }   
    
    // Checks, whether this path length could be possible
    // with the given number of cameras
    private boolean testWithPath(int K, int testPathSize) {
        // Reset diameters (because probe path length changed) 
        for (int i = 0; i < _diameters.length; i++){
            _diameters[i] = -1;             
        }
        return camerasRequired(_nodes[0], testPathSize) <= K;
    }
    
    // Calculates required number of cameras
    // To provide this maximal possible length of path
    private int camerasRequired(MapNode node, int testPathSize) {
        // It is a leaf or loop - don't check
        if (node == null || _visitedNodes[node.Id] >= _runsCount){
            return 0;
        }
        // Node is checked
        _visitedNodes[node.Id]++;
        int numberOfCameras = 0;
        // Get number of cameras for subtrees
        for (int nodeId : node.Connections){
            numberOfCameras += camerasRequired(_nodes[nodeId], testPathSize);
        }
        
        // Calculate subtrees diameters
        int pathId = 0;
        for (int nodeId : node.Connections){
            if (_diameters[nodeId]!=-1){
                _subTreeSizes[pathId++] = _diameters[nodeId];                
            }
        }
        // Reverse it
        Arrays.sort(_subTreeSizes, 0, pathId, Collections.reverseOrder());
        
        int maxDiameter = -1;
        // If we have a situation, when this node is connected
        // to more, than one, subtrees
        for (int i = 0; i < pathId - 1; i++){
            // Path, that passes via this node, is too long
            if (_subTreeSizes[i] + _subTreeSizes[i + 1] + 2 > testPathSize){
                // Place the camera
                numberOfCameras++;
            }else{
                // All pathes are less, than tested. Just proceed 
                // but notice the longest path to this node
                maxDiameter = Math.max(maxDiameter, _subTreeSizes[i]);
                break;
            }
        }
        
        // If we have a single path, entering this node
        if (pathId >= 1){
            int index = pathId - 1;
            // Path is too long
            if (_subTreeSizes[index] + 1 > testPathSize){
                // Place the camera
                numberOfCameras++;
            }else{
                // The same as for previous case
                maxDiameter = Math.max(maxDiameter, _subTreeSizes[index]);                 
            }
        }
        
        // This is a leaf or we "cut" all pathes with cameras
        if (maxDiameter == -1){
            _diameters[node.Id] = 0;
        }
        else{
            // Otherwise next hop at this route is possible
            _diameters[node.Id] = maxDiameter + 1;
        }
        
        return numberOfCameras;
    }
}