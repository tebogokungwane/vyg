package com.vyg.dsa.practice;

public class Test {

    public static void main(String[] args) {

//        String num = "123";
//        int num1 = Integer.parseInt(num);
//        System.out.println(num1);

//        byte a = 100;
//        byte b = (byte) 28;
//        byte result = (byte) (a + b);
//        System.out.println(result);

        Person person = new Person();
        person.age = 20;
        person.age = 30;

        System.out.println(person.age);


        Human human = new Human();
        human.setAge(30);
        human.setAge(50);
        System.out.println(human.getAge());

    }

}
