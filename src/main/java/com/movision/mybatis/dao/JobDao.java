package com.movision.mybatis.dao;

import com.movision.mybatis.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 职位相关接口实现类
 *
 * @author zhuangyuhao
 * @date 2016年12月21日
 */
@Repository
public class JobDao {

    @Autowired
    private JobMapper jobMapper;

    public int getResumeCount(Map query) {
        return jobMapper.getResumeCount(query);
    }

    public List<Map<String, Integer>> selectExpireDownloadResume() {
        return jobMapper.selectExpireDownloadResume();
    }

    public void deleteDownloadRecord(String id) {
        jobMapper.deleteDownloadRecord(id);
    }

    public List<Integer> selectViewGoods(Map<String, Integer> map) {
        return jobMapper.selectViewGoods(map);
    }

    public void deleteViewGoods(String id) {
        jobMapper.deleteViewGoods(id);
    }

    public int getFbzwCount(Map query) {
        return jobMapper.getFbzwCount(query);
    }
}
