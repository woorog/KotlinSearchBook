package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.adapter.BookAdapter
import com.example.myapplication.adapter.HistoryAdapter
import com.example.myapplication.api.BookService
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.BestSellerDto
import com.example.myapplication.model.History
import com.example.myapplication.model.SearchBookDto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var bookService: BookService
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var db: AppDatabase

   private val TAG="MyActivity"


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initBookRecyclerView()
        initSearchEditText()
        initHistoryRecyclerView()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            name = "BookSearchDB"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create<BookService>()

        bookService.getBestSellerBooks(apiKey = getString(R.string.InterparkApiKey))
            .enqueue(object: Callback<BestSellerDto> {
                override fun onResponse( //true.
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {

                    if(response.isSuccessful.not()){
                        Log.e(TAG,"error")
                        return
                    }
                    response.body()?.let {
                        Log.d(TAG,it.toString())

                        it.books.forEach{book ->
                            Log.d(TAG,book.toString())

                        }

                        adapter.submitList(it.books)
                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    Log.e(TAG,t.toString())
                }


            })

    //검색기능


    }

    private fun search(keyword:String){
        bookService.getBooksByName(getString(R.string.InterparkApiKey),keyword)
            .enqueue(object: Callback<SearchBookDto> {
                override fun onResponse( //true.
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if(response.isSuccessful.not()){
                        Log.e(TAG,"error")
                        return
                    }

                    adapter.submitList(response.body()?.books.orEmpty())

                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    hideHistoryView()
                    Log.e(TAG,t.toString())
                }


            })
    }

    private fun initBookRecyclerView(){
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("bookModel", it)
            startActivity(intent)
        })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(
            historyDeleteClickedListener = {
                deleteSearchKeyword(it)
            }, historyKeywordClicktedListener = {
                search(it)
            })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
        initSearchEditText()

    }


    private fun initSearchEditText() {
        binding.searchEditText.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
                // true 주면 오류생김.
            }
            return@setOnTouchListener false
        }

        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        }


    private fun showHistoryView() {
        Thread {
            val keywords = db.historyDao().getAll().reversed()

            runOnUiThread {
                if (keywords.size == 0){
                    binding.historyRecyclerView.isVisible = false
                    return@runOnUiThread
                }
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    /*private fun hideBookRecyclerView() {
        binding.bookRecyclerView.isVisible = false
    }*/

    private fun saveSearchKeyword(keyword: String){
        Thread{
            db.historyDao().insertHistory(History(null,keyword))
        }.start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDao()?.delete(keyword)
            showHistoryView()
        }.start()
    }




    private lateinit var appBarConfiguration: AppBarConfiguration

    //private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}