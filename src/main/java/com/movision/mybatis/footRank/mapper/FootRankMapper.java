package com.movision.mybatis.footRank.mapper;

import com.movision.mybatis.footRank.entity.FootRank;
import org.springframework.stereotype.Repository;

@Repository
public interface FootRankMapper {
    int insert(FootRank record);

    int insertSelective(FootRank record);

    void update(FootRank footRank);
}