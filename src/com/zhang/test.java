package com.zhang;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import java.io.IOException;

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
}
