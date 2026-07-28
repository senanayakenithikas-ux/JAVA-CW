# Malabe Tuk-Tuk & Three-Wheeler Spares Depot

A JavaFX desktop application for managing spare-parts inventory and dealer information for a small three-wheeler spares business. Built for CM1601 Programming Fundamentals — no database is used; all data is stored and persisted in plain text files.

## Features

- **Inventory management** — add, update and delete spare parts, each with its own price, quantity, category and low-stock threshold
- **Dealer management** — view all dealers and get four randomly selected, location-sorted recommended dealers
- **Search** — filter parts by keyword, category and price range
- **Low stock monitor** — instantly see which parts have fallen at or below their own reorder threshold
- **Shopping cart & checkout** — add parts to a cart, with automatic bulk (5%) and Engine+Electrical synergy (10%) discounts, stock deduction, and a full audit trail
- **Legacy data cleaning** — standalone utilities that turn inconsistent, real-world "dirty" data (mixed comma/pipe/semicolon delimiters, currency prefixes, multiple date formats) into clean CSV files the application can read

## Tech Stack

- Java
- JavaFX 21
- Maven
- JUnit 5 (Jupiter)

## Project Structure

```
src/main/java
├── Controllers/     JavaFX controllers — one per screen (Home, Inventory, Search, Dealer, Cart, LowStock),
│                     plus HelloApplication (JavaFX entry point) and Launcher (main() entry point)
├── models/           Plain data classes — Part, Dealer, CartItem
├── main/              Business-logic managers — InventoryManager, DealerManager, Cart
└── utils/             InventoryCleaner, DealerCleaner (legacy-data cleaning),
                        InventorySearch (multi-criteria search), AuditLogger

src/main/resources
├── models/           FXML screen layouts
└── assets/            Part images + default placeholder image

src/test/java         JUnit 5 test classes (models, main, utils)

.data/                Legacy and cleaned text files (inventory, dealers, audit log)
```

## Getting Started

### Prerequisites
- Java 17+ (JDK)
- Maven (or use the included Maven Wrapper — no separate install needed)

### Clone
```bash
git clone https://github.com/senanayakenithikas-ux/JAVA-CW.git
cd JAVA-CW
```

### Run
```bash
./mvnw javafx:run        # macOS/Linux
mvnw.cmd javafx:run       # Windows
```

On first launch, the app automatically checks whether `.data/inventory_clean.txt` and `.data/dealers_clean.txt` exist. If either is missing, it runs `InventoryCleaner`/`DealerCleaner` to generate clean data from the legacy files before the home screen opens.

### Run Tests
```bash
./mvnw test
```

## How the Legacy Data Pipeline Works

The raw legacy files (`.data/inventory_legacy.txt`, `.data/dealers_legacy.txt`) intentionally contain messy, real-world data — mixed delimiters, inconsistent currency formatting, and several different date formats. `InventoryCleaner` and `DealerCleaner`:

1. Count how many commas, pipes and semicolons appear on each line and treat whichever occurs most often as the real delimiter for that line
2. Protect commas that appear inside dates (e.g. `Oct 15, 2023`) from being mistaken for delimiters
3. Strip currency prefixes and normalise dates into a single format
4. Write the result to `.data/inventory_clean.txt` / `.data/dealers_clean.txt`, which is what the running application actually reads and writes

## Testing

The project includes 7 JUnit 5 test classes covering the model classes (`Part`, `Dealer`, `CartItem`), the manager classes (`InventoryManager`, `DealerManager`, `Cart`) and the legacy-data cleaner (`InventoryCleaner`).

## Author

N.S.Senanayake
BSc (Hons) Artificial Intelligence and Data Science — CM1601 Programming Fundamentals

## Acknowledgements

Thanks to Mr. Dinusha Ruwan Kumara, Mr. Ruwan Egodawaththa, Ms. Gangulie Ranawaka, Ms. Kavindi Gimshani and Dr. Prasan Yapa for their guidance throughout this module.
