package com.bugbycode.monitor.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bugbycode.module.ResultCode;
import com.util.ProcessUtil;

/**
 * 进程监控任务
 */
@Configuration
@EnableScheduling
public class MonitorTomcatTask {
	
	private final Logger logger = LogManager.getLogger(MonitorTomcatTask.class);
	
	/**
	 * 进程监控任务 每1分钟执行一次
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "23 0/1 * * * ?")
	public void executeShapeTask() {
		ResultCode code = ProcessUtil.startupTomcat();
		if(code == ResultCode.ERROR) {
			logger.info("Start Tomcat error.");
		}
	}
}
