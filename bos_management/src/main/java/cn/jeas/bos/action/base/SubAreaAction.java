package cn.jeas.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.domain.base.SubArea;
import cn.jeas.bos.serivce.base.AreaService;
import cn.jeas.bos.serivce.base.SubAreaService;

@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {

	private static final long serialVersionUID = -6940337488906950516L;

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
	
	private Integer rows;
	private Integer page;
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public void setPage(Integer page) {
		this.page = page;
	}

	
	@Autowired
	private AreaService areaService;
	@Autowired
	private SubAreaService subAreaService;
	
	
	@Action("sub_area")
	public String area(){
		Pageable pageable =  new PageRequest(page-1 , rows);
		Page<SubArea> plist = subAreaService.findAreaListPage(pageable);
		pushPageDataToValuaestackBoot(plist);
		
		return JSON;
		
	}
	@Action("sub_importData")
	public String importData(){
		List<SubArea> subAreaList=new ArrayList<>();
		try {
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(upload));
			HSSFSheet sheet = workbook.getSheetAt(0);//g根据索引来读取工作表0-based physical & logical
			for (Row row : sheet){
				//第一行一般是标题，要跳过
				if(row.getRowNum()==0){
					continue;
				}
				 String id = row.getCell(0).getStringCellValue();
				 String keyWords = row.getCell(4).getStringCellValue();
				 String startNum = row.getCell(5).getStringCellValue();
				 String endNum = row.getCell(6).getStringCellValue();
				 String single = row.getCell(7).getStringCellValue();
				 String assistKeyWords = row.getCell(8).getStringCellValue();
				 
				 
				 String province = row.getCell(1).getStringCellValue();
				 String city = row.getCell(2).getStringCellValue();
				 String distrcict = row.getCell(3).getStringCellValue();
				 
				 Area area = areaService.findByProvinceAndCityAndDistrict(province,city,distrcict);
				 
				 SubArea subArea = new SubArea();
				 subArea.setId(id);
				 subArea.setArea(area);
				 subArea.setEndNum(endNum);
				 subArea.setAssistKeyWords(assistKeyWords);
				 subArea.setStartNum(startNum);
				 subArea.setKeyWords(keyWords);
				 char[] charArray = single.toCharArray();
				 subArea.setSingle(charArray[0]);
				 subAreaList.add(subArea);
			}
			
			subAreaService.saveAll(subAreaList);
			pushJsonDataToValuaestackBoot(true);
		} catch (Exception e) {
			e.printStackTrace();
			pushJsonDataToValuaestackBoot(false);
		}
		return JSON;
		
	}
	
}
