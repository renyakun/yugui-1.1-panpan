import java.sql.Date;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class TestString {
    public static void main(String[] args) {
        OffsetDateTime time = OffsetDateTime.now();
        System.out.println(time);

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts);

        Date day = new Date(System.currentTimeMillis());
        System.out.println(day);




    }
}
