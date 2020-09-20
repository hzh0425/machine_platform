package com.moxi.search.service;
import com.moxi.search.mapper.HighlightResultHelper;
import com.moxi.search.pojo.ESBlogIndex;
import com.moxi.search.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hzh
 * @since 2020-08-07
 */
@Slf4j
@Service
public class ElasticSearchService {


    @Autowired
    ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    HighlightResultHelper highlightResultHelper;

    @Autowired
    BlogRepository blogRepository;

}
