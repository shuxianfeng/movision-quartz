package com.movision.mybatis.dao;

import com.movision.mybatis.mapper.AskPriceMapper;
import com.movision.mybatis.mapper.OfferPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cxx
 * @since 16/8/4.
 */
@Repository
public class PriceDao {
    private static final Logger logger = LoggerFactory.getLogger(PriceDao.class);

    @Autowired
    AskPriceMapper askPriceMapper;

    @Autowired
    OfferPriceMapper offerPriceMapper;


    public List<Map<String,String>> findLatestAskPriceList(String hour){
        try{
            return askPriceMapper.findLatestAskPriceList(hour);
        }catch (Exception e){
            logger.error("PriceDao::findLatestAskPriceList",e);
            throw e;
        }
    }

    public List<Map<String,String>> findLatestOfferPriceList(String hour) {
        try{
            return offerPriceMapper.findLatestOfferPriceList(hour);
        }catch (Exception e){
            logger.error("PriceDao::findLatestOfferPriceList",e);
            throw e;
        }
    }
}
