package hap.exam.itf_defination.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hap.exam.itf_defination.dto.ItfDefinationHeaders;

public interface ItfDefinationHeadersMapper extends Mapper<ItfDefinationHeaders>{
    Long seletHeaderIdByName(String name);
}