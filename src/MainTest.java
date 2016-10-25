import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class MainTest {
    Analyzer analyzer = null;
    Directory directory = null;
    IndexWriter indexWriter = null;
    @Before
    public void init() throws IOException {
        analyzer = new StandardAnalyzer();
        directory = FSDirectory.open(Paths.get("./index"));
        indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
    }

    @After
    public void destory() throws IOException {
        indexWriter.close();
        directory.close();
    }

    @Test
    public void index() throws IOException {
        Document document = new Document();
        document.add(new TextField("content", "Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎（英文与德文两种西方语言）。Lucene的目的是为软件开发人员提供一个简单易用的工具包，以方便的在目标系统中实现全文检索的功能，或者是以此为基础建立起完整的全文检索引擎。Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。在Java开发环境里Lucene是一个成熟的免费开源工具。就其本身而言，Lucene是当前以及最近几年最受欢迎的免费Java信息检索程序库。人们经常提到信息检索程序库，虽然与搜索引擎有关，但不应该将信息检索程序库与搜索引擎相混淆。", Field.Store.YES));
        document.add(new TextField("title", "Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎（英文与德文两种西方语言）。Lucene的目的是为软件开发人员提供一个简单易用的工具包，以方便的在目标系统中实现全文检索的功能，或者是以此为基础建立起完整的全文检索引擎。Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。在Java开发环境里Lucene是一个成熟的免费开源工具。就其本身而言，Lucene是当前以及最近几年最受欢迎的免费Java信息检索程序库。人们经常提到信息检索程序库，虽然与搜索引擎有关，但不应该将信息检索程序库与搜索引擎相混淆。", Field.Store.YES));
        indexWriter.addDocument(document);
        System.out.println("索引完成");
    }

    @Test
    public void search() throws IOException, ParseException {
        IndexReader indexReader = DirectoryReader.open(directory);
        Formatter formatter = new SimpleHTMLFormatter("<b>", "</b>");
        QueryParser queryParser = new QueryParser("content", analyzer);
        Query query = queryParser.parse("软件基金会");
        Scorer scorer = new QueryScorer(query);

        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new SimpleFragmenter(20));

        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        TopDocs search = indexSearcher.search(query, 100);
        System.out.println("命中数：" + search.totalHits);
        System.out.println("最高得分：" + search.getMaxScore());
        ScoreDoc[] scoreDocs = search.scoreDocs;

        for (int i = 0; i < scoreDocs.length; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            System.out.println(scoreDoc.score);
            int doc = scoreDoc.doc;
            Document doc1 = indexSearcher.doc(doc);

            System.out.println(doc1.get("content"));
        }
    }

    @Test
    public void del() throws IOException {
        Term term = new Term("content", "apache");
        long l = indexWriter.deleteDocuments(term);
        System.out.println("删除数量:" + l);
    }

}