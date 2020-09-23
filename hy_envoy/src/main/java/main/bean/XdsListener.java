package main.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 8:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XdsListener extends Model<XdsListener> {
    @TableId(type = IdType.UUID)
    private String id;
    private int version;
    private String json;

    private Date create_date;
}
