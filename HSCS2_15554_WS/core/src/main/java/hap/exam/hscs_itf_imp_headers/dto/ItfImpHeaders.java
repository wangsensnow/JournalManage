package hap.exam.hscs_itf_imp_headers.dto;

/**Auto Generated By Hap Code Generator**/

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ExtensionAttribute(disable=true)
@Table(name = "hscs_itf_imp_headers")
public class ItfImpHeaders extends BaseDTO {

     public static final String FIELD_HEADER_ID = "headerId";
     public static final String FIELD_SOURCE_SYSTEM_CODE = "sourceSystemCode";
     public static final String FIELD_BATCH_NUM = "batchNum";
     public static final String FIELD_INTERFACE_NAME = "interfaceName";
     public static final String FIELD_MODULE_CODE = "moduleCode";
     public static final String FIELD_ITF_LINE_COUNT = "itfLineCount";
     public static final String FIELD_ITF_HEADER_ID = "itfHeaderId";
     public static final String FIELD_LINE_COUNT = "lineCount";
     public static final String FIELD_DISABLE_FLAG = "disableFlag";
     public static final String FIELD_PROCESS_STATUS = "processStatus";
     public static final String FIELD_IMPORT_STATUS = "importStatus";
     public static final String FIELD_IMPORT_DATE = "importDate";
     public static final String FIELD_IMPORT_MESSAGE = "importMessage";
     public static final String FIELD_PROGRAM_ID = "programId";
     public static final String FIELD_REQUEST_ID = "requestId";


     @Id
     @GeneratedValue
     private Long headerId; //表ID，主键，供其他表做外键

     @Length(max = 30)
     private String sourceSystemCode; //来源系统

     @Length(max = 100)
     private String batchNum; //批次号

     @Length(max = 240)
     private String interfaceName; //接口定义名称

     @NotEmpty
     @Length(max = 30)
     private String moduleCode; //所属模块，CR：结算，ITF：接口

     private Long itfLineCount; //接口行数量，外围系统传

     private Long itfHeaderId; //接口定义头ID

     @NotNull
     private Long lineCount; //行数量,实际计算的汇总数量

     @Length(max = 1)
     private String disableFlag; //失效标识

     @Length(max = 1)
     private String processStatus; //导入数据至行表拆分校验状态，N/S/E/P

     @NotEmpty
     @Length(max = 1)
     private String importStatus; //业务单据生成状态，N/S/E/P

     private Date importDate; //导入日期

     @Length(max = 4000)
     private String importMessage; //后台处理信息

     private Long programId;

     private Long requestId;


     public void setHeaderId(Long headerId){
         this.headerId = headerId;
     }

     public Long getHeaderId(){
         return headerId;
     }

     public void setSourceSystemCode(String sourceSystemCode){
         this.sourceSystemCode = sourceSystemCode;
     }

     public String getSourceSystemCode(){
         return sourceSystemCode;
     }

     public void setBatchNum(String batchNum){
         this.batchNum = batchNum;
     }

     public String getBatchNum(){
         return batchNum;
     }

     public void setInterfaceName(String interfaceName){
         this.interfaceName = interfaceName;
     }

     public String getInterfaceName(){
         return interfaceName;
     }

     public void setModuleCode(String moduleCode){
         this.moduleCode = moduleCode;
     }

     public String getModuleCode(){
         return moduleCode;
     }

     public void setItfLineCount(Long itfLineCount){
         this.itfLineCount = itfLineCount;
     }

     public Long getItfLineCount(){
         return itfLineCount;
     }

     public void setItfHeaderId(Long itfHeaderId){
         this.itfHeaderId = itfHeaderId;
     }

     public Long getItfHeaderId(){
         return itfHeaderId;
     }

     public void setLineCount(Long lineCount){
         this.lineCount = lineCount;
     }

     public Long getLineCount(){
         return lineCount;
     }

     public void setDisableFlag(String disableFlag){
         this.disableFlag = disableFlag;
     }

     public String getDisableFlag(){
         return disableFlag;
     }

     public void setProcessStatus(String processStatus){
         this.processStatus = processStatus;
     }

     public String getProcessStatus(){
         return processStatus;
     }

     public void setImportStatus(String importStatus){
         this.importStatus = importStatus;
     }

     public String getImportStatus(){
         return importStatus;
     }

     public void setImportDate(Date importDate){
         this.importDate = importDate;
     }

     public Date getImportDate(){
         return importDate;
     }

     public void setImportMessage(String importMessage){
         this.importMessage = importMessage;
     }

     public String getImportMessage(){
         return importMessage;
     }

     public void setProgramId(Long programId){
         this.programId = programId;
     }

     public Long getProgramId(){
         return programId;
     }

     public void setRequestId(Long requestId){
         this.requestId = requestId;
     }

     public Long getRequestId(){
         return requestId;
     }

     }
