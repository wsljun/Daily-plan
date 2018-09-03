

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件处理工具类
 */
public class FileUtils {

	public static final long B = 1;
    public static final long KB = B * 1024;
	public static final long MB = KB * 1024;
	public static final long GB = MB * 1024;
	private static final int BUFFER = 8192;
	/**
	 * 格式化文件大小<b> 带有单位
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		StringBuilder sb = new StringBuilder();
		String u = null;
		double tmpSize = 0;
		if (size < KB) {
			sb.append(size).append("B");
			return sb.toString();
		} else if (size < MB) {
			tmpSize = getSize(size, KB);
			u = "KB";
		} else if (size < GB) {
			tmpSize = getSize(size, MB);
			u = "MB";
		} else {
			tmpSize = getSize(size, GB);
			u = "GB";
		}
		return sb.append(twodot(tmpSize)).append(u).toString();
	}

	/**
	 * 保留两位小数
	 * 
	 * @param d
	 * @return
	 */
	public static String twodot(double d) {
		return String.format("%.2f", d);
	}

	public static double getSize(long size, long u) {
		return (double) size / (double) u;
	}

	/**
	 * sd卡挂载且可用
	 * 
	 * @return
	 */
	public static boolean isSdCardMounted() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 按行读取txt
	 */
	private static String readTextFromSDcard(InputStream is) throws Exception {
		InputStreamReader reader = new InputStreamReader(is, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuffer buffer = new StringBuffer("");
		String str;
		while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
				buffer.append("\n");
		}
		return buffer.toString();
	}

