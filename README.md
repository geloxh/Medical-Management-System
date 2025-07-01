<div align="center">

# 🏥 Medical Management System

### *Professional Pharmacy Inventory Management Solution*

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

*Streamline your pharmacy operations with our comprehensive inventory management system*

</div>

---

## 🚀 **Overview**

The Medical Management System is a cutting-edge pharmacy inventory solution designed for healthcare professionals. Built with modern Java technologies and MySQL database, it provides a robust platform for managing pharmaceutical inventory, suppliers, and sales operations.

## ✨ **Key Features**

### 🏥 **Core Modules**
| Module | Description | Key Features |
|--------|-------------|-------------|
| **Products** | Comprehensive product management | Add, Edit, Delete, Search, Print Reports |
| **Suppliers** | Vendor relationship management | Contact management, Product tracking |
| **Purchase** | Smart purchasing system | Cart functionality, Real-time pricing |
| **Sales** | Advanced sales tracking | Export to CSV, Print reports, Analytics |
| **Users** | Multi-user authentication | Account creation, Secure login |

### 🎨 **Modern Interface**
- **Tabbed Navigation**: Intuitive multi-panel interface
- **Professional Design**: Clean, modern UI with color-coded actions
- **Responsive Dialogs**: Smart forms with real-time validation
- **Print Integration**: Professional report printing capabilities
- **Export Functions**: CSV export for data analysis

### 🔒 **Security & Reliability**
- **SQL Injection Protection**: Parameterized queries throughout
- **User Authentication**: Secure login with database integration
- **Data Validation**: Comprehensive input sanitization
- **Error Handling**: Graceful error management with user feedback

## 🏗️ **Architecture**

```
📁 medical-management-system/
├── 📁 src/main/
│   ├── 📁 java/com/medical/
│   │   ├── 🚀 Main.java
│   │   ├── 📁 model/          # Data Models
│   │   │   ├── Product.java
│   │   │   └── Supplier.java
│   │   ├── 📁 ui/             # User Interface
│   │   │   ├── LoginFrame.java
│   │   │   ├── MainFrame.java
│   │   │   ├── ProductPanel.java
│   │   │   ├── SupplierPanel.java
│   │   │   ├── PurchasePanel.java
│   │   │   └── SalesPanel.java
│   │   └── 📁 util/           # Utilities
│   │       ├── DatabaseConnection.java
│   │       └── ReportPrinter.java
│   └── 📁 resources/images/   # UI Assets
├── 📁 docs/                   # Documentation
├── 🔧 pom.xml                # Maven Configuration
└── 📋 README.md              # This file
```

## 🛠️ **Quick Start**

### **Prerequisites**
```bash
☑️ Java 11 or higher
☑️ MySQL 8.0 or higher  
☑️ Maven 3.6 or higher
```

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/medical-mgt-system.git
   cd medical-mgt-system
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE medical_system;
   -- Tables are auto-created on first run
   ```

3. **Run the application**
   ```bash
   # Quick start (Windows)
   run.bat
   
   # Or using Maven
   mvn clean compile exec:java -Dexec.mainClass="com.medical.Main"
   
   # Build standalone JAR
   mvn clean package
   java -jar target/medical-management-system-2.0.0.jar
   ```

### **Default Credentials**
```
Username: admin
Password: admin
```

## 📊 **System Capabilities**

<div align="center">

| Feature | Status | Description |
|---------|--------|--------------|
| 🏥 Product Management | ✅ Complete | Full CRUD operations with search |
| 🏢 Supplier Management | ✅ Complete | Vendor information and tracking |
| 🛒 Purchase System | ✅ Complete | Cart-based purchasing workflow |
| 📈 Sales Analytics | ✅ Complete | Comprehensive sales reporting |
| 🖨️ Print Reports | ✅ Complete | Professional report printing |
| 📤 Data Export | ✅ Complete | CSV export functionality |
| 👥 User Management | ✅ Complete | Multi-user authentication |
| 🔍 Advanced Search | ✅ Complete | Real-time search across modules |

</div>

## 🎯 **Technology Stack**

- **Frontend**: Java Swing with modern UI components
- **Backend**: Java 11+ with MVC architecture
- **Database**: MySQL 8.0+ with optimized queries
- **Build Tool**: Maven for dependency management
- **Design Pattern**: Model-View-Controller (MVC)
- **Security**: PreparedStatements, Input validation

## 🚀 **What's New in v2.0**

### **🎨 User Experience**
- Modern tabbed interface design
- Professional login screen with account creation
- Color-coded action buttons for intuitive navigation
- Real-time data validation and feedback

### **🔧 Technical Enhancements**
- Complete MVC architecture implementation
- Enhanced database schema with proper relationships
- Comprehensive error handling and logging
- Print functionality for all reports

### **📊 Business Features**
- Advanced purchase management with cart system
- Export capabilities for data analysis
- Multi-user support with secure authentication
- Professional report generation

## 🤝 **Contributing**

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 **Support**

For support and questions:
- 📧 Email: support@medical-system.com
- 🐛 Issues: [GitHub Issues](https://github.com/your-username/medical-mgt-system/issues)
- 📖 Documentation: [Wiki](https://github.com/your-username/medical-mgt-system/wiki)

---

<div align="center">

**develop by geloxh**

*Empowering pharmacies with modern technology*

</div>
