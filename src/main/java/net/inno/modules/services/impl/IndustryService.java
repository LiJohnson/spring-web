package net.inno.modules.services.impl;

import java.util.ArrayList;
import java.util.List;

import net.inno.modules.dao.IndustryMapper;
import net.inno.modules.pojo.Industry;
import net.inno.modules.services.IIndustryService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 制造联行业
 * @author LYM
 */
@Service("industryService")
public class IndustryService extends BaseService implements IIndustryService {
	
	@Autowired
	private IndustryMapper industryMapper;
	
	public List<Industry> loadIndustry(Long industryId){
		if(industryId == null){
			return this.getChildren(new Industry(Industry.INDUSTRY_TOP));
		}
		
		Industry child = this.industryMapper.get(industryId);
		if(child == null){
			return null;
		}
		Industry parent = this.industryMapper.get(child.getParentId());
		List<Industry> list = new ArrayList<Industry>();
		if(parent != null){
			list.add(parent);
		}
		list.add(child);
		return list;
	}
	
	/**
	 * 递归获取子行业
	 * @param category
	 * @return
	 */
	private  List<Industry> getChildren( Industry industry ){
		List<Industry> list = this.industryMapper.getByParentId(industry.getIndustryId());
		industry.setChildren(list);
		if( list != null ){
			for(Industry child : list){
				this.getChildren(child);
			}
		}
		return list;
	}

	public int handleIndustry(Industry industry) {
		if(industry == null){
			return 0;
		}
		
		if(StringUtils.isBlank(industry.getName())){
			return 0;
		}
		
		if(industry.getIndustryId() <= 0){
			if(this.industryMapper.isExist(industry.getName())){
				return 0;
			}
			
			return this.industryMapper.insert(industry);
		}else{
			return this.industryMapper.edit(industry);
		}
	}

	public int deleteIndustry(long industryId) {
		int count = this.industryMapper.deleteByParentId(industryId);
		count += this.industryMapper.delete(industryId);
		return count;
	}
	
}
