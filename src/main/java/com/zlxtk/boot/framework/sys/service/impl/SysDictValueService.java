package com.zlxtk.boot.framework.sys.service.impl;

import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.sys.model.SysDictValue;
import com.zlxtk.boot.framework.sys.repository.SysDictValueRepository;
import com.zlxtk.boot.framework.sys.service.ISysDictValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysDictValueService extends BaseService<SysDictValue,String> implements ISysDictValueService {

    private SysDictValueRepository dictValueRepository;

    @Autowired
    public SysDictValueService(SysDictValueRepository dictValueRepository) {
        super(dictValueRepository);
        this.dictValueRepository = dictValueRepository;
    }

    @Override
    public int updateEnable(boolean enabled, String dictValueId) {
        SysDictValue val = findById(dictValueId);
        if (val == null) {
            return 0;
        }
        if(enabled){
            val.setState(ApplicationConstants.MODEL_STATE_ENABLE);
        }else {
            val.setState(ApplicationConstants.MODEL_STATE_UNENABLE);
        }
        insert(val);
        List<SysDictValue> list = findByParentId(dictValueId);
        for (SysDictValue v : list) {
            updateEnable(enabled, v.getId());
        }
        return 1;
    }

    @Override
    public List<SysDictValue> findByParentId(String parentId) {
        return dictValueRepository.findByParentId(parentId);
    }

    @Override
    public void deleteById(String id) {
        SysDictValue dictValue = findById(id);
        insert(dictValue);
        List<SysDictValue> list = findByParentId(id);
        for (SysDictValue v : list) {
            deleteById(v.getId());
        }
    }

}
