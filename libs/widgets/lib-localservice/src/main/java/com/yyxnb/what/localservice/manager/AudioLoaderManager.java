package com.yyxnb.what.localservice.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.yyxnb.what.localservice.R;
import com.yyxnb.what.localservice.bean.LocalFolder;
import com.yyxnb.what.localservice.bean.LocalMedia;

import java.util.ArrayList;

/**
 * 音频 音乐
 */
public class AudioLoaderManager extends LoaderM implements LoaderManager.LoaderCallbacks<Cursor> {
    String[] MEDIA_PROJECTION = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.MIME_TYPE};

    Context mContext;
    DataCallback mLoader;

    public AudioLoaderManager(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int picker_type, Bundle bundle) {

        Uri queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                MEDIA_PROJECTION,
                null,
                null, // Selection args (none).
                MediaStore.Audio.Media.DATE_ADDED + " DESC" // 默认倒序.
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        try {
            ArrayList<LocalFolder> localFolders = new ArrayList<>();
            LocalFolder allLocalFolder = new LocalFolder(mContext.getResources().getString(R.string.all_audio));
            localFolders.add(allLocalFolder);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                if (size < 1 || size > 10000000L) {
                    continue;
                }
                if (path == null || path.equals("")) {
                    continue;
                }

                Log.d("AudioLoaderManager", path + " ，" + name + " ， " + id + " ， " + mimeType);

                String dirName = getParent(path);
                LocalMedia localMedia = new LocalMedia();
                localMedia.setTitle(title);
                localMedia.setPath(path);
                localMedia.setName(name);
                localMedia.setTime(dateTime);
                localMedia.setSize(size);
                localMedia.setId(id);
                localMedia.setMediaType(mediaType);
                localMedia.setParentDir(dirName);
                localMedia.setArtist(artist);
                localMedia.setMimeType(mimeType);
                localMedia.setDuration(duration);
                allLocalFolder.addMedias(localMedia);

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
