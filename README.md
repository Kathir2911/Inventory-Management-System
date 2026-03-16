# Inventory Management System (IMS)

A modern, full-featured Inventory Management System built with Spring Boot, Thymeleaf, and MySQL to help businesses efficiently manage products, stock levels, suppliers, and orders. This system provides real-time inventory tracking, automated stock alerts, and comprehensive role-based access control to streamline inventory operations.

## 🚀 Features

### 📊 Dashboard
- **Overview of inventory statistics**
  - Total products, categories, suppliers
  - Purchase and sales order summary
  - Low stock alerts with visual indicators
  - Recent stock activity logs
  - Real-time inventory insights

### 📦 Inventory Management
- **Product Management**
  - Add, edit, delete products
  - Category assignment
  - Price and reorder level management
  - Automatic stock tracking
- **Category Management**
  - Organize products by categories
  - Category-based filtering and reporting
- **Supplier Management**
  - Supplier contact information
  - Purchase order tracking per supplier
- **Stock Tracking**
  - Real-time stock level monitoring
  - Automatic stock updates from orders
  - Stock movement history

### 📋 Orders
- **Purchase Orders**
  - Create and manage purchase orders
  - Status tracking (Pending, Received, Cancelled)
  - Automatic stock increase when marked as "Received"
  - Supplier integration
- **Sales Orders**
  - Process customer sales
  - Automatic stock deduction
  - Payment method tracking
  - Customer information management

### 🔔 Smart Alerts
- **Low Stock Detection**
  - Automatic monitoring of stock levels
  - Visual badges on low stock products
  - Dashboard alerts for immediate attention
- **Email Notifications**
  - Automatic email alerts when stock falls below reorder level
  - Detailed stock information in emails
  - Configurable email recipients

### 📈 Reporting
- **Inventory Insights**
  - Stock level reports
  - Product performance analytics
- **Order Tracking**
  - Purchase and sales order history
  - Order status monitoring
- **Stock Activity Logs**
  - Complete audit trail of stock movements
  - Change tracking with timestamps

### 🔐 Security & Access Control
- **Role-based Access Control**
  - Three-tier permission system
  - Secure authentication and authorization
- **User Management**
  - Admin-controlled user creation
  - Role assignment and management

## 👥 User Roles

### 🔑 Admin
- **Full system access**
- Manage products, categories, suppliers
- Manage purchase and sales orders
- View all reports and analytics
- Manage users and assign roles
- System configuration access

### 👨‍💼 Manager
- **Inventory and order management**
- Manage products and stock levels
- Create and process orders
- View reports and analytics
- **Cannot manage users**

### 👨‍💻 Staff
- **Limited operational access**
- Process purchase orders
- Process sales orders
- Update stock levels
- View assigned inventory sections

## 🛠️ Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA** - Database operations
- **Spring Security** - Authentication & authorization
- **Hibernate** - ORM framework
- **Maven** - Dependency management

### Frontend
- **Thymeleaf** - Server-side templating
- **HTML5** - Modern markup
- **CSS3** - Responsive styling
- **JavaScript** - Interactive features
- **Bootstrap** - UI components

### Database
- **MySQL 8.0+** - Primary database
- **HikariCP** - Connection pooling

### Email Service
- **Spring Mail** - Email notifications
- **Gmail SMTP** - Email delivery

## 📁 Project Structure

```
Inventory-Management-System/
│
├── src/main/java/com/kathir/Inventory/Management/System/
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── DashboardController.java
│   │   ├── ProductController.java
│   │   ├── CategoryController.java
│   │   ├── SupplierController.java
│   │   ├── PurchaseOrderController.java
│   │   ├── SalesOrderController.java
│   │   ├── ReportController.java
│   │   └── UserController.java
│   │
│   ├── service/
│   │   ├── ProductService.java
│   │   ├── CategoryService.java
│   │   ├── SupplierService.java
│   │   ├── PurchaseOrderService.java
│   │   ├── SalesOrderService.java
│   │   ├── EmailService.java
│   │   ├── StockLogService.java
│   │   └── CustomUserDetailsService.java
│   │
│   ├── repository/
│   │   ├── ProductRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── SupplierRepository.java
│   │   ├── PurchaseOrderRepository.java
│   │   ├── SalesOrderRepository.java
│   │   ├── StockLogRepository.java
│   │   └── UserRepository.java
│   │
│   ├── entity/
│   │   ├── Products.java
│   │   ├── Category.java
│   │   ├── Supplier.java
│   │   ├── PurchaseOrder.java
│   │   ├── SalesOrder.java
│   │   ├── StockLog.java
│   │   └── User.java
│   │
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── DataInitializer.java
│   │
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── ResourceNotFoundException.java
│
├── src/main/resources/
│   ├── templates/
│   │   ├── fragments/
│   │   ├── dashboard.html
│   │   ├── products/
│   │   ├── categories/
│   │   ├── suppliers/
│   │   ├── purchase-orders/
│   │   ├── sales-orders/
│   │   └── reports/
│   │
│   ├── static/
│   │   ├── css/
│   │   ├── js/
│   │   └── images/
│   │
│   └── application.properties
│
└── pom.xml
```

