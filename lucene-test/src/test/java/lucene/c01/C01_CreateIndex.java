package lucene.c01;

import lucene.pojo.Product;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 首先，测试的数据都在数据库中，所以需要先load进来。
 * 然后每条记录做成一个Document。
 * 最后将所有的Document都写入。
 * <p>
 * 此时 硬盘上指定路径已经有了索引文件！
 * <p>
 * 然后，再测试增删改查。
 * <p>
 * FIXME 卧槽，IK分词器不支持新版本lucene！所以lucene又切换成4.10.4
 * <p>
 * Created by 张少昆 on 2018/3/31.
 */
public class C01_CreateIndex {

    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static final String LOCATION = "d:/lucene";

    // @Before //因为又多了一个测试，不需要反复读取jdbc，就注释了
    public void init(){
        Connection connection = null;
        try{
            // Class.forName("com.mysql.cj.jdbc.Driver"); //TODO 哟西，已经变啦
            //TODO 高版本的connector需要指定时区！
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene?serverTimezone=GMT%2B8", "root", "root@localhost");
            Statement statement = connection.createStatement();
            String sql = "SELECT `PID`,`NAME`,`CATALOG`,`CATALOG_NAME`,`PRICE`,`NUMBER`,`DESCRIPTION`,`PICTURE`,`RELEASE_TIME` FROM products;";
            ResultSet rs = statement.executeQuery(sql);
            Product product = null;
            while(rs.next()){
                product = new Product();
                product.setPid(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setCatalog(rs.getInt(3));
                product.setCatalog_name(rs.getString(4));
                product.setPrice(rs.getDouble(5));
                product.setNumber(rs.getInt(6));
                product.setDescription(rs.getString(7));
                product.setPicture(rs.getString(8));
                product.setRelease_time(rs.getTimestamp(9));
                // System.out.println("---------");
                // System.out.println(rs.getDate(9));
                // System.out.println(rs.getTime(9));
                // System.out.println(rs.getTimestamp(9));
                PRODUCTS.add(product);
            }
            // System.out.println(products);

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void r1(){
        //为了测试jdbc是否连接成功
        init();
    }

    /***
     * 总之，目的就是为了创建索引。创建索引，本质上就新增索引
     *
     * @throws IOException
     */
    @Test
    public void createIndexes() throws IOException{
        init();
        // Directory directory = FSDirectory.open(Paths.get(LOCATION));
        Directory directory = FSDirectory.open(new File(LOCATION)); //会自动挑选最优实现类

        // Analyzer analyzer = new StandardAnalyzer();//分词器，可以做更进一步的定制
        Analyzer analyzer = new IKAnalyzer();//分词器，可以做更进一步的定制  嗯，据说IK不支持新版本？？？
        // Analyzer analyzer = new CJKAnalyzer();
        // IndexWriterConfig cfg = new IndexWriterConfig(analyzer);//可以做更进一步的定制
        IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);//可以做更进一步的定制
        cfg.setInfoStream(System.out);//输出信息
        cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE);//覆盖之前的

        IndexWriter indexWriter = new IndexWriter(directory, cfg);

        indexWriter.addDocuments(documentList());

        indexWriter.close();
    }

    @Test
    public void createIndexWithBoost() throws IOException{
        Directory directory = FSDirectory.open(new File(LOCATION)); //会自动挑选最优实现类

        Analyzer analyzer = new IKAnalyzer();//分词器，可以做更进一步的定制  嗯，据说IK不支持新版本？？？
        IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);//可以做更进一步的定制
        cfg.setInfoStream(System.out);//输出信息
        cfg.setOpenMode(IndexWriterConfig.OpenMode.APPEND);//追加

        IndexWriter indexWriter = new IndexWriter(directory, cfg);

        Document doc = new Document();
        StoredField pid = new StoredField("pid", -1);//只存储，不索引
        doc.add(pid);
        TextField name = new TextField("name", "小黄人小红人小绿人神偷奶爸午睡枕音乐金属", Field.Store.YES);//分词、索引、存储
        name.setBoost(1); //TODO 写死加权。还可以在搜索时加权！
        doc.add(name);

        indexWriter.addDocument(doc);

        indexWriter.close();
    }

    /**
     * 准备documentList
     *
     * @return
     */
    public List<Document> documentList(){
        List<Document> list = new ArrayList<>();

        PRODUCTS.forEach(product -> {
            Document doc = new Document();

            StoredField pid = new StoredField("pid", product.getPid());//只存储，不索引
            doc.add(pid);
            TextField name = new TextField("name", product.getName(), Field.Store.YES);//分词、索引、存储
            doc.add(name);
            StoredField catalog = new StoredField("catalog", product.getCatalog());
            doc.add(catalog);
            // StringField catalog_name = new StringField("catalog_name", product.getCatalog_name(), Field.Store.YES);//索引+不分词
            StoredField catalog_name = new StoredField("catalog_name", product.getCatalog_name());//索引+不分词
            doc.add(catalog_name);
            StoredField price = new StoredField("price", product.getPrice());
            doc.add(price);

            // new DoubleRange("price",p1,p2); //直接建立范围索引，查找更简单！空间换时间！
            // new DoublePoint("price",p1...);//看起来和上面的一样？有啥区别？

            // new Field("number",product.getNumber(),StoredField.TYPE) //不适用于基本类型！
            StoredField number = new StoredField("number", product.getNumber());
            doc.add(number);

            Field description = new Field("description", product.getDescription(), TextField.TYPE_STORED);
            doc.add(description);

            //TODO 日期相关的！务必注意
            Field release_time = new Field("release_time", DateTools.dateToString(product.getRelease_time(), DateTools.Resolution.SECOND), StringField.TYPE_STORED);
            doc.add(release_time);

            list.add(doc);
        });
        return list;
    }

}
