# Use Azul Zulu OpenJDK 21 Alpine as base image
FROM azul/zulu-openjdk-alpine:21

# Install curl and git for health checks and source cloning
RUN apk add --no-cache curl git

# Install Gradle
RUN apk add --no-cache gradle

# Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Build the application
RUN gradle clean build -x test --no-daemon

# Copy the built JAR file
RUN cp fineract-provider/build/libs/fineract-provider-*.jar app.jar

# Expose port 10000 (Render default)
EXPOSE 10000

# Set environment variables
ENV JAVA_OPTS="-Xmx1024m -Xms512m"
ENV PORT=10000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:$PORT/actuator/health || exit 1

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=$PORT"]
