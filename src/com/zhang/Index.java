package com.zhang;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/28.
 */
public class Index {
    public static IndexWriter getIndexWriter() {
        IndexWriter indexWriter = null;
        try {
            Directory directory = FSDirectory.open(Paths.get("index"));
            indexWriter = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    private static void getFiles(File file, ArrayList<File> result) {
        try {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    getFiles(f, result);
                } else {
                    if (f.getName().endsWith(".txt")) {
                        System.out.println(f.getName());
                        result.add(f);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("1111" + file.getName());
        }
    }

    public void index() throws IOException {

        File file = new File("F:\\");

        ArrayList<File> arrayList = new ArrayList<>();
        getFiles(file, arrayList);

        System.out.println(arrayList.size());

        IndexWriter indexWriter = getIndexWriter();
       // System.out.println(indexWriter.deleteAll());

        long l = System.currentTimeMillis();
        for (File f :
                arrayList) {
            Document document = new Document();
            document.add(new Field("contents", FileUtils.readFileToString(f, "utf-8"), TextField.TYPE_STORED));
            document.add(new Field("fileName", f.getName(), TextField.TYPE_STORED));
            document.add(new Field("filePath", f.getParent(), TextField.TYPE_STORED));


            indexWriter.addDocument(document);
        }

        long l1 = System.currentTimeMillis();

        System.out.println("maxDoc" + indexWriter.maxDoc());
        System.out.println("numDoc" + indexWriter.numDocs());
        System.out.println(l1 - l);

        indexWriter.close();
    }

    public void delete() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.deleteDocuments(new Term("contents", "html"));
        indexWriter.commit();
        indexWriter.close();
    }

    public void update() throws IOException {
        IndexWriter indexWriter = getIndexWriter();

        Document document = new Document();
        document.add(new Field("contents", "哈哈安徽", TextField.TYPE_STORED));
        document.add(new Field("fileName", "银行", TextField.TYPE_STORED));
        document.add(new Field("filePath", "E:\\absdfsd", TextField.TYPE_STORED));
        indexWriter.updateDocument(new Term("fileName", "银行.txt"), document);

        indexWriter.close();
    }

    public void marge() {
        IndexWriter indexWriter = getIndexWriter();

        try {
            indexWriter.forceMerge(1);
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
