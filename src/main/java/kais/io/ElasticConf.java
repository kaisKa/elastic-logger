package kais.io;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticConf {
    public RestHighLevelClient connect() {
    
    RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

    return new RestHighLevelClient(clientBuilder);

    }

    public void createNewIndex(String indexName) throws IOException {
        RestHighLevelClient client = connect();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("response id: " + createIndexResponse.index());

    }

    public void writeDataToIndex(RestHighLevelClient client ,String indexName, Object obj) throws IOException {

        IndexRequest indexRequest = new IndexRequest(indexName);
        // indexRequest.id("1");
        String data = new ObjectMapper().writeValueAsString(obj); 
        indexRequest.source(data, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("response id: " + indexResponse.getId());
        System.out.println("response name: " + indexResponse.getResult().name());

    }
}
