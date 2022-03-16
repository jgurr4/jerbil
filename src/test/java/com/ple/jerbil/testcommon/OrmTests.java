package com.ple.jerbil.testcommon;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.DatabaseBuilder;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.testcommon.orm.User;
import com.ple.jerbil.testcommon.orm.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Properties;

public class OrmTests {
  final TestDatabaseContainer testDb = DatabaseBuilder.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public OrmTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(
        props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password")
    );
  }

  //TODO: Decide whether .load() should be inside TableContainer or inside User Pojo.
  //TODO: Finish implementing load and save inside the relevant classes using external Bridge methods.
  @Test
  public void testLoad() {
    final int id = 1;
    final User user1 = user.load(id);
    final UserId userId = user1.userId;
    assertEquals("john", user1.name);
    assertEquals(14, user1.age);
  }

  @Test
  public void testSave() {
    final int id = 1;
    final User user1 = User.make(UserId.make(id), "john", 14);
    final UserId savedUserId = user1.save();
  }

  @Test
  public void testModifyAndSave() {
    final int id = 1;
    final User user1 = user.load(id);
    final User modifiedUser = user1.setName("bob").setAge(25);
    modifiedUser.save();
  }
}
