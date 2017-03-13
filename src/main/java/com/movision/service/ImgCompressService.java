package com.movision.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.movision.constants.ImgCompressConstants;
import com.movision.mybatis.dao.BrandDao;
import com.movision.mybatis.dao.ExhibitionDao;
import com.movision.mybatis.dao.ImgCompressLogDao;
import com.movision.mybatis.dao.MemberDao;
import com.movision.mybatis.dao.NewsDao;
import com.movision.mybatis.dao.ProductDao;
import com.movision.mybatis.dao.ShopDao;
import com.movision.mybatis.entity.ImgCompressLog;
import com.movision.utils.FileUtils;
import com.movision.utils.ImgCompressUtil;
import com.movision.utils.ImgUtil;
import com.movision.utils.image.ImageUtils;
import com.movision.utils.oss.AliOSSClient;

@Transactional
@Service
public class ImgCompressService {
    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ImgCompressLogDao imgCompressLogDao;

    @Autowired
    ShopDao shopDao;

    @Autowired
    BrandDao brandDao;

    @Autowired
    NewsDao newsDao;

    @Autowired
    ExhibitionDao exhibitionDao;

    @Autowired
    AliOSSClient aliossClient;

    private static final Logger log = LoggerFactory.getLogger(ImgCompressService.class);

    /**
     * 活动列表图片
     */
    public void handleExhibitionImg(String mode) {
        log.info("2 压缩活动图片， 开始");
        List<Map<String, Object>> list;
        while (true) {
            list = queryExhibitionImgList(mode);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_EXHIBITION, ImgCompressConstants.PC_DIR_EXHIBITION_270_176, ImgCompressConstants.W_270, ImgCompressConstants.H_176,
                    ImgCompressConstants.IMG_270_176, ImgCompressConstants.EXHIBITION, mode);
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_EXHIBITION, ImgCompressConstants.M_DIR_EXHIBITION_386_252, ImgCompressConstants.W_386, ImgCompressConstants.H_252,
                    ImgCompressConstants.IMG_386_252, ImgCompressConstants.EXHIBITION, mode);

