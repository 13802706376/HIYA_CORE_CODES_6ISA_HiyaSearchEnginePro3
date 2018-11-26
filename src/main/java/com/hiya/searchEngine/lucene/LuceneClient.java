package com.hiya.searchEngine.lucene;

public class LuceneClient
{
    public static void main(String[] args) throws Exception
    {
        MangerIndex demo = new MangerIndex();
        demo.createIndex();
        demo.searchIndex("º«Ë³Æ½");
    }
}
