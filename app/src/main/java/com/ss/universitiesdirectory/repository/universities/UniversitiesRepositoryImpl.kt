package com.ss.universitiesdirectory.repository.universities

import com.google.firebase.firestore.FirebaseFirestore
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class UniversitiesRepositoryImpl @Inject constructor() : UniversitiesRepository {

    private val database = FirebaseFirestore.getInstance()
    private var universities: List<UniversityModel> = mutableListOf()

    override suspend fun getUniversities() = callbackFlow<ResponseState<List<UniversityModel>>> {
        this.trySend(ResponseState.Progress())
        val language = Locale.getDefault().language.uppercase()

        val reference = database.collection("Universities$language")
        val task = reference.get()

        task.addOnSuccessListener {
            universities = it.toObjects(UniversityModel::class.java)
            this.trySend(ResponseState.Success(universities))
        }

        task.addOnFailureListener {
            it.localizedMessage?.let { m -> this.trySend(ResponseState.Error(m)) }
            it.printStackTrace()
        }

        this.awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun getSearchList(query: String): Flow<ResponseState<List<UniversityModel>>> = flow {
        val searchList = if (query.isNotBlank()) universities.filter {
            if (!it.province) it.name.lowercase().contains(query.lowercase()) else false
        } else universities

        this.emit(ResponseState.Success(searchList))
    }
}