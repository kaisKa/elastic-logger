package kais.io;

import java.io.IOException;


import org.elasticsearch.client.RestHighLevelClient;

import com.github.javafaker.Faker;

import kais.io.model.LoremData;


public class App {

    public static final String INDEX_NAME = "lorem-index";

    public static void main(String[] args) {

        // Generate a sentence of Lorem ipsum text
        Faker faker = new Faker();
        LoremData loremIpsum = new LoremData(faker.lorem().sentence()); ;
        System.out.println("Data to be indexed \n" + loremIpsum);

        // Try pushing the data to an index called lorem-index
        ElasticConf conf = new ElasticConf();
        try (RestHighLevelClient client = conf.connect()) {
            conf.writeDataToIndex(client, INDEX_NAME, loremIpsum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
