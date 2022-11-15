# Dil öğrenme uygulaması
#### Hozan BAYDU

Merhaba,bu uygulamayı kotlin ile Google'nin önerdiği son teknolojiler ile kodladım.  


## Özellikler

- Kullanıcılar dil öğrenmek için istediği sayıda kelime kaydedilebilir.
- Kelimelerin anımsanması için her kelime için bir resim çizilebilir.
- İnternetten aratılan kelimeye göre 3 milyon fotoğraf arasında seçim yapılabilir.
- Ana ekranda kaydedilen kelimeye tıklayarak kelimenin ayrıntıları görülebilmektedir.


### Giriş sayfası

Kullanıcılar bu sayfada önceden kaydettikleri kelimeleri recyclerview içinde görebilecektir.

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgK-1jxsWf2uvujj3Cv2RQm08AovBuCOjtPmNcsUX1wndUqdMPGa4KCihxTO6MVv6Mn0BBJFD1bJ8lLec7jmEB2SfGQz0D3EhNONYx4n-Vmfy0XxSxxYy13su7M1Mqj-8SwAfJOBDlASnNtUAlYolCIQgJGEtnDGQD1BfFu4L5egdd7OpG8Sv2-AdtT/s600/learn6.jpeg)


```sh
private val swipeCallBack=object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedArt=wordRecyclerAdapter.words[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }
    }
```
Yukarıdaki kod sayesinde kullanıcı silmek istediği kelimeyi sağa veya sola sürükleyerek silebilecektir.


```sh
setOnClickListener {
                onItemClickListener?.let {
                    it(nameToString,sentenceToString,urlToString)
                }
            }
```
Yukarıda recyclerviewde oluşturduğum fonksiyonu hilt sayesinde fragmentten direk çağırıp tıklanan elemanın bilgilerini details fragmente yollar.

```sh
fun setOnItemClickListener(listener : (String,String,String) -> Unit) {
        onItemClickListener = listener
    }
setOnClickListener {
                onItemClickListener?.let {
                    it(nameToString,sentenceToString,urlToString)
                }
            }
```
```sh
wordRecyclerAdapter.setOnItemClickListener { word, sentence, url ->

            viewModel.word=word
            viewModel.sentence=sentence
            viewModel.ımageUrl=url

            findNavController().navigate(WordFragmentDirections.actionWordFragmentToDetailsFragment())
        }
 ```
        
Yukarıdaki kod parçası recyclerviewden gelen bilgileri oluşturduğum viewmodeldeki değişkenlerin değerini belirlemek için kullanır.


### DetailsFragment


![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhE7DE3vFkSe9C5dZUrle5B7WQ2eOxw0wmi2Ew23vPm84mXx7z7ugjOfB5yT4KM8WDey4fS-CBaIKACjv-PMUnS_ZAmRIgY0Sr_r715iaPJ0Ua7HckHNTOY_d1Myms4xw8RghYhruSCsUesbyQHubQkuxDdItS2U3rCrvHw-mZekklRrZNnLYYMcNMS/s600/learn1.jpeg)

Kullanıcı bu ekranda kaydedilen kelimenin ayrıntılarına ulaşabilir.

```sh
binding.detailsWord.text=viewModel.word
        binding.detailsSentence.text=viewModel.sentence

        glide.load(viewModel.ımageUrl).into(binding.detailsImageview)
```
Viewmodelden aldığımız güncel verileri kullanıcılar,glide sayesinde bu ekranda görebilir.


### ChoiceFragment

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhYIkR7c5V3hXBk7t0rZiuKry_9wzzQiOB5SsqbmB-NUhu1HCO8RHfyOnlv3N6rMolDCejOydj1V2KKlUHiwwbrURPbPRor-dqhrBiiODMFyJPLhWvSRY1LJ1AZyu-NMceBc4azWId0MGfeBNbrHoDbSbPYB8DaBWw1p_fAUjuL4hjhXrpmd8ffhcZW/s600/learn5.jpeg)

Kullanıcı bu ekranda kaydedilecek kelimeyi çizerek veya internetten stok görüntüyle mi kaydedeceğine karar verir.

```sh
binding.chooseImageview.setOnClickListener {
            findNavController().navigate(ChoiceFragmentDirections.actionChoiceFragmentToApiFragment())
        }
        binding.drawingImageview.setOnClickListener {
            findNavController().navigate(ChoiceFragmentDirections.actionChoiceFragmentToDrawingFragment())
        }
```

