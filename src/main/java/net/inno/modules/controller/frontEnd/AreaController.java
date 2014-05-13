package net.inno.modules.controller.frontEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.services.IAreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 地区
 * @author LYM
 */
@Controller
public class AreaController extends BaseController {
	
	/**
	 * 地区Service
	 */
	@Autowired
	private IAreaService areaService;
	
	/**
	 * 获取地区
	 * @param map
	 * @param parentId 父节点ID 不传参数的时候获取“省”
	 * @param areaId 子节点ID 不传参数的时候获取“省”,获取三级目录的数组
	 */
	@RequestMapping(value="/area.json")
	public void areaJson(ModelMap map, Integer parentId, Integer areaId){
		parentId = parentId == null ? 0 : parentId;
		map.put("data", areaId == null ? this.areaService.getAreaByParentId(parentId) : this.areaService.getArea(areaId));
	}
	
}