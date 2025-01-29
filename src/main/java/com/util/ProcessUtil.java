package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bugbycode.module.ResultCode;

public class ProcessUtil {
	
	private final static Logger logger = LogManager.getLogger(ProcessUtil.class);

	public static long monitorPid(String cmd) {
		
		long pId = -1;
		
		try {
			
			logger.debug("run \"{}\"", cmd);
			
			String pidStr = CommandUtil.run(cmd);
			
			pidStr = pidStr.trim();
			
			logger.debug(pidStr);
			
			if(StringUtil.isNotEmpty(pidStr)) {
				pId = Long.valueOf(pidStr);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return pId;
	}
	
	public static synchronized ResultCode startupMongod() {
		
		ResultCode code = ResultCode.ERROR;

		String cmd = "/usr/local/mongodb/bin/start.sh";
		String monitorCmd = "ps -ef | grep mongod | grep -v grep | grep -v mongosh | awk '{print $2}'";
		try {

			long pId = monitorPid(monitorCmd);
			
			if(pId == -1) {
				logger.debug("run \"{}\"", cmd);
				String result = CommandUtil.run(cmd);
				logger.debug(result);
				
				pId = monitorPid(monitorCmd);
			}
			
			logger.debug("Mongodb pid as {}", pId);
			
			if(pId > 0) {
				code = ResultCode.SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return code;
	}
	
	public static synchronized ResultCode startupTomcat() {

		ResultCode code = ResultCode.ERROR;

		String cmd = "/usr/local/tomcat/bin/startup.sh";
		String monitorCmd = "ps -ef | grep tomcat | grep -v grep | awk '{print $2}'";
		try {

			long pId = monitorPid(monitorCmd);
			
			if(pId == -1) {
				logger.info("run \"{}\"", cmd);
				String result = CommandUtil.run(cmd);
				logger.info(result);
				
				pId = monitorPid(monitorCmd);
			}
			
			logger.debug("Tomcat pid as {}", pId);
			
			if(pId > 0) {
				code = ResultCode.SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return code;
	}
}
