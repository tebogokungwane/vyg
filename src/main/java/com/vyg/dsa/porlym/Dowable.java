package com.vyg.dsa.porlym;

public interface Dowable {

   default void dowable(){
        System.out.println("Dowable");
    }

     static void doingSOmething(){
         System.out.println();
     }


     private void doplicate(){
       System.out.println("Doplicate");
     }
}
