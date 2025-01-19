import java.util.Arrays;
import java.util.Random;

/**
 * This class attempts to figure out how a certain card trick works that I don't
 * know the name of. My dad showed it to me on Christmas, and he didn't know how
 * it worked, so I am trying to figure it out.
 *
 * General Process: When you break it down to its simplest for this is the process. 1. shuffle the deck
 *  2. draw a card, then subtract that number from 13 and add that many cards on top of the first card you drew, 
 * for example: you draw 6 so you add 7 cards. 3. Repeat this process 2 more times. 4. Flip all the stack over. 
 * 5. Flip the top cards over on the outer two stacks. 6. add those numbers together, then add 10 
 * 7. Subtract that number from the remaining number of cards in the deck that you haven't placed down. 
 * 8. the number of cards now remaining in the deck should be the top card of the middle deck. 
 * 9. flip the top card of the middle deck over and it should match the number of remaining cards in the deck!
 * 
 * -----------------------------------------------------------------------------
 Example:
 suppose that you have the 4 stacks and they have been turned over so that

- stack 1 has c1 as the top card value and has a total of 13 - c1 + 1 cards
- stack 2 has c2 as the top card value and has a total of 13 - c2 + 1 cards
- stack 3 has c3 as the top card value and has a total of 13 - c3 + 1 cards
- stack 4 has c4 as the top card value and has a total of 13 - c4 + 1 cards

now suppose we put stack 4 back with the rest of the cards. 

the total number of cards on the table is
(14 - c1) + (14 - c2) + (14 - c3) = 42 - c1 - c2 - c3

and the total number of cards leftover is 52 - # cards on the table, i.e.,
52 - (42 - c1 - c2 - c3) = 10 + c1 + c2 + c3

when you take c1 and c2 and add them and then add 10, you have
c1 + c3 + 10

so if you deal out that many cards from your hand, you have exactly
(10 + c1 + c2 + c3) - (c1 + c3 + 10) = c2
cards remaining, which is the top card of stack 2.

for example, 

- if c1 is 5, then there are 9 cards in stack 1
- if c2 is 8, then there are 6 cards in stack 2
- if c3 is 10, then there are 4 cards in stack 3

the number of cards on the table is
42 - c1 - c2 - c3 = 42 - 5 - 8 - 10 = 19 (which you can check is equal to 9 + 6 + 4)

and the number of cards leftover is
10 + c1 + c2 + c3 = 10 + 5 + 8 + 10 = 33 (which you can check is 52 - 19)

count out c1 + c2 + 10 = 5 + 10 + 10 = 25 cards from the stack of 33 and you have 8 leftover,
which is exactly the value of c2.
----------------------------------------------------------------------------------------------
 *
 * @author Calvin Hickey
 * @version 11/27/23
 * @updated 1/14/25
 */
public class ChristmasCardTrick {
    // PRIVATE VARIABLES:
    private CardDeck cardDeck;
    private int[] deck;

    /**
 * Performs the main operations of the card trick and returns true or false depending on
 * whether or not the result is the expected one.
 *
 * @return the number of the top card in stack 2 if successful, -1 otherwise.
 */
public int mainTrick() {
    CardDeck deck = new CardDeck();
    deck.shuffleDeck();

    System.out.println("Deck after shuffling: " + Arrays.toString(deck.getDeck()));

    // Step 1: Create 3 stacks
    int[][] stacks = new int[3][];
    int deckIndex = 0;

    for (int i = 0; i < 3; i++) {
        if (deckIndex >= deck.getDeck().length) {
            throw new IllegalStateException("Not enough cards to create stack " + (i + 1));
        }

        // Normalize the card value to range 1–13
        int topCard = (deck.getDeck()[deckIndex] - 1) % 13 + 1;
        int cardsToAdd = 13 - topCard; // Cards to add based on top card value
        int stackSize = 1 + cardsToAdd;

        if (deckIndex + stackSize > deck.getDeck().length) {
            throw new IllegalStateException("Not enough cards to create stack " + (i + 1));
        }

        stacks[i] = Arrays.copyOfRange(deck.getDeck(), deckIndex, deckIndex + stackSize);
        deckIndex += stackSize;
    }

    // Print stacks
    for (int i = 0; i < 3; i++) {
        System.out.println("Stack " + (i + 1) + ": " + Arrays.toString(stacks[i]));
    }

    // Step 2: Flip all the stacks over
    // (Already handled when constructing stacks, so no changes needed)

    // Step 3: Flip the top cards of the outer two stacks
    int leftStackTop = (stacks[0][0] - 1) % 13 + 1; // Normalize value to 1–13
    int rightStackTop = (stacks[2][0] - 1) % 13 + 1; // Normalize value to 1–13
    System.out.println("Left stack top card: " + leftStackTop);
    System.out.println("Right stack top card: " + rightStackTop);

    // Add their values and add 10
    int totalSum = leftStackTop + rightStackTop + 10;
    System.out.println("Total sum (left + right + 10): " + totalSum);

    // Step 4: Subtract that number from the remaining number of cards in the deck
    int remainingCards = deck.getDeck().length - deckIndex; // Remaining cards after forming stacks
    int cardsToRemove = totalSum;
    int remainingAfterRemove = remainingCards - cardsToRemove;

    System.out.println("Remaining cards: " + remainingCards);
    System.out.println("Cards to remove: " + cardsToRemove);
    System.out.println("Remaining cards after removal: " + remainingAfterRemove);

    // Step 5: The number of cards remaining is the top card of the middle stack
    int finalCard = (stacks[1][0] - 1) % 13 + 1; // Normalize the top card of the middle stack to 1–13
    System.out.println("Final card (top of the middle stack): " + finalCard);

    // Step 6: Check if the remaining cards match the top card of the middle stack
    if (remainingAfterRemove == finalCard) {
        return finalCard; // Success
    } else {
        return -1; // Failure
    }
}
    


    /**
     * This class represents a standard 52-card deck
     */
    public static class CardDeck {
        // PRIVATE VARIABLES
        public static final int TOTAL_CARDS = 52;
        public int[] deck;

        /**
         * Basic constructor for a standard 52-card deck. Cards are numbered consecutively
         * from 1 to 52. Suits are omitted as they are not relevant to the trick.
         */
        public CardDeck() {
            deck = new int[TOTAL_CARDS];
            for (int i = 0; i < TOTAL_CARDS; i++) {
                deck[i] = i + 1;
            }
        }

        public String visualizeDeck() {
            StringBuilder deckVisualization = new StringBuilder();
            for (int i = 0; i < TOTAL_CARDS; i++) {
                if (i == TOTAL_CARDS - 1) {
                    deckVisualization.append(deck[i]);
                } else if ((i + 1) % 13 == 0) {
                    deckVisualization.append(deck[i]).append("\n");
                } else {
                    deckVisualization.append(deck[i]).append(", ");
                }
            }
            return deckVisualization.toString();
        }

        public void shuffleDeck() {
            Random rand = new Random();
            for (int i = 0; i < TOTAL_CARDS; i++) {
                int temp = deck[i];
                int randomIndex = rand.nextInt(TOTAL_CARDS);
                deck[i] = deck[randomIndex];
                deck[randomIndex] = temp;
            }
        }

        public int[] getDeck() {
            return deck;
        }
    }
}
