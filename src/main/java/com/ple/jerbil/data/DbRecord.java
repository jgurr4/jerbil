package com.ple.jerbil.data;

import java.util.Optional;

public class DbRecord<T extends DbRecord, I extends DbRecordId> {

  public final Optional<DataBridge> defaultBridge;

  public I save(DataBridge bridge) {
    return bridge.save(this);
  }

  public I save() {
    defaultBridge.ifPresentOrElse(
      bridge -> bridge.save(this),
      () -> DataGlobal.bridge.save(this)
    );
  }

}
