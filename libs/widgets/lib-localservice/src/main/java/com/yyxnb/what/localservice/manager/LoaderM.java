package com.yyxnb.what.localservice.manager;


import com.yyxnb.what.localservice.bean.LocalFolder;

import java.util.ArrayList;


public class LoaderM {

    public String getParent(String path) {
        String sp[] = path.split("/");
        return sp[sp.length - 2];
    }

    public int hasDir(ArrayList<LocalFolder> localFolders, String dirName) {
        for (int i = 0; i < localFolders.size(); i++) {
            LocalFolder localFolder = localFolders.get(i);
            if (localFolder.name.equals(dirName)) {
                return i;
            }
        }
        return -1;
    }


}
