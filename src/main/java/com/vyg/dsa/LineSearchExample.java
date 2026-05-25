package com.vyg.dsa;

public class LineSearchExample {

    public static void main(String[] args) {

        int [] shoes  = {10,20,30,40,50 ,60, 70, 80, 90, 100};
        int myShoes = 80;

        for(int iShoes:shoes){

            if(iShoes==myShoes){
                System.out.println(myShoes + " found");
                break;
            }
            System.out.println(iShoes);

        }
    }
}
