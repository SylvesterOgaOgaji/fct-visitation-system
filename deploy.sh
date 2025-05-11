#!/bin/bash

echo "Setting up FCT Visitation System..."

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

# Build and start the containers
echo "Building and starting containers..."
docker-compose up -d

echo "Waiting for services to start..."
sleep 10

# Check if application is running
if curl -s http://localhost:8080/fct-visitation/actuator/health | grep -q "UP"; then
    echo "FCT Visitation System is now running at http://localhost:8080/fct-visitation"
    echo "Default admin credentials: admin / admin123"
else
    echo "There might be an issue with the deployment. Please check docker logs."
    echo "Run: docker-compose logs app"
fi

echo "Setup complete!"
