# Recommendation System

This project implements a quote recommendation system for matching user-requested topics with available providers. The application uses Spring Boot and leverages REST APIs to calculate and return matching quotes based on specific algorithms and configurations.

---

## Table of Contents
1. [Features](#features)
2. [Technology Stack](#technology-stack)
3. [Setup and Installation](#setup-and-installation)
4. [Configuration](#configuration)
5. [How to Run](#how-to-run)
7. [Testing](#testing)
8. [License](#license)

---

## Features
- RESTful API to calculate and return quotes based on user-specified topics.

---

## Technology Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.x
- **Build Tool:** Gradle
- **Libraries:**
    - Jackson (for JSON processing)
    - Spring Web (for RESTful APIs)
- **Testing Tools:**
    - JUnit 5
    - Mockito

---

## Setup and Installation

### Prerequisites
- Java 21
- Gradle 
- IDE.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/szymonjasniak/recommendation-system
   ```
2. Build the project:
   ```bash
   ./gradlew clean build
   ```

---

## Configuration
### JSON Configuration File
The `provider_topic.json` file defines the mapping between providers and topics. Example format:

```json
{
  "provider_topics": {
    "ProviderA": "Topic1+Topic2+Topic3",
    "ProviderB": "Topic2+Topic4",
    "ProviderC": "Topic1+Topic4+Topic5"
  }
}
```

- **Key:** Provider name
- **Value:** List of topics (separated by `+`)

### ProviderTopicLoader
This class loads the `provider_topic.json` file during application startup and initializes the provider-topic configuration.

---

## How to Run
1. Run the application:
   ```bash
   ./gradlew bootRun
   ```
2. The API will be available at `http://localhost:8080/rest/quote`.

---

## Testing

### Unit Tests
- Unit tests are written using JUnit 5 and Mockito.
- To run tests:
  ```bash
  ./gradlew test
  ```

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

