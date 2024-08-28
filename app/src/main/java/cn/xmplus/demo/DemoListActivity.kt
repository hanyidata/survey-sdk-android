package cn.xmplus.demo
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.xmplus.sdk.HYSurveyView


class DemoListActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DemoListAdapter
    private val products = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 创建根布局
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // 创建 SwipeRefreshLayout
        swipeRefreshLayout = SwipeRefreshLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // 创建 RecyclerView
        recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@DemoListActivity)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        // 初始化适配器
        adapter = DemoListAdapter(products)
        recyclerView.adapter = adapter

        // 将 RecyclerView 添加到 SwipeRefreshLayout 中
        swipeRefreshLayout.addView(recyclerView)

        // 将 SwipeRefreshLayout 添加到根布局
        rootLayout.addView(swipeRefreshLayout)

        // 设置根布局为 Activity 的内容视图
        setContentView(rootLayout)

        // 启用 ActionBar 的返回箭头
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // 初始化商品列表
        initProducts()

        // 设置下拉刷新监听器
        swipeRefreshLayout.setOnRefreshListener {
            refreshProducts()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // 关闭当前 Activity，返回上一个 Activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initProducts() {
        // 初始化 10 条商品数据
        for (i in 1..10) {
            products.add("Product $i")
        }
        adapter.notifyDataSetChanged()
    }

    private fun refreshProducts() {
        swipeRefreshLayout.isRefreshing = true
        products.clear()
        adapter.notifyDataSetChanged()

        // 模拟异步刷新数据（例如网络请求）
        Handler(Looper.getMainLooper()).postDelayed({
            products.clear()

            for (i in 1..10) {
                products.add("New Product $i")
            }

            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }, 2000) // 模拟 4 秒延迟
    }

    class DemoListAdapter(private val items: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val ITEM_TYPE_NORMAL = 0
        private val ITEM_TYPE_CUSTOM = 1

        override fun getItemViewType(position: Int): Int {
            return if (position == 2) ITEM_TYPE_CUSTOM else ITEM_TYPE_NORMAL
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == ITEM_TYPE_CUSTOM) {
                val container = FrameLayout(parent.context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                CustomViewHolder(container)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
                NormalViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (items.isEmpty()) {
                if (holder is CustomViewHolder) {
                    holder.showLoadingIndicator()
                }
                return
            }

            if (holder is NormalViewHolder) {
                // 调整索引以避免越界
                val adjustedPosition = if (position > 2) position - 1 else position
                holder.bind(items[adjustedPosition])
            } else if (holder is CustomViewHolder) {
                holder.loadThirdPartyComponent() // 异步加载第三方组件
            }
        }


        override fun getItemCount(): Int {
            // 如果 items 为空，则只显示 1 个项目（即自定义组件）
            return if (items.isEmpty()) 0 else items.size + 1
        }

        class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(android.R.id.text1)

            fun bind(text: String) {
                textView.text = text
            }
        }

        class CustomViewHolder(private val container: FrameLayout) : RecyclerView.ViewHolder(container) {

            fun alert(context: Context, message: String) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        //do things
                    })
                val alert: AlertDialog = builder.create()
                alert.show()
            }

            fun loadThirdPartyComponent() {
                // 异步加载第三方组件
                HYSurveyView.makeViewAsync(container.context, SurveyOptions.sid, SurveyOptions.cid, SurveyOptions.parameters, SurveyOptions.options,
                    {
                        // onReady
                        // 清除之前的视图（如果有的话）
                        container.removeAllViews();
                        var survey = it as HYSurveyView;
                        survey.setOnClose {
                            this.alert(container.context, "结束作答")
                            container.removeView(survey);
                        }
                        survey.setOnCancel {
                            this.alert(container.context, "取消作答")
                            container.removeView(survey);
                        }
                        container.addView(survey);
                    },
                    {
                        // onError
                        this.alert(container.context, it as String)
                    }
                )
//                AsyncTask.execute {
//                    // 模拟网络请求或其他耗时操作，延迟加载
//                    Thread.sleep(2000) // 这里模拟 2 秒延迟
//
//                    // 在主线程更新 UI
//                    (container.context as Activity).runOnUiThread {
//                        val thirdPartyView = TextView(container.context).apply {
//                            text = "Loaded Third Party Component"
//                            layoutParams = FrameLayout.LayoutParams(
//                                FrameLayout.LayoutParams.MATCH_PARENT,
//                                FrameLayout.LayoutParams.WRAP_CONTENT
//                            )
//                        }
//                        container.addView(thirdPartyView)
//                    }
//                }
            }

            fun showLoadingIndicator() {
                // 显示一个加载指示器或提示信息
                container.removeAllViews() // 清除以前的视图
                val loadingView = TextView(container.context).apply {
                    text = "Loading..."
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                container.addView(loadingView)
            }
        }

    }
}
