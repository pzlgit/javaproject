package com.pzl.program.frametool.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Metric 聚合分析
 */
@Slf4j
@Service
public class AggrMetricService {

    private final RestHighLevelClient restHighLevelClient;

    public AggrMetricService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * stats 统计员工总数、员工工资最高值、员工工资最低值、员工平均工资、员工工资总和
     * <p>
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_stats": {
     * "stats": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationStats() {
        String responseResult = "";
        try {
            //设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.stats("salary_stats").field("salary");
            //查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            //设置查询结果不返回，只返回聚合结果
            searchSourceBuilder.size(0);
            //创建查询请求对象，将查询条件配置到其中
            SearchRequest searchRequest = new SearchRequest("mydlq-user");
            searchRequest.source(searchSourceBuilder);
            //执行请求
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            //输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                //转换为stats对象
                ParsedStats aggregation = aggregations.get("salary_stats");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("count:{}", aggregation.getCount());
                log.info("avg：{}", aggregation.getAvg());
                log.info("max：{}", aggregation.getMax());
                log.info("min：{}", aggregation.getMin());
                log.info("sum：{}", aggregation.getSum());
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (Exception e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * min 统计员工工资最低值
     * eg：
     * <p>
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_min": {
     * "min": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationMin() {
        String responseResult = "";
        try {
            // 设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.min("salary_min").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 Min 对象
                ParsedMin aggregation = aggregations.get("salary_min");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("min：{}", aggregation.getValue());
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * max 统计员工工资最高值
     * eg:
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_max": {
     * "max": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationMax() {
        String responseResult = "";
        try {
            // 设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.max("salary_max").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 Max 对象
                ParsedMax aggregation = aggregations.get("salary_max");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("max：{}", aggregation.getValue());
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * avg 统计员工工资平均值
     * eg:
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_avg": {
     * "avg": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationAvg() {
        String responseResult = "";
        try {
            // 设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.avg("salary_avg").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 Avg 对象
                ParsedAvg aggregation = aggregations.get("salary_avg");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("avg：{}", aggregation.getValue());
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * sum 统计员工工资总值
     * <p>
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_sum": {
     * "sum": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationSum() {
        String responseResult = "";
        try {
            // 设置聚合条件
            SumAggregationBuilder aggr = AggregationBuilders.sum("salary_sum").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 Sum 对象
                ParsedSum aggregation = aggregations.get("salary_sum");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("sum：{}", String.valueOf((aggregation.getValue())));
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * count 统计员工总数
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "employee_count": {
     * "value_count": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationCount() {
        String responseResult = "";
        try {
            // 设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.count("employee_count").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 ValueCount 对象
                ParsedValueCount aggregation = aggregations.get("employee_count");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                log.info("count：{}", aggregation.getValue());
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

    /**
     * percentiles 统计员工工资百分位
     * eg:
     * GET /mydlq-user/_search
     * {
     * "size": 0,
     * "aggs": {
     * "salary_percentiles": {
     * "percentiles": {
     * "field": "salary"
     * }
     * }
     * }
     * }
     */
    public Object aggregationPercentiles() {
        String responseResult = "";
        try {
            // 设置聚合条件
            AggregationBuilder aggr = AggregationBuilders.percentiles("salary_percentiles").field("salary");
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.aggregation(aggr);
            searchSourceBuilder.size(0);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("mydlq-user");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status()) || aggregations != null) {
                // 转换为 Percentiles 对象
                ParsedPercentiles aggregation = aggregations.get("salary_percentiles");
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                for (Percentile percentile : aggregation) {
                    log.info("百分位：{}：{}", percentile.getPercent(), percentile.getValue());
                }
                log.info("-------------------------------------------");
            }
            // 根据具体业务逻辑返回不同结果，这里为了方便直接将返回响应对象Json串
            responseResult = response.toString();
        } catch (IOException e) {
            log.error("", e);
        }
        return responseResult;
    }

}