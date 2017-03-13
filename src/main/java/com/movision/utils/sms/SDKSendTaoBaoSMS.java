package com.movision.utils.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SDKSendTaoBaoSMS {

    private static Logger log = LoggerFactory.getLogger(SDKSendTaoBaoSMS.class);

    private static final String TAOBAO_CLIENT_URL = "http://gw.api.taobao.com/router/rest";
    private static final String APPKEY = "23361295";
    private static final String SECRET = "752b6bcb411e07baf34e11e0b4ddb767";


    /**
     * 发送短信
     *
     * @param mobile       短信接收号码 (支持单个或多个手机号码) 英文逗号分隔 最多200个
     * @param params       短信模板参数
     * @param templateCode 短信模板编码
     * @return 成功失败 {true|false}
     */
    public static Boolean sendSMS(String mobile, String params, String templateCode){
        TaobaoClient client = new DefaultTaobaoClient(TAOBAO_CLIENT_URL, APPKEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        try {
            req.setExtend("123456");
            req.setSmsType("normal");
            req.setSmsFreeSignName("筑慧宝");
            req.setSmsParamString(params);
            req.setRecNum(mobile);
            req.setSmsTemplateCode(templateCode);
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            log.info("send sms response:" + rsp.getBody());
            return rsp.getResult().getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信异常");
            return false;
        }

    }

}
