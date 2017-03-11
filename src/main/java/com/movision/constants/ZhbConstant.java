package com.movision.constants;

/**
 * @author tongxinglong
 * @date 2017/1/11 0011.
 */
public class ZhbConstant {
    /**
     * 筑慧币流水记录的类型
     *
     * @author tongxinglong
     *
     */
    public enum ZhbRecordType {
        /**
         * 支付
         */
        PAYFOR("1"),
        /**
         * 充值
         */
        PREPAID("2"),
        /**
         * 退款
         */
        REFUND("3");

        public final String value;

        private ZhbRecordType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * 筑慧币商品类型
     */
    public enum ZhbGoodsType {
        /**
         * 到期扣除
         */
        DQKC,
        /**
         * 套餐到期返回
         */
        TCDQFH,
    }
}
