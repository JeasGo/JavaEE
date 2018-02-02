package cn.jeas.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.serivce.base.AreaService;



@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

	private static final long serialVersionUID = -4850031735720436429L;
	
	private File upload;//文件对象
	private String uploadFileName;//文件名
	private String uploadContentType; //文件类型
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	//分页对象
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Autowired
	private AreaService areaService;
	
	
	@Action("area_listPage")
	//返回页面JSON
	public String listPage(){
		Pageable pageable =  new PageRequest(page-1, rows);
		Page<Area> plist = areaService.findAreaListPage(pageable);
		pushPageDataToValuaestackBoot(plist);
		return JSON;
		
	}

	
	@Action("area_importData")
	public String importData(){
		//目标：读excel，入库
		List<Area> areaList=new ArrayList<>();
		
		try {
			//1.打开工作簿(97格式)
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(upload));
			//2.从工作簿中打开工作表
			//workbook.getSheet("Sheet1");//根据名字读取工作表
			HSSFSheet sheet = workbook.getSheetAt(0);//g根据索引来读取工作表0-based physical & logical
			//3.一行一行读
			for (Row row : sheet) {
				//第一行一般是标题，要跳过
				if(row.getRowNum()==0){
					continue;
				}
				//一格一格读数据
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//汉字转拼音
				String provinceStr = StringUtils.substring(province,0,-1);
				String cityStr = StringUtils.substring(city, 0,-1);
				String districtStr = StringUtils.substring(district, 0,-1);
				
				//区域简码
				String shortcode=PinyinHelper.getShortPinyin(provinceStr+cityStr+districtStr).toUpperCase();
				//城市编码
				//参数1: 要转成拼音的中文字符串
				String citycode=PinyinHelper.convertToPinyinString(cityStr, "", PinyinFormat.WITHOUT_TONE);
				
				
				//将值封装到对象
				Area area=new Area();
				area.setId(id);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				
				
				area.setShortcode(shortcode);
				area.setCitycode(citycode);
				
				//将对象填入集合
				areaList.add(area);
			}
			
			//批量将区域保存到数据库
			areaService.saveArea(areaList);
			pushJsonDataToValuaestackBoot(true);
		} catch (Exception e) {
			e.printStackTrace();
			pushJsonDataToValuaestackBoot(false);
		} 
		return JSON;
	}

	
}
