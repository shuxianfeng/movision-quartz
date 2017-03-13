import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuangyuhao
 * @since 16/6/1.
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        String start = "2016-06-01 08:00:10";
        String end = "2016-06-01 08:30:00";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date one = format.parse(start);
        Date two = format.parse(end);

        Long time1 = one.getTime();
        Long time2 = two.getTime();

        System.out.println(time1);
        System.out.println(time2);
        System.out.println((time2-time1)/1000/60);
    }
}
