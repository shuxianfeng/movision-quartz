package com.movision.constants;


/**
 * 订单常量
 */
public class OrderConstants {


    /**
     * 订单状态 1未支付，2：已支付，3：支付失败 ，4：待退款，5：退款中，6：退款失败，7：已退款 ，8:已关闭
     */
    public enum OrderStatus {
        WZF("1"), YZF("2"),FAIL("3"),DTK("4"), TKZ("5"), TKSB("6"), YTK("7"),CLOSED("8");
        public final String value;

        OrderStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * 商品类型 1：技术培训，2：专家培训   3：VIP服务套餐， 4:筑慧币  5:活动报名
     */
    public enum GoodsType {
        JSPX("1"), ZJPX("2"),VIP("3"),ZHB("4"),ACTIVITY_APPLY("5");
        public final String value;

        GoodsType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * 状态：1有效，2已使用，3已过期，4已取消，5未生效
     */
    public enum TicketStatus {
        YX("1"), YSY("2"),YGQ("3"),YQX("4"),WSX("5");
        public final String value;

        TicketStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * 短信状态 1：待发送  2：已发送  3：发送失败
     */
    public enum SmsStatus {
        WAITING("1"), SUCCESS("2"),FAIL("3");
        public final String value;

        SmsStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
