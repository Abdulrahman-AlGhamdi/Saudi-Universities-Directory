package com.ss.universitiesdirectory.ui.universities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.ItemUniversityNameBinding
import com.ss.universitiesdirectory.databinding.ItemUniversityRegionBinding
import com.ss.universitiesdirectory.model.UniversityModel
import com.ss.universitiesdirectory.utils.navigateTo

class UniversitiesAdapter(
    private val universityList: List<UniversityModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RegionViewHolder(
        private val binding: ItemUniversityRegionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
                binding.header.setTextColor(itemView.resources.getColor(R.color.black, null))
            else binding.header.setTextColor(itemView.resources.getColor(R.color.white, null))

            binding.header.text = data.name
        }
    }

    inner class UniversityViewHolder(
        private val binding: ItemUniversityNameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel) {
            binding.name.text = data.name

            binding.root.setOnClickListener {
                val action     = UniversitiesFragmentDirections
                val directions = action.actionUniversitiesFragmentToDetailsFragment(data)
                itemView.findNavController().navigateTo(directions, R.id.universitiesFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater         = LayoutInflater.from(parent.context)
        val headerViewHolder = ItemUniversityRegionBinding.inflate(inflater, parent, false)
        val itemViewHolder   = ItemUniversityNameBinding.inflate(inflater, parent, false)

        return when (viewType) {
            LIST -> UniversityViewHolder(itemViewHolder)
            else -> RegionViewHolder(headerViewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RegionViewHolder) holder.bind(universityList[position])
        else if (holder is UniversityViewHolder) holder.bind(universityList[position])
    }

    override fun getItemCount() = universityList.size

    override fun getItemViewType(position: Int): Int {
        return if (universityList[position].province) HEADER else LIST
    }

    companion object {
        private const val LIST   : Int = 1
        private const val HEADER : Int = 0
    }
}