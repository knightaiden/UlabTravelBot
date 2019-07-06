import net.ulab.travelbot.Application;
import net.ulab.travelbot.mapper.PatUsers;
import net.ulab.travelbot.model.PatUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

/**
 * Created by zhangzhe on 6/7/19.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class PatDataTester {

    @Autowired
    private PatUsers patUsers;

    @Test
    public void testUserProcess(){
        PatUser user = new PatUser();
        user.setFrontend_key("1");
        user.setPat_user_key("1");
        int insertResult = patUsers.insertPatUserInfo(user);
        int updateResult = patUsers.updatePatUserKeyByFID("2", "1");
        PatUser selectResult = patUsers.getPatUserByFID("1");
        assertEquals("1", selectResult.getFrontend_key());
        assertEquals("2", selectResult.getPat_user_key());
        int deleteResult = patUsers.deletePatUserById("1");
        assertEquals(1, deleteResult);
    }

}
