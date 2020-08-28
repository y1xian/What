package com.yyxnb.module_novel.view.page;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-4-29.
 */

public class RxUtils {

    public static <T> SingleSource<T> toSimpleSingle(Single<T> upstream){
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableSource<T> toSimpleSingle(Observable<T> upstream){
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T,R> TwoTuple<T,R> twoTuple(T first,R second){
        return new TwoTuple<T, R>(first, second);
    }

//    public static <NovelLibraryListBean> Single<DetailBean<NovelLibraryListBean>> toCommentDetail(Single<NovelLibraryListBean> detailSingle,
//                                                            Single<List<CommentBean>> bestCommentsSingle,
//                                                            Single<List<CommentBean>> commentsSingle){
//        return Single.zip(detailSingle, bestCommentsSingle, commentsSingle,
//                new Function3<NovelLibraryListBean, List<CommentBean>, List<CommentBean>, DetailBean<NovelLibraryListBean>>() {
//                    @Override
//                    public DetailBean<NovelLibraryListBean> apply(NovelLibraryListBean t, List<CommentBean> commentBeen,
//                                               List<CommentBean> commentBeen2) throws Exception {
//                        return new DetailBean<NovelLibraryListBean>(t,commentBeen,commentBeen2);
//                    }
//                });
//    }

    public static class TwoTuple<A, B> {
        public final A first;
        public final B second;

        public TwoTuple(A a, B b) {
            this.first = a;
            this.second = b;
        }
    }
}
