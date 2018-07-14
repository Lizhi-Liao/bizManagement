package com.inventory.service.impl;

import com.inventory.comm.vo.SimCommodity;
import com.inventory.domain.entity.Commodity;
import com.inventory.domain.enums.CommodityType;
import com.inventory.repository.CommodityRepository;
import com.inventory.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public Long createCommodity(Commodity commodity) throws IllegalArgumentException{
        Assert.hasLength(commodity.getName(), "name not empty");
        Assert.notNull(commodity.getCommodityType(), "commodity type not empty");
        Assert.hasLength(commodity.getQuantityUnit(), "quantity unit not empty");
        Assert.notNull(commodity.getProcessingPeriod(), "processing period not empty");
        Commodity created = commodityRepository.save(commodity);

        return  created.getId();
    }

    @Override
    public List<Commodity> getAllCommodities() {
        return commodityRepository.findCommoditiesByIsAvailableTrue();
    }

    @Override
    public List<Commodity> getAllCommoditiesByCommodityType(CommodityType commodityType) {
        return commodityRepository.findCommoditiesByCommodityTypeAndIsAvailableTrue(commodityType);
    }

    @Override
    public Commodity updateCommodity(Long commodityId, Commodity newCommodityInfo) {
        Commodity commodity = commodityRepository.findOne(commodityId);
        Assert.notNull(commodity, "commodity not existed");
        if (!StringUtils.isEmpty(newCommodityInfo.getName()) ){
            commodity.setName(newCommodityInfo.getName());
        }
        if (null != newCommodityInfo.getCommodityType()) {
            commodity.setCommodityType(newCommodityInfo.getCommodityType());
        }
        if (null != newCommodityInfo.getQuantityUnit()) {
            commodity.setQuantityUnit(newCommodityInfo.getQuantityUnit());
        }
        if (null != newCommodityInfo.getProcessingPeriod()) {
            commodity.setProcessingPeriod(newCommodityInfo.getProcessingPeriod());
        }

        Commodity updated = commodityRepository.save(commodity);
        return updated;
    }

    @Override
    public void deleteCommodity(Long commodityId) {
        Commodity commodity = commodityRepository.findOne(commodityId);
        Assert.notNull(commodity, "item not exist");
        commodity.setIsAvailable(false);
        commodityRepository.save(commodity);

    }

    @Override
    public Commodity getCommoditiesByName(String name) {
        Assert.hasLength(name, "name not empty");
        Commodity res = commodityRepository.findCommodityByNameAndIsAvailableTrue(name);

        return res;
    }

    @Override
    public List<SimCommodity> getCommodityOptions() {
        List<SimCommodity> res = commodityRepository.findCommodityOptions();

        return res;
    }
}
