package org.itstep.blackjack;

import lombok.extern.slf4j.Slf4j;
import org.itstep.blackjack.card.Card;

@Slf4j
public class Game {
    public static final int TWENTY_ONE = 21;
    public static final int THRESHOLD = 15;

    private final Player player;
    private final Player dealer;
    private final Deck deck;

    public Game() {
        deck = new Deck();
        dealer = new Player(0);
        player = new Player(1000);
    }

    public void hit() {
        Card card = deck.getOne();
        player.takeCard(card);
        log.info("Player take a card {}", card);
        // TODO: вызвать publishPlayerTakeCard
        if (player.getPoints() > TWENTY_ONE) {
            dealer.getCards().get(0).setHide(false);
            // TODO: вызвать publishGameOver
            log.info("Game over. Win {}", getWinner());
        }
    }

    public void stand() {
        dealer.getCards().get(0).setHide(false);
        // TODO: вызвать publishStand
        log.info("Stand");
        while (dealer.getPoints() < THRESHOLD) {
            Card card = deck.getOne();
            dealer.takeCard(card);
            // TODO: вызвать publishDealerTakeCard
            log.info("Dealer take a card {}", card);
        }
        // TODO: вызвать publishGameOver(getWinner());
        log.info("Game over. Win {}", getWinner());
    }

    public void setBet(int amount) throws NoMoneyEnough {
        player.setBet(amount);
        // TODO: реализовать логику добавления ставки
    }

    public String getWinner() {
        if (player.getPoints() <= TWENTY_ONE) {
            if (dealer.getPoints() > TWENTY_ONE) {
                return "Player";
            } else if (player.getPoints() > dealer.getPoints()) {
                return "Player";
            } else {
                return "Dealer";
            }
        }
        return "Dealer";
    }

    public void play() {
        deck.shuffle();
        player.clear();
        dealer.clear();
        // TODO: вызвать publishStart();
        log.info("Play game");

        Card firstCard = deck.getOne();
        player.takeCard(firstCard);
        // TODO: вызвать publishPlayerTakeCard
        log.info("Player take first card {}", firstCard);

        Card second = deck.getOne();
        player.takeCard(second);
        // TODO: вызвать publishPlayerTakeCard
        log.info("Player take second card {}", second);

        Card hiddenCard = deck.getOne();
        hiddenCard.setHide(true);
        dealer.takeCard(hiddenCard);
        // TODO: вызвать publishDealerTakeCard
        log.info("Dealer take hidden card {}", hiddenCard);

        Card lastCard = deck.getOne();
        dealer.takeCard(lastCard);
        // TODO: вызвать publishDealerTakeCard
        log.info("Dealer take second card {}", lastCard);
    }

}
