package com.movision.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 */
@Component
public class Constants {

    @Value("${course_order_invalid_time}")
    private Long courseOrderInvalidTime;

    @Value("${zhb_order_invalid_time}")
    private Long zhbOrderInvalidTime;

    public Long getCourseOrderInvalidTime() {
        return courseOrderInvalidTime;
    }

    public void setCourseOrderInvalidTime(Long courseOrderInvalidTime) {
        this.courseOrderInvalidTime = courseOrderInvalidTime;
    }

    public Long getZhbOrderInvalidTime() {
        return zhbOrderInvalidTime;
    }

    public void setZhbOrderInvalidTime(Long zhbOrderInvalidTime) {
        this.zhbOrderInvalidTime = zhbOrderInvalidTime;
    }
}
