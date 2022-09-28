package NRow.Heuristics;

import NRow.Board;
import NRow.Game;

import java.util.HashSet;

public class SimpleHeuristic extends Heuristic {

    public SimpleHeuristic(int gameN) {
        super(gameN);
    }

    @Override
    protected String name() {
        return "Simple";
    }

    /**
     * Determine utility of a board state
     */
    @Override
    protected int evaluate(int player, Board board) {
        int[][] boardState = board.getBoardState();
        int winning = Game.winning(boardState, this.gameN);
        if (winning == player) {
            return Integer.MAX_VALUE;
        } else if (winning != 0) {
            return Integer.MIN_VALUE;
        }

        /*
         * If not winning or losing, return highest number of claimed squares in a row
         */
        int maxInRow = 0;
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] == player) {
                    maxInRow = Math.max(maxInRow, 1);
                    for (int x = 1; x < boardState.length - i; x++) {
                        if (boardState[i + x][j] == player) {
                            maxInRow = Math.max(maxInRow, x + 1);
                        } else {
                            break;
                        }
                    }
                    for (int y = 1; y < boardState[0].length - j; y++) {
                        if (boardState[i][j + y] == player) {
                            maxInRow = Math.max(maxInRow, y + 1);
                        } else {
                            break;
                        }
                    }
                    for (int d = 1; d < Math.min(boardState.length - i, boardState[0].length - j); d++) {
                        if (boardState[i + d][j + d] == player) {
                            maxInRow = Math.max(maxInRow, d + 1);
                        } else {
                            break;
                        }
                    }
                    for (int a = 1; a < Math.min(boardState.length - i, j); a++) {
                        if (boardState[i + a][j - a] == player) {
                            maxInRow = Math.max(maxInRow, a + 1);
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return maxInRow;
    }

//    @Override
//    protected HashSet make_tree() {
//        parent = parent_node
//        depth = 0
//        while depth < 10 {
//            children[] = make_children(parent)
//            for i in range 7:
//            tree[depth][i] = make_children(children[i])
//            depth ++
//        }
//
//        // return
//        return null;
//
//    }



    //TODO
    // def Board make_children(current state) {
    //      7 columns
    //  return 7 children nodes
    // }

    // TODO
    //  def Board array state_trees(parent_node) {
    //      tree[0][0] = parent node LINKED LIST
    //      parent = parent_node
    //
    //      for i in range limit
    //         for j in range 7 ?
    //              tree[i][j] = make_children(parent)[j]
    //              parent = tree[i][j] # first iteration 7 children which become next parent
    /*


     Tree{
     parent = parent_node
     depth = 0
     while depth < 10 {
         children[] = make_children(parent)
            for i in range 7:
               tree[depth][i] = make_children(children[i])
            depth ++
          }


          return tree
     }

     *
     */
    //


}
