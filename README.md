# CariKerja
Carikerja adalah aplikasi console lowongan pekerjaan yang dibuat dengan Java dan MySQL. Aplikasi ini dibuat untuk memenuhi tugas PBO.


https://user-images.githubusercontent.com/87528336/150668327-4883e41b-0c1b-4aa5-97bd-591f9c3a742a.mp4

# Fitur
- Login, Register, dan Logout
- Menampilkan daftar lowongan pekerjaan
- Menampilkan detail lowongan pekerjaan
- Mencari lowongan pekerjaan
- Menampilkan lowongan pekerjaan berdasarkan kategori
- Buat, ubah, hapus dan menampilkan kategori
- Hapus, ubah dan menampilkan user

# Menjalankan Aplikasi
Untuk menjalankan aplikasi, pastikan anda telah menginstal komponen yang dibutuhkan:
- JDK 11
- Apache Maven
- MySQL

## Dengan IntelliJ
1. Clone repository ini dengan cara:
    ```bash
    $ git clone https://github.com/10120128/CariKerja
   ```
2. Copy file `.env.example` ke `.env`
3. Lengkapi file `.env` dengan data yang diperlukan
4. Buka project CariKerja di IntelliJ
5. Jalankan aplikasi

## Cara manual
1. Clone repository ini dengan cara:
    ```bash
    $ git clone https://github.com/10120128/CariKerja 
    ```
2. Install dependency maven dengan cara:
    ```bash
    $ mvn clean install
    ```
3. Copy file `.env.example` ke `.env`
4. Lengkapi file `.env` dengan data yang diperlukan
5. Jalankan aplikasi

# Anggota Kelompok
10120128, 10120143, 10120155

# Lisensi
[Apache License 2.0](LICENSE)
## Library
- [ormlite](https://ormlite.com/) ISC
- [text-io](https://github.com/beryx/text-io) Apache License v2.0
- [ascii-table](https://github.com/freva/ascii-table) MIT
- [java-dotenv](https://github.com/cdimascio/dotenv-java) Apache License v2.0
- [slf4j](https://www.slf4j.org/) MIT