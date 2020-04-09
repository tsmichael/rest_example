import io.qameta.allure.Description;
import org.testng.annotations.Test;
public class BaseTest {

    @Test(description = "lol1")
    @Description("LOL1")
    public void test1() {
        System.out.println("Kilimanjaro");
    }


    @Test(description = "lol2")
    @Description("LOL2")
    public void test2() {
        System.out.println(21+21);
    }


    @Test(description = "lol3")
    @Description("LOL3")
    public void test3() {
        System.out.println("Kioto");
    }
}
