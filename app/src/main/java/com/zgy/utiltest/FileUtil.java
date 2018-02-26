package com.zgy.utiltest;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * @author zhang
 *
 */
/**
 * @author zhang
 *
 */

/**
 * @author zhang
 *
 */
public class FileUtil {
	/**
	 * 拷贝目录下的所有文件到指定目录
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFilesTo(File srcDir, File destDir) throws IOException {
		if (!destDir.exists())
			destDir.mkdirs();
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;// 判断是否是目录
		}
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}

		return true;
	}

	/**
	 * 移动目录下的所有文件到指定目录
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static boolean moveFilesTo(File srcDir, File destDir) throws IOException {
		if (!destDir.exists())
			destDir.mkdirs();
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;// 判断是否是目录
		}
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
				srcFiles[i].delete();
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "/" + srcFiles[i].getName());
				moveFilesTo(srcFiles[i], theDestDir);
				delDir(srcFiles[i]);
			}
		}
		delDir(srcDir);
		return true;
	}

	public static boolean delDir(File dir) {
		if (dir == null || !dir.exists() || dir.isFile()) {
			return false;
		}

		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				delDir(file);// 递归
			}
		}
		dir.delete();
		return true;
	}

	/**
	 * 拷贝一个文件,srcFile源文件，destFile目标文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFileTo(File srcFile, File destFile) throws IOException {
		if (srcFile.isDirectory() || destFile.isDirectory()) {
			return false;// 判断是否是文件
		}
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		} else {
			if (destFile.exists()) {
				destFile.delete();
			}
		}
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = fis.read(buf)) != -1) {
			fos.write(buf, 0, readLen);
		}
		fos.flush();
		fos.close();
		fis.close();

		return true;
	}

	/**
	 * 递归删除某目录及其所有子文件和子目录
	 * 
	 * @Description:
	 * @param dir
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-24
	 */
	public static boolean delFileDir(File dir, boolean deleteRoot) {
		if (dir == null || !dir.exists()) {
			return false;
		}
		if (dir.isFile()) {
			dir.delete();
		} else {
			File[] listFiles = dir.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				if (deleteRoot) {
					dir.delete();
				}
			} else {
				for (File file : listFiles) {
					if (file.isFile()) {
						file.delete();
					} else if (file.isDirectory()) {
						delFileDir(file, true);
					}
				}

				if (deleteRoot) {
					dir.delete();
				}
			}
		}

