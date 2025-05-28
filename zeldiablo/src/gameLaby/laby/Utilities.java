package gameLaby.laby;

import java.util.ArrayList;

public class Utilities {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static boolean areTwoCoordinatesEqual(int[] c1, int[] c2){
        return c1[0] == c2[0] && c1[1] == c2[1];
    }

    public static boolean isThisCoordinateInArrayOfCoordinates(ArrayList<int[]> array, int[] coo){
        for (int i = 0; i < array.size(); i++){
            if(areTwoCoordinatesEqual(array.get(i), coo)) return true;
        }
        return false;
    }
}
