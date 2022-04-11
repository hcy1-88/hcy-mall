import com.hcy.dto.OrderForUser;
import com.hcy.mapper.OrderMapper;
import com.hcy.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/3 1:31
 */
@SpringBootTest(classes = {MyTest.class})
@RunWith(SpringRunner.class)
@WebAppConfiguration
@MapperScan("com.hcy.mapper")
public class MyTest {
    @Autowired
    private OrderService orderService;
    
    @Test
    public void test01(){
    }
}
