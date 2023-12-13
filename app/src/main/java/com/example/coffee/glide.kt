package com.example.coffee

import android.Manifest
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.coffee.databinding.ActivityGlideBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.sql.DataSource

class glide : AppCompatActivity() {

    //создаем переменную, которая должна иметь имя, такое же как на файле разметки
    lateinit var binding: ActivityGlideBinding

    //переменная для CHeckBoks
    private var useKeyWords: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityGlideBinding.inflate(layoutInflater).also { setContentView(it.root) } //Метод inflate() преобразует XML-разметку в объект класса ActivityMainBinding.


        //обработка события при нажатии на поиск на клавиатуре
        binding.searchViewEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                return@setOnEditorActionListener onGetRandomImage()
            }
            return@setOnEditorActionListener false
        }

        //при нажатии на кнопку вызываем метод поиска картинки
        binding.btGetRandom.setOnClickListener(){
            onGetRandomImage()
        }

        binding.isSearchCheckbox.setOnClickListener {
            this.useKeyWords = binding.isSearchCheckbox.isChecked
            updateUi()
        }

        updateUi()


    }


    private fun onGetRandomImage(): Boolean {

        val keyworld = binding.searchViewEditText.text.toString()
        //проверка есть ли что то в editText
        if (keyworld.isBlank() && useKeyWords){
            binding.searchViewEditText.error = "KeyWorld is Empty"
            return true
        }

        //преобразуем введенное слово в кодировку для поиска
        binding.progressBar.visibility = View.VISIBLE
        val enCodedKeyWorld = URLEncoder.encode(keyworld, StandardCharsets.UTF_8.name())
        Glide
            .with(this)
//            .asBitmap()
            //.downloadOnly() //загрузить изображение и сохранить его в кеш
            .load("https://source.unsplash.com/random?${if (useKeyWords) enCodedKeyWorld else ""}")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)

            .listener(object : RequestListener<Drawable> {
            //- Создаем объект-слушатель, который реализует интерфейс RequestListener<Drawable>. Этот интерфейс используется для отслеживания состояния загрузки изображения.
            //будет вызван в случае, если загрузка изображения не удалась
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                binding.progressBar.visibility = View.GONE
                return false
            }

            //будет вызван, когда загрузка изображения будет выполнена успешно
            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource,
                isFirstResource: Boolean
            ): Boolean {
                binding.progressBar.visibility = View.GONE
                return false
            }

        })

            .into(binding.imageTest)

        return false
    }

    companion object{
        @JvmStatic val TAG = ActivityGlideBinding::class.simpleName
        @JvmStatic private val KEY_USE_WORD = "IS_KEY_USE_WORD"

    }
    //функция для проверки чекбокса
    private fun updateUi() = with(binding){
        isSearchCheckbox.isChecked = useKeyWords
        if (useKeyWords){
            //ставим editText видимым если чекбокс выбран
            searchViewEditText.visibility = View.VISIBLE
        } else {
            searchViewEditText.visibility = View.GONE
        }
    }

}