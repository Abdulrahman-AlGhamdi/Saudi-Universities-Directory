package com.ss.universitiesdirectory.ui.universities

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

@ExperimentalCoroutinesApi
class UniversitiesRepository {

    fun getUniversities() = callbackFlow {
        offer(UniversitiesState.Loading)
            FirebaseDatabase.getInstance().reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (Locale.getDefault().language == "en")
                    offer(UniversitiesState.List(snapshot.child("EN").children.toMutableList()))
                else offer(UniversitiesState.List(snapshot.child("AR").children.toMutableList()))
            }
            override fun onCancelled(error: DatabaseError) {
                offer(UniversitiesState.Failed(error.message))
            }
        })
        this.awaitClose()
    }

    sealed class UniversitiesState {
        object Loading : UniversitiesState()
        data class List(val universitiesList: MutableList<DataSnapshot>) : UniversitiesState()
        data class Failed(val message: String) : UniversitiesState()
    }
}