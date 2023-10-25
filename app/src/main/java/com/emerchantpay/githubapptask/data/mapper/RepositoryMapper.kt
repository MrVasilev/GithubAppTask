package com.emerchantpay.githubapptask.data.mapper

import com.emerchantpay.githubapptask.data.model.RepositoryResponse
import com.emerchantpay.githubapptask.domain.model.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryMapper @Inject constructor() {

    fun mapToDomainModel(response: RepositoryResponse) = Repository(
        id = response.id,
        name = response.name.orEmpty(),
        url = response.url.orEmpty(),
        owner = response.owner,
        contributors = response.contributors ?: emptyList()
    )
}