		return true;
	}

	public static void CopyAssetFile(Context context, String fileName, String desDir) throws Exception {

		String strCpSdPath = desDir + fileName;
		File file = new File(strCpSdPath);
		if (file.exists()) {
			file.delete();
		}

		if (!file.getParentFile().exists()) {

			if (!file.getParentFile().mkdirs()) {
				Log.e("--CopyAssets--", "cannot create directory.");
				throw new Exception("存储卡写入失败！");
			}
		}

		try {
			InputStream myInput = context.getAssets().open(fileName);

			file.createNewFile();
			OutputStream myOutput = new FileOutputStream(file, true);
			byte[] buffer = new byte[1024];
			int length;

			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("字体文件读取失败！");
		}

	}

	/**
	 * 获得多个文件目录的大小
	 * 
	 * @param @param files
	 * @param @return 
	 * @author zhuanggy
	 * @date 2015-1-26
	 */
	public static String getFilesLength(String[] files) {
		long sizeFinal = 0;
		if (files != null) {
			for (String filePath : files) {
				FileUtil fileUtil = new FileUtil();
				sizeFinal = sizeFinal + fileUtil.getFileSize(new File(filePath));
			}
			return sizeLongToStringOne(sizeFinal);
		} else {
			return "0byte";
		}

	}

	/**
	 * 清除多个文件目录
	 * 
	 * @param @param files 
	 * @author zhuanggy
	 * @date 2015-1-26
	 */
	public static void deleteFiles(String[] files) {
		if (files != null) {
			for (String filePath : files) {
				FileUtil.delFileDir(new File(filePath), false);
			}
		}

	}

	/**
	 * 递归获得文件目录大小
	 * 
	 * @Description:
	 * @param dir
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-24
	 */
	private long size = 0;

	public long getFileSize(File dir) {

		try {
			if (!dir.isDirectory()) {
				setSize(getSize() + dir.length());
			} else {
				for (File file : dir.listFiles()) {
					if (!file.isDirectory()) {
						setSize(getSize() + file.length());
					} else {
						getFileSize(file);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			setSize(-1);
		}

		return getSize();
	}

	private void setSize(long size) {
		this.size = size;
	}

	private long getSize() {
		return this.size;
	}

	/**
	 * 转换文件的大小，将文件的字节数转换为kb、mb、或gb
	 * 
	 * @Description:
	 * @return 保留1位小数
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-1-11
	 */
	public static String sizeLongToStringOne(long size) {
		String a = "";
		if (size == 0) {
			a = "0byte";
		} else if (size < 1024) {
			a = String.format("%d byte(s)", size);
		} else if (size / 1024 < 1024.0) {
			a = String.format("%.1f KB", size / 1024.0);
		} else if (size / 1048576 < 1024) {
			a = String.format("%.1f MB", size / 1048576.0);
		} else {
			a = String.format("%.1f GB", size / 1073740824.0);
		}
		return a;
	}

	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param @param file
	 * @param @return 
	 * @author zhuanggy
	 * @date 2015-2-12
	 */

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

//	/**
//	 * 以UTF-8方式读取文档内容，并将内容以String类型返回
//	 *
//	 * @param @param path
//	 * @param @return 
//	 * @author xln
//	 * @date 2015-6-30
//	 */
//	public static String readFileContent(String path) {
//		File file = new File(path);
//		if (file.exists() && file.isFile()) {
//			FileInputStream fin;
//			try {
//				fin = new FileInputStream(path);
//				int length = fin.available();
//				byte[] buffer = new byte[length];
//				fin.read(buffer);
//				return EncodingUtils.getString(buffer, "UTF-8");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return "";
//	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			return false;
		} else if (!srcFile.isFile()) {
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			return false;
		} else if (!srcDir.isDirectory()) {
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				new File(destDirName).delete();
			} else {
				return false;
			}
		} else {
			// 创建目的目录
			System.out.println("目的目录不存在，准备创建。。。");
			if (!destDir.mkdirs()) {
				System.out.println("复制目录失败：创建目的目录失败！");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 修改文件的后缀名, 暂时修改为txt格式
	 *
	 * @param path
	 */
	public static boolean renameFileEndwithTxt(String path){
		File file = new File(path);
		String filename = file.getAbsolutePath();
        if(filename.indexOf(".")>=0)     
        {     
            filename = filename.substring(0, filename.lastIndexOf("."));     
        }
		if(file.exists()){
			file.renameTo(new File(filename + ".txt"));
			return true;
		}
		
		return false;
	}
	
	
	

	/**
	 * 获取目录下某个后缀名的所有文件路径列表
	 * 
	 * @param files
	 *            返回的路径列表 ，每个路径以 file:// 开头
	 * @param path
	 *            搜索的文件夹
	 * @param ext
	 *            要搜索的后缀名，如： “.jpg”
	 * @param filename
	 *            如果搜索指定文件名的所有文件，则设置此参数，否则传入null
	 * @param excludefilename
	 *            如果排除指定文件名的所有文件，则设置此参数，否则传入null，如：搜索所有jpg文件，但不包含cover.jpg，此处可传入 cover.jpg
	 * @return void
	 */
	public static void getFilesInDirectory(List<String> files, String path, final String ext, final String filename, final String excludefilename) {
		File dir = new File(path);
		// File file = new File(dir,"big_pic@2x.png");
		// 内部类
		FilenameFilter searchSuffix = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// Debug.e("add", name.substring(name.lastIndexOf('/')));
				String string = name.substring(name.lastIndexOf('/') + 1);
				if (name.toLowerCase().lastIndexOf(ext) > -1) {
					if (excludefilename != null) {
						if (string.equalsIgnoreCase(excludefilename)) {
							return false;
						}
					}
					if (filename != null) {
						if (!string.equalsIgnoreCase(filename)) {
							return false;
						}
					}
					return true;
				}
				return false;
			}
		};
		writeToArr(dir, searchSuffix, files);
	}

	private static void writeToArr(File dir, FilenameFilter searchSuffix, List<String> al) {

		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				// 递归了。
				writeToArr(f, searchSuffix, al);
			} else {
				if (searchSuffix.accept(dir, f.getPath())) {
					al.add("file://" + f.getPath());
					Log.e("imgpath", f.getPath());
					// ToastView.showToast(TemplateDetailActivity.this, f.getPath(), ToastView.LENGTH_SHORT);
				}
			}
		}
	}
	
	// 个人做字读码表文件
	public static class CodeMapRecord {
        public CodeMapRecord(String code, String fontchar, String pinyin, String shengdiao, int nClass) {
			mCode = code;
			mFontChar = fontchar;
			mPinYin = pinyin;
			mShengDiao = shengdiao;
			mCategory = nClass;
		}
        public String mCode;
        public String mFontChar;
        public String mPinYin;
        public String mShengDiao;
        public int    mCategory;
    }
	
	//开始读取
