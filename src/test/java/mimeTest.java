import org.junit.Before;
import org.junit.Test;
import org.whatpull.mime.annotation.FieldLog;
import org.whatpull.mime.annotation.FieldLogAnnotator;
import org.whatpull.mime.annotation.Scheduled;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.ParseDom;

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
    public void init() throws NoSuchFieldException, NoSuchMethodException {
        FieldLogAnnotator.setFieldLog(mimeTest.class);
        ScheduledAnnotator.setScheduled(mimeTest.class);
    }

    @Test
    @Scheduled
    public void mime() throws Exception {
        name = ParseDom.parseDom();
        System.out.println(name);
    }

}
