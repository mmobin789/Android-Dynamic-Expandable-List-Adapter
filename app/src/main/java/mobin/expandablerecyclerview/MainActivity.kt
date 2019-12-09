package mobin.expandablerecyclerview

import android.os.Bundle
import android.widget.Toast
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

        val adapter = MyAdapter(list)
        rvM.adapter = adapter

        adapter.setExpanded(false)

        adapter.setExpandableViewClickListener { expandableGroup, position ->
            Toast.makeText(
                this,
                expandableGroup.name + " Position: " + position,
                Toast.LENGTH_SHORT
            ).show()
        }

        adapter.setExpandedViewClickListener { expandedType, expandableGroup, position ->
            Toast.makeText(
                this,
                expandableGroup.name + " " + expandedType.name + " Position: " + position,
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}
