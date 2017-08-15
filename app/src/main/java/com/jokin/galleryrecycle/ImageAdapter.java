package com.jokin.galleryrecycle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jokin.galleryrecycle.ecogallery.EcoGallery;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author LittleLiByte
 * 
 */
public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageAdapter";

	private Context context;
	private List<String> filmList;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ImageAdapter(Context context) {
		this.context = context;
		this.filmList = ListInfo.getfilmInfo();
		initImageLoader();
	}

	private void initImageLoader() {
		this.imageLoader = ImageLoader.getInstance();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();

		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;	// 2147483648 够1w张照片循环了。
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return filmList.get(position % filmList.size());
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position % filmList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			Log.d(TAG, String.format("[MISS] create View for position %d", position));

			RelativeLayout layout = new RelativeLayout(context);
			if (Constants.EcoGalleryDemo) {
				layout.setLayoutParams(new EcoGallery.LayoutParams(320, 400));
			} else {
				layout.setLayoutParams(new Gallery.LayoutParams(320, 400));
			}

			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(320, 400));

			TextView textView = new TextView(context);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			textView.setLayoutParams(params);

			layout.addView(imageView);
			layout.addView(textView);

			viewHolder = new ViewHolder();
			viewHolder.imageView = imageView;
			viewHolder.textView = textView;

			convertView = layout;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

			Log.d(TAG, String.format("[HIT] use Recycle View %d for position %d", viewHolder.imageView.hashCode(), position));
		}

		ImageView imageView = viewHolder.imageView;
		imageLoader.displayImage(filmList.get(position % filmList.size()), imageView, options);
		imageView.setScaleType(ScaleType.CENTER_CROP);

		TextView textView = viewHolder.textView;
		textView.setTextSize(21);
		textView.setTextColor(Color.YELLOW);
		textView.setText(String.valueOf(position));

		return convertView;
	}

	public List<String> getData() {
        return filmList;
    }

    private static class ViewHolder {
		private ImageView imageView;
		private TextView textView;
	}
}