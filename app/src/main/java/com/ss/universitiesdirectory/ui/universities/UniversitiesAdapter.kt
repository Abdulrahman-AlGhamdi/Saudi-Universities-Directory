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
    private val universityList: List<UniversityModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HeaderViewHolder(
        private val binding: RawUniversityHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
                binding.header.setTextColor(itemView.resources.getColor(R.color.black, null))
            else binding.header.setTextColor(itemView.resources.getColor(R.color.white, null))

            binding.header.text = data.name

            binding.root.setOnClickListener {
                val action = UniversitiesFragmentDirections
                val directions = action.actionUniversitiesFragmentToDetailsFragment(data)
                itemView.findNavController().navigate(directions)
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: RawUniversityItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UniversityModel) {
            binding.name.text = data.name

            binding.root.setOnClickListener {
                val action     = UniversitiesFragmentDirections
                val directions = action.actionUniversitiesFragmentToDetailsFragment(data)
                itemView.findNavController().navigate(directions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater         = LayoutInflater.from(parent.context)
        val headerViewHolder = RawUniversityHeaderBinding.inflate(inflater, parent, false)
        val itemViewHolder   = RawUniversityItemBinding.inflate(inflater, parent, false)

        return when (viewType) {
            LIST -> ItemViewHolder(itemViewHolder)
            else -> HeaderViewHolder(headerViewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) holder.bind(universityList[position])
        else if (holder is ItemViewHolder) holder.bind(universityList[position])
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