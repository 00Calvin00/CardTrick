
/**
 * Test file for the ChristmasCardTrick class.
 */
public class ChristmasCardTrickTest {
    public static void main(String[] args) {
        // Test the ChristmasCardTrick main functionality
        System.out.println("Testing ChristmasCardTrick Main Functionality...");
        ChristmasCardTrick trick = new ChristmasCardTrick();
        int result = trick.mainTrick();
        if (result != -1) {
            System.out.println("The trick worked! The middle stack's top card was: " + result);
        } else {
            System.out.println("ERROR: The trick did not work correctly.");
        }
    }
}
