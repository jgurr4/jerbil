package com.ple.jerbil;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.bridge.MariadbR2dbcBridge;
import com.ple.jerbil.data.builder.DatabaseBuilderOld;
import com.ple.jerbil.data.selectExpression.Literal;
import com.ple.jerbil.data.selectExpression.booleanExpression.And;
import com.ple.jerbil.testcommon.*;
import com.ple.jerbil.testcommon.DBO.User;
import com.ple.jerbil.testcommon.DBO.UserId;
import com.ple.observabilityBridge.JaegerHandler;
import com.ple.observabilityBridge.PrometheusHandler;
import com.ple.observabilityBridge.RecordingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

public class DBOTests {
  final TestDatabaseContainer testDb = DatabaseBuilderOld.generate(TestDatabaseContainer.class, "test");
  final UserTableContainer user = testDb.user;
  final ItemTableContainer item = testDb.item;
  final PlayerTableContainer player = testDb.player;
  final InventoryTableContainer inventory = testDb.inventory;
  final OrderTableContainer order = testDb.order;

  public DBOTests() {
    final Properties props = ConfigProps.getProperties();
    DataGlobal.bridge = MariadbR2dbcBridge.make(props.getProperty("host"), Integer.parseInt(props.getProperty("port")),
        props.getProperty("user"), props.getProperty("password"), RecordingService.make(PrometheusHandler.only,
            JaegerHandler.only));
  }

  //TODO: Finish implementing load and save inside the relevant classes using external Bridge methods.
  @Test
  public void testSave() {
    // Manual method:  (This is done by the simpler method behind the scenes.)
    user.insert().set(user.age, Literal.make(14)).set(user.name, Literal.make("john")).execute().unwrap();
    final DbResult userResult = user.select().where(And.and(user.name.eq(Literal.make("john")), user.age.eq(Literal.make(14))))
        .execute().unwrap();
    final User userAlt = User.make(userResult);
    final UserId userIdAlt = userAlt.userId;

    // Simpler method:
    final User user1 = User.make("john", 14);
    final UserId userId = user1.save();
    final User loadedFromId = userId.load();
    assertEquals("john", loadedFromId.name);
    assertEquals(14, loadedFromId.age);
  }

  @Test
  public void testLoad() {
    final DbResult userResult = user.select().where(And.and(user.name.eq(Literal.make("john")), user.age.eq(Literal.make(14))))
        .execute().unwrap();
//    final User user1 = user.load("john", 14);         //Consider supporting this method of loading. Weigh the pros and cons.
    final User user1 = User.make(userResult);
    final User loadedFromId = user1.userId.load();
    assertEquals("john", loadedFromId.name);
    assertEquals(14, loadedFromId.age);
  }

  @Test
  public void testModifyAndSave() {
    final DbResult userResult = user.select().where(And.and(user.name.eq(Literal.make("john")), user.age.eq(Literal.make(14))))
        .execute().unwrap();
    final User user1 = User.make(userResult);
    final User modifiedUser = user1.setName("bob").setAge(25);
    final UserId userId = modifiedUser.save();
    final User currentUser = userId.load();
    assertEquals(userId, user1.userId);
    assertEquals(14, user1.age);
    assertEquals("john", user1.name);
    assertEquals(25, currentUser.age);
    assertEquals("bob", currentUser.name);
  }
}
