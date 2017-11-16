package hap.exam.itf_defination.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hap.exam.itf_defination.dto.ItfDefinationHeaders;
import hap.exam.itf_defination.mapper.ItfDefinationHeadersMapper;
import hap.exam.itf_defination.service.IItfDefinationHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfDefinationHeadersServiceImpl extends BaseServiceImpl<ItfDefinationHeaders> implements IItfDefinationHeadersService{
@Autowired
    ItfDefinationHeadersMapper itfDefinationHeadersMapper;
    @Override
    public Long seletHeaderIdByName(String name) {
        return itfDefinationHeadersMapper.seletHeaderIdByName(name);
    }
}