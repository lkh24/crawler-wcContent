package kh.science.dao;

import kh.science.entity.Content;

import java.util.List;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/24 11:47 PM
 */
public interface WebSchoolContentDao {
     void batchInsert(List<Content> contentList);

     List<Content> finAll();
}
