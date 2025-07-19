# Trade Note - Aplikasi Pencatat Trading

Trade Note adalah aplikasi Android yang dirancang untuk membantu pengguna mencatat aktivitas trading mereka (profit/loss). Aplikasi ini menggunakan SQLite untuk penyimpanan data trading lokal.

## Fitur Utama

* **Pencatatan Trading**: Catat jumlah trading, profit, dan loss dengan tanggal spesifik.

* **Manajemen Data Trading**: Lihat, edit, dan hapus catatan trading yang ada.

* **Sistem Otentikasi Sederhana**: Login dan registrasi pengguna menggunakan SQLite.

* **Tampilan Chart**: Fitur untuk melihat chart .

## Teknologi yang Digunakan

* **Bahasa Pemrograman**: Java

* **Platform**: Android

* **Database Lokal**: SQLite

* **Komponen UI**:

  * `RecyclerView` untuk menampilkan daftar trading.

  * `CardView` untuk tampilan item yang menarik.

  * `FloatingActionButton` untuk menambahkan catatan baru.

  * `Material Components` untuk desain UI modern.

## Struktur Proyek

* `app/src/main/java/com/example/tradenote/`:

  * `LoginActivity.java`: Activity untuk login dan registrasi pengguna.

  * `MainActivity.java`: Activity utama yang menampilkan daftar catatan trading.

  * `AddEditTradeActivity.java`: Activity untuk menambah atau mengedit catatan trading.

  * `ChartActivity.java`: Activity placeholder untuk tampilan chart.

  * `User.java`: Model data untuk objek pengguna.

  * `Trade.java`: Model data untuk objek catatan trading.

  * `UserDatabaseHelper.java`: Helper SQLite untuk operasi database pengguna.

  * `TradeDatabaseHelper.java`: Helper SQLite untuk operasi database trading.

  * `TradeAdapter.java`: Adapter untuk `RecyclerView` catatan trading.

* `app/src/main/res/layout/`: Berisi semua file layout XML untuk Activity dan item RecyclerView.

* `app/src/main/res/menu/`: Berisi file menu XML (misalnya, `main_menu.xml`).

* `app/build.gradle (Module: app)`: File konfigurasi Gradle yang berisi dependensi proyek.

## Persyaratan Sistem

* Android Studio

* Android SDK (API Level 24 atau lebih tinggi direkomendasikan)

## Cara Mengatur dan Menjalankan Proyek

1. **Kloning Repositori (Jika ada)**:

   ```bash
   git clone https://github.com/Aril-Man/Trading-Note
   cd Trading-Note
   ```

   Jika Anda hanya memiliki file, cukup buka proyek di Android Studio.

2. **Buka di Android Studio**:

   * Buka Android Studio.

   * Pilih `File` > `Open` dan navigasikan ke direktori proyek `Trade-Note`.

3. **Instal Dependensi**:
   Pastikan Anda telah menambahkan dependensi berikut di file `build.gradle (Module: app)` Anda:

   ```gradle
   dependencies {
       // ... dependensi yang sudah ada ...
       implementation 'androidx.cardview:cardview:1.0.0' // Pastikan ini ada
       implementation 'androidx.recyclerview:recyclerview:1.3.0' // Pastikan ini ada
       implementation 'com.google.android.material:material:1.12.0' // Pastikan ini ada
   }
   ```

   Kemudian, sinkronkan proyek Gradle.

4. **Jalankan Aplikasi**:

   * Hubungkan perangkat Android Anda atau mulai emulator.

   * Klik tombol `Run` (ikon panah hijau) di Android Studio.

## Penggunaan Aplikasi

1. **Login/Registrasi**: Saat pertama kali membuka aplikasi, Anda akan diarahkan ke layar login. Anda bisa mendaftar akun baru jika belum punya.

2. **Layar Utama (Catatan Trading)**: Setelah login, Anda akan melihat daftar catatan trading Anda.

   * Klik tombol `+` (Floating Action Button) untuk menambahkan catatan trading baru.

   * Klik ikon pensil untuk mengedit catatan.

   * Klik ikon tempat sampah untuk menghapus catatan.

3. **Logout**:

   * Klik ikon menu (tiga titik vertikal) di pojok kanan atas layar utama.

   * Pilih "Logout" untuk keluar dari akun Anda.
  
4. **Lihat Chart**
   * Klik ikon menu (tiga titik vertikal) di pojok kanan atas layar utama.
   * Pilih Lihat Chart

## Pengembangan Lebih Lanjut

* Sinkronisasi Cloud: Menambahkan fitur backup dan sinkronisasi data ke cloud (misalnya menggunakan Firebase) agar data tidak hilang dan bisa diakses dari beberapa perangkat.
* Analisis & Statistik: Membuat halaman dasbor yang menampilkan statistik performa trading, seperti win rate, average profit, average loss, dan risk-to-reward ratio.
* Mode Gelap (Dark Mode): Menambahkan dukungan tema gelap untuk kenyamanan pengguna.
* Integrasi Berita Finance : Menambahkan halaman untuk melihat berita terkait finance


## Screenshot Hasil
<img width="264" height="584" alt="image" src="https://github.com/user-attachments/assets/26ce2528-5f74-48a4-b9e2-44bdc756f238" />

<img width="261" height="578" alt="image" src="https://github.com/user-attachments/assets/478c329b-5779-4038-80e7-2befa636ebb9" />

<img width="281" height="584" alt="image" src="https://github.com/user-attachments/assets/c15cfe52-81a3-4cfc-85ec-a5aff1173d18" />

<img width="306" height="669" alt="image" src="https://github.com/user-attachments/assets/8f89225a-10ff-4751-b4d1-9f23ce776525" />



