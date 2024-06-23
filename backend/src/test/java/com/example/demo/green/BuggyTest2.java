package com.example.demo.green;

public class BuggyTest2 {
    public static void main(String[] args) {
        String temp = "";

        for(int i = 0; i < 10000; i++) {
            temp += "aadf";
            temp += "a";
            temp += "aasefd";
            temp += "aadfadf";
            temp += "a";
        }
        System.out.println(temp);

        temp += "hello";
        System.out.println(temp);
    }
}
