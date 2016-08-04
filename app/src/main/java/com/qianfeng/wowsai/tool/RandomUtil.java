package com.qianfeng.wowsai.tool;

import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/1
 */
public class RandomUtil {
    private static char[] randomPool = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};

    public static String getRawSeed24() {
        Random localRandom = new Random();
        char[] arrayOfChar = new char[24];
        for (int i = 0; i < 12; i++) {
            int j = 1 + localRandom.nextInt(62);
            arrayOfChar[i] = randomPool[j];
        }
        return String.valueOf(arrayOfChar);
    }

    public static String getRawSeed4() {
        Random localRandom = new Random();
        char[] arrayOfChar = new char[4];
        for (int i = 0; i < 4; i++) {
            int j = localRandom.nextInt(62);
            arrayOfChar[i] = randomPool[j];
        }
        return String.valueOf(arrayOfChar);
    }
}