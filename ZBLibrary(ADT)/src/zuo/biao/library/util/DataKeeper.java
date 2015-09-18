/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zuo.biao.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

/**数据存储工具类
 * @must 1.必须将fileRootPath中的包名（这里是zuo.biao.library）改为你的应用包名
 * 		 2.必须在Application中init
 */
@SuppressLint("DefaultLocale")
public class DataKeeper {

	public static final String TAG = "DataKeeper";

	public static final String SAVE_SUCCEED = "保存成功";
	public static final String SAVE_FAILED = "保存失败";
	public static final String DELETE_SUCCEED = "删除成功";
	public static final String DELETE_FAILED = "删除失败";

	public static final String ROOT_SHARE_PREFS_ = "ZBLIBRARY_SHARE_PREFS_";

	//文件缓存<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**必须将fileRootPath中的包名（这里是zuo.biao.library）改为你的应用包名*/
	public static final String fileRootPath = getSDPath() != null ? (getSDPath() + "/zuo.biao.library/") : null;
	public static final String accountPath = fileRootPath + "account/";
	public static final String audioPath = fileRootPath + "audio/";
	public static final String videoPath = fileRootPath + "video/";
	public static final String imagePath = fileRootPath + "image/";
	public static final String tempPath = fileRootPath + "temp/";
	//文件缓存>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//存储文件的类型<<<<<<<<<<<<<<<<<<<<<<<<<
	public static final int TYPE_FILE_TEMP = 0;								//保存保存临时文件
	public static final int TYPE_FILE_IMAGE = 1;							//保存图片
	public static final int TYPE_FILE_VIDEO = 2;							//保存视频
	public static final int TYPE_FILE_AUDIO = 3;							//保存语音
	//存储文件的类型>>>>>>>>>>>>>>>>>>>>>>>>>

	//不能实例化
	private DataKeeper() {}

	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		return context == null ? null : context.getSharedPreferences(ROOT_SHARE_PREFS_, Context.MODE_PRIVATE);
	}

	//获取context，获取存档数据库引用
	public static void init() {
		Log.i(TAG, "root path: " + fileRootPath);
		//判断SD卡存在
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			if(fileRootPath != null) {
				File file = new File(imagePath);
				if(!file.exists()) {
					file.mkdirs();
				}
				file = new File(videoPath);
				if(!file.exists()) {
					file.mkdir();
				}
				file = new File(audioPath);
				if(!file.exists()) {
					file.mkdir();
				}
				file = new File(fileRootPath + accountPath);
				if(!file.exists()) {
					file.mkdir();
				}
				file = new File(tempPath);
				if(!file.exists()) {
					file.mkdir();
				}
			}
		}
	}


	//**********外部存储缓存***************
	/**
	 * 存储缓存文件 返回文件绝对路径
	 * @param file
	 * 		要存储的文件
	 * @param type
	 * 		文件的类型
	 *		IMAGE = "imgae";							//图片         
	 *		VIDEO = "video";							//视频        
	 *		VOICE = "voice";							//语音         
	 *		 = "voice";							//语音         
	 * @return	存储文件的绝对路径名
	 * 		若SDCard不存在返回null
	 */
	public static String storeFile(File file, String type) {

		if(!hasSDCard()) {
			return null;
		}
		String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		byte[] data = null;
		try {
			FileInputStream in = new FileInputStream(file);
			data = new byte[in.available()];
			in.read(data, 0, data.length);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storeFile(data, suffix, type);
	}

	/** @return	存储文件的绝对路径名
				若SDCard不存在返回null */
	public static String storeFile(byte[] data, String suffix, String type) {

		if(!hasSDCard()) {
			return null;
		}
		String path = null;
		if(type.equals(TYPE_FILE_IMAGE)) {
			path = imagePath + "IMG_" + Long.toHexString(System.currentTimeMillis()).toUpperCase()
					+ "." + suffix;
		} else if(type.equals(TYPE_FILE_VIDEO)) {
			path = videoPath + "VIDEO_" + Long.toHexString(System.currentTimeMillis()).toUpperCase()
					+ "." + suffix;
		} else if(type.equals(TYPE_FILE_AUDIO)) {
			path = audioPath + "VOICE_" + Long.toHexString(System.currentTimeMillis()).toUpperCase()
					+ "." + suffix;
		}
		try {
			FileOutputStream out = new FileOutputStream(path);
			out.write(data, 0, data.length);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			path = null;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			path = null;
		}
		return path;
	}


	/**jpg
	 * @param fileName
	 * @return
	 */
	public static String getImageFileCachePath(String fileName) {
		return getFileCachePath(TYPE_FILE_IMAGE, fileName, "jpg");
	}
	/**mp4
	 * @param fileName
	 * @return
	 */
	public static String getVideoFileCachePath(String fileName) {
		return getFileCachePath(TYPE_FILE_VIDEO, fileName, "mp4");
	}
	/**mp3
	 * @param fileName
	 * @return
	 */
	public static String getAudioFileCachePath(String fileName) {
		return getFileCachePath(TYPE_FILE_AUDIO, fileName, "mp3");
	}

	/** 获取一个文件缓存的路径  */
	public static String getFileCachePath(int fileType, String fileName, String formSuffix) {

		switch (fileType) {
		case TYPE_FILE_IMAGE:
			return imagePath + fileName + "." + formSuffix; 
		case TYPE_FILE_VIDEO:
			return videoPath + fileName + "." + formSuffix; 
		case TYPE_FILE_AUDIO:
			return audioPath + fileName + "." + formSuffix; 
		default:
			return tempPath + fileName + "." + formSuffix; 
		}
	}

	/**若存在SD 则获取SD卡的路径 不存在则返回null*/
	public static String getSDPath(){
		File sdDir = null;
		String path = null;
		//判断sd卡是否存在
		boolean sdCardExist = hasSDCard();
		if (sdCardExist) {
			//获取跟目录
			sdDir = Environment.getExternalStorageDirectory();
			path = sdDir.toString();
		}
		return path;
	}

	/**判断是否有SD卡*/
	public static boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}



}