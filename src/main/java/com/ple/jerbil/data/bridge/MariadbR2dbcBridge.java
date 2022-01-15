package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.DataBridge;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.translator.MysqlLanguageGenerator;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.jetbrains.annotations.Nullable;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Immutable
public class MariadbR2dbcBridge implements DataBridge {

  public final LanguageGenerator generator = MysqlLanguageGenerator.make();
  public final String driver;
  public final String host;
  public final int port;
  public final String username;
  public final String password;
  @Nullable public final String database;
  @Nullable public final ConnectionPool pool;

  protected MariadbR2dbcBridge(String driver, String host, int port, String username, String password, @Nullable String database, @Nullable ConnectionPool pool) {
    this.driver = driver;
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
    this.pool = pool;
  }

  //TODO: Consider creating another make() method that allows developers to skip using the driver parameter. Since it's not required for MariadbR2dbc connector. Or just get rid of the driver parameter entirely unless I find a use for it.
  public static MariadbR2dbcBridge make(String driver, String host, int port, String username, String password, String database, ConnectionPool pool) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database, pool);
  }

  public static DataBridge make(String driver, String host, int port, String username, String password, String database) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database, null);
  }

  public static DataBridge make(String driver, String host, int port, String username, String password) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, null, null);
  }

  @Override
  public LanguageGenerator getGenerator() {
    return generator;
  }

  @Override
  public Flux<Result> execute(String sql) {
    final MariadbR2dbcBridge bridge = this.createConnectionPool();
    final Connection connection = bridge.pool.create().block();
    final Statement statement = connection.createStatement(sql);
    return Flux.from(statement.execute());
  }

  private MariadbR2dbcBridge createConnectionPool() {
    MariadbR2dbcBridge bridge = null;
    try {
      final MariadbConnectionConfiguration.Builder builder = MariadbConnectionConfiguration.builder()
        .host(host)
        .port(port)
        .username(username)
        .allowMultiQueries(true)
        .password(password);
      if (database != null) {
        builder.database(database);
      }
      final MariadbConnectionConfiguration conf = builder.build();
      final MariadbConnectionFactory factory = MariadbConnectionFactory.from(conf);
      final ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration
        .builder(factory)
        .maxIdleTime(Duration.ofMillis(1000))
        .maxSize(20)
        .build();
      bridge = MariadbR2dbcBridge.make(driver, host, port, username, password, database, new ConnectionPool(poolConfig));
    } catch (IllegalArgumentException e) {
      System.err.println("Issue creating connection pool");
      e.printStackTrace();
    } finally {
      bridge.pool.close();
    }
    return bridge;
  }

}
