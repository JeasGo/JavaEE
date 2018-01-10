package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.web.action.common.BaseAction;

//区域的action
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

	//文件上传相关
	private File upload;//文件，名字必须和input name="upload"一样
	private String uploadFileName;//文件名，xxx+FileName
	private String uploadContentType;//文件的mime类型，xxx+ContentType 
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	//注入业务层
	@Autowired
	private AreaService areaService;
	
	
	//导入excel的区域数据
	@Action("area_importData")
	public String importData(){
		System.out.println(upload);
		
		//定义一个集合存放区域
		List<Area> areaList=new ArrayList<>();
		
		//map存放导入结果
		Map<String, Object> resultMap=new HashMap<>();
		
		try {
			//目标：读取excel文件的内容
			//poi-hssf(97格式)：记忆方式：平时怎么读一个excel，这里就怎么写代码
			//1)打开工作簿
			HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(upload));
			//2)打开指定的工作表
//			hssfWorkbook.getSheet("Sheet1");//根据表的名字来读
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);//根据表的索引来读index of the sheet number (0-based physical & logical)
			//3)一行一行的读
			//sheet：Sheet extends Iterable<Row>
			for (Row row : sheet) {
				//略过第一行：第一行往往是标题（指导用户填写表格）
				if(row.getRowNum()==0){
					//第一行略过
					continue;
				}
				
				//实体类对象
				Area area=new Area();
				
				//读取行的数据(行是由格组成的，一格一格的读)
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				area.setId(id);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				
				//处理区域简码和城市编码
				//区域简码:先拼接中文，再转成英文
				//省
				String provinceStr=StringUtils.substring(province, 0, -1);
				//市
				String cityStr=StringUtils.substring(city, 0, -1);
				//区
				String districtStr=StringUtils.substring(district, 0, -1);
				//拼接转换
				String shortcode=PinyinHelper.getShortPinyin(provinceStr+cityStr+districtStr).toUpperCase();
				
				area.setShortcode(shortcode);
				
				//城市编码
				//转换
				//参数1：要转换的字符串
				//参数2：转换后的拼音之间的分隔符
				//参数3：拼音的格式： 拼音格式：WITH_TONE_NUMBER--数字代表声调，WITHOUT_TONE--不带声调，WITH_TONE_MARK--带声调
				String citycode = PinyinHelper.convertToPinyinString(cityStr, "", PinyinFormat.WITHOUT_TONE);
				
				area.setCitycode(citycode);
				
				
				//将区域添加到集合
				areaList.add(area);
				
			}
			
			//调用业务层保存
			areaService.saveArea(areaList);
			//成功
			resultMap.put("result", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			//成功
			resultMap.put("result", false);
		} 
		
		//压入root栈顶{result:true}
		ActionContext.getContext().getValueStack().push(resultMap);
		
		return JSON;
	}
	
}
