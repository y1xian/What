package com.yyxnb.what.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.Collection;
import java.util.List;

@Dao
public interface BaseDao<T> {

    /*
     * 查询
     * 注意，冒号后面必须紧跟参数名，中间不能有空格。大于小于号和冒号中间是有空格的。
     * SELECT count(*) FROM 【表名】 获取条数
     * SELECT * from 【表名】WHERE【表中列名】 =:【参数名】------>等于
     * WHERE 【表中列名】 < :【参数名】 小于
     * WHERE 【表中列名】 between :【参数名1】 and :【参数2】------->这个区间
     * WHERE 【表中列名】like :参数名----->模糊查询
     * WHERE 【表中列名】 in (:【参数名集合】)---->查询符合集合内指定字段值的记录
     * Order by 【表中列名】asc 是指定列按升序排列，desc 则是指定列按降序排列
     * COLLATE NOCASE 不区分大小写
     *
     * 如果明知道查询结果只有一个,SQL语句中使用 LIMIT 1 会提高查询效率, 索引字段不需要
     * limit y 分句表示: 读取 y 条数据
     * limit x, y 分句表示: 跳过 x 条数据，读取 y 条数据
     * limit y offset x 分句表示: 跳过 x 条数据，读取 y 条数据
     *
     * INNER JOIN【表名】ON
     *
     * UPDATE【表名】SET xxx WHERE
     *
     * DELETE FROM【表名】WHERE =:【参数名】
     */

    /*
    //策略冲突就替换旧数据
    int REPLACE = 1;
    //策略冲突就回滚事务
    int ROLLBACK = 2;
    //策略冲突就退出事务
    int ABORT = 3;
    //策略冲突就使事务失败
    int FAIL = 4;
    //忽略冲突
    int IGNORE = 5;
     */

    /**
     * 插入单条数据
     * 指定为REPLACE替换原有数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertItem(T item);

    /**
     * 插入list数据
     * 指定为REPLACE替换原有数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertItems(Collection<T> items);

    /**
     * 删除item
     */
    @Delete
    int deleteItem(T item);

    /**
     * 删除items
     */
    @Delete
    int deleteItem(Collection<T> items);

    /**
     * 更新item
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateItem(T item);

    /**
     * 更新items
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateItem(Collection<T> items);

}