package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Node;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AbPlayer extends PlayerController{
    /**
     * Create AB player
     *
     * @param playerId  can take values 1 or 2 (0 = empty)
     * @param gameN     N in a row required to win
     * @param heuristic the heuristic the player should use
     */
    public AbPlayer(int playerId, int gameN, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
    }

    /**
     * Returns the move for the ab player
     * @param board the current board
     * @return the move index
     */
    @Override
    public int makeMove(Board board) {
        //Node node = new Node(board);
        int depth = 6; // looks 6 moves ahead

        int best = abPruning(depth,board,true)[0];
        System.out.println("best " + best);

        return best;
    }

    /**
     * AB pruning method
     *
     * @param depth depth of search
     * @param board board state
     * @param maxPlayer is it the max player?
     * @return best move index, heuristic value
     */
    public int[] abPruning(int depth, Board board, boolean maxPlayer) {

        Node tree = new Node(board); //this is our database


        int maxMove = 0;
        int value = 0;
        int a = -10000000;
        int b = 10000000;

        if (depth == 0 || board.isTerminal()) {
            //return heuristic.evaluateBoard(playerId, board);
            return new int[]{heuristic.getBestAction(playerId, board), value};
        }

        if (maxPlayer) {
            int maxValue = -10000;
            value = maxValue;

            for (int i = 0; i < board.width; i++) { //for each of the possible moves
                if (board.isValid(i)) { //if the move is valid
                    Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                    Node.addChild(tree, newBoard);

                    value = max(value, abPruning(depth - 1, board, true)[1]);

                    if (value > maxValue) {
                        maxMove = i;
                    }
                    a = max(a,value);
                    if (a >= b){
                        break;
                    }

                }
            }
            return new int[]{maxMove, value};


        }

        else {
            int minValue = 100000;
            value = minValue;

            for (int i = 0; i < board.width; i++) { //for each of the possible moves
                if (board.isValid(i)) { //if the move is valid
                    //Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move

                    value = min(value, abPruning(depth - 1, board, false)[1]);

                    if (value < minValue) {
                        maxMove = i;
                    }
                    b = min(b,value);
                    if (a >= b){
                        break;
                    }

                }
            }
            return new int[]{maxMove, value};
        }

    }
}
