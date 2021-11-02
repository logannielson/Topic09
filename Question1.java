import javax.sound.midi.Soundbank;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Question1 {

    public static int[][] bfs(int[][] board, int knightX, int knightY) {
        Queue<int[]> search = new LinkedList<>();
        board[knightX][knightY] = -1;
        search.add(new int[] {knightX,knightY});

        int[] dr = {1,2,-1,2,1,-2,-1,-2}, dc = {2,1,2,-1,-2,1,-2,-1};
        while(!search.isEmpty()){
            int[] curr = search.remove();

            int r = curr[0];
            int c = curr[1];

            for(int i = 0; i < 8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if((nr >= 0) && (nr < 8) && (nc >= 0) && (nc < 8) && (board[nr][nc] == 0)) {
                    if(board[r][c] == -1) {
                        board[nr][nc] = board[r][c] + 2;
                    } else {
                        board[nr][nc] = board[r][c] + 1;
                    }
                    search.add(new int[] {nr,nc});
                }
            }
        }
        return board;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(System.out);

        String knight = in.nextLine();
        String pawn = in.nextLine();

        String knightLetter = knight.substring(0,1);
        int knightNumber = Integer.parseInt(knight.substring(1));
        int knightY = 0;
        int knightX = (knightNumber - 8)*-1;

        String pawnLetter = pawn.substring(0,1);
        int pawnNumber = Integer.parseInt(pawn.substring(1));
        int pawnY = 0;
        int pawnX = (pawnNumber - 8)*-1;

        if(knightLetter.equals("a")) {
            knightY = 0;
        } else if(knightLetter.equals("b")) {
            knightY = 1;
        } else if(knightLetter.equals("c")) {
            knightY = 2;
        } else if(knightLetter.equals("d")) {
            knightY = 3;
        } else if(knightLetter.equals("e")) {
            knightY = 4;
        } else if(knightLetter.equals("f")) {
            knightY = 5;
        } else if(knightLetter.equals("g")) {
            knightY = 6;
        } else if(knightLetter.equals("h")) {
            knightY = 7;
        }

        if(pawnLetter.equals("a")) {
            pawnY = 0;
        } else if(pawnLetter.equals("b")) {
            pawnY = 1;
        } else if(pawnLetter.equals("c")) {
            pawnY = 2;
        } else if(pawnLetter.equals("d")) {
            pawnY = 3;
        } else if(pawnLetter.equals("e")) {
            pawnY = 4;
        } else if(pawnLetter.equals("f")) {
            pawnY = 5;
        } else if(pawnLetter.equals("g")) {
            pawnY = 6;
        } else if(pawnLetter.equals("h")) {
            pawnY = 7;
        }

        int[][] board = new int[8][8];
        board[knightX][knightY] = 1;

        int[][] ans = bfs(board, knightX, knightY);

        int realans = ans[pawnX][pawnY];

        out.println(realans);

        out.close();
    }

    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        private int MAX_LINE_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer, lineBuf;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            lineBuf = new byte[MAX_LINE_SIZE];
            bufferPointer = bytesRead = 0;
        }
        public FastReader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
        public boolean hasNext() throws IOException {
            byte c;
            while ((c = read()) != -1) {
                if (c > ' ') {        // Find first byte bigger than ' '
                    bufferPointer--;
                    return true;
                }
            }
            return false;
        }
        // return the next line that contains at least one character > ' '
        public String nextLine() throws IOException {
            int ctr = 0;
            byte c;
            boolean empty = true;
            while ((c = read()) != -1) {
                if (c == '\r')        continue;     // ignore '\r'
                if (c == '\n') {
                    if (empty)  { ctr = 0;   continue;  } // read only spaces etc. until \n
                    else        break;
                }
                if (ctr == MAX_LINE_SIZE) {
                    MAX_LINE_SIZE *= 2;
                    lineBuf = Arrays.copyOf(lineBuf, MAX_LINE_SIZE);
                }
                lineBuf[ctr++] = c;
                if (c > ' ')  empty = false;
            }
            return new String(lineBuf, 0, ctr);
        }
        public String next() throws IOException {
            int ctr = 0;
            byte c = read();
            while (c <= ' ')      c = read();
            while (c > ' ') {
                if (ctr == MAX_LINE_SIZE) {
                    MAX_LINE_SIZE *= 2;
                    lineBuf = Arrays.copyOf(lineBuf, MAX_LINE_SIZE);
                }
                lineBuf[ctr++] = c;
                c = read();
            }
            return new String(lineBuf, 0, ctr);
        }
        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')   c = read();
            boolean neg = (c == '-');
            if (neg)           c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)           return -ret;
            return ret;
        }
        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')    c = read();
            boolean neg = (c == '-');
            if (neg)            c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)            return -ret;
            return ret;
        }
        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)      c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
            if (neg)     return -ret;
            return ret;
        }
        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        }
        private byte read() throws IOException {
            if (bufferPointer == bytesRead)     fillBuffer();
            if (bytesRead <= 0)  return -1;  // No data
            return buffer[bufferPointer++];
        }
        public void close() throws IOException {
            if (din == null)        return;
            din.close();
        }
    }
}