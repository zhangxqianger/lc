package com.zhang;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Administrator on 2016/10/31.
 */
public class test {
    private Index index;
    private Search search;

    @Test
    public void index() throws IOException {
        index = new Index();
        index.index();
    }

    @Test
    public void Search() throws IOException, ParseException {
        search = new Search();
        search.search();
    }

    @Test
    public void delete() throws IOException {
        index = new Index();
        index.delete();
    }

    @Test
    public void update() throws IOException {
        index = new Index();
        index.update();
    }

    @Test
    public void marge() {
        index = new Index();
        index.marge();
    }

    @Test
    public void testAnay() throws IOException {
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        TokenStream contents = standardAnalyzer.tokenStream("contents", new StringReader("i come from china! beijing 哈哈哈哈哈 你好啊"));
        contents.reset();
        CharTermAttribute attribute = contents.getAttribute(CharTermAttribute.class);
        while (contents.incrementToken()) {
            System.out.print("[" + attribute +"]");
        }
        contents.close();
        standardAnalyzer.close();
    }
}
