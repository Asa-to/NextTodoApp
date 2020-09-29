package com.example.nexttodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // メンバ変数にはmを付けてる
    private val mAdapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerViewの取得と設定
        val recyclerView = findViewById<RecyclerView>(R.id.todoList)
        // LayoutManagerの取得(リストの表示形式を決める)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mAdapter
        // クリック処理インターフェースを実装
        mAdapter.setOnLongClickListener(object : TodoAdapter.LongClickListener{
            override fun itemLongClickListener(position: Int, item: String) {
                createDialog(position, item)
            }
        })
    }

    fun onClick(v: View?) {
        val edit = findViewById<EditText>(R.id.addTask)
        mAdapter.addItem(edit.text.toString())
        edit.setText("")
    }

    fun createDialog(position: Int, item: String) {
        AlertDialog.Builder(this).apply {
            setTitle("確認")
            setMessage("${item}を削除しますか")
            setPositiveButton("はい") { _, _ ->
                mAdapter.removeItem(position)
            }
            setNegativeButton("いいえ") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }
}