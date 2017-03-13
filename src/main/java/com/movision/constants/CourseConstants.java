package com.movision.constants;


/**
 * 课程常量
 */
public class CourseConstants {


    /**
     * 课程状态  状态：1未上架，2销售中，3待开课，4开课，5已终止，6已完成
     */
    public enum Status {
        WSJ("1"), XSZ("2"), DKK("3"), KK("4"), YZZ("5"),YWC("6");
        public final String value;

        Status(String value) {
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
        YX("1"), YSY("2"), YGQ("3"), YQX("4"), WSX("5");
        public final String value;

        TicketStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
