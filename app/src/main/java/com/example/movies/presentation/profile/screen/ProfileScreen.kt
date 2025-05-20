package com.example.movies.presentation.profile.screen

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.movies.model.ProfileViewModel
import com.example.movies.presentation.profile.utils.SystemBroadcastReceiver
import org.koin.androidx.compose.koinViewModel
import java.io.File


const val DOWNLOAD_COMPLETE_ACTION = "android.intent.action.DOWNLOAD_COMPLETE"

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController)  {

    val context = LocalContext.current
    InitBroadcastReceiver(context)

    val viewModel = koinViewModel<ProfileViewModel>()
    val state = viewModel.viewState

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.profile))
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        Modifier
                            .padding(end = 8.dp)
                            .clickable { navController.navigate("edit")}
                    )
                }
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = state.photoUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(128.dp),
                error = painterResource(R.drawable.person)
            )
            Text(text = state.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Самый лучший пользователь", style = MaterialTheme.typography.bodyMedium)
            Button(onClick = {
                enqueueDownloadRequest(
                    "https://blankum.ru/download/resume1.pdf",
                    context
                )
            }) {
                Text(text = "резюме")
            }
        }
    }
}

@Composable
private fun InitBroadcastReceiver(context: Context) {
    SystemBroadcastReceiver(systemAction = DOWNLOAD_COMPLETE_ACTION, onSystemEvent = { intent ->
        if (intent?.action == DOWNLOAD_COMPLETE_ACTION) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                navigateToDownloadedInvoice(context)
            }
        }
    })
}

private fun enqueueDownloadRequest(
    url: String, context: Context
) {
    val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
    with(request) {
        setTitle("Test pdf")
        setMimeType("application/pdf")
        setDescription("Downloading pdf...")
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, "test1.pdf"
        )
    }
    val manager: DownloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

private fun navigateToDownloadedInvoice(context: Context) {
    try {
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ), "Test1.pdf"
        )
        val uri = FileProvider.getUriForFile(
            context, context.applicationContext?.packageName + ".provider", file
        )
        val intent = Intent(Intent.ACTION_VIEW)
        with(intent) {
            setDataAndType(
                uri, "application/pdf"
            )
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}