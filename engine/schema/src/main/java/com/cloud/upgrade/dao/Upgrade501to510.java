package com.cloud.upgrade.dao;

import java.io.File;
import java.sql.Connection;

import com.cloud.utils.exception.CloudRuntimeException;
import com.cloud.utils.script.Script;

import org.apache.log4j.Logger;

public class Upgrade501to510 implements DbUpgrade {

  private static final String PREVIOUS_VERSION = "5.0.1";
  private static final String NEXT_VERSION = "5.1.0";
  private static final String SCHEMA_SCRIPT = "db/schema-501to510.sql";
  private static final String SCHEMA_CLEANUP_SCRIPT = "db/schema-501to510-cleanup.sql";

  final static Logger s_logger = Logger.getLogger(Upgrade501to510.class);

  @Override
  public String[] getUpgradableVersionRange() {
    return new String[] { PREVIOUS_VERSION, NEXT_VERSION };
  }

  @Override
  public String getUpgradedVersion() {
    return NEXT_VERSION;
  }

  @Override
  public boolean supportsRollingUpgrade() {
    return false;
  }

  @Override
  public File[] getPrepareScripts() {
    return getScript(SCHEMA_SCRIPT);
  }

  @Override
  public File[] getCleanupScripts() {
    return getScript(SCHEMA_CLEANUP_SCRIPT);
  }

  @Override
  public void performDataMigration(Connection conn) {
  }

  private File[] getScript(String scriptName) {
    final String script = Script.findScript("", scriptName);
    if (script == null) {
      throw new CloudRuntimeException("Unable to find " + scriptName);
    }

    return new File[] { new File(script) };
  }
}
