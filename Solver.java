public class Solver {
    
    private BoardNode targetBoardNode; // record targetBoardNode
    
    private class BoardNode implements Comparable<BoardNode> {
        private Board item;
        private BoardNode prev;
        private int move;
        private boolean isTwin;
        
        // compare by priority
        public int compareTo(BoardNode that) {
            if (that == null) 
                throw new NullPointerException("Input argument is null");
            int thisPriority = this.move + this.item.manhattan();
            int thatPriority = that.move + that.item.manhattan();
            if (thisPriority < thatPriority)
                return -1;
            else if (thisPriority == thatPriority)
                return 0;
            else
                return 1;
        }
    }
    
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        targetBoardNode = null;
        // priority queue maintain the minimum elements 
        MinPQ<BoardNode> minpq = new MinPQ<BoardNode>();
        // initial boardnode
        BoardNode bn = new BoardNode();
        bn.item = initial;
        bn.prev = null;
        bn.move = 0;
        bn.isTwin = false;
        minpq.insert(bn);
        // initial twin boardnode
        BoardNode twinbn = new BoardNode();
        twinbn.item = initial.twin();
        twinbn.prev = null;
        twinbn.move = 0;
        twinbn.isTwin = true;
        minpq.insert(twinbn);
        
        while (!minpq.isEmpty()) {
            BoardNode curbn = minpq.delMin();
            if (curbn.item.isGoal()) {
                if (curbn.isTwin) targetBoardNode = null;
                else targetBoardNode = curbn;
                break;
            }
            
            for (Board it : curbn.item.neighbors()) {
                if (curbn.prev == null || !curbn.prev.item.equals(it)) {
                    bn = new BoardNode();
                    bn.item = it;
                    bn.prev = curbn;
                    bn.move = curbn.move+1;
                    if (curbn.isTwin)
                        bn.isTwin = true;
                    else
                        bn.isTwin = false;
                    minpq.insert(bn);
                }
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return targetBoardNode != null;
    }       
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable())
            return targetBoardNode.move;
        else 
            return -1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        BoardNode tmpbn = targetBoardNode;
        while (tmpbn != null) {
            stack.push(tmpbn.item);
            tmpbn = tmpbn.prev;
        }
        if (stack.isEmpty()) 
            return null;
        else 
            return stack;
    }      
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            StdOut.println("Estados=" + initial.hamming());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}