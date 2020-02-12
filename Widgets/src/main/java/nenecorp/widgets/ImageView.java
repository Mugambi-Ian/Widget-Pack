package nenecorp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageView extends LinearLayout {
    private static final String TAG = "Tag";
    private String imageUrl;
    private View fetchingImage;
    private android.widget.ImageView imageView;

    public ImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(attrs);
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageView);
        imageUrl = a.getString(R.styleable.ImageView_fileUrl);
        int imageType = a.getInt(R.styleable.ImageView_viewType, 1);
        if (imageType == 1) {
            regularImageView();
        }
        if (imageType == 2) {
            circularImageView();
        }
    }

    private void circularImageView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_image_view_circular, this, true);
        initView();
    }

    private void regularImageView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_image_view_regular, this, true);
        initView();
    }

    private void initView() {
        if (imageUrl != null && !imageUrl.equals("")) {
            String x = imageUrl;
            imageUrl = "";
            onUrlAdded(x);
        }
    }

    public void loadUrl(String url) {
        onUrlAdded(url);
    }

    private void onUrlAdded(String url) {
        fetchingImage = findViewById(R.id.VIV_progressBar);
        imageView = findViewById(R.id.VIV_imageView);
        if (!imageUrl.equals(url) && imageView != null) {
            imageUrl = url;
            Glide.with(this).load(imageUrl).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imageView.setVisibility(VISIBLE);
                    fetchingImage.setVisibility(GONE);
                    return false;
                }
            }).fitCenter().into((android.widget.ImageView) findViewById(R.id.VIV_imageView));
            imageView.setVisibility(INVISIBLE);
            fetchingImage.setVisibility(VISIBLE);
        }
    }
}
