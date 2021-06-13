package com.ss.universitiesdirectory.ui.universities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.RawUniversityHeaderBinding
import com.ss.universitiesdirectory.databinding.RawUniversityItemBinding
import com.ss.universitiesdirectory.model.UniversityModel

class UniversitiesAdapter(
    private val universityList: List<UniversityModel?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class UniversityHeaderViewHolder(
        private val binding: RawUniversityHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel?) {
            if (data != null) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
                    binding.root.setBackgroundColor(itemView.resources.getColor(R.color.silver, null))
                else binding.root.setBackgroundColor(itemView.resources.getColor(R.color.colorPrimary, null))

                binding.header.text = data.Name

                if (!data.Province) {
                    binding.root.setOnClickListener {
                        val action = UniversitiesFragmentDirections
                        val directions = action.actionUniversitiesFragmentToDetailsFragment(data)
                        itemView.findNavController().navigate(directions)
                    }
                }
            }
        }
    }

    inner class UniversityItemViewHolder(
        private val binding: RawUniversityItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel?) {
            if (data != null) {
                binding.name.text = data.Name

                binding.root.setOnClickListener {
                    val action = UniversitiesFragmentDirections
                    val directions = action.actionUniversitiesFragmentToDetailsFragment(data)
                    itemView.findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> UniversityHeaderViewHolder(RawUniversityHeaderBinding.inflate(inflater, parent, false))
            else -> UniversityItemViewHolder(RawUniversityItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UniversityHeaderViewHolder) holder.bind(universityList[position])
        else if (holder is UniversityItemViewHolder) holder.bind(universityList[position])
    }

    override fun getItemCount() = universityList.size

    override fun getItemViewType(position: Int): Int {
        return if (universityList[position]?.Province == true) HEADER else LIST
    }

    companion object {
        private const val HEADER: Int = 0
        private const val LIST: Int = 1
    }
}