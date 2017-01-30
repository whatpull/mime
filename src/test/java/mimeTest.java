import org.junit.Test;
import org.whatpull.mime.annotation.FieldLog;

/**
 * Created by yeonsu on 2017-01-30
 * since 2017-01-30
 */
public class mimeTest {

    // TODO 어노테이션 목업 테스트
    @FieldLog
    private String name;

    @Test
    public void mime() {
        name = "테스트";
    }

}
