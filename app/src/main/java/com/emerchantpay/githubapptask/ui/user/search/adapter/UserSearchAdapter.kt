package com.emerchantpay.githubapptask.ui.user.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emerchantpay.githubapptask.databinding.ItemUserBinding
import com.emerchantpay.githubapptask.domain.model.User

class UserSearchAdapter() :
    RecyclerView.Adapter<UserSearchAdapter.ViewHolder>(), Filterable {

    private val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = userListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(userListDiffer.currentList[position])

    override fun getFilter(): Filter = userSearchFilter

    fun setUserItems(userItems: List<User>) {
        users.clear()
        users.addAll(userItems)
        userListDiffer.submitList(users)
    }

    private val userListDiffer: AsyncListDiffer<User> =
        AsyncListDiffer(this, UserDiffCallback)

    private object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

    private val userSearchFilter = object : Filter() {

        override fun performFiltering(query: CharSequence?): FilterResults {
            val searchQuery = query.toString().trim()
            val filteredUsers = if (searchQuery.isEmpty()) {
                users.toList()
            } else {
                users.filter { user -> isMatchSearchQuery(user, searchQuery) }.toList()
            }

            return FilterResults().apply {
                values = filteredUsers
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            userListDiffer.submitList(results?.values as List<User>)
        }
    }

    private fun isMatchSearchQuery(user: User, query: String): Boolean =
        user.login.contains(query, ignoreCase = true)

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                ivUserPhoto.load(user.avatarUrl)
                tvUserName.text = user.login
            }
        }
    }

}