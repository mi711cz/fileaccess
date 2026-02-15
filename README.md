# File Access Application

Simple Java application that reads data from an input file, splits each line, and generates SQL SELECT queries.

## Overview

This application processes a delimited text file and generates SQL queries based on column values from each line. Each line is split by a configurable delimiter, and the first two values are used to generate SELECT statements.

## Requirements

- **Java**: 21 or later
- **Maven**: 3.6+

## Configuration

Configuration is managed via `src/main/resources/config_de.properties`. Required parameters:

| Parameter | Description | Example |
|-----------|-------------|---------|
| `SplittingChar` | Delimiter character(s) | `#.#` |
| `InputFile` | Input file path | `/path/to/input.txt` |
| `OutputFile` | Output SQL file path | `/path/to/output.sql` |

### Example Configuration

```properties
SplittingChar=#.#
InputFile=/home/user/data.txt
OutputFile=/home/user/queries.sql
```

## Logging

Logging is configured via `src/main/resources/log4j2.properties`. By default:
- Logs are written to **both Console and `logs/` directory**
- Log level: `INFO`
- Rolling file appender with 12 MB size limit
- Compressed rollover files (up to 5 archives)
- File: `logs/fileaccess.log`

### Custom Log Level

```bash
java -Dlog4j2.logLevel=DEBUG -cp "target/classes:target/dependency/*" FileAccess
```

## Building

### Compile and Package (Recommended)

```bash
mvn clean package
```

This automatically:
- Compiles source code
- Copies all dependencies to `target/dependency/`
- Creates executable JAR with manifest

### Compile Only

```bash
mvn clean compile
```

## Execution

### Option 1: Direct Classpath Execution (Recommended for Development)

```bash
java -cp "target/classes:target/dependency/*" FileAccess
```

### Option 2: JAR Execution (After `mvn package`)

```bash
java -jar target/fileaccess-1.0.0.jar
```

The JAR includes a proper manifest with classpath pointing to dependencies.

### Custom Logging Configuration

```bash
java -Dlog4j2.logLevel=DEBUG \
     -cp "target/classes:target/dependency/*" FileAccess
```

## Input File Format

Input file should contain lines with at least 2 delimited values:

```
value1#.#value2
value3#.#value4
```

## Output Format

Output file contains SQL SELECT queries:

```sql
SELECT * FROM abc WHERE Col1 = 'value1' AND Col2 = 'value2';
SELECT * FROM abc WHERE Col1 = 'value3' AND Col2 = 'value4';
```

## Error Handling

The application uses exit codes:
- `0` - Success
- `1` - I/O error (file not found, write error, etc.)
- `2` - Configuration error (missing parameters)
- `3` - Unexpected error

## Features

- ✅ Efficient file processing with try-with-resources
- ✅ UTF-8 encoding support
- ✅ Proper error handling and logging
- ✅ SQL injection protection (single quote escaping)
- ✅ Console and file logging (simultaneous)
- ✅ Automatic dependency management (maven-dependency-plugin)
- ✅ Executable JAR with manifest
- ✅ Java 21 compatibility
- ✅ Zero external dependencies (Log4j only)

## Troubleshooting

### NoClassDefFoundError: org/apache/logging/log4j/LogManager

Ensure you run `mvn package` first or manually copy dependencies:
```bash
mvn dependency:copy-dependencies
java -cp "target/classes:target/dependency/*" FileAccess
```

### No output visible

The new log4j2.properties includes a console appender. Log messages should appear on stdout. Verify:
- `log4j2.properties` is in `target/classes/`
- Configuration is valid (check color and format output)

### Configuration parameter not found

Verify `src/main/resources/config_de.properties` contains all required parameters: `SplittingChar`, `InputFile`, `OutputFile`

## Project Structure

```
fileaccess/
├── src/
│   └── main/
│       ├── java/
│       │   ├── FileAccess.java
│       │   ├── CheckArguments.java
│       │   └── CheckConfigurationFile.java
│       └── resources/
│           ├── config_de.properties
│           └── log4j2.properties
├── target/              (ignored by git)
├── logs/                (ignored by git)
├── pom.xml
└── README.md
```

## License

Open source

## Error Handling

The application uses exit codes for error conditions:
- `0` - Success
- `1` - I/O error
- `2` - Configuration error
- `3` - Unexpected error

## License

Open source