package net.inno.modules.services.impl;

import java.util.List;

import net.inno.modules.dao.AllianceMapper;
import net.inno.modules.pojo.Alliance;
import net.inno.modules.services.IAllianceService;
import net.inno.myBatisPlugin.page.model.PageRowBounds;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 制造联
 * @author LYM
 */
@Service("allianceService")
public class AllianceService extends BaseService implements IAllianceService {
	
	@Autowired
	private AllianceMapper allianceMapper;

	public int handleAlliance(Alliance alliance) {
		if(alliance == null){
			return 0;
		}
		if(alliance.getIndustryId() <= 0 || alliance.getAreaId() <= 0 || StringUtils.isBlank(alliance.getName())){
			return 0;
		}
		if(alliance.getCost() != Alliance.YN.Y){
			alliance.setCost(Alliance.YN.N);
		}
		if(alliance.getCrafts() != Alliance.YN.Y){
			alliance.setCrafts(Alliance.YN.N);
		}
		if(alliance.getQuality() != Alliance.YN.Y){
			alliance.setQuality(Alliance.YN.N);
		}
		if(alliance.getDelivery() != Alliance.YN.Y){
			alliance.setDelivery(Alliance.YN.N);
		}
		if(alliance.getYield() != Alliance.YN.Y){
			alliance.setYield(Alliance.YN.N);
		}
		if(alliance.getAllianceId() <= 0){
			return this.allianceMapper.insert(alliance);
		}else{
			return this.allianceMapper.update(alliance);
		}
	}

	public int deleteAlliance(long allianceId) {
		if(allianceId <= 0){
			return 0;	
		}
		return this.allianceMapper.delete(allianceId);
	}

	public Alliance getById(long allianceId) {
		if(allianceId <= 0){
			return null;	
		}
		return this.allianceMapper.get(allianceId);
	}
	
	public Alliance getByLogin_id(String login_id) {
		if(StringUtils.isBlank(login_id)){
			return null;	
		}
		return this.allianceMapper.getByLogin_id(login_id);
	}

	public List<Alliance> getList(Alliance alliance, PageRowBounds page) {
		if(alliance == null){
			alliance = new Alliance();
		}else{
			if(StringUtils.isNotBlank(alliance.getName())){
				alliance.setName("%" + alliance.getName() + "%");
			}
		}
		return this.allianceMapper.getList(alliance, page);
	}
	
	
	
}