Navigasyon kullanarak yukarıdaki kodlarla bu yönlendirmeyi kolaylıkla sağladım.


### DetailsActivity
![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjpLAcbbLvCWZJmHKnNt--ZwxMVpBj2b9F-LQiEsOPlk3j4IeigN_g663l-rh-fX6-RKwPm2wxoH9-wYRkmMfgpOa4cGgVTw6fBW3Uv-6xidCI5c10sWh4qc1gOvBUsY_nTSzQZeIgkUQPL5KMkjoDzgiPW8s6UwxAlof9FZRZinD0hFcvbQNvpdT7c/s600/learn3.jpeg)

Kullanıcılar internetten resim seçmek istediklerinde bu ekran ile karşılaşacaklardır.Kelime ve cümle seçtikten sonra resim seçmek için imageviewe tıkladıktan sonra pixabay sitesine bir istek atılacaktır.Aşağıdaki kod parçası bunu göstermektedir.

```sh
interface RetrofitApi {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String=API_KEY
    ): Response<Images>
}
```
Gelen yanıt room kullanılarak kaydedilecektir.

```sh
@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Word)

    @Delete
    suspend fun deleteArt(art: Word)

    @Query("SELECT * FROM words")
    fun observeArt(): LiveData<List<Word>>
}
```

Dao bu şekilde kodlanmıştır.Hafıza yönetimi ve verimlilik için cevaplar liveData şeklinde alınmıştır.Gelen cevaplar üzerinde herhangi bir değişiklik veya filtreleme kullanmadığım için flow kullanma gereği duymadım ama flow ile de yapılabilir.

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjneP-ucXHZ7tLRKgWcq7Fzq9J3R6KH9pX6xcYujLp9Gu5mvvH4KgP9mYmphX6kdnUaPfP5RgWHo-_g_G1Fey3OnpWExkzWZpMsA3CBnYjGXaGEkK65E2xJEHpz-25ZZil_9Uup6FRn_TQi6EHCRL7dOoTJ0xLS16fs0tRirByb7dILyUeoZSv4dnK-/s600/learn2.jpeg)

Resim seçmek için kelime girildikten sonra kullanıcı gridLayoutla yapılmış 16 elemanlı bir recyclerview ile karşılaşır.

```sh
var job: Job?=null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(100)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        wordViewModel.searchForImage(it.toString())
                    }
                }
            }
        }
}
```

Kullanıcının seçiminden sonra fragment destroy edildiğinde verimlilik için yazılan işin kapatılması gerekiyor bu nedenle yukarıdaki kodda görüldüğü üzere bir iş tanımladım.

### DrawingFragment
![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhMzFt2Ng-vJs2xYgl8DuQg5WvIkm6toAL6Chc7GNtI3_mApqYQP0W4uSgLhUoEPRJozy864bhQ4NOSraDmOVieon26Dt8g8msGWr6xOf7_Cytmde2EF4cSe5cIDn9nfGye3_G60WMhdehdM0MRtebn5r5wh3LfdIFE7yHmdE0jTmS1p109nTl3iDz2/s600/learn4.jpeg)

Kullanıcı seçim ekranında çizerek kelime kaydetmeye karar verdiğinde yukarıdaki ekranda göründüğü üzere,resmi çizip sonrasında bunu kaydedebilir.

```sh
binding.save.setOnClickListener {
            var bitmap = getBitmapFromView(binding.drawingLayout)
            if (bitmap != null) {
                smallBitmap=makeSmallBitmap(bitmap,200)
            }
            smallBitmap.saveImage(context = requireContext())
            val imageUri = smallBitmap?.saveImage(requireContext()).toString()
            println(imageUri)
            wordViewModel.setSelectedImage(imageUri)
        }
```

Kullanıcı kaydet butonuna tıklayınca önceden yazdığım makeSmallBitmap fonksiyonu sayesinde resim küçültülüp saveBitmap foksiyonu ile kaydedilir.

```sh
@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun injectRoomDatabase(   
        @ApplicationContext context: Context
    )= Room.databaseBuilder(
        context,WordDatabase::class.java,"ArtBookDataBase"
    ).build()

    @Singleton
    @Provides
    fun injectDao(dataBase: WordDatabase)=dataBase.wordDao()
```

Database için yazdığım hilt modul kodlarının bir kısmı yukarıda görülmektedir.

Uygulamayı yazan:Hozan BAYDU

Tasarım:Adobexd,Canva

Tarih:20.10.2022

iletişim:hozan.baydu3447@gmail.com
