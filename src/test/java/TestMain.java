import com.cose.easemob.lmc.service.TalkDataService;
import com.cose.easemob.lmc.service.impl.TalkDataServiceImpl;
import com.cose.easemob.lmc.service.impl.TalkHttpServiceImplApache;

public class TestMain {

    private static String uid_1 = "bf1627981aa943d1";
    private static String unick_1 = "频率coo";
    private static String uid_2 = "e20cd40b09a14dea";
    private static String unick_2 = "张思捷";

    public static void main(String[] args) throws Exception {
        // 通过构造方法注入http请求业务以实现数据业务
        TalkDataService talkDataService = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
        // 注册环信
        talkDataService.userSave(uid_1, uid_1, unick_1);
        talkDataService.userSave(uid_2, uid_2, unick_2);
    }

}
