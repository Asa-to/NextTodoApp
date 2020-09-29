package com.example.nexttodoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class TodoAdapter(): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    // メンバ
    private lateinit var mListener: LongClickListener
    private val mDataList: ArrayList<String> = arrayListOf()

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    // リスト全体のレイアウトの設定をする
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflaterで、XMLデータをもとにViewを生成する
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(inflater)
    }

    // 1行分のレイアウトの設定をする
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // テキストを設定する
        holder.view.todo_element.text = mDataList[position]
        // クリック時の処理
        holder.view.todo_element.setOnLongClickListener() {
            mListener.itemLongClickListener(position, mDataList[position])
            true
        }
    }

    // itemの数を返す
    override fun getItemCount(): Int = mDataList.size

    // クリックリスナー用インターフェースの用意
    interface LongClickListener {
        fun itemLongClickListener(position: Int, item: String)
    }

    // クリックリスナーの実装をするためのメソッド
    fun setOnLongClickListener(listener: LongClickListener){
        this.mListener = listener
    }

    fun removeItem(position: Int) {
        mDataList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(item: String) {
        if(item.isNotBlank()){
            mDataList.add(item)
            notifyDataSetChanged()
        }
    }
}