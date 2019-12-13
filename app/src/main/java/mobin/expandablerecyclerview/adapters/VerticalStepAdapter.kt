package mobin.expandablerecyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.step.*
import mobin.expandablerecyclerview.R
import mobin.expandablerecyclerview.models.VerticalStep

class VerticalStepAdapter(private val steps: ArrayList<VerticalStep>) :
    RecyclerView.Adapter<VerticalStepAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.step,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int {
        return steps.size
    }


    inner class StepViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            containerView.setOnClickListener {
                val position = adapterPosition
                val step = steps[position]
                step.isSelected = !step.isSelected
                notifyItemChanged(position)

            }
        }

        fun bind(verticalStep: VerticalStep) {

            val visibility = if (verticalStep.isSelected) {
                tvSummary.text = verticalStep.summary
                View.GONE
            } else {
                tvSummary.setText(R.string.click_for_details)
                View.VISIBLE
            }

            tvStep.text = verticalStep.title



            btn1.text = verticalStep.btnText1
            btn2.text = verticalStep.btnText2

            btn1.visibility = if (verticalStep.btnText1.isNullOrBlank() || !verticalStep.isSelected)
                View.GONE
            else View.VISIBLE

            btn2.visibility = if (verticalStep.btnText2.isNullOrBlank() || !verticalStep.isSelected)
                View.GONE
            else View.VISIBLE


        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }
}