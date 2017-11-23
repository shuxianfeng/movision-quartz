package com.movision.task;

import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/11/22 17:18
 */
@Service
public class DauStatisticTask {

    private static final Logger log = LoggerFactory.getLogger(DauStatisticTask.class);

    @Autowired
    private UserService userService;

    public void run() {
        log.info("统计前一天的日活用户数据...start...");

        //统计前一天的日活用户数据
        int count = userService.dauStatistic();
        //存入数据库中
        Map<String, Object> parammap = new HashMap<>();

        //获取前一天的日期
        Date intime = new Date();//当前日期和时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(intime);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        Date date = calendar.getTime();

        parammap.put("date", date);
        parammap.put("usersum", count);
        parammap.put("intime", intime);
        userService.updateDauStatistic(parammap);

        log.info("统计前一天的日活用户数据...end...");
    }
}
