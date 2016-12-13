
import java.io.*;
import java.util.*;

public class Board {
    private int[][] matrix; // blocks
    private int N; // deimension
    private int posX, posY; // 0' position 
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = blocks[i][j];
                if (matrix[i][j] == 0) {
                    posX = i;
                    posY = j;
                }
            }
        }
    }
    
    // board dimension N
    public int dimension() {
        return N;
    } 
        
    // number of blocks out of place
    public int hamming() {
        int hammingDis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) continue;
                if (i*N+j+1 != matrix[i][j]) hammingDis++;
            }
        }
        return hammingDis;
    }    
        
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanDis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) continue;
                int x, y;
                if (matrix[i][j] % N == 0) {
                    x = matrix[i][j] / N - 1;
                    y = N - 1;
                } else {
                    x = matrix[i][j] / N;
                    y = matrix[i][j] % N - 1;
                }
                manhattanDis += Math.abs(i-x) + Math.abs(j-y);
            }
        }
        return manhattanDis;
    } 
            
    // is this board the goal board?
    public boolean isGoal() {
        if (posX != N-1 || posY != N-1) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) continue;
                if (i*N+j+1 != matrix[i][j]) return false;
            }
        }
        return true;
    }               
            
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int x = -1, y = -1;
        int[][] tmpBlock = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j < N-1 && matrix[i][j] != 0 && matrix[i][j+1] != 0) {
                    x = i;
                    y = j;
                }
                tmpBlock[i][j] = matrix[i][j];
            }
        }
        if (x == -1 && y == -1) throw new IllegalArgumentException();
        int t = tmpBlock[x][y];
        tmpBlock[x][y] = tmpBlock[x][y+1];
        tmpBlock[x][y+1] = t;
        return new Board(tmpBlock);
    }           
        
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        int sz = this.dimension();
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (this.matrix[i][j] != that.matrix[i][j])
                    return false;
            }
        }
        return true;
    }  
        
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();
        int[] dx = {0, 0, -1, 1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int x = posX + dx[i];
            int y = posY + dy[i];
            
            if (x < N && x >= 0 && y < N && y >= 0) {
                int tmp = matrix[posX][posY];
                matrix[posX][posY] = matrix[x][y];
                matrix[x][y] = tmp;
                queue.enqueue(new Board(matrix));
                tmp = matrix[posX][posY];
                matrix[posX][posY] = matrix[x][y];
                matrix[x][y] = tmp;
            }
        }
        return queue;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", matrix[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    } 
   
    public static void main(String[] args) {
    try{
        //Para leer el archivo con un Buffer
        String dirArchivo=args[0];
        File file = new File(dirArchivo);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        int[][] mat= new int[3][3];
        for(int j=0; j<3;j++){
            line = bufferedReader.readLine();
            String[] datos = line.split(" ");
                for(int i=0; i<3;i++){
                    mat[j][i]=Integer.parseInt(datos[i]);
                  }
        }
        //hamming
        //manhattan
        Board b = new Board(mat);
        Board c = b.twin().twin();
        StdOut.println(b.equals(c));
        StdOut.print(b.toString());
        for (Board it : b.neighbors()) {
            StdOut.print(it.toString());
            StdOut.println("hamming: " + it.hamming());
            StdOut.println("manhattan: " + it.manhattan());
        }

    }catch (Exception e) {
        throw new InputMismatchException("Invalid input format constructor");
    }
        
    }
    
}