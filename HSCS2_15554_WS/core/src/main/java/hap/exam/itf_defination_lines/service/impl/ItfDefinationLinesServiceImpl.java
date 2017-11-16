package hap.exam.itf_defination_lines.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hap.exam.itf_defination_lines.dto.FlexSetColumn;
import hap.exam.itf_defination_lines.dto.ItfDefinationLines;
import hap.exam.itf_defination_lines.mapper.ItfDefinationLinesMapper;
import hap.exam.itf_defination_lines.service.IItfDefinationLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfDefinationLinesServiceImpl extends BaseServiceImpl<ItfDefinationLines> implements IItfDefinationLinesService {
    @Autowired
    private ItfDefinationLinesMapper itfDefinationLinesMapper;

    @Override
    public List<FlexSetColumn> selectFlexSetColumn(String code) {
        return itfDefinationLinesMapper.selectFlexSetColumn (code);
    }

    @Override
    public void deleteByHeaderId(Integer headerId) {
        itfDefinationLinesMapper.deleteByHeaderId(headerId);
    }

    @Override
    public List<ItfDefinationLines> selectByName(ItfDefinationLines itfDefinationLines) {
        return itfDefinationLinesMapper.selectByName(itfDefinationLines);
    }
}