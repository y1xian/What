package com.yyxnb.what.contact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.core.app.ActivityCompat;

public class ContactImpl implements IContact {

    @Override
    public void contactPermission(Context context) {
        String[] strings = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions((Activity) context, strings, 1);
    }

    @Override
    public void openContact(Context context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public String[] getContact(Context context, Uri uri) {
        // 指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        StringBuilder builder = new StringBuilder();
        String[] arr = new String[2];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                builder.append(name).append("%=%");

                //根据联系人的ID获取此人的电话号码
                @SuppressLint("Recycle") Cursor phonesCursor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);


                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCursor != null && phonesCursor.moveToFirst()) {
                    do {
                        String num = phonesCursor.getString(0);
                        builder.append(num).append(";");

                    } while (phonesCursor.moveToNext());
                }
                i++;
            } while (cursor.moveToNext());

            // 关闭游标
            cursor.close();
        }

        // 分割联系人与手机号，且去掉手机号最后的;
        arr = builder.toString().substring(0, builder.length() - 1).split("%=%");
        return arr;
    }

    @Override
    public String[] getContacts(Context context) {
        // 联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        // 指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        String[] arr = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                arr[i] = name + "=";

                //根据联系人的ID获取此人的电话号码
                @SuppressLint("Recycle") Cursor phonesCursor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCursor != null && phonesCursor.moveToFirst()) {
                    do {
                        String num = phonesCursor.getString(0);
                        arr[i] += num + ";";

                    } while (phonesCursor.moveToNext());
                }
                i++;
            } while (cursor.moveToNext());

            // 关闭游标
            cursor.close();
        }
        return arr;
    }


}
