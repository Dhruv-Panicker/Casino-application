# Casino Application

A CLI-based casino application built in Java featuring Blackjack.

## Prerequisites
- Java 21+
- Maven 3.6+

## Getting Started

### Build
```bash
mvn clean compile
```

### Run the Application
```bash
mvn exec:java
```

### Run Tests
```bash
mvn test
```

## How to Play

1. Start the application with `mvn exec:java`
2. Enter your name and starting balance
3. Choose to play Blackjack
4. Place your bet and follow the prompts (Hit/Stand/Double Down)
5. Try to beat the dealer without going over 21!

## CLI Snapshot

```
╔════════════════════════════════════════╗
║   🎰 WELCOME TO CASINO APPLICATION 🎰  ║
╚════════════════════════════════════════╝

Enter your name: Alice

Choose your starting balance:
1. $100
2. $500
3. $1000
4. Custom amount
Enter your choice (1-4): 2

Welcome, Alice! You start with $500.00

╔════════════════════════════════════════╗
║        WELCOME TO CASINO APP           ║
╚════════════════════════════════════════╝
Player: Alice
Balance: $500.00

=== Main Menu ===
1. Play Blackjack
2. View Session Stats
3. Exit Casino
Enter your choice (1-3): 1

=== Blackjack ===

--- Betting Phase ---
Current balance: $500.00
Enter your bet amount: $50

--- Initial Deal ---
Dealer's hand: K♠ [Hidden]
Your hand: 5♥ 7♦ [Value: 12]

--- Your Turn ---
Your hand: 5♥ 7♦ [Value: 12]
Hit (h), Stand (s), or Double Down (d)? h
You drew: Q♣

Your hand: 5♥ 7♦ Q♣ [Value: 22]

💥 Bust! You went over 21. Dealer wins!
Current balance: $450.00
```

## Features

- **Blackjack Game** - Classic Las Vegas rules
- **Session Management** - Track balance and game history
- **Safe Input Validation** - Prevents invalid bets and actions
- **Game Statistics** - View your performance

## Technologies

- Java 21
- Maven
- JUnit 5 (Testing)
- Mockito (Mocking)
- SLF4J + Logback (Logging)
