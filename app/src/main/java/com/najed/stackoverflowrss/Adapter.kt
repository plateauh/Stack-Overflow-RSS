package com.najed.stackoverflowrss

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.najed.stackoverflowrss.databinding.QuestionItemBinding

class Adapter (private val questions: ArrayList<Question>, private val context: Context):
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: QuestionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(QuestionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val question = questions[position]
        holder.binding.apply {
            titleTv.text = question.title
            authorTv.text = "Asked by: ${question.author}"
            categoriesTv.text = question.categories
            questionCard.setOnClickListener {
                Alert(context, question.title, question.details)
            }
        }
    }

    override fun getItemCount() = questions.size
}