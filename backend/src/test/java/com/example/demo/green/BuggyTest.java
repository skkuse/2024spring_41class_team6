package com.example.demo.green;

import java.util.ArrayList;
import java.util.Random;

public class BuggyTest {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        arr.add("d");
        arr.add("e");
        arr.add("f");
        arr.add("g");

        boolean cond1 = checkCond1();
        boolean cond2 = checkCond2();
        boolean cond3 = checkCond3();

        if(cond1) {
            if(cond2) {
                if(cond3) {
                    System.out.println("Triple Nested Loop");
                }
            }
        }

        if(cond1) {
            if (cond2) {
                System.out.println("Double Nested Loop");
            }
        }

        if(cond1) {
            if(cond2) {
                if(cond3) {
                    System.out.println("Triple Nested Loop");
                }
            }
        }

        for(Integer i=0; i<arr.size(); i++) {
            Random r = new Random();
            System.out.println("Hello");
        }
    }

    public static boolean checkCond1() {
        int sum = 0;
        for(int i=0; i<10000; i++) {
            sum += i;
        }
        return sum > 50000;
    }

    public static boolean checkCond2() {
        int sum = 0;
        for(int i=0; i<10000; i++) {
            sum *= i;
        }
        return sum > 50000;
    }

    public static boolean checkCond3() {
        int sum = 0;
        for(int i=0; i<10000; i++) {
            sum *= i;
        }
        return sum > 50000;
    }
}
