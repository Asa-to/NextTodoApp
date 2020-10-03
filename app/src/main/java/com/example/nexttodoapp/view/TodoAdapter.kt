package com.example.nexttodoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodoapp.R
import com.example.nexttodoapp.store.TodoListStore
import kotlinx.android.synthetic.main.list_item.view.*

class TodoAdapter(): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    // メンバ
    private lateinit var mListener: LongClickListener
    private lateinit var mStore: TodoListStore

    constructor(store:TodoListStore) : this() {
        mStore = store
    }

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
        holder.view.todo_element.text = mStore.todoList[position].taskName
        // クリック時の処理
        holder.view.todo_element.setOnLongClickListener() {
            mListener.itemLongClickListener(position,mStore.todoList[position].id, mStore.todoList[position].taskName)
            true
        }
    }

    // itemの数を返す
    override fun getItemCount(): Int = mStore.todoList.size
    // クリックリスナー用インターフェースの用意
    interface LongClickListener {
        fun itemLongClickListener(position: Int,id:Long, item: String)
    }

    // クリックリスナーの実装をするためのメソッド
    fun setOnLongClickListener(listener: LongClickListener){
        this.mListener = listener
    }
}