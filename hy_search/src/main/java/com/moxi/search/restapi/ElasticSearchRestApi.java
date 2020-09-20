package com.moxi.search.restapi;


import com.moxi.search.pojo.ESBlogIndex;
import com.moxi.search.repository.BlogRepository;
import com.moxi.search.service.ElasticSearchService;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.StringUtils;
import com.moxi.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ElasticSearch RestAPI
 *
 */

/**
 * @author hzh
 * @since 2020-08-07
 */
@RequestMapping("/search")
@Api(value = "ElasticSearch相关接口", tags = {"ElasticSearch相关接口"})
@RestController
public class ElasticSearchRestApi {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private ElasticSearchService searchService;
    @Autowired
    private BlogRepository blogRepository;

    /**
     * 以下为测试内容
     * @return
     */

    @ApiOperation(value = "获取所有es博客", notes = "获取所有es博客", response = String.class)
    @GetMapping("/getList")
    public Iterable<ESBlogIndex> getList(){
        return blogRepository.findAll();
    }

    @ApiOperation(value = "根据id获取博客", notes = "根据id获取博客", response = String.class)
    @GetMapping("/getById")
    public Optional<ESBlogIndex> getById(@RequestParam("uid")String id){
        return blogRepository.findById(id);
    }

}
