package com.yyxnb.what.localservice.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.yyxnb.what.localservice.LocalConfig;
import com.yyxnb.what.localservice.R;
import com.yyxnb.what.localservice.bean.LocalFolder;
import com.yyxnb.what.localservice.bean.LocalMedia;

import java.util.ArrayList;

/**
 * 图片 + 视频
 */
public class MediaLoaderManager extends LoaderM implements LoaderManager.LoaderCallbacks<Cursor> {
    String[] MEDIA_PROJECTION = {
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns._ID,
            MediaStore.Video.VideoColumns.DURATION,
            MediaStore.Video.VideoColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.PARENT};

    Context mContext;
    DataCallback mLoader;

    public MediaLoaderManager(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        try {
            ArrayList<LocalFolder> localFolders = new ArrayList<>();
            LocalFolder allLocalFolder = new LocalFolder(mContext.getResources().getString(R.string.all_dir_name));
            localFolders.add(allLocalFolder);
            LocalFolder allVideoDir = new LocalFolder(mContext.getResources().getString(R.string.video_dir_name));
            localFolders.add(allVideoDir);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.MIME_TYPE));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION));

                if (size < 1 || size > LocalConfig.DEFAULT_SELECTED_MAX_SIZE) {
                    continue;
                }
                if (path == null || path.equals("")) {
                    continue;
                }
                if (mimeType.contains("video") && duration < 2000) {
                    continue;
                }

//                Log.d("VideoLoaderManager", name + "," +  mimeType);

                String dirName = getParent(path);
                LocalMedia localMedia = new LocalMedia();
                localMedia.setTitle(title);
                localMedia.setPath(path);
                localMedia.setName(name);
                localMedia.setTime(dateTime);
                localMedia.setMediaType(mediaType);
                localMedia.setSize(size);
                localMedia.setId(id);
                localMedia.setParentDir(dirName);
                localMedia.setDuration(duration);
                localMedia.setMimeType(mimeType);
                allLocalFolder.addMedias(localMedia);
                if (mediaType == 3) {
                    allVideoDir.addMedias(localMedia);
                }

                int index = hasDir(localFolders, dirName);
                if (index != -1) {
                    localFolders.get(index).addMedias(localMedia);
                } else {
                    LocalFolder localFolder = new LocalFolder(dirName);
                    localFolder.addMedias(localMedia);
                    localFolders.add(localFolder);
                }
            }
            mLoader.onData(localFolders);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
