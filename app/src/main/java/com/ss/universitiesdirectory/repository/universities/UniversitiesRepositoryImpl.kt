package com.ss.universitiesdirectory.repository.universities

import com.google.firebase.firestore.FirebaseFirestore
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class UniversitiesRepositoryImpl @Inject constructor() : UniversitiesRepository {

    private val database = FirebaseFirestore.getInstance()

    override suspend fun getUniversities() = callbackFlow<ResponseState<List<UniversityModel>>> {
        this.trySend(ResponseState.Progress())
        val language = Locale.getDefault().language.uppercase()

        val reference = database.collection("Universities$language")
        val task = reference.get()

        task.addOnSuccessListener {
            this.trySend(ResponseState.Success(it.toObjects(UniversityModel::class.java)))
        }

        task.addOnFailureListener {
            it.localizedMessage?.let { m -> this.trySend(ResponseState.Error(m)) }
            it.printStackTrace()
        }

        this.awaitClose()
    }.flowOn(Dispatchers.IO)
}