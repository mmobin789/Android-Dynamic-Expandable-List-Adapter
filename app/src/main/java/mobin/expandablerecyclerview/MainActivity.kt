package mobin.expandablerecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mobin.expandablerecyclerview.adapters.MyAdapter
import mobin.expandablerecyclerview.models.Parent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = ArrayList<Parent>(10)
        for (i in 0..9)
            list.add(Parent("Parent $i"))

        val adapter = MyAdapter(this, list)
        //adapter.setExpanded(true)
        rvM.adapter = adapter

    }
}
