package mobin.expandablerecyclerview.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * ExpandableGroup Adapter for recycler view needs a child type and parent type and a parent list in constructor
 * to create an expandable listing view UI
 * @param ExpandableType Parent Type (This is the class providing concrete implementation of the ExpandableGroup Interface)
 * @param ExpandedType Child Type (This can be a subtype of Any object)
 */
abstract class ExpandableRecyclerViewAdapter<ExpandedType : Any, ExpandableType : ExpandableRecyclerViewAdapter.ExpandableGroup<ExpandedType>, PVH : ExpandableRecyclerViewAdapter.ParentViewHolder, CVH : ExpandableRecyclerViewAdapter.ChildViewHolder>
    (
    private val mExpandableList: List<ExpandableType>,
    private val expandingDirection: ExpandingDirection
) :
    RecyclerView.Adapter<PVH>() {

    private var singleExpanded = true


    enum class ExpandingDirection {
        HORIZONTAL,
        VERTICAL
    }

    /*
     A list to keep account of expanded item positions.
     */
    private val expandedIndexList by lazy {
        val list = ArrayList<Int>(itemCount)

        list
    }


    private fun initializeChildRecyclerView(childRecyclerView: RecyclerView?) {

        if (childRecyclerView != null) {

            val linearLayoutManager = LinearLayoutManager(childRecyclerView.context)

            linearLayoutManager.orientation = if (expandingDirection == ExpandingDirection.VERTICAL)
                LinearLayoutManager.VERTICAL
            else LinearLayoutManager.HORIZONTAL

            childRecyclerView.layoutManager = linearLayoutManager


        }
    }


    override fun getItemCount(): Int {
        return mExpandableList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return createParentView(parent, viewType)
    }


    private fun createParentView(parent: ViewGroup, viewType: Int): PVH {
        val pvh = onCreateParentViewHolder(parent, viewType)

        initializeChildRecyclerView(pvh.containerView.getRecyclerView())

        pvh.containerView.setOnClickListener {
            val position = pvh.adapterPosition
            val expandable = mExpandableList[position]
            it.tag = if (it.tag == 0)
                it.tag = 1
            else it.tag = 0
            notifyItemChanged(position)
            onParentViewClicked(expandable, position)

            Log.d("ExpandableParentClick", "Clicked !")
        }


        return pvh
    }

//    private fun adjustChildRecyclerViewHeight(
//        childRecyclerView: RecyclerView,
//        initializing: Boolean,
//        onBind: Boolean
//    ) {
//        val width = if (expandingDirection == ExpandingDirection.VERTICAL)
//            RecyclerView.LayoutParams.MATCH_PARENT
//        else RecyclerView.LayoutParams.WRAP_CONTENT
//
//        val originalLayoutParams = LinearLayout.LayoutParams(
//            RecyclerView.LayoutParams.WRAP_CONTENT,
//            width
//
//        )
//        val hiddenLayoutParams = LinearLayout.LayoutParams(0, 0)
//
//        if (onBind && childRecyclerView.height == 0)
//            childRecyclerView.layoutParams = originalLayoutParams
//        else if (initializing) {
//            childRecyclerView.layoutParams = if (!initiallyExpanded())
//                hiddenLayoutParams
//            else originalLayoutParams
//        }
//
//    }


    override fun onBindViewHolder(holder: PVH, position: Int) {
        setupChildRecyclerView(holder, position)

    }

    private fun setupChildRecyclerView(holder: PVH, position: Int) {
        val expandableType = mExpandableList[position]
        val childListAdapter = ChildListAdapter(
            expandableType.getExpandingItems()
        ) { viewGroup, viewType ->
            onCreateChildViewHolder(viewGroup, viewType)

        }
        val childRecyclerView = holder.containerView.getRecyclerView()

        if (childRecyclerView?.adapter == null)
            childRecyclerView?.adapter = childListAdapter

        clickEvent(expandableType, holder.containerView)


        onBindParentViewHolder(holder, expandableType)
    }

    private fun clickEvent(expandableType: ExpandableType, containerView: View) {
        val childRecyclerView = containerView.getRecyclerView()
        when {
            containerView.tag == 1 -> childRecyclerView?.visibility =
                View.VISIBLE
            containerView.tag == 0 -> childRecyclerView?.visibility = View.GONE
        }

    }


    /**
     * Searches View hierarchy for an instance of RecyclerView
     * @return RecyclerView or null if not found
     */
    private fun View.getRecyclerView(): RecyclerView? {
        if (this is ViewGroup && childCount > 0) {
            forEach {
                if (it is RecyclerView) {
                    return it
                }

            }
        }
        Log.e("ExpandableAdapter", "Recycler View for expanded items not found in parent layout.")
        return null
    }

    private inner class ChildListAdapter(
        private val mExpandedList: List<ExpandedType>,
        private val onChildRowCreated: (ViewGroup, Int) -> CVH
    ) :
        RecyclerView.Adapter<CVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVH {
            return onChildRowCreated(parent, viewType)
        }

        override fun getItemCount(): Int {
            return mExpandedList.size
        }

        override fun onBindViewHolder(holder: CVH, position: Int) {
            val expanded = mExpandedList[position]
            onBindChildViewHolder(holder, expanded)
        }

    }

    abstract class ParentViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    abstract class ChildViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer


    interface ExpandableGroup<out E> {
        fun getExpandingItems(): List<E>

        /**
         *   Specifies if you want to show the UI in expanded form.
         */
        fun isExpanded(): Boolean
    }


    abstract fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PVH

    abstract fun onParentViewClicked(expandableType: ExpandableType, position: Int)

    abstract fun onBindParentViewHolder(parentViewHolder: PVH, expandableType: ExpandableType)

    abstract fun onCreateChildViewHolder(child: ViewGroup, viewType: Int): CVH

    abstract fun onBindChildViewHolder(childViewHolder: CVH, expandedType: ExpandedType)

    /**
     * Specifies if you want to show one item expanded in UI.
     * @return true to enable one child expansion at a time.
     */
    abstract fun isSingleExpanded(): Boolean


}

