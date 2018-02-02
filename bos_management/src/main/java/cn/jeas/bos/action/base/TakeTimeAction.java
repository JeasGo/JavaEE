package cn.jeas.bos.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.TakeTime;
import cn.jeas.bos.serivce.base.TakeTimeService;

@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {

	private static final long serialVersionUID = -5731727494019168703L;
	
	@Autowired
	private TakeTimeService takeTimeAction;
	
	@Action("taketime_listNoDeltag")
	public String listNoDeltag(){
		List<TakeTime> takeTimeList = takeTimeAction.findTakeTimeListNoDeltag();
		ActionContext.getContext().getValueStack().push(takeTimeList);
		return JSON;
	}

}
