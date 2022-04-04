package com.ss.universitiesdirectory.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NetworkChangeReceiver : BroadcastReceiver() {

    @Inject
    lateinit var universityRepository: UniversitiesRepository

    private lateinit var alertDialog: AlertDialog
    private lateinit var coroutineScope: CoroutineScope

    override fun onReceive(context: Context, intent: Intent) {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivity.getNetworkCapabilities(connectivity.activeNetwork)

        if (!::coroutineScope.isInitialized) coroutineScope = CoroutineScope(Dispatchers.IO)

        if (!::alertDialog.isInitialized) alertDialog = MaterialAlertDialogBuilder(context).apply {
            this.setTitle(context.getString(R.string.connection_error))
            this.setMessage(context.getString(R.string.network_error_message))
            this.setCancelable(false)
        }.create()

        if (capabilities != null) {
            coroutineScope.launch { universityRepository.getAllUniversities() }
            alertDialog.dismiss()
        } else alertDialog.show()
    }
}