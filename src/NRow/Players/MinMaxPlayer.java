package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Node;

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
     * Fetches the best move from the minmax function
     *
     * @param board the current board
     * @return column integer the player chose
     */
    @Override
    public int makeMove(Board board) {

        //Node node = new Node(board);
        int depth = 6; // looks 6 moves ahead
        // need to figure out exactly what maxMove returns
        int best = minmax(depth, board, true)[0];
        System.out.println("best " + best);

        return best;
    }


    /**
     * Minmax implementation
     *
     * @param depth depth of search
     * @param board board state
     * @param maxPlayer is it the max player?
     * @return best move index, heuristic value
     */
    public int[] minmax(int depth, Board board, boolean maxPlayer) {

        Node tree = new Node(board); //this is our database

        Board copy = new Board(board);

        int bestMove = 0;
        //int value = 0;

        if (depth == 0 || board.isTerminal()) {
            //return heuristic.evaluateBoard(playerId, board);
            return new int[]{heuristic.getBestAction(playerId, board), heuristic.evaluateBoard(1,board)};
        }

        int value;
        if (maxPlayer) {
            value = -10000;

            for (int i = 0; i < board.width; i++) { //for each of the possible moves
                if (board.isValid(i)) { //if the move is valid
                    Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                    Node.addChild(tree, newBoard);

                    int newValue = max(value, minmax(depth - 1, newBoard, false)[1]);


                    if (value < newValue){
                        bestMove = i;
                        System.out.println("case1: max" + bestMove);

                        value = newValue;

                    }
                    copy = new Board(board);


                }
            }

        } else {
            value = 100000;

            for (int i = 0; i < board.width; i++) { //for each of the possible moves
                if (copy.isValid(i)) { //if the move is valid
                    Board newBoard = copy.getNewBoard(i, playerId); // Get a new board resulting from that move
                    Node.addChild(tree, newBoard);
                    int newValue = min(value, minmax(depth - 1, newBoard, true)[1]);

                    if (value > newValue){
                        bestMove = i;
                        System.out.println("case2: min " + bestMove);
                        value = newValue;
                    }

                    copy = new Board(board);


                }

            }
            System.out.println("bestMove: " + bestMove);
            System.out.println("--------------------------------------------------------------------------------");
        }
        return new int[] {bestMove,value};


    }

    public int getDepth() {
        return depth;
    }
}
