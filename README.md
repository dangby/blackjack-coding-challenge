# blackjack-coding-challenge
BlackJack Coding Challenge (make an OOP BlackJack game in the language of your choice)


Rules originally from https://www.mindfiretechnology.com/pages/blackjack

This is an opportunity to show off your coding experience and ability in a practical way. You will be doing this by coding a simple game. The requirements and rules are found below. Be sure to read the requirements carefully as they may be slightly different than you may expect.

Guidelines
1) Please use the framework and programming language you’re most comfortable with.
2) Focus on your strengths. If you’re best at the user interface then spend your time there. If you’re best at writing back-end code, then focus there, etc.
3) Please do not take more than 4 hours to do this exercise.
4) Please report how much time was taken. You do not need to include reading of rules or requirements as part of your time.
5) You don’t have to submit a completed/functional game. The code doesn’t even need to compile.
6) This exercise is a measurement of quality and quantity so don’t cut corners in order to get the game completed. There are no extra points awarded for a completed game.
7) Please include only source files and resources needed to build the application. Do not send binaries, dependent packages, user preference files, temp files, etc.

Scoring
1) 20% Readability - How easy is it to tell what the intent of the code is. How easy could someone come along and complete the code?
2) 20% OOP / SOLID - How well are the principles of Object Oriented Programming and SOLID applied. See principles here.
3) 10% Architecture & Design Patterns - How well was the solution designed. Does it use recognizable architectural and design patterns?
4) 10% Framework / Language - How well did you leverage the frameworks and programming languages you built your code upon, and was it used correctly / appropriately.
5) 10% Requirements - How well did the solution you created follow the given requirements
6) 30% Productivity - How much was accomplished given the amount of time taken.

Style Guide
1) Let your code do most of the talking. Add comments to describe things that are difficult to make obvious in your code.
2) Don’t spend time on formatting code so it lines up vertically. We don’t do that here so don’t waste your time.
3) Avoid magic numbers and/or magic strings
4) Use the best practices for the languages and frameworks you’re using for your solution.
5) The larger the variables scope, the more descriptive the name may need to be, but avoid single letter variables, even in a for loop. For example, use index inside of a for loop instead of x, etc.
6) Practice good namespacing (if applicable).
7) Keep functions short.
8) Use exception handling as appropriate, but do not use exception handling as the main flow of the application.
9) Avoid relying on external classes to house the internal state of your application. E.g. avoid reading from and writing to a label to keep track of the score.
10) One class per file.
11) Clear out dead code.
12) Include appropriate whitespace between lines.
13) Make descriptive variable and function names.
14) Avoid prefix or postfix notations unless idiomatic of the language/framework. E.g. avoid strName or NameStr
15) Avoid abbreviations that are not standard or which multiple possible abbreviations exist.
16) Group like class types together. E.g. group variables, properties, constructors, public methods, etc.
17) Avoid nesting code too deeply.

Blackjack Rules and Requirements
1) There is a permanent dealer which will deal all cards.
2) There will be one standard deck of 52 cards used, however the number of decks should be configurable for a future release. The deck is shuffled before each game.
3) The dealer will be dealt 2 cards, one face up, one face down to begin the game.
4) Each player will be dealt 2 cards, face up, to begin the game.
5) For this release we’re only supporting one human player vs. the dealer/computer, but code in such a way that adding a second player is a future requirement.
6) The objective is to get as close to the number 21 as possible, without going over. The card values are the value of the card for 2 thru 10. Jack, Queen, and King are each worth 10. Aces can be valued at either 1 or 11. A player's hand may have multiple aces, and each may be worth either 1 or 11.
7) If the player has 21 resulting from a combination of the original two cards dealt, and the dealer does not have 21 from his original two cards dealt the player wins automatically. If the dealer also has a total of 21 from two cards, then each are given a point.
8) If the player and dealer end up with the same value, the player loses the hand.
9) The player may request additional cards by asking for a “hit”. At which point the dealer will provide a card, face up. The player may “hit” as many times as he likes until he “busts” with a total over 21.
10) The player signals they are done taking cards by calling “hold”
11) The dealer / computer takes their turn after all of the players have finished with their turns. When the dealer holds, their hidden card is then shown.

Scoring should happen as follows:
1) A player win over the dealer is worth 1 point
2) A dealer win over a player is worth 1 point
3) A player or dealer win with two cards that total 21 is worth 2 points
4) A player and dealer each with two cards that total 21 are both awarded 1 point
5) The dealer will be controlled by the following rules:
6) The dealer must hit if his total is below 17
7) If the dealer has any high aces (counted as 11) as part of his total, he must hit while his count is below 18.
8) If any player has a value greater than their current value, the dealer must continue to hit. E.g. if a player has 20, the dealer must hit until they have 20 or greater.
