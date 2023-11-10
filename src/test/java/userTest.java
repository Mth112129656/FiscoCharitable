
import org.example.demo.util.Utils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userTest {


    @Test
    public void validEmail() {
        boolean res = Utils.isValidEmail("bcp22@12.com");
        System.out.print(res);
    }
}
