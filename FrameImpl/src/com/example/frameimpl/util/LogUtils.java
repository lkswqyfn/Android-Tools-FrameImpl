package com.example.frameimpl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class LogUtils {
	/**
	 * 异常信息标־
	 */
	private final static String TAG = "MainLog";

	private static final String LOG_EXCEPTION = "异常信息-->";

	public static final String LOG_TAG_USER_OPERATION = "用户操作-->";

	public static final String LOG_TAG_PARAMETER = "参数-->";

	public static final String LOG_MODEL_SYSTEM = "系统-->";

	public final static int E_LOG_VERBOSE = 0; // Debug信息，verbose信息

	public final static int E_LOG_DEBUG = 1; // Debug信息，一般调试信息

	public final static int E_LOG_INFO = 2; // Release信息，一般提示信息

	public final static int E_LOG_WARN = 3; // Release信息，一般警告信息

	public final static int E_LOG_ERROR = 4; // Release信息，错误信息

	private static boolean enableLogToSdcard = false;

	private static boolean enableLogToLogcat = false;

	private static boolean logInitOk = false;

	private static StringBuilder logBuffer = new StringBuilder();

	private static int cachedSize = 0;

	private static FileOutputStream mFos;

	/**
	 * 记录异常信息
	 * 
	 * @param e
	 *            捕获到的异常
	 */
	public static void logExceptionInfo(Throwable e) {
		LogUtils.pa(LOG_EXCEPTION, e.getMessage());
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		LogUtils.pa(LOG_EXCEPTION, stringWriter.toString());
	}

	/**
	 * 设置是否打开日志开关 设置为关闭日志时 即便路径中存在日志文件也不记录
	 * 
	 * @param flag
	 */
	public static void setEnableLogToSdcard(boolean flag) {
		enableLogToSdcard = flag;
	}

	/**
	 * 设置是否将日志输出到logcat
	 * 
	 * @param flag
	 */
	public static void setEnableLogToLogcat(boolean flag) {
		enableLogToLogcat = flag;
	}

	/**
	 * 初始化日志记录系统
	 * 
	 * @param logPath
	 *            日志文件的路径和文件名
	 */
	public static void initLogUtils(boolean enableLogToSdcard, boolean enableLogToLogcat, String logPath) {
		if (!logInitOk) {
			logInitOk = true;

			setEnableLogToLogcat(enableLogToLogcat);
			setEnableLogToSdcard(enableLogToSdcard && checkLogFileExist(logPath));

			if (LogUtils.enableLogToSdcard) {
				checkLogFile(logPath);
				initLogFile(logPath);
			}
		}
	}

	/**
	 * 关闭日志记录系统 关闭后记录日志和清除日志功能不可用 只有重新初始化后方可使用
	 */
	public static boolean uninitLogUtils() {
		flushLogToSdcard();
		return uninitLogFile();
	}

	/**
	 * 检查指定的日志文件是否存在 如不存在则不记录日志
	 * 
	 * @param logpath
	 *            日志文件路径
	 */
	public static boolean checkLogFileExist(String logPath) {
		try {
			File logFile = new File(logPath);
			return logFile.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 记录用户操作 自动在信息前添加 用户操作 --> 标识
	 * 
	 * @param model
	 *            模块名
	 * @param info
	 *            信息
	 */
	public static void uo(String model, String info) {
		if (model != null) {
			int index = model.lastIndexOf('.');
			StringBuilder sb = new StringBuilder();
			sb.append(LOG_TAG_USER_OPERATION);
			sb.append("[");
			sb.append(model.substring(index + 1));
			sb.append("] ");
			sb.append(info);

			info(sb.toString());
		}
	}

	/**
	 * 记录用户操作 自动在信息前添加 用户操作
	 * 
	 * @param info
	 *            信息
	 */
	public static void uo(String info) {
		info(LOG_TAG_USER_OPERATION + info);
	}

	/**
	 * 记录方法产生的参数和值 自动在信息前添加 参数 --> 标识
	 * 
	 * @param paname
	 * @param pavalue
	 */
	public static void pa(String model, String paname, Object pavalue) {
		if (model != null) {
			int index = model.lastIndexOf('.');
			StringBuilder sb = new StringBuilder();
			sb.append(LOG_TAG_PARAMETER);
			sb.append("[");
			sb.append(model.substring(index + 1));
			sb.append("] ");
			sb.append(paname);
			sb.append(pavalue);

			info(sb.toString());
		}
	}

	/**
	 * 记录方法产生的参数和值 自动在信息前添加 参数
	 * 
	 * @param paname
	 * @param pavalue
	 */
	public static void pa(String model, Object parameter) {
		if (model != null) {
			int index = model.lastIndexOf('.');
			StringBuilder sb = new StringBuilder();
			sb.append(LOG_TAG_PARAMETER);
			sb.append("[");
			sb.append(model.substring(index + 1));
			sb.append("] ");
			sb.append(parameter);

			info(sb.toString());
		}
	}

	/**
	 * 记录verbose信息
	 * 
	 * @param info
	 */
	public static void verbose(String info) {
		saveLogToSDcard(E_LOG_VERBOSE, info);

		if (LogUtils.enableLogToLogcat) {
			Log.v(TAG, info);
		}
	}

	/**
	 * 记录一般调试信息
	 * 
	 * @param info
	 */
	public static void debug(String info) {
		saveLogToSDcard(E_LOG_DEBUG, info);

		if (LogUtils.enableLogToLogcat) {
			Log.d(TAG, info);
		}
	}

	/**
	 * 记录一般提示信息
	 * 
	 * @param info
	 */
	public static void info(String info) {
		saveLogToSDcard(E_LOG_INFO, info);

		if (LogUtils.enableLogToLogcat) {
			Log.i(TAG, info);
		}
	}

	/**
	 * 记录一般警告信息
	 * 
	 * @param info
	 */
	public static void warn(String info) {
		saveLogToSDcard(E_LOG_WARN, info);

		if (LogUtils.enableLogToLogcat) {
			Log.w(TAG, info);
		}
	}

	/**
	 * 记录错误信息
	 * 
	 * @param info
	 */
	public static void error(String info) {
		saveLogToSDcard(E_LOG_ERROR, info);

		if (LogUtils.enableLogToLogcat) {
			Log.e(TAG, info);
		}
	}

	private static void logToCache(int level, String info) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		logBuffer.append(formatter.format(date));
		logBuffer.append("  <");
		logBuffer.append(Integer.toString(level));
		logBuffer.append("> ");
		logBuffer.append(info);
		logBuffer.append("\r\n");

		cachedSize++;

		// LogObj logObj = new LogObj();
		// logObj.level = level;
		// logObj.logstr = info;
		// logObj.timestamp = System.currentTimeMillis();
		// logBuffer.add(logObj);
	}

	/**
	 * 记录日志信息到日志文件
	 * 
	 * @param level
	 *            日志级别
	 * @param info
	 *            日志内容
	 */
	public static void saveLogToSDcard(int level, String info) {
		if (!LogUtils.enableLogToSdcard) {
			return;
		} else {
			logToCache(level, info);

			if (cachedSize > 20) {
				writeLogFile(logBuffer);
				logBuffer.delete(0, logBuffer.capacity());
				cachedSize = 0;
			}
			// if (logBuffer.size() > 20) {
			// for (LogObj logObj : logBuffer) {
			// writeLogFile(logObj.level, logObj.logstr, logObj.timestamp);
			// }
			//
			// logBuffer.clear();
			// }
		}
	}

	/**
	 * 将缓存中的数据全部写入日志文件
	 */
	public static void flushLogToSdcard() {
		writeLogFile(logBuffer);
		logBuffer.delete(0, logBuffer.capacity());
		cachedSize = 0;
	}

	public static void getANRInfo() {
		final String tracesFilePath = "/data/anr/traces.txt";
		File tracesFile = new File(tracesFilePath);
		if (tracesFile.isFile() && tracesFile.exists()) {

		} else {
			LogUtils.pa("/data/anr/traces.txt", "");
		}
	}


	/**
	 * 设置日志文件路径
	 * 
	 * @param [in] strPath 日志文件路径
	 * @return 设置路径及打开文件是否成功
	 */
	private static boolean initLogFile(String path) {
		try {
			mFos = new FileOutputStream(new File(path),true);
			return mFos != null;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * 清空日志文件内容
	 */
	private static boolean checkLogFile(String path) {
		File file = new File(path);
		try {
			if (file.exists() && file.length() > 1024*1024) {
				file.delete();
				file.createNewFile();
			}

			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * 释放日志文件
	 * 
	 * @return
	 */
	private static boolean uninitLogFile() {
		try {
			if (mFos != null) {
				mFos.close();
				mFos = null;
			}

			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	private static boolean writeLogFile(StringBuilder sb) {
		if (mFos == null) {
			return false;
		}

		try {
			mFos.write(sb.toString().getBytes());
			mFos.flush();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}

}
