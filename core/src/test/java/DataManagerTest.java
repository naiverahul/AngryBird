import com.badlogic.drop.user.DataManager;
import com.badlogic.drop.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {

    @Test
    public void testSingletonInstance() {
        DataManager instance1 = DataManager.getInstance();
        DataManager instance2 = DataManager.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testAddUser() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.addUser("TestUser");
        assertTrue(dataManager.userExists("TestUser"));
    }

    @Test
    public void testGetUser() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.addUser("TestUser");
        User user = dataManager.getUser("TestUser");
        assertNotNull(user);
        assertEquals("TestUser", user.getName());
    }

    @Test
    public void testSaveAndLoadData() {
        DataManager dataManager = DataManager.getInstance();
        dataManager.addUser("TestUser");
        dataManager.saveData();
        DataManager newDataManager = DataManager.getInstance();
        assertTrue(newDataManager.userExists("TestUser"));
    }
}
