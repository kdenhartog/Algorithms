/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj1;

import java.util.Scanner;

/**
 *
 * @author kyle
 */
public class modular {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a number: \n");
        int number = scan.nextInt();
        for(int i = 2; i < number / 2; i++){
            int result = number%i;
            if(result == 0){
                System.out.println("Number:" + number + "\n" + "i:" + i + "\n" + number / i);
                //break;
            }
            System.out.println(number + "mod" + i + "= " + result);
        }
    }
}
