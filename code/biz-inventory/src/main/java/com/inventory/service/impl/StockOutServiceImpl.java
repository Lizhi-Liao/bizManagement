package com.inventory.service.impl;

import com.inventory.domain.entity.StockOut;
import com.inventory.domain.enums.StockOutPurpose;
import com.inventory.repository.StockOutRepository;
import com.inventory.service.StockOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
public class StockOutServiceImpl implements StockOutService {

    @Autowired
    private StockOutRepository stockOutRepository;

    @Override
    public Long createStockOut(StockOut stockOut) {
        Assert.notNull(stockOut.getItemId(), "item not empty");
        Assert.notNull(stockOut.getPickedUser(), "picked user not empty");
        Assert.notNull(stockOut.getApprovedUser(), "approved user not empty");
        Assert.notNull(stockOut.getPurpose(), "purpose not empty");
        Assert.hasLength(stockOut.getNote(), "note not empty");
        StockOut created = stockOutRepository.save(stockOut);

        return created.getId();
    }

    @Override
    public List<StockOut> getAllStockOut() {
        return stockOutRepository.findAll();
    }

    @Override
    public Page<StockOut> getAllStockOut(Pageable pageable) {
        return stockOutRepository.findAll(pageable);
    }

    @Override
    public StockOut updateStockOut(Long stockOutId, StockOut newStockOutInfo) {
        StockOut stockOut = stockOutRepository.findOne(stockOutId);
        Assert.notNull(stockOut, "stockout no exist");
        if (null != newStockOutInfo.getItemId()) {
            stockOut.setItemId(newStockOutInfo.getItemId());
        }
        if (null != newStockOutInfo.getPickedTime()) {
            stockOut.setPickedTime(newStockOutInfo.getPickedTime());
        }
        if (null != newStockOutInfo.getPickedUser()) {
            stockOut.setPickedUser(newStockOutInfo.getPickedUser());
        }
        if (null != newStockOutInfo.getApprovedUser()) {
            stockOut.setApprovedUser(newStockOutInfo.getApprovedUser());
        }
        if (null != newStockOutInfo.getPurpose()) {
            stockOut.setPurpose(newStockOutInfo.getPurpose());
        }
        if (StringUtils.isEmpty(newStockOutInfo.getNote())) {
            stockOut.setNote(newStockOutInfo.getNote());
        }
        StockOut updated = stockOutRepository.save(stockOut);

        return updated;
    }

    @Override
    public StockOut getStockOutDetail(Long stockOutId) {
        Assert.notNull(stockOutId, "stockOutId not null");
        StockOut stockOutDetail = stockOutRepository.findOne(stockOutId);

        return stockOutDetail;
    }

    @Override
    public StockOut getStockOutDetailByItem(Long itemId) {
        Assert.notNull(itemId, "itemId not null");
        StockOut stockOutDetail = stockOutRepository.findStockOutByItemId(itemId);

        return stockOutDetail;
    }

    @Override
    public List<StockOut> getStockOutByPickedTime(Timestamp fromTime, Timestamp toTime) {
        Assert.notNull(fromTime, "fromTime not null");
        Assert.notNull(toTime, "toTime not null");
        List<StockOut> stockOutsByPickedTime = stockOutRepository.findStockOutsByPickedTimeBetween(fromTime, toTime);

        return stockOutsByPickedTime;
    }

    @Override
    public Page<StockOut> getStockOutByPickedTime(Pageable pageable, Timestamp fromTime, Timestamp toTime) {
        Assert.notNull(fromTime, "fromTime not null");
        Assert.notNull(toTime, "toTime not null");
        Page<StockOut> stockOutsByPickedTime = stockOutRepository.findStockOutsByPickedTimeBetween(fromTime, toTime, pageable);

        return stockOutsByPickedTime;
    }

    @Override
    public List<StockOut> getStockOutByPurpose(StockOutPurpose purpose) {
        Assert.notNull(purpose, "purpose not null");
        List<StockOut> stockOutsByPurpose = stockOutRepository.findStockOutsByPurpose(purpose.getPurpose());

        return stockOutsByPurpose;
    }

    @Override
    public Page<StockOut> getStockOutByPurpose(Pageable pageable, StockOutPurpose purpose) {
        Assert.notNull(purpose, "purpose not null");
        Page<StockOut> stockOutsByPurpose = stockOutRepository.findStockOutsByPurpose(purpose.getPurpose(), pageable);

        return stockOutsByPurpose;
    }

    @Override
    public List<StockOut> getStockOutByPickedUser(Long pickedUserId) {
        Assert.notNull(pickedUserId, "pickedUser Id not null");
        List<StockOut> stockOutsByPickUser = stockOutRepository.findStockOutsByPickedUser(pickedUserId);

        return stockOutsByPickUser;
    }

    @Override
    public Page<StockOut> getStockOutByPickedUser(Pageable pageable, Long pickedUserId) {
        Assert.notNull(pickedUserId, "pickedUser Id not null");
        Page<StockOut> stockOutsByPickUser = stockOutRepository.findStockOutsByPickedUser(pickedUserId, pageable);

        return stockOutsByPickUser;
    }

    @Override
    public List<StockOut> getStockOutByApprovedUser(Long approvedUser) {
        Assert.notNull(approvedUser, "approvedUser Id not null");
        List<StockOut> stockOutsByApprovedUser = stockOutRepository.findStockOutsByApprovedUser(approvedUser);

        return stockOutsByApprovedUser;
    }

    @Override
    public Page<StockOut> getStockOutByApprovedUser(Pageable pageable, Long approvedUser) {
        Assert.notNull(approvedUser, "approvedUser Id not null");
        Page<StockOut> stockOutsByApprovedUser = stockOutRepository.findStockOutsByApprovedUser(approvedUser, pageable);

        return stockOutsByApprovedUser;
    }

    @Override
    public List<StockOut> getStockOutByCritierion(HashMap<String, Object> criterion) {
        return null;
    }

    @Override
    public Page<StockOut> getStockOutByCritierion(Pageable pageable, HashMap<String, Object> criterion) {
        return null;
    }
}
