# 🅿️ SkyPark - Sistem Management Parkir

![Java](https://img.shields.io/badge/Language-Java-orange)
![Platform](https://img.shields.io/badge/Platform-Desktop%20(Swing)-blue)
![License](https://img.shields.io/badge/License-Educational-green)
![Status](https://img.shields.io/badge/Status-Completed-success)

**SkyPark** adalah aplikasi desktop berbasis Java Swing yang dirancang untuk mempermudah manajemen parkir kendaraan. Aplikasi ini mengimplementasikan konsep **Object-Oriented Programming (OOP)** yang modular, validasi data yang ketat, serta penyimpanan data persisten menggunakan format CSV.

---

## 📖 Daftar Isi

- [Fitur Utama](#-fitur-utama)
- [Arsitektur & Struktur Kode](#-arsitektur--struktur-kode)
- [Teknologi yang Digunakan](#-teknologi-yang-digunakan)
- [Persyaratan Sistem](#-persyaratan-sistem)
- [Instalasi & Cara Menjalankan](#-instalasi--cara-menjalankan)
- [Panduan Penggunaan](#-panduan-penggunaan)
- [Format Penyimpanan Data](#-format-penyimpanan-data)
- [Tim Pengembang](#-tim-pengembang)

---

## ✨ Fitur Utama

### 🏠 1. Real-Time Dashboard
- Menampilkan **Total Kendaraan Parkir**.
- Menampilkan **Total Transaksi**.
- Menampilkan **Total Pendapatan** secara real-time.
- Menu statistik visual dengan desain modern.

### 📝 2. Check-In System (Registrasi Masuk)
- Input Plat Nomor dan Jenis Kendaraan (Motor, Mobil, Truk, Bus).
- **Smart Validation**: Sistem akan menolak input jika Plat Nomor duplikat (masih ada di parkiran).
- Kalkulasi tarif per jam otomatis berdasarkan jenis kendaraan.

### 📂 3. Data Management (CRUD)
- **Read**: Tabel data interaktif dengan fitur *Sorting* dan *Searching*.
- **Update**: Edit data kendaraan (Plat Nomor/Jenis) jika terjadi kesalahan input.
- **Delete**: Menghapus data dengan konfirmasi keamanan (*Alert Dialog*).

### 📤 4. Check-Out System (Transaksi Keluar)
- Menghitung durasi parkir secara otomatis.
- Mengkalkulasi total biaya akhir.
- Memindahkan status data dari "AKTIF" menjadi "SELESAI" (Masuk ke History).

### 💾 5. Data Persistence
- Data tersimpan permanen dalam file `parking_db.csv`.
- Data tidak hilang meskipun aplikasi ditutup dan dibuka kembali.

---

## 🛠 Tools

| Komponen | Tools                  |
|----------|------------------------|
| **Bahasa Pemrograman** | Java (JDK 17)  |
| **GUI Framework** | Java Swing (AWT)       |
| **Penyimpanan Data** | File Handling (CSV format) |
| **Version Control** | Git & GitHub           |
| **IDE** | IntelliJ IDEA  |

---

## 📋 Persyaratan Sistem

### Perangkat Lunak
- **Java Development Kit (JDK)**: Versi 17.
- **Sistem Operasi**: Windows 10/11, macOS, atau Linux.

---

## 🚀 Instalasi & Cara Menjalankan

### Metode 1: Menggunakan IDE (IntelliJ IDEA)
1. **Clone Repository** ini atau unduh sebagai ZIP.
   ```bash
   git clone https://github.com/B4RSKY/UAP_PEMLAN_Sistem_Penghitung_Biaya_Parkir.git

2. Buka IDE, pilih **Open Project** dan arahkan ke folder `UAP_PEMLAN_Sistem_Penghitung_Biaya_Parkir`.
3. Tunggu hingga indexing selesai.
4. Buka file `src/org/example/main/Main.java`.
5. Klik tombol **Run** ▶️.

### Metode 2: Menggunakan Terminal / CMD

Pastikan Anda berada di direktori `src`.

```bash
# 1. Compile semua file java
javac org/example/main/Main.java

# 2. Jalankan aplikasi
java org.example.main.Main

```

---

## 📚 Panduan Penggunaan

### 1️⃣ Melakukan Input Parkir (Check-In)

1. Buka menu **Input Masuk**.
2. Masukkan **Plat Nomor** (Contoh: B 412 SKY).
3. Pilih **Jenis Kendaraan**. Tarif per jam akan muncul otomatis.
4. Klik tombol **SIMPAN DATA**.
> *Catatan: Jika plat nomor sudah ada dan statusnya masih parkir, sistem akan menolak input.*



### 2️⃣ Mengelola Data & Update

1. Buka menu **Data Parkir**.
2. Gunakan kolom **Pencarian** untuk mencari plat nomor dengan cepat.
3. Untuk mengedit: Klik salah satu baris tabel, lalu klik tombol **Update Data**.
4. Ubah data yang diinginkan pada dialog yang muncul, lalu klik OK.

### 3️⃣ Melakukan Transaksi Keluar (Check-Out)

1. Buka menu **Data Parkir**.
2. Pilih kendaraan yang ingin keluar.
3. Klik tombol **Checkout / Keluar**.
4. Sistem akan menampilkan total biaya yang harus dibayar.
5. Data akan otomatis pindah ke menu **Riwayat Transaksi**.

---

## 💾 Format Penyimpanan Data

Data disimpan dalam file `parking_db.csv` yang dapat dibuka menggunakan Microsoft Excel atau Notepad.

**Format Kolom:**
`Plat Nomor, Jenis Kendaraan, Waktu Masuk, Waktu Keluar, Biaya, Status`

**Contoh Data:**

```csv
B 412 SKY,Mobil,2025-12-25 18:38:32,null,0.0,AKTIF
B 4 RY,Bus,2025-12-25 18:39:19,null,0.0,AKTIF
R 41 MU,Truk,2025-12-25 18:39:49,2025-12-25 18:40:59,10000.0,SELESAI
S 4444 TU,Motor,2025-12-25 18:41:32,2025-12-25 18:42:05,2000.0,SELESAI

```

---

## 👨‍💻 Tim Pengembang

Proyek ini dikembangkan untuk memenuhi Tugas Ujian Akhir Praktikum Mata Kuliah **Pemrograman Lanjut**.

<div align="center">

| Nama Mahasiswa                      | NIM             |
|-------------------------------------|-----------------|
| **Mohammad Putra Akbar Rafsanjani** | 202410370110311 |
| **Ahmad Barry Mahardika**                    | 202410370110310 |

**Universitas Muhammadiyah Malang** Fakultas Teknik - Informatika

2025

</div>