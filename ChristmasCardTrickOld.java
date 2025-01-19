
import java.util.Arrays;
import java.util.Random;

/**
 * This class attempts to figure out how a certain card trick works that I don't
 * know the name of. My dad showed it to me on Christmas, and he didn't know how
 * it worked, so I am trying to figure it out.
 *
 * General Process: -You draw a card and subtract whatever number it is from 13
 * then add that many cards to that stack, then you repeat it 3 more times. -Then,
 * a user discards one pile -Next, you flip the outer two stack's top card face
 * up and add their numbers together, then add 10 to that sum. -Then, from your
 * remaining pile, remove however many cards the previous sum totaled up to be
 * -Finally, the number/amount of remaining cards you have left is going to be
 * the number of the top card in the middle stack
 *
 * @author Calvin Hickey
 * @version 11/27/23
 */
public class ChristmasCardTrickOld {
    // PRIVATE VARIABLES:
    private CardDeck cardDeck;
    private int[] deck;

    /**
     * Set up the card deck and shuffle it.
     */
    public ChristmasCardTrickOld() {
        cardDeck = new CardDeck();
        System.out.println(cardDeck.visualizeDeck());
        cardDeck.shuffleDeck();
        System.out.println(cardDeck.visualizeDeck());
        deck = cardDeck.getDeck();

        System.out.println(Arrays.toString(deck) + " main deck");
    }

    /**
     * Performs the main operations of the card trick and returns true or false depending on
     * whether or not the result is the expected one.
     *
     * @return the number of the top card in the middle stack if successful, -1 otherwise.
     */
    public int mainTrick() {
        int firstCard = 0, secondCard = 0, thirdCard = 0, difference, currentCard = 1;
        int[] firstStack = drawStack(currentCard++); //for some reason the card you pick to start at doesn't matter, I've changed current card to a lot of different things and the trick always works!
        int[] secondStack = drawStack(firstStack.length + currentCard++);
        int[] thirdStack = drawStack(firstStack.length + secondStack.length + currentCard++);

        System.out.println(Arrays.toString(firstStack) + " first");
        System.out.println(Arrays.toString(secondStack) + " second");
        System.out.println(Arrays.toString(thirdStack) + " third");

        int cardsToRemove = firstStack[0] + thirdStack[0] + 10;
        System.out.println((firstStack.length + secondStack.length + thirdStack.length) + " number of cards already removed");
        System.out.println(cardsToRemove + " cards to remove");

        int totalCardsDrawn = firstStack.length + secondStack.length + thirdStack.length + cardsToRemove;
        System.out.println(totalCardsDrawn + " total cards removed");

        int testFinalCard = 52 - totalCardsDrawn;
        System.out.println(testFinalCard + " actual card");
        System.out.println(secondStack[0] + " expected card");

        return (testFinalCard == secondStack[0]) ? testFinalCard : -1;
    }

    private int[] drawStack(int startingIndex) {
        int difference = 13 - deck[startingIndex];
        int[] stack = new int[difference + 1];
        stack[0] = deck[startingIndex];
        for (int j = 1; j < difference + 1; j++) {
            stack[j] = deck[startingIndex + j - 1];
        }
        return stack;
    }

    /**
     * This class represents a standard 52-card deck
     */
    private class CardDeck {
        // PRIVATE VARIABLES
        public static final int TOTAL_CARDS = 52;
        public static final int SUITS = 4;
        public static final int CARDS_PER_SUIT = 13;

        public int[] deck;

        /**
         * Basic constructor for a standard 52-card deck. Face cards are represented as
         * their number relevant to their ordering rather than their name, e.g., "king"
         * -> 13, "queen" -> 12, "jack" -> 10. Houses/suites don't matter in this card
         * trick so they are omitted.
         */
        public CardDeck() {
            deck = new int[TOTAL_CARDS];
            int counter = 0;
            for (int i = 0; i < SUITS; i++) {
                for (int j = 1; j <= CARDS_PER_SUIT; j++) {
                    deck[counter++] = j;
                }
            }
        }

        public String visualizeDeck() {
            StringBuilder deckVisualization = new StringBuilder();
            for (int i = 0; i < TOTAL_CARDS; i++) {
                if (i == TOTAL_CARDS - 1) {
                    deckVisualization.append(deck[i]);
                } else if (i % CARDS_PER_SUIT == 0 && i > 0) {
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
