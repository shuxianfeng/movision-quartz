package com.movision.service;

import com.movision.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @Author zhuangyuhao
 * @Date 2017/5/16 11:20
 */
@Service
public class TestRedisService {

    private static Logger log = LoggerFactory.getLogger(TestRedisService.class);

    @CacheEvict(value = "indexData", key = "'index_data'")
    public void deleteRedisKey() {
        log.debug("当前时间是：" + DateUtil.getCurrentTime());
        log.debug("【清除首页数据】清除key=index_data的缓存");
    }


}
