package lucene.c01;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by zengbin on 2018/3/31.
 */
public class C01_DeleteIndex {
    private static final String LOCATION = "d:/lucene";

    @Test
    public void delete(){
        try{
            Directory directory = FSDirectory.open(new File(LOCATION));
            IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_4, new IKAnalyzer());
            cfg.setInfoStream(System.out);
            IndexWriter indexWriter = new IndexWriter(directory, cfg);


            // indexWriter.deleteAll();
            // indexWriter.deleteDocuments(term...);
            // indexWriter.deleteDocuments(query...);
            // indexWriter.deleteUnusedFiles();

            Term term = new Term("name", "不错的风格");
            indexWriter.deleteDocuments(term);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
