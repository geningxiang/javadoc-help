package org.genx.javadoc.field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
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

    /**
     * 仅用于测试 jsr
     * Digits 指定 整数、小数 精度
     *
     */
    @Null
    @NotNull
    @AssertTrue
    @AssertFalse
    @Min(100)
    @Max(200)
    @DecimalMin("100")
    @DecimalMax("200")
    @Size(min = 5, max = 10)
    @Digits(integer = 5, fraction = 2)
    @Past
    @Future
    @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
    @Email
    @NotEmpty
    @Length(min = 5, max = 10)
    @Range(min = 0, max = 999)
    @URL
    private Integer level;

    private UserLoginField userLoginField;

}
