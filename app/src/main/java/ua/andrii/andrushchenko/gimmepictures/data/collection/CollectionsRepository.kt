package ua.andrii.andrushchenko.gimmepictures.data.collection

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ua.andrii.andrushchenko.gimmepictures.data.common.PAGE_SIZE
import ua.andrii.andrushchenko.gimmepictures.models.Collection
import ua.andrii.andrushchenko.gimmepictures.models.Photo
import ua.andrii.andrushchenko.gimmepictures.util.BackendResult
import ua.andrii.andrushchenko.gimmepictures.util.backendRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionsRepository @Inject constructor(private val collectionsService: CollectionsService) {

    fun getAllCollections(order: CollectionsPagingSource.Companion.Order): LiveData<PagingData<Collection>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CollectionsPagingSource(collectionsService, order) }
        ).liveData

    fun getCollectionPhotos(collectionId: Int): LiveData<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CollectionPhotosPagingSource(collectionsService, collectionId) }
        ).liveData

    suspend fun updateCollection(
        id: Int,
        title: String?,
        description: String?,
        isPrivate: Boolean
    ): BackendResult<Collection> = backendRequest {
        collectionsService.updateCollection(id, title, description, isPrivate)
    }

    suspend fun deleteCollection(id: Int): BackendResult<Unit> = backendRequest {
        collectionsService.deleteCollection(id)
    }
}