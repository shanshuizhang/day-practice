import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/12/11 11:45
 */
@Slf4j
public class TimeTest {



    public static void main(String[] args) {
        // 2. 检查拼团时间
        Date now = DateTime.now().plusSeconds(5).toDate();
        if(now.after(new Date())){
            log.info("时间：{}",now.getTime());
        }
    }
}
