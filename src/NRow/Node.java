package NRow;
// https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java

import java.util.ArrayList;


public class Node {

    private ArrayList<Node> children;
    private Board board;
    private Game game;
    private Node predecessor;
//    private PlayerController[] players;

    public static int Q = 0;


    public Node(Board new_board) {
        this.children = new ArrayList<>();
        this.board = new_board;
        this.predecessor = null;
    }

    public Node(Board new_board, Game game) {
        this.children = new ArrayList<>();
        this.board = new_board;
        this.predecessor = null;
        this.game = game;
    }

    public Node(Node other, Game game) {
        this.children = new ArrayList<>();
        this.board = other.board;
        this.predecessor = other.predecessor;
        this.game = game;
    }

//    public static Board getBoard(){
//        return new Board(board.getBoardState());
//    }

    public static boolean isTerminal(Node node){
        return node.children == null;
    }

    public static ArrayList<Node> getChildren(Node node){
        //TODO evaluate next board state?

        return node.children;
    }

    public static void addChildren(ArrayList<Node> descendants, Node node){
        node.children = descendants;
    }










    public static Node make_tree(Board board1, Game game, int depth) { // we may neeed th array with players fot the playerID
        System.out.println("Q: " + Q);
        Q++;

        Board copy = new Board(board1);

        Node node = new Node(copy);

        int id = 0; // playerID



        // maybe while a move is valid and until a game is over or game is won
        if (game.isOver() || depth>6) {
            System.out.println("Base Case");
            return node;

        } else {
            for (int i = 0; i < board1.width; i++) {
                if(board1.isValid(i)) {

                    copy.play(i, id); // prepare
                    System.out.println("depth: " + depth);
                    node.children.add(make_tree(copy.getNewBoard(i, (id + 1)), game, depth + 1)); // recurse
//                  (id+1) % players.length for the next player ?

                    copy = new Board(board1); // repair
                }

            }
        }
        return node;
    }


    public static void addChild(Node node, Board newBoard) {
        node.children.add(new Node(newBoard)); // todo why is this a boolean?
    }
}

