package com.ple.jerbil.data;

/**
 * Configure how the bridge will handle diffs between the schema object and the actual schema inside the database.
 *
 * The options are as follows: Each one above create is a lower level of preserving existing schema data.
 *
 * create : Bridge only creates schema if it doesn't already exist. If any diffs exist between schema object and
 * existing schema it will throw an error and exit program rather than modifying the existing schema.
 *
 * update : (Default) Bridge modifies schema if any diffs exist, or creates schema if it doesn't exist.
 * Does not ever drop tables/schema.
 *
 * replace : Bridge will drop any existing structure and recreate entire schema. Destroys previous data.
 *
 * replaceDrop = Bridge will drop the existing structure and recreate entire schema. Destroys previous data.
 * Then drops schema again when session ends.
 *
 */
public enum DdlOption {
  create,
  update,
  replace,
  replaceDrop,
}
