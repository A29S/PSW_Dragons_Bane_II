package dkeep.cli;

import dkeep.logic.*;

import java.util.Scanner;

public class GameCycle {
    private GameState game;


    public GameCycle(){

        //this.game=new GameState();

    }
    static char userMoveInput(){

        Scanner scan=new Scanner(System.in);

        System.out.print("Move:");

        char move;

        do {

            move=scan.next().charAt(0);

            if (move=='w' || move=='a' || move=='s' || move=='d')
                break;

            System.out.println("Error!");

        }while(true);


        return move;

    }

    public char newGame(){
        Scanner scan=new Scanner(System.in);

        System.out.print("Do You want to play again(Y or N):");
        char aux=' ';
        do {

            aux=scan.next().charAt(0);

            if (aux=='Y' || aux=='N')
                break;

            System.out.println("Error!");

        }while(true);

        return aux;
    }

    public int dragonNumber(){
        Scanner scan=new Scanner(System.in);

        System.out.print("How many dragons do you want:");
        int aux;
        do {

            aux=scan.nextInt();

            if (aux<=9)
                break;

            System.out.println("Error!");

        }while(true);

        return aux;
    }

    public void loop(){

        char move;

        int dragons=dragonNumber();
        GameState game=new GameState(dragons);

        do{

            game.DisplayMap();

            move=userMoveInput();

            game.HeroMovement(move);

            game.StateCheck();

            if(game.getlose()==true){
                System.out.println(game.getlose());
                game.DisplayMap();
                char aux=newGame();
                if (aux=='N')
                    break;
                dragons=dragonNumber();
                game=new GameState(dragons);
                continue;
            }

            else if(game.getwin()==true){
                System.out.println(game.getwin());
                game.DisplayMap();
                //new game
                char aux=newGame();
                if (aux=='N')
                    break;
                dragons=dragonNumber();
                game=new GameState(dragons);
                continue;
            }

            game.NonControlabelMovement();

            game.StateCheck();

            if(game.getlose()==true){
                System.out.println(game.getlose());
                game.DisplayMap();
                char aux=newGame();
                if (aux=='N')
                    break;
                dragons=dragonNumber();
                game=new GameState(dragons);
            }

            else if(game.getwin()==true){
                System.out.println(game.getwin());
                game.DisplayMap();
                char aux=newGame();
                if (aux=='N')
                    break;
                dragons=dragonNumber();
                game=new GameState(dragons);
            }




        }while (true);


    }





}

