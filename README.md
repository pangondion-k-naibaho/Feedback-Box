# <center>Feedback Box</center>

<p>
  Merupakan Aplikasi yang berfungsi untuk mengolah data feedback
</p>

## <u>Requirement</u>

Sebelum memulai, pastikan anda memiliki hal-hal berikut :
- **Android Studio**: Versi terbaru atau versi yang direkomendasikan
- **JDK** : Versi 17 atau lebih baru
- **Android SDK** : Minimum : SDK 31 (Android 12)
- **Perangkat Smartphone Android** : dengan minimum versi Android yakni Android 12

## <u>Cara Instalasi Aplikasi via Android Studio</u>
Berikut langkah-langkah untuk install dan menjalankan aplikasi :

1. **Clone Repository** :
   ```bash
   https://github.com/pangondion-k-naibaho/Feedback-Box.git
2. **Buka Project di Android Studio** :
   - Pilih "Open an existing Android Studio Project" dan arahkan ke folder project yang baru saja di-clone
3. **Sync Gradle** :
   - Klik "Sync Project with Gradle Files" di toolbar Android Studio untuk mengunduh dependensi yang diperlukan
4. **Konfigurasi Emulator atau Perangkat** :
   - Pilih emulator atau sambungkan perangkat Android untuk pengujian.

##<u>Cara Instalasi Aplikasi secara Langsung ke Perangkat Android</u>
1. **Unduh APK** :
   - APK Feedback Box versi terakhir dapat diunduh di sini : https://bit.ly/4093SVc
2. **Install APK**
   - Simpan file .apk ini ke direktori lokal device Android anda
   - Buka file .apk dan ikuti petunjuk dari Android untuk instalasi
   - Setelah terinstall, silahkan jalankan aplikasi. Pastikan perangkat masih memiliki penyimpanan storage yang tersisa, dikarenakan aplikasi ini menyimpan berbagai data hasil interaksi secara lokal

## <u>Cara Build APK</u>
Untuk membangun APK dari project, ikuti langkah-langkah berikut :
1. Buka Android Studio
2. Pilih **Build** dari menu bagian atas
3. Pilih **Build Bundle(s) / APK(s)** :
   - Klik **Build APK(s)** untuk menghasilkan file APK
4. Temukan APK :
   - Setelah proses selesai, APK dapat ditemukan di 'direktori_penyimpanan_project/app/build/outputs/apk/debug/'
  
## <u>Struktur Aplikasi</u>
<b>1. 'src/main/java'</b>
- Merupakan tempat kode sumber Kotlin. terdiri atas beberapa paket seperti :
  - '**com.goods.client**': Package utama aplikasi
     - 'data' : berfungsi untuk penanganan data yang diperlukan untuk berjalannya aplikasi, berisikan model, remote, repository
     -  'ui' : berfungsi untuk penampilan data yang diperoleh dari komunikasi via WebService, bersisikan activities, custom component & adapter, dan viewmodel
  - '**res**' : Berisi resource yang mendukung pembuatan ui aplikasi, yang berisikan anim, drawable, font, layout, menu, mipmap, values, dan xml.
 
## <u>Fitur Aplikasi</u>
Aplikasi ini terdiri atas empat fitur sederhana, yakni :
1. **Read Feedback** :
   - Fitur ini terlihat pertama kali setelah **Splash Screen**. Di sini terjadi penampilan data Feedback jika memang sudah ditambahkan feedback, apabila belum, maka akan ada pemberitahuan kalau feedback belum ada
2. **Add Feedback** :
   - Fitur ini bisa diakses dari icon menu '+' di sebelah kiri Toolbar teratas. Kita dapat mengisi data data yang dibutuhkan di form tersebut
3. **Update Feedback** :
   - Fitur ini bisa diakses dari icon pensil di setiap item feedback. Kita bisa mengedit data feedback dari form yang mirip dengan form **Add Feedback**
4. **Delete Feedback** :
   - Fitur delete ini ada bersamaan di form **Update Feedback**. Jika kita memang ingin menghapus data feedback tersebut, kita bisa menghapusnya
