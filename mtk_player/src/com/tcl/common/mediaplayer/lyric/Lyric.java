/**
 * ------------------------------------------------------------------
 *  Copyright (c) 2011 TCL, All Rights Reserved.
 *  -----------------------------------------------------------------
 *  
 * @author bailing /qiaobl@tcl.com/2011.09.21
 * @JDK version: 1.6
 * @brief: provide the play and control of video and audio;
 * @version:v1.0
 */
package com.tcl.common.mediaplayer.lyric;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.tcl.common.mediaplayer.http.HttpUtils;
import com.tcl.common.mediaplayer.utils.Utils;

/**
 * 表示一首歌的歌词对象,它可以以某种方式来画自己
 * 
 * @author hadeslee
 */
public class Lyric implements Serializable {

	private static final String TAG = "AudioPlayer_Lyric";
	private static final long serialVersionUID = 20071125L;
	private List<Sentence> list = new ArrayList<Sentence>();// 里面装的是所有的句子
	private transient PlayListItem info;// 有关于这首歌的信息
	private int offset;// 整首歌的偏移量
	// 用于缓存的一个正则表达式对象
	private static final Pattern pattern = Pattern
			.compile("(?<=\\[).*?(?=\\])");

	private ILyricParseListener mLyricParseListener;
	private String codeLyric = "GBK"; // "UTF-8";

	/**
	 * 读取某个指定的歌词文件,这个构造函数一般用于 拖放歌词文件到歌词窗口时调用的,拖放以后,两个自动关联
	 * 
	 * @param file
	 *            歌词文件
	 * @param info
	 *            歌曲信息
	 */
	public Lyric(File file, PlayListItem info,
			ILyricParseListener lyricParseListener) {
		this.offset = info.getOffset();
		this.info = info;
		mLyricParseListener = lyricParseListener;
		// codeLyric = parse(file);
		Utils.printLog("Lyric --", "" + codeLyric);
		init(file);
	}

	public Lyric(String url_lyc, PlayListItem info,
			ILyricParseListener lyricParseListener) {
		this.offset = info.getOffset();
		this.info = info;
		mLyricParseListener = lyricParseListener;
		initURL(url_lyc);
	}

	public List<Sentence> getLyricList() {
		return list;
	}

