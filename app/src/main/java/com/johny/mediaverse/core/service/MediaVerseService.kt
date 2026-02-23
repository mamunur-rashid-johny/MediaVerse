package com.johny.mediaverse.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationHelper
import com.johny.mediaverse.R
import com.johny.mediaverse.presentation.MainActivity
import org.koin.android.ext.android.inject

@UnstableApi
class MediaVerseService : MediaSessionService() {

    private val player: ExoPlayer by inject()
    private var mediaSession: MediaSession? = null
    private val handler = Handler(Looper.getMainLooper())
    private var progressRunnable: Runnable? = null

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "media_playback_channel"
        private const val ACTION_REWIND = "com.johny.mediaverse.REWIND"
        private const val ACTION_PLAY_PAUSE = "com.johny.mediaverse.PLAY_PAUSE"
        private const val ACTION_FORWARD = "com.johny.mediaverse.FORWARD"
        private const val PROGRESS_UPDATE_INTERVAL_MS = 1000L
    }

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            postMediaNotification()
            if (isPlaying) startProgressUpdates() else stopProgressUpdates()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            postMediaNotification()
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(pendingIntent)
            .build()

        player.addListener(playerListener)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Media Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Media playback controls"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createActionPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaVerseService::class.java).apply {
            this.action = action
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.getForegroundService(
                this, action.hashCode(), intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getService(
                this, action.hashCode(), intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    private fun formatDuration(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }

    private fun postMediaNotification() {
        val session = mediaSession ?: return

        val mediaStyle = MediaStyleNotificationHelper.MediaStyle(session)
            .setShowActionsInCompactView(0, 1, 2)

        val playPauseIcon = if (player.isPlaying) R.drawable.ic_notif_pause else R.drawable.ic_notif_play
        val playPauseTitle = if (player.isPlaying) "Pause" else "Play"

        val currentPosition = player.currentPosition.coerceAtLeast(0L)
        val duration = player.duration.coerceAtLeast(0L)
        val elapsed = formatDuration(currentPosition)
        val total = formatDuration(duration)
        val contentText = if (duration > 0) "$elapsed / $total" else player.mediaMetadata.artist ?: ""

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(player.mediaMetadata.title ?: "MediaVerse")
            .setContentText(contentText)
            .setStyle(mediaStyle)
            .addAction(R.drawable.ic_notif_rewind, "Rewind", createActionPendingIntent(ACTION_REWIND))
            .addAction(playPauseIcon, playPauseTitle, createActionPendingIntent(ACTION_PLAY_PAUSE))
            .addAction(R.drawable.ic_notif_forward, "Forward", createActionPendingIntent(ACTION_FORWARD))
            .setOngoing(player.isPlaying)

        if (duration > 0) {
            builder.setProgress(duration.toInt(), currentPosition.toInt(), false)
        }

        val notification = builder.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceCompat.startForeground(
                this,
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun startProgressUpdates() {
        stopProgressUpdates()
        progressRunnable = object : Runnable {
            override fun run() {
                if (player.isPlaying) {
                    postMediaNotification()
                    handler.postDelayed(this, PROGRESS_UPDATE_INTERVAL_MS)
                }
            }
        }
        handler.postDelayed(progressRunnable!!, PROGRESS_UPDATE_INTERVAL_MS)
    }

    private fun stopProgressUpdates() {
        progressRunnable?.let { handler.removeCallbacks(it) }
        progressRunnable = null
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        postMediaNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                if (player.isPlaying) player.pause() else player.play()
                return START_STICKY
            }
            ACTION_REWIND -> {
                player.seekTo((player.currentPosition - 10_000).coerceAtLeast(0))
                return START_STICKY
            }
            ACTION_FORWARD -> {
                player.seekTo((player.currentPosition + 10_000).coerceAtMost(player.duration))
                return START_STICKY
            }
        }

        super.onStartCommand(intent, flags, startId)
        postMediaNotification()
        return START_STICKY
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        stopProgressUpdates()
        player.removeListener(playerListener)
        mediaSession?.run {
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}