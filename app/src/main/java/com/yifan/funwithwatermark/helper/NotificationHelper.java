package com.yifan.funwithwatermark.helper;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.weli.mediaplayer.R;
import cn.weli.mediaplayer.bean.SongData;
import cn.weli.mediaplayer.constant.SongsConstant;
import cn.weli.mediaplayer.manager.MediaManager;
import cn.weli.mediaplayer.module.main.MainActivity;

import static android.app.Notification.CATEGORY_MESSAGE;
import static cn.weli.mediaplayer.MediaApplication.appCtx;

/**
 * 通知栏工具类(兼容低版本).
 */
public class NotificationHelper extends ContextWrapper {
    private NotificationManager mManager;

    public NotificationHelper() {
        super(appCtx);
    }

    /**
     * 判断通知权限是否打开(api >= 19)
     *
     * @param context context
     * @return 通知权限是否打开
     */
    public static boolean isNotificationEnable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return isNotificationEnable24(context);
        } else {
            return isNotificationEnable19(context);
        }
    }

    /**
     * 判断通知权限是否打开(api >= 24)
     *
     * @param context context
     * @return 通知权限是否打开
     */
    private static boolean isNotificationEnable24(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(
                context);
        return notificationManagerCompat.areNotificationsEnabled();
    }

    /**
     * 判断通知权限是否打开(api >= 19)
     *
     * @param context context
     * @return 通知权限是否打开
     */
    private static boolean isNotificationEnable19(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();

        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        // Context.APP_OPS_MANAGER
        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE,
                    Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
                    == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 发送音乐通知
     *
     * @param id
     * @param songData
     */
    public void sendNotification(int id, SongData songData) {

        Intent intent = new Intent(appCtx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(appCtx, SongsConstant.REQUEST_CODE_START_ACTIVITY, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {       //适配高版本
            createChannel();
            Notification notification = getNotification(songData, pendingIntent).build();
//            notification.contentView.setInt(R.id.notification_container, "setBackgroundColor", Color.WHITE);        //通过反射设置背景颜色
            getManager().notify(id, notification);
        } else {
            Notification notification = getNotificationBelow25(songData, pendingIntent).build();
//            notification.contentView.setInt(R.id.notification_container, "setBackgroundColor", Color.WHITE);        //通过反射设置背景颜色
            getManager().notify(id, notification);
        }

    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(SongsConstant.mMusicId, SongsConstant.mMusicChannel,
                NotificationManager.IMPORTANCE_HIGH);   //通知级别，共5个
        channel.enableLights(false);     //是否在桌面icon展示小红点
        channel.enableVibration(false);
        channel.setSound(null, null);   //关了通知默认提示音
        channel.setShowBadge(false);    //是否在久按桌面图标时显示此渠道的通知
        channel.setVibrationPattern(new long[]{0});
        getManager().createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getNotification(SongData songData, PendingIntent pendingIntent) {

        RemoteViews remoteViews = initNotifyView(songData);

        return new Notification.Builder(getApplicationContext(), SongsConstant.mMusicId)
                .setOngoing(true)
                .setOnlyAlertOnce(false)
                .setAutoCancel(false)   //点击不让消失
                .setSound(null)     //关了通知默认提示音
                .setVibrate(new long[]{0})   //关了震
                .setContentIntent(pendingIntent)//整个点击跳转activity安排上
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setPriority(Notification.PRIORITY_MAX) //咱们通知很重要
                .setSmallIcon(R.drawable.icon_music)    //这玩意在通知栏上显示一个logo
                .setContent(remoteViews)    //把自定义view放上
                .setCategory(CATEGORY_MESSAGE);
    }

    private NotificationCompat.Builder getNotificationBelow25(SongData songData, PendingIntent pendingIntent) {
        RemoteViews remoteViews = initNotifyView(songData);
        return new NotificationCompat.Builder(getApplicationContext())
                .setOngoing(true)
                .setOnlyAlertOnce(false)
                .setAutoCancel(false)   //点击不让消失
                .setSound(null)     //关了通知默认提示音
                .setVibrate(new long[]{0})   //关了震
                .setContentIntent(pendingIntent)//整个点击跳转activity安排上
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setPriority(Notification.PRIORITY_MAX) //咱们通知很重要
                .setSmallIcon(R.drawable.icon_music)    //这玩意在通知栏上显示一个logo
                .setContent(remoteViews)    //把自定义view放上
                .setCategory(CATEGORY_MESSAGE);
    }

    /**
     * 自定义RemoteView
     *
     * @param songData
     * @return
     */
    private RemoteViews initNotifyView(SongData songData) {
        String packageName = appCtx.getPackageName();
        RemoteViews remoteViews = new RemoteViews(packageName, R.layout.notification_layout);

        remoteViews.setImageViewBitmap(R.id.music_img, MediaHelper.getSongAlbumBitmap(MediaHelper.isPlaySongData.songMediaId
                , MediaHelper.isPlaySongData.albumId));
        remoteViews.setTextViewText(R.id.noti_music_name, songData.songName);
        remoteViews.setTextViewText(R.id.noti_music_nauthor, songData.singer);

        if (!MediaManager.getInstance().mMediaPlayer.isPlaying()) {        //音乐停止
            remoteViews.setViewVisibility(R.id.img_status_play, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.img_status_pause, View.GONE);
        } else if (MediaManager.getInstance().mMediaPlayer.isPlaying()) {          //音乐播放
            remoteViews.setViewVisibility(R.id.img_status_play, View.GONE);
            remoteViews.setViewVisibility(R.id.img_status_pause, View.VISIBLE);
        } else if (MediaManager.getInstance().isPause()) {          //音乐暂停
            remoteViews.setViewVisibility(R.id.img_status_play, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.img_status_pause, View.GONE);
        }

        //播放
        Intent play = new Intent(SongsConstant.ACTION_PLAY_MUSIC);
        PendingIntent intent_play = PendingIntent.getBroadcast(appCtx, SongsConstant.REQUEST_CODE_PLAY, play, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_status_play, intent_play);

        //暂停
        Intent pause = new Intent(SongsConstant.ACTION_PAUSE_MUSIC);    //暂停
        PendingIntent intent_pause = PendingIntent.getBroadcast(appCtx, SongsConstant.REQUEST_CODE_PAUSE, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_status_pause, intent_pause);

        //上一首
        Intent last = new Intent(SongsConstant.ACTION_PLAY_LAST_MUSIC);    //播放上一首
        PendingIntent intent_prv = PendingIntent.getBroadcast(appCtx, SongsConstant.REQUEST_CODE_LAST, last, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_lastsong, intent_prv);

        //下一首
        Intent next = new Intent(SongsConstant.ACTION_PLAY_NEXT_MUSIC);    //播放下一首
        PendingIntent intent_next = PendingIntent.getBroadcast(appCtx, SongsConstant.REQUEST_CODE_NEXT, next, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_nextsong, intent_next);

        return remoteViews;
    }

}
