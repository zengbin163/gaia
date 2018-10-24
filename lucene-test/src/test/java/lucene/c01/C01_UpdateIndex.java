package lucene.c01;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
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
 * Created by 张少昆 on 2018/3/31.
 */
public class C01_UpdateIndex {
    private static final String LOCATION = "d:/lucene";

    // indexWriter.updateDocument();
    // indexWriter.updateDocuments();
    // indexWriter.updateDocValues();
    // indexWriter.updateNumericDocValue();

    @Test
    public void updateTheNonexist() throws IOException{ //更新不存在的Document！实际上就是新增
        Directory directory = FSDirectory.open(new File(LOCATION)); //会自动挑选最优实现类

        Analyzer analyzer = new IKAnalyzer();//分词器，可以做更进一步的定制  嗯，据说IK不支持新版本？？？
        IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);//可以做更进一步的定制
        cfg.setInfoStream(System.out);//输出信息
        cfg.setOpenMode(IndexWriterConfig.OpenMode.APPEND);//追加

        IndexWriter indexWriter = new IndexWriter(directory, cfg);

        Document doc = new Document();
        StoredField pid = new StoredField("pid", -1);//只存储，不索引
        doc.add(pid);
        TextField name = new TextField("name", "啊不错的风格", Field.Store.YES);//分词、索引、存储
        name.setBoost(1); //TODO 写死加权。还可以在搜索时加权！
        doc.add(name);

        Term term = new Term("name", "---");//TODO 故意测试一个不存在的
        indexWriter.updateDocument(term, doc);

        indexWriter.close();
    }
}