## 🚀 Installation and Setup

### Prerequisites
- **Java 17 or higher**
- **MySQL 8.0 or higher**
- **Maven 3.6+**
- **Git**

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/inventory-management-system.git
cd inventory-management-system
```

### 2. Database Setup
Create a MySQL database:
```sql
CREATE DATABASE inventory_db;
```

### 3. Configure Database
Update `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8081

# Thymeleaf Configuration
spring.thymeleaf.cache=false
```

### 4. Email Configuration (Optional)
For low stock email alerts, add to `application.properties`:
```properties
# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 5. Build the Project
```bash
mvn clean install
```

### 6. Run the Application
```bash
mvn spring-boot:run
```

### 7. Access the Application
Open your browser and navigate to:
```
http://localhost:8081
```

## 🔐 Default Login Credentials

The system creates default users on first startup:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Manager | manager | manager123 |
| Staff | staff | staff123 |

**⚠️ Important**: Change these default passwords in production!

## 📧 Email Configuration Setup

### For Gmail Users:
1. **Enable 2-Step Verification** in your Google Account
2. **Generate App Password**:
   - Go to Google Account Settings
   - Navigate to Security → App Passwords
   - Generate a new app password for "Mail"
   - Use this password in `spring.mail.password`

### Security Note
- **Never commit real credentials to version control**
- Use environment variables for production:
```bash
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-app-password
```

## 🎯 Key Features in Detail

### Automatic Stock Management
- **Sales orders automatically decrease stock**
- **Purchase orders increase stock when marked "Received"**
- **Real-time stock level updates**
- **Comprehensive audit trail**

### Smart Email Alerts
- **Triggers when stock falls below reorder level**
- **Detailed email with product information**
- **Configurable recipient addresses**
- **Error handling with fallback logging**

### Role-Based Security
- **Spring Security integration**
- **Method-level security**
- **Template-level access control**
- **Secure password encoding**

## 🧪 Testing the System

### Test Low Stock Alerts:
1. Create a product with reorder level = 5, quantity = 10
2. Create a sales order for 8 units (leaves 2 units)
3. Since 2 < 5 (reorder level), email alert will be sent
4. Check console logs for confirmation

### Test Role-Based Access:
1. Login as different user roles
2. Verify access restrictions are enforced
3. Test CRUD operations based on permissions

## 🔧 Configuration Options

### Database Configuration
```properties
# Connection Pool Settings
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5

# JPA Settings
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.jdbc.batch_size=20
```

### Security Configuration
```properties
# Session Management
server.servlet.session.timeout=30m
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.secure=true
```

## 🚀 Future Enhancements

- [ ] **Export reports to PDF/Excel**
- [ ] **Barcode/QR code inventory tracking**
- [ ] **Advanced analytics dashboard**
- [ ] **Mobile-responsive UI improvements**
- [ ] **REST API for third-party integrations**
- [ ] **Multi-warehouse support**
- [ ] **Advanced reporting with charts**
- [ ] **Automated reorder suggestions**
- [ ] **Integration with accounting systems**

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2026 Kathir G

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👨‍💻 Author

**Kathir G**
- **GitHub**: [@Kathir2911](https://github.com/Kathir2911)
- **Email**: kathirganesan11@gmail.com
- **Institution**: St. Joseph's College of Engineering
- **Program**: Computer Science Engineering

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Thymeleaf team for the templating engine
- MySQL team for the robust database system
- Bootstrap team for UI components
- All contributors and testers

---

⭐ **If you find this project helpful, please give it a star!** ⭐

For questions, issues, or suggestions, please open an issue on GitHub or contact the author directly.