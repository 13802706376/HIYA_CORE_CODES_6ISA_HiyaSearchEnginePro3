package com.hiya.searchEngine.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MangerIndex
{
    // �����洢�ĵ�ַ
    private static final String INDEX_PATH = "index";

    // �����洢File
    private static List<File> files = new ArrayList<File>();


    // ��ȡ����
    public void searchIndex(String keyword) throws Exception
    {
        // 1.��������
        String path = this.getIndexPath();
        // ������Ŀ¼
        FSDirectory fs = FSDirectory.open(new File(path));

        // 2.��ȡIndexReader ��ȡ����
        IndexReader reader = IndexReader.open(fs);

        // 3.����IndexReader c����IndexSeacher
        IndexSearcher searcher = new IndexSearcher(reader);

        // 4.��������Query
        // ����parser ��ȷ�������ļ������� �����������ļ�����һ������
        QueryParser parser = new QueryParser(Version.LUCENE_45, "name", new StandardAnalyzer(Version.LUCENE_45));

        // 5.����Query ,��ʾ���������а���Java���ĵ�
        Query query = parser.parse(keyword);

        // 6.����Seacher ��������TopDose �� 10��ʾ��ѯ10��
        TopDocs docs = searcher.search(query, 10);

        // 7.����TopDocs ��ȡSocreDoc
        for (ScoreDoc result : docs.scoreDocs)
        {

            // ����id����ȡ��document
            Document doc = searcher.doc(result.doc);

            // ��ȡ�������ļ�����
            System.out.println(doc.get("name") + "\r" + doc.get("path"));
        }
    }

    /**
     * �����û�:��쭵�yellowcong ��������:2017��12��2�� ����ʱ��:����11:54:53 ���ܸ�Ҫ:��������
     * 
     * @throws Exception
     */
    public void createIndex() throws Exception
    {
        // 1.��������
        String path = this.getIndexPath();
        // ������Ŀ¼
        FSDirectory fs = FSDirectory.open(new File(path));

        // 2.����IndexWrite
        IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_45, new StandardAnalyzer(Version.LUCENE_45));
        // �½�һ������
        IndexWriter writer = new IndexWriter(fs, cfg);

        // 3.����Document ����
        String targetpath = "F:\\goto\\HIYA\\lianxi\\documents\\lucene";
        this.getFile(new File(targetpath));
        // ����������ļ�����������
        for (File file : files)
        {
            // 4.���������ĵ�
            Document doc = new Document();

            // ������������ǲ�����
            doc.add(new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));

            // ���������ƴ洢
            doc.add(new Field("name", file.getName(), Field.Store.YES, Field.Index.ANALYZED));

            // 5.����ĵ������� ��
            writer.addDocument(doc);
            System.out.println(file.getPath());
        }

        writer.close();
    }

    /**
     * �����û�:��쭵�yellowcong ��������:2017��12��2�� ����ʱ��:����12:14:54 ���ܸ�Ҫ:�����ļ��У���ȡ�����ļ�
     * 
     * @param file
     */
    public void getFile(File file)
    {

        if (file.isFile())
        {
            files.add(file);
        } else
        {
            // �ļ���������ļ�
            File[] childFile = file.listFiles();
            for (File child : childFile)
            {
                if (child.isFile())
                {
                    files.add(child);
                } else
                {
                    getFile(child);
                }
            }
        }
    }

    /**
     * �����û�:��쭵�yellowcong ��������:2017��12��2�� ����ʱ��:����12:05:52 ���ܸ�Ҫ:��ȡ����Ŀ¼
     * 
     * @return
     */
    public String getIndexPath()
    {
        // ��ȡ������Ŀ¼
        String path = MangerIndex.class.getClassLoader().getResource("index").getPath();

        // �����ھʹ���Ŀ¼
        File file = new File(path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return path;
    }

}