//	 public static ArrayList<CodeMapRecord> readCodeMap() throws Exception {
//	        ArrayList<CodeMapRecord> result = new ArrayList<CodeMapRecord>();
//
//	        InputStreamReader inputReader = new InputStreamReader( FontApplication.getInstance().getResources().getAssets().open("6763.txt") );
//	        BufferedReader in = new BufferedReader(inputReader);
//	        //逐行读取
//	        String line = in.readLine();
//	        while (line != null) {
//	            CodeMapRecord rec = parseLine(line);
//	            if (rec == null)
//	                Log.e("", "could not parse line: '"+line+"'");
//	            else {
//	                result.add(rec);
//	            }
//	            line = in.readLine();
//	        }
//	        in.close();
//	        return result;
//	    }

	private static CodeMapRecord parseLine(String line) {
		if (line == null)
            return null;
        String[] split = line.split(" ");
        if (split.length < 4)
            return null;
        try {
            return new CodeMapRecord(split[0],split[1],split[2],split[3], 0);
        }
        catch (Exception e) {
            Log.e("","Invalid format in line '"+line+"'");
            return null;
        }
	}
	
	private static class PicFilenameFilter implements FilenameFilter {
		private String filterString;
		
		public PicFilenameFilter(String filterString) {
			super();
			this.filterString = filterString;
		}

		@Override
		public boolean accept(File arg0, String arg1) {
			// TODO Auto-generated method stub
			return arg1.endsWith(filterString);
		}		
	}
	
	// 获取某个路径下某个后缀名的文件个数
	// e.g GetFileNumInDir(PersonalFontCharacterInfo.getContourFileDir(mCurSelFont.id) + "/", ".png");
	public static int GetFileNumInDir(String path, String filterString){
		if (path != null && filterString != null) {
			File file = new File(path);
			if (file.isDirectory()) {
				PicFilenameFilter picFilter = new PicFilenameFilter(filterString);
				return file.listFiles(picFilter).length;
			}
		}	
		
		return 0;
	}

	public static String getPathFromImageURI(Context context, Uri contentURI) {
		String result;
		Cursor cursor = context.getContentResolver().query(contentURI,
				new String[]{MediaStore.Images.ImageColumns.DATA},
				null, null, null);
		if (cursor == null) result = contentURI.getPath();
		else {
			cursor.moveToFirst();
			int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(index);
			cursor.close();
		}
		return result;
	}
}
