# 🏥 MediLux Project — Team Work Allocation (6 Members)

මෙම ලේඛනය මඟින් කණ්ඩායමේ සාමාජිකයින් 6 දෙනා අතර ව්‍යාපෘතියේ වැඩ බෙදී යන ආකාරය (කෙනෙකුට UI 2ක් සහ CRUD 3ක් ලැබෙන පරිදි) දක්වා ඇත.

> **Note:** ව්‍යාපෘතිය දැන් **Spring Data JPA Repositories** භාවිතා කරන බැවින් DAO ෆෝල්ඩරය ඉවත් කර ඒ වෙනුවට `repository` package එක භාවිතා වේ. අලුතින් `admin/messages.html` ද එකතු කර ඇත.

---

## 👨‍💻 Member 1: Authentication & User Management
**UI Components (2):**
1. `login.html` (පරිශීලක පිවිසුම)
2. `register.html` (නව ගිණුම් සෑදීම)

**CRUD Operations (3):**
1. **CREATE:** New User Registration (`UserController.register`)
2. **READ:** User Login Authentication (`UserController.login`)
3. **DELETE:** Delete User Account (`UserController.deleteUser`)

**Files Involved:**
- `src/main/resources/static/login.html`
- `src/main/resources/static/register.html`
- `src/main/java/com/medilux/controller/UserController.java`
- `src/main/java/com/medilux/repository/UserRepository.java`

---

## 👨‍💻 Member 2: Home, About & Profile Updates
**UI Components (2):**
1. `index.html` (මුල් පිටුව)
2. `about.html` (ආයතනය පිළිබඳව)

**CRUD Operations (3):**
1. **READ:** Get User details by ID (`UserController.getUserById`)
2. **UPDATE:** Update User Profile Name & Email (`UserController.updateProfile`)
3. **UPDATE:** Change User Password (`UserController.updatePassword`)

**Files Involved:**
- `src/main/resources/static/index.html`
- `src/main/resources/static/about.html`
- `src/main/java/com/medilux/controller/UserController.java`
- `src/main/java/com/medilux/repository/UserRepository.java`

---

## 👨‍💻 Member 3: Product Catalog & Admin Products
**UI Components (2):**
1. `products.html` (Customer side භාණ්ඩ පෙන්වීම)
2. `admin/products.html` (Admin side භාණ්ඩ කළමනාකරණය)

**CRUD Operations (3):**
1. **CREATE:** Add New Product (`ProductController.addProduct`)
2. **READ:** Get All Products & Search (`ProductController.getAllProducts`, `searchProducts`)
3. **DELETE:** Delete Product (`ProductController.deleteProduct`)

**Files Involved:**
- `src/main/resources/static/products.html`
- `src/main/resources/static/admin/products.html`
- `src/main/java/com/medilux/controller/ProductController.java`
- `src/main/java/com/medilux/repository/ProductRepository.java`

---

## 👨‍💻 Member 4: Shopping Cart & Order Tracking
**UI Components (2):**
1. `cart.html` (මිලදී ගැනීමේ කරත්තය)
2. `track-order.html` (ඇණවුම් තත්ත්වය පරීක්ෂා කිරීම)

**CRUD Operations (3):**
1. **CREATE:** Place Order/Checkout (`OrderController.placeOrder`)
2. **UPDATE:** Reduce Product Stock after checkout (`ProductController.updateStock`)
3. **READ:** Track Order by ID (`OrderController.trackOrder`)

**Files Involved:**
- `src/main/resources/static/cart.html`
- `src/main/resources/static/track-order.html`
- `src/main/java/com/medilux/controller/OrderController.java`
- `src/main/java/com/medilux/controller/ProductController.java`

---

## 👨‍💻 Member 5: Contact System & Admin Messages
**UI Components (2):**
1. `contact.html` (Customer side පණිවිඩ යැවීමේ පිටුව)
2. `admin/messages.html` (Admin side පණිවිඩ කියවීමේ පිටුව)

**CRUD Operations (3):**
1. **CREATE:** Submit new contact message (`ContactController.submitContact`)
2. **READ:** View all messages in admin portal (`ContactController.getAllContacts`)
3. **UPDATE:** Mark message as Read/Replied (`ContactController.updateStatus`)

**Files Involved:**
- `src/main/resources/static/contact.html`
- `src/main/resources/static/admin/messages.html`
- `src/main/java/com/medilux/controller/ContactController.java`
- `src/main/java/com/medilux/repository/ContactRepository.java`

---

## 👨‍💻 Member 6: Admin Dashboard & Order Management
**UI Components (3):**
1. `admin/dashboard.html` (ප්‍රධාන පාලන පුවරුව)
2. `admin/orders.html` (සියලු ඇණවුම් බැලීම)
3. `admin/customers.html` (සියලු පාරිභෝගිකයින් බැලීම)

**CRUD Operations (3):**
1. **READ:** Get All Orders & Filter by status (`OrderController.getAllOrders`, `getOrdersByStatus`)
2. **UPDATE:** Update Order Status (Pending -> Shipped -> Delivered) (`OrderController.updateStatus`)
3. **READ:** Get All Customers/Users (`UserController.getAllUsers`)

**Files Involved:**
- `src/main/resources/static/admin/dashboard.html`
- `src/main/resources/static/admin/orders.html`
- `src/main/resources/static/admin/customers.html`
- `src/main/java/com/medilux/controller/OrderController.java`
- `src/main/java/com/medilux/controller/UserController.java`

---

## 📁 Complete File Tree (Updated for JPA)

```
New folder (2)/
├── pom.xml                          
└── src/main/
    ├── resources/
    │   ├── application.properties   
    │   └── static/
    │       ├── index.html           ← Member 2
    │       ├── about.html           ← Member 2
    │       ├── login.html           ← Member 1
    │       ├── register.html        ← Member 1
    │       ├── products.html        ← Member 3
    │       ├── track-order.html     ← Member 4
    │       ├── contact.html         ← Member 5
    │       ├── cart.html            ← Member 4
    │       ├── js/
    │       │   └── tailwind.js
    │       └── admin/
    │           ├── dashboard.html   ← Member 6
    │           ├── products.html    ← Member 3
    │           ├── orders.html      ← Member 6
    │           ├── customers.html   ← Member 6
    │           ├── messages.html    ← Member 5
    │           └── index.html       ← Redirect
    └── java/com/medilux/
        ├── MediluxApplication.java  
        ├── model/
        │   ├── User.java            
        │   ├── Product.java         
        │   ├── Order.java           
        │   ├── CartItem.java
        │   └── Contact.java         
        ├── repository/              (New JPA Repositories)
        │   ├── UserRepository.java         
        │   ├── ProductRepository.java      
        │   ├── OrderRepository.java        
        │   └── ContactRepository.java      
        ├── controller/
        │   ├── UserController.java  
        │   ├── ProductController.java 
        │   ├── OrderController.java 
        │   └── ContactController.java 
        └── util/
            └── DBConnection.java    (Not needed anymore with JPA)
```
