package com.example.frameimpl.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.util.EncodingUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.example.frameimpl.bean.ResponseResult;

@SuppressWarnings("unused")
public class RequestFormatIsJson implements IRequestFormat {

	/**
	 * 构造Get参数
	 * 
	 * @param paramNames
	 *            参数名
	 * @param paramValues
	 *            参数值
	 * @return
	 */
	private String formatReqParam(String[] paramNames, String... paramValues) {

		StringBuilder sb = new StringBuilder();

		String encodingType = "utf-8";
		if (paramValues != null)
			for (int i = 0, count = paramNames.length; i < count; i++) {
				if (sb.length() != 0) {
					sb.append("&");
				}
				sb.append(paramNames[i]);
				sb.append("=");
				if (paramValues[i] != null && paramValues[i].length() > 0) {
					try {
						sb.append(URLEncoder.encode(paramValues[i], encodingType));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}

		return sb.toString();
	}

	private String RequestPage(String uri, String postString) throws IOException {

		String reResult = "";
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		String encodingType = "utf-8";
		try {
			URL requestUrl = new URL(uri);
			conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(600000);
			conn.connect();

			if (!TextUtils.isEmpty(postString)) {
				OutputStream requestOutputStream = conn.getOutputStream();
				byte[] requestBytes = EncodingUtils.getAsciiBytes(postString);
				requestOutputStream.write(requestBytes);
				requestOutputStream.close();
			}

			if (conn.getResponseCode() == HttpStatus.SC_OK) {

				inputStream = conn.getInputStream();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				byte[] b = new byte[1024];
				int len = inputStream.read(b);
				while (len != -1) {
					outputStream.write(b, 0, len);
					len = inputStream.read(b);
				}
				outputStream.flush();
				outputStream.close();
				String strResult = EncodingUtils.getString(outputStream.toByteArray(), encodingType).trim().replace("(\r\n|\r|\n)", "");

				reResult = strResult;
				inputStream.close();
			}
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return reResult;

	}

	private Bitmap RequestPageForBitmap(String uri, String postString) throws IOException {

		Bitmap bitmap = null;

		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			URL requestUrl = new URL(uri);
			conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(600000);
			conn.connect();

			if (!TextUtils.isEmpty(postString)) {
				OutputStream requestOutputStream = conn.getOutputStream();
				byte[] requestBytes = EncodingUtils.getAsciiBytes(postString);
				requestOutputStream.write(requestBytes);
				requestOutputStream.close();
			}

			if (conn.getResponseCode() == HttpStatus.SC_OK) {

				inputStream = conn.getInputStream();

				bitmap = BitmapFactory.decodeStream(inputStream);

				inputStream.close();
			}

		} catch (IOException e1) {
			throw e1;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return bitmap;

	}

	@Override
	public ResponseResult<List<String>> queryKeyword(String keyword, String pSize) throws IOException {
		return null;
	}


}
