package com.project.eldarchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.eldarchallenge.R
import com.project.eldarchallenge.databinding.CardsItemsBinding
import com.project.eldarchallenge.db.Cards

class CardsAdapter: RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {
    private var cardsList = emptyList<Cards>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder =
        CardsViewHolder(
            CardsItemsBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cards_items,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(cardsList[position])
    }

    class CardsViewHolder(
        private val binding: CardsItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cards) {
            binding.tvCardName.text = item.brand
        }
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun setData(cards: List<Cards>) {
        this.cardsList = cards
        notifyDataSetChanged()
    }
}