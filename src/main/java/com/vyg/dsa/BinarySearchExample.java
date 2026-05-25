package com.vyg.dsa;

public class BinarySearchExample {

    public static int binarySearch(int [] number, int target){
        int left = 0;
        int right = number.length-1;

        while(left <= right){
            int middle = left + (right-left)/2;
            int guess = number[middle];

            if(guess == target){
                return middle;
            }
            else if(guess < target){
                left = middle+1;
            }else{
                right = middle-1;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        int [] nums = new int[]{2,4,6,8,10,12,14,16,18,20};
        System.out.println(binarySearch(nums,10));
        System.out.println(binarySearch(nums,5));

    }
}
