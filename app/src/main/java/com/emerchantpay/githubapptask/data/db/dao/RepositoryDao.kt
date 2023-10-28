package com.emerchantpay.githubapptask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emerchantpay.githubapptask.data.db.model.RepositoryEntity

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): List<RepositoryEntity>?

    @Query("SELECT * FROM repositories WHERE is_starred = 0")
    fun getAllOwnedRepositories(): List<RepositoryEntity>?

    @Query("SELECT * FROM repositories WHERE is_starred = 1")
    fun getAllStarredRepositories(): List<RepositoryEntity>?

    @Query("SELECT * FROM repositories WHERE repo_id = :id LIMIT 1")
    fun getRepoById(id: Long): RepositoryEntity?

}