package kh.science.mapper;

import kh.science.dao.WebSchoolContentDao;
import kh.science.entity.Content;
import kh.science.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/25 12:34 AM
 */
public class ContentMapper {
    /**
     * 批量添加
     * @param contentList
     */
    public void batchInsertMapper(List<Content> contentList) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            WebSchoolContentDao mapper = session.getMapper(WebSchoolContentDao.class);
            mapper.batchInsert(contentList);
            session.commit();
        }
    }

    /**
     * 查看所有
     */
    public List<Content>  findListMapper() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            WebSchoolContentDao mapper = session.getMapper(WebSchoolContentDao.class);
            return mapper.finAll();
        }
    }
}
