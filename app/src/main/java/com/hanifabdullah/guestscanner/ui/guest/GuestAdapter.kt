package com.hanifabdullah.guestscanner.ui.guest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.guestscanner.R
import kotlinx.android.synthetic.main.item_guest.view.*
import kotlin.random.Random

class GuestAdapter(val list: ArrayList<GuestModel>) :
    RecyclerView.Adapter<GuestAdapter.ViewHolder>(), Filterable {

    var listOrigin: ArrayList<GuestModel>? = null
    var listFilter: ArrayList<GuestModel>? = null

    init {
        listOrigin = list
        listFilter = list
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun clear() {
            itemView.ig_tv_name.text = ""
            itemView.ig_tv_from.text = ""
            Glide.with(itemView.context)
                .load(R.drawable.ic_attend_disable)
                .into(itemView.ig_iv_attend)
            Glide.with(itemView.context)
                .load(R.drawable.avatar1)
                .into(itemView.ig_iv_avatar)
        }

        fun bind(guestModel: GuestModel?) {
            itemView.ig_tv_name.text = guestModel?.name
            itemView.ig_tv_from.text = guestModel?.from

            if (guestModel?.isAttended == 0){
                Glide.with(itemView.context)
                    .load(R.drawable.ic_attend_disable)
                    .into(itemView.ig_iv_attend)
            }else{
                Glide.with(itemView.context)
                    .load(R.drawable.ic_attend_enable)
                    .into(itemView.ig_iv_attend)
            }

            val random = Random.nextInt(1, 6)
            var img: Int = R.drawable.avatar1
            when (random) {
                1 -> img = R.drawable.avatar1
                2 -> img = R.drawable.avatar2
                3 -> img = R.drawable.avatar3
                4 -> img = R.drawable.avatar4
                5 -> img = R.drawable.avatar5
                6 -> img = R.drawable.avatar6
            }
            Glide.with(itemView.context)
                .load(img)
                .into(itemView.ig_iv_avatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
    )

    override fun getItemCount(): Int = listFilter?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clear()
        holder.bind(listFilter?.get(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listFilter = listOrigin
                } else {
                    listFilter = ArrayList()
                    for (row in listOrigin ?: ArrayList()) {

                        val name = row.name ?: ""
                        val from = row.from ?: ""
                        if (name.toLowerCase().contains(charString.toLowerCase()) || from.toLowerCase().contains(charString.toLowerCase())) {
                            listFilter?.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = listFilter
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listFilter = filterResults.values as ArrayList<GuestModel>
                notifyDataSetChanged()
            }
        }
    }

}