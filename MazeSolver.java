import java.util.*;
import java.io.*;
import java.util.Stack;
public class MazeSolver{ 
 
    public static void main (String[] args){
        File file = new File("C:\\Users\\dawid\\Desktop\\stuff\\CS210\\Maze1.txt");
        
        int lives = 200;
        int posX = 0;
        int posY = 0;
        String[] input = new String[20];
        try {
            Scanner scan = new Scanner(file);
            
            for(int i = 0; i < 20; i++) {
            input[i] = scan.nextLine(); // read the maze
            }
            posX=Integer.parseInt(scan.nextLine()); //this is where you appear
            posY=Integer.parseInt(scan.nextLine());
            scan.close();
        } 
        catch (Exception e) {
            System.err.println(e);
        } 
        
        boolean[][] maze = new boolean[20][20];
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                if(input[i].charAt(j)=='X'){
                    maze[i][j]=false; //there's a wall
                }else{
                    maze[i][j]=true; //there's a space
                }
            }
        }
        Brain myBrain = new Brain();
        
        
        while(lives>0){
            System.out.println("\nCurrent position: "+posX+" "+posY+"\n");
            for(int i=0;i<20;i++){ //print out the map
            for(int j=0;j<20;j++){
                if(posX==i&&posY==j){
                    System.out.print("o"); //where we are
                }else if(maze[i][j]==true){
                    System.out.print(" "); //there is a space
                }else{
                    System.out.print("X"); //there is a wall
                }
                }
                    System.out.println();
            }
            
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                // Handle interrupted exception (if necessary)
                e.printStackTrace();
            }
            String move =myBrain.getMove(posX,posY,maze);
            if(move.equals("north")&&maze[posX-1][posY]){
                //System.out.print("MOVE: NORTH");
                posX--; //if the brain wants to move North AND it's possible
            }else if(move.equals("south")&&maze[posX+1][posY]){
                //System.out.print("MOVE: SOUTH");
                posX++; //if the brain wants to move South AND it's possible
            }else if(move.equals("east")&&maze[posX][posY+1]){
                //System.out.print("MOVE: EAST");
                posY++;
            }else if(move.equals("west")&&maze[posX][posY-1]){
                //System.out.print("MOVE: WEST");
                posY--;
            }
            lives--;
            if(posY%19==0||posX%19==0){ //found a way out!
                System.out.println("You found the exit at: "+posX+","+posY);
                System.exit(0);
            }
        }
        System.out.println("You died in the maze!");
        }
    }


    class Brain{
        private Stack<Integer> visitedX=new Stack<Integer>();
        private Stack<Integer> visitedY=new Stack<Integer>();
        private boolean [][]visited=new boolean[20][20];

        public String getMove(int posX,int posY, boolean maze[][])
        {
            visited[posX][posY]=true;
            if(!visited[posX+1][posY]==true && maze[posX+1][posY]==true){
                visitedX.push(posX);
                visitedY.push(posY);
                visited[posX+1][posY]=true;
                return "south"; //if the brain wants to move South AND it's possible
            }
                else if(!visited[posX-1][posY]==true && maze[posX-1][posY]==true){
                visitedX.push(posX);
                visitedY.push(posY);
                visited[posX-1][posY]=true;
                return "north"; //if the brain wants to move North AND it's possible
            }else if(!visited[posX][posY-1]==true && maze[posX][posY-1]==true){
                visitedX.push(posX);
                visitedY.push(posY);
                visited[posX][posY-1]=true;
                return "west";
                }else if(!visited[posX][posY+1]==true && maze[posX][posY+1]==true){
                visitedX.push(posX);
                visitedY.push(posY);
                visited[posX][posY+1]=true;
                return "east";
            }else
            {
                int lastPosX=visitedX.pop();
                int lastPosY=visitedY.pop();
                if(lastPosX<posX)
                {
                    return "north";
                }
                else if(lastPosX>posX)
                {
                    return "south";
                }
                else if(lastPosY>posY)
                {
                    return "east";
                }
                else
                {
                    return "west";
                }
            }
        }
}

