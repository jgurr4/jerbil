package com.ple.jerbil.data;

/**
 * Configure how the bridge will handle diffs between the schema object and the actual schema inside the database.
 *
 * The options are as follows:
 * validate   = Bridge only checks if schemas match, if any diffs exist it will throw an error and exit program.
 * update     = (Default) Bridge only adds to schema or creates it if needed. Does not ever drop tables/schema.
 * create     = Bridge will recreate entire schema anew. Destroying previous data.
 * createDrop = Bridge will recreate entire schema anew. Destroying previous data. Then drops schema when session exits.
 */
public enum DdlOption {
  validate,
  update,
  create,
  createDrop,
}
