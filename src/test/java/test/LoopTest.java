package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.movision.constants.ImgCompressConstants;
import com.movision.service.ImgCompressService;


public class LoopTest extends BaseSpringContext{

	@Autowired
	ImgCompressService imgSV;

	private static final Logger log = LoggerFactory.getLogger(LoopTest.class);

	@Test
	@Transactional   //标明此方法需使用事务  
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void loopTest(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		log.info("压缩产品图片， 开始");
		String mode = ImgCompressConstants.TEST;
		while(true){
			list = imgSV.queryProductImgList(mode);
			if (CollectionUtils.isEmpty(list)) {
				break;
			}
			imgSV.compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_PRODUCT, ImgCompressConstants.PC_DIR_PRODUCT_200_200,
					ImgCompressConstants.W_200, ImgCompressConstants.H_200, ImgCompressConstants.IMG_200_200, ImgCompressConstants.PRODUCT, mode);
			imgSV.compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_PRODUCT, ImgCompressConstants.M_DIR_PRODUCT_240_240,
					ImgCompressConstants.W_240, ImgCompressConstants.H_240, ImgCompressConstants.IMG_240_240, ImgCompressConstants.PRODUCT, mode);

			list.clear();
		}
		log.info("压缩产品图片， 结束");
	}
}
