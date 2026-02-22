# NewsFeed Simulator

Aplikasi Android simulasi news feed menggunakan Kotlin, Jetpack Compose, dan Coroutines.

## Cara Menjalankan

1. Clone repository ini
2. Buka project menggunakan **Android Studio**
3. Pastikan sudah menginstall SDK Android (min SDK 24)
4. Jalankan aplikasi di emulator atau perangkat fisik via tombol **Run**

## Fitur

### 1. Flow — Simulasi Data Berita Setiap 2 Detik
`NewsRepository` menggunakan `flow {}` untuk men-emit berita baru setiap 2 detik secara terus-menerus, mensimulasikan data yang datang dari server.

```kotlin
fun getNewsStream(): Flow<News> = flow {
    while (true) {
        delay(2000)
        emit(News(...))
    }
}
```

### 2. Filter Berita Berdasarkan Kategori
Pengguna dapat memfilter berita berdasarkan kategori **Tech**, **Sports**, atau **Finance**. Filter diterapkan di `NewsViewModel` menggunakan operator `filter()`.

```kotlin
list.filter { category == null || it.category == category }
```

### 3. Transform Data ke Format yang Ditampilkan
Sebelum ditampilkan ke UI, judul berita ditransformasi menjadi huruf kapital menggunakan operator `map()`.

```kotlin
.map { news -> news.copy(title = news.title.uppercase()) }
```

### 4. StateFlow — Menyimpan Jumlah Berita yang Sudah Dibaca
Jumlah berita yang dibaca disimpan menggunakan `MutableStateFlow` dan bertahan selama lifecycle ViewModel. Nilainya bertambah setiap kali pengguna mengklik berita.

```kotlin
private val _readCount = MutableStateFlow(0)
val readCount: StateFlow<Int> = _readCount.asStateFlow()
```

### 5. Coroutines — Mengambil Detail Berita Secara Async
Detail berita dimuat secara asinkron menggunakan `async` di `Dispatchers.IO` agar tidak memblokir main thread.

```kotlin
fun loadDetail(news: News, onResult: (News) -> Unit) {
    viewModelScope.launch {
        val detail = async(Dispatchers.IO) {
            repository.getNewsDetail(news)
        }.await()
        onResult(detail)
    }
}
```

## Struktur Project

```
com.example.newsfeed/
├── model/          # Data class News
├── data/           # NewsRepository (sumber data)
├── viewmodel/      # NewsViewModel (state & logika)
└── ui/             # NewsScreen & NewsItem (tampilan)
```

## Teknologi

- **Kotlin** + **Coroutines & Flow**
- **Jetpack Compose** (UI)
- **ViewModel + StateFlow** (state management)
- **Kotlin Multiplatform** (struktur project)


## Screenshot Aplikasi

<img width="1080" height="2400" alt="Screenshot_20260222_142447" src="https://github.com/user-attachments/assets/272baada-f26f-4ee1-a0a0-cd6e93450b48" />

<img width="1080" height="2400" alt="Screenshot_20260222_142430" src="https://github.com/user-attachments/assets/043a5a7b-d081-4443-84cb-1094093e0b98" />
