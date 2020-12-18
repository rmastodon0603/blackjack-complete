# Практическое задание

1. Создайте в пакете [org.itstep.blackjack.event](src/main/java/org/itstep/blackjack/event) интерфейс 
   слушателя игровых событий `GameEventListener`:

```java
public interface GameEventListener {
    void gameStart();
    void stand();
    void playerGetCard(Card card, int points);
    void dealerGetCard(Card card, int points);
    void playerSetBet(int amount);
    void gameOver(String winner, int playerPoints, int dealerPoints);
}
```

2. В классе [Game](src/main/java/org/itstep/blackjack/Game.java) объявляем список слушателей типа`GameEventListener`:

```java
private final List<GameEventListener> eventListeners;
```

Инициализируйте его в конструкторе класса [Game](src/main/java/org/itstep/blackjack/Game.java).

3. Для добавления слушателей реализуйте метод:

```java
public void addGameEventListener(GameEventListener eventHandler) {
        eventListeners.add(eventHandler);
}
```

4. Добавьте методы для публикации событий в игре:

```java
    private void publishStand() {
        eventListeners.forEach(GameEventListener::stand);
    }

    private void publishStart() {
        eventListeners.forEach(GameEventListener::gameStart);
    }

    private void publishGameOver(String winner) {
        eventListeners.forEach(l -> l.gameOver(winner, player.getPoints(), dealer.getPoints()));
    }

    private void publishPlayerTakeCard(Card card) {
        eventListeners.forEach(l -> l.playerGetCard(card, player.getPoints()));
    }

    private void publishDealerTakeCard(Card card) {
        eventListeners.forEach(l -> l.dealerGetCard(card, dealer.getPoints()));
    }
```

5. Вызовите необходимые методы для публикации всех слушателей о ходе игры в `hit()`, `stand()` и `play()`.
Внимательно читайте комментарии

6. В классе [BlackjackController](src/main/java/org/itstep/ui/controller/BlackjackController.java) 
   создайте [внутренний класс](https://ru.wikipedia.org/wiki/%D0%92%D0%BD%D1%83%D1%82%D1%80%D0%B5%D0%BD%D0%BD%D0%B8%D0%B9_%D0%BA%D0%BB%D0%B0%D1%81%D1%81) `GameEventHandler`:

```java
private class GameEventHandler implements GameEventListener {

        @Override
        public void gameStart() {
            start();
        }

        @Override
        public void gameOver(String winner, int playerPoints, int dealerPoints) {
            stand();
            updatePlayerPoints(playerPoints);
            updateDealerPoints(dealerPoints);
            lblBlackJack.setText(winner + " WIN");
        }

        @Override
        public void stand() {
            stop();
            CardView node = (CardView) hbDealerCards.getChildren().get(0);
            node.setHide(false);
        }

        @Override
        public void playerGetCard(Card card, int points) {
            hbPlayerCards.getChildren().add(new CardView(card));
            updatePlayerPoints(points);
        }

        @Override
        public void dealerGetCard(Card card, int points) {
            hbDealerCards.getChildren().add(new CardView(card));
            updateDealerPoints(points);
        }

        @Override
        public void playerSetBet(int amount) {

        }
    }
```

7. В конструкторе класса [BlackjackController](src/main/java/org/itstep/ui/controller/BlackjackController.java) 
   создайте экземпляр внутреннего класса и передайте его в метод `addEventListener()` объекта `game`:

```java
game.addGameEventListener(new GameEventHandler());
```

8. Проверьте работу игры

Дополнительное задание: Добавьте ставки в игре

## Ссыкли

* [Наблюдатель](https://refactoring.guru/ru/design-patterns/observer)
* [JavaFX Documentation Project](https://fxdocs.github.io/docs/book.pdf)
* [Getting Started with JavaFX](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm)
* [JavaFX: Working with JavaFX UI Components](https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/ui_controls.htm)
* [Учебник по JavaFX](https://code.makery.ch/ru/library/javafx-tutorial/)
* [Graphical user interfaces](https://java-programming.mooc.fi/part-13)
* [JavaFX Tutorial: Getting started](https://www.vojtechruzicka.com/javafx-getting-started/)
