package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Node;

import java.util.ArrayList;
import java.util.Formattable;

import static NRow.Node.getChildren;
import static NRow.Node.isTerminal;
import static java.lang.Math.max;
import static java.lang.Math.min;

/*
https://tutorialedge.net/artificial-intelligence/min-max-algorithm-in-java/
https://www.e4developer.com/2018/09/23/implementing-minimax-algorithm-in-java/
https://medium.com/analytics-vidhya/artificial-intelligence-at-play-connect-four-minimax-algorithm-explained-3b5fc32e4a4f
 */

public class MinMaxPlayer extends PlayerController {
    private final int depth;

    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        //You can add functionality which runs when the player is first created (before the game starts)
    }

    /**
   * Implement this method yourself!
   * @param board the current board
   * @return column integer the player chose
   */
    @Override
    public int makeMove(Board board) {
        // TODO: implement minmax player!
        // HINT: use the functions on the 'board' object to produce a new board given a specific move
        // HINT: use the functions on the 'heuristic' object to produce evaluations for the different board states!

        /*
        // Example: 
        int maxValue = Integer.MIN_VALUE;
        int maxMove = 0;
        for(int i = 0; i < board.width; i++) { //for each of the possible moves
            if(board.isValid(i)) { //if the move is valid
                Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                int value = heuristic.evaluateBoard(playerId, newBoard); //evaluate that new board to get a heuristic value from it
                if(value > maxValue) {
                    maxMove = i;
                }
            }
        }
        // This returns the same as:
        heuristic.getBestAction(playerId, board); // Very useful helper function!


        This is obviously not enough (this is depth 1)
        Your assignment is to create a data structure (tree) to store the gameboards such that you can evaluate a higher depths.
        Then, use the minmax algorithm to search through this tree to find the best move/action to take!
        */
        //my code
        Node node = new Node(board);
        int depth = 4; // looks 4 moves ahead
       // need to figure out exactly what maxmove returns

        return minmax2(depth,board,true);
    }


    public int minmax(Node node, int depth, Board board, boolean maxPlayer) {

        Node tree = new Node(board); //this is our database


        int value = 0;
        int bestMove = 0;

        if (depth == 0 || isTerminal(node)) {
            return heuristic.evaluateBoard(playerId, board);
        }

        if (maxPlayer) {
            value = -1000;

            ArrayList<Node> children = getChildren(node); //getchildren will evaluate the next possible board states
            Node.addChildren(children,tree); // TODO i want this to work lke this: tree.add(children) //this is how we store states
            for (Node child : children) {

                value = max(value, minmax(child, depth-1, board, false));

            }

        }
        else {
            value = 100000;
            ArrayList<Node> children = getChildren(node);
            // mayb the eval should be here
            for (Node child : children) {
                value = min(value, minmax(child, depth-1, board, true));
            }


        }
        return value;

    }


    /**
     * Minmax but i evaluate states here

     * @param depth
     * @param board
     * @param maxPlayer
     * @return
     */
    public int minmax2(int depth, Board board, boolean maxPlayer) {

        Node tree = new Node(board); //this is our database



        int maxMove = 0;
        int value = 0;

        if (depth == 0 || board.isTerminal()) {
            //return heuristic.evaluateBoard(playerId, board);
            return  heuristic.getBestAction(playerId, board);
        }

        if (maxPlayer) {
            int maxValue = -10000;
            value = maxValue;

            for(int i = 0; i < board.width; i++) { //for each of the possible moves
                if(board.isValid(i)) { //if the move is valid
                    Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                    Node.addChild(tree,newBoard);

                    if(value > maxValue) {
                        maxMove = i;
                    }
                    value = max(value, minmax2(depth-1, board, true));
                }
            }

        }
        else {
            int minValue = 100000;
            value=minValue;

            for(int i = 0; i < board.width; i++) { //for each of the possible moves
                if(board.isValid(i)) { //if the move is valid
                    Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                    value = min(value, minmax2(depth-1, board, false));
                    if(value < minValue) {
                        maxMove = i;
                    }
                }
            }

        }
        return maxMove;

    }


    public int getDepth() {
        return depth;
    }
}