            list.clear();
        }
        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.PC_DIR_EXHIBITION_270_176));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_EXHIBITION_386_252));
        }

        log.info("2 压缩活动图片， 结束");
    }

    /**
     * 获取活动（会展）图片集合
     *
     * @return
     */
    public List<Map<String, Object>> queryExhibitionImgList(String mode) {
        log.info("1 获取活动图片集合，开始");
        List<Map<String, Object>> list = exhibitionDao.selectImg();
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String imgUrl = (String) map.get("imgUrl");
                genNewList(newList, map, imgUrl, mode);
            }
        } else {
            log.error("会展信息表中无图片数据");
        }
        log.info("1 获取活动图片集合，结束");
        return newList;
    }

    /**
     * 处理资讯
     */
    public void handleNewsImg(String mode) {
        log.info("压缩资讯图片， 开始");
        List<Map<String, Object>> list;
        while (true) {
            list = queryNewsImgList(mode);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_NEWS, ImgCompressConstants.M_DIR_NEWS_386_252, ImgCompressConstants.W_386, ImgCompressConstants.H_252,
                    ImgCompressConstants.IMG_386_252, ImgCompressConstants.NEWS, mode);

            list.clear();
        }

        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_NEWS_386_252));
        }
        log.info("压缩资讯图片， 结束");
    }

    /**
     * 获取资讯图片集合
     *
     * @return
     */
    public List<Map<String, Object>> queryNewsImgList(String mode) {
        log.info("1 获取资讯图片集合，结束");
        List<Map<String, Object>> list = newsDao.selectImg();
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String imgUrl = (String) map.get("photo");
                genNewList(newList, map, imgUrl, mode);
            }
        } else {
            log.error("资讯表中无图片数据");
        }
        log.info("1 获取资讯图片集合，结束");
        return newList;
    }

    /**
     * 处理品牌图片
     */
    public void handleBrandImg(String mode) {

        List<Map<String, Object>> list;
        log.info("压缩品牌图片， 开始");
        while (true) {
            list = queryBrandImgList(mode);

            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            // 品牌列表logo
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_BRAND, ImgCompressConstants.PC_DIR_BRAND_182_92, ImgCompressConstants.W_182, ImgCompressConstants.H_92,
                    ImgCompressConstants.IMG_182_92, ImgCompressConstants.BRAND, mode);
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_BRAND, ImgCompressConstants.M_DIR_BRAND_424_240, ImgCompressConstants.W_424, ImgCompressConstants.H_240,
                    ImgCompressConstants.IMG_424_240, ImgCompressConstants.BRAND, mode);
            // 品牌详情页logo
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_BRAND, ImgCompressConstants.M_DIR_BRAND_412_184, ImgCompressConstants.W_412, ImgCompressConstants.H_184,
                    ImgCompressConstants.IMG_412_184, ImgCompressConstants.BRAND, mode);

            list.clear();
        }
        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.PC_DIR_BRAND_182_92));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_BRAND_424_240));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_BRAND_412_184));
        }
        log.info(" 压缩品牌图片， 结束");
    }

    /**
     * 获取品牌图片集合
     *
     * @return
     */
    public List<Map<String, Object>> queryBrandImgList(String mode) {
        log.info("1 获取品牌图片集合，开始");
        List<Map<String, Object>> list = brandDao.selectImg();
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String imgUrl = (String) map.get("logourl");
                genNewList(newList, map, imgUrl, mode);
            }
        } else {
            log.error("品牌表中无此品牌的logo图片");
        }
        log.info("1 获取品牌图片集合，结束");
        return newList;
    }

    /**
     * 处理商铺的图片
     */
    public void handleShopImg(String mode) {

        List<Map<String, Object>> list;

        List<Map<String, Object>> mobile_banner_url_t_list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mobile_banner_url_s_list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> mobile_banner_url_f_list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> bannerUrl_list = new ArrayList<Map<String, Object>>();

        // 小事务循环处理数据，每次500条
        log.info(" 压缩商铺图片， 开始");
        while (true) {
            list = shopDao.selectImg();
            // 每次取500条数据，放到容器中
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            log.info("获取商铺图片集合，开始");
            for (Map<String, Object> map : list) {
                chooseImgToAddList(mobile_banner_url_t_list, map, "mobile_banner_url_t", mode);
                chooseImgToAddList(mobile_banner_url_s_list, map, "mobile_banner_url_s", mode);
                chooseImgToAddList(mobile_banner_url_f_list, map, "mobile_banner_url_f", mode);
                chooseImgToAddList(bannerUrl_list, map, "bannerUrl", mode);
            }
            log.info("获取商铺图片集合，结束");
            compressPcAndMobilePic(mobile_banner_url_t_list, mode);
            compressPcAndMobilePic(mobile_banner_url_s_list, mode);
            compressPcAndMobilePic(mobile_banner_url_f_list, mode);
            compressPcAndMobilePic(bannerUrl_list, mode);
            // 处理完则，清除容器
            list.clear();
        }

        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.PC_DIR_PRODUCT_200_200));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_SHOP_505_320));
        }
        log.info(" 压缩商铺图片， 结束");
    }

    /**
     * 压缩PC端和手机端的商铺图片
     *
     * @param list
     */
    public void compressPcAndMobilePic(List<Map<String, Object>> list, String mode) {
        compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_SHOP, ImgCompressConstants.PC_DIR_SHOP_200_200, ImgCompressConstants.W_200, ImgCompressConstants.H_200,
                ImgCompressConstants.IMG_200_200, ImgCompressConstants.SHOP, mode);
        compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_SHOP, ImgCompressConstants.M_DIR_SHOP_505_320, ImgCompressConstants.W_505, ImgCompressConstants.H_320,
                ImgCompressConstants.IMG_505_320, ImgCompressConstants.SHOP, mode);
    }

    /**
     * 选取指定的值加入集合 ,alioss
     *
     * @param list
     * @param map
     */
    public void chooseImgToAddList4Alioss(List<Map<String, Object>> list, Map<String, Object> map, String key) {
        String imgUrl = (String) map.get(key);
        if (StringUtils.isNotEmpty(imgUrl)) {
            // 转换域名为测试环境的IP
            map.put("imgPath", imgUrl);
            list.add(map);
        }
    }

    /**
     * 选取map中指定的字段，添加进入list
     * 
     * @param newList
     * @param map
     * @param key
     * @param mode
     * @return
     */
    public List<Map<String, Object>> chooseImgToAddList(List<Map<String, Object>> newList, Map<String, Object> map, String key, String mode) {
        String imgUrl = (String) map.get(key);
        if (StringUtils.isNotEmpty(imgUrl)) {
            genNewList(newList, map, imgUrl, mode);
        }
        return newList;
    }

    /**
     * 处理产品搜索列表产品图
     */
    public void handleProductImg(String mode) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        log.info("压缩产品图片， 开始");
        while (true) {
            list = queryProductImgList(mode);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_PRODUCT, ImgCompressConstants.PC_DIR_PRODUCT_200_200, ImgCompressConstants.W_200, ImgCompressConstants.H_200,
                    ImgCompressConstants.IMG_200_200, ImgCompressConstants.PRODUCT, mode);
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_PRODUCT, ImgCompressConstants.M_DIR_PRODUCT_240_240, ImgCompressConstants.W_240, ImgCompressConstants.H_240,
                    ImgCompressConstants.IMG_240_240, ImgCompressConstants.PRODUCT, mode);

            list.clear();
        }
        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.PC_DIR_PRODUCT_200_200));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_PRODUCT_240_240));
        }
        log.info("压缩产品图片， 结束");
    }

    /**
     * 获取产品图片集合
     *
     * @return
     */
    public List<Map<String, Object>> queryProductImgList(String mode) {
        log.info("1 获取产品图片集合，开始");
        List<Map<String, Object>> list = productDao.selectImg();
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String imgUrl = (String) map.get("imgUrl");
                genNewList(newList, map, imgUrl, mode);
            }
        } else {
            log.error("产品表中查不到imgUrl");
        }
        log.info("1 获取产品图片集合，结束");
        return newList;
    }

    /**
     * 图片处理：下载图片+压缩+上传
     *
     * @param urlList
     * @param saveDirectory
     *            下载的目标文件夹
     * @param w
     * @param h
     * @param parDiv
     * @param chann
     */
    /*
     * @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
     * Exception.class) public void HandleImg(List<Map<String, Object>> urlList,
     * String sourceTable, String saveDirectory, int w, int h, String parDiv,
     * String chann) { // 压缩后的图片存的文件夹路径 String compress_dir_path = saveDirectory
     * + System.getProperty("file.separator") + "afterCompress";
     * log.info("compress_dir_path=" + compress_dir_path);
     * ImgUtil.validationDir(compress_dir_path); List<String> existFileList =
     * getExistFiles(compress_dir_path); // 遍历图片集合 boolean compressFlag = false;
     * // 遍历图片集合 String size = w+"_"+h; for (Map<String, Object> m : urlList) {
     * try { // 获取图片文件名 String urlString = (String)m.get("enterpriseLogo");
     * String filename = FileUtils.getPicName(urlString); log.info("filename=" +
     * filename); // 1 下载图片 String filePath = ImgUtil.downloadImg(saveDirectory,
     * urlString, filename); // 2 生成压缩图片的文件夹路径 // 压缩后的图片的url String
     * compress_file_path = compress_dir_path +
     * System.getProperty("file.separator") + filename; log.info("压缩后的图片的url=" +
     * compress_file_path); // 3 判断该文件夹下是否有同名的图片，若有则不处理，若没有则压缩 if
     * (CollectionUtils.isEmpty(existFileList) ||
     * !existFileList.contains(filename)) { log.info("压缩了这张图片，filename=" +
     * filename); compressFlag = ImgCompressUtil.ImgCompress(filePath,
     * compress_file_path, w, h); existFileList.add(filename); } else {
     * log.info("该图片已存在，不需要压缩，filename=" + filename); } // 4 记录图片压缩日志
     * addImgCompressLog(compressFlag, m, sourceTable, size); // 5 上传alioss服务器
     * aliossClient.uploadFileStream(filePath, parDiv, chann); } catch
     * (Exception e) { log.error("图片压缩失败", e); } } // 删除临时文件夹和图片
     * FileUtils.deleteDir(new File(saveDirectory)); }
     */

    /**
     * 处理会员表企业log图片
     */
    public void handleEnterpriseLogoImg(String mode) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        log.info("压缩企业logo图片， 开始");
        // 小事务循环处理数据，每次500条
        while (true) {
            // 每次取500条数据，放到容器中
            list = queryEnterpriseLogoList(mode);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_MEMBER, ImgCompressConstants.PC_DIR_MEMBER_192_108, ImgCompressConstants.W_192, ImgCompressConstants.H_108,
                    ImgCompressConstants.IMG_192_108, ImgCompressConstants.MEMBER, mode);
            compressImgList4MapList(list, ImgCompressConstants.IMAGE_TYPE_MEMBER, ImgCompressConstants.M_DIR_MEMBER_412_184, ImgCompressConstants.W_412, ImgCompressConstants.H_184,
                    ImgCompressConstants.IMG_412_184, ImgCompressConstants.MEMBER, mode);
            // 处理完则，清除容器
            list.clear();
        }
        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            // 删除临时文件夹和图片
            FileUtils.deleteDir(new File(ImgCompressConstants.PC_DIR_MEMBER_192_108));
            FileUtils.deleteDir(new File(ImgCompressConstants.M_DIR_MEMBER_412_184));
        }
        log.info("压缩企业logo图片， 结束");
    }

    /**
     * 查询企业log图片集合
     *
     * @return
     */
    public List<Map<String, Object>> queryEnterpriseLogoList(String mode) {
        log.info("1 获取企业logo图片集合，开始");
        List<Map<String, Object>> lsit = memberDao.selectEnterpriseLogo();
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

        if (lsit != null && lsit.size() > 0) {

            for (Map<String, Object> map : lsit) {
                String imgUrl = (String) map.get("enterpriseLogo");
                genNewList(newList, map, imgUrl, mode);
            }
        } else {
            log.error("会员表t_m_member中查不到enterpriseLogo");
        }
        log.info("1 获取企业logo图片集合，结束");
        return newList;
    }

    /**
     * 生成新的list
     * 
     * @param newList
     * @param map
     * @param imgUrl
     * @param mode
     *            上传模式
     */
    public void genNewList(List<Map<String, Object>> newList, Map<String, Object> map, String imgUrl, String mode) {
        // 原始的imgUrl
        map.put("imgUrl", imgUrl);
        if (mode.equals(ImgCompressConstants.TEST)) {
            if (imgUrl.contains(ImgCompressConstants.IMAGE_ZHUHUI8_COM)) {
                // 转换域名为测试环境的IP
                map.put("imgPath", imgUrl.replace(ImgCompressConstants.IMAGE_ZHUHUI8_COM, ImgCompressConstants.TEST_ZHB_IP));

            } else {
                map.put("imgPath", imgUrl);
            }
        }
        if (mode.equals(ImgCompressConstants.ALIOSS)) {
            map.put("imgPath", imgUrl);
        }

        newList.add(map);
    }

    /**
     * 压缩图片到指定路径文件夹 测试环境
     *
     * @param pathList
     *            Map集合
     * @param saveDirectory
     * @param w
     * @param h
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void compressImgList4MapList(List<Map<String, Object>> pathList, String sourceTable, String saveDirectory, int w, int h, String parDiv, String chann, String mode) {

        List<String> existFileList = getExistFiles(saveDirectory);
        // 判断保存图片的路径是否存在，若不存在则创建
        ImgUtil.validationDir(saveDirectory);

        // 压缩后的图片存的文件夹路径
        // compress_dir_path=/upload/member/img_412_184/afterCompress
        String compress_dir_path = saveDirectory + "/after_compress";
        log.info("compress_dir_path=" + compress_dir_path);
        ImgUtil.validationDir(compress_dir_path);

        // 遍历图片集合
        log.info("需要处理的图片数量为：" + pathList.size());
        String des_size = w + "_" + h; // 目标图片大小
        for (Map<String, Object> map : pathList) {
            boolean compressFlag = false;
            try {
                // 获取图片文件名
                String imgPaths = StringUtils.trimToEmpty((String) map.get("imgPath"));
                // 考虑到产品图片等为多图情况
                String[] imgPathArray = imgPaths.split(";");
                for (String imgPath : imgPathArray) {
                    if (StringUtils.isBlank(imgPath)) {
                        continue;
                    }

                    String filename = FileUtils.getPicName(imgPath);
                    log.info("filename=" + filename);

                    // 1 下载图片
                    String filePath = ImgUtil.downloadImg(saveDirectory, imgPath, filename);
                    // 判断图片是否下载成功
                    if (StringUtils.isNotEmpty(filePath)) {

                        // log.info("下载后保存的图片路径，filepath ="+filePath);
                        // 2 生成压缩后的图片的url
                        String compress_file_path = compress_dir_path + "/" + filename;
                        // log.info("压缩后的图片路径，compress_file_path=" +
                        // compress_file_path);

                        // 3 判断该文件夹下是否有同名的图片，若有则不处理，若没有则进行处理
                        if (CollectionUtils.isEmpty(existFileList) || !existFileList.contains(filename)) {
                            // 压缩核心算法
                            compressFlag = compressJpgOrPng(w, h, compressFlag, filename, filePath, compress_file_path);
                            // 处理过的图片加入到已处理集合，防止重复压缩图片
                            existFileList.add(filename);
                        } else {
                            compressFlag = true;
                            log.info("该图片已存在，不需要压缩，filename=" + filename);
                        }
                        if (compressFlag && mode.equals(ImgCompressConstants.ALIOSS)) {
                            // 上传
                            aliossClient.uploadFileStream(filePath, parDiv, chann);
                        }
                    }
                }
            } catch (FileNotFoundException e1) {
                log.error("需要下载的图片不存在", e1);
            } catch (Exception e2) {
                log.error("图片压缩失败", e2);
            } catch (Throwable e) {
                log.error("报错", e);
            } finally {
                // 4 压缩结果入库
                addImgCompressLog(compressFlag, map, sourceTable, des_size);
            }
        }

    }

    public boolean compressJpgOrPng(int w, int h, boolean compressFlag, String filename, String filePath, String compress_file_path) {
        if (filename.endsWith(".jpg")) {

            log.info("压缩jpg图片，filepath=" + filePath);
            compressFlag = ImgCompressUtil.ImgCompress(filePath, compress_file_path, w, h);
        }
        if (filename.endsWith(".png")) {
            File srcFile = new File(filePath);
            log.info("压缩png图片,filepath=" + filePath);
            compressFlag = ImageUtils.fromFile(srcFile).size(w, h).quality(0.7f).fixedGivenSize(false).keepRatio(true) // 图片宽高比例
                    .bgcolor(null) // 透明背景
                    .toFile(new File(compress_file_path));
        }
        return compressFlag;
    }

    /**
     * 获取制定文件夹下的文件 不分类型，即png,jpg都有
     * 
     * @param saveDirectory
     * @return
     */
    public List<String> getExistFiles(String saveDirectory) {
        List<String> existFileList = new ArrayList<String>();
        try {
            existFileList = FileUtils.readfileName(saveDirectory, null);
            log.info("指定路径下的图片有：" + existFileList.toArray().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existFileList;
    }

    /**
     * 入库操作，先判断成功或失败压缩
     * 
     * @param compressFlag
     * @param map
     * @param sourceTable
     * @param size
     */
    public void addImgCompressLog(boolean compressFlag, Map<String, Object> map, String sourceTable, String size) {

        ImgCompressLog imgCompressLog = new ImgCompressLog();
        imgCompressLog.setSourceTable(sourceTable);
        imgCompressLog.setImgUrl((String) map.get("imgUrl"));
        imgCompressLog.setProcessTime(new Date());
        imgCompressLog.setDesSize(size);

        imgCompressLog.setRelationId((Long) map.get("id"));

        if (compressFlag) {
            imgCompressLog.setCompressFlag(1);
            // log.info("成功压缩一张图片");
        } else {
            imgCompressLog.setCompressFlag(0);
            // log.info("失败压缩一张图片");
        }
        // log.info("新增的记录，imgCompressLog = " + imgCompressLog.toString());
        imgCompressLogDao.insert(imgCompressLog);
        // log.info("t_img_compress_log，新增一条记录成功");
    }

    /**
     * 处理本地图片
     */
    public void handleLocalImg() {
        String path1 = "F:/Download_pic/img/5811f657N0374b9f5.jpg"; // 图片不存在
        String path2 = "F:/Download_pic/img/5816e870N2153f53e.jpg";
        ArrayList<String> list = new ArrayList<>();
        list.add(path1);
        list.add(path2);

        String saveDirectory = "F:/Download_pic/img_100_100";

        ImgUtil.compressImgList(list, saveDirectory, 100, 100);
    }
}
