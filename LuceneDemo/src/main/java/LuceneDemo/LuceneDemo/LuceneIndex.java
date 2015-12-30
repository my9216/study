package LuceneDemo.LuceneDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndex {

	public static void main(String[] args) throws Exception {
		String filePath = "C:/test"; //待搜索文本
		String indexPath = "C:/testIndex"; //索引位置
		String fieldName = "key";
		String keyword = "idea";
		//System.out.println("aaaaaaa");
		//createIndex(filePath,indexPath,fieldName);
		serach(indexPath,fieldName,keyword);
		
	}
	
	
	/**
	 * 查询
	 * @param indexPath 索引路径
	 * @param fieldName	按照指定属性搜索
	 * @param keyWord	关键字
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void serach(String indexPath,String fieldName,String keyWord) throws IOException, ParseException
	{
		//"C:/testIndex"
		File indexdir = new File(indexPath);
		Directory directory = FSDirectory.open(indexdir.toPath());
		DirectoryReader reader = DirectoryReader.open(directory);
		StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
//		CharArraySet set = new CharArraySet(1, true);
//		set.add("hello");
//		//设置"hello"不是关键字，通过此词不能被搜索到
//		StandardAnalyzer standardAnalyzer = new StandardAnalyzer(set);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(fieldName, standardAnalyzer);
		Query query = parser.parse(keyWord);
		//搜索结果条数
		TopScoreDocCollector create = TopScoreDocCollector.create(10);
		searcher.search(query, create);
		ScoreDoc[] hits = create.topDocs().scoreDocs; 
		for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = searcher.doc(hits[i].doc);
		      System.out.println(hitDoc.get("key"));
		 }
		reader.close();
		directory.close();
		
	}
	
	
	/**
	 * 创建index
	 * @param resourcesPath 资源文件路径
	 * @param indexPath	索引路径
	 * @param fieldName 搜索属性名称
	 * @throws IOException
	 */
	private static void createIndex(String resourcesPath,String indexPath,String fieldName) throws IOException
	{
		File folder = new File(resourcesPath);

		File indexdir = new File(indexPath);
		Directory directory = FSDirectory.open(indexdir.toPath());
//		CharArraySet set = new CharArraySet(1, true);
//		set.add("hello");
//		//设置"hello"不是关键字，通过此词不能被搜索到
//		StandardAnalyzer standardAnalyzer = new StandardAnalyzer(set);
		StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
		//索引创建的方式：
		indexWriterConfig.setOpenMode(OpenMode.CREATE);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		File[] listFiles = folder.listFiles();
		for (int i = 0; i < listFiles.length; i++) {

			if (listFiles[i].isFile() && listFiles[i].getName().endsWith(".txt")) {
				String content = readFile(listFiles[i].getPath(), "UTF-8");
				//增加一条记录，记录属性为fieldName 和 "fileName"
				Document document = new Document();
//				document.add(new TextField(fieldName, content, Store.YES));
				document.add(new Field(fieldName, content, TextField.TYPE_STORED));
				document.add(new Field("fileName", listFiles[i].getName(), TextField.TYPE_STORED));
				indexWriter.addDocument(document);
			}
		}
		indexWriter.commit();
		indexWriter.close();
	}
	
	
	private static String readFile(String fileName,String charset) throws IOException, FileNotFoundException
	{
		BufferedReader reader =   new  BufferedReader(new InputStreamReader(new FileInputStream(fileName),charset));   
        String line =   new  String();   
        String temp =   new  String();   
        while  ((line  =  reader.readLine())  !=   null)  {   
            temp +=  line;   
        }
        reader.close();   
        return  temp;   
	}
}
