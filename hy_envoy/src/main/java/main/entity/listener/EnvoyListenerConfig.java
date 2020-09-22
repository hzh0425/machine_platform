package main.entity.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.listener.EnvoyListener;

import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 22:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyListenerConfig {
    public String version_info;
    public List<EnvoyListener> resources;
}
