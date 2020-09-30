package com.example.nexttodoapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexttodoapp.R
import com.example.nexttodoapp.action.ActionMode
import com.example.nexttodoapp.action.DbControlActionCreator
import com.example.nexttodoapp.action.DbControlActionState
import com.example.nexttodoapp.dispatcher.Dispatcher
import com.example.nexttodoapp.store.TodoListStore
import com.example.nexttodoapp.utility.CancelableCoroutineScope

class MainActivity : AppCompatActivity(),View.OnClickListener {
    // メンバ変数にはmを付けてる
    private val coroutineScope = CancelableCoroutineScope()
    private val dispatcher = Dispatcher()
    private val mDbControlActionCreator = DbControlActionCreator(dispatcher, coroutineScope)
    private val mTodoListStore = TodoListStore(dispatcher)
    private val mAdapter = TodoAdapter(mTodoListStore)

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
        mAdapter.apply {
            setOnLongClickListener(object : TodoAdapter.LongClickListener {
                override fun itemLongClickListener(position: Int, item: String) {
                    createDialog(position, item)
                }
            })
        }

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener(this)

        mTodoListStore.actionData.observe(this, Observer<DbControlActionState> { _ ->
            Log.d("activity","observe")
            mAdapter.notifyDataSetChanged()
            val edit = findViewById<EditText>(R.id.addTask)
            edit.setText("")
        })

        mDbControlActionCreator.execute(ActionMode.GET,null,null,this)
    }

    override fun onClick(v: View?) {
        val edit = findViewById<EditText>(R.id.addTask)
        mDbControlActionCreator.execute(ActionMode.INSERT,null,edit.text.toString(),this)
    }

    fun createDialog(position: Int, item: String) {
        AlertDialog.Builder(this).apply {
            setTitle("確認")
            setMessage("${item}を削除しますか")
            setPositiveButton("はい") { _, _ ->
                mDbControlActionCreator.execute(ActionMode.DELETE,position,item,this@MainActivity)
            }
            setNegativeButton("いいえ") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }
}