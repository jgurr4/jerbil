package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.DataBridge;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.translator.MysqlLanguageGenerator;
import org.jetbrains.annotations.Nullable;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.mariadb.r2dbc.api.MariadbConnection;
import org.mariadb.r2dbc.api.MariadbResult;
import org.mariadb.r2dbc.api.MariadbStatement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Immutable
public class MariadbR2dbcBridge implements DataBridge {

  public final LanguageGenerator generator = MysqlLanguageGenerator.make();
  public final String driver;
  public final String host;
  public final int port;
  public final String username;
  public final String password;
  @Nullable public final String database;

  //TODO: Consider creating another make() method that allows developers to skip using the driver parameter. Since it's not required for MariadbR2dbc connector. Or just get rid of the driver parameter entirely unless I find a use for it.
  protected MariadbR2dbcBridge(String driver, String host, int port, String username, String password, @Nullable String database) {
    this.driver = driver;
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.database = database;
  }

  public static DataBridge make(String driver, String host, int port, String username, String password, String database) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, database);
  }

  public static DataBridge make(String driver, String host, int port, String username, String password) {
    return new MariadbR2dbcBridge(driver, host, port, username, password, null);
  }

  @Override
  public LanguageGenerator getGenerator() {
    return generator;
  }

  @Override
  public Flux<MariadbResult> executeQuery(String toSql) {
    final MariadbConnection conn = getConnection();
    final MariadbStatement statement = conn.createStatement(toSql);
    final Flux<MariadbResult> result = statement.execute();
    return result;
  }

  @Override
  public Mono<Long> executeUpdate(String toSql) {
    final MariadbConnection conn = getConnection();
    final MariadbStatement statement = conn.createStatement(toSql);
    final Flux<MariadbResult> result = statement.execute();
    return result.count();
  }

  @Override
  public Flux<Boolean> execute(String toSql) {
    return null;
  }

  //TODO: See if I need to have this here, or if I should only instantiate connection once, and reuse it in multiple methods.
  //See https://mariadb.com/docs/clients/mariadb-connectors/connector-r2dbc/native/dml/
  // Also add try-catch if needed.
  private MariadbConnection getConnection() {
    MariadbConnectionConfiguration.Builder builder = MariadbConnectionConfiguration.builder()
        .host(host)
        .port(port)
        .username(username)
        .password(password);
    if (database != null) {
      builder.database(database);
    }
    MariadbConnectionConfiguration conf = builder.build();
    final MariadbConnectionFactory factory = MariadbConnectionFactory.from(conf);
    return factory.create().block();
  }

}
