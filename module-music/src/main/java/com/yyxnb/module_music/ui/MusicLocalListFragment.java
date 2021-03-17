package com.yyxnb.module_music.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_adapter.base.BaseViewHolder;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.lib_localservice.LocalConfig;
import com.yyxnb.lib_localservice.bean.LocalFolder;
import com.yyxnb.lib_localservice.bean.LocalMedia;
import com.yyxnb.lib_localservice.manager.AudioLoaderManager;
import com.yyxnb.lib_localservice.manager.DataCallback;
import com.yyxnb.lib_music.MusicPlayerManager;
import com.yyxnb.util_permission.PermissionListener;
import com.yyxnb.util_permission.PermissionUtils;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.adapter.MusicLocalListAdapter;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.bean.MusicLocalBean;
import com.yyxnb.module_music.databinding.FragmentMusicLocalListBinding;
import com.yyxnb.module_music.db.MusicDatabase;
import com.yyxnb.module_music.viewmodel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地音乐列表.
 */
@BindRes(subPage = true)
public class MusicLocalListFragment extends BaseFragment {

    private FragmentMusicLocalListBinding binding;
    private RecyclerView mRecyclerView;
    private MusicLocalListAdapter mAdapter;

    @BindViewModel
    MusicViewModel mViewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_music_local_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.rvContent;
    }

    @Override
    public void initViewData() {
        mAdapter = new MusicLocalListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                MusicPlayerManager.getInstance().startPlayMusic(mAdapter.getData(), position);

                //情况之前的数据
                MusicDatabase.getInstance().musicDao().deleteAll();
                //点击本地音乐时，替换播放数据源
                List<MusicBean> musicBeans = new ArrayList<>(mAdapter.getData());
                MusicDatabase.getInstance().musicDao().insertItems(musicBeans);
            }
        });


    }

    @Override
    public void initObservable() {
//        mViewModel.reqLocalMusicData();

        mViewModel.getLocalMusicData().observe(this, data -> {
            if (data != null && data.size() > 0) {
                mAdapter.setDataItems(data);
            } else {
                getLocalMusicData();
            }

//            LogUtils.e("进入了 " + data);
        });

        mViewModel.getRecordData().observe(this, data -> {
            if (data != null) {
//                LogUtils.e("最后一条记录： " + data);
            }
        });
    }

    private void getLocalMusicData() {
        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.WAKE_LOCK)
                .addPermissions(Manifest.permission.VIBRATE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                        getActivity().runOnUiThread(() -> {
                            getLoaderManager().initLoader(LocalConfig.AUDIO, null, new AudioLoaderManager(getActivity(), new DataCallback() {
                                @Override
                                public void onData(ArrayList<LocalFolder> list) {
                                    if (list != null) {
                                        List<LocalMedia> localMedia = list.get(0).getLocalMedia();
                                        List<MusicLocalBean> musicBeans = new ArrayList<>();
                                        for (LocalMedia media : localMedia) {

                                            MusicLocalBean bean = new MusicLocalBean();

                                            bean.mid = media.getId() + "";
                                            bean.title = media.getTitle();
                                            bean.url = media.getPath();
                                            bean.totalTime = media.getDuration() + "";
                                            bean.author = media.getArtist();

                                            musicBeans.add(bean);
                                        }

                                        MusicDatabase.getInstance().musicLocalDao().insertItems(musicBeans);
                                        mAdapter.setDataItems(musicBeans);
                                    }
                                }
                            }));
                        });

                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    }
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission();
    }
}