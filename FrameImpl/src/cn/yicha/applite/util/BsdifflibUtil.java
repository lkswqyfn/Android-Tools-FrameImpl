package cn.yicha.applite.util;
/**
 * 增量升级本地端
 * @author wqYuan
 *
 */
public class BsdifflibUtil {
		static {
			System.loadLibrary("bsdiff");
		}
		public static native String applyPatchToOldApk(String oldApkPath,String newApkPath,String patch); 
}
