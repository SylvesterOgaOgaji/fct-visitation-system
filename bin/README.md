# FCT Visitation System

## Overview

The FCT Visitation System is a comprehensive visitor management solution designed for Federal Capital Territory (FCT) government offices in Nigeria. The system facilitates visitor registration, QR code-based access control, vehicle management, parking allocation, and security incident management with specific adaptations for Nigerian government requirements.

![FCT Logo](src/main/resources/static/images/fct-logo.png)

## Key Features

- **Visitor Registration**: Online pre-registration system with appointment scheduling
- **NIN Verification**: Integration with NIMC API for National Identification Number validation
- **QR Code Access Control**: Generation and validation of QR codes at checkpoints
- **Vehicle Management**: Registration of visitor vehicles with driver verification
- **Parking Management**: Allocation and tracking of parking spaces
- **Security Alerts**: Real-time security incident reporting and response
- **Reporting & Analytics**: Comprehensive reporting on visitor statistics and security incidents

## System Architecture

The FCT Visitation System follows a modular architecture based on Spring Boot:

```
/FCTVisitationSystem
├── Frontend (Thymeleaf + Bootstrap)
├── Backend (Spring Boot 3.2)
├── Database (MySQL 8.0)
└── Security Tools Integration
```

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.2, Spring Security, JWT
- **Frontend**: Thymeleaf, Bootstrap 5, jQuery, HTML5/CSS3
- **Database**: MySQL 8.0, Hibernate ORM, Liquibase
- **Security**: BCrypt, TLS/SSL, Input Validation, CSRF Protection
- **QR Code**: ZXing for generation and scanning
- **NIN Verification**: eNVS API Integration
- **Notifications**: Email (SMTP), SMS Gateway
- **Security Tools**: OSSEC, OpenDLP, Zabbix, Gotify

## Prerequisites

- JDK 21 or higher
- MySQL 8.0 or higher
- Maven 3.8.x or higher
- Node.js 18.x or higher (for frontend build tools)
- SMTP Server for email notifications
- SMS Gateway account for SMS notifications

## Installation

### Database Setup

1. Create a new MySQL database:
   ```sql
   CREATE DATABASE fct_visitation_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'fctuser'@'localhost' IDENTIFIED BY 'FCT#VisitPass2025';
   GRANT ALL PRIVILEGES ON fct_visitation_db.* TO 'fctuser'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. Run the initialization scripts:
   ```bash
   mysql -u fctuser -p fct_visitation_db < sql/001-initial-schema.sql
   mysql -u fctuser -p fct_visitation_db < sql/002-initial-data.sql
   ```

### Application Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/fcta/visitation-system.git
   cd visitation-system
   ```

2. Configure application properties:
   ```bash
   cp src/main/resources/application.properties.sample src/main/resources/application.properties
   # Edit application.properties with your environment-specific settings
   ```

3. Build the application:
   ```bash
   mvn clean package
   ```

4. Run the application:
   ```bash
   java -jar target/fct-visitation-system-1.0.0.jar
   ```

5. Access the application:
   ```
   http://localhost:8080
   ```

## Configuration

### NIMC API Integration

Configure the NIMC API settings in `application.properties`:

```properties
nimc.api.url=https://api.nimc.gov.ng/v1
nimc.api.key=YOUR_API_KEY
nimc.api.secret=YOUR_API_SECRET
nimc.api.timeout=30000
```

For development, you can enable mock mode:
```properties
nimc.api.mock-enabled=true
```

### SMS Gateway Configuration

Configure the SMS gateway in `application.properties`:

```properties
sms.gateway.url=https://sms.provider.com/api/v1/send
sms.gateway.api-key=YOUR_SMS_API_KEY
sms.gateway.sender-id=FCT-VISIT
sms.gateway.enabled=true
```

## Usage

### Admin Login

Access the admin dashboard with the default admin credentials:
- Username: `admin`
- Password: `Admin123#`

**Important**: Change the default password immediately after first login.

### Security Personnel Login

Security personnel can access the security dashboard with their credentials:
- Username: `{assigned_username}`
- Password: `Security123#` (default, should be changed)

### Officer Login

FCT officers can access their dashboard with their credentials:
- Username: `{assigned_username}`
- Password: `Password123#` (default, should be changed)

### Visitor Registration

Visitors can register by:
1. Going to the homepage and clicking "Register Visit"
2. Filling in personal details, including NIN for verification
3. Selecting the facility, officer, and purpose of visit
4. Providing vehicle information if applicable
5. Receiving a confirmation with QR code via email

## Development

### Project Structure

```
/src
├── main
│   ├── java/com/fct/visitation
│   │   ├── config/             # Configuration classes
│   │   ├── controllers/        # MVC and REST controllers
│   │   ├── models/             # Entity and DTO classes
│   │   ├── repositories/       # Data access interfaces
│   │   ├── services/           # Business logic implementations
│   │   ├── security/           # Security configurations
│   │   └── utils/              # Utility classes
│   └── resources
│       ├── static/             # Static resources (CSS, JS, images)
│       ├── templates/          # Thymeleaf templates
│       └── application.properties # Application configuration
└── test                        # Test classes
```

### Building for Production

```bash
mvn clean package -P production
```

This will activate the production profile which:
- Minifies CSS and JS files
- Enables template caching
- Sets proper logging levels
- Configures for production environment

## Security Features

- **Authentication**: JWT token-based authentication
- **Password Security**: BCrypt hashing with salt
- **Role-Based Access Control**: Granular permissions system
- **Input Validation**: All inputs validated and sanitized
- **HTTPS**: TLS/SSL for all communications
- **CSRF Protection**: Cross-Site Request Forgery prevention
- **XSS Protection**: Cross-Site Scripting prevention
- **Security Headers**: HTTP security headers
- **QR Code Security**: Encrypted and time-limited QR codes
- **Audit Logging**: Comprehensive audit trails

## Nigerian-Specific Adaptations

- **NIN Integration**: National Identification Number verification via NIMC API
- **Nigerian Phone Format**: Support for Nigerian phone number formats (+234 or 0)
- **Nigerian Government Styling**: Green and white color scheme reflecting Nigerian flag
- **FCT Branding**: FCT logo and Nigerian coat of arms integrated in UI
- **Address Format**: Support for Nigerian address format
- **Local Date/Time**: Nigeria time zone (Africa/Lagos)
- **Security Requirements**: Compliance with Nigerian government security standards

## Monitoring & Maintenance

### Health Checks

Access the health endpoint to check system status:
```
http://localhost:8080/actuator/health
```

### Database Backup

Automated daily backups are configured via scheduled jobs. Manual backup:
```bash
./scripts/backup-database.sh
```

### Log Files

Log files are located at:
```
./logs/fct-visitation.log
```

Configure log rotation in `logback-spring.xml`.

## API Documentation

API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/my-feature`
5. Submit a pull request

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any inquiries, please contact:
- Email: support@fctvisitation.gov.ng
- Phone: +234 9 123 4567

## Acknowledgements

- Federal Capital Territory Administration
- National Identity Management Commission (NIMC)
- Ministry of Communications and Digital Economy
- All FCT security personnel and government officials