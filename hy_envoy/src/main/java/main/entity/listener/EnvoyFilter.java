package main.entity.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 22:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyFilter {
    private String name;
    private EnvoyFilterConfig config;
}
