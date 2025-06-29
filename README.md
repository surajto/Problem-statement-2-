# Hospital Management System (HMS)

## 📌 Project Overview
The **Hospital Management System (HMS)** is a web-app application designed to manage doctor appointments efficiently. It provides a platform where **patients** can book appointments, **doctors** can see thier schedules, and **admins** can oversee the entire system.

## ⚙️ Features
- **Admin Panel**: Manage doctors,Manages slots, monitor appointments and patients.
- **Doctor Management**: Doctors can view booked appointments.
- **Patient Management**: Patients can book and view thier appointments.

## 🏛 Database Structure
### **1️⃣ Admins Table**
Stores admin login details.
```sql
desc admins;
```
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| admin_id | int          | NO   | PRI | NULL    | auto_increment |
| name     | varchar(100) | NO   |     | NULL    |                |
| password | varchar(255) | NO   |     | NULL    |                |
+----------+--------------+------+-----+---------+----------------+

### **2️⃣ Doctors Table**
Stores doctor details.
```sql
desc doctors;
```
+----------------+--------------+------+-----+---------+----------------+
| Field          | Type         | Null | Key | Default | Extra          |
+----------------+--------------+------+-----+---------+----------------+
| doctor_id      | int          | NO   | PRI | NULL    | auto_increment |
| name           | varchar(100) | NO   |     | NULL    |                |
| specialization | varchar(100) | NO   |     | NULL    |                |
| email          | varchar(100) | NO   | UNI | NULL    |                |
| phone          | varchar(15)  | NO   | UNI | NULL    |                |
| password       | varchar(255) | NO   |     | NULL    |                |
+----------------+--------------+------+-----+---------+----------------+

### **3️⃣ Patients Table**
Stores patient details.
```sql
desc patients;
```
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| patient_id | int          | NO   | PRI | NULL    | auto_increment |
| name       | varchar(100) | NO   |     | NULL    |                |
| email      | varchar(100) | NO   | UNI | NULL    |                |
| phone      | varchar(15)  | NO   | UNI | NULL    |                |
| password   | varchar(255) | NO   |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+

### **4️⃣ Doctor Slots Table**
Tracks doctor availability.
```sql
desc doctor_slots;
```
mysql> desc doctor_slots;
+-----------+------------+------+-----+---------+----------------+
| Field     | Type       | Null | Key | Default | Extra          |
+-----------+------------+------+-----+---------+----------------+
| slot_id   | int        | NO   | PRI | NULL    | auto_increment |
| doctor_id | int        | NO   | MUL | NULL    |                |
| slot_time | datetime   | NO   |     | NULL    |                |
| is_booked | tinyint(1) | YES  |     | 0       |                |
+-----------+------------+------+-----+---------+----------------+

### **5️⃣ Appointments Table**
Manages appointment bookings.
```sql
desc appointments;
```
+------------------+-----------------------------------------------------+------+-----+-----------+----------------+
| Field            | Type                                                | Null | Key | Default   | Extra          |
+------------------+-----------------------------------------------------+------+-----+-----------+----------------+
| appointment_id   | int                                                 | NO   | PRI | NULL      | auto_increment |
| patient_id       | int                                                 | NO   | MUL | NULL      |                |
| doctor_id        | int                                                 | NO   | MUL | NULL      |                |
| appointment_date | datetime                                            | NO   |     | NULL      |                |
| status           | enum('Scheduled','Accepted','Rejected','Completed') | YES  |     | Scheduled |                |
+------------------+-----------------------------------------------------+------+-----+-----------+----------------+

### **3️⃣ Import Tables**
```sql
-- Run SQL schema file to create tables
SOURCE schema.sql;
```

### **4️⃣ Insert Sample Data**
```sql
-- Run sample data file
SOURCE sample_data.sql;
```

## 🔄 Workflow
1️⃣ **Admin** adds doctors and moniters appointments and manages slots.
2️⃣ **Doctors** monitors the appointment.
3️⃣ **Patients** register and book appointments.

## 📞 Contact
For any queries, reach out to `surajtomar9309@gmail.com`.
---



