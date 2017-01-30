import org.junit.Before;
import org.junit.Test;
import org.whatpull.mime.annotation.FieldLog;
import org.whatpull.mime.annotation.FieldLogAnnotator;

import javax.annotation.PostConstruct;

/**
 * Created by yeonsu on 2017-01-30
 * since 2017-01-30
 */
public class mimeTest {

    // TODO 어노테이션 목업 테스트
    @FieldLog
    private String name;

    @Before
    public void init() throws NoSuchFieldException {
        FieldLogAnnotator.setFieldLog(mimeTest.class);
    }

    @Test
    public void mime() {
        name = "테스트";
        System.out.println(name);
    }

}
