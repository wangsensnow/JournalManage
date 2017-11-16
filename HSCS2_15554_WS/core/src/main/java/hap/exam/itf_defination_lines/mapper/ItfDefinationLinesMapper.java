package hap.exam.itf_defination_lines.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hap.exam.itf_defination_lines.dto.FlexSetColumn;
import hap.exam.itf_defination_lines.dto.ItfDefinationLines;

import java.util.List;

public interface ItfDefinationLinesMapper extends Mapper<ItfDefinationLines> {
    List<FlexSetColumn> selectFlexSetColumn(String code);
    void deleteByHeaderId(Integer headerId);
    List<ItfDefinationLines> selectByName(ItfDefinationLines itfDefinationLines);
}