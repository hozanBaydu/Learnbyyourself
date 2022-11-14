package com.hozanbaydu.learnbyyourself.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hozanbaydu.learnbyyourself.databinding.WordRecyclerRowBinding
import com.hozanbaydu.learnbyyourself.roomdatabase.Word
import javax.inject.Inject

class WordRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder>() {


    private var onItemClickListener : ((word:String,sentence:String,url:String) -> Unit)? = null

    class WordViewHolder(var binding:WordRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var words: List<Word>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener : (String,String,String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = WordRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WordViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val imageView = holder.binding.wordImageview
        val nameText = holder.binding.wordTextview
        val word = words[position]
        val nameToString=words[position].name
        val sentenceToString=words[position].sentence
        val urlToString=words[position].imageUrl

        holder.itemView.apply {
            glide.load(word.imageUrl).into(imageView)
            nameText.text = word.name
            holder.binding.sentenceTextview.text=word.sentence

            setOnClickListener {
                onItemClickListener?.let {
                    it(nameToString,sentenceToString,urlToString)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return words.size
    }

}