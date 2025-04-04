# FCT Visitation System

An advanced online visitation module for enhanced security and efficiency in FCT government offices.

## Overview

The FCT Visitation System is a comprehensive solution for managing visitors, vehicles, parking spaces, and security responses in Federal Capital Territory (FCT) government offices. This system addresses the inadequacies of existing manual processes by introducing automated visitor registration, vehicle tracking, parking management, and security incident response mechanisms.

## Features

- **Visitor Management**: Pre-registration system with QR code generation for visitors to select specific officers and purposes of visit
- **Vehicle Management**: Tracking of personal and rented vehicles, with driver verification using NIN for rented vehicles
- **Parking Allocation**: Automated allocation of parking spaces based on availability and vehicle type
- **Security Protocols**: QR code checkpoints for tracking visitor movement and real-time alerts for security breaches
- **Comprehensive Audit Trail**: Detailed logs of visitor-officer interactions for accountability and transparency

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.2
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5
- **Mobile App**: Ionic Framework
- **Security**: JWT Authentication, RBAC, OSSEC, OpenDLP
- **QR Code**: Zxing
- **Notifications**: Postfix (Email), SMSGateway.me (SMS)
- **Incident Management**: Zabbix

## Getting Started

### Prerequisites

- JDK 21 or higher
- MySQL 8.0
- Maven 3.8+
- Node.js 18+ and npm (for mobile app)

### Installation

1. Clone the repository:
mysql -u root -p
CREATE DATABASE fct_visitation_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'fctvisitadmin'@'localhost' IDENTIFIED BY 'securepassword';
GRANT ALL PRIVILEGES ON fct_visitation_db.* TO 'fctvisitadmin'@'localhost';
FLUSH PRIVILEGES;
3. Update application properties:
Edit `src/main/resources/application.properties` with your database credentials.

4. Build and run the application:
5. Access the application:
Open http://localhost:8080/fct-visitation in your browser.

## Documentation

Comprehensive documentation is available in the [docs](./docs) directory.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Project Structure

The project follows a standard Spring Boot structure:
- `src/main/java`: Java source code
- `src/main/resources`: Configuration files and templates
- `src/test`: Test code
- `fct-visitation-mobile`: Mobile application code

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Federal Capital Territory Administration
- IGNOU Master of Computer Applications Program
