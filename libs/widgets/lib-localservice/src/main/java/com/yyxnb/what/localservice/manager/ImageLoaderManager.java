package com.yyxnb.what.localservice.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.yyxnb.what.localservice.R;
import com.yyxnb.what.localservice.bean.LocalFolder;
import com.yyxnb.what.localservice.bean.LocalMedia;

import java.util.ArrayList;


/**
 * 图片
 */
public class ImageLoaderManager extends LoaderM implements LoaderManager.LoaderCallbacks<Cursor> {

    String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media._ID};

    Context mContext;
    DataCallback mLoader;

    public ImageLoaderManager(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int picker_type, Bundle bundle) {
        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(
                mContext,
                queryUri,
                IMAGE_PROJECTION,
                null,
                null, // Selection args (none).
                MediaStore.Images.Media.DATE_ADDED + " DESC" // Sort order.
        );
        return cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        try {
            ArrayList<LocalFolder> localFolders = new ArrayList<>();
            LocalFolder allLocalFolder = new LocalFolder(mContext.getResources().getString(R.string.all_image));
            localFolders.add(allLocalFolder);
            while (cursor.moveToNext()) {

                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));

                if (size < 1) {
                    continue;
                }
                if (path == null || path.equals("")) {
                    continue;
                }

//                Log.d("ImageLoaderManager", mediaType +" ， "+mimeType + " ,dateTime "+ dateTime);

                String dirName = getParent(path);
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(path);
                localMedia.setTitle(title);
                localMedia.setName(name);
                localMedia.setTime(dateTime);
                localMedia.setMediaType(mediaType);
                localMedia.setSize(size);
                localMedia.setId(id);
                localMedia.setParentDir(dirName);
                localMedia.setMimeType(mimeType);
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