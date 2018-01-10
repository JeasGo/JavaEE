package cn.itcast.bos.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.service.system.UserService;

//用户任务
public class UserJob implements Job{
	//注入用户的service
	@Autowired
	private UserService userService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//调用业务层处理用户
		userService.updateUserStatusByActivetimeForLock();
		System.out.println("定时任务，处理用户的过期情况了。。。。。。");
	}

}
