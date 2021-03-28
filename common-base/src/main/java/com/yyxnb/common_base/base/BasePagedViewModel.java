package com.yyxnb.common_base.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.yyxnb.what.arch.viewmodel.BaseViewModel;

/**
 * Paging
 * @param <T>
 */
public abstract class BasePagedViewModel<T> extends BaseViewModel {

    protected PagedList.Config config;
    private DataSource dataSource;
    private LiveData<PagedList<T>> pageData;
    private int pageSize = 10;
    private int initialSize = pageSize * 2;

    private MutableLiveData<Boolean> boundaryPageData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
    }

    public BasePagedViewModel() {

        config = new PagedList.Config.Builder()
                // 配置分页加载的数量
                .setPageSize(pageSize)
                // 第一次加载多少数据，必须是分页加载数量的倍数
                .setInitialLoadSizeHint(initialSize)
                // 距离底部还有多少条数据时开始预加载
                // .setPrefetchDistance(pageSize / 2)
                // 最大数量 明确情况下
                // .setMaxSize(100)；
                // 是否启用占位符，若为true，则视为固定数量的item
                // .setEnablePlaceholders(true)
                // 设置距离最后还有多少个item时，即寻呼库开始加载下一页的数据
                // .setPrefetchDistance(10)
                .build();

        // 将PagedList和LiveData整合成LiveData<PagedList>
        //noinspection unchecked
        pageData = new LivePagedListBuilder(factory, config)
                // 可通过 pagedList.getLastKey() 获取此值，默认值当然为 Key(这里为Integer)类型的初始化值()这里为0
                .setInitialLoadKey(0)
                // 设置获取数据源的线程
//                .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
                .setBoundaryCallback(callback)
                .build();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    /**
     * 有数据则触发回调
     */
    public LiveData<PagedList<T>> getPageData() {
        return pageData;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 是否有数据 true则有
     */
    public LiveData<Boolean> getBoundaryPageData() {
        return boundaryPageData;
    }

    //PagedList数据被加载 情况的边界回调callback
    //但 不是每一次分页 都会回调这里，具体请看 ContiguousPagedList#mReceiver#onPageResult
    //deferBoundaryCallbacks
    PagedList.BoundaryCallback<T> callback = new PagedList.BoundaryCallback<T>() {
        @Override
        public void onZeroItemsLoaded() {
            //新提交的PagedList中没有数据
            boundaryPageData.postValue(false);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            //新提交的PagedList中第一条数据被加载到列表上
            boundaryPageData.postValue(true);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
            //新提交的PagedList中最后一条数据被加载到列表上
        }
    };

    //数据源
    DataSource.Factory factory = new DataSource.Factory() {
        @NonNull
        @Override
        public DataSource create() {
            if (dataSource == null || dataSource.isInvalid()) {
                dataSource = createDataSource();
            }
            return dataSource;
        }
    };

    /**
     * （1）PageKeyedDataSource：如果页面在加载时插入一个/下一个键，例如：从网络获取社交媒体的帖子，可能需要将nextPage加载到后续的加载中；
     * <p>
     * （2）ItemKeyedDataSource：在需要让使用的数据的item从N条增加到N+1条时使用，列如：根据key获取数据；
     * <p>
     * （3）PositionalDataSource：如果需要从数据存储的任意位置来获取数据页面。此类支持你从任何位置开始请求一组item的数据集。
     * 例如，该请求可能会返回从位置1200条开始的20个数据项；
     */
    public abstract DataSource createDataSource();


    //可以在这个方法里 做一些清理 的工作
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
