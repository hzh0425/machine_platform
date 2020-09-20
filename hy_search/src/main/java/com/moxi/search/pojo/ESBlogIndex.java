package com.moxi.search.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * ESBlogIndex
 */

/**
 * @author hzh
 * @since 2020-08-07
 */
@Data
@Document(indexName = "", type = "", shards = 1, replicas = 0)
public class ESBlogIndex {
    @Id
    private String id;

    /**
     * 所以可搜索信息
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

}
