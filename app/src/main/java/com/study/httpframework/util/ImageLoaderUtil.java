package com.study.httpframework.util;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.study.httpframework.AppContext;
import com.study.httpframework.R;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * 基于glide的图片工具类
 */
public class ImageLoaderUtil {

    private ImageLoaderUtil() {
    }

    private static ImageLoaderUtil mInstance = new ImageLoaderUtil();

    public static ImageLoaderUtil getInstance() {
        return mInstance;
    }

    /**
     * 显示图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayCommonImage(String imageUrl, ImageView imageView) {
        // Trigger the download of the URL asynchronously into the image view.
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.default_img);
            return;
        }

        Glide.with(AppContext.getmInstance())
                .load(imageUrl)    // 指定图片地址
                .placeholder(R.drawable.default_img)  // 未加载图片前的默认图
                .error(R.drawable.default_img)        // 加载失败显示的图片
                .centerCrop()      // 图片缩放类型
                .skipMemoryCache(true)    // 跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //自动选择缓存策略
                .priority(Priority.NORMAL)     // 设置加载的优先级
                .into(imageView);

    }

    /**
     * 显示图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayCommonImage(File imageUrl, ImageView imageView) {
        // Trigger the download of the URL asynchronously into the image view.
        Glide.with(AppContext.getmInstance())
                .load(imageUrl)    // 指定图片地址
                .placeholder(R.drawable.default_img)  // 未加载图片前的默认图
                .error(R.drawable.default_img)        // 加载失败显示的图片
                .centerCrop()      // 图片缩放类型
                .skipMemoryCache(true)    // 跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //自动选择缓存策略
                .priority(Priority.NORMAL)     // 设置加载的优先级
                .into(imageView);
    }

    /**
     * 显示圆角图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayCostomRoundImage(String imageUrl, ImageView imageView, int radius) {
        // Trigger the download of the URL asynchronously into the image view.
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.default_img);
            return;
        }

        Glide.with(AppContext.getmInstance())
                .load(imageUrl)    // 指定图片地址
                .placeholder(R.drawable.default_img)  // 未加载图片前的默认图
                .error(R.drawable.default_img)        // 加载失败显示的图片
                .centerCrop()      // 图片缩放类型
                .skipMemoryCache(true)    // 跳过内存缓存
                .apply(bitmapTransform(new RoundedCornersTransformation(radius, 0,
                        RoundedCornersTransformation.CornerType.ALL)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //自动选择缓存策略
                .priority(Priority.NORMAL)     // 设置加载的优先级
                .into(imageView);
    }

    /**
     * 显示圆角图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayCostomRoundImage(Object imageUrl, ImageView imageView, int radius) {
        Glide.with(AppContext.getmInstance())
                .load(imageUrl)    // 指定图片地址
                .placeholder(R.drawable.default_img)  // 未加载图片前的默认图
                .error(R.drawable.default_img)        // 加载失败显示的图片
                .centerCrop()      // 图片缩放类型
                .skipMemoryCache(true)    // 跳过内存缓存
                .apply(bitmapTransform(new RoundedCornersTransformation(radius, 0,
                        RoundedCornersTransformation.CornerType.ALL)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //自动选择缓存策略
                .priority(Priority.NORMAL)     // 设置加载的优先级
                .into(imageView);
    }

    /**
     * 显示圆角图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayRoundImage(String imageUrl, ImageView imageView) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.default_img);
            return;
        }
        Glide.with(AppContext.getmInstance())
                .load(imageUrl)    // 指定图片地址
                .placeholder(R.drawable.default_img)  // 未加载图片前的默认图
                .error(R.drawable.default_img)        // 加载失败显示的图片
                .centerCrop()      // 图片缩放类型
                .skipMemoryCache(true)    // 跳过内存缓存
                .apply(bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) //自动选择缓存策略
                .priority(Priority.NORMAL)     // 设置加载的优先级
                .into(imageView);
    }

}
