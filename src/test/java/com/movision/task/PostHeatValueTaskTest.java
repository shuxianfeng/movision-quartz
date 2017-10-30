package com.movision.task;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseSpringContext;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/30 14:28
 */
public class PostHeatValueTaskTest extends BaseSpringContext {

    @Autowired
    private PostHeatValueTask postHeatValueTask;

    @Test
    public void testAdd() throws Exception {

        postHeatValueTask.addPostHeatvalueRecord(2314, 50);
    }

}