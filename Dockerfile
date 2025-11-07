# Use Eclipse Temurin as base image
FROM eclipse-temurin:24-jdk

# Set the working directory in the container
WORKDIR /app

# Copy Gradle wrapper files
COPY gradlew ./
COPY gradle gradle

# Copy build configuration files
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

# Download dependencies (cache layer)
RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src src

# Build the application - don't run tests
RUN ./gradlew build -x test --no-daemon

# Expose the port the application will run on
EXPOSE 7070

# Run the application (fixed typo: --no-daemon)
CMD ["./gradlew", "run"]