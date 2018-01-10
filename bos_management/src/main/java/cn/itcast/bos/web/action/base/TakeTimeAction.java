package cn.itcast.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;
import cn.itcast.bos.web.action.common.BaseAction;

//取派时间
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {
	//注入取派时间的service
	@Autowired
	private TakeTimeService takeTimeService;

	//列出所有没有删除的时间
	@Action("takeTime_listNoDel")
	public String listNoDel(){
		List<TakeTime> takeTimeList= takeTimeService.findTakeTimeNoDel();
		ActionContext.getContext().getValueStack().push(takeTimeList);
		return JSON;
	}
}
