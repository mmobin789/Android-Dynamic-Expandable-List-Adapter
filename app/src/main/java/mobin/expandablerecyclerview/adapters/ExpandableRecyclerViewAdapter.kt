package mobin.expandablerecyclerview.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Expandable Adapter for recycler view needs a child type and parent type and a parent list in constructor
 * to create an expandable listing view UI
 * @param Expandable Parent Type (This is the class providing concrete implementation of the Expandable Interface)
 * @param Expanded Child Type   (Any type of Object)
 */
abstract class ExpandableRecyclerViewAdapter<Expandable : ExpandableRecyclerViewAdapter.Expandable, PVH : ExpandableRecyclerViewAdapter.ParentViewHolder, CVH : ExpandableRecyclerViewAdapter.ChildViewHolder>
    (private val mParentList: List<Expandable>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return mParentList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return onCreateParentViewHolder()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val parent = mParentList[position]
        val childListAdapter =
            ChildListAdapter(onCreateChildViewHolder(), parent.getExpandingItems())
    }


    abstract class ParentViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    abstract class ChildViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer


    interface Expandable {
        fun getExpandingItems(): List<Expanded>

    }

    interface Expanded {
        fun getName(): String
    }


    abstract fun onCreateParentViewHolder(): PVH

    abstract fun onBindParentViewHolder(parentViewHolder: PVH, position: Int)

    abstract fun onCreateChildViewHolder(): CVH

    abstract fun onBindChildViewHolder(childViewHolder: CVH, position: Int)


    inner class ChildListAdapter(
        private val childViewHolder: CVH,
        private val items: List<Expanded>
    ) :
        RecyclerView.Adapter<ChildViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
            return childViewHolder
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
            val item = items[position]
        }

    }
}

/**
 * Searches View hierarchy for an instance of RecyclerView
 * @return RecyclerView or null if not found
 */
//private fun View.getRecyclerView(): RecyclerView? {
//    if (this is ViewGroup && childCount > 0) {
//        forEach {
//            if (it is RecyclerView)
//                return it
//        }
//    }
//    Log.e("ExpandableAdapter", "Recycler View for expanded items not found in child layout.")
//    return null
//}