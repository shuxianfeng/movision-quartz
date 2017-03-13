package com.movision.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.movision.constants.ImgCompressConstants;
import com.movision.service.ImgCompressService;
import com.movision.utils.ApplicationPropertiesUtils;
import com.movision.utils.DateUtil;
import com.movision.utils.EmailUtil;

/**
 * 图片压缩任务
 * 
 * 1 测试环境，不需要下载，直接压缩图片到指定文件夹，即可 ( 无日志 )
 * 注意点：数据库中存的图片的url需要把域名替换成测试服务器的IP，把image.zhuhui8.com转化成139.196.189.100
 *
 * 2开发环境，同测试环境 没有从数据库中取数据。
 *
 * 3 生产环境， 先从文件服务器下载图片到WEB服务器， 存入临时文件夹， 然后压缩到临时文件夹，
 * 最后上传到文件服务器指定文件夹。 注意点：数据库中存的图片的url不需要更改域名，保持不变，即image.zhuhui8.com
 * 
 * @author zhuangyuhao
 * @time 2016年11月1日 下午7:13:04
 *
 */
@Service
public class ImgCompressTask {

    @Autowired
    ImgCompressService imgSV;

    private static final Logger log = LoggerFactory.getLogger(OrderTask.class);
    // 邮件接受者 "tongxl@zhuhui8.com"
    private static final String[] mailTo = { "zhuangyuhao@zhuhui8.com" };
    // 当前图片上传模式
    public static final String CUR_UPLOAD_MODE = ApplicationPropertiesUtils.getValue("upload_mode");

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void run() {
        log.info("图片压缩处理任务开始...");
        String curDateStr = DateUtil.date2Str(new Date(), "YYYY-MM-dd HH:mm:ss");
        String contentStart = EmailUtil.buildContent(CUR_UPLOAD_MODE + "图片压缩处理" + curDateStr, "开始执行");
        // 发送开始处理邮件
        EmailUtil.sendMutilMail(mailTo, contentStart, "图片压缩处理开始");
        boolean isSucc = false;
        try {
            if (ImgCompressConstants.DEV.equals(CUR_UPLOAD_MODE)) {
                // 本地开发环境
                imgSV.handleLocalImg();

            } else {
                // 测试环境 和 正式环境 共用
                imgSV.handleEnterpriseLogoImg(CUR_UPLOAD_MODE);
                imgSV.handleProductImg(CUR_UPLOAD_MODE);
                imgSV.handleShopImg(CUR_UPLOAD_MODE);
                imgSV.handleBrandImg(CUR_UPLOAD_MODE);
                imgSV.handleNewsImg(CUR_UPLOAD_MODE);
                imgSV.handleExhibitionImg(CUR_UPLOAD_MODE);
            }

            isSucc = true;
        } catch (Exception e) {
            log.error("图片压缩处理失败", e);
            e.printStackTrace();
        } finally {
            EmailUtil.sendMutilMail(mailTo, "图片压缩处理结束", "图片压缩处理" + curDateStr + " 已结束，处理结果：" + isSucc);
        }

        log.info("图片压缩处理任务结束...");
    }

}
