package org.example.utils;


import java.util.List;
import java.util.Random;

//zobrist hashing to assist with identifying 'ko' situation in board state
public class Zobrist {
    //board position [][] - state [] empty/black/white
    private final long[][][] zobristTable;

    public Zobrist() {
        zobristTable = new long[19][19][3];
        Random rand = new Random();

        for(int i =0; i < 19; i++) {
            for(int j = 0; j < 19; j++) {
                for(int k = 0; k < 3; k++){
                    zobristTable[i][j][k] = rand.nextLong();
                }
            }
        }
    }


//    public long updateHash(Long oldHash, int x, int y, int oldState, int newState) {
//        //XOR out the old state and xor new state
//        oldHash ^= zobristTable[x][y][oldState];
//        return oldHash ^= zobristTable[x][y][newState];
//
//    }

    public long generateHash(List<List<Integer>> board) {
        long hash = 0L;

        for(int i = 0; i < 19; i ++){
            for(int j = 0; j < 19; j++){
                int stone = board.get(i).get(j);
                hash ^= zobristTable[i][j][stone];
            }
        }
        return hash;
    }
}
