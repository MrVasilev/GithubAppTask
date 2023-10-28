@file:JvmName(name = "NetworkExtensions")

package com.emerchantpay.githubapptask.data.network.utils

internal suspend fun <R, D, T> fetchRemoteDataAndInsertInDb(
    fetchRemoteData: suspend () -> List<R>,
    mapToDbModel: (R) -> D,
    insertDbData: suspend (List<D>) -> Unit,
    mapToDomainModel: (D) -> T,
): List<T> =
    fetchRemoteData()
        .map { remoteData -> mapToDbModel(remoteData) }
        .run {
            insertDbData(this)
            map { dbData -> mapToDomainModel(dbData) }
        }
