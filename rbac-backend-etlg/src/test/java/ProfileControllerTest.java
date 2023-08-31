import com.imyuanxiao.rbac.controller.api.ProfileController;
import org.junit.jupiter.api.*;

import static com.imyuanxiao.rbac.util.CommonConst.ACTION_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileControllerTest {

    private static ProfileController profileController;

    @BeforeAll
    public static void setUp() {
        // 在此处初始化ProfileController实例
        profileController = new ProfileController();
    }

    @Test
    public void testAddUserProfile() {
        // 编写测试添加用户配置的代码
        // 例如，模拟一个PlayerDataParam对象，并调用addUserProfile方法进行测试
        // 验证方法是否返回ACTION_SUCCESSFUL
        String playerDataJson = "{\"userId\": 49, \"spaceshipScore\": 1100, \"achievementPoint\": 50, \"chapterNum\": 8}";
        try {
            String result = profileController.addUserProfile(playerDataJson);
            assertEquals(ACTION_SUCCESSFUL, result);
        } catch (SQLException e) {
            fail("SQLException occurred during test: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUserProfile() {
        // 编写测试更新用户配置的代码
        // 例如，模拟一个PlayerDataParam对象，并调用addUserProfile方法进行测试
        // 验证方法是否返回ACTION_SUCCESSFUL
        String playerDataJson = "{\"userId\": 1, \"spaceshipScore\": 101, \"achievementPoint\": 57, \"chapterNum\": 3}";
        try {
            String result = profileController.addUserProfile(playerDataJson);
            assertEquals(ACTION_SUCCESSFUL, result);
        } catch (SQLException e) {
            fail("SQLException occurred during test: " + e.getMessage());
        }
    }

    @Test
    public void testGetPlayerSaving() {
        // 编写测试获取用户配置的代码
        // 例如，模拟一个用户ID，并调用getPlayerSaving方法进行测试
        // 验证方法返回的playerData是否符合预期
        int userId = 3;
        try {
            String playerData = profileController.getPlayerSaving(userId);
            assertEquals("1", playerData); // 假设playerData初始值为"1"
        } catch (SQLException e) {
            fail("SQLException occurred during test: " + e.getMessage());
        }
    }

    @Test
    public void testGetRank() {
        // 编写测试获取排名列表的代码
        // 例如，调用GetRank方法获取排名列表，并验证返回的结果是否符合预期
        int pageNumber = 1;
        int pageSize = 10;
        int rankMode = 0; // 假设rankMode为0，表示按分数排序
        try {
            List<List<Object>> rankList = profileController.GetRank(pageNumber, pageSize, rankMode);
            assertNotNull(rankList);
            assertFalse(rankList.isEmpty());

            List<List<Object>> playerData = new ArrayList<>();
            List<Object> row1 = Arrays.asList("user", 49, 1100, 50, 8);
            playerData.add(row1);
            List<Object> row2 = Arrays.asList("player1", 3, 1000, 150, 0);
            playerData.add(row2);
            List<Object> row3 = Arrays.asList("player2", 4, 800, 200, 0);
            playerData.add(row3);
            List<Object> row4 = Arrays.asList("player3", 5, 700, 100, 0);
            playerData.add(row4);
            List<Object> row5 = Arrays.asList("player4", 6, 600, 50, 0);
            playerData.add(row5);
            List<Object> row6 = Arrays.asList("player5", 7, 500, 250, 0);
            playerData.add(row6);
            List<Object> row7 = Arrays.asList("admin", 1, 101, 57, 3);
            playerData.add(row7);
            //验证rankList的内容是否符合预期
            assertEquals(rankList, playerData);

        } catch (SQLException e) {
            fail("SQLException occurred during test: " + e.getMessage());
        }
    }
}
