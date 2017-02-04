import org.junit.Before;
import org.junit.Test;
import org.whatpull.mime.annotation.Scheduled;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.ParseDom;

/**
 * Created by yeonsu on 2017-01-30
 * since 2017-01-30
 */
public class mimeTest {

    // TODO 어노테이션 목업 테스트
    private String name;

    @Before
    public void init() throws NoSuchFieldException, NoSuchMethodException {
        ScheduledAnnotator.setScheduled(mimeTest.class);
    }

    @Test
    @Scheduled
    public void mime() throws Exception {
        name = ParseDom.parseDom();
        System.out.println(name);
    }

}
