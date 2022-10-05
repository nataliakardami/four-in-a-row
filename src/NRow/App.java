package NRow;

import NRow.Heuristics.SimpleHeuristic;
import NRow.Players.HumanPlayer;
import NRow.Players.MinMaxPlayer;
import NRow.Players.PlayerController;


public class App {

    public static void main(String[] args) throws Exception {
        Board b = new Board(7,6);

//        System.out.println(Arrays.deepToString(b.getBoardState()));

        int gameN = 4;
        int boardWidth = 7;
        int boardHeight = 6;
        int depth = 0;

        PlayerController[] players = getMinMaxPlayer(gameN);

        Game game = new Game(gameN, boardWidth, boardHeight, players);
        game.startGame();
        //Node.make_tree(b, game, depth);




        /*
        int gameN = 4;
        int boardWidth = 7;
        int boardHeight = 6;

        PlayerController[] players = getPlayers(gameN);

        Game game = new Game(gameN, boardWidth, boardHeight, players);
        game.startGame();

         */


    }

    /**
     * Determine the players for the game
     * @param n
     * @return an array of size 2 with two Playercontrollers
     */
    private static PlayerController[] getPlayers(int n) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(n);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(n);

        PlayerController human = new HumanPlayer(1, n, heuristic1);
        PlayerController human2 = new HumanPlayer(2, n, heuristic2);

        //TODO: Implement other PlayerControllers (MinMax, AlphaBeta)

        PlayerController[] players = { human, human2 };

        return players;
    }

    private static PlayerController[] getMinMaxPlayer(int n) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(n);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(n);

        PlayerController human = new HumanPlayer(1, n, heuristic1);
        PlayerController minmax = new MinMaxPlayer(2, n,4,heuristic1);

        PlayerController[] players = { human, minmax };

        return players;
    }

    private static PlayerController[] getAbPlayer(int n){
        SimpleHeuristic heuristic = new SimpleHeuristic(n);

        PlayerController human = new HumanPlayer(1, n, heuristic);
        PlayerController minmax = new MinMaxPlayer(2, n,4,heuristic);
        PlayerController[] players = { human, minmax };

        return players;
    }
}
