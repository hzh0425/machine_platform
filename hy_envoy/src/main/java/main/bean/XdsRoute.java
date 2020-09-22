package main.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 19:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XdsRoute {
    private String id;
    private int version;
    private String json;
    private Date create_date;
}
