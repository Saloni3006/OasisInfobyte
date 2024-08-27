import java.util.Random;
import java.util.Scanner;

class guess_number{
public static void main(String[] args) {

    Scanner sc=new Scanner (System.in);
    Random random=new Random();

    int maxRounds=3;
    int totalCSore=0;

    System.out.println("welcome to the game");

    for(int round=1;round<=maxRounds;round++){

        System.out.println("round"+round);
        int numberToGues=random.nextInt(100)+1;
        int maxAttempts=5;
        int score=0;
        boolean hasGuessedCorrectly=false;

        for(int attempt=1;attempt<=maxAttempts;attempt++){
            System.out.println("attemp"+attempt+" enter ur guess 1-100");

            int userGuess=sc.nextInt();

            if (userGuess==numberToGues) {
                System.out.println("congo, no. is guessed");
                score=maxAttempts-attempt+1;
                totalCSore+=score;
                hasGuessedCorrectly=true;
                break;
            }
            else if(userGuess<numberToGues){
                System.out.println("tooo low");

            }
            else{
                System.out.println("too high");
            }


        }
        
    if (!hasGuessedCorrectly) {
        System.out.println("soory...the number was"+numberToGues);
                
    }
        System.out.println(score);
    
}
System.out.println(totalCSore);

    }


}