	/**
	 * 从Asset读取text文件
	 * */
	public static void getContentByAsset(Context context,String filename){
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(filename);
			final String content = readTextFromSDcard(is) + "";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* 读取文本为字符串*/
	public String getFromAssets(Context context,String fileName){
		String Result="";
		try {
			InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line="";
			while((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}

	/**
	 * Opens and reads a file, and returns the contents as one String.
	 */
	public static String readFileAsString(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * Open and read a file, and return the lines in the file as a list of
	 * Strings.
	 */
	public static List<String> readFileAsListOfStrings(String filename) throws Exception {
		List<String> records = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null) {
			records.add(line);
		}
		reader.close();
		return records;
	}

	/**
	 * Save the given text to the given filename.
	 *
	 * @param canonicalFilename
	 *          Like /Users/al/foo/bar.txt
	 * @param text
	 *          All the text you want to save to the file as one String.
	 * @throws IOException
	 */
	public static void writeFile(String canonicalFilename, String text) throws IOException {
		File file = new File(canonicalFilename);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write(text);
		out.close();
	}

	/**
	 * Write an array of bytes to a file. Presumably this is binary data; for
	 * plain text use the writeFile method.
	 */
	public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException {
		OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
		InputStream inputStream = new ByteArrayInputStream(bytes);
		int token = -1;

		while ((token = inputStream.read()) != -1) {
			bufferedOutputStream.write(token);
		}
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		inputStream.close();
	}

	/**
	 * Important note: This method hasn't been tested yet, and was originally written a long, long time ago.
	 * @param canonicalFilename
	 * @param text
	 * @throws IOException
	 */
	public static void appendToFile(String canonicalFilename, String text) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(canonicalFilename, true));
			bw.write(text);
			bw.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally { // always close the file
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
					// ignore it
				}
		}

	}



	/**
	 * 递归创建文件目录
	 * 
	 * @param path
	 * */
	public static void CreateDir(String path) {
		if (!isSdCardMounted())
			return;
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.mkdirs();
			} catch (Exception e) {
				Log.e("hulutan", "error on creat dirs:" + e.getStackTrace());
			}
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	/**
	 * 从流中读取文件
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuffer strbuffer = new StringBuffer();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	/**
	 * 将文本内容写入文件
	 * 
	 * @param file
	 * @param str
	 * @throws IOException
	 */
	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(str.getBytes());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

//	/**
//	 * 将Bitmap保存本地JPG图片
//	 * @param url
//	 * @return
//	 * @throws IOException
//	 */
//	public static String saveBitmap2File(String url) throws IOException {
//
//		BufferedInputStream inBuff = null;
//		BufferedOutputStream outBuff = null;
//
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//		String timeStamp = sf.format(new Date());
//		File targetFile = new File(Constants.ENVIROMENT_DIR_SAVE, timeStamp
//				+ ".jpg");
//		File oldfile = ImageLoader.getInstance().getDiscCache().get(url);
//		try {
//
//			inBuff = new BufferedInputStream(new FileInputStream(oldfile));
//			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
//			byte[] buffer = new byte[BUFFER];
//			int length;
//			while ((length = inBuff.read(buffer)) != -1) {
//				outBuff.write(buffer, 0, length);
//			}
//			outBuff.flush();
//			return targetFile.getPath();
//		} catch (Exception e) {
//
//		} finally {
//			if (inBuff != null) {
//				inBuff.close();
//			}
//			if (outBuff != null) {
//				outBuff.close();
//			}
//		}
//		return targetFile.getPath();
//	}

//	/**
//	 * 读取表情配置文件
//	 *
//	 * @param context
//	 * @return
//	 */
//	public static List<String> getEmojiFile(Context context) {
//		try {
//			List<String> list = new ArrayList<String>();
//			InputStream in = context.getResources().getAssets().open("emoji");// 文件名字为rose.txt
//			BufferedReader br = new BufferedReader(new InputStreamReader(in,
//					"UTF-8"));
//			String str = null;
//			while ((str = br.readLine()) != null) {
//				list.add(str);
//			}
//
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 获取一个文件夹大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {

		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	}

	/**
	 * 判断文件是否存在
	 * @param strFilePath
	 * @return
	 */
	public static boolean isFilesExists(String strFilePath)
	{
		File file = new File(strFilePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * @param file
	 */
	public static String getMIMEType(File file) {

		String type="*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
    /* 获取文件的后缀名*/
		String end=fName.substring(dotIndex,fName.length()).toLowerCase();
		if(end=="")return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for(int i=0;i<Constants.MIME_MapTable.length;i++){ //MIME_MapTable是
			if(end.equals(Constants.MIME_MapTable[i][0]))
				type = Constants.MIME_MapTable[i][1];
		}
		return type;
	}

	public static String getFileType(String fileName) {
		String type = "";
		String fName = fileName;
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
    /* 获取文件的后缀名*/
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "") {
			return type;
		}else {
			type = end;
		}
		return  type;
	}

	/**
	 * 递归删除文件和文件夹
	 * @param file    要删除的根目录
	 */
	// "RecursionDeleteFile(new File(Environment.getExternalStorageDirectory() + "/download/youni/"));"
	public static void RecursionDeleteFile(File file){
		if(file.isFile()){
			file.delete();
			return;
		}
		if(file.isDirectory()){
			File[] childFile = file.listFiles();
			if(childFile == null || childFile.length == 0){
				file.delete();
				return;
			}
			for(File f : childFile){
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	/**
	 * 文件刷新
	 */
	public static  void refreshFile(Context context,String filePath) {
		File file = new File(filePath);
		MimeTypeMap mtm = MimeTypeMap.getSingleton();
		MediaScannerConnection.scanFile(context,
				new String[] { file.toString() },
				new String[] { mtm.getMimeTypeFromExtension(file.toString().substring(file.toString().lastIndexOf(".")+1)) },
				new MediaScannerConnection.OnScanCompletedListener() {
					@Override
					public void onScanCompleted(final String path, final Uri uri) {
						Log.d("fileUpdate", "刷新完毕path= "+path);
					}
				});
	}




	/**
	 * 删除指定目录下文件及目录
	 * @param deleteThisPath
	 * @param filePath
	 * @return
	 */
	public void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件夹大小
	 * @param file File实例
	 * @return long
	 */
	public static long getFolderSize(java.io.File file){

		long size = 0;
		try {
			java.io.File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				if (fileList[i].isDirectory())
				{
					size = size + getFolderSize(fileList[i]);

				}else{
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return size/1048576;
		return size;
	}

	/**
	 * 格式化单位
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size/1024;
		if(kiloByte < 1) {
			return size + "B";
		}

		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}










}
