package hap.exam.hscs_itf_imp_lines.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hap.exam.hscs_itf_imp_lines.dto.ItfImpLines;
import hap.exam.hscs_itf_imp_lines.service.IItfImpLinesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfImpLinesServiceImpl extends BaseServiceImpl<ItfImpLines> implements IItfImpLinesService{

}