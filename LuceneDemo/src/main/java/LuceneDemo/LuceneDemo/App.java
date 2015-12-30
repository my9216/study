package LuceneDemo.LuceneDemo;

import java.util.Date;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Hello world!
 *
 */
public class App {
	
	private static String content="";
	private static String INDEX_DIR = "D:\\luceneIndex";
	private static String DATA_DIR = "D:\\luceneData";
	private static Analyzer analyzer = null;
	private static Directory directory = null;
	private static IndexWriter indexWriter = null;
	 
	public static void main(String[] args) {
//		try {
//			App.createIndex(DATA_DIR);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		App.searchIndex("y");
	}

	
    
	/**
	 * 创建当前文件目录的索引
	 * 
	 * @param path
	 *            当前文件目录
	 * @return 是否成功
	 * @throws IOException 
	 */
	public static boolean createIndex(String path) throws IOException {
		Date date1 = new Date();
		File[] fileList = FileUtil.tolistFiles(path);
		for (File file : fileList) {
			content = "";
			// 获取文件后缀
			String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if ("txt".equalsIgnoreCase(type)) {

				content += FileUtil.readFileByLine(file.getPath());

			} else if ("doc".equalsIgnoreCase(type)) {

				//content += doc2String(file);

			} else if ("xls".equalsIgnoreCase(type)) {

				//content += xls2String(file);

			}

			System.out.println("name :" + file.getName());
			System.out.println("path :" + file.getPath());
			System.out.println();

			try {
				analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
				directory = FSDirectory.open(new File(INDEX_DIR));

				File indexFile = new File(INDEX_DIR);
				if (!indexFile.exists()) {
					indexFile.mkdirs();
				}
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
				//config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); 
				//config.setMergePolicy(mergePolicy)
				indexWriter = new IndexWriter(directory, config);
				
				Document document = new Document();
				document.add(new TextField("filename", file.getName(), Store.YES));
				document.add(new TextField("content", content, Store.YES));
				document.add(new TextField("path", file.getPath(), Store.YES));
				indexWriter.addDocument(document);
				indexWriter.commit();
				indexWriter.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			content = "";
		}
		Date date2 = new Date();
		System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
		return true;
	}
	
	 /**
     * 查找索引，返回符合条件的文件
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public static void searchIndex(String text){
        Date date1 = new Date();
        try{
            directory = FSDirectory.open(new File(INDEX_DIR));
            analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
    
            QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content", analyzer);
            Query query = parser.parse(text);
            
            ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
        
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("*****************************");
                System.out.println(hitDoc.get("filename"));
                System.out.println(hitDoc.get("content"));
                System.out.println(hitDoc.get("path"));
                System.out.println("*****************************");
            }
            ireader.close();
            directory.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Date date2 = new Date();
        System.out.println("查看索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
    }
}
