package com.yyxnb.module_novel.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_novel.bean.BookShelfBean;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface BookShelfDao extends BaseDao<BookShelfBean> {


    @Query("SELECT * FROM bookShelf ORDER BY addTime DESC")
    List<BookShelfBean> getAllList();

    @Query("DELETE from bookShelf WHERE bookId = :bookId")
    void delete(int bookId);

    @Query("SELECT * from bookShelf WHERE bookId = :bookId")
    BookShelfBean getDataById(int bookId);

    /**
     * 是否在书架
     * @param bookId
     * @return
     */
    @Query("SELECT 1 from bookShelf WHERE bookId = :bookId limit 1")
    boolean isInBookshelf(int bookId);

    @Query("UPDATE bookShelf SET hasUpdate = :hasUpdate , addTime = :updateTime WHERE bookId = :bookId")
    void updateHasUpdate(int bookId, boolean hasUpdate, long updateTime);

//    public boolean exists(int bookId) {
//        TbBookShelf tbBookShelf = getEntity(bookId);
//        return tbBookShelf != null;
//    }
//
//    public void addOrUpdate(TbBookShelf tbBookShelf) {
//        if (tbBookShelf == null || tbBookShelf.bookId < 1)
//            return;
//
//        try {
//            TbBookShelf existsTbBookShelf = getEntity(tbBookShelf.bookId);
//            if (existsTbBookShelf != null) {
//                tbBookShelf.id = existsTbBookShelf.id;
//                update(tbBookShelf);
//            } else {
//                insert(tbBookShelf);
//            }
//        } catch (Exception ex) {
//            UtilityException.catchException(ex);
//        }
//    }
//
//    /**
//     * 删除书架上的书，同时删除目录中的书
//     *
//     * @param bookId
//     */
//    public void deleteByBookId(int bookId) {
//        try {
//            delete(bookId);
//
//            AppDatabase.getInstance().ChapterDao().delete(bookId);
//        } catch (Exception ex) {
//            UtilityException.catchException(ex);
//        }
//    }
}