package com.ple.jerbil.data;

import com.ple.jerbil.data.bridge.DataBridge;
import com.ple.jerbil.data.bridge.VoidBridge;
import com.ple.observabilityBridge.RecordingService;
import com.ple.observabilityBridge.SystemOutLogHandler;

public class DataGlobal {
  // bridge contains all the information needed to connect to database. Each bridge will only need one language Generator.
  // Each language type should have it's own global. All database languages will have a bridge and a translator.
  // But other languages will not need a bridge. For instance html doesn't have a bridge, it only has translator.

  // Must be set to something so there is a default if no other bridge is present. May use VoidBridge if you don't want
  // anything to happen, or MemoryBridge if you want an in memory data manager.
  public static DataBridge bridge = new VoidBridge();
  public static RecordingService recordingService = RecordingService.make(SystemOutLogHandler.only);  //Alternatively default to console_log.
  //TODO: replace recordingService value with RecordingService.make(NullHandler) once jerm pushes his updates.
}
