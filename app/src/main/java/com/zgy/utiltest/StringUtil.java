package com.zgy.utiltest;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

	/**
	 * 超过1w则展示 x.xx万
	 * 
	 * @param @param count
	 * @param @return 
	 * @author zhuanggy
	 * @date 2014-11-14
	 */
	public static String getCount(String count) {
		try {
			int c = Integer.parseInt(count);
			if (c >= 10000) {
				return c / 10000 + "." + (c % 10000) / 100 + "w";
			} else {
				return count;
			}

		} catch (Exception e) {
			return count;
		}

	}

	/**
	 * 去掉字符串中的换行
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-9-25 注： \n 回车 \t 水平制表符 \s 空格 \r 换行
	 */
	public static String deleteEmptyLine(String str) {
		String dest = str;
		if (str != null) {
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 多个换行合并
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-9-25 注： \n 回车 \t 水平制表符 \s 空格 \r 换行
	 */
	public static String deleteMulityEmptyLine(String str) {
		String dest = str;
		if (str != null) {
			Pattern p = Pattern.compile("[\r|\n]+");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("\n");
		}
		return dest;
	}
	
	/**
	 * tab 换为一个空格，多个空格合并
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-9-25
	 */
	public static String combineBlank(String str) {
		String dest = str;
		if (str != null) {
			Pattern p = Pattern.compile("\t");
			Matcher m = p.matcher(str);
			dest = (m.replaceAll(" ")).trim().replaceAll(" +", " ");
		} else {
			dest = "";
		}
		return dest;
	}

	/**
	 * 版本号比较 0相等；>0 s1>s2；<0 s1<s2 <br/>
	 * 参考<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr(null, "2.0.7") ); -1<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr("", "2.0.7") ); 最大正int<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr("2.0.7", "") ); 最小负int<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr("2.0.5", "2.0.7") ); -2<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr("2.0.7", "2.0.5") ); 2<br/>
	 * Debug.e("", "" + StringUtil.compareVersionStr("1.0", "2.0.7") ); -1<br/>
	 * 
	 * @param @param s1
	 * @param @param s2
	 * @param @return 
	 * @author zhuanggy
	 * @date 2015-1-7
	 */
	public static int compareVersionStr(String s1, String s2) {

		if (s1 == null && s2 == null)
			return 0;
		else if (s1 == null)
			return -1;
		else if (s2 == null)
			return 1;

		String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2.split("[^a-zA-Z0-9]+");

		int i1, i2, i3;

		for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
			if (ii == arr1.length)
				return ii == arr2.length ? 0 : -1;
			else if (ii == arr2.length)
				return 1;

			try {
				i1 = Integer.parseInt(arr1[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(arr2[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = arr1[ii].compareTo(arr2[ii]);

			if (i3 != 0)
				return i3;
		}

		return 0;
	}

	// /**
	// * 获得最大长度的串
	// * @param @param username
	// * @param @return 
	// * @author zhuanggy
	// * @date 2015-3-10
	// */
	// public static String getStringMax(String str, int maxLength) {
	// String result = str;
	// if(result !=null && result.length()>maxLength) {
	// result = result.substring(0, maxLength) + "...";
	// }
	//
	// return result;
	// }

	/**
	 * 取前length个字节的字符串
	 * 
	 * @param @param value
	 * @param @return 
	 * @author zhuanggy
	 * @date 2014-7-7
	 */
	public static String limitString(String value, int length) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}

			if (valueLength > length) {
				String v = value.substring(0, i) + "...";
				return v;
			}
		}

		return value;
	}

	/**
	 * 从数组文件读取数组
	 * 
	 * @Description:
	 * @param file
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-10
	 */
	public static ArrayList<String> readArray(File file) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			result = (ArrayList<String>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 把String，写入数组文件
	 * 
	 * @Description:
	 * @param file
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-10
	 */
	public static boolean writeArray(ArrayList<String> a, File file) {

		try {

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(a);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static String getMessageCount(int count) {
		if (count > 100) {
			return "99+";
		} else if (count < 10 && count > 0) {
			return " " + count + " ";
		} else if(count == 0) {
			 return "";
		} else {
			return count + "";
		}
	}

	public static String getUrlLastParam(String url) {
		String result = "";
		if (url != null && url.length() > 0) {
			String[] strarray = url.split("/");
			if(strarray != null) {
				result = strarray[strarray.length - 1];
			}
		}

		return result;
	}
	
	@SuppressWarnings("static-access")
	public  static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字和中文//[\\pP‘’“”  
        String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
        Pattern p = Pattern.compile(regEx);
        StringBuilder sb = new StringBuilder(str);
 
        for (int len = str.length(), i = len - 1; i >= 0; --i) {   
            if (!p.matches(regEx, String.valueOf(str.charAt(i)))) {
                sb.deleteCharAt(i);  
            }  
        }  
 
        return sb.toString();  
    }
	
	/**
	 * 字符串转换unicode，返回的Unicode字符串样式：\u6700\u4ee3\u7801
	 */
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {	 
	        // 取出每一个字符
	        char c = string.charAt(i);	 
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c).toUpperCase());
	    }	 
	    return unicode.toString();
	}
	
	/**
	 * unicode 转字符串，Unicode字符串样式：\u6700\u4ee3\u7801
	 */
	public static String unicode2String(String unicode) {
	    StringBuffer string = new StringBuffer();
	    String[] hex = unicode.split("\\\\u");
	    for (int i = 1; i < hex.length; i++) {	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	        // 追加成string
	        string.append((char) data);
	    }	 
	    return string.toString();
	}
	
	// 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
    
    // 去掉字符串重复的字符
    public static String removeRepeatedChar(String s) {
        if (s == null)  
            return s;  
  
        StringBuffer sb = new StringBuffer();
        int i = 0, len = s.length();  
        while (i < len) {  
            char c = s.charAt(i);  
            sb.append(c);  
            i++;  
            while (i < len && s.charAt(i) == c) {//这个是如果这两个值相等，就让i+1取下一个元素  
                i++;  
            }  
        }  
        return sb.toString();  
    } 
    
	// 邮箱认证方法
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}
	
    public static boolean isPhoneNumber(String number) {
//      String rgx = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
      String rgx = "^((1[0-9][0-9]))\\d{8}$";
      return isCorrect(rgx, number);
  }
    
    private static boolean isCorrect(String rgx, String res) {
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(res);
        return m.matches();
    }
    
    
    public static final String Digit_pwd = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    public static boolean isOkPwd(String pwd) {
    	if(TextUtils.isEmpty(pwd)) {
    		return false;
    	}
    	 for(int i=0; i<pwd.length(); i++) {
    		 if(!Digit_pwd.contains(pwd.charAt(i)+"")) {
    			 return false;
    		 }
    	 }
    	 
    	 return true;
    }
}
