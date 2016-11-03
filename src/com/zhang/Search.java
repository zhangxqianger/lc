package com.zhang;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Search {
    public static DirectoryReader getSearch() {
        DirectoryReader open = null;
        try {
            Directory directory = FSDirectory.open(Paths.get("index"));
            open = DirectoryReader.open(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return open;
    }

    public void search() throws IOException, ParseException {
        IndexReader indexReader = getSearch();
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        QueryParser queryParser = new QueryParser("contents", new StandardAnalyzer());
        Query parse = queryParser.parse("网站");

        //Query parse = new TermQuery(new Term("contents", "body"));

        TopDocs search = indexSearcher.search(parse, 10);
       // TopDocs search = indexSearcher.searchAfter(q.scoreDocs[9], parse, 10);

        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc s :
                scoreDocs) {
            Document doc = indexSearcher.doc(s.doc);
            System.out.println(doc.getField("filePath").stringValue() + "\t" + doc.getField("fileName").stringValue());
        }

        System.out.println(indexReader.maxDoc());
        System.out .println(indexReader.numDocs());
        indexReader.close();
    }

}
