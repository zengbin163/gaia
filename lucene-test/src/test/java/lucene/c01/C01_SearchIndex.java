package lucene.c01;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by zengbin on 2018/3/31.
 */
public class C01_SearchIndex {
    private static final String LOCATION = "d:/lucene";


    // field_name:KEY1 AND/OR/NOT KEY2
    // decription:java AND python
    @Test
    public void querySearch() throws ParseException{ //普通查找 QueryParser有几个子类，扩展了不同查找功能！
        //嗯？ 这里的f是默认field
        QueryParser queryParser = new QueryParser("name", new IKAnalyzer());
        // QueryParser queryParser = new QueryParser("description", new CJKAnalyzer());//TODO 不要搜索这个字段，因为里面有很多隐藏字符(html转义），把正常词语给断开了！
        Query query = queryParser.parse("name:小黄人");//会将`小黄人`分词！

        search(query, 10);
    }

    //TODO 为啥`小黄人`没找到结果？？？ 因为IK分词器的词库里没有！！！
    @Test
    public void termSearch() throws IOException{//最小单元(Term)查找！
        Term t1 = new Term("name", "风格");//Term就是最小单元！！！  尼玛，居然不认·小黄人·！
        Query q1 = new TermQuery(t1);

        search(q1, 10);
    }

    @Test
    public void multiFieldSearch() throws ParseException, IOException{ //多Field查询！
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[]{"name", "description"}, new IKAnalyzer());
        Query query = multiFieldQueryParser.parse("小黄人");

        search(query, 5);
    }

    @Test
    public void searchWithBoost(){//TODO 加权搜索
        Term term = new Term("name", "风格");
        TermQuery termQuery = new TermQuery(term);
        termQuery.setBoost(-2);//加权

        search(termQuery, 5);
    }

    @Test
    public void booleanSearch(){//多条件查询
        Term term1 = new Term("name", "风格");
        TermQuery termQuery1 = new TermQuery(term1);

        Term term2 = new Term("name", "不错");//应该可以不同Field
        TermQuery termQuery2 = new TermQuery(term2);

        //Occur的匹配，有规则，见说明
        BooleanQuery booleanClauses = new BooleanQuery();
        booleanClauses.add(termQuery1, BooleanClause.Occur.MUST);
        booleanClauses.add(termQuery2, BooleanClause.Occur.MUST_NOT);
        // booleanClauses.add(termQuery2, BooleanClause.Occur.SHOULD);

        search(booleanClauses, 10);
    }

    @Test
    public void rangeSearch(){//测试范围查询
        //FIXME 尼玛，没建立索引，查不到啊
        NumericRangeQuery<Double> query = NumericRangeQuery.newDoubleRange("price", 10, 15.0d, 1000.0d, true, true);
        // TermRangeQuery query = new TermRangeQuery();
        search(query, 10);
    }

    //------------------------------------------------------------------

    /**
     * 通用搜索！
     *
     * @param query 查询
     * @param topN  需要返回的条数(topN）
     */
    private void search(Query query, int topN){
        Directory directory = null;
        IndexReader reader = null;
        try{
            directory = FSDirectory.open(new File(LOCATION));

            reader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(reader);

            TopDocs topDocs = indexSearcher.search(query, topN);//N指定需要显示的topN条记录
            int totalHits = topDocs.totalHits;
            System.out.println("匹配总数：" + totalHits);

            for(ScoreDoc scoreDoc : topDocs.scoreDocs){
                int docId = scoreDoc.doc;
                Document document = indexSearcher.doc(docId);
                String name = document.get("name");
                String pid = document.get("pid");
                System.out.println("name: " + name);
                System.out.println("pid: " + pid);

                System.out.println("--------------------");
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            if(reader != null){
                try{
                    reader.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}
