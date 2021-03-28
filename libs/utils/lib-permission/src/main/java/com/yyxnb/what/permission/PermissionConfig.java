package com.yyxnb.what.permission;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @desc: 添加描述
 */
public class PermissionConfig implements Parcelable {
    //必须要所有的权限都通过才能通过
    private boolean forceAllPermissionsGranted;
    //设置用户点击不再提示之后的弹窗文案
    private String forceDeniedPermissionTips;

    public String getForceDeniedPermissionTips() {
        return forceDeniedPermissionTips;
    }

    public PermissionConfig setForceDeniedPermissionTips(String forceDeniedPermissionTips) {
        this.forceDeniedPermissionTips = forceDeniedPermissionTips;
        return this;
    }

    private PermissionUtils check;

    public PermissionConfig(PermissionUtils check) {
        this.check = check;
    }

    public boolean isForceAllPermissionsGranted() {
        return forceAllPermissionsGranted;
    }

    public PermissionConfig setForceAllPermissionsGranted(boolean forceAllPermissionsGranted) {
        this.forceAllPermissionsGranted = forceAllPermissionsGranted;
        return this;
    }

    public PermissionUtils buildConfig() {
        return check;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.forceAllPermissionsGranted ? (byte) 1 : (byte) 0);
        dest.writeString(this.forceDeniedPermissionTips);
    }

    protected PermissionConfig(Parcel in) {
        this.forceAllPermissionsGranted = in.readByte() != 0;
        this.forceDeniedPermissionTips = in.readString();
    }

    public static final Creator<PermissionConfig> CREATOR = new Creator<PermissionConfig>() {
        @Override
        public PermissionConfig createFromParcel(Parcel source) {
            return new PermissionConfig(source);
        }

        @Override
        public PermissionConfig[] newArray(int size) {
            return new PermissionConfig[size];
        }
    };
}