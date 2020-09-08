package org.genx.javadoc.field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/7 23:02
 */
@Data
@ApiModel(value = "新闻查询", description = "新闻查询对象")
public class ArticleQueryField {


    @ApiModelProperty(value = "关键词", required = true, name = "keyword")
    private String keyword;

    @ApiModelProperty(value = "查询区间开始时间", name = "startTime")
    private Date startTime;

    @ApiModelProperty(value = "查询区间结束时间", name = "endTime")
    private Date endTime;

    @Range(min = 0, max = 999)
    private Integer level;

}
