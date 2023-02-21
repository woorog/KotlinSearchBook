package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemBookBinding
import com.example.myapplication.model.Book

class BookAdapter(private val itemClickedListener: (Book) -> Unit): ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {

    inner class  BookItemViewHolder(private val binding: ItemBookBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookModel:Book){
            binding.titleTextView.text=bookModel.title
            binding.descriptionTextView.text=bookModel.description
            binding.root.setOnClickListener {
                itemClickedListener(bookModel)
            }
            //이미지
           Glide
               .with(binding.coverImageView)
               .load(bookModel.coverSmallUrl)
               .into(binding.coverImageView)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
    return  BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }//뷰홀더 없을 경우

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }//

     //새로운 값을 할당 결정
    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem==newItem

            }//안의 컨텐츠 같냐 다르냐 구별별

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id==newItem.id
            }


        }
    }


}