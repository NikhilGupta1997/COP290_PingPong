import java.util.*;

public class RandomDemo {
   public static void main( String args[] ){
   // create random object
   Random randomno = new Random();
 
   // check next int value  
   System.out.println("Next int value: " + randomno.nextInt(10000)+","+randomno.nextInt(10000));
   }    
}