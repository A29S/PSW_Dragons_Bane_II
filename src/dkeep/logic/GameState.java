package dkeep.logic;


import java.util.Random;
import java.util.Vector;

public class GameState {

        private dkeep.logic.Map map;
        private dkeep.logic.Hero hero;
        private Vector<Dragon> dragons;
        private dkeep.logic.Sword sword;
        private boolean lose;
        private boolean win;

        private int dragonDeadCount=0;

        public GameState(int dragonsNumber){

            this.map=new dkeep.logic.Map();

            this.hero=new dkeep.logic.Hero(1,1);

            this.dragons=new Vector<>(dragonsNumber);
            int x,y;
            for(int i=0; i<dragonsNumber;i++){
                Random random=new Random();
                while (true){
                     x=random.nextInt(9);
                     y=random.nextInt(9);
                    if(map.getMap()[y][x]==' ' && map.getMap()[y-1][x]!='H' && map.getMap()[y][x-1]!='H'){
                        //System.out.println(x+" "+y);
                        map.setMap(x,y,'D');
                        break;
                    }
                }
                Dragon dragon=new Dragon(x,y);
                dragons.add(dragon);
            }

            this.sword=new dkeep.logic.Sword(1,8);

            this.lose=false;

            this.win=false;


        }


        public void DisplayMap(){

            for (int i=0; i<10; i++){
                if(i!=0){
                    System.out.print("\n");
                }
                for(int j=0;j<10;j++){
                    System.out.print(map.getMap()[i][j]);
                }
            }
            System.out.print("\n");

        }

        public void StateCheck(){

            System.out.println(sword.getHasSword());
            if (!sword.getHasSword()){
                if(hero.getY()== sword.getY() && hero.getX()==sword.getX()){
                    sword.setHasSword(true);
                    map.setMap(hero.getX(), hero.getY(),'A');


                }
            for(Dragon dragon:dragons){
                if((((hero.getX()+1)==dragon.getX()||(hero.getX()-1)==dragon.getX()) && hero.getY()==dragon.getY()) || (((hero.getY()+1)==dragon.getY()||(hero.getY()-1)==dragon.getY()) && hero.getX()==dragon.getX())){
                    lose=true;
                    break;
                }
            }
            }


            else if(sword.getHasSword()){
             //não é o mais eficiente mas funciona
             for (Dragon dragon:dragons){
                if((((hero.getX()+1)==dragon.getX()||(hero.getX()-1)==dragon.getX()) && hero.getY()==dragon.getY()) || (((hero.getY()+1)==dragon.getY()||(hero.getY()-1)==dragon.getY()) && hero.getX()==dragon.getX())){
                    map.setMap(dragon.getX(), dragon.getY(), ' ');
                    if(!dragon.getdragonDead()){
                        dragon.setdragonDead(true);
                        dragonDeadCount+=1;
                        System.out.println("Count:"+dragonDeadCount);
                    }

                    }
                }
             }



             if(hero.getY()==5 && hero.getX()==9 && dragonDeadCount==dragons.size())
                    win=true;






        }

       public boolean getwin(){
            return  this.win;
        }

        public boolean getlose(){
            return this.lose;
        }

        public void HeroMovement(char move){

            hero.movement(move, map, sword.getHasSword(),dragonDeadCount,dragons.size());



        }

        public void NonControlabelMovement(){
           if (dragonDeadCount!=dragons.size())
               for (Dragon dragon:dragons)
                   if(!dragon.getdragonDead())
                        dragon.movement(map);


        }


}

class Map{

        private char[][] map={{'X','X','X','X','X','X','X','X','X','X'},
                {'X','H',' ',' ',' ',' ',' ',' ',' ','X'},
                {'X',' ','X','X',' ','X','X','X',' ','X'},
                {'X',' ','X','X',' ','X','X','X',' ','X'},
                {'X',' ','X','X',' ','X','X','X',' ','X'},
                {'X',' ',' ',' ',' ',' ',' ','X',' ','E'},
                {'X',' ','X','X',' ','X','X','X',' ','X'},
                {'X',' ','X','X',' ','X','X','X',' ','X'},
                {'X','S','X','X',' ',' ',' ',' ',' ','X'},
                {'X','X','X','X','X','X','X','X','X','X'}};

       public char[][] getMap(){

            return this.map;

        }

       public void setMap(int x, int y, char element){

            //ver se faço algo mais complicado aqui
            this.map[y][x]=element;


        }


}

class GameElement{

        //atributos

        private int x,y;

        public GameElement(){
            this.x=0;
            this.y=0;
        }
        GameElement(int x, int y){
            this.x=x;
            this.y=y;
        }



