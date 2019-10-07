package kr.ac.gachon.searchdogs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DictAdapter () : RecyclerView.Adapter<DictAdapter.ItemViewHolder>() {
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 데이터베이스(firebase) 연동 후 구현
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_dict, parent, false)
        return ItemViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindDictData(/* 데이터베이스(firebase) 연동 후 구현*/)
    }


    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindDictData () {
            // 데이터베이스(firebase) 연동 후 구현

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailDict::class.java)
                //intent.putExtra()
                itemView.context.startActivity(intent)
            }
        }
    }
}