	/**
	 * 根据文件来初始化
	 * 
	 * @param file
	 *            文件
	 */
	private void init(File file) {

		BufferedReader br = null;
		try {
			Log.d(TAG, "now file path is " + file.getPath());

			codeLyric = codeString(file.getPath());
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), codeLyric));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\n");
			}

			if (Utils.DEBUG) {
				System.out.println("++++++++++++++++++++++++++++++++ Lyc \n"
						+ sb.toString());
			}
			init(sb.toString());
		} catch (Exception ex) {
			Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);

		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
				Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}
	}

	/**
	 * 根据文件来初始化
	 * 
	 * @param file
	 *            文件
	 */
	private void initURL(final String url) {
		// URL downUrl=null;
		// BufferedReader br = null;

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection conn = null;
				InputStream inStream = null;
				BufferedReader br = null;
				try {
					URL requestUrl = new URL(url);
					conn = (HttpURLConnection) requestUrl.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Charset", "UTF-8"); // UTF-8
					conn.connect();
					Log.d(TAG,
							"initURL    conn.getResponseCode()=="
									+ conn.getResponseCode());
					if (conn.getResponseCode() == 200) {
						inStream = conn.getInputStream();
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						copy(inStream, output);
						byte[] b = output.toByteArray();

						if (b[4] > -30 && b[0] >0) {
							codeLyric = "UTF-8";
						} else {
							codeLyric = "GBK";
						}
						Log.d(TAG, "initURL   codeLyric=" + codeLyric);
						String res = new String(b, codeLyric);

						if (Utils.DEBUG) {
							System.out
									.println("++++++++++++++++++++++++++++++++ Lyc \n"
											+ res.toString());
						}
						init(res);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mLyricParseListener.onParseFinished(null);
				} finally {
					try {
						if (br != null) {
							br.close();
						}
						if (inStream != null) {
							inStream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}).start();
	}

	// try {
	// downUrl = new URL(url);
	// HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
	// http.setRequestMethod("GET");
	// http.setRequestProperty("Accept", "*/*");
	// http.setRequestProperty("Accept-Language", "zh-CN");
	// http.setRequestProperty("Referer", downUrl.toString());
	// http.setRequestProperty("Charset", "UTF-8");
	// http.setRequestProperty("Range", "bytes=" + 0 + "-");
	// http
	// .setRequestProperty(
	// "User-Agent",
	// "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
	// http.setRequestProperty("Connection", "Keep-Alive");
	// http.setConnectTimeout(60000);
	// http.setReadTimeout(60000);
	//
	// InputStream inStream = http.getInputStream();
	// br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
	// StringBuilder sb = new StringBuilder();
	// String temp = null;
	// while ((temp = br.readLine()) != null) {
	// sb.append(temp).append("\n");
	// }
	//
	// if(Utils.DEBUG){
	// System.out.println("++++++++++++++++++++++++++++++++ Lyc \n"+sb.toString());
	// }
	// init(sb.toString());
	// } catch (Exception ex) {
	// Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);
	// mLyricParseListener.onParseFinished(null);
	// } finally {
	// try {
	// if(br!=null)
	// br.close();
	// } catch (Exception ex) {
	// Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null,
	// ex);
	// }
	// }
	// }

	/**
	 * 最重要的一个方法，它根据读到的歌词内容 进行初始化，比如把歌词一句一句分开并计算好时间
	 * 
	 * @param content
	 *            歌词内容
	 */
	private void init(String content) {
		// 如果歌词的内容为空,则后面就不用执行了
		// 直接显示歌曲名就可以了
		if (content == null || content.trim().equals("")) {
			list.add(new Sentence(info.getFormattedName(), Integer.MIN_VALUE,
					Integer.MAX_VALUE));
			Log.d(TAG, "init--1111111111---" + list.size());
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new StringReader(content));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				parseLine(temp.trim());
			}
			br.close();
			// 读进来以后就排序了
			Collections.sort(list, new Comparator<Sentence>() {

				public int compare(Sentence o1, Sentence o2) {
					return (int) (o1.getFromTime() - o2.getFromTime());
				}
			});
			// 处理第一句歌词的起始情况,无论怎么样,加上歌名做为第一句歌词,并把它的
			// 结尾为真正第一句歌词的开始
			if (list.size() == 0) {
				list.add(new Sentence(info.getFormattedName(), 0,
						Integer.MAX_VALUE));
				Log.d(TAG, "init--222222222222---" + list.size());
				return;
			} else {
				Sentence first = list.get(0);
				list.add(
						0,
						new Sentence(info.getFormattedName(), 0, first
								.getFromTime()));
			}

			int size = list.size();
			for (int i = 0; i < size; i++) {
				Sentence next = null;
				if (i + 1 < size) {
					next = list.get(i + 1);
				}
				Sentence now = list.get(i);
				if (next != null) {
					now.setToTime(next.getFromTime() - 1);
				}
			}
			// Utils.printLog("LYC", "The lyc List Content");
			// for (int i = 0; i < size; i++) {
			// Utils.printLog("Sentence i ="+i,
			// "   From "+list.get(i).getFromTime()
			// +"  To"+list.get(i).getToTime()+" with Content ="+list.get(i).getContent());
			// }
			// 如果就是没有怎么办,那就只显示一句歌名了
			if (list.size() == 1) {
				list.get(0).setToTime(Integer.MAX_VALUE);
			} else {
				// Sentence last = list.get(list.size() - 1);
				// last.setToTime(info == null ? Integer.MAX_VALUE :
				// info.getLength() * 1000 + 1000);
			}
			Log.d(TAG, "init--3333333---" + list.size());
			mLyricParseListener.onParseFinished(list);
		} catch (Exception ex) {
			Logger.getLogger(Lyric.class.getName()).log(Level.SEVERE, null, ex);
			mLyricParseListener.onParseFinished(null);
		}
	}

	/**
	 * 分析出整体的偏移量
	 * 
	 * @param str
	 *            包含内容的字符串
	 * @return 偏移量，当分析不出来，则返回最大的正数
	 */
	private int parseOffset(String str) {
		String[] ss = str.split("\\:");
		if (ss.length == 2) {
			if (ss[0].equalsIgnoreCase("offset")) {
				int os = Integer.parseInt(ss[1]);
				System.err.println("整体的偏移量：" + os);
				return os;
			} else {
				return Integer.MAX_VALUE;
			}
		} else {
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * 分析这一行的内容，根据这内容 以及标签的数量生成若干个Sentence对象 当此行中的时间标签分布不在一起时，也要能分析出来 所以更改了一些实现
	 * 20080824更新
	 * 
	 * @param line
	 *            这一行
	 */
	private void parseLine(String line) {
		if (line.equals("")) {
			return;
		}
		// Utils.printLog("LYC Generate Sentence parseLine", "line ="+line);
		Matcher matcher = pattern.matcher(line);
		List<String> temp = new ArrayList<String>();
		int lastIndex = -1;// 最后一个时间标签的下标
		int lastLength = -1;// 最后一个时间标签的长度
		while (matcher.find()) {

			String s = matcher.group();
			// Utils.printLog("LYC Generate Sentence", " matcher.group()=  "+s);
			int index = line.indexOf("[" + s + "]");
			if (lastIndex != -1 && index - lastIndex > lastLength + 2) {
				// Utils.printLog("LYC Generate Sentence", " lastIndex != -1 ");
				// 如果大于上次的大小，则中间夹了别的内容在里面
				// 这个时候就要分段了
				String content = line.substring(lastIndex + lastLength + 2,
						index);
				for (String str : temp) {
					long t = parseTime(str);
					// Utils.printLog("LYC Generate Sentence", "time ="+t);
					if (t != -1) {
						// Utils.printLog("LYC Generate Sentence",
						// "content ="+content +" time ="+t);
						list.add(new Sentence(content, t));
					}
				}
				temp.clear();
			}
			temp.add(s);
			lastIndex = index;
			lastLength = s.length();
		}
		// 如果列表为空，则表示本行没有分析出任何标签
		if (temp.isEmpty()) {
			return;
		}
		try {
			int length = lastLength + 2 + lastIndex;
			String content = line.substring(length > line.length() ? line
					.length() : length);
			// if (Config.getConfig().isCutBlankChars()) {
			// content = content.trim();
			// }
			// 当已经有了偏移量的时候，就不再分析了
			if (content.equals("") && offset == 0) {

				for (String s : temp) {
					int of = parseOffset(s);
					if (of != Integer.MAX_VALUE) {
						offset = of;
						info.setOffset(offset);
						break;// 只分析一次
					}
				}
				return;
			}
			for (String s : temp) {
				long t = parseTime(s);
				// Utils.printLog("LYC Generate Sentence", "content ="+content
				// +" time ="+t);
				if (t != -1) {

					list.add(new Sentence(content, t));
				}
			}
		} catch (Exception exe) {
		}
	}

	/**
	 * 把如00:00.00这样的字符串转化成 毫秒数的时间，比如 01:10.34就是一分钟加上10秒再加上340毫秒 也就是返回70340毫秒
	 * 
	 * @param time
	 *            字符串的时间
	 * @return 此时间表示的毫秒
	 */
	private long parseTime(String time) {
		String[] ss = time.split("\\:|\\.");
		// 如果 是两位以后，就非法了
		// for(int i=0;i<ss.length;i++){
		// Utils.printLog("LYC", "parse Time ss["+i+"]="+ss[i]);
		// }
		//
		if (ss.length < 2) {
			return -1;
		} else if (ss.length == 2) {// 如果正好两位，就算分秒
			try {
				// 先看有没有一个是记录了整体偏移量的
				if (offset == 0 && ss[0].equalsIgnoreCase("offset")) {
					offset = Integer.parseInt(ss[1]);
					info.setOffset(offset);
					System.err.println("整体的偏移量：" + offset);
					return -1;
				}
				int min = Integer.parseInt(ss[0]);
				int sec = Integer.parseInt(ss[1]);
				if (min < 0 || sec < 0 || sec >= 60) {
					throw new RuntimeException("数字不合法!");
				}
				return (min * 60 + sec) * 1000L;
			} catch (Exception exe) {
				exe.printStackTrace();
				return -1;
			}
		} else if (ss.length == 3) {// 如果正好三位，就算分秒，十毫秒
			try {
				int min = Integer.parseInt(ss[0]);
				int sec = Integer.parseInt(ss[1]);
				int mm = Integer.parseInt(ss[2]);
				if (mm > 99) {
					mm = mm / 10;
				}
				if (min < 0 || sec < 0 || sec >= 60 || mm < 0 || mm > 99) {
					throw new RuntimeException("数字不合法!");
				}
				return (min * 60 + sec) * 1000L + mm * 10;
				// return (min * 60 + sec) * 1000L + mm;
			} catch (Exception exe) {
				exe.printStackTrace();
				return -1;
			}
		} else {// 否则也非法
			return -1;
		}
	}

	private String parse(File file) {
		FileReader read;
		String encode = "GBK";
		try {
			read = new FileReader(file);
			BufferedReader br = new BufferedReader(read);
			String str = "";

			int i = 0;
			if ((str = br.readLine()) != null) {
				// encode = "GB2312";
				// try {
				// byte[] stringBytes;
				// stringBytes = str.getBytes(encode);
				// String gb2312Str = new String(stringBytes, encode);
				// //
				// if (str.equals(gb2312Str)) {
				// String s = encode;
				// return s;
				// }
				// } catch (Exception exception) {
				// }
				// encode = "ISO-8859-1";
				// try {
				// if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s1 = encode;
				// return s1;
				// }
				// } catch (Exception exception1) {
				// }
				// encode = "GB18030";
				// try {
				// if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s3 = encode;
				// return s3;
				// }
				// } catch (Exception exception3) {
				// }
				encode = "GBK";
				try {
					if (str.equals(new String(str.getBytes(encode), encode))) {
						String s3 = encode;
						return s3;
					}
				} catch (Exception exception3) {
				}

				encode = "UTF-8";
				try {
					if (str.equals(new String(str.getBytes(encode), encode))) {
						String s2 = encode;
						return s2;
					}
				} catch (Exception exception2) {
				}

				//
				// encode = "UNICODE";
				// try {
				// if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s3 = encode;
				// return s3;
				// }
				// } catch (Exception exception3) {
				// }
				//
				// encode = "GB2312";
				// try {
				// if (str.equals(new String(str.getBytes(encode), encode))) {
				// String s3 = encode;
				// return s3;
				// }
				// } catch (Exception exception3) {
				// }
				// Log.i(TAG,"return default as UTF-8");
				return "GBK";

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encode;
	}

	private String codeString(String fileName) throws Exception {

		String code = null;
		File file = new File(fileName);
		InputStream in = new java.io.FileInputStream(file);
		byte[] b = new byte[6];
		in.read(b);
		in.close();
		Log.d(TAG, "Lyric b is " + b[0] + "===" + b[1] + "====" + b[2] + "==="
				+ b[3] + "====" + b[4] + "===" + b[5]);

		if (b[4] > -30 && b[0] > 0) {
			code = "UTF-8";
		} else {
			code = "GBK";
		}
		return code;
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}