        public void movement(char move, dkeep.logic.Map map, boolean hasSword, int dragonDead, int size){

            switch (move){

                case 'w':
                    if(map.getMap()[y-1][x]=='X' || (map.getMap()[y-1][x]=='E' && dragonDead!=size) )
                        break;

                    else {
                        if (hasSword){
                            map.setMap(x,y,' ');
                            map.setMap(x,y-1,'A');
                            y -= 1;
                        }


                        else {
                            map.setMap(x,y,' ');
                            map.setMap(x,y-1,'H');
                            y -= 1;
                        }
                    }
                    break;


                case 'a':
                    if(map.getMap()[y][x-1]=='X' || map.getMap()[y][x-1]=='E')
                        break;

                    else {
                        if(hasSword){
                            map.setMap(x,y,' ');
                            map.setMap(x-1,y,'A');
                            x-=1;
                        }
                        else{
                            map.setMap(x,y,' ');
                            map.setMap(x-1,y,'H');
                            x-=1;
                        }
                    }
                    break;

                case 's':
                    if(map.getMap()[y+1][x]=='X' || map.getMap()[y+1][x]=='E')
                        break;

                    else {
                        if(hasSword) {
                            map.setMap(x, y, ' ');
                            map.setMap(x, y + 1, 'A');
                            y += 1;
                        }

                        else {
                            map.setMap(x, y, ' ');
                            map.setMap(x, y + 1, 'H');
                            y += 1;
                        }
                    }
                    break;


                case 'd':
                    if(map.getMap()[y][x+1]=='X' || (map.getMap()[y][x+1]=='E' && dragonDead!=size))
                        break;

                    else {

                        if (hasSword){
                            map.setMap(x,y,' ');
                            map.setMap(x+1,y,'A');
                            x+=1;
                        }

                        else if(dragonDead==size && map.getMap()[y][x+1]=='E'){
                            map.setMap(x,y,' ');
                            map.setMap(x+1,y,'H');
                            x+=1;
                        }


                        else{
                            map.setMap(x,y,' ');
                            map.setMap(x+1,y,'H');
                            x+=1;
                        }
                    }
                    break;

            }



        }

        public int getX(){
            return this.x;
        }

        public int getY(){
            return this.y;
        }

        public void setX(int x){
            this.x=x;
        }

        public void setY(int y){
            this.y=y;
        }
}


class Hero extends dkeep.logic.GameElement {

        public Hero(int x, int y){
            super(x,y);
        }


}


class Dragon extends dkeep.logic.GameElement {

        private char movementDirection=' ';

        private boolean dragonDead;
        public Dragon(int x, int y){
            super(x,y);
            this.dragonDead=false;
        }

        public boolean getdragonDead(){
            return this.dragonDead;
        }

        public void setdragonDead(boolean state){
            this.dragonDead=state;
        }
        public void auxMovement(char movementDirection, Map map) {

        switch (movementDirection) {
            case 'a':
                map.setMap(getX(), getY(), ' ');
                map.setMap(getX() - 1, getY(), 'D');
                setX(getX() - 1);
                break;

            case 's':
                map.setMap(getX(), getY(), ' ');
                map.setMap(getX(), getY() + 1, 'D');
                setY(getY() + 1);
                break;

            case 'w':
                map.setMap(getX(), getY(), ' ');
                map.setMap(getX(), getY() - 1, 'D');
                setY(getY() - 1);
                break;

            case 'd':
                map.setMap(getX(), getY(), ' ');
                map.setMap(getX() + 1, getY(), 'D');
                setX(getX() + 1);
                break;
        }
    }

        //não é a melhor forma
        public void movement(Map map) {

            char[] directions = {'w', 'a', 's', 'd'};
            Random random = new Random();



            if ((map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'E') && map.getMap()[getY()][getX() - 1] == 'X' && map.getMap()[getY() + 1][getX()] == ' ' && map.getMap()[getY() - 1][getX()] == ' ') {

                if (movementDirection == ' ') {

                    //forma diferente de usar o random
                    char[] aux = {directions[0], directions[2]};
                    movementDirection = aux[random.nextInt(2)];

                }

                auxMovement(movementDirection,map);

            }
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D') && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && map.getMap()[getY()][getX() + 1] == ' ' && map.getMap()[getY()][getX() - 1] == ' ') {

                if (movementDirection == ' ') {

                    //forma diferente de usar o random
                    char[] aux = {directions[1], directions[3]};
                    movementDirection = aux[random.nextInt(2)];

                }
                auxMovement(movementDirection,map);

            }


            //canto inferior direito
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D') && map.getMap()[getY() - 1][getX()] == ' ' && (map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'D') && map.getMap()[getY()][getX() - 1] == ' ') {
                char[] aux = {directions[0], directions[1]};
                movementDirection = aux[random.nextInt(2)];

