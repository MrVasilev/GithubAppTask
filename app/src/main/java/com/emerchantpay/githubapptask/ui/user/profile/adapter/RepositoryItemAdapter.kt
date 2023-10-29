package com.emerchantpay.githubapptask.ui.user.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emerchantpay.githubapptask.databinding.ItemRepositoryBinding
import com.emerchantpay.githubapptask.domain.model.Repository

class RepositoryItemAdapter : RecyclerView.Adapter<RepositoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = repositoryListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(repositoryListDiffer.currentList[position])

    fun setRepositoryItems(repositoryItems: List<Repository>) {
        repositoryListDiffer.submitList(repositoryItems)
    }

    private val repositoryListDiffer: AsyncListDiffer<Repository> =
        AsyncListDiffer(this, RepositoryDiffCallback)

    private object RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem == newItem
    }

    class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository) {
            binding.apply {
                tvRepoName.text = repository.name
                tvRepoUrl.text = repository.url
                ivStarred.visibility = if (repository.isStarred) View.VISIBLE else View.GONE
            }
        }
    }
}