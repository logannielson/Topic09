import javax.sound.midi.Soundbank;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Question2 {

    public static int bfs(int[][] traps, int numTraps) {
        int[][] grid = new int[50][50];
        Queue<int[]> search = new LinkedList<>();
        search.add(new int[] {0,0});
        int ans = -1;
        int[][] trapGrid = new int[50][50];
        for(int z = 0; z < numTraps; z++) {
            int time = traps[z][0];
            int xCord1 = traps[z][1];
            int yCord1 = traps[z][1];
            int xCord2 = traps[z][3];
            int yCord2 = traps[z][3];
            for(int i = yCord1; i <= yCord2; i++) {
                for(int j = xCord1; j <= xCord2; j++) {
                    trapGrid[i][j] = -1*time;
                }
            }
        }

        int[] dr = {1,0,-1,0}, dc = {0,-1,0,1};
        while(!search.isEmpty()){
            int[] curr = search.remove();

            int r = curr[0];
            int c = curr[1];

            for(int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if((nr >= 0) && (nr < 50) && (nc >= 0) && (nc < 50) && (grid[nr][nc] == 0)) {
                    if((trapGrid[nr][nc]) == 0) {
                        grid[nr][nc] = grid[r][c] + 1;
                        search.add(new int[] {nr,nc});
                    } else if(-1*trapGrid[nr][nc] >= grid[nr][nc] + 1){
                        grid[nr][nc] = grid[r][c] + 1;
                        search.add(new int[] {nr,nc});
                    }
                    grid[nr][nc] = grid[r][c] + 1;
                    search.add(new int[] {nr,nc});
                }
            }
        }
        ans = grid[49][49];
        if(ans == 0) {
            ans = -1;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(System.out);

        int numTraps = in.nextInt();
        int[][] traps = new int[numTraps][5];

        for(int i = 0; i < numTraps; i++) {
            String line = in.nextLine();
            String[] data = line.split(" ");
            traps[i][0] = Integer.parseInt(data[0]);
            traps[i][1] = Integer.parseInt(data[1]);
            traps[i][2] = Integer.parseInt(data[2]);
            traps[i][3] = Integer.parseInt(data[3]);
            traps[i][4] = Integer.parseInt(data[4]);
        }

        int ans = bfs(traps, numTraps);

        out.println(ans);

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