                auxMovement(movementDirection,map);

            }

            //cruzameto direita
            else if (map.getMap()[getY() + 1][getX()] == ' ' && map.getMap()[getY() - 1][getX()] == ' ' && (map.getMap()[getY()][getX() + 1] == 'X' && map.getMap()[getY()][getX() + 1] == 'D') && map.getMap()[getY()][getX() - 1] == ' ') {
                //gerar
                char[] aux = {directions[0], directions[1], directions[2]};
                movementDirection = aux[random.nextInt(3)];

                auxMovement(movementDirection,map);

            }

            //cruzamento esquerda
            else if (map.getMap()[getY() + 1][getX()] == ' ' && map.getMap()[getY() - 1][getX()] == ' ' && map.getMap()[getY()][getX() + 1] == ' ' && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {
                //gerar
                char[] aux = {directions[0], directions[2], directions[3]};
                movementDirection = aux[random.nextInt(3)];

                auxMovement(movementDirection,map);


            }

            //canto inferior esquerdo
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D') && map.getMap()[getY() - 1][getX()] == ' ' && map.getMap()[getY()][getX() + 1] == ' ' && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {
                //gerar
                char[] aux = {directions[0], directions[3]};
                movementDirection = aux[random.nextInt(2)];

                auxMovement(movementDirection,map);
            }

            //apenas para cima
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'S' || map.getMap()[getY() + 1][getX()] == 'D') && map.getMap()[getY() - 1][getX()] == ' ' && (map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'D') && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {

                movementDirection = 'w';
                auxMovement(movementDirection,map);
            }

            //apenas para esquerda
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D')  && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && (map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'D') && map.getMap()[getY()][getX() - 1] == ' ') {

                movementDirection = 'a';
                auxMovement(movementDirection,map);
            }

            //apenas para direita
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D')  && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && map.getMap()[getY()][getX() + 1] == ' ' && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {

                movementDirection = 'd';
                auxMovement(movementDirection,map);
            }

            //apenas para baixo
            else if (map.getMap()[getY() + 1][getX()] == ' '  && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && (map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'D') && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {

                movementDirection = 's';
                auxMovement(movementDirection,map);
            }



            //canto superior direito
            else if (map.getMap()[getY() + 1][getX()] == ' ' && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && (map.getMap()[getY()][getX() + 1] == 'X' || map.getMap()[getY()][getX() + 1] == 'D') && map.getMap()[getY()][getX() - 1] == ' ') {
                //gerar
                char[] aux = {directions[1], directions[2]};
                movementDirection = aux[random.nextInt(2)];

                auxMovement(movementDirection,map);
            }

            //canto superior esquerdo
            else if (map.getMap()[getY() + 1][getX()] == ' ' && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && map.getMap()[getY()][getX() + 1] == ' ' && (map.getMap()[getY()][getX() - 1] == 'X' || map.getMap()[getY()][getX() - 1] == 'D')) {
                //gerar
                char[] aux = {directions[3], directions[2]};
                movementDirection = aux[random.nextInt(2)];

                auxMovement(movementDirection,map);

            }
            else if (map.getMap()[getY() + 1][getX()] == ' ' && (map.getMap()[getY() - 1][getX()] == 'X' || map.getMap()[getY() - 1][getX()] == 'D') && map.getMap()[getY()][getX() + 1] == ' ' && map.getMap()[getY()][getX() - 1] == ' ') {
                //gerar
                char[] aux = {directions[1], directions[2], directions[3]};
                movementDirection = aux[random.nextInt(3)];

                auxMovement(movementDirection,map);

            }
            else if ((map.getMap()[getY() + 1][getX()] == 'X' || map.getMap()[getY() + 1][getX()] == 'D') && map.getMap()[getY() - 1][getX()] == ' ' && map.getMap()[getY()][getX() + 1] == ' ' && map.getMap()[getY()][getX() - 1] == ' ') {
                //gerar
                char[] aux = {directions[1], directions[2], directions[3]};
                movementDirection = aux[random.nextInt(3)];

                auxMovement(movementDirection,map);


            }

            else if (map.getMap()[getY() + 1][getX()] == ' ' && map.getMap()[getY() - 1][getX()] == ' ' && map.getMap()[getY()][getX() + 1] == ' ' && map.getMap()[getY()][getX() - 1] == ' ') {
                //gerar
                char[] aux = {directions[1], directions[2], directions[3]};
                movementDirection = aux[random.nextInt(3)];

                auxMovement(movementDirection,map);

            }
        }


}

class Sword extends dkeep.logic.GameElement {

        private boolean hasSword;

        public Sword(int x, int y){
            super(x, y);
            this.hasSword=false;

        }

        boolean getHasSword(){
            return this.hasSword;
        }

        void setHasSword(boolean state){
            this.hasSword=state;
        }


}


