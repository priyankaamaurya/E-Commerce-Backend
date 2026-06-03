#  Cartify – E-Commerce Backend System

Cartify is a scalable and secure backend system designed for an e-commerce application. It is built using Java, Spring Boot, and MySQL, following a layered architecture. The system supports complete e-commerce workflows such as user authentication, product management, cart operations, and order processing.

The project focuses on building secure REST APIs, clean architecture, and real-world backend development practices using Spring Boot ecosystem.

---

##  Project Objective

The main objective of Cartify is to design a backend system that can handle:

- Secure user authentication and authorization
- Product listing and management
- Cart functionality for users
- Order placement and processing
- Scalable REST API architecture

This project demonstrates real-world backend development skills using industry-standard technologies.

---

##  Key Features

###  Authentication & Security

- User registration and login system
- JWT (JSON Web Token) based authentication
- Role-based authorization (USER / ADMIN)
- Password encryption using BCrypt
- Stateless session handling
  
###  Product Management

- Add new products (Admin only)
- Update and delete products
- Fetch product details
- View all available products

### Cart Management

- Add products to cart
- Remove products from cart
- View user cart items
- Automatic price calculation

###  Order Management

- Place order from cart
- Order summary generation
- Order tracking structure (basic level)

---

##  System Architecture

The project follows a 3-layer architecture:

- Controller Layer → Handles API requests
- Service Layer → Contains business logic
- Repository Layer → Handles database operations

This ensures clean code separation and scalability.

---

##  Tech Stack

- Language: Java
- Framework: Spring Boot
- Security: Spring Security, JWT
- ORM: Spring Data JPA, Hibernate
- Database: MySQL
- Build Tool: Maven
- Testing Tools: Postman
- API Documentation: Swagger UI

---

##  Database Design

Main entities used:

- User
- Product
- Cart
- Order
- Role

Relationships:

- One User → Many Orders
- One User → One Cart
- Cart → Multiple Products

---

##  Authentication Flow

- User registers or logs in
- Server generates JWT token
- Token is sent to client
- Client includes token in every request header
- Server validates token before processing request

Example:
```
Authorization: Bearer <JWT_TOKEN>
```

---

##  API Endpoints

###  Auth APIs

- POST /api/auth/register → Register new user
- POST /api/auth/login → Login user
  
###  Product APIs

- GET /api/products → Get all products
- POST /api/products → Add product (Admin)
- PUT /api/products/{id} → Update product
- DELETE /api/products/{id} → Delete product
  
###  Cart APIs

- POST /api/cart/add → Add item to cart
- GET /api/cart → View cart
- DELETE /api/cart/remove/{id} → Remove item

###  Order APIs

- POST /api/orders/place → Place order
- GET /api/orders → View orders

---

##  API Testing

All APIs are tested using Postman:

- Verified authentication flow
- Tested CRUD operations
- Validated secure endpoints with JWT

---

##  API Documentation

Swagger UI is integrated for API documentation:
```
http://localhost:8080/swagger-ui/
```

It provides:

- API testing interface
- Request/response structure
- Endpoint descriptions

---

##  Project Structure
```
Cartify
 ┣ controller
 ┣ service
 ┣ repository
 ┣ model
 ┣ dto
 ┣ config
 ┣ security
 ┣ exception
 ┗ util
```

---

##  How to Run

1. Clone repository
```
git clone https://github.com/your-username/cartify.git
```

2. Configure MySQL in application.properties
3. Run project
```
mvn spring-boot:run
```

4. Open Swagger UI
```
http://localhost:8080/swagger-ui/
```
---

##  Future Enhancements

- Payment gateway integration (Razorpay/Stripe)
- Product search & filtering
- Pagination & sorting
- Email notifications
- Frontend integration (React.js)
- Microservices migration

---

##  Key Learnings

- Building secure REST APIs using Spring Boot
- Implementing JWT authentication from scratch
- Working with relational databases using JPA
- Designing scalable backend architecture
- API testing and documentation practices

---

##  Author

Priyanka Maurya

BCA Graduate | Java Full Stack Developer

GitHub: https://github.com/priyankaamaurya 

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and feel free to explore the code!
