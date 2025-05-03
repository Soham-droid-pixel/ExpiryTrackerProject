# 📆 Expiry Tracker - Document Expiry Reminder System

A Spring Boot application that tracks the expiry dates of important documents and sends timely email reminders to users. It helps individuals avoid missing important renewal deadlines like PAN, Aadhaar, license, insurance, etc.

---

## 📌 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [How It Works](#how-it-works)
- [Getting Started](#getting-started)
- [Why This Project](#why-this-project)
- [Future Enhancements](#future-enhancements)
- [Advantages Over DigiLocker and Calendars](#advantages-over-digilocker-and-calendars)
- [Author](#author)

---

## ✅ Features

- 🔔 Email reminders before document expiry
- 🕒 Daily scheduled checks using Spring Scheduler
- ✉️ Gmail SMTP integration for sending alerts
- 🗃️ PostgreSQL for storing users, documents, and reminders
- 📅 Automatically tracks all expiry dates

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot
- **Database**: PostgreSQL (stores user info, documents, and reminders)
- **Email Service**: JavaMailSender (Gmail SMTP)
- **Scheduling**: Spring Scheduler (`@EnableScheduling`)
- **IDE**: Eclipse IDE
- **Build Tool**: Maven

---

## ⚙️ How It Works

1. User adds document details and expiry date.
2. Data is stored securely in a PostgreSQL database.
3. Spring Scheduler runs daily to check for upcoming expiries.
4. Email is sent to the user as a reminder.

---

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd expiry-tracker
````

2. **Configure Gmail in `MailConfig.java`**

   ```java
   mailSender.setUsername("your-email@gmail.com");
   mailSender.setPassword("your-app-password");
   ```

3. **Set up PostgreSQL**

   * Create a database (e.g., `expiry_tracker`)
   * Update `application.properties`:

     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/expiry_tracker
     spring.datasource.username=your_db_user
     spring.datasource.password=your_db_password
     ```

4. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

---

## 💬 Why This Project?

* Helps users remember renewal dates.
* Calendar apps require manual entry and don’t auto-alert.
* DigiLocker stores documents but doesn’t notify users before expiry.
* This project fills that gap with smart reminders and tracking.

---

## 🔮 Future Enhancements

* 📎 Document upload feature
* 📊 User dashboard to view all expiry data
* 🔐 Admin/user login roles
* 📱 Mobile/Web app integration
* ⏰ Custom notification intervals

---

## 🆚 Advantages Over DigiLocker and Calendars

| Feature                   | Expiry Tracker ✅ | DigiLocker ❌ | Calendar ❌ |
| ------------------------- | ---------------- | ------------ | ---------- |
| Expiry Reminders          | ✅                | ❌            | ✅ (manual) |
| Auto Email Alerts         | ✅                | ❌            | ❌          |
| PostgreSQL Data Storage   | ✅                | ✅            | ❌          |
| Smart Notification System | ✅ (Planned)      | ❌            | ❌          |
| Document Upload           | ✅ (Planned)      | ✅            | ❌          |

---

## 👨‍💻 Author

**Soham Kalgutkar**
📧 Email: [sohamkalg@gmail.com](mailto:sohamkalg@gmail.com)

