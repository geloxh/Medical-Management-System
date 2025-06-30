# Medical Management System v2.0

A professional pharmacy inventory management system built with Java Swing and MySQL.

## Features

### 🏥 Core Functionality
- **Product Management**: Add, edit, delete, and search medical products
- **Supplier Management**: Manage supplier information and contacts
- **Purchase Management**: Handle product purchases with cart functionality
- **Sales Records**: View and export sales data with reporting

### 🎨 Modern UI/UX
- Clean, professional tabbed interface
- Responsive design with proper layouts
- Color-coded buttons and status indicators
- Modern dialog boxes with validation

### 🔧 Technical Improvements
- **MVC Architecture**: Proper separation of concerns
- **Database Integration**: MySQL with connection pooling
- **Error Handling**: Comprehensive exception handling
- **Data Validation**: Input validation and sanitization
- **Export Functionality**: CSV export for reports

## Project Structure

```
src/main/java/com/medical/
├── Main.java                 # Application entry point
├── model/                    # Data models
│   ├── Product.java
│   └── Supplier.java
├── ui/                       # User interface components
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── ProductPanel.java
│   ├── ProductDialog.java
│   ├── SupplierPanel.java
│   ├── SupplierDialog.java
│   ├── PurchasePanel.java
│   └── SalesPanel.java
└── util/                     # Utility classes
    └── DatabaseConnection.java
```

## Setup Instructions

### Prerequisites
- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Install MySQL and create a database named `medical_system`
2. Update database credentials in `DatabaseConnection.java` if needed
3. Tables will be created automatically on first run

### Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run: `mvn clean compile exec:java -Dexec.mainClass="com.medical.Main"`
4. Or build JAR: `mvn clean package` then `java -jar target/medical-management-system-2.0.0.jar`

### Default Login
- Username: `admin`
- Password: `admin`

## Key Improvements from v1.0

### Code Quality
- ✅ Proper package structure and naming conventions
- ✅ Separation of concerns with MVC pattern
- ✅ Consistent error handling and validation
- ✅ Modern Java practices and coding standards

### User Experience
- ✅ Professional login screen
- ✅ Tabbed interface for better navigation
- ✅ Intuitive dialogs for data entry
- ✅ Real-time search and filtering
- ✅ Export functionality for reports

### Database Design
- ✅ Proper table relationships and constraints
- ✅ Auto-increment primary keys
- ✅ Timestamp tracking for sales
- ✅ Connection pooling and resource management

### Security & Reliability
- ✅ SQL injection prevention with PreparedStatements
- ✅ Input validation and sanitization
- ✅ Proper resource cleanup
- ✅ Exception handling with user-friendly messages

## Future Enhancements
- User role management and authentication
- Inventory alerts for low stock
- Advanced reporting and analytics
- Barcode scanning integration
- Multi-location support

## License
This project is licensed under the MIT License.
