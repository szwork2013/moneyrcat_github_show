package com.emperises.monercat.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

public class UploadImageUtil {


	/**
	 * 将图片保存到指定路径
	 * @param path
	 */
	public static File saveImage(Bitmap image,String oldPath){
			File f = new File(oldPath);
			File newPath = new File(getAlbumDir(), "small_" + f.getName());
			try {
				FileOutputStream fos = new FileOutputStream(newPath);
				image.compress(Bitmap.CompressFormat.JPEG, 40, fos);
			} catch (Exception e) {
				
			}
		return newPath;
	}
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
//	public static String bitmapToString(String filePath) {
//
//		Bitmap bm = getSmallBitmap(filePath);
//		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
//		byte[] b = baos.toByteArray();
//		
//		return Base64.encodeToString(b, Base64.DEFAULT);
//		
//	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	

	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath , String text) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 320, 480);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap b = BitmapFactory.decodeFile(filePath, options).copy(Bitmap.Config.ARGB_8888, true);
		if(!TextUtils.isEmpty(text)){
			String lin1 = "";
			String lin2 = "";
			Canvas c = new Canvas(b);
			Paint p = new Paint();
			p.setShadowLayer(10, 5, 2, Color.parseColor("#2E0088"));
			p.setColor(Color.parseColor("#880001"));
			p.setTextSize(30);
			p.setAntiAlias(true);
			p.setTypeface(Typeface.DEFAULT_BOLD);
			if(text.length() > 10){
				StringBuffer sb1 = new StringBuffer(text);
				sb1.delete(10, text.length());
				lin1 = sb1.toString();
				
				StringBuffer sb2 = new StringBuffer(text);
				sb2.delete(0, 10);
				lin2 = sb2.toString();
				c.drawText(lin1, 30, 30, p);
				c.drawText(lin2, 30, 70, p);
			} else {
				c.drawText(text, 30, 30, p);
			}
		}
		return b;
	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取保存图片的目录
	 * 
	 * @return
	 */
	public static File getAlbumDir() {
		File f = new File(Environment.getExternalStorageDirectory(),"moneycat");
		if(!f.exists()){
			f.mkdir();
		}
		return f;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "sheguantong";
	}
}
