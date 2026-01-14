# Casino Application

A CLI-based casino application built in Java featuring Blackjack and Roulette games.

## Project Structure

```
casino-app/
├── src/
│   ├── main/
│   │   ├── java/com/casino/
│   │   │   ├── models/          # Core game models (Card, Deck, Player, etc.)
│   │   │   ├── games/           # Game implementations (Blackjack, Roulette)
│   │   │   ├── cli/             # Command-line interface
│   │   │   ├── utils/           # Utility classes
│   │   │   └── Main.java        # Entry point
│   │   └── resources/           # Configuration files
│   └── test/
│       └── java/com/casino/     # Unit tests
├── pom.xml                      # Maven configuration
└── README.md
```

## Building the Project

### Prerequisites
- Java 11+
- Maven 3.6+

### Build
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package
```bash
mvn clean package
```

### Run the Application
```bash
java -jar target/casino-app.jar
```

## Development Phases

- **Phase 0**: Project setup & Java fundamentals (Card, Deck, Player)
- **Phase 1**: OOP deep dive (Abstract classes, encapsulation)
- **Phase 2**: Blackjack basic logic
- **Phase 3**: Blackjack advanced features
- **Phase 4**: Roulette game engine
- **Phase 5**: CLI interface
- **Phase 6**: Casino management & design patterns
- **Phase 7**: Collections & data structures
- **Phase 8**: Testing & quality assurance
- **Phase 9**: Advanced features (Streams, logging)
- **Phase 10**: Code quality & documentation

## Key Technologies
- **Java 11** - Language
- **Maven** - Build tool
- **JUnit 5** - Testing framework
- **Mockito** - Mocking library
- **SLF4J + Logback** - Logging
