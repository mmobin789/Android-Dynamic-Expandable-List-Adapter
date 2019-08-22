package mobin.expandablerecyclerview.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Expandable Adapter for recycler view needs a child type and parent type and a parent list in constructor
 * to create an expandable listing view UI
 * @param Expandable Parent Type (This is typically a class implementing the Expandable Interface)
 * @param Expanded Child Type   (Any type of Child Object)
 */
abstract class ExpandableRecyclerViewAdapter<Expandable : ExpandableRecyclerViewAdapter.Expandable<String>, PVH : ExpandableRecyclerViewAdapter.ParentViewHolder, CVH : ExpandableRecyclerViewAdapter.ChildViewHolder>
    (private val mParentList: List<Expandable>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return mParentList.count()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val pvh = onCreateParentViewHolder(parent, viewType)
        val cvh = onCreateChildViewHolder(parent, viewType)
        val childRecyclerView = cvh.containerView.getRecyclerView()

        return pvh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }


    abstract class ParentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    abstract class ChildViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer


    interface Expandable<out T> {

        fun getExpandingItems(): List<T>

    }


    abstract fun onCreateParentViewHolder(vg: ViewGroup, viewType: Int): PVH

    abstract fun onBindParentViewHolder(parentViewHolder: PVH, position: Int)

    abstract fun onCreateChildViewHolder(vg: ViewGroup, viewType: Int): CVH

    abstract fun onBindChildViewHolder(childViewHolder: CVH, position: Int)
}

/**
 * Searches View hierarchy for an instance of RecyclerView
 * @return RecyclerView or null if not found
 */
private fun View.getRecyclerView(): RecyclerView? {
    if (this is ViewGroup && childCount > 0) {
        forEach {
            if (it is RecyclerView)
                return it
        }
    }
    Log.e("ExpandableAdapter", "Recycler View for expanded items not found in child layout.")
    return null
}