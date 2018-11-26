package com.hiya.searchEngine.solr;

public class SolrClient
{
    public static void main(String[] args) throws Exception
    {
        SolrEngine solr = new SolrEngine();
        solr.createSolrServer();
       // solr.addDoc();
        solr.querySolr();
     //   solr.deleteDocumentById();
       // solr.querySolr();
    }
}
