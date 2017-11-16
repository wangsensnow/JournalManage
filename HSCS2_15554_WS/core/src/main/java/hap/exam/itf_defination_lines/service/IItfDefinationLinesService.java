package hap.exam.itf_defination_lines.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hap.exam.itf_defination_lines.dto.FlexSetColumn;
import hap.exam.itf_defination_lines.dto.ItfDefinationLines;

import java.util.List;

public interface IItfDefinationLinesService extends IBaseService<ItfDefinationLines>, ProxySelf<IItfDefinationLinesService>{
    List<FlexSetColumn> selectFlexSetColumn(String code);
    void deleteByHeaderId(Integer headerId);
    List<ItfDefinationLines> selectByName(ItfDefinationLines itfDefinationLines);
}