package com.movision.mybatis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.entity.ImgCompressLog;
import com.movision.mybatis.mapper.ImgCompressLogMapper;
/**
 * 
 * @author zhuangyuhao
 * @time   2016年10月31日 下午2:02:16
 *
 */
@Repository
public class ImgCompressLogDao {
	
	private static final Logger log = LoggerFactory.getLogger(ImgCompressLogDao.class);

    @Autowired
    ImgCompressLogMapper imgCompressLogMapper;
	
	public void insert(ImgCompressLog imglog) {
        int count;
        try {
            count = imgCompressLogMapper.insertSelective(imglog);
            if(count != 1){
                log.error("插入{}失败","t_img_compress_log");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入{}失败","t_img_compress_log");
        }
    }
}
