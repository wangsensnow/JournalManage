package hap.exam.hscs_itf_mapping_lines.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hap.exam.hscs_itf_mapping_lines.dto.ItfMappingLines;
import hap.exam.hscs_itf_mapping_lines.service.IItfMappingLinesService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfMappingLinesServiceImpl extends BaseServiceImpl<ItfMappingLines> implements IItfMappingLinesService